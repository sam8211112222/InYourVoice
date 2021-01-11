<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>  
<%@ page import="com.productphoto.model.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	ProductService ProductSvc = new ProductService();
    List<ProductVO> list = ProductSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有商品資料</title>
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
<body bgcolor='white'>
<%@ include file="/back-end/sb/page1.file" %>

<div align="center" style="position:relative" id="table-1">
	所有商品資料 
</div>

<div align="center" id="select">
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/select_page.jsp')">回後台商品首頁</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllProductUnapproval.jsp')">商品審核</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllOrdersMView.jsp')">列出商品訂單</button></a> --%>
<!-- </div> -->
<!-- <div align="center" id="select"> -->
<%-- <a href='<%=request.getContextPath()%>/back-end/product/protect/listAllTicketBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/product/protect/listAllTicketBandView.jsp')">列出票卷訂單</button></a> --%>
<%-- <a href='<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/back-end/productphoto/listAllProductPhoto.jsp')">列出商品照片</button></a> --%>
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

	<%@ include file="page1.file" %> 
<div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered nowrap"  id="dataTable" cellspacing="0">
                <thead>
					<tr>
						<th>商品編號</th>
						<th>樂團編號</th>
						<th>商品分類</th>
						<th>商品名稱</th>
						<th>商品簡介</th>
						<th>商品詳細說明</th>
						<th>商品單價</th>
						<th>商品庫存量</th>
						<th>審核狀態</th>
						<th>上下架狀態</th>
						<th>預計上架時間</th>
						<th>預計下架時間</th>
						<th>提交時間</th>
						<th>折扣</th>
						<th>折扣開始時間</th>
						<th>折扣結束時間</th>
						<th>最後修改時間</th>
						<th>最後修改者</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td>${productVO.product_id}</td>
							<td>${productVO.band_id}</td>
							<td><c:choose>
							<c:when test="${productVO.product_type == 1}">
							  上衣
							</c:when>
							<c:when test="${productVO.product_type == 2}">
							  褲子
							</c:when>
							<c:otherwise>
							配件
							</c:otherwise>
							</c:choose></td>
							<td>${productVO.product_name}</td>
							<td>${productVO.product_intro}</td>
							<td>${productVO.product_detail}</td>
							<td>${productVO.product_price}</td>
							<td>${productVO.product_stock}</td>
							<td>
							<c:choose>
								<c:when test="${productVO.product_check_status == 1}">
								  已審核
								</c:when>
								<c:otherwise>
								未審核
								</c:otherwise>
								</c:choose>
							</td>		
							<td>
							<c:choose>
							<c:when test="${productVO.product_status == 1}">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" style="margin-bottom: 0px;">
							     已上架
							     <input type="submit" value="▼按此下架" id="submit2" class="shelf_btn">
							     <input type="hidden" name="product_id"  value="${productVO.product_id}">
							     <input type="hidden" name="action" value="dislaunch"></FORM>
							</c:when>
							<c:otherwise>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" style="margin-bottom: 0px;">
							      已下架
							     <input type="submit" value="▲按此上架" id="submit2" class="shelf_btn">
							     <input type="hidden" name="product_id"  value="${productVO.product_id}">
							     <input type="hidden" name="action" value="launch"></FORM>
							</c:otherwise>
							</c:choose>
							</td>
							<td><fmt:formatDate value="${productVO.product_on_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${productVO.product_off_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${productVO.product_add_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
							<td>${productVO.product_discount}</td>
							<td><fmt:formatDate value="${productVO.product_discount_on_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${productVO.product_discount_off_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${productVO.product_last_edit_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${productVO.product_last_editor}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<%@ include file="page2.file" %>
<%@ include file="/back-end/sb/page2.file" %>
<script>
	$(function(){
		$(document).on("click", ".shelf_btn", function(){
			alert("已更改上下架狀態");
		})
	})
</script>
</body>
</html>