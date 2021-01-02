//package com.orders.controller;
//
//import java.io.IOException;
//import java.sql.Timestamp;
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
//@WebServlet("/orders/NewOrdersServlet")
//public class NewOrdersServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
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
////		req.setCharacterEncoding("UTF-8");
//		doPost(req, res);
//		
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		
//		String url = "/views/orders/addOrders.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);
//		successView.forward(req, res);
//
//		String action = req.getParameter("action");
//		
//		if ("insert".equals(action)) {
//			String member_id = req.getParameter("member_id");
//			
//			Integer order_status = new Integer(req.getParameter("order_status"));
//			
//			java.sql.Timestamp order_place_time = null;
//			try {
//				order_place_time = java.sql.Timestamp.valueOf(req.getParameter("order_place_time").trim());
//			} catch (Exception e) {
//				order_place_time = new java.sql.Timestamp(System.currentTimeMillis());
//			}
//			String order_name = req.getParameter("order_name");
//			String order_mail = req.getParameter("order_mail");
//			String order_phone = req.getParameter("order_phone");
//			java.sql.Timestamp order_delivery_time = null;
//			try {
//				order_delivery_time = java.sql.Timestamp.valueOf(req.getParameter("order_delivery_time").trim());
//			} catch (Exception e) {
//				order_delivery_time = new java.sql.Timestamp(System.currentTimeMillis());
//			}
//
//			java.sql.Timestamp order_received_time = null;
//			try {
//				order_received_time = java.sql.Timestamp.valueOf(req.getParameter("order_received_time").trim());
//			} catch (Exception e) {
//				order_received_time = new java.sql.Timestamp(System.currentTimeMillis());
//			}
//
//			OrdersVO ordersVO = new OrdersVO();
//			ordersVO.setMember_id(member_id);
//			ordersVO.setOrder_status(order_status);
//			ordersVO.setOrder_place_time(order_place_time);
//			ordersVO.setOrder_name(order_name);
//			ordersVO.setOrder_mail(order_mail);
//			ordersVO.setOrder_phone(order_phone);
//			ordersVO.setOrder_delivery_time(order_delivery_time);
//			ordersVO.setOrder_received_time(order_received_time);
//
//			req.setAttribute("ordersVO", ordersVO);
////			RequestDispatcher failureView = req.getRequestDispatcher("/orders/ordersServlet");
////			failureView.forward(req, res);
//			/*********************** 新增資料 ***********************/
//			ordersVO = service.addOrders(member_id, order_status, order_place_time, order_name, order_mail, order_phone,
//					order_delivery_time, order_received_time);
//			
//			/***************************3.新增完成,準備轉交(Send the Success view)***********/
//			res.sendRedirect(req.getContextPath() +"/orders/ordersServlet" );	
//		}
//
//	}
//
//}
