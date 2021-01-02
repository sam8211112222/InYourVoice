//package com.product.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.product.model.ProductService;
//import com.product.model.ProductVO;
//import com.productphoto.model.ProductPhotoService;
//這是鈺涵的
//@WebServlet("/ProductPicController")
//public class ProductPicController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//	}
//
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		
//		req.setCharacterEncoding("UTF-8");
//		
//		ServletOutputStream sos =  res.getOutputStream();
//		
//		String product_id = (String) req.getParameter("product_id");
//		Integer product_photo = new Integer(req.getParameter("product_sort"));
//		ProductPhotoService productPhotoSvc = new ProductPhotoService();
//		List<ProductVO> productVO =  productPhotoSvc.getProductPhotos(product_id);
//		
//		
//		
//		
//		
//
//	}
//
//}
