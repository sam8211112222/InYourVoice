//package com.orders.controller;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.orders.model.OrdersService;
//import com.orders.model.OrdersVO;
//
//@WebServlet("/ordrs/selectOneOrderServlet")
//public class SelectOneOrderServlet extends HttpServlet {
//
//	private OrdersService service;
//
//	@Override
//	public void init() throws ServletException {
//		super.init();
//
//		service = new OrdersService();
//	}
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		doPost(req, res);
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		String action = req.getParameter("action");
//
//		// 來自select_page.jsp的請求
//		if ("getOne_For_Display".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			String order_id = req.getParameter("order_id");
//
//			/*************************** 2.開始查詢資料 *****************************************/
//
//			OrdersService ordersSvc = new OrdersService();
//			OrdersVO ordersVO = ordersSvc.getOneOrders(order_id);
//			if (ordersVO == null) {
//				errorMsgs.add("查無資料");
//			}
//			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//			req.setAttribute("ordersVO", ordersVO); // 資料庫取出的empVO物件,存入req
//			String url = "/views/orders/oneOrders.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//			successView.forward(req, res);
//
//
//		}
//	}
//
//}
