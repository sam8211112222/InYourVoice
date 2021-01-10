<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">
<style>

</style>
<body onload="connect();">

	<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>

	<div class="container content clear-fix">

		<input type="button" class="button" value="Send" onclick="sendMessage();">
		<div class='title'>Click on the Icon</div>
		<div class='wrapper hide'>
			<div class='num hide'>1</div>

			<svg width="900px" height="300px" viewBox="0 0 1280 800"
				version="1.1" xmlns="http://www.w3.org/2000/svg"
				xmlns:xlink="http://www.w3.org/1999/xlink" xml:space="preserve"
				style="fill-rule: evenodd; clip-rule: evenodd; stroke-linejoin: round; stroke-miterlimit: 1.41421;">
    <path
					d="M590.832,448.337C589.517,447.337 587.981,446.403 587.268,445.76C585.646,444.297 584.799,442.068 583.9,439.879C583.076,437.287 583.226,434.708 583.826,432.105C598.194,409.92 613.659,388.608 615.226,353.606C614.434,313.878 623.308,291.788 636.25,278.444C646.872,265.203 661.154,255.282 678.585,248.44L678.682,248.392L678.782,248.315C678.544,246.767 678.498,245.016 678.624,243.268C678.695,242.277 678.846,241.558 678.891,240.782C678.979,239.258 679.235,237.743 679.617,236.244C683.002,222.955 697.006,213.234 710.971,213.252C725.736,213.271 740.731,223.402 743.195,238.331C743.829,242.172 743.947,245.478 743.635,248.315L743.674,248.315C743.666,249.147 743.627,249.95 743.552,250.709C764.033,255.522 778.234,269.91 790.363,287.817C800.055,309.26 808.132,332.749 805.903,365.982C807.185,397.309 827.417,409.547 831.671,424.611C823.129,420.685 814.503,416.156 805.461,414.136C798.061,410.999 790.252,409.309 782.342,408.311C774.976,406.891 767.529,405.671 759.828,405.082C750.158,403.271 740.388,402.951 730.57,403.341C735.655,405.566 740.848,407.555 746.861,408.442C750.394,408.979 753.871,409.508 756.3,409.982C760.698,410.838 765.552,412.122 769.691,413.057C773.005,413.806 776.268,414.71 779.84,415.717C783.341,416.652 786.619,417.054 789.637,418.667C792.317,419.256 794.42,421.268 796.434,422.357C798.768,423.618 800.839,425.135 802.242,427.168L804.335,430.933C805.091,433.967 805.325,437.021 804.201,440.126C803.005,442.829 801.051,444.819 798.698,446.434C798.065,446.976 797.42,447.494 796.76,447.981C793.873,451.945 782.789,456.794 765.653,460.908C763.77,461.469 761.844,461.906 759.865,462.219C742.58,465.912 720.171,468.804 694.412,469.557C642.401,471.078 599.137,456.184 590.832,448.337Z"
					style="fill:#fff;" />
    <g transform="matrix(1,0,0,1,-7.83975,-8.3859)">
    
        <circle class='bell' cx="696.599" cy="475.846" r="27.99" />
     
    </g>
    
</svg>
		</div>
		<div class="notification hide">
			<div class="section one">
				<div class="text one"></div>
				<div class="rect one"></div>
			</div>
			<div class="section two">
				<div class="text"></div>
				<div class="rect two"></div>

			</div>
			<div class="section three">
				<div class="text"></div>
				<div class="rect three"></div>

			</div>
			<div class="section four">
				<div class="text"></div>
				<div class="rect four"></div>

			</div>
		</div>


	</div>






	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>

	<script>
		var MyPoint = "/notification/{${memberVo.memberId}}";
		console.log(MyPoint);
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		console.log(endPointURL);
		var webSocket;
		function connect() {
			// create a websocket
			webSocket = new WebSocket(endPointURL);

			webSocket.onopen = function(event) {
				console.log("Connect Success!");
			};
			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				icRing();
				console.log(jsonObj);
			}
		}
		function sendMessage() {
			var jsonObj = {
				title : "發送成功",
				content : "安安安 ",
				senderId : "${memberVo.memberId}",
				isRead : false,
				sendTime : new Date().getTime(),
			};
			webSocket.send(JSON.stringify(jsonObj));
		}
		
		function icRing(){
			let ring = document.getElementById("ring");
			let number = parseInt($("#ring").text()) +1;
			$("#ring").text(number);
		}
	</script>
	<%@ include file="/css/member/member_center_bottom.file"%>

</body>
</html>