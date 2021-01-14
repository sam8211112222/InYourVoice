package com.orderlist.controller;


import java.io.IOException;

import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;

import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.product.model.ProductService;


@WebServlet("/orderList/orderListServlet")
public class OrderListServlet extends HttpServlet {

	OrderListService olSvc = new OrderListService();
	OrdersService oSvc = new OrdersService();

//	OrdersDAO_interface orderDao = new OrdersJNDIDAO();
//	OrderListDAO_interface orderListDao = new OrderListJNDIDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String id = req.getParameter("id");
//		OrdersVO vo = orderDao.findByPrimaryKey(id);
//		List<OrderListVO> list = orderListDao.findByOrderId(id);

		String totalPrice = req.getParameter("totalPrice");
		Integer realTotalPrice = new Integer(totalPrice);
		OrdersVO order = oSvc.getOneOrders(id);
		ProductService productSvc = new ProductService();
		List<OrderListVO> list = olSvc.findByOrderId(id);
//		List<ProductVO> product_list = new ArrayList<ProductVO>();

//		for(int i = 0; i<list.size();i++) {
//			String product_id = list.get(i).getProduct_id();
//			product_list.add(produtSvc.getOneProduct(product_id));
//		}

		req.setAttribute("realTotalPrice", realTotalPrice);
		req.setAttribute("order", order);
		req.setAttribute("list", list);
//		req.setAttribute("productItem", product_list);
		req.setAttribute("productSvc", productSvc);
		String url = "/front-end/orders/protect/myOrdersDetail.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
		String action = req.getParameter("action");
		if ("reviewItem".equals(action)) {

			try {
				String reviewScoreText = req.getParameter("review_score");
				int review_score = 0;
				try {
					review_score = Integer.parseInt(reviewScoreText);
				} catch (Exception e) {
					e.printStackTrace();
				}
//			Integer review_score = new Integer(req.getParameter("review_score"));

				String review_msg = req.getParameter("review_msg");

				java.sql.Timestamp review_time = null;
//			 review_time = java.sql.Timestamp.valueOf(req.getParameter("review_time").trim());
				review_time = new java.sql.Timestamp(System.currentTimeMillis());

				String orderlist_id = req.getParameter("orderlist_id");

				OrderListVO orderListVO = new OrderListVO();
				orderListVO.setReview_score(review_score);
				orderListVO.setReview_msg(review_msg);
				orderListVO.setReview_time(review_time);
				orderListVO.setOrderlist_id(orderlist_id);

				/*************************** 2.開始修改資料 *****************************************/
				OrderListService olSvc = new OrderListService();
				orderListVO = olSvc.updeteReview(review_score, review_msg, review_time, orderlist_id);

				res.getWriter().write("true");
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().write("false");
			}
		}
		
		
		
		}
		
		
	}


