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
import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVo;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.util.JedisPoolUtil;

@WebServlet("/CheckTicketController")
public class CheckTicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");

		if ("check-in".equals(action)) {
			// 取得票券ID
			String orderlist_id = req.getParameter("orderListId");
			System.out.println(orderlist_id);

			// 更新票券狀態
			EventOrderListService eventOrderListSvc = new EventOrderListService();
			EventOrderListVO eventOrderListVO = eventOrderListSvc.getOneByOrderListId(orderlist_id);
			System.out.println(eventOrderListVO);
			int originStatus = eventOrderListVO.getOrderlist_status();
			if (originStatus == 0) {
				Integer status = new Integer(1);
				eventOrderListSvc.updateOrderStatus(status, eventOrderListVO);
			} else {
				String url = "/front-end/eventorder/alreadyCheck.html";
				RequestDispatcher alreadyCheck = req.getRequestDispatcher(url);
				alreadyCheck.forward(req, res);
			}


			JedisPool pool = JedisPoolUtil.getJedisPool();
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(10);
			
			if(jedis.exists(orderlist_id)) {
				jedis.del(orderlist_id);
			}
			
			jedis.close();

			// 查找會員資料與票券資料
			EventOrderService eventOrderSvc = new EventOrderService();
			EventOrderVO eventOrderVO = eventOrderSvc.getOneByEventOrderId(eventOrderListVO.getEvent_order_id());
			MemberService memberSvc = new MemberService();
			TicketService ticketSvc = new TicketService();
			MemberVo memberVO = memberSvc.getOne(eventOrderVO.getMember_id());
			TicketVO ticketVO = ticketSvc.getOneTicket(eventOrderListVO.getTicket_id());

			// 查找完成 準備轉交

			LocalDateTime dt = LocalDateTime.now();

			String url = "/front-end/eventorder/checkSuccess.html";
			req.setAttribute("time", dt);
			req.setAttribute("memberVO", memberVO);
			req.setAttribute("ticketVO", ticketVO);

			RequestDispatcher successPage = req.getRequestDispatcher(url);
			successPage.forward(req, res);

		}
	}

}
