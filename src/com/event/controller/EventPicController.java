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
import com.utils.ImageUtil;

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

		if ("getEventPic".equals(action)) {
			String event_id = req.getParameter("event_id");
			EventVO eventVO = eventSvc.getOneEvent(event_id);
			System.out.println(event_id);
			System.out.println(eventVO);
			byte[] pic = eventVO.getEvent_poster();
			byte[] pic2 = ImageUtil.shrink(pic, 250);
			res.setContentLength(pic2.length);
			sos.write(pic2);	
			sos.close();
			return;
		}
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
			
			String orderlist_id =req.getParameter("orderListId");
			
			JedisPool pool = JedisPoolUtil.getJedisPool();
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(10);
			
			Gson gson = new Gson();
			
			String qrCode = jedis.get(orderlist_id);
			byte[] qrCodeByte = gson.fromJson(qrCode, byte[].class);
			sos.write(qrCodeByte);
			sos.close();
			jedis.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
