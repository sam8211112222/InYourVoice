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
div.lea-img{
	width: 200px;
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
		<div class="leatitle-all">					

			<div class="lea-time"></div>
		</div>
		<div class="lea-song-list">
						
			<c:forEach var="eventVO" items="${eventSvc.getEventByName(name)}"> 

				<div class="song-line">
					<div class="num"></div>
					<div class="lea-img" style="cursor:pointer;" onclick="location.href='<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}';">
						<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" alt="">

						<div class="in-play" style="display: none;">
							<i class="far fa-play-circle img-play"></i>
						</div>
					</div>
					<div class="song-name">
						${eventVO.event_title}<br> 

					</div>
						<div class="add">
							<i class="fas fa-plus"></i>
							<input type="hidden" class="favId" value="${eventVO.event_id}">
						</div>
<!-- 						<div class="delete"> -->
<!-- 							<i class="fas fa-minus"></i> -->
<%-- 							<input type="hidden" class="favId" value="${eventVO.event_id}"> --%>
<!-- 						</div> -->
					</div>
				<hr class="songlist-hr">
			</c:forEach>
		</div>
	</div>
		
	<%@include file="/front-end/header_footer/footer.jsp" %>
	
	
	<script>
		let data = {
		    action:"getAllMeberfav",
		    memberid :"${memberVo.memberId}",
	  	};
		$.ajax({
	        type: "POST",
	        url: "<%=request.getContextPath()%>/FavoritesServlet",
	        data:data,
	        success: function (data) {
	           	console.log(data);
	        },
			error : function(err) { 
				console.log(err);
			}
		});
	
		$(".add").click(function(){
		  let eventid = $(this).find("input.favId")[0].value;
		  let memberid = "${memberVo.memberId}";
		  
		  let data = {
				  action:"addfav",
				  favid : eventid,
				  memberid : memberid,
				  type : "0",
		  };
		  $.ajax({
              type: "POST",
              url: "<%=request.getContextPath()%>/FavoritesServlet",
              data:data,
              success: function (data) {
                 	alert("新增成功");
              },
				error : function(err) {
					alert("請登入");
				}
			});
		})
		$(".delete").click(function(){
		  let eventid = $(this).find("input.favId")[0].value;
		  let memberid = "${memberVo.memberId}";
		  
		  let data = {
				  action:"deletefav17",
				  favid : eventid,
				  memberid : memberid,
		  };
		  $.ajax({
              type: "POST",
              url: "<%=request.getContextPath()%>/FavoritesServlet",
              data:data,
              success: function (data) {
                 	alert("移除成功");
              },
				error : function(err) {
					alert("請登入");
				}
			});
		})
	</script>	
</body>
</html>