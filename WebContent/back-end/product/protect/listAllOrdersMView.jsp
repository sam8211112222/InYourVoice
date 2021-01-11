<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品訂單資料</title>
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
 </style>
</head>
<body>
<%@ include file="/back-end/sb/page1.file" %>
<div align="center" style="position:relative" id="table-1">
	商品訂單資料 
</div>

<div align="center" id="select">
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/select_page.jsp')">回後台商品首頁</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductManagement.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/listAllProductManagement.jsp')">列出全部商品</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/listAllProductUnapproval.jsp')">商品審核</button></a> --%>
<!-- </div> -->
<!-- <div align="center" id="select"> -->
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllTicketBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/orders/listAllTicketBandView.jsp')">列出票卷訂單</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp')">列出商品照片</button></a> --%>
</div>


	<div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered nowrap"  id="dataTable" cellspacing="0">
                <thead>
					<tr>
						<!-- 				<th align="left">訂單編號</th> -->
						<!-- 				<th align="left">商品名稱</th> -->
						<!-- 				<th align="left">賣家名稱</th> -->
						<!-- 				<th align="left">訂單時間</th> -->
						<!-- 				<th align="left">金額</th> -->
						<!-- 				<th align="left">狀態</th> -->
		
						<th align="left">訂單編號</th>
						<th align="left">買家編號</th>
						<th align="left">訂單狀態</th>
						<th align="left">下單時間</th>
						<th align="left">買家名稱</th>
						<th align="left">買家信箱</th>
						<th align="left">買家電話</th>
						<th align="left">運送時間</th>
						<th align="left">收到時間</th>
						<th align="left">訂單詳情</th>
		
					</tr><jsp:useBean id="orders" scope="page" class="com.orders.model.OrdersService" />
				<thead>
				<tbody>
				<c:forEach items="${orders.all2}" var="vo">
					<tr>
						<td align="left">${vo.order_id}</td>
						<td align="left">${vo.member_id}</td>
	
						<td><c:choose>
								<c:when test="${vo.order_status==0}">
							未處理
							</c:when>
								<c:when test="${vo.order_status==1}">
							已出貨
							</c:when>
								<c:when test="${vo.order_status==2}">
							未結帳
							</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose></td>
						
						<td align="left"><fmt:formatDate
								value="${vo.order_place_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td align="left">${vo.order_name}</td>
						<td align="left">${vo.order_mail}</td>
						<td align="left">${vo.order_phone}</td>
						<td align="left"><fmt:formatDate
								value="${vo.order_delivery_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td align="left"><fmt:formatDate
								value="${vo.order_received_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td align="left"><a
							href="${pageContext.request.contextPath}/orderList/orderListServletB?id=${vo.order_id}">點此查看</a></td>
					</tr>
				</c:forEach>
				</tbody>
	</Table>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<%@ include file="/back-end/sb/page2.file" %>
</body>
</html>