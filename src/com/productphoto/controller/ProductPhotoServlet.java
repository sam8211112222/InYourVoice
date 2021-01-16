package com.productphoto.controller;

import java.awt.MultipleGradientPaint.CycleMethod;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.productphoto.model.ProductPhotoService;
import com.productphoto.model.ProductPhotoVO;
import java.util.Base64;

@WebServlet("/productphoto/productphoto.do")
@MultipartConfig(fileSizeThreshold = 1024*1024,
				maxFileSize = 5*1024*1024,
				maxRequestSize = 5*5*1024*1024)
public class ProductPhotoServlet extends HttpServlet {
	
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
				String str = req.getParameter("productphoto_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入照片編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/productphoto/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String productphoto_id = null;
				try {
					productphoto_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("照片編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/productphoto/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ProductPhotoService producPhototSVC = new ProductPhotoService();
				ProductPhotoVO productPhotoVO = producPhototSVC.getOneProductPhoto(productphoto_id);
				if (productPhotoVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/productphoto/protect/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("productPhotoVO", productPhotoVO); // 資料庫取出的productPhotoVO物件,存入req
				String url = "/front-end/productphoto/protect/listOneProductPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/productphoto/protect/select_page.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Display測試成功");
		}		

		if ("getOne_For_Update".equals(action)) { // 來自listAllProductPhoto.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String productphoto_id = new String(req.getParameter("productphoto_id"));
				System.out.println(1);
				/*************************** 2.開始查詢資料 ****************************************/
				ProductPhotoService producPhototSVC = new ProductPhotoService();
				ProductPhotoVO productPhotoVO = producPhototSVC.getOneProductPhoto(productphoto_id);
				System.out.println(2);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("productPhotoVO", productPhotoVO); // 資料庫取出的productPhotoVO物件,存入req
				String url = "/front-end/productphoto/protect/update_productPhoto_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);
				System.out.println(3);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/productphoto/protect/listAllProductPhoto.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Update測試成功");
		}
		
//		if("changepic".equalsIgnoreCase(action)) {
//			System.out.println("ok");
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			byte[] productphoto_photo=null;
//			try {
//				ProductPhotoVO productPhotoVO = new ProductPhotoVO();
//				
//				
//				if (!req.getPart("productphoto_photo").getContentType().equals("application/octet-stream")) {
//					System.out.println("ok222");
//					productphoto_photo = transPicToBytes(req.getPart("productphoto_photo"));
//		
//				} else {
//					productphoto_photo = productPhotoVO.getProductphoto_photo();
//				}
//				System.out.println(productphoto_photo);
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("productPhotoVO", productPhotoVO); // 含有輸入格式錯誤的productVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front-end/productphoto/update_productPhoto_input.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始修改資料*****************************************/
//				ProductPhotoService producPhototSVC = new ProductPhotoService();
//				producPhototSVC.changeProductPhoto(productphoto_photo);
//						
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("productPhotoVO", productPhotoVO); // 資料庫update成功後,正確的的productVO物件,存入req
//				String url = "/front-end/productphoto/listAllProductPhoto.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				e.printStackTrace();
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/productphoto/addProductPhoto_input.jsp");
//				failureView.forward(req, res);
//			}
//			System.out.println("change測試成功");
//			}

		if ("update".equals(action)) { // 來自update_productPhoto_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String productphoto_id = new String(req.getParameter("productphoto_id").trim());
				System.out.println(productphoto_id);
				
				String product_id= new String(req.getParameter("product_id").trim());

				byte[] productphoto_photo=null;
				
				Integer productphoto_sort = null;
				try {
					productphoto_sort = new Integer(req.getParameter("productphoto_sort").trim());
				} catch (NumberFormatException e) {
					productphoto_sort = 0;
					errorMsgs.add("排序狀態請填數字");
				}
				
				java.sql.Timestamp productphoto_add_time = null;
				try {
					productphoto_add_time = java.sql.Timestamp.valueOf(req.getParameter("productphoto_add_time").trim());
				} catch (IllegalArgumentException e) {
					productphoto_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				ProductPhotoVO productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProductphoto_id(productphoto_id);
				productPhotoVO.setProduct_id(product_id);
				
				if (!req.getPart("productphoto_photo").getContentType().equals("application/octet-stream")) {
					productphoto_photo = transPicToBytes(req.getPart("productphoto_photo"));
					productPhotoVO.setProductphoto_photo(productphoto_photo);
				} else {
					productphoto_photo = productPhotoVO.getProductphoto_photo();
				}
				productPhotoVO.setProductphoto_sort(productphoto_sort);
				productPhotoVO.setProductphoto_add_time(productphoto_add_time);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productPhotoVO", productPhotoVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/productphoto/protect/update_productPhoto_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductPhotoService producPhototSVC = new ProductPhotoService();
				productPhotoVO = producPhototSVC.updateProductPhoto(productphoto_id, product_id, productphoto_photo, productphoto_sort, productphoto_add_time);
						
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productPhotoVO", productPhotoVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/front-end/productphoto/protect/listAllProductPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/productphoto/protect/update_productPhoto_input.jsp");
				failureView.forward(req, res);
			}
			System.out.println("update測試成功");
		}
		if ("insert".equals(action)) { // 來自addProductPhoto_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String product_id= new String(req.getParameter("product_id").trim());
//				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{4,9}$";
//				if (band_id == null || band_id.trim().length() == 0) {
//					errorMsgs.add("樂團編號: 請勿空白");
//				} else if(!band_id.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("樂團編號: 只能是中、英文字母、數字和_ , 且長度必需在4到9之間");
//	            }
				
				Integer productphoto_sort = null;
				try {
					productphoto_sort = new Integer(req.getParameter("productphoto_sort").trim());
				} catch (NumberFormatException e) {
					productphoto_sort = 0;
					errorMsgs.add("排序狀態請填數字");
				}
				
				java.sql.Timestamp productphoto_add_time = null;
				try {
					productphoto_add_time = java.sql.Timestamp.valueOf(req.getParameter("productphoto_add_time").trim());
				} catch (IllegalArgumentException e) {
					productphoto_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
			
				ProductPhotoVO productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProduct_id(product_id);
				byte[] productphoto_photo=null;
				if (!req.getPart("productphoto_photo").getContentType().equals("application/octet-stream")) {
					productphoto_photo = transPicToBytes(req.getPart("productphoto_photo"));
					productPhotoVO.setProductphoto_photo(productphoto_photo);
				} else {
					productphoto_photo = productPhotoVO.getProductphoto_photo();
				}
				
				productPhotoVO.setProductphoto_sort(productphoto_sort);
				productPhotoVO.setProductphoto_add_time(productphoto_add_time);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productPhotoVO", productPhotoVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/productphoto/protect/update_productPhoto_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				System.out.println("1");
				/***************************2.開始修改資料*****************************************/
				ProductPhotoService producPhototSVC = new ProductPhotoService();
				productPhotoVO = producPhototSVC.addProductPhoto(product_id, productphoto_photo, productphoto_sort, productphoto_add_time);
				System.out.println("2");
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productPhotoVO", productPhotoVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/front-end/productphoto/protect/listAllProductPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				System.out.println("3");
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/productphoto/protect/addProductPhoto_input.jsp");
				failureView.forward(req, res);
			}
			System.out.println("insert測試成功");
		}
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String productphoto_id = new String(req.getParameter("productphoto_id"));
				
				/***************************2.開始刪除資料***************************************/
				ProductPhotoService ProductPhotoSvc = new ProductPhotoService();
				ProductPhotoSvc.deleteProductPhoto(productphoto_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/productphoto/listAllProductPhoto.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/productphoto/listAllProductPhoto.jsp");
				failureView.forward(req, res);
			}
		}
		if("findFirst".equals(action)) {
			String productId = req.getParameter("productId");
			ProductPhotoService ppSvc = new ProductPhotoService();
			byte[] photo = ppSvc.findFirst(productId);
			res.setContentType("image/gif");
			ServletOutputStream sos =  res.getOutputStream();
			sos.write(photo);
			sos.close();
		}
	}
	
	
//=========================轉換照片資料的方法==========================	
	private byte[] transPicToBytes(Part part) {
		byte[] pic = null;
		try (InputStream in = part.getInputStream()) {
			pic = new byte[in.available()];
			in.read(pic);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return pic;
	}
}
