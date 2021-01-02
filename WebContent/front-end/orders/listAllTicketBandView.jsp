<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ticket.model.*"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	TicketService TicketSvc = new TicketService();
    List<TicketVO> list = TicketSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<html>
<head>
<meta charset="BIG5">
<title>listAllTicketBandView.jsp</title>
</head>
<body>


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
		<th>活動票券編號</th>
		<th>活動編號</th>
		<th>票種排序</th>
		<th>活動票種名稱</th>
		<th>票種張數</th>
		<th>單張金額</th>
		<th>售出時間</th>
		
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="ticketVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${ticketVO.ticket_id}</td>
			<td>${ticketVO.event_id}</td>
			<td>${ticketVO.ticket_sort}</td>
			<td>${ticketVO.ticket_name}</td>
			<td>${ticketVO.ticket_amount}</td>
			<td>${ticketVO.ticket_price}</td>
			<td>${productVO.ticket_onsale_time}</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>