<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="com.event.model.EventVO"%>
<%@page import="java.util.List"%>
<%@page import="com.event.model.EventService"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="com.album.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- new 一個AlbumService來用 -->
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>


<!-- AlbumService albumSvc = new AlbumService(); -->
<!-- AlbumVO albumVO = albumSvc.getOneAlbum("ALBUM00000"); -->
<!-- String album_name = albumVO.getAlbum_name(); -->

<% MemberVo memberVo = (MemberVo) session.getAttribute("memberVo"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>InYourVoice</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/slick/slick-theme.css" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homepage.css" />
</head>

<body onload="connect();" onunload="disconnect();">


	<jsp:include page="/front-end/header_footer/header.jsp"></jsp:include>

	<%
		//step 1 拿到所有活動資料
		EventService eventSvc = new EventService();
		List<EventVO> eventVOList = eventSvc.getAll().stream()
				.filter(a -> a.getEvent_status() == 1)
				.collect(Collectors.toList());
		pageContext.setAttribute("eventVOList", eventVOList);//EL

		//step 2 跑迴圈，並判斷是否為主打活動
		//step 3 若確定是主打活動 此次迴圈才加上輪播的圖片
		//step 4加上輪播圖片所需的html程式碼時 記得替換src的內容
		List<BandVO> BandVOList = bandSvc.getAllBand().stream()
				.filter(a -> a.getBand_status() == 1)
				.collect(Collectors.toList());
		pageContext.setAttribute("bandVOList", BandVOList);
		
		List<AlbumVO> AlbumVOList = albumSvc.getAllAlbums().stream()
				.filter(a -> a.getAlbum_status() == 1)
				.collect(Collectors.toList());
		pageContext.setAttribute("albumVOList", AlbumVOList);
		
		
	%>

	<!-- 輪播 -->
	<div class="banner" style =  >

		<c:forEach var="eventVO" items="${eventVOList}">
			<c:if test="${eventVO.event_type==3&&eventVO.event_status==1}">
				<div class="ls-slide" style="cursor:pointer;" onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">
					<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}">
				</div>
			</c:if>
		</c:forEach>
	</div>
	<!-- 輪播 -->

	<!-- 最新消息 -->
	<div class="wrap">
		<h2 class="wrap-title">最新消息</h2>
		<table class="table table table-hover">
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="text-align: right;"><a href="#">More<i class="fas fa-angle-double-right"></i></i></a></th>
				</tr>
			</thead>
			<tbody>

				<c:forEach var="eventVO" items="${eventVOList}">
					<c:if test="${eventVO.event_type==4&&eventVO.event_status==1}">
						<tr onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">
							<th scope="row"><img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="latest-events"></th>
							<td>${eventVO.event_title}</td>
						</tr>
					</c:if>
				</c:forEach>

			</tbody>
		</table>
	</div>


	<!-- 熱門排行榜 -->
	<div class="title-song wrap">
		<h2 class="wrap-title">熱門排行榜</h2>
		<div class="leatitle-all">

			<!-- <div class="lea-title">
                熱門排行榜
            </div> -->

<!-- 			<div class="playall"> -->
<!-- 				<i class="fas fa-play"></i> 全部播放 -->
<!-- 			</div> -->
		</div>		
			<% int i = 1; %>
			<c:forEach var="albumVO" items="${albumVOList}" begin="1" end="10">
			
					<div class="lea-song-list">
						<div class="song-line" style="cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/band/band.do?action=getBandMain&band_id=${bandSvc.getOneBand(albumVO.band_id).band_id}';">
							<div class="num">
								<%= i++ %> <i class="fas fa-sort-up"></i>
							</div>
							<div class="lea-img" >
								<!-- 					<img src="./images/排行榜歌圖1.png" alt=""> -->
								<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" alt="">
			
								<div class="in-play" style="display: none;">
									<i class="far fa-play-circle img-play"></i>
								</div>
							</div>
							<div class="song-name">
								${albumVO.album_name}<br> <span> ${bandSvc.getOneBand(albumVO.band_id).band_name} </span>
							</div>
							<div class="add">
								<i class="fas fa-plus"></i>
							</div>
<!-- 							<div class="heart"> -->
<!-- 								<i class="fas fa-heartbeat"></i> 18.8k -->
<!-- 							</div> -->
							<div class="play-mu">
								<i class="far fa-play-circle"></i>
							</div>
						</div>
					</div>
				
			</c:forEach>
	</div>


	<!-- 每日好聲音 -->
	<div class="wrap">
		<h2 class="wrap-title">每日好聲音</h2>
		<div class="card-deck">
			<c:forEach var="bandVO" items="${bandVOList}" begin="1" end="5">
					<div class="card" style="cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/band/band.do?action=getBandMain&band_id=${bandVO.band_id}';">
						<img src="<%=request.getContextPath()%>/band/band.do?action=getBandPhoto&band_id=${bandVO.band_id}" class="card-img-top" alt="">
						<div class="card-body">
							<h5 class="card-title">${bandVO.band_name}</h5>
						</div>	
					</div>
			</c:forEach>
		</div>
	</div>


	<!-- 近期活動，放鬆好去處，特色活動 -->
	<div class="wrap">
		<h2 class="wrap-title">推薦活動</h2>
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation"><a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">近期活動</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">放鬆好去處</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">特色活動</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}" begin="1" end="6">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn" onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">檢視活動</button>
									</div>
								</div>
							</div>
					</c:forEach>




				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}">
						<c:if test="${eventVO.event_type==1}">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn" onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">檢視活動</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>


				</div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}">
						<c:if test="${eventVO.event_type==2}">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn" onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">檢視活動</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
		<!-- 客服聊天室窗 -->
	<c:if test="${memberVo.memberId!=null}">
		<div id="chat-circle" class="btn btn-raised">
			<div id="chat-overlay"></div>
			<i class="far fa-comment-dots"></i>
		</div>
	
		<div class="chat-box">
			<div class="chat-box-header">
				客服 <span class="chat-box-toggle"><i class="fas fa-times"></i></span>
			</div>
			<div class="chat-box-body">
				<div class="chat-box-overlay"></div>
				<div class="chat-logs" id="chat-logs"></div>
				<!--chat-log -->
			</div>
			<div class="chat-input">
				<input type="text" id="chat-input" placeholder="請輸入您的問題" onkeydown="if (event.keyCode == 13) sendMessage();" />
				<button type="submit" class="chat-submit" id="chat-submit">
					<i class="fas fa-paper-plane"></i>
				</button>
			</div>
		</div>
	</c:if>
	
	<jsp:include page="/front-end/header_footer/footer.jsp"></jsp:include>



        <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/vendors/slick/slick.min.js"></script>

	<!-- 輪播 -->
	<script>
		$('.banner').slick({
			 dots: true,
			 infinite: true,
			 speed: 300,
			 slidesToShow: 1,
			 centerMode: true,
			 adaptiveHeight: true,
			 variableWidth: true,
			 autoplay: true,
			 autoplaySpeed: 2000,
		});
	</script>
	<!-- 客服聊天 -->
	<script>
		var INDEX = 0;
		$("#chat-submit").click(function(e) {
			e.preventDefault();
			sendMessage();
		})

		function generate_message(msg, type) {
			INDEX++;
			var str = "";
			str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg "+type+"\">";
			str += "          <div class=\"cm-msg-text\">";
			str += msg;
			str += "          <\/div>";
			str += "        <\/div>";
			$(".chat-logs").append(str);
			$("#cm-msg-" + INDEX).hide().fadeIn(300);
			if (type == 'self') {
				$("#chat-input").val('');
			}
			$(".chat-logs").stop().animate({
				scrollTop : $(".chat-logs")[0].scrollHeight
			}, 1000);
		}

		function generate_button_message(msg, buttons) {
			/* Buttons should be object array 
			[
			    {
			    name: 'Existing User',
			    value: 'existing'
			    },
			    {
			    name: 'New User',
			    value: 'new'
			    }
			]
			 */
			INDEX++;
			var btn_obj = buttons
					.map(
							function(button) {
								return "              <li class=\"button\"><a href=\"javascript:;\" class=\"btn btn-primary chat-btn\" chat-value=\""+button.value+"\">"
										+ button.name + "<\/a><\/li>";
							}).join('');
			var str = "";
			str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg user\">";
			str += "          <div class=\"cm-msg-text\">";
			str += msg;
			str += "          <\/div>";
			str += "          <div class=\"cm-msg-button\">";
			str += "            <ul>";
			str += btn_obj;
			str += "            <\/ul>";
			str += "          <\/div>";
			str += "        <\/div>";
			$(".chat-logs").append(str);
			$("#cm-msg-" + INDEX).hide().fadeIn(300);
			$(".chat-logs").stop().animate({
				scrollTop : $(".chat-logs")[0].scrollHeight
			}, 1000);
			$("#chat-input").attr("disabled", true);
		}

		$(document).delegate(".chat-btn", "click", function() {
			var value = $(this).attr("chat-value");
			var name = $(this).html();
			$("#chat-input").attr("disabled", false);
			generate_message(name, 'self');
		})

		$("#chat-circle").click(function() {
			$("#chat-circle").toggle('scale');
			$(".chat-box").toggle('scale');
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		})

		$(".chat-box-toggle").click(function() {
			$("#chat-circle").toggle('scale');
			$(".chat-box").toggle('scale');
		})

		var userName = '${memberVo.memberName}';
	    var friend = 'EMP00000';
	    var host = window.location.host;
	    var path = window.location.pathname;
	    var webCtx = path.substring(0, path.indexOf('/', 1));
	    var endPointURL = "ws://" + window.location.host + webCtx + "/FriendWS/" + userName;
			
		var messagesArea = document.getElementById("chat-logs");
		var self = userName;
		var webSocket;
		
		function connect() {
			if (!userName) {
				return;
			}
			// create a websocket
			webSocket = new WebSocket(endPointURL);

			webSocket.onopen = function(event) {
				console.log("Connect Success!");
			};

			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("open" === jsonObj.type) {
// 					refreshFriendList(jsonObj);
				} else if ("history" === jsonObj.type) {
					messagesArea.innerHTML = '';
					// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
					var messages = JSON.parse(jsonObj.message);
					for (var i = 0; i < messages.length; i++) {
						var historyData = JSON.parse(messages[i]);
						var showMsg = historyData.message;
						
						// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
						generate_message(showMsg, historyData.sender === self ? 'self' : 'user');
					}
					messagesArea.scrollTop = messagesArea.scrollHeight;
				} else if ("chat" === jsonObj.type) {
					generate_message(jsonObj.message, jsonObj.sender === self ? 'self' : 'user');
					messagesArea.scrollTop = messagesArea.scrollHeight;
				} else if ("close" === jsonObj.type) {
// 					refreshFriendList(jsonObj);
				}
				
			};

			webSocket.onclose = function(event) {
				console.log("Disconnected!");
			};
		}
		
		function sendMessage() {
			var inputMessage = document.getElementById("chat-input");
			var message = inputMessage.value.trim();

			if (message === "") {
				alert("Input a message");
				inputMessage.focus();
			} else if (friend === "") {
				alert("Choose a friend");
			} else {
				var jsonObj = {
					"type" : "chat",
					"sender" : self,
					"receiver" : friend,
					"message" : message
				};
				webSocket.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
			}
		}
		
		function disconnect() {
			webSocket.close();
		}
		
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
	</script>
</body>
</html>