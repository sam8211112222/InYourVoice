package com.emp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;

@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		if("login".equals(action)) {
			String empEmail = req.getParameter("empEmail");
			String empPassword = req.getParameter("empPassword");
			Map<String,String> errors = new HashMap<String,String>();
			req.setAttribute("errors", errors);
			if(empEmail==null || empEmail.length()==0) {
				errors.put("username", "請輸入帳號");
			}
			if(empPassword==null || empPassword.length()==0) {
				errors.put("password", "請輸入密碼");
			}
			if(errors!=null && !errors.isEmpty()) {
				req.getRequestDispatcher(
						"/back-end/emp/LoginBackEnd.jsp").forward(req, res);
				return;
			}
			EmpService empSvc = new EmpService();
			EmpVO empVO = empSvc.login(empEmail, empPassword);
			if(empVO == null) {
				errors.put("password", "登入失敗 請檢查帳號密碼");
				req.getRequestDispatcher(
						"/back-end/emp/LoginBackEnd.jsp").forward(req, res);
			} else {
				HttpSession session = req.getSession();
				session.setAttribute("empVO", empVO);
				
				String path = req.getContextPath();
				res.sendRedirect(path+"/back-end/member/memberList.jsp");
			}
		
		}
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("emp_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}

				String emp_id = null;
				try {
					emp_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/home.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/home.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String emp_id = new String(req.getParameter("emp_id"));
				
				/***************************2.�}�l�d�߸��****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_id);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("empVO", empVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/emp/update_emp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String emp_id = new String(req.getParameter("emp_id").trim());
				
				String emp_password = req.getParameter("emp_password").trim();
				
				java.sql.Timestamp emp_add_time = null;
				try {
					emp_add_time = new java.sql.Timestamp(java.sql.Date.valueOf(req.getParameter("emp_add_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					emp_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String emp_mail = req.getParameter("emp_mail");
				String emp_mailReg = "^[a-z0-9]+([.][_a-z0-9-]+)*@[a-z0-9]+([.][_a-z0-9-]+)*$";
				if (emp_mail == null || emp_mail.trim().length() == 0) {
					errorMsgs.add("信箱: 請勿空白");
				} else if(!emp_mail.trim().matches(emp_mailReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("信箱格式不符");
	            }
				
				String emp_phone = req.getParameter("emp_phone");
				String emp_phoneReg = "^09[0-9]{8}$";
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!emp_phone.trim().matches(emp_phoneReg)) { 
					errorMsgs.add("手機號碼格式不符");
	            }
				
				Integer emp_status = new Integer(req.getParameter("emp_status").trim());
				Integer emp_auth = new Integer(req.getParameter("emp_auth").trim());
				
				java.sql.Timestamp emp_last_edit_time = null;
				try {
					emp_last_edit_time = java.sql.Timestamp.valueOf(req.getParameter("emp_last_edit_time").trim());
				} catch (IllegalArgumentException e) {
					emp_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String emp_last_editor = req.getParameter("emp_last_editor");
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_id(emp_id);
				empVO.setEmp_password(emp_password);
				empVO.setEmp_add_time(emp_add_time);
				empVO.setEmp_mail(emp_mail);
				empVO.setEmp_phone(emp_phone);
				empVO.setEmp_status(emp_status);
				empVO.setEmp_auth(emp_auth);
				empVO.setEmp_last_edit_time(emp_last_edit_time);
				empVO.setEmp_last_editor(emp_last_editor);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateEmp(emp_id, emp_password, emp_add_time, emp_mailReg, emp_phoneReg, emp_status, emp_auth, emp_last_edit_time, emp_last_editor);
				
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				
				String emp_password = req.getParameter("emp_password");
		
				
				java.sql.Timestamp emp_add_time = null;
				try {
					emp_add_time = new java.sql.Timestamp(java.sql.Date.valueOf(req.getParameter("emp_add_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					emp_add_time=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				String emp_mail = req.getParameter("emp_mail");
				String emp_mailReg = "^[a-z0-9]+([.][_a-z0-9-]+)*@[a-z0-9]+([.][_a-z0-9-]+)*$";
				if (emp_mail == null || emp_mail.trim().length() == 0) {
					errorMsgs.add("信箱: 請勿空白");
				} else if(!emp_mail.trim().matches(emp_mailReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("信箱格式不符");
	            }
				
				String emp_phone = req.getParameter("emp_phone");
				String emp_phoneReg = "^09[0-9]{8}$";
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!emp_phone.trim().matches(emp_phoneReg)) { 
					errorMsgs.add("手機號碼格式不符");
	            }
				
				Integer emp_status = new Integer(req.getParameter("emp_status").trim());
				Integer emp_auth = new Integer(req.getParameter("emp_auth").trim());
				
				java.sql.Timestamp emp_last_edit_time = null;
				try {
					emp_last_edit_time = java.sql.Timestamp.valueOf(req.getParameter("emp_last_edit_time").trim());
				} catch (IllegalArgumentException e) {
					emp_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String emp_last_editor = req.getParameter("emp_last_editor");

				EmpVO empVO = new EmpVO();
				empVO.setEmp_password(emp_password);
				empVO.setEmp_add_time(emp_add_time);
				empVO.setEmp_mail(emp_mail);
				empVO.setEmp_phone(emp_phone);
				empVO.setEmp_status(emp_status);
				empVO.setEmp_auth(emp_auth);
				empVO.setEmp_last_edit_time(emp_last_edit_time);
				
				empVO.setEmp_last_editor(emp_last_editor);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.addEmp(emp_password, emp_add_time, emp_mailReg, emp_phoneReg, emp_status, emp_auth, emp_last_edit_time, emp_last_editor);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String emp_id = new String(req.getParameter("emp_id"));
				
				/***************************2.�}�l�R�����***************************************/
				EmpService empSvc = new EmpService();
				empSvc.deleteEmp(emp_id);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
