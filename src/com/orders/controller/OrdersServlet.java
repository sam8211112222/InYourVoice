package com.orders.controller;

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
import javax.servlet.http.HttpSession;

import com.cart.controller.CartServlet;
import com.cart.model.CartVO;
import com.member.model.MemberVo;
import com.orderlist.model.OrderListService;
import com.orderlist.model.OrderListVO;
import com.orders.enums.OrderStatus;
import com.orders.model.OrdersService;
import com.orders.model.OrdersVO;
import com.product.model.ProductService;
//import static com.utils.DataSourceUtils.TEST;

@WebServlet("/orders/ordersServlet")
public class OrdersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrdersService ordersService;
	private OrderListService orderListService;
	private ProductService productService;

	public static final String UPDATE_VO = "for_update";

	@Override
	public void init() throws ServletException {
		super.init();

		ordersService = new OrdersService();
		orderListService = new OrderListService();
		productService = new ProductService();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//String x = TEST;
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

//		String url = "/front_end/orders/select_page.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);
//		successView.forward(req, res);

		// 查詢
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String order_mail = req.getParameter("order_mail");

				if (order_mail == null || (order_mail.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號(email):");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/

				OrdersService ordersSvc = new OrdersService();
				List<OrdersVO> orderMail = ordersSvc.findByEmail(order_mail);

				if (orderMail == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("orderMail", orderMail); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/orders/selectByMail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/select_page.jsp");
				failureView.forward(req, res);
			}
		}
//==============================================================================================================

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			OrdersVO ordersVO = null;
			try {
				String member_id = req.getParameter("member_id");
				String member_idReg = "^[(A-Z0-9)]{11}$";
				if (member_id == null || member_id.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				} else if (!member_id.trim().matches(member_idReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號: 只能是英文字母、數字 , 且長度必需共11碼");
				}

				Integer order_status = new Integer(req.getParameter("order_status"));

				java.sql.Timestamp order_place_time = null;
				try {
					order_place_time = java.sql.Timestamp.valueOf(req.getParameter("order_place_time").trim());
				} catch (Exception e) {
					order_place_time = new java.sql.Timestamp(System.currentTimeMillis());
				}

				String order_name = req.getParameter("order_name");
				if (order_name == null || order_name.trim().length() == 0) {
					errorMsgs.add("買家姓名: 請勿空白");
				}

				String order_mail = req.getParameter("order_mail");
				if (order_mail == null || order_mail.trim().length() == 0) {
					errorMsgs.add("訂購者email: 請勿空白");
				}

				String order_phone = req.getParameter("order_phone");
				String order_phoneReg = "^[(0-9)]{10}$";
				if (order_phone == null || order_phone.trim().length() == 0) {
					errorMsgs.add("訂購者電話: 請勿空白");
				} else if (!order_phone.trim().matches(order_phoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂購者電話: 數字且長度是10碼");
				}

				java.sql.Timestamp order_delivery_time = null;
				try {
					order_delivery_time = java.sql.Timestamp.valueOf(req.getParameter("order_delivery_time").trim());
				} catch (Exception e) {
					order_delivery_time = new java.sql.Timestamp(System.currentTimeMillis());
				}

				java.sql.Timestamp order_received_time = null;
				try {
					order_received_time = java.sql.Timestamp.valueOf(req.getParameter("order_received_time").trim());
				} catch (Exception e) {
					order_received_time = new java.sql.Timestamp(System.currentTimeMillis());
				}

				ordersVO = new OrdersVO();
				ordersVO.setMember_id(member_id);
				ordersVO.setOrder_status(order_status);
				ordersVO.setOrder_place_time(order_place_time);
				ordersVO.setOrder_name(order_name);
				ordersVO.setOrder_mail(order_mail);
				ordersVO.setOrder_phone(order_phone);
				ordersVO.setOrder_delivery_time(order_delivery_time);
				ordersVO.setOrder_received_time(order_received_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordersVO", ordersVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/addOrders.jsp");
					failureView.forward(req, res);
					return;
				}

//			RequestDispatcher failureView = req.getRequestDispatcher("/orders/ordersServlet");
//			failureView.forward(req, res);
				/*********************** 新增資料 ***********************/
				ordersVO = ordersService.addOrders(member_id, order_status, order_place_time, order_name, order_mail,
						order_phone, order_delivery_time, order_received_time);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//			res.sendRedirect(req.getContextPath() +"/orders/allOrdersServlet" );
//			req.setAttribute("orderList", service.getAll());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/listAllOrders.jsp");
				failureView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("新增沒有成功" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/addOrders.jsp");

				failureView.forward(req, res);
			}
		}
// ===================================================================
		// 刪除
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			String delete = req.getParameter("delete");
			try {

				/*************************** 2.開始刪除資料 ***************************************/
				OrdersService ordersSvc = new OrdersService();
				ordersSvc.deleteOrders(delete);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front_end/orders/listAllOrders.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/listAllOrders.jsp");

				failureView.forward(req, res);
			}
		}
//=============================================================================	

		if ("getOne_For_Update".equals(action)) { // 來自listAllOrders.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String order_id = req.getParameter("order_id");
				System.out.print("order_id:" + order_id);

				/*************************** 2.開始查詢資料 ****************************************/
				OrdersService ordersSvc = new OrdersService();
				OrdersVO for_update = ordersSvc.getOneOrders(order_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute(UPDATE_VO, for_update); // 資料庫取出的empVO物件,存入req
				String url = "/front_end/orders/update_orders.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/select_page.jsp");
				failureView.forward(req, res);
			}

		}

//===================================================================		

		if ("update".equals(action)) { // 來自front_end/orders/update_orders.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String order_id = req.getParameter("order_id");

				String member_id = req.getParameter("member_id");

				Integer order_status = new Integer(req.getParameter("order_status").trim());

				java.sql.Timestamp order_place_time = java.sql.Timestamp
						.valueOf(req.getParameter("order_place_time").trim());

				String order_name = req.getParameter("order_name");
//				String order_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (order_name == null || order_name.trim().length() == 0) {
					errorMsgs.add("訂購者姓名: 請勿空白");
				}
//				else if(!ename.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//	            }

				String order_mail = req.getParameter("order_mail");
				if (order_mail == null || order_mail.trim().length() == 0) {
					errorMsgs.add("訂購者email: 請勿空白");
				}

				String order_phone = req.getParameter("order_phone");
				String order_phoneReg = "^[(0-9)]{10}$";
				if (order_phone == null || order_phone.trim().length() == 0) {
					errorMsgs.add("訂購者電話: 請勿空白");
				} else if (!order_phone.trim().matches(order_phoneReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("訂購者電話: 數字且長度是10碼");
				}

				java.sql.Timestamp order_delivery_time = null;
				try {
					order_delivery_time = java.sql.Timestamp.valueOf(req.getParameter("order_delivery_time").trim());
				} catch (IllegalArgumentException e) {
					order_delivery_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				java.sql.Timestamp order_received_time = null;
				try {
					order_received_time = java.sql.Timestamp.valueOf(req.getParameter("order_received_time").trim());
				} catch (IllegalArgumentException e) {
					order_received_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				OrdersVO ordersVO = new OrdersVO();
				ordersVO.setOrder_id(order_id);
				ordersVO.setMember_id(member_id);
				ordersVO.setOrder_status(order_status);
				ordersVO.setOrder_place_time(order_place_time);
				ordersVO.setOrder_name(order_name);
				ordersVO.setOrder_mail(order_mail);
				ordersVO.setOrder_phone(order_phone);
				ordersVO.setOrder_delivery_time(order_delivery_time);
				ordersVO.setOrder_received_time(order_received_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute(UPDATE_VO, ordersVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/update_orders.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				OrdersService ordersSvc = new OrdersService();
				ordersVO = ordersSvc.updateOrders(member_id, order_status, order_place_time, order_name, order_mail,
						order_phone, order_delivery_time, order_received_time, order_id);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute(UPDATE_VO, ordersVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front_end/orders/listAllOrders.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front_end/orders/update_orders.jsp");
//			req.setAttribute(UPDATE_VO, ordersVO);
				failureView.forward(req, res);
			}
		}

		if ("checkout".equals(action)) {
			System.out.println("checkout");
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
				
//				if (memberVo == null) {
//					// TODO 暫時的
//					memberVo = new MemberVo();
//
//					memberVo.setMemberName("Sophia");
//					memberVo.setMemberAccount("S0001");
//					memberVo.setMemberAddress("台北市中山區xx路xx號");
//					memberVo.setMemberId("MEMBERS00000");
//
//				}

			String memberId = memberVo.getMemberId();

				Timestamp order_place_time = new Timestamp(System.currentTimeMillis());

				String orderName = req.getParameter("orderName");
				String orderMail = req.getParameter("orderMail");
				String orderPhone = req.getParameter("orderPhone");

				// 新增訂單
				OrdersVO vo = ordersService.addOrders(memberId, OrderStatus.PROCESSING.getStatus(), order_place_time,
						orderName, orderMail, orderPhone, null, null);
				// 新增訂單明細

				List<CartVO> cartList = (List<CartVO>) session.getAttribute(CartServlet.SESSION_CART_KEY);

				List<OrderListVO> detailList = orderListService.addList(cartList, vo.getOrder_id(),
						req.getParameter("remarks"));

				int totalPrice = 0;
				if (detailList != null) {
					for (OrderListVO tmp : detailList) {
						totalPrice += tmp.getPrice() * tmp.getOrderlist_goods_amount();
					}
				}
				// 更新商品庫存
				for (CartVO cartVo : cartList) {
					productService.updateStock(cartVo.getProduct_id(), -cartVo.getProduct_quantity());
				}

				// 購物完成，移除session中購物車資料
				session.removeAttribute(CartServlet.SESSION_CART_KEY);
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/cart/protect/orderSuccess.jsp");
				req.setAttribute("totalPrice", totalPrice);
				req.setAttribute("order_place_time", order_place_time);
				req.setAttribute("orderId", vo.getOrder_id());
				successView.forward(req, res);
			} catch (Exception e) {
				// TODO 顯示失敗的結果
				e.printStackTrace();

			}

		}
	}
}