package com.tags.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tags.model.TagsService;
import com.tags.model.TagsVO;


@WebServlet("/tags/tags.do")
public class TagsServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("tag_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入Tag ID");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tags/select_tags.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
			
				/***************************2.開始查詢資料*****************************************/
				
				String tag_id = str;
				TagsService tagSvc = new TagsService();
				TagsVO tagsVO = tagSvc.getOneTag(tag_id);
				if (tagsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tags/select_tags.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("tagsVO", tagsVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/tags/list_one_tag.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/tags/select_tags.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String tag_id = req.getParameter("tag_id");
				
				/***************************2.開始查詢資料****************************************/
				TagsService tagsSvc = new TagsService();
				TagsVO tagsVO = tagsSvc.getOneTag(tag_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("tagsVO", tagsVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/tags/update_tag_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/tags/select_tags.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String tag_id = req.getParameter("tag_id");
				
				String tag_name = req.getParameter("tag_name");
				
				java.sql.Timestamp tag_add_time = new java.sql.Timestamp(System.currentTimeMillis());

				TagsVO tagsVO = new TagsVO();
				tagsVO.setTag_id(tag_id);
				tagsVO.setTag_name(tag_name);
				tagsVO.setTag_add_time(tag_add_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tagsVO", tagsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tags/update_tag_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				TagsService tagSvc = new TagsService();
				tagsVO = tagSvc.updateTags(tag_id, tag_name, tag_add_time);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("tagsVO", tagsVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/tags/list_one_tag.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/tags/update_tag_input.jsp");
				failureView.forward(req, res);
			}
		}
		
        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String tag_name = req.getParameter("tag_name");
								
				java.sql.Timestamp tag_add_time = new java.sql.Timestamp(System.currentTimeMillis());
				
				TagsVO tagsVO = new TagsVO();
				tagsVO.setTag_name(tag_name);
				tagsVO.setTag_add_time(tag_add_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tagsVO", tagsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/tags/add_tags.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				TagsService tagsSvc = new TagsService();
				tagsVO = tagsSvc.insertTags(tag_name, tag_add_time);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/tags/list_all_tags.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/tags/add_tags.jsp");
				failureView.forward(req, res);
			}
		}
        
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String tag_id = req.getParameter("tag_id");
				
				/***************************2.開始刪除資料***************************************/
				TagsService tagsSvc = new TagsService();
				tagsSvc.deleteTag(tag_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/tags/list_all_tags.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/tags/select_tags.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}

}
