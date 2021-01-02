package com.orderlist.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.orderlist.model.OrderListJNDIDAO;
import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;
import com.orders.model.OrdersDAO_interface;
import com.orders.model.OrdersJNDIDAO;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;

@WebServlet("/orderList/orderListServlet")
public class OrderListServlet extends HttpServlet {
	
	OrderListService olSvc = new OrderListService();
	OrdersService oSvc=new OrdersService();

//	OrdersDAO_interface orderDao = new OrdersJNDIDAO();
//	OrderListDAO_interface orderListDao = new OrderListJNDIDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String id = req.getParameter("id");
//		OrdersVO vo = orderDao.findByPrimaryKey(id);
//		List<OrderListVO> list = orderListDao.findByOrderId(id);
		
		OrdersVO order = oSvc.getOneOrders(id);
		ProductService productSvc = new ProductService();
		List<OrderListVO> list =olSvc.findByOrderId(id);
//		List<ProductVO> product_list = new ArrayList<ProductVO>();
		
//		for(int i = 0; i<list.size();i++) {
//			String product_id = list.get(i).getProduct_id();
//			product_list.add(produtSvc.getOneProduct(product_id));
//		}
		
		req.setAttribute("order", order);
		req.setAttribute("list", list);
//		req.setAttribute("productItem", product_list);
		req.setAttribute("productSvc", productSvc);
		String url = "/front-end/orders/myOrdersDetail.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	
	}
}
