package com.favorites.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
import com.google.gson.Gson;



@WebServlet("/FavoritesServlet")
public class FavoritesServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("uniqueid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/favorites/F_home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String uniqueid = null;
				try {
					uniqueid = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/favorites/F_home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				FavoritesService favSvc = new FavoritesService();
				FavoritesVO favoritesVO = favSvc.getOneFavorites(uniqueid);
				if (favoritesVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/favorites/F_home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("favoritesVO", favoritesVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/favorites/listOneFavorites.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/favorites/F_home.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String uniqueid = new String(req.getParameter("uniqueid"));
		
				/***************************2.�}�l�d�߸��****************************************/
				FavoritesService favSvc = new FavoritesService();
				FavoritesVO favoritesVO = favSvc.getOneFavorites(uniqueid);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("favoritesVO", favoritesVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/favorites/update_favorites_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/favorites/listAllFavorites.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String uniqueid = new String(req.getParameter("uniqueid").trim());
				
				String member_id = new String(req.getParameter("member_id").trim());
				if (member_id == null || member_id.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				}
				Integer favorite_type = new Integer(req.getParameter("favorite_type").trim());
				
				String favorite_id = new String(req.getParameter("favorite_id").trim());
				if (favorite_id == null || favorite_id.trim().length() == 0) {
					errorMsgs.add("收藏編號: 請勿空白");
				}
				java.sql.Timestamp favorite_add_time = null;
				try {
					favorite_add_time = java.sql.Timestamp.valueOf(req.getParameter("favorite_add_time").trim());
				} catch (IllegalArgumentException e) {
					favorite_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
						
				FavoritesVO favoritesVO = new FavoritesVO();			
				favoritesVO.setUniqueid(uniqueid);
				favoritesVO.setMember_id(member_id);
				favoritesVO.setFavorite_type(favorite_type);
				favoritesVO.setFavorite_id(favorite_id);
				favoritesVO.setFavorite_add_time(favorite_add_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("favoritesVO", favoritesVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/favorites/update_favorites_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				FavoritesService favSvc = new FavoritesService();
				favoritesVO = favSvc.updateFavorites(uniqueid, member_id, favorite_type, favorite_id, favorite_add_time);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("favoritesVO", favoritesVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/back-end/favorites/listOneFavorites.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/favorites/update_favorites_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				
				String member_id = new String(req.getParameter("member_id").trim());
				if (member_id == null || member_id.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				}
				Integer favorite_type = new Integer(req.getParameter("favorite_type").trim());
				
				String favorite_id = new String(req.getParameter("favorite_id").trim());				
				if (favorite_id == null || favorite_id.trim().length() == 0) {
					errorMsgs.add("收藏編號: 請勿空白");
				}
				java.sql.Timestamp favorite_add_time = null;
				try {
					favorite_add_time = java.sql.Timestamp.valueOf(req.getParameter("favorite_add_time").trim());
				} catch (IllegalArgumentException e) {
					favorite_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
						

				FavoritesVO favoritesVO = new FavoritesVO();			
				favoritesVO.setMember_id(member_id);
				favoritesVO.setFavorite_type(favorite_type);
				favoritesVO.setFavorite_id(favorite_id);
				favoritesVO.setFavorite_add_time(favorite_add_time);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("favoritesVO", favoritesVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/favorites/addFavorites.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				FavoritesService favSvc = new FavoritesService();
				favoritesVO = favSvc.addFavorites(member_id, favorite_type, favorite_id, favorite_add_time);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/favorites/listAllFavorites.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/favorites/addFavorites.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String uniqueid = new String(req.getParameter("uniqueid"));
				
				/***************************2.�}�l�R�����***************************************/
				FavoritesService favSvc = new FavoritesService();
				favSvc.deleteFavorites(uniqueid);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/back-end/favorites/listAllFavorites.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/favorites/listAllFavorites.jsp");
				failureView.forward(req, res);
			}
		}
		if ("deleteFav".equals(action)) { 
				String favId = new String(req.getParameter("favId"));
		
				FavoritesService favSvc = new FavoritesService();
				favSvc.deleteFav(favId);
				
		}
		
		//新增收藏
		if("addfav".equals(action)) {
			String memberid = req.getParameter("memberid");
			String favid = req.getParameter("favid");
			Integer type = Integer.valueOf(req.getParameter("type"));
			FavoritesService favSvc = new FavoritesService();
			favSvc.addfav(memberid, favid, type);
		}
		//移除收藏
		if("deletefav17".equals(action)) {
			String memberid = req.getParameter("memberid");
			String favid = req.getParameter("favid");
			FavoritesService favSvc = new FavoritesService();
			favSvc.deletefav17(memberid, favid);
		}
		//查詢使用者全部收藏
		if("getAllMeberfav".equals(action)) {
			String memberid = req.getParameter("memberid");
			FavoritesService favSvc = new FavoritesService();
			
			String jsonObjectString = new Gson().toJson(favSvc.getAllMeberfav(memberid));
			
			PrintWriter out = res.getWriter();
			out.print(jsonObjectString);
			out.flush();
		}
	}
  
}
