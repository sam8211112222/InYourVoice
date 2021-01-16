<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="java.util.*"%>
<%
	EmpVO empVO = (EmpVO) session.getAttribute("empVO");
	BandVO bandVO = (BandVO) request.getAttribute("bandVO");
	MemberService memberSvc = new MemberService();
	MemberVo memberVo = memberSvc.findByBandId(bandVO.getBand_id());
	pageContext.setAttribute("bandVO", bandVO);
	pageContext.setAttribute("memberVo", memberVo);
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<style>
#content {
	text-align: center;
}

#td1 {
	margin-top: 80px;
}

th.texttiltle {
	width: 250px;
}

td.tdtext {
	width: 400px;
}

.borderbox {
	border: 2px solid;
	width: 100%;
	height: 300px;
	border-radius: 20px;
	overflow: hidden;
}

img {
	width: 100%;
	height: 100%;
}

.pictitle {
	float: left;
	margin-left: 30px;
}

audio {
	outline: none;
}

.btn {
	margin-top: 10px;
	margin-bottom: 10px;
}

#success {
	margin-right: 10px;
	width: 100px;
}

#rollback {
	margin-left: 10px;
	width: 100px;
}
</style>
</head>

<body onload="connect();">
	<div class="container-fluid" id="content">
		<h1>
			樂團審核表單<span class="badge badge-secondary"><i
				class="fas fa-music"></i></span>
		</h1>
		<table class="table table-borderless" id="td1">
			<thead>
				<tr>
					<th scope="col" class="texttiltle"></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row" class="texttiltle">申請會員帳號</th>
					<td class="tdtext">${memberVo.memberAccount}</td>
				</tr>
				<tr>
					<th scope="row" class="texttiltle">申請人</th>
					<td class="tdtext">${memberVo.memberName}</td>
				</tr>
				<tr>
					<th scope="row" class="texttiltle">樂團名稱</th>
					<td class="tdtext">${bandVO.band_name}</td>
				</tr>
				<tr>
					<th scope="row" class="texttiltle">樂團介紹</th>
					<td class="tdtext">${bandVO.band_intro}</td>
				</tr>
				<tr>
					<th scope="row" class="texttiltle">音樂作品試聽</th>
					<td class="tdtext"><audio controls>
							<source
								src="<%=request.getContextPath() %>/band/band.do?action=getSong&bandId=${bandVO.band_id}"
								type="audio/ogg">
						</audio></td>
				</tr>
			</tbody>
		</table>
		<h4 class="pictitle">主頁橫幅</h4>
		<div class="borderbox">
			<img
				src="<%=request.getContextPath() %>/band/band.do?action=getBanner&bandId=${bandVO.band_id}"
				alt="">
		</div>
		<div class="form-group">
			<label for="exampleFormControlTextarea1"
				style="font-weight: 600; font-size: 24px">審核內容回覆</label>
			<textarea class="form-control" id="replyArea" rows="3"></textarea>
		</div>
		<div class="btn">
			<button type="button" class="btn btn-outline-success" id="success">審核通過</button>
			<button type="button" class="btn btn-outline-danger" id="return">退回</button>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>
	<script defer
		src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
	<script>
	
    let successBtn = document.getElementById("success");
    successBtn.addEventListener("click",function(){
    	
    	var yes = confirm('確定通過審核嗎？');
    	if (yes) {
    		let obj={
    				action : "updateStatus",
    				bandId : "${bandVO.band_id}",
    				bandLastEditor : "${empVO.emp_id}",
    				bandStatus : "1",
    		}
    		$.ajax({
				type : "POST",
				url : "/TEA102G6/band/band.do",
				data : obj,
				success : function(result) {
					sendMessage();
					sendMessageNoti();
				},
				error : function(err) {
					alert("系統錯誤");
				}
			});
    	} else {
    	    alert('你按了取消按鈕');
    	}        
		})

     let returnBtn = document.getElementById("return");
    returnBtn.addEventListener("click",function(){
    	var yes = confirm('確定退回審核嗎？');
    	if (yes) {
    		let obj={
    				action : "updateStatus",
    				bandId : "${bandVO.band_id}",
    				bandLastEditor : "${empVO.emp_id}",
    				bandStatus : "3",
    		}
    		$.ajax({
				type : "POST",
				url : "/TEA102G6/band/band.do",
				data : obj,
				success : function(result) {
					sendMessage2();
				},
				error : function(err) {
					alert("系統錯誤");
				}
			});
    	} else {
    	    alert('你按了取消按鈕');
    	}        
		})
		var MyPoint = "/notification/{${empVO.emp_id}}";
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

				console.log(jsonObj);
			}
		}
		function sendMessage() {

			var jsonObj = {
				title : "審核成功",
				content : $("#replyArea").val(),
				sender : "${empVO.emp_id}",
				isRead : false,
				sendTime : new Date().getTime(),
				receiver : "${memberVo.memberId}",
				link:"<%=request.getContextPath()%>/band/band.do?action=bandreply&memberId=${memberVo.memberId}",
				type : "audit",
			};
			webSocket.send(JSON.stringify(jsonObj));
			window.location.replace("<%=request.getContextPath()%>/back-end/band/protect/bandList.jsp");
		}
		function sendMessage2() {
			
			var jsonObj = {
				title : "審核退回",
				content : $("#replyArea").val(),
				sender : "${empVO.emp_id}",
				isRead : false,
				sendTime : new Date().getTime(),
				receiver : "${memberVo.memberId}",
				link:"<%=request.getContextPath()%>/band/band.do?action=bandreply&memberId=${memberVo.memberId}",
				type : "audit",
			};
			webSocket.send(JSON.stringify(jsonObj));
			window.location.replace("<%=request.getContextPath()%>/back-end/band/protect/bandList.jsp");
		}
			function sendMessageNoti() {
			
			var jsonObj = {
				title : "${bandVO.band_name} 已經成立囉 趕快來去追蹤!!",
				content :"${bandVO.band_name}已經上線囉 !!",
				sender : "${empVO.emp_id}",
				isRead : false,
				sendTime : new Date().getTime(),
				link:"<%=request.getContextPath()%>/band/band.do?action=getBandMain&band_id=${bandVO.band_id}",
				type : "newband",
			};
			webSocket.send(JSON.stringify(jsonObj));
		}
	</script>
</body>

</html>