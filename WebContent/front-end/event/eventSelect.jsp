<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.event.model.*"%>
<%@ page import="java.util.*"%>
<%
	if(session.getAttribute("list")==null){
		EventService eventSvc = new EventService();
		List<EventVO> list = eventSvc.getListEventOrderby();
		session.setAttribute("list", list);
	return;
	}else{
		session.getAttribute("list");
	}
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
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	padding: 10px;
}

div.main_content {
	width: 90%;
	margin: 0 auto;
	font-size: 0;
}

aside.aside {
	background-color: #ddd;
	width: 200px;
	height: 500px;
	display: inline-block;
	vertical-align: top;
	font-size: 1rem;
	margin-right: 10px;
	border: 1px solid #999;
}

main.main {
	background-color: #ddd;
	width: calc(100% - 200px - 10px);
	display: inline-block;
	vertical-align: top;
	font-size: 1rem;
	border: 1px solid #999;
}

@media screen and (max-width: 767px) {
	/* 針對 banner 區域 */
	div.banner_bg {
		min-width: auto;
	}

	/* 主內容區域 */
	div.main_content {
		width: 100%;
	}
	div.main_content aside.aside, div.main_content main.main {
		margin-top: 30px;
		width: 100%;
		display: block;
	}
}

/* Styles for wrapping the search box */
.main {
	width: 50%;
	margin: 50px auto;
}

/* Bootstrap 4 text input with search icon */
.has-search .form-control {
	padding-left: 2.375rem;
}

.has-search .form-control-feedback {
	position: absolute;
	z-index: 2;
	display: block;
	width: 2.375rem;
	height: 2.375rem;
	line-height: 2.375rem;
	text-align: center;
	pointer-events: none;
	color: #aaa;
}

.cardarea {
	border: 2px solid
}

img {
	height: 200px;
	border-radius: 5%;
}
</style>

</head>
<body>
	<div>
		<div class="main">
			<!-- Another variation with a button -->
			<div class="input-group">
				<input type="text" class="form-control"
					placeholder="關鍵字">
				<div class="input-group-append">
					<button class="btn btn-secondary" type="button">
						<i class="fas fa-search"></i>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="main_content">

		<aside class="aside">
			<h1>側邊欄區域</h1>
			<ul>
				<li>列表1</li>
				<li>列表2</li>
			</ul>
		</aside>

		<main class="main">
			<div class="row row-cols-1 row-cols-md-3">
				<c:forEach var="eventVO" items="${list}">
					<div class="col mb-4">
						<div class="card">
							<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top" alt="...">
							<div class="card-body">
								<button class="view-event-btn">檢視活動</button>
								<p class="card-text">${eventVO.event_title}</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</main>

	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>
	<script defer
		src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
</body>
</html>