<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<style type="text/css">
	.panel {
		float: right;
		border: 2px solid #0078ae;
		border-radius: 5px;
		width: 260px;
	}
	
	.message-area {
		height: 300px;
		resize: none;
		box-sizing: border-box;
		overflow: auto;
		background-color: #ffffff;
	}
	
	.input-area {
		background: #0078ae;
		box-shadow: inset 0 0 10px #00568c;
	}
	
	.input-area input {
		margin: 0.5em 0em 0.5em 0.5em;
	}
	
	.text-field {
		border: 1px solid grey;
		padding: 0.2em;
		box-shadow: 0 0 5px #000000;
	}

	#message {
		min-width: 50%;
		max-width: 60%;
	}
	
	.statusOutput {
		width: 60%;
		height: 36.79px;
		font-size: 19px;
		background: #0078ae;
		text-align: center;
		color: #ffffff;
		border: 1px solid grey;
		padding: 0.2em;
		box-shadow: 0 0 5px #000000;
		margin-bottom: 0;
	}
	
	#row {
		float: left;
		width: 50%;
		display: flex;
    	flex-wrap: wrap;
	}
	
	.column {
	  align-items: center;
	  justify-content: space-around;
	  float: left;
	  width: 80%;
	  padding: 5%;
	  margin-bottom: 5px;
	  background-color: #ffffff;
	}
	
	.column h2 {
		margin: 0;
	}
	
	ul#area{
	  list-style: none;
	  margin: 0;
	  padding: 0;
	}
	
	ul#area li{
	  display:inline-block;
	  clear: both;
	  padding: 7px 15px;
      border-radius: 20px;
	  margin-bottom: 2px;
	  font-family: Helvetica, Arial, sans-serif;
	}
	
	.friend{
	  background: #eee;
	  float: left;
	}
	
	.me{
	  float: right;
	  background: #0084ff;
	  color: #fff;
	}
	
	.friend + .me{
	  border-bottom-right-radius: 5px;
	}
	
	.me + .me{
	  border-top-right-radius: 5px;
	  border-bottom-right-radius: 5px;
	}
	
	.me:last-of-type {
	  border-bottom-right-radius: 30px;
	}
	
	.column {
		display: flex;
	}
	.column .fa-exclamation-circle {
		color: red;
		font-size: 20px;
		visibility: hidden;
	}
	.column .fa-exclamation-circle.show {
		visibility: visible;
	}
</style>
<title>客服</title>

<script
	src="<%=request.getContextPath()%>/back-end/events/ckeditor/ckeditor.js"></script>
<script
	src="<%=request.getContextPath()%>/back-end/events/js/jquery-3.5.1.min.js"></script>
<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css"
	rel="stylesheet">
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.full.js"></script>

</head>
<body onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sb/page1.file" %>
	<div id="row"></div>
	<div style="width: 260px;display: flex;flex-wrap: wrap;justify-content: center;float: right;margin-right: 60px">
		<h3 id="statusOutput" class="statusOutput"></h3>
		<div id="messagesArea" class="panel message-area" ></div>
		<div class="panel input-area">
			<input id="message" class="text-field" type="text" placeholder="回答內容" onkeydown="if (event.keyCode == 13) sendMessage();" /> 
			<input type="submit" id="sendMessage" class="button" value="送出" onclick="sendMessage();" /> 
		</div>		
	</div>
	<div style="clear: both;margin-bottom: 20px;"></div>
	<%@ include file="/back-end/sb/page2.file" %>
</body>
<script>
	var MyPoint = "/FriendWS/EMP00000";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var messagesArea = document.getElementById("messagesArea");
	var self = 'EMP00000';
	var webSocket;
	
	var friends;
	var friend;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			document.getElementById('sendMessage').disabled = false;
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("list" === jsonObj.type) {
				refreshFriendList(jsonObj);
			} else if ("history" === jsonObj.type) {
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var li = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = showMsg;
					ul.appendChild(li);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {
				if (jsonObj.sender === friend || jsonObj.sender === self) {
					var li = document.createElement('li');
					jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
					li.innerHTML = jsonObj.message;
					console.log(li);
					li && document.getElementById("area").appendChild(li);
					messagesArea.scrollTop = messagesArea.scrollHeight;					
				} else {
					if (!friends.includes(jsonObj.sender)) {
						friends.push(jsonObj.sender);
						var i = friends.length - 1;
						var temp = '<div id=' + i + ' class="column" name="friendName" value=' + jsonObj.sender + ' ><h2>' + jsonObj.sender + '</h2><i class="fas fa-exclamation-circle"></i></div>';
						$("#row").append(temp);
					}
					
					var index = friends.findIndex(function(item) {
						return item === jsonObj.sender;
					})
					
					$(".column .fa-exclamation-circle").eq(index).addClass("show");
				}	
			} else if ("open" === jsonObj.type) {
				// 
			} else if ("close" === jsonObj.type) {
				//
			}
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function sendMessage() {
		var inputMessage = document.getElementById("message");
		var friend = statusOutput.textContent;
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
	
	// 有好友上線或離線就更新列表>>有歷史訊息的人
	function refreshFriendList(jsonObj) {
		friends = jsonObj.chatPartners;
		var row = document.getElementById("row");
		row.innerHTML = '';
		for (var i = 0; i < friends.length; i++) {
			if (friends[i] === self) { continue; }
			row.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2><i class="fas fa-exclamation-circle"></i></div>';
		}
		addListener();
	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		var container = document.getElementById("row");
		container.addEventListener("click", function(e) {
			friend = e.srcElement.textContent;
			updateFriendName(friend);
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
			
			var index = friends.findIndex(function(item) {
				return item === friend;
			})
			
			$(".column .fa-exclamation-circle").eq(index).removeClass("show");
		});
	}
	
	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
	}
	
	function updateFriendName(name) {
		statusOutput.innerHTML = name;
	}
</script>
</html>