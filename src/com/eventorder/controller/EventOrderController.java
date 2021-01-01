package com.eventorder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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

import com.eventorder.model.EventOrderService;
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
		if (req.getParameter("action").equals("go-check-out")) {
			HttpSession session = req.getSession();
			
			MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
			String member_id = memberVo.getMemberId();

			if (session.getAttribute("ticket" + member_id) == null) {

				String[] ticket_id_list = req.getParameterValues("ticket_id");
				String[] orderList_goods_amount_list = req.getParameterValues("orderlist_goods_amount");
				Map<String, Integer> cartList = new HashMap<String, Integer>();
				TicketService ticketSvc = new TicketService();

				for (int i = 0; i < ticket_id_list.length; i++) {
					Integer orderList_goods_amount = null;
					orderList_goods_amount = new Integer(orderList_goods_amount_list[i]);
					if (orderList_goods_amount.intValue() != 0 && orderList_goods_amount != null) {
						cartList.put(ticket_id_list[i], orderList_goods_amount);
					}

					ServletContext context = getServletContext();
					ConcurrentHashMap<String, Integer> ticketRestAmount = (ConcurrentHashMap<String, Integer>) context
							.getAttribute("ticketRestAmount");
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
												ticketRestAmount.get(ticket_id) - cartList.get(ticket_id));
									}
								}

								session.removeAttribute("ticket" + member_id);
							}

						};
						Calendar cal = Calendar.getInstance();
						timer.schedule(task, (cal.getTimeInMillis()) + 10 * 60 * 1000);

						session.setAttribute("timer", timer);
						session.setAttribute("ticket" + member_id, cartList);
					}
					String url = "/front-end/eventorder/checkOutPage.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}

			}
		}

		if (req.getParameter("action").equals("check-out")) {
			HttpSession session = req.getSession();
			if (session.getAttribute("memberVo") != null) {
				String member_id = ((MemberVo) session.getAttribute("memberVo")).getMemberId();
				if (session.getAttribute("ticket" + member_id) != null) {
					Map<String, Integer> cartList = (HashMap<String, Integer>) session
							.getAttribute("ticket" + member_id);
					TicketService ticketSvc = new TicketService();
					String event_id = ticketSvc.getOneTicket(cartList.keySet().iterator().next()).getEvent_id();
					Timestamp order_place_time = new Timestamp(System.currentTimeMillis());
					String order_name = req.getParameter("order_name");
					String order_mail = req.getParameter("orde_mail");
					String order_phone = req.getParameter("order_phone");
					EventOrderService eventOrderSvc = new EventOrderService();
					eventOrderSvc.addOrder(member_id, event_id, order_place_time, order_name, order_mail, order_phone,
							cartList);
					
					Timer timer = (Timer)session.getAttribute("timer");
					timer.cancel();
				}
			}

		}

	}

	public void destroy() {
		timer.cancel();
	}

}
