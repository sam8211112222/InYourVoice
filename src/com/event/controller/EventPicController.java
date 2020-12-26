package com.event.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event.model.EventService;
import com.event.model.EventVO;

@WebServlet("/EventPicController")
public class EventPicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream sos = res.getOutputStream();

		EventService eventSvc = new EventService();
		String event_id = null;
		EventVO eventVO = null;

		if (req.getParameter("event_id") != null && req.getParameter("event_id").trim().length() != 0) {
			event_id = req.getParameter("event_id");
			eventVO = eventSvc.getOneEvent(event_id);
		}
		
		try {
			if ("getEventPoster".equals(req.getParameter("action"))) {
					sos.write(eventVO.getEvent_poster());
			} else if ("getEventSeat".equals(req.getParameter("action"))) {
					sos.write(eventVO.getEvent_seat());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sos.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
