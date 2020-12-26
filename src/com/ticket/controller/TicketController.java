package com.ticket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ticket.model.TicketService;

@WebServlet("/TicketController")
public class TicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String ticket_id = req.getParameter("ticket_id");
		TicketService ticketSvc = new TicketService();
		String returnMsg = null;
		try {
			if (ticketSvc.deleteTicket(ticket_id) != 0) {
				Map<String, String> prepareForTrans = new HashMap<String, String>();
				prepareForTrans.put("msg", "success");
				Gson gson = new Gson();
				returnMsg = gson.toJson(prepareForTrans);
			}
			
			PrintWriter out = res.getWriter();
			out.write(returnMsg);			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
