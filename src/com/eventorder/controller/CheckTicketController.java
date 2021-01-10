package com.eventorder.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventorder.model.EventOrderService;
import com.eventorder.model.EventOrderVO;
import com.eventorderlist.model.EventOrderListService;
import com.eventorderlist.model.EventOrderListVO;
import com.member.model.MemberService;
import com.member.model.MemberVo;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

/**
 * Servlet implementation class CheckTicketController
 */
@WebServlet("/CheckTicketController")
public class CheckTicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");

		if ("check-in".equals(action)) {
			//取得票券ID
			String orderlist_id = req.getParameter("orderListId");
			
			//更新票券狀態
			EventOrderListService eventOrderListSvc = new EventOrderListService();
			EventOrderListVO eventOrderListVO = eventOrderListSvc.getOneByOrderListId(orderlist_id);
			Integer status = new Integer(1);
			eventOrderListSvc.updateOrderStatus(status, eventOrderListVO);
			
			//查找會員資料與票券資料
			EventOrderService eventOrderSvc = new EventOrderService();
			EventOrderVO eventOrderVO = eventOrderSvc.getOneByEventOrderId(eventOrderListVO.getEvent_order_id());
			MemberService memberSvc  = new MemberService();
			TicketService ticketSvc = new TicketService();
			MemberVo memberVO = memberSvc.getOne(eventOrderVO.getMember_id());
			TicketVO ticketVO = ticketSvc.getOneTicket(eventOrderListVO.getTicket_id());
			
			//查找完成 準備轉交
			
			LocalDateTime dt = LocalDateTime.now();
			
			String url ="";
			req.setAttribute("time", dt);
			req.setAttribute("memberVO", memberVO);
			req.setAttribute("ticketVO", ticketVO);
			
			RequestDispatcher successPage = req.getRequestDispatcher(url);
			successPage.forward(req, res);
			
		}
	}

}
