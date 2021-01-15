package com.member.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVo;

/**
 * Servlet implementation class BackendController
 */
@WebServlet("/BackendController")
public class BackendController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// backend
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String str = request.getParameter("action");
		if("login".equals(str)) {
			String empEmail = request.getParameter("empEmail");
			String empPassword = request.getParameter("empPassword");
			Map<String,String> errors = new HashMap<String,String>();
			request.setAttribute("errors", errors);
			if(empEmail==null || empEmail.length()==0) {
				errors.put("username", "請輸入帳號");
			}
			if(empPassword==null || empPassword.length()==0) {
				errors.put("password", "請輸入密碼");
			}
			if(errors!=null && !errors.isEmpty()) {
				request.getRequestDispatcher(
						"/front-end/member/Login.jsp").forward(request, response);
				return;
			}
			EmpService empSvc = new EmpService();
			EmpVO empVO = empSvc.login(empEmail, empPassword);
			if(empVO == null) {
				errors.put("password", "登入失敗 請檢查帳號密碼");
				request.getRequestDispatcher(
						"/front-end/member/Login.jsp").forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("empVO", empVO);
				
				String path = request.getContextPath();
				response.sendRedirect(path+"/front-end/member/memberCenter2.jsp");
			}
		}
		if ("addMember".equals(str)) {
			String memberAccount = request.getParameter("memberAccount");
			Gson gson = new Gson();
			Map<String,String> msg = new HashMap<String,String>();
			PrintWriter pw = response.getWriter();
			MemberService memberSvc = new MemberService();
			String memberPassword = request.getParameter("memberPassword");

			String memberGender = request.getParameter("memberGender");
			String memberPhone = request.getParameter("memberPhone");
			String memberAddress = request.getParameter("memberAddress");
			String memberName = request.getParameter("memberName");
			
			String memberNickname = request.getParameter("memberNickname");
			if(memberName==null||memberName.trim().isEmpty()) {
				memberNickname = "";
			}
			
			java.sql.Date memberBirth = java.sql.Date.valueOf(request.getParameter("memberBirth").trim());

			Integer memberMsgAuth = 1; //預設1
			String memberCardNumber = "0"; //預設
			Integer memberCardExpyear = 0;
			Integer memberCardExpmonth = 0;
			java.util.Date date = new java.util.Date();
			Timestamp addTime = new Timestamp(date.getTime());
			String bandId = null;
			InputStream in = new FileInputStream(getServletContext().getRealPath("/images/無圖片.png"));
			byte[] memberPhoto = new byte[in.available()];
			in.read(memberPhoto);

			memberSvc.addMember(memberAccount, memberPassword, memberGender, memberPhone, memberAddress, memberName, memberNickname, memberBirth, memberMsgAuth, memberCardNumber, memberCardExpyear, memberCardExpmonth, addTime, bandId, memberPhoto);

			msg.put("msg", "true");
			msg.put("status","新增成功");								
			pw.write(gson.toJson(msg));				
			pw.close();
		}
		//ajax 驗證註冊帳號
				if("accountMatche".equals(str)) {
					String memberAccount = request.getParameter("memberAccount");
					MemberService memberSvc = new MemberService();
					Map<String,String> msg = new HashMap<String,String>();
					PrintWriter pw = response.getWriter();
					Gson gson = new Gson();
					String reg = "^\\w{0,30}@{1}\\w{1,15}\\.\\w{1,10}$";
					if(memberAccount==null || memberAccount.trim().isEmpty()) {
						msg.put("error", "null");
						msg.put("msg", "帳號不能為空");
						pw.write(gson.toJson(msg));
						pw.close();
					}
					if(memberSvc.findByAccount(memberAccount)!=null) {
						msg.put("error", "repeat");
						msg.put("msg", "帳號重複囉 請換一個");
						pw.write(gson.toJson(msg));
						pw.close();
					}
					if(!memberAccount.matches(reg)) {
						msg.put("error", "noMatche");
						msg.put("msg", "帳號格式不對");
						pw.write(gson.toJson(msg));
						pw.close();
					}
					msg.put("error", "true");
					msg.put("msg", "帳號可以使用");
					pw.write(gson.toJson(msg));
					pw.close();
				}//ajax 驗證註冊帳號的手機號碼
				if("phoneMatche".equals(str)) {
					String memberPhone = (String)request.getParameter("memberPhone");
					String phoneReg = "^0(9)[0-9]{8}$";
					Gson gson = new Gson();
					Map<String,String> msg = new HashMap<String,String>();
					PrintWriter pw = response.getWriter();
					if(!(memberPhone.matches(phoneReg))&&!(memberPhone.length()==10)) {						
						msg.put("msg", "false");
						msg.put("errorPhone","格式錯誤");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}else {
						msg.put("msg", "true");
						msg.put("errorPhone","手機格式正確 可以使用");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}
				}//ajax 檢查密碼
				if("passwordCheck".equals(str)) {
					String password = (String)request.getParameter("memberPassword");
					Gson gson = new Gson();
					Map<String,String> msg = new HashMap<String,String>();
					PrintWriter pw = response.getWriter();
					if((password.length()<4)) {						
						msg.put("msg", "length");
						msg.put("errorPassword","長度至少4碼");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}else if(password.trim().isEmpty()){
						msg.put("msg", "isEmpty");
						msg.put("errorPassword","密碼不能為空");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}
					msg.put("msg", "true");
					msg.put("errorPassword","密碼符合要求");								
					pw.write(gson.toJson(msg));				
					pw.close();
				}
				if("nameCheck".equals(str)) {
					String name = (String)request.getParameter("memberName");
					Gson gson = new Gson();
					Map<String,String> msg = new HashMap<String,String>();
					PrintWriter pw = response.getWriter();
					if((name.trim().isEmpty())) {						
						msg.put("msg", "length");
						msg.put("errorName","請輸入姓名");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}else {
						msg.put("msg", "true");
						msg.put("errorName","OK");								
						pw.write(gson.toJson(msg));				
						pw.close();
					}
				}
		if ("getone".equals(str)) {
			String memberId = (String) (request.getParameter("memberId"));
			MemberService memberSvc = new MemberService();
			MemberVo memberVo = memberSvc.getOne(memberId);
			request.setAttribute("memberVo", memberVo);
			String url = "/back-end/updateMember.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		if ("updateMember".equals(str)) {
			Map<String, String> errors = new HashMap<String, String>();
			request.setAttribute("errors", errors);
			Timestamp addTime = (Timestamp) request.getAttribute("addTime");
			String memberId = (String) request.getParameter("memberId");
			String memberAccount = (String) request.getParameter("memberAccount");
			String memberPassword = (String) request.getParameter("memberPassword");
			String memberName = (String) request.getParameter("memberName");
			String memberNickname = (String) request.getParameter("memberNickname");
			String memberGender = (String) request.getParameter("memberGender");
			String memberPhone = (String) request.getParameter("memberPhone");
			String phoneReg = "^0(9)[0-9]{8}$";
			if (!(memberPhone.matches(phoneReg)) && !(memberPhone.length() == 10)) {
				errors.put("phone", "手機格式有誤");
			}
			String memberAddress = (String) request.getParameter("memberAddress");
			String bandId = (String) request.getParameter("bandId");
			Integer memberMsgAuth = Integer.valueOf(request.getParameter("memberMsgAuth"));
			java.sql.Date memberBirth = java.sql.Date.valueOf(request.getParameter("memberBirth"));

			MemberVo memberVo = new MemberVo();
			memberVo.setAddTime(addTime);
			memberVo.setMemberId(memberId);
			memberVo.setMemberAccount(memberAccount);
			memberVo.setMemberPassword(memberPassword);
			memberVo.setMemberAddress(memberAddress);
			memberVo.setMemberName(memberName);
			memberVo.setMemberNickname(memberNickname);
			memberVo.setMemberGender(memberGender);
			memberVo.setMemberPhone(memberPhone);
			memberVo.setBandId(bandId);
			memberVo.setMemberBirth(memberBirth);
			memberVo.setMemberMsgAuth(memberMsgAuth);
			if (errors != null && !errors.isEmpty()) {
				request.setAttribute("memberVo", memberVo);
				request.getRequestDispatcher("/back-end/updateMember.jsp").forward(request, response);
				return;
			}
			memberService = new MemberService();
			memberService.getOneForUpdate(memberName, memberNickname, memberGender, memberPhone, memberAddress,
					memberVo, bandId, memberBirth, memberMsgAuth);
			String url = "/back-end/AllMember.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		if ("delete".equals(str)) {
			String memberId = (String) request.getParameter("memberId");
			MemberService memberSvc = new MemberService();
			memberSvc.delet(memberId);
			System.out.println("刪除成功");
			String url = "/back-end/AllMember.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		if ("update".equals(str)) {
			String memberId = (String) request.getParameter("memberId");
			System.out.println(memberId);
			String memberName = (String) request.getParameter("memberName");
			String memberGender = (String) request.getParameter("memberGender");
			String memberPhone = (String) request.getParameter("memberPhone");
			memberService = new MemberService();
			memberService.updateFromBackEnd(memberId, memberPhone, memberGender, memberName);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
