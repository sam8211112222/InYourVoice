<%@page import="java.util.List"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>後台商品首頁</title>
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
<body bgcolor='white'>
<%@ include file="/back-end/sb/page1.file" %>
<div align="center" style="position:relative" id="table-1">
   後台商品首頁
</div>

<div align="center" id="select">
<a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductManagement.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllProductManagement.jsp')">列出全部商品</button></a>
<a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp')">商品審核</button></a>
<a href='<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp')">列出商品訂單</button></a>
</div>
<div align="center" id="select">
<a href='<%=request.getContextPath()%>/back-end/product/protect/listAllTicketBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllTicketBandView.jsp')">列出票卷訂單</button></a>
<a href='<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp')">列出商品照片</button></a>
</div>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<%@ include file="/back-end/sb/page2.file" %>
</body>
</html>