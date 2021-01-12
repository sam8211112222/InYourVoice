<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.notification.model.*"%>
<%
	MemberVo member = (MemberVo) session.getAttribute("memberVo");
	if (member != null) {
		request.setAttribute("isRead", JedisMessage.count(member.getMemberId()));
		request.setAttribute("new5", JedisMessage.getMessageNew5(member.getMemberId()));
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>header</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">


<style>
div.logo  img {
	width: 100%;
	height: 100%;
}

header {
	display: flex;
	justify-content: space-between;
}

.navbar {
	width: calc(100% - 40px);
}

#dropdownMenuButton {
	width: 40px;
	height: 22px;
	margin-right: 10px;
	border-radius: 50%;
	position: relative;
	cursor: pointer;
}

.fa-user:before {
	font-size: 25px;
}

#dropdownMenuButton img, #dropdownMenuButton .fa-user {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

#dropdownMenuButton img {
	width: 100%;
}

.userAvatar {
	width: 80px;
	height: 80px;
	border: 1px solid #000;
	margin-left: 40px;
	border-radius: 50%;
	overflow: hidden;
	position: relative;
	cursor: pointer;
}

.userAvatar img {
	width: 100%;
}

.dropdown-menu.show {
	display: block;
	text-align: center;
	top: 45px !important;
	left: -116px !important;
}

.logo {
	width: 60px;
	margin-left: 40px;
	position: relative;
	cursor: pointer;
	margin: auto;
	display: flex;
	align-items: center;
}

.logo a {
	margin: 0;
	padding: 0;
}

.fa-bell:before {
	content: "\f0f3";
	font-size: 25px;
}

.dropdown, .dropleft, .dropright, .dropup {
	display: flex;
	align-items: center;
}

.notice {
	position: relative;
}

.notice-number {
	width: 20px;
	height: 20px;
	/* border: 1px solid #000; */
	border-radius: 50%;
	position: absolute;
	top: -12px;
	left: 12px;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 6px;
	color: #fff;
	background: red;
}

.user {
	width: 80px;
	height: 80px;
	border: 1px solid #000;
	margin-left: 40px;
	border-radius: 50%;
	overflow: hidden;
	position: relative;
	cursor: pointer;
}

.user img {
	width: 100%;
}
</style>

</head>
<body onload="connect();">


	<header class="bg-light">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarTogglerDemo03"
				aria-controls="navbarTogglerDemo03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="logo">
				<a class="navbar-brand"
					href="<%=request.getContextPath()%>/index.jsp"><img
					src="<%=request.getContextPath()%>/images/logo2.jpg"></a>
			</div>
			<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<!-- <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li> -->
					<li class="nav-item"><a class="nav-link" href="#">活動資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="#">樂團資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="#">周邊商品</a></li>

					<!-- <li class="nav-item">
                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                    </li> -->
					<form action="<%=request.getContextPath()%>/album/album.do"
						method="get" class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search" placeholder="搜尋"
							aria-label="Search" name="search" id="search" value="${name}">
						<input type="hidden" name="action" value="searchName">
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜尋</button>
					</form>
				</ul>
			</div>
			<!-- 小鈴鐺   -->
			<div class="dropdown dropleft">

				<div id="dropdownMenuButton" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
					<div class="notice">
						<span class="" id="ring"></span> <i class="fas fa-bell"
							style="color: dimgrey;" id="ringring"></i>
					</div>
				</div>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton"
					id="noti">
					<c:choose>
						<c:when test="${new5.size()==0}">
							<a href="#" class="dropdown-item">沒有任何通知</a>
						</c:when>

						<c:when test="${new5.size()!=0}">
							<c:forEach var="noti" items="${new5}">
								<a href="#" class="dropdown-item"><div>${noti.title}</div></a>

							</c:forEach>
						</c:when>
					</c:choose>

				</div>
			</div>

			<div class="dropdown dropleft">
				<div id="dropdownMenuButton" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
					<!-- <img src="./images/logo.jpg"> -->
					<i class="far fa-user"></i>
				</div>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<c:if test="${memberVo.memberId==null}">
						<div class="userAvatar">
							<!-- <img src="./images/logo.jpg"> -->
							<i class="fas fa-meh" style="font-size: 79px; color: #888;"></i>
						</div>
					</c:if>
					<c:if test="${memberVo.memberId!=null}">
						<div class="userAvatar"></div>
					</c:if>
					<div style="color: #2cbcf4">${memberVo.memberName}</div>
					<a class="dropdown-item" href="#">會員中心</a> <a class="dropdown-item"
						href="#">通知中心</a> <a class="dropdown-item" href="#">購物車</a> <a
						class="dropdown-item" href="#">我的最愛</a>
					<c:if test="${memberVo.memberId==null}">
						<a class="dropdown-item"
							href="<%=request.getContextPath()%>/front-end/member/Login.jsp">登入</a>
					</c:if>
					<c:if test="${memberVo.memberId!=null}">
						<a id="logoutBtn" class="dropdown-item" href="#">登出</a>
					</c:if>
				</div>
			</div>
	</header>

	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>

	<script>
	//登出
	$("#logoutBtn").click(function() {
		let obj = new FormData();
		obj.append("action", "logout");
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/Login",
				contentType : false,
				processData : false,
				cache : false,
				data : obj,

				success : function(result) {
					location.reload();
				},
				error : function(err) {
					alert("系統錯誤");
				}
			});
		})

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
				if ("${isRead}" != 0) {
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

		function icRing() {
			let ring = document.getElementById("ring");
			let number;
			if ($("#ring").text() === "") {
				$("#ring").text(parseInt(1));
				$("#ring").addClass("notice-number");
			} else {
				let number = parseInt($("#ring").text());
				$("#ring").text(number + 1);
			}
		}
		function noti(data) {
			var noti = document.getElementById("noti");
			let notifi = document.createElement("a");
			let content = notifi.setAttribute("class", "dropdown-item");
			notifi.text = data;

			console.log(notifi.text);
			noti.append(notifi);
		}
		function isRead() {
			var jsonObj = {
				title : "",
				content : " ",
				senderId : "${memberVo.memberId}",
				type : "isRead",
				sendTime : new Date().getTime(),
			};
			webSocket.send(JSON.stringify(jsonObj));
		}
		$("#ringring").click(function(e) {
			isRead();
			let ring = document.getElementById("ring");
			ring.innerText = "";
			$("#ring").removeClass("notice-number");
		});
	</script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
		

</body>
</html>