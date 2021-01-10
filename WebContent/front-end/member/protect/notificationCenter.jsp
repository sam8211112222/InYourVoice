<%@page import="com.notification.model.JedisMessage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.notification.model.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
	
	pageContext.setAttribute("mesg", JedisMessage.getMessage(memberVo.getMemberId()));
	pageContext.setAttribute("isRead", JedisMessage.count(memberVo.getMemberId()));
	pageContext.setAttribute("new5", JedisMessage.getMessageNew5(memberVo.getMemberId()));
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
<body onload="connect();">
<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>

	<div class="container content clear-fix">

		<h2 class="mt-5 mb-5">通知中心</h2>

		<div class="row" style="height: 100%">
			<div class="col-md-10" style="margin-left: 60px;">
				<table class="table">
					<thead>
						<tr>
							<th scope="col"></th>
							<th scope="col">標題</th>
							<th scope="col">接收人</th>
							<th scope="col">時間</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="memNoti" items="${mesg}">
						<tr>
							<th scope="row">1</th>
							<td>${memNoti.title}</td>
							<td>${memNoti.receiver}</td>
							<td><fmt:formatDate value="${memNoti.sendTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>



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
			if("${isRead}" !=0){
			let ring = document.getElementById("ring");
			$("#ring").text("${isRead}");
			$("#ring").addClass("notice-number");
			}
			console.log("Connect Success!");
		};
		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			icRing();
			noti(jsonObj.title);
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
		let number ;
		if($("#ring").text()===""){
			$("#ring").text(parseInt(1));
			$("#ring").addClass("notice-number");
		}else{
			let number = parseInt($("#ring").text());
			$("#ring").text(number+1);
		}				
	}
	function noti(data){
		var noti = document.getElementById("noti");
		let notifi = document.createElement("a");
		let content = notifi.setAttribute("class","dropdown-item");
		notifi.text= data;
		
		
		console.log(notifi.text);
		noti.append(notifi);
	}
	function isRead(){
		var jsonObj = {
				title : "",
				content : " ",
				senderId : "${memberVo.memberId}",
				type : "isRead",
				sendTime : new Date().getTime(),
			};
		webSocket.send(JSON.stringify(jsonObj));
	}
	$("#ringring").click(function(e){
		isRead();
		let ring = document.getElementById("ring");
		ring.innerText="";
		$("#ring").removeClass("notice-number");
	});
	</script>
	<%@ include file="/css/member/member_center_bottom.file"%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>