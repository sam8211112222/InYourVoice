package com.member.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVo;

import redis.clients.jedis.Jedis;


@WebServlet("/Login")
@MultipartConfig
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String str = request.getParameter("action");
		String memberpic = request.getParameter("memberpic");
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		if("login".equals(str)) {
			String memberAccount = request.getParameter("memberAccount");
			String memberPassword = request.getParameter("memberPassword");
			Map<String,String> errors = new HashMap<String,String>();
			request.setAttribute("errors", errors);
		if(memberAccount==null || memberAccount.length()==0) {
			errors.put("username", "請輸入帳號");
		}
		if(memberPassword==null || memberPassword.length()==0) {
			errors.put("password", "請輸入密碼");
		}
		
		if(errors!=null && !errors.isEmpty()) {
			request.getRequestDispatcher(
					"/front-end/Login.jsp").forward(request, response);
			return;
		}
		memberService = new MemberService();
		MemberVo memberVo = memberService.login(memberAccount, memberPassword);
		if(memberVo == null) {
			errors.put("password", "登入失敗 請檢查帳號密碼");
			request.getRequestDispatcher(
					"/front-end/Login.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("memberVo", memberVo);
			
			String path = request.getContextPath();
			response.sendRedirect(path+"/front-end/member/memberCenter.jsp");
		}
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
		if("registered".equals(str)) {
			String memberAccount = request.getParameter("memberAccount");
			Gson gson = new Gson();
			Map<String,String> msg = new HashMap<String,String>();
			PrintWriter pw = response.getWriter();
//			Map<String,String> errors = new HashMap<String,String>();
//			request.setAttribute("errors", errors);
			MemberService memberSvc = new MemberService();
//			if(memberSvc.findByAccount(memberAccount)!=null) {
//				errors.put("account", "帳號重複囉");
//			}
//			if(memberAccount==null || memberAccount.trim().isEmpty()) {
//				errors.put("account", "請輸入帳號");
//			}
			String memberPassword = request.getParameter("memberPassword");
//			if(memberPassword==null || memberPassword.trim().isEmpty()) {
//				errors.put("password", "請輸入密碼");
//			}
			String memberGender = request.getParameter("memberGender");
//			if(memberGender==null|| memberGender.trim().isEmpty()) {
//				errors.put("gender", "請勾選性別");
//			}
			String memberPhone = request.getParameter("memberPhone");
//			String phoneReg = "^0(9)[0-9]{8}$";
//			if(!(memberPhone.matches(phoneReg))&& !(memberPhone.length()==10)) {
//				errors.put("phone", "手機格式有誤");
//			}
			String memberAddress = request.getParameter("memberAddress");
			String memberName = request.getParameter("memberName");
//			if(memberName==null||memberName.trim().isEmpty()) {
//				errors.put("name", "請輸入名字");
//			}
			String memberNickname = request.getParameter("memberNickname");
			if(memberName==null||memberName.trim().isEmpty()) {
				memberNickname = "";
			}
			
			java.sql.Date memberBirth = java.sql.Date.valueOf(request.getParameter("memberBirth").trim());
//			if(memberBirth==null) {
//				errors.put("birth", "請選擇生日");
//			}
			Integer memberMsgAuth = 1; //預設1
			String memberCardNumber = "0"; //預設
			Integer memberCardExpyear = 0;
			Integer memberCardExpmonth = 0;
			java.util.Date date = new java.util.Date();
			Timestamp addTime = new Timestamp(date.getTime());
			String bandId = null;
//			MemberVo memberVo = new MemberVo();
//			memberVo.setMemberAccount(memberAccount);
//			memberVo.setMemberAddress(memberAddress);
//			memberVo.setMemberGender(memberGender);
//			memberVo.setMemberName(memberName);
//			memberVo.setMemberNickname(memberNickname);
//			memberVo.setMemberPhone(memberPhone);
//			memberVo.setMemberBirth(memberBirth);
			
//			if(errors!=null && !errors.isEmpty()) {
//				request.setAttribute("memberVo", memberVo);
//				request.getRequestDispatcher(
//						"/front-end/registered.jsp").forward(request, response);
//				return;
//			}
			memberSvc.addMember(memberAccount, memberPassword, memberGender, memberPhone, memberAddress, memberName, memberNickname, memberBirth, memberMsgAuth, memberCardNumber, memberCardExpyear, memberCardExpmonth, addTime, bandId);
//			System.out.println("註冊成功");
//			RequestDispatcher successView = request.getRequestDispatcher("/front-end/login.jsp"); // 新增成功後轉交listAllEmp.jsp
//			successView.forward(request, response);
			msg.put("msg", "true");
			msg.put("status","註冊成功");								
			pw.write(gson.toJson(msg));				
			pw.close();
			String authcode = genAuthCode();
			sendMail(memberAccount,authcode);
			jedis.set("authCode", authcode);
			jedis.set("memberAccount", memberAccount);
		}

		//更換照片
		if("updatePic".equals(str)) {
			HttpSession session = request.getSession();
			MemberVo memberVo = (MemberVo)session.getAttribute("memberVo");
			Part part = request.getPart("pic");
			InputStream in = part.getInputStream();
			byte[] buf = new byte[in.available()];
			in.read(buf);
			in.close();
			memberService = new MemberService();
			memberService.updatePhoto(memberVo,buf);
//			String status ="{\"status\" : 200,\"msg\":\"succes\"}";
//			response.getWriter().append(status);
//			RequestDispatcher successView = request.getRequestDispatcher("/front-end/member.jsp");
//			successView.forward(request, response);
			
			}
		//更換帳號
		
		if("updateac".equals(str)) {
		
			String memberName = (String)request.getParameter("memberName");
			String memberNickname = (String)request.getParameter("memberNickname");
			String memberGender = (String)request.getParameter("memberGender");
			String memberPhone = (String)request.getParameter("memberPhone");
			String phoneReg = "^0(9)[0-9]{8}$";
			if(!(memberPhone.matches(phoneReg))&& !(memberPhone.length()==10)) {
				
			}
			String memberAddress = (String)request.getParameter("memberAddress");
			String memberCardNumber = (String)request.getParameter("memberCardNumber");
			Integer memberCardExpyear = Integer.valueOf(request.getParameter("memberCardExpyear"));
			Integer memberCardExpmonth = Integer.valueOf(request.getParameter("memberCardExpmonth"));
			HttpSession session = request.getSession();
			MemberVo memberVo = (MemberVo)session.getAttribute("memberVo");
			memberService = new MemberService();
			memberService.updateMemberinfo(memberName, memberNickname, memberGender, memberPhone, memberAddress, memberCardNumber, memberCardExpyear, memberCardExpmonth, memberVo);	
		}
		//更改密碼請求 檢查舊密碼
		if("changePasswordCheck".equals(str)) {
			String password = (String)request.getParameter("memberPassword");
			HttpSession session = request.getSession();
			MemberVo memberVo =(MemberVo)session.getAttribute("memberVo");
			Gson gson = new Gson();
			Map<String,String> msg = new HashMap<String,String>();
			PrintWriter pw = response.getWriter();
			if(password.equals(memberVo.getMemberPassword())) {
				msg.put("msg", "correct");
				msg.put("errorPassword", "密碼確認完成");
				pw.write(gson.toJson(msg));
			}else {
				msg.put("msg", "error");
				msg.put("errorPassword", "不正確請重新輸入");
				pw.write(gson.toJson(msg));
			}
		}
		if("changePassword".equals(str)) {
			String password = (String)request.getParameter("memberPassword");
			HttpSession session = request.getSession();
			MemberVo memberVo =(MemberVo)session.getAttribute("memberVo");
			Gson gson = new Gson();
			Map<String,String> msg = new HashMap<String,String>();
			PrintWriter pw = response.getWriter();
			MemberService memberSvc = new MemberService();
			memberSvc.changePassword(memberVo, password);
			msg.put("msg", "true");
			pw.write(gson.toJson(msg));
		}
		//登出
		if("logout".equals(str)) {
			HttpSession session = request.getSession();
			session.invalidate();
		}
		if("picDisplay".equals(memberpic)) {
			String id = (String)request.getParameter("memberId");
			MemberService memberSvc = new MemberService();

			byte[] pic = memberSvc.getOne(id).getMemberPhoto();
			OutputStream sout = response.getOutputStream();
			response.setContentLength(pic.length);
			sout.write(pic);
			sout.close();
		}
		if("auth".equals(str)) {
			String authCode = (String)request.getParameter("authCode");
			if(jedis.get("authCode").equals(authCode)) {
				MemberService memberSvc = new MemberService();
				MemberVo memberVo = memberSvc.findByAccount(jedis.get("memberAccount"));
				memberSvc.memberSetAuth(memberVo, 2);
			}
			response.sendRedirect("http://localhost:8081/TEA102G6/front-end/member/Login.jsp");
		}
}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	public String genAuthCode() {
		String str = ("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		char[] c =new char[8];
		
		for(int i =0;i<8;i++) {
			int x =(int)(Math.random()*62);
			c[i] = str.charAt(x);
		}
		String code = String.valueOf(c);//用valueof將字元陣列轉換字串
		return code;
	}
	public void sendMail(String memberAccount,String code) {
		String targetEmail = memberAccount;

	      // Sender's email ID needs to be mentioned
	      String from = "kevintsaowei1992@gmail.com";
	      final String username = "kevintsaowei1992";//change accordingly
	      final String password = "chicagobulls1992";//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
		   }
	         });

	      try {
		   // Create a default MimeMessage object.
		   Message message = new MimeMessage(session);
		
		   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(targetEmail));
		
		   // Set Subject: header field
		   message.setSubject("恭喜你已成功註冊成為VarYours會員");
		
		   // Now set the actual message
		   message.setText("恭喜你已註冊完成請點擊下列網址進行首次登入驗證"+"<a href='http://localhost:8081/TEA102G6/Login?action=auth&authCode="
	                + code + "'></a>");

		   // Send message
		   Transport.send(message);

		   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	}
}
