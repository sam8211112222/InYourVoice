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
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.util.JedisPoolUtil;

@WebServlet("/EventPicController")
public class EventPicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		res.setContentType("image/gif");
		ServletOutputStream sos = res.getOutputStream();
		EventService eventSvc = new EventService();

		if (req.getParameter("event_id") != null && req.getParameter("event_id").trim().length() != 0) {

			String event_id = req.getParameter("event_id");
			EventVO eventVO = eventSvc.getOneEvent(event_id);
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

		if ("send-mail".equals(action)) {
			
			String orderlist_id =req.getParameter("orderlist_id");
			
			JedisPool pool = JedisPoolUtil.getJedisPool();
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(10);
			
			Gson gson = new Gson();
			
			String qrCode = jedis.get(orderlist_id);
			byte[] qrCodeByte = gson.fromJson(qrCode, byte[].class);
			sos.write(qrCodeByte);
			sos.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
