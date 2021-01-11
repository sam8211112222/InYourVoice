<%@page import="java.util.List"%>
<%@page import="com.event.model.EventVO"%>
<%@page import="com.event.model.EventService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="eventSvc" scope="page" class="com.event.model.EventService"></jsp:useBean>   


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Query_event</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homepage.css" />
</head>

<style>

.view-event-btn {
    border-radius: 30px;
    padding: 10px;
    background-color: #f1c29d;
    margin-left: 19px;
}

</style>

<body>

	<%@include file="/front-end/header_footer/header.jsp" %>
	
		<%
			List<EventVO> list = eventSvc.getAll();
			pageContext.setAttribute("list", list);
		%>


	<div class="title-song wrap">
		<h2 class="wrap-title"><i class="fas fa-search"></i>【關鍵字】搜尋結果</h2>
		<a href="<%= request.getContextPath() %>/album/album.do?action=searchName&search=${name}"><button class="view-event-btn">歌曲</button></a>
		<a href="<%= request.getContextPath() %>/product/product.do?action=searchName&search=${name}"><button class="view-event-btn">商品</button></a>
		<a href="<%= request.getContextPath() %>/event/EventServlet?action=searchName&search=${name}"><button class="view-event-btn">活動</button></a>
		<a href="<%= request.getContextPath() %>/band/band.do?action=searchName&search=${name}"><button class="view-event-btn">樂團</button></a>
		<div class="leatitle-all">					

			<div class="lea-time"></div>
		</div>
		<div class="lea-song-list">
						
			<c:forEach var="eventVO" items="${eventSvc.getEventByName(name)}"> 

				<div class="song-line">
					<div class="num"></div>
					<div class="lea-img">
						<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" alt="">

						<div class="in-play" style="display: none;">
							<i class="far fa-play-circle img-play"></i>
						</div>
					</div>
					<div class="song-name">
						${eventVO.event_title}<br> <span>${eventVO.event_detail}</span>
					</div>
					<div class="add">
						<i class="fas fa-plus"></i>
					</div>
				</div>
				<hr class="songlist-hr">

			</c:forEach>
			
		</div>
	</div>
	
	
	<%@include file="/front-end/header_footer/footer.jsp" %>
	
</body>
</html>