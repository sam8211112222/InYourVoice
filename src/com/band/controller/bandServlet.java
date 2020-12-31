package com.band.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.band.model.BandService;
import com.band.model.BandVO;
import com.utils.DataSourceUtils;

@WebServlet("/band/band.do")
public class bandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		res.setCharacterEncoding("UTF-8");

// 取得樂團頁面上半部
		if ("getBandMain".equals(action)) {
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

		if ("getBandPhoto".equals(action)) {

			String band_id = req.getParameter("band_id");
			ServletOutputStream out = res.getOutputStream();

			try {
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(band_id);
				res.setContentType("image/gif");
				res.setContentLength(bandVO.getBand_photo().length);
				out.write(bandVO.getBand_photo());
			} catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/images/fileNotFound.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}

		}
		
		if ("getBandBanner".equals(action)) {

			String band_id = req.getParameter("band_id");
			ServletOutputStream out = res.getOutputStream();

			try {
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(band_id);
				res.setContentType("image/gif");
				res.setContentLength(bandVO.getBand_banner().length);
				out.write(bandVO.getBand_banner());
			} catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/images/fileNotFound.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}

		}

//		
		if ("listAllBand".equals(action)) {
			
			BandService bandSvc = new BandService();
			DataSource ds = null;

			// 取得關鍵字
			// 關鍵字
			String searchKeyWord = req.getParameter("searchKeyWord");
			System.out.println(searchKeyWord);
			// band tag

			// 若無關鍵字則list all
			if (searchKeyWord == null || searchKeyWord.length() == 0) {
				System.out.println("no searchKeyWord");
				// list all
				List<BandVO> bandVOList = bandSvc.getAllBand();
				req.setAttribute("bandVOList", bandVOList);
				String url = "/front-end/band/listAllBand.jsp";
				RequestDispatcher listAllView = req.getRequestDispatcher(url);
				listAllView.forward(req, res);

			} else {
				// 依照關鍵字及band tag進資料庫搜尋
				System.out.println("yes searchKeyWord");

				List<BandVO> bandVOList = bandSvc.getBySearchKeyWord(searchKeyWord);
				req.setAttribute("bandVOList", bandVOList);
				String url = "/front-end/band/listAllBand.jsp";
				RequestDispatcher searchView = req.getRequestDispatcher(url);
				searchView.forward(req, res);
			}

		}

	}

}
