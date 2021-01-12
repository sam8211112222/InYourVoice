package com.product.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.product.model.*;

@WebServlet("/product/product.do")
public class ProductServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs); 

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("product_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String product_id = null;
				try {
					product_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("產品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ProductService productSVC = new ProductService();
				ProductVO productVO = productSVC.getOneProduct(product_id);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/front-end/product/protect/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/product/protect/select_page.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Display測試成功");
		}
		
		if ("getone_For_Display_Management".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs); 
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("product_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				String product_id = null;
				try {
					product_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("產品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 2.開始查詢資料 *****************************************/
				ProductService productSVC = new ProductService();
				ProductVO productVO = productSVC.getOneProduct(product_id);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/back-end/product/protect/listOneProductManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/protect/select_page.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Display_Management測試成功");
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String product_id = new String(req.getParameter("product_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				ProductService productSVC = new ProductService();
				ProductVO productVO = productSVC.getOneProduct(product_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/front-end/product/protect/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/product/protect/listAllProduct.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Update測試成功");
		}
		if ("getOne_For_Update_Management".equals(action)) { // 來自listAllEmp.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String product_id = new String(req.getParameter("product_id"));
				
				/*************************** 2.開始查詢資料 ****************************************/
				ProductService productSVC = new ProductService();
				ProductVO productVO = productSVC.getOneProduct(product_id);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/back-end/product/protect/update_product_management.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/protect/listAllProductManagement.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Update_M測試成功");
		}
		
		if ("update".equals(action)) { // 來自update_product_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String product_id = new String(req.getParameter("product_id").trim());
				
				String band_id= new String(req.getParameter("band_id").trim());
				
				Integer product_type = null;		
				product_type = new Integer(req.getParameter("product_type").trim());
					
				String product_name = req.getParameter("product_name").trim();
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱請勿空白");
				}	
				String product_intro = req.getParameter("product_intro").trim();
				if (product_intro == null || product_intro.trim().length() == 0) {
					errorMsgs.add("商品簡介請勿空白");
				}	
				String product_detail = req.getParameter("product_detail").trim();
				if (product_detail == null || product_detail.trim().length() == 0) {
					errorMsgs.add("商品詳細說明請勿空白");
				}
				Double product_price = null;
				
				product_price = new Double(req.getParameter("product_price").trim());
				
				Integer product_stock = null;
				try {
					product_stock = new Integer(req.getParameter("product_stock").trim());
				} catch (NumberFormatException e) {
					product_stock = 0;
					errorMsgs.add("商品庫存量請填數字.");
				}
				Integer product_check_status = null;
				product_check_status = new Integer(req.getParameter("product_check_status").trim());
				
				Integer product_status = null;
				product_status = new Integer(req.getParameter("product_status").trim());
				
				
				java.sql.Timestamp product_on_time = null;
				try {
					product_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_off_time = null;
				try {
					product_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_add_time = null;
				try {
					product_add_time = java.sql.Timestamp.valueOf(req.getParameter("product_add_time").trim());
				} catch (IllegalArgumentException e) {
					product_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Double product_discount = null;
				try {
					product_discount = new Double(req.getParameter("product_discount").trim());
				} catch (NumberFormatException e) {
					product_discount = 0.0;
					errorMsgs.add("折扣請填數字.");
				}

				java.sql.Timestamp product_discount_on_time = null;
				try {
					product_discount_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_discount_off_time = null;
				try {
					product_discount_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_last_edit_time = null;
				try {
					product_last_edit_time = java.sql.Timestamp.valueOf(req.getParameter("product_last_edit_time").trim());
				} catch (IllegalArgumentException e) {
					product_last_edit_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String product_last_editor = new String(req.getParameter("product_last_editor").trim());

				ProductVO productVO = new ProductVO();
				productVO.setProduct_id(product_id);
				productVO.setBand_id(band_id);
				productVO.setProduct_type(product_type);
				productVO.setProduct_name(product_name);
				productVO.setProduct_intro(product_intro);
				productVO.setProduct_detail(product_detail);
				productVO.setProduct_price(product_price);
				productVO.setProduct_stock(product_stock);
				productVO.setProduct_check_status(product_check_status);
				productVO.setProduct_status(product_status);
				productVO.setProduct_on_time(product_on_time);
				productVO.setProduct_off_time(product_off_time);
				productVO.setProduct_add_time(product_add_time);
				productVO.setProduct_discount(product_discount);
				productVO.setProduct_discount_on_time(product_discount_on_time);
				productVO.setProduct_discount_off_time(product_discount_off_time);
				productVO.setProduct_last_edit_time(product_last_edit_time);
				productVO.setProduct_last_editor(product_last_editor);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/protect/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.updateProduct(product_id, band_id, product_type, product_name, product_intro, product_detail, product_price, product_stock, product_check_status, product_status, product_on_time, product_off_time, product_add_time, product_discount, product_discount_on_time, product_discount_off_time, product_last_edit_time, product_last_editor);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/front-end/product/protect/listAllProductEnd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/protect/update_product_input.jsp");
				failureView.forward(req, res);
			}
			System.out.println("update測試成功");
		}
		if ("insert".equals(action)) { // 來自addproduct.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String band_id= new String(req.getParameter("band_id").trim());
				String enameReg = "^(BAND)(\\d){5}$";
				if (band_id == null || band_id.trim().length() == 0) {
					errorMsgs.add("樂團編號: 請勿空白");
				} else if(!band_id.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("樂團編號: BAND與數字和, 且長度必需到9");
	            }
				
				Integer product_type = null;
				try {
					product_type = new Integer(req.getParameter("product_type").trim());
				} catch (NumberFormatException e) {
					product_type = 0;
					errorMsgs.add("商品分類請填數字.");
				}	
				String product_name = req.getParameter("product_name").trim();
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱請勿空白");
				}	
				String product_intro = req.getParameter("product_intro").trim();
				if (product_intro == null || product_intro.trim().length() == 0) {
					errorMsgs.add("商品簡介請勿空白");
				}	
				String product_detail = req.getParameter("product_detail").trim();
				if (product_detail == null || product_detail.trim().length() == 0) {
					errorMsgs.add("商品詳細說明請勿空白");
				}
				Double product_price = null;
				try {
					product_price = new Double(req.getParameter("product_price").trim());
				} catch (NumberFormatException e) {
					product_price = 0.0;
					errorMsgs.add("商品單價請填數字.");
				}
				Integer product_stock = null;
				try {
					product_stock = new Integer(req.getParameter("product_stock").trim());
				} catch (NumberFormatException e) {
					product_stock = 0;
					errorMsgs.add("商品庫存量請填數字.");
				}
				Integer product_check_status = null;
				product_check_status = new Integer(req.getParameter("product_check_status").trim());
				Integer product_status = null;		
				product_status = new Integer(req.getParameter("product_status").trim());
				
				java.sql.Timestamp product_on_time = null;
				try {
					product_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_off_time = null;
				try {
					product_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_add_time = null;
				try {
					product_add_time = java.sql.Timestamp.valueOf(req.getParameter("product_add_time").trim());
				} catch (IllegalArgumentException e) {
					product_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Double product_discount = null;
				try {
					product_discount = new Double(req.getParameter("product_discount").trim());
				} catch (NumberFormatException e) {
					product_discount = 0.0;
					errorMsgs.add("折扣請填數字.");
				}
				
				java.sql.Timestamp product_discount_on_time = null;
				try {
					product_discount_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_discount_off_time = null;
				try {
					product_discount_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_last_edit_time = null;
				try {
					product_last_edit_time = java.sql.Timestamp.valueOf(req.getParameter("product_last_edit_time").trim());
				} catch (IllegalArgumentException e) {
					product_last_edit_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String product_last_editor = new String(req.getParameter("product_last_editor").trim());
				
				ProductVO productVO = new ProductVO();
				productVO.setBand_id(band_id);
				productVO.setProduct_type(product_type);
				productVO.setProduct_name(product_name);
				productVO.setProduct_intro(product_intro);
				productVO.setProduct_detail(product_detail);
				productVO.setProduct_price(product_price);
				productVO.setProduct_stock(product_stock);
				productVO.setProduct_check_status(product_check_status);
				productVO.setProduct_status(product_status);
				productVO.setProduct_on_time(product_on_time);
				productVO.setProduct_off_time(product_off_time);
				productVO.setProduct_add_time(product_add_time);
				productVO.setProduct_discount(product_discount);
				productVO.setProduct_discount_on_time(product_discount_on_time);
				productVO.setProduct_discount_off_time(product_discount_off_time);
				productVO.setProduct_last_edit_time(product_last_edit_time);
				productVO.setProduct_last_editor(product_last_editor);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/protect/addProduct.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.addProduct(band_id, product_type, product_name, product_intro, product_detail, product_price, product_stock, product_check_status, product_status, product_on_time, product_off_time, product_add_time, product_discount, product_discount_on_time, product_discount_off_time, product_last_edit_time, product_last_editor);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/front-end/product/protect/listAllProductEnd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllProductEnd.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/protect/addProduct.jsp");
				failureView.forward(req, res);
			}
			System.out.println("insert測試成功");
		}
		if ("update_Management".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String product_id = new String(req.getParameter("product_id").trim());
				
				String band_id= new String(req.getParameter("band_id").trim());
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,9}$";
//				if (band_id == null || band_id.trim().length() == 0) {
//					errorMsgs.add("樂團編號: 請勿空白");
//				} else if(!band_id.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("樂團編號: 只能是中、英文字母、數字和_ , 且長度必需在4到9之間");
//	            }
				
				Integer product_type = null;
				try {
					product_type = new Integer(req.getParameter("product_type").trim());
				} catch (NumberFormatException e) {
					product_type = 0;
					errorMsgs.add("商品分類請填數字.");
				}	
				String product_name = req.getParameter("product_name").trim();
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱請勿空白");
				}	
				String product_intro = req.getParameter("product_intro").trim();
				if (product_intro == null || product_intro.trim().length() == 0) {
					errorMsgs.add("商品簡介請勿空白");
				}	
				String product_detail = req.getParameter("product_detail").trim();
				if (product_detail == null || product_detail.trim().length() == 0) {
					errorMsgs.add("商品詳細說明請勿空白");
				}
				Double product_price = null;
				try {
					product_price = new Double(req.getParameter("product_price").trim());
				} catch (NumberFormatException e) {
					product_price = 0.0;
					errorMsgs.add("商品單價請填數字.");
				}
				Integer product_stock = null;
				try {
					product_stock = new Integer(req.getParameter("product_stock").trim());
				} catch (NumberFormatException e) {
					product_stock = 0;
					errorMsgs.add("商品庫存量請填數字.");
				}
				Integer product_check_status = null;
				
				Integer product_status = null;
				
				
				java.sql.Timestamp product_on_time = null;
				try {
					product_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_off_time = null;
				try {
					product_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_add_time = null;
				try {
					product_add_time = java.sql.Timestamp.valueOf(req.getParameter("product_add_time").trim());
				} catch (IllegalArgumentException e) {
					product_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Double product_discount = null;
				try {
					product_discount = new Double(req.getParameter("product_discount").trim());
				} catch (NumberFormatException e) {
					product_discount = 0.0;
					errorMsgs.add("折扣請填數字.");
				}
				
				java.sql.Timestamp product_discount_on_time = null;
				try {
					product_discount_on_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_on_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_on_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_discount_off_time = null;
				try {
					product_discount_off_time = java.sql.Timestamp.valueOf(req.getParameter("product_discount_off_time").trim());
				} catch (IllegalArgumentException e) {
					product_discount_off_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				java.sql.Timestamp product_last_edit_time = null;
				try {
					product_last_edit_time = java.sql.Timestamp.valueOf(req.getParameter("product_last_edit_time").trim());
				} catch (IllegalArgumentException e) {
					product_last_edit_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String product_last_editor = new String(req.getParameter("product_last_editor").trim());
				
				ProductVO productVO = new ProductVO();
				productVO.setProduct_id(product_id);
				productVO.setBand_id(band_id);
				productVO.setProduct_type(product_type);
				productVO.setProduct_name(product_name);
				productVO.setProduct_intro(product_intro);
				productVO.setProduct_detail(product_detail);
				productVO.setProduct_price(product_price);
				productVO.setProduct_stock(product_stock);
				productVO.setProduct_check_status(product_check_status);
				productVO.setProduct_status(product_status);
				productVO.setProduct_on_time(product_on_time);
				productVO.setProduct_off_time(product_off_time);
				productVO.setProduct_add_time(product_add_time);
				productVO.setProduct_discount(product_discount);
				productVO.setProduct_discount_on_time(product_discount_on_time);
				productVO.setProduct_discount_off_time(product_discount_off_time);
				productVO.setProduct_last_edit_time(product_last_edit_time);
				productVO.setProduct_last_editor(product_last_editor);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/protect/listOneProductManagement.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.updateProduct(product_id, band_id, product_type, product_name, product_intro, product_detail, product_price, product_stock, product_check_status, product_status, product_on_time, product_off_time, product_add_time, product_discount, product_discount_on_time, product_discount_off_time, product_last_edit_time, product_last_editor);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/back-end/product/protect/listAllProductFinal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/protect/update_product_management.jsp");
				failureView.forward(req, res);
			}
			System.out.println("update_M測試成功");
		}
		
		if ("launch".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String product_id = new String(req.getParameter("product_id").trim());
				
				ProductVO productVO = new ProductVO();
				productVO.setProduct_id(product_id);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/protect/listOneProductManagement.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.launchProduct(product_id);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
//				String url = "/back-end/product/protect/listAllProductFinal.jsp";
				String url = "/back-end/product/protect/listAllProductManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/protect/listOneProductManagement.jsp");
				failureView.forward(req, res);
			}
			System.out.println("launch測試成功");
		}
		
		if ("dislaunch".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String product_id = new String(req.getParameter("product_id").trim());
				
				ProductVO productVO = new ProductVO();
				productVO.setProduct_id(product_id);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/protect/listOneProductManagement.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.dislaunchProduct(product_id);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
//				String url = "/back-end/product/protect/listAllProductFinal.jsp";
				String url = "/back-end/product/protect/listAllProductManagement.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/protect/listOneProductManagement.jsp");
				failureView.forward(req, res);
			}
			System.out.println("dislaunch測試成功");
		}
		
		if ("approval".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String product_id = new String(req.getParameter("product_id").trim());
				
				ProductVO productVO = new ProductVO();
				productVO.setProduct_id(product_id);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/protect/listAllProductUnapproval.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService ProductSvc = new ProductService();
				productVO = ProductSvc.approvalProduct(product_id);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/back-end/product/protect/listAllProductUnapproval.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/protect/listAllProductUnapproval.jsp");
				failureView.forward(req, res);
			}
			System.out.println("approval測試成功");
		}
		
		//冠華
		//這是新增的搜尋方法
		if ("searchName".equals(action)) {

			String name = req.getParameter("search");

			req.getSession().setAttribute("name", name);
			res.sendRedirect(req.getContextPath() + "/front-end/query/query_product.jsp");
		}

	}
}