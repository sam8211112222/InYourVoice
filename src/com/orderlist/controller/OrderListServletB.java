package com.orderlist.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orderlist.model.OrderListDAO_interface;
import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;
import com.orders.model.OrdersDAO_interface;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;

@WebServlet("/orderList/orderListServletB")
public class OrderListServletB extends HttpServlet {
	
	OrderListService olSvc = new OrderListService();
	OrdersService oSvc=new OrdersService();

//	OrdersDAO_interface orderDao = new OrdersJNDIDAO();
//	OrderListDAO_interface orderListDao = new OrderListJNDIDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String id = req.getParameter("id");
//		OrdersVO vo = orderDao.findByPrimaryKey(id);
//		List<OrderListVO> list = orderListDao.findByOrderId(id);
		
		OrdersVO vo = oSvc.getOneOrders(id);
		List<OrderListVO> list =olSvc.findByOrderIdB(id);

		req.setAttribute("order", vo);
		req.setAttribute("details", list);

		String url = "/back-end/product/protect/detail.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	
	}
}
