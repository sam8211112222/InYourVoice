<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單詳情</title>
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
</head>
<body>
<%@ include file="/back-end/sb/page1.file" %>
<div align="center" style="position:relative" id="table-1">
	檢視訂單詳情 
</div>

<div align="center" id="select">
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp')">回商品訂單</button></a> --%>
</div>
<div style="width:80%; margin:0 auto;">
<table width="100%">
<tr>
<th>訂單編號:</th>
<th>下單時間:</th>
<th>買家名稱:</th>
<th>買家信箱:</th>
<th>買家電話:</th>
</tr>
<tr>
<td>${order.order_id}</td>
<td>${order.order_place_time}</td>
<td>${order.order_name}</td>
<td>${order.order_mail}</td>
<td>${order.order_phone}</td>
</tr>
</table>

<h4>訂單明細:</h4>
	<Table width="100%" border="1">
			<tr>
				<th align="left">商品編號</th>
				<th align="left">數量</th>
				<th align="left">評價</th>
				<th align="left">備註</th>
			</tr>
			<c:forEach items="${details}" var="detailVo">
				<tr>
					<td align="left">${detailVo.product_id}</td>
					<td align="left">${detailVo.orderlist_goods_amount}</td>
					<td align="left">${detailVo.review_msg}</td>
					<td align="left">${detailVo.orderlist_remarks}</td>
				</tr>
			</c:forEach>
	</Table>
</div>
<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script>
<%@ include file="/back-end/sb/page2.file" %>
</body>
</html>