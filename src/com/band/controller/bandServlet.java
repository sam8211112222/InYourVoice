package com.band.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.band.model.BandService;
import com.band.model.BandVO;

import sun.rmi.server.Dispatcher;


@WebServlet("/band/band.do")
public class bandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String action = req.getParameter("action");
		res.setCharacterEncoding("UTF-8");

// 取得樂團頁面上半部
		if("getBandMain".equals(action)) {
			String band_id = req.getParameter("band_id");
			BandService bandSvc = new BandService();
			BandVO bandVO = bandSvc.getOneBand(band_id);
			req.setAttribute("bandVO", bandVO);
			
			RequestDispatcher bandIndexView = req.getRequestDispatcher("/front-end/band/index_band.jsp");
			bandIndexView.forward(req, res);
		}
		
// 頁籤中的"關於樂團"被按下後，回傳下半部關於樂團的頁面
//		if("getBandIntro".equals(action)) {
//			String band_id = req.getParameter("band_id");
//			BandVO bandVO = new BandVO();
//			bandVO.setBand_id(band_id);
//			RequestDispatcher bandIntroView = req.getRequestDispatcher("/front-end/band/band_intro.jsp");
//			bandIntroView.forward(req, res);
//		}
//		
		
		
		
	}

}
