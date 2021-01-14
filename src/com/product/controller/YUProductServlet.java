package com.product.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
import com.member.model.MemberVo;
import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;
import com.orderlist.model.ReviewVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productphoto.model.ProductPhotoService;

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
	private FavoritesService favoritesService;
	public static final Integer PRODUCT_FAVORITE_TYPE = 2;

//	private Timer timer = null;

	@Override
	public void init() throws ServletException {
		super.init();
		proService = new ProductService();
		productPhotoSvc = new ProductPhotoService();
		os_service = new OrderListService();
		favoritesService = new FavoritesService();

//		ServletContext context = getServletContext();

//		timer = new Timer();

//		TimerTask task = new TimerTask() {
//
//			public void run() {
//
//				Map<String, Integer> reviewTotal = new ConcurrentHashMap<String, Integer>();
//
//				List<ProductVO> allProductList = proService.getAll();
//				for (ProductVO product : allProductList) {
//					String productId = product.getProduct_id();
//					List<ReviewVO> reviewList = os_service.getReviewListByProductId(productId);
//					Integer reviewScore = new Integer(0);
//					for (ReviewVO review : reviewList) {
//						reviewScore += review.getReview_score();
//					}
//					if (reviewList.size() > 0) {
//						reviewScore = reviewScore / reviewList.size();
//						reviewTotal.put(productId, reviewScore);
//
//					}
//				}
//
//				context.setAttribute("reviewTotal", reviewTotal);
//				System.out.println("reviewTotal:"+reviewTotal.size());
//			}
//		};
//
//		GregorianCalendar gc = new GregorianCalendar();
//		timer.scheduleAtFixedRate(task, gc.getTime(), 5 * 1000);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if (action == null || "".equals(action)) {

			List<ProductVO> productList = proService.findProductForList(null,null);

			req.setAttribute("productList", productList);
			req.setAttribute("os_service", os_service);

			String url = "/front-end/product/band_productAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("findByProductName".equals(action)) {
			String productName = req.getParameter("productName");
			req.setAttribute("productList", proService.findProductForList( productName ,null));
			String url = "/front-end/product/band_productAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("findByProductType".equals(action)) {
			String productType = req.getParameter("productType");
			req.setAttribute("productList", proService.findProductForList( null ,productType));
			String url = "/front-end/product/band_productAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		

		if ("show_me_one".equals(action)) {

			String id = req.getParameter("id");
			ProductVO productVO = proService.getOneProduct(id);
			List<OrderListVO> list = os_service.findByProductId(id);
			List<String> photoIdList = productPhotoSvc.getIdListByProductId(id);
			List<ReviewVO> reviewList = os_service.getReviewListByProductId(id);
			HttpSession session = req.getSession();
			FavoritesVO favoritesVO = null;
			if (session.getAttribute("memberVo") != null) {
				MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
				String member_id = loginMember.getMemberId();
				favoritesVO = favoritesService.findByMemberIdAndProductId(member_id, productVO.getProduct_id());
			}
			req.setAttribute("productVO", productVO);
			req.setAttribute("orderListVO", list);
			req.setAttribute("photoIdList", photoIdList);
			req.setAttribute("reviewList", reviewList);
			req.setAttribute("favoritesVO", favoritesVO);

			String url = "/front-end/product/band_productDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("show_me_band".equals(action)) {
			String band_id = req.getParameter("band_id");
			

			List<ProductVO> bandProduct = proService.getAllByBand(band_id);
			
			req.setAttribute("bandProduct", bandProduct);
			req.setAttribute("os_service", os_service);
			

			String url = "/front-end/product/bandID_product.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
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

		if ("show_me_one".equals(action)) {

			String id = req.getParameter("id");
			ProductVO productVO = proService.getOneProduct(id);
			List<OrderListVO> list = os_service.findByProductId(id);
			List<String> photoIdList = productPhotoSvc.getIdListByProductId(id);

			HttpSession session = req.getSession();
			FavoritesVO favoritesVO = null;
			if (session.getAttribute("memberVo") != null) {
				System.out.println("hi1");
				MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
				String member_id = loginMember.getMemberId();
				favoritesVO = favoritesService.findByMemberIdAndProductId(member_id, productVO.getProduct_id());
				System.out.println("hi2:" + favoritesVO);
			}
			req.setAttribute("productVO", productVO);
			req.setAttribute("orderListVO", list);
			req.setAttribute("photoIdList", photoIdList);
			req.setAttribute("favoritesVO", favoritesVO);

			String url = "/front-end/product/band_productDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		} else if ("show_me_band".equals(action)) {
			String band_id = req.getParameter("band_id");

			List<ProductVO> bandProduct = proService.getAllByBand(band_id);
			req.setAttribute("bandProduct", bandProduct);

			String url = "/front-end/product/bandID_product.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		} else if ("addFavorite".equals(action)) {
			try {
				HttpSession session = req.getSession();
				if (session.getAttribute("memberVo") != null) {
					MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
					String member_id = loginMember.getMemberId();
					favoritesService.addFavorites(member_id, PRODUCT_FAVORITE_TYPE, req.getParameter("productId"),
							new Timestamp(System.currentTimeMillis()));

					res.getWriter().write("true");
				} else {
					res.getWriter().write("you need to login first");
				}
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().write("false");
			}
			return;
		} else if ("removeFavorite".equals(action)) {
			try {
				HttpSession session = req.getSession();
				if (session.getAttribute("memberVo") != null) {
					MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
					String member_id = loginMember.getMemberId();
					favoritesService.deleteByMemberIdAndProductId(member_id, req.getParameter("productId"));

					res.getWriter().write("true");
				} else {
					res.getWriter().write("請先登入會員");
				}
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().write("false");
			}
			return;
		}

	}

}
