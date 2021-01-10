package com.product.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.SessionException;

import com.orderlist.model.OrderListDAO_interface;
import com.orderlist.model.OrderListJDBCDAO;
import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.product.model.ProductDAO_interface;
import com.product.model.ProductJDBCDAO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productphoto.model.ProductPhotoDAO_interface;
import com.productphoto.model.ProductPhotoJDBCDAO;
import com.productphoto.model.ProductPhotoService;
import com.productphoto.model.ProductPhotoVO;

//這是鈺涵的
@WebServlet("/product/YUproductServlet")
public class YUProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService proService;
	private ProductPhotoService productPhotoSvc;
	private OrderListService os_service;

	@Override
	public void init() throws ServletException {
		super.init();
		proService = new ProductService();
		productPhotoSvc = new ProductPhotoService();
		os_service = new OrderListService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//
//		String id = req.getParameter("id");
//		ProductVO productVO = productDAO.findByPrimaryKey(id);
//		List<ProductVO> list = productDAO.getAll();
//		OrderListVO orderListVO = orderListDAO.findByPrimaryKey(id);
//
//		req.setAttribute("orderList", orderListVO);
//		req.setAttribute("product", productVO);
//		req.setAttribute("productItem", list);
//
//		String url = "/front_end/product/productAll.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);
//		successView.forward(req, res);

		if (req.getParameter("action").equals("show_me_one")) {

			String id = req.getParameter("id");
			ProductVO productVO = proService.getOneProduct(id);
			List<OrderListVO> list = os_service.findByProductId(id);
			List<String> photoIdList = productPhotoSvc.getIdListByProductId(id);

			req.setAttribute("productVO", productVO);
			req.setAttribute("orderListVO", list);
			req.setAttribute("photoIdList", photoIdList);
			

			String url = "/front-end/product/band_productDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if (req.getParameter("action").equals("show_me_band")) {
			String band_id = req.getParameter("band_id");
			System.out.println(band_id);

			List<ProductVO> bandProduct = proService.getAllByBand(band_id);
			System.out.println(bandProduct.size());
			req.setAttribute("bandProduct", bandProduct);

			String url = "/front-end/product/bandID_product.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
	}

}
