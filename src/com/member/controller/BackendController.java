package com.member.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if ("addMember".equals(str)) {
			String memberAccount = request.getParameter("memberAccount");
			Map<String, String> errors = new HashMap<String, String>();
			request.setAttribute("errors", errors);
			MemberService memberSvc = new MemberService();
			String accountReg = "^[a-z0-9A-Z]+@{1}";
			if (memberSvc.findByAccount(memberAccount) != null) {
				errors.put("account", "帳號重複囉");
			}
			if (memberAccount == null || memberAccount.trim().isEmpty()) {
				errors.put("account2", "請輸入帳號");
			}
			if (memberAccount.matches(accountReg)) {
				errors.put("account2", "帳號格式有誤");
			}
			String memberPassword = request.getParameter("memberPassword");
			if (memberPassword == null || memberPassword.trim().isEmpty()) {
				errors.put("password", "請輸入密碼");
			}
			String memberGender = request.getParameter("memberGender");
			if (memberGender == null || memberGender.trim().isEmpty()) {
				errors.put("gender", "請勾選性別");
			}
			String memberPhone = request.getParameter("memberPhone");
			String phoneReg = "^0(9)[0-9]{8}$";
			if (!(memberPhone.matches(phoneReg)) && !(memberPhone.length() == 10)) {
				errors.put("phone", "手機格式有誤");
			}
			String memberAddress = request.getParameter("memberAddress");
			if (memberAddress == null || memberAddress.trim().isEmpty()) {
				memberAddress = null;
			}
			String memberName = request.getParameter("memberName");
			if (memberName == null || memberName.trim().isEmpty()) {
				errors.put("name", "請輸入名字");
			}
			String memberNickname = request.getParameter("memberNickname");
			if (memberName == null || memberName.trim().isEmpty()) {
				memberNickname = null;
			}
			String memberBirth = request.getParameter("memberBirth");
			if (memberBirth == null) {
				errors.put("memberBirth", "請選擇生日");
			}
			Integer memberMsgAuth = 1; // 預設1
			String memberCardNumber = null;
			Integer memberCardExpyear = 0;
			Integer memberCardExpmonth = 0;
			java.util.Date date = new java.util.Date();
			Timestamp addTime = new Timestamp(date.getTime());
			String bandId = null;

			if (errors != null && !errors.isEmpty()) {
				request.getRequestDispatcher("/back-end/addMember.jsp").forward(request, response);
				return;
			}
			memberSvc.addMember(memberAccount, memberPassword, memberGender, memberPhone, memberAddress, memberName,
					memberNickname, java.sql.Date.valueOf(memberBirth), memberMsgAuth, memberCardNumber,
					memberCardExpyear, memberCardExpmonth, addTime, bandId);
			System.out.println("註冊成功");
			RequestDispatcher successView = request.getRequestDispatcher("/back-end/AllMember.jsp"); // 新增成功後轉交listAllEmp.jsp
			successView.forward(request, response);
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
