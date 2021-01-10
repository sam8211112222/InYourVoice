<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id content="
	content='客戶端ID'>
<script src="https://apis.google.com/js/platform.js" async defer></script>
</head>
<body>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
	<a href="#" onclick="signOut();">Sign out</a>
	<script>
		function signOut() {
			var auth2 = gapi.auth2.getAuthInstance();
			auth2.signOut().then(function() {
				console.log('User signed out.');
			});
		}

		function onSignIn(googleUser) {
			//跳轉到http://gntina.iok.la/sendRedirect(獲取使用者資訊)
			location.href = "http://gntina.iok.la/sendRedirect";
			//獲取使用者基本資訊，但是此id不能給後臺用，不安全，改用id_token
			/*從這往下的程式碼都不需要，因為是在後臺驗證，後臺獲取使用者資訊
                        var profile = googleUser.getBasicProfile();
			console.log('google自己封裝好的獲取使用者資訊');
			console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
			console.log('Name: ' + profile.getName());
			console.log('Image URL: ' + profile.getImageUrl());
			console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
