package com.eventorder.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

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

			// 更新票券狀態
			EventOrderListService eventOrderListSvc = new EventOrderListService();
			EventOrderListVO eventOrderListVO = eventOrderListSvc.getOneByOrderListId(orderlist_id);
			EventOrderService eventOrderSvc = new EventOrderService();
			EventOrderVO eventOrderVO = eventOrderSvc.getOneByEventOrderId(eventOrderListVO.getEvent_order_id());
			TicketService ticketSvc = new TicketService();
			TicketVO ticketVO = ticketSvc.getOneTicket(eventOrderListVO.getTicket_id());
			Gson gson = new Gson();
			JedisPool pool = JedisPoolUtil.getJedisPool();
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(11);

			char[] originMail = eventOrderVO.getOrder_mail().toCharArray();
			for (int i = 0; i < eventOrderVO.getOrder_mail().length(); i++) {
				if (i == 0) {
					continue;
				} else if (i >= (eventOrderVO.getOrder_mail().indexOf("@") - 1)) {
					continue;
				} else {
					originMail[i] = '*';
				}
			}
			String ticketOwner = new String(originMail);
			// 查找完成 準備轉交

			Date dt = new Date();

			int originStatus = eventOrderListVO.getOrderlist_status();
			if (originStatus == 0) {
				Integer status = new Integer(1);
				eventOrderListSvc.updateOrderStatus(status, eventOrderListVO);
			} else {

				dt = gson.fromJson(jedis.get(orderlist_id), java.util.Date.class);
				
				jedis.close();
				req.setAttribute("time", dt);
				req.setAttribute("ticketOwner", ticketOwner);
				req.setAttribute("ticketVO", ticketVO);

				String url = "/front-end/eventorder/alreadyCheck.jsp";
				RequestDispatcher alreadyCheck = req.getRequestDispatcher(url);
				alreadyCheck.forward(req, res);
				
				return;
			}

			String checkInTime = gson.toJson(dt);

			if (!jedis.exists(orderlist_id)) {
				jedis.set(orderlist_id, checkInTime);
			}

			jedis.close();

			// 查找會員資料與票券資料

			String url = "/front-end/eventorder/checkSuccess.jsp";
			req.setAttribute("time", dt);
			req.setAttribute("ticketOwner", ticketOwner);
			req.setAttribute("ticketVO", ticketVO);

			RequestDispatcher successPage = req.getRequestDispatcher(url);
			successPage.forward(req, res);

		}
	}

}
