package com.eventorder.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event.model.EventService;
import com.event.model.EventVO;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

@WebServlet("/EventOrderController")
public class EventOrderController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		if (req.getParameter("event_id") != null) {
			String event_id = req.getParameter("event_id").trim();
			if (event_id != null & event_id.length() != 0) {
				EventService eventSvc = new EventService();
				TicketService ticketSvc = new TicketService();

				EventVO eventVO = eventSvc.getOneEvent(event_id);
				List<TicketVO> ticketList = ticketSvc.getTicketByEventId(event_id);
				
				req.setAttribute("eventVO", eventVO);
				req.setAttribute("ticketList",ticketList);
				
				String url = "/front-end/eventorder/listOneToBuy.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
			}

		}
	}

}
