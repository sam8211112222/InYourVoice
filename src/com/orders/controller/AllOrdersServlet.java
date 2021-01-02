//package com.orders.controller;
//
//import java.io.IOException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.orders.model.OrdersService;
//
//@WebServlet("/orders/allOrdersServlet")
//public class AllOrdersServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
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
//		
//		req.setCharacterEncoding("UTF-8");
//
//		req.setAttribute("orderList", service.getAll());
//
//		String url = "/views/orders/listAllOrders.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);
//		successView.forward(req, res);
//	}
//
//}
