<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
	content='54001673882-mtl8uc9ijbk6k723bogll7nvgfsout9f.apps.googleusercontent.com'>
<script src="https://apis.google.com/js/platform.js" async defer></script>
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
						<c:if test="${not empty errors}">
						<p><font style="color: red">${errors.username}</font></p>
					</c:if>
				</div>
				<div class="input_field">
					<label>Password</label> <input type="password" class="input"
						name="memberPassword">
						<c:if test="${not empty errors}">
						<p><font style="color: red">${errors.password}</font></p>
					</c:if>
						 <a href="<%=request.getContextPath()%>/front-end/member/forgetPasswordEmail.jsp" class="forgot">忘記密碼?</a>
				</div>
				<div class="btn">
					<input type="submit" value="登入" class="sign_btn" name="log">
					<input type="hidden" name="action" value="login">
					<div class="social-signin">
<!-- 						<button class="social-signin facebook">Facebook</button> -->
<!-- 						<button class="social-signin google" id="googleSignin">Google+</button> -->
<!-- 						<div class="g-signin2" data-onsuccess="onSignIn"></div> -->
<!-- 						<a href="#" onclick="signOut();">Sign out</a> -->
						
					</div>
				</div>
			</div>
			<div class="create_act">
				<p>
					未註冊嗎? <a href="<%=request.getContextPath()%>/front-end/member/signup.jsp">註冊VarYours</a>
				</p>
			</div>
		</div>
	
	</form>
	<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
<script>
// function onSignIn(googleUser) {
//     // Useful data for your client-side scripts:
//     var profile = googleUser.getBasicProfile();
//     console.log("ID: " + profile.getId()); // Don't send this directly to your server!
//     console.log('Full Name: ' + profile.getName());
//     console.log('Given Name: ' + profile.getGivenName());
//     console.log('Family Name: ' + profile.getFamilyName());
//     console.log("Image URL: " + profile.getImageUrl());
//     console.log("Email: " + profile.getEmail());

//     // The ID token you need to pass to your backend:
//     var id_token = googleUser.getAuthResponse().id_token;
//     console.log("ID Token: " + id_token);
//     let data ={
//             action:"google",
//             memberName:profile.getName(),
//             memberAccount : profile.getEmail(),
//            };
//     $.ajax({
//              type: "POST",
//              url: "/TEA102G6/Login",
//              data:data,
//              success: function (result) {
//             	 signOut();
//              },
//              error: function (err) {
//                  alert("系統錯誤");
//              }
//          });
//   }
// function signOut() {
// 	var auth2 = gapi.auth2.getAuthInstance();
// 	auth2.signOut().then(function() {
// 		console.log('User signed out.');
// 	});
// }
  </script>
</body>
</html>

