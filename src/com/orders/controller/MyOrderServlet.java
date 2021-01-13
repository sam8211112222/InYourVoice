package com.orders.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.event.model.EventService;
import com.eventorder.model.EventOrderService;
import com.eventorder.model.EventOrderVO;
import com.eventorderlist.model.EventOrderListService;
import com.eventorderlist.model.EventOrderListVO;
import com.member.model.MemberVo;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

/**
 * Servlet implementation class MyOrderServlet
 */
@WebServlet("/orders/myOrderServlet")
public class MyOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrdersService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyOrderServlet() {
		super();
		service = new OrdersService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("showOrderDetail".equals(action)) {

//			String url = "/views/orders/listAllOrders.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);

		} else if (action == null || "".equals(action)) {
			HttpSession session = req.getSession();
			if (session.getAttribute("memberVo") != null) {
				MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
				String member_id = loginMember.getMemberId();

				List<OrdersVO> orderList = service.findByMemberId(member_id);
				req.setAttribute("orderList", service.getAll());

				
				//柏崴的code
				EventOrderService eventOrderSvc = new EventOrderService();
				List<EventOrderVO> eventOrderList = eventOrderSvc.getListByMemberId(member_id);
				EventService eventSvc = new EventService();
				EventOrderListService eventOrderListSvc = new EventOrderListService();
				TicketService ticketSvc = new TicketService();
				Map<String, Integer> amountPrice = new HashMap<String, Integer>();

				for (EventOrderVO eventOrderVO : eventOrderList) {
					String event_order_id = eventOrderVO.getEvent_order_id();
					List<EventOrderListVO> list = eventOrderListSvc.getListByEventOrderId(event_order_id);
					Integer oneListPrice = new Integer(0);
					for (EventOrderListVO vo : list) {
						TicketVO ticketVO = ticketSvc.getOneTicket(vo.getTicket_id());
						oneListPrice += ticketVO.getTicket_price() * vo.getOrderlist_goods_amount();
					}
					
					amountPrice.put(event_order_id, oneListPrice);
				}
				
				
				req.setAttribute("amountPrice", amountPrice);
				req.setAttribute("eventOrderList", eventOrderList);
				req.setAttribute("eventSvc", eventSvc);

				String url = "/front-end/orders/protect/myOrders.jsp";
				req.setAttribute("orderList", orderList);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
