package com.eventorder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.event.model.EventService;
import com.event.model.EventVO;
import com.eventorder.model.EventOrderService;
import com.eventorder.model.TicketRedisThread;
import com.eventorderlist.model.EventOrderListService;
import com.eventorderlist.model.EventOrderListVO;
import com.member.model.MemberVo;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

@WebServlet(urlPatterns = "/EventOrderController", loadOnStartup = 1)
public class EventOrderController extends HttpServlet {
	Timer timer;

	public void init() throws ServletException {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			public void run() {
				TicketService ticketSvc = new TicketService();
				EventOrderListService eventOrderListSvc = new EventOrderListService();
				List<TicketVO> listAllTicket = ticketSvc.getAll();
				List<EventOrderListVO> listAllOrderItem = eventOrderListSvc.getAll();
				HashMap<String, Integer> orderItemAmount = new HashMap<String, Integer>();
				ConcurrentHashMap<String, Integer> ticketRestAmount = new ConcurrentHashMap<String, Integer>();
				ServletContext context = getServletContext();

				for (EventOrderListVO item : listAllOrderItem) {
					if (orderItemAmount.containsKey(item.getTicket_id())) {
						orderItemAmount.replace(item.getTicket_id(),
								orderItemAmount.get(item.getTicket_id()) + item.getOrderlist_goods_amount());
					} else {
						orderItemAmount.put(item.getTicket_id(), item.getOrderlist_goods_amount());
					}
				}
				for (TicketVO ticket : listAllTicket) {
					if (orderItemAmount.containsKey(ticket.getTicket_id())) {
						ticketRestAmount.put(ticket.getTicket_id(),
								ticket.getTicket_amount() - orderItemAmount.get(ticket.getTicket_id()));
					} else {
						ticketRestAmount.put(ticket.getTicket_id(), ticket.getTicket_amount());
					}
				}

				context.setAttribute("ticketRestAmount", ticketRestAmount);

			}
		};
		Calendar cal = Calendar.getInstance();
		GregorianCalendar gc = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		timer.scheduleAtFixedRate(task, gc.getTime(), 2 * 60 * 60 * 1000);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("go-check-out".equals(action)) {
			HttpSession session = req.getSession();
			System.out.println("有近來");
			if (session.getAttribute("memberVo") == null) {
				session.setAttribute("location", req.getRequestURI() + "?" + req.getQueryString());
				res.sendRedirect(req.getContextPath() + "/front-end/member/Login.jsp");
				return;
			}
			MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
			String member_id = memberVo.getMemberId();
			ServletContext context = getServletContext();
			ConcurrentHashMap<String, Integer> ticketRestAmount = (ConcurrentHashMap<String, Integer>) context
					.getAttribute("ticketRestAmount");
			if (session.getAttribute("ticket" + member_id) == null) {

				System.out.println("購物車是空的");

				String[] ticket_id_list = req.getParameterValues("ticket_id");
				String[] orderList_goods_amount_list = req.getParameterValues("orderlist_goods_amount");
				Map<String, Integer> cartList = new HashMap<String, Integer>();

				for (int i = 0; i < ticket_id_list.length; i++) {
					Integer orderList_goods_amount = null;
					orderList_goods_amount = new Integer(orderList_goods_amount_list[i]);
					if (orderList_goods_amount.intValue() != 0 && orderList_goods_amount != null) {
						if (ticketRestAmount.get(ticket_id_list[i]) < orderList_goods_amount) {
							res.sendRedirect(
									req.getContextPath()+"/event/EventServlet?action=getOne_For_Display&event_id="
											+ req.getParameter("event_id"));
							return;
						}
						cartList.put(ticket_id_list[i], orderList_goods_amount);
					}
				}
				if (!cartList.isEmpty()) {
					

					Set<String> cartListKeys = cartList.keySet();
					Iterator<String> it = cartListKeys.iterator();
					while (it.hasNext()) {
						String ticket_id = (String) it.next();
						if (ticketRestAmount.containsKey(ticket_id)) {
							ticketRestAmount.replace(ticket_id,
									ticketRestAmount.get(ticket_id) - cartList.get(ticket_id));
						}
					}

					session.setAttribute("ticket" + member_id, cartList);

					Timer timer = new Timer();
					TimerTask task = new TimerTask() {

						public void run() {

							Map<String, Integer> cartList = (HashMap<String, Integer>) session
									.getAttribute("ticket" + member_id);
							ConcurrentHashMap<String, Integer> ticketRestAmount = (ConcurrentHashMap<String, Integer>) context
									.getAttribute("ticketRestAmount");
							Set<String> cartListKeys = cartList.keySet();
							Iterator<String> it = cartListKeys.iterator();
							while (it.hasNext()) {
								String ticket_id = (String) it.next();
								if (ticketRestAmount.containsKey(ticket_id)) {
									ticketRestAmount.replace(ticket_id,
											ticketRestAmount.get(ticket_id) + cartList.get(ticket_id));
								}
							}
							System.out.println("timer執行");
							session.removeAttribute("ticket" + member_id);
							System.out.println(TimeOut.connectedSessions);
							if (TimeOut.connectedSessions.containsKey(member_id)) {
								TimeOut.connectedSessions.get(member_id).getAsyncRemote().sendText("go-out");
								System.out.println("訊息發送成功");
							}

						}

					};
					timer.schedule(task, 1 * 60 * 1000);
					session.setAttribute("timer", timer);

					req.setAttribute("event_id", req.getParameter("event_id"));
					String url = "/front-end/eventorder/protect/checkOutPage.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					System.out.println("success");
					return;
				} else {
					System.out.println("沒選東西,重導回前頁");
					res.sendRedirect(
							req.getContextPath()+"/event/EventServlet?action=getOne_For_Display&event_id="
									+ req.getParameter("event_id"));
					return;

				}

			} else {
				req.setAttribute("event_id", req.getParameter("event_id"));
				String url = "/front-end/eventorder/protect/checkOutPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
			}

		}

		if ("check-out".equals(action)) {
			HttpSession session = req.getSession();
			String member_id = null;

			if (session.getAttribute("memberVo") != null) {
				member_id = ((MemberVo) session.getAttribute("memberVo")).getMemberId();
			}

			if (session.getAttribute("ticket" + member_id) != null) {
				Map<String, Integer> cartList = (HashMap<String, Integer>) session.getAttribute("ticket" + member_id);
				TicketService ticketSvc = new TicketService();
				String event_id = ticketSvc.getOneTicket(cartList.keySet().iterator().next()).getEvent_id();
				Timestamp order_place_time = new Timestamp(System.currentTimeMillis());
				String order_name = req.getParameter("orderName");
				String order_mail = req.getParameter("orderMail");
				String order_phone = req.getParameter("orderPhone");
				String orderlist_remarks = req.getParameter("remarks");

				EventOrderService eventOrderSvc = new EventOrderService();
				Map<String, List<String>> orders = eventOrderSvc.addOrder(member_id, event_id, order_place_time,
						order_name, order_mail, order_phone, orderlist_remarks, cartList);
				if (session.getAttribute("timer") != null) {
					Timer timer = (Timer) session.getAttribute("timer");
					timer.cancel();
				}

				Integer ticketPrice = new Integer(0);
				Iterator<String> it = cartList.keySet().iterator();
				while (it.hasNext()) {
					String ticket_id = it.next();
					ticketPrice += (ticketSvc.getOneTicket(ticket_id).getTicket_price() * cartList.get(ticket_id));
				}

				EventService eventSvc = new EventService();
				EventVO eventVO = eventSvc.getOneEvent(event_id);
				String event_title = eventVO.getEvent_title();

				TicketRedisThread ticketRedisThread = new TicketRedisThread(order_mail, event_title, orders);
				Thread th = new Thread(ticketRedisThread);
				th.start();

				String orderId = null;
				Set<String> keySet = orders.keySet();
				for (String key : keySet) {
					orderId = key;
				}

				session.removeAttribute("ticket" + member_id);
				req.setAttribute("orderId", orderId);
				req.setAttribute("order_place_time", order_place_time);
				req.setAttribute("ticketPrice", ticketPrice);
				String url = "/front-end/eventorder/checkoutsuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				System.out.println("success");

			}

		}

		if ("cancel".equals(action)) {
			HttpSession session = req.getSession();
			ServletContext context = req.getServletContext();
			if (session.getAttribute("memberVo") != null) {
				MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
				String member_id = memberVo.getMemberId();
				if (session.getAttribute("ticket" + member_id) != null) {
					System.out.println("有近來取消timer");
					Map<String, Integer> cartList = (HashMap<String, Integer>) session
							.getAttribute("ticket" + member_id);
					ConcurrentHashMap<String, Integer> ticketRestAmount = (ConcurrentHashMap<String, Integer>) context
							.getAttribute("ticketRestAmount");
					Set<String> cartListKeys = cartList.keySet();
					Iterator<String> it = cartListKeys.iterator();
					while (it.hasNext()) {
						String ticket_id = (String) it.next();
						if (ticketRestAmount.containsKey(ticket_id)) {
							ticketRestAmount.replace(ticket_id,
									ticketRestAmount.get(ticket_id) + cartList.get(ticket_id));
						}
					}
					if (session.getAttribute("timer") != null) {
						Timer timer = (Timer) session.getAttribute("timer");
						timer.cancel();
					}
					session.removeAttribute("ticket" + member_id);
				}
			}

			res.sendRedirect("http://localhost:8081/TEA102G6/event/EventServlet?action=getOne_For_Display&event_id="
					+ req.getParameter("event_id"));
		}

		if ("getOrderDetail".equals(action)) {
			String event_order_id = req.getParameter("event_order_id");

			List<EventOrderListVO> list = new EventOrderListService().getListByEventOrderId(event_order_id);
			TicketService ticketSvc = new TicketService();

			Integer amountPrice = new Integer(0);

			for (EventOrderListVO item : list) {
				String ticket_id = item.getTicket_id();
				TicketVO ticketVO = ticketSvc.getOneTicket(ticket_id);
				amountPrice += ticketVO.getTicket_price() * item.getOrderlist_goods_amount();
			}

			req.setAttribute("list", list);
			req.setAttribute("ticketSvc", ticketSvc);
			req.setAttribute("amountPrice", amountPrice);

			String url = "/front-end/orders/protect/eventOrdersDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

	}

	public void destroy() {
		timer.cancel();
	}

}
