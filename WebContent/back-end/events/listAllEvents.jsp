<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    EventService eventSvc = new EventService();
    List<EventVO> list = eventSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%-- <jsp:useBean id="eventSvc" scope="page" class="com.event.model.EventService" /> --%>

<html>
<head>
<title>所有活動資料 - listAllEvents.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 900px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  img{
  width: 100px;
  height: 100px;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有活動資料 - listAllEvents.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/events/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>活動編號</th>
		<th>樂團編號</th>
		<th>活動類型</th>
		<th>活動排序</th>
		<th>活動標題</th>
		<th>活動詳情</th>
		<th>活動海報</th>
		<th>活動區域</th>
		<th>活動場地</th>
		<th>活動縣市</th>
		<th>活動縣市分區</th>
		<th>活動地址</th>
		<th>活動開始時間</th>
		<th>活動上架時間</th>
		<th>最後修改時間</th>
		<th>最後修改者</th>
		<th>活動狀態</th>
		<th>座位圖</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="eventVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${eventVO.event_id}</td>
			<td>${eventVO.band_id}</td>
			<td>${eventVO.event_type}</td>
			<td>${eventVO.event_sort}</td>
			<td>${eventVO.event_title}</td>
			<td>${eventVO.event_detail}</td>
			<td><img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
			<td>${eventVO.event_area}</td>
			<td>${eventVO.event_place}</td>
			<td>${eventVO.event_city}</td>
			<td>${eventVO.event_cityarea}</td>
			<td>${eventVO.event_address}</td>
			<td>${eventVO.event_start_time}</td>
			<td>${eventVO.event_on_time}</td>
			<td>${eventVO.event_last_edit_time}</td>
			<td>${eventVO.event_last_editor}</td>
			<td>${eventVO.event_status}</td>
			<td><img src="<%=request.getContextPath()%>/EventPicController?action=getEventSeat&event_id=${eventVO.event_id}"></td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="event_id"  value="${eventVO.event_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="event_id"  value="${eventVO.event_id}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

</body>
</html>