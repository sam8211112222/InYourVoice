<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>  
<%@ page import="com.eventorderlist.model.*"%>  
<%@ page import="com.ticket.model.*"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	EventOrderListService evoSvc = new EventOrderListService();
    List<EventOrderListVO> list = evoSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<html>
<head>
<title>票卷訂單資料</title>
<link href="<%=request.getContextPath()%>/css/product/product.css" rel="stylesheet" type="text/css">
<link
 href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css"
 rel="stylesheet" type="text/css">
<link
 href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
 rel="stylesheet">

<!-- Custom styles for this template -->
<link
 href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css"
 rel="stylesheet">

<!-- Custom styles for this page -->
<link
 href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css"
 rel="stylesheet">
  <style>
	.nowrap {
	   white-space:nowrap;
	}
	img.poster{
		width:200px;
	}
	img.seat{
		width:100px;
	}
 </style>
</head>
<body bgcolor='white'>
<%@ include file="/back-end/sb/page1.file" %>
<div align="center" style="position:relative" id="table-1">
	票卷訂單資料 
</div>

<!-- <div align="center" id="select"> -->
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/select_page.jsp')">回後台商品首頁</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductManagement.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllProductManagement.jsp')">列出全部商品</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp')">商品審核</button></a> --%>
<!-- </div> -->
<!-- <div align="center" id="select"> -->
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp')">列出商品訂單</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp')">列出商品照片</button></a> --%>
<!-- </div> -->

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>


<div class="table" align="center">
<table>
	<%@ include file="page1.file" %> 
	<c:forEach var="eVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<tr>
		<jsp:useBean id="TicketSvc" scope="page"
				class="com.ticket.model.TicketService" />
		<th>票券訂單編號</th>
		<th>活動編號</th>
		<th>活動票券編號</th>
		<th>票種張數</th>
		<th>單張金額</th>
		<th>總金額</th>
		<th>備註</th>
	</tr>
		<tr>
			<td>${eVO.orderlist_id}</td>
			<td>${eVO.ticket_id}</td>
			<td>${eVO.event_order_id}</td>
			<td>${eVO.orderlist_goods_amount}</td>		
			<td>${TicketSvc.getOneTicket(eVO.ticket_id).ticket_price}</td>
			<td>${eVO.orderlist_goods_amount*TicketSvc.getOneTicket(eVO.ticket_id).ticket_price }</td>
			<td>${eVO.orderlist_remarks}</td>		
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</div>
<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<%@ include file="page2.file" %>
<%@ include file="/back-end/sb/page2.file" %>
</body>
</html>