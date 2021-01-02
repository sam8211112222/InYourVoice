<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<title>登入VarYours</title>


<link href="<%=request.getContextPath()%>/css/member/Login.css" rel="stylesheet">
<style>
#logo{
width:120px;
height:120px;
}
</style>
</head>
<body class="text-center">
	<form class="form-signin" action="<%=request.getContextPath()%>/Login"
		method="POST">
		<div class="wrapper">
			<div class="logo">
				<img src="<%=request.getContextPath() %>/images/inYourVoice.jpg" alt="InYouVoice">
			</div>
			<div class="title">
				<p>Sign in to InYouVoice</p>
			</div>
			<div class="form">
				<div class="input_field">
					<label>email address</label> <input type="text" class="input"
						name="memberAccount">
				</div>
				<div class="input_field">
					<label>Password</label> <input type="password" class="input"
						name="memberPassword"> <a href="<%=request.getContextPath()%>/front-end/member/forgetPasswordEmail.jsp" class="forgot">忘記密碼?</a>
				</div>
				<div class="btn">
					<input type="submit" value="登入" class="sign_btn" name="log">
					<input type="hidden" name="action" value="login">
					<div class="social-signin">
						<button class="social-signin facebook">Facebook</button>
						<button class="social-signin google">Google+</button>
					</div>
				</div>
			</div>
			<div class="create_act">
				<p>
					未註冊嗎? <a href="<%=request.getContextPath()%>/front-end/member/signup.jsp">註冊VarYours</a>
				</p>
			</div>
		</div>
		<jsp:useBean id="memberVo" scope="session"
			class="com.member.model.MemberVo" />
	</form>
	
</body>
</html>
