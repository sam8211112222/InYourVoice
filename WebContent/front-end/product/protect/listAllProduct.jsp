<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>  
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
	ProductService ProductSvc = new ProductService();
    List<ProductVO> list = ProductSvc.getBand(memberVo.getBandId());
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有商品資料 </title>
<link href="<%=request.getContextPath()%>/css/product/product.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor='white'>
<%@ include file="/front-end/header_footer/header.jsp"%>
<%@ include file="/css/member/member_center_top.file"%>
<div id="table-1">
	所有商品資料 
</div>

<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp')">回商品首頁</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp')">新增商品</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp')">列出商品訂單</button></a>
</div>
<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/listAllTicketBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllTicketBandView.jsp')">列出票卷訂單</button></a>
<a href='<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp')">商品照片首頁</button></a>
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

<div class="table">
<table>

	<%@ include file="page1.file" %>
	<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	
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
		<th>折扣</th>
		<th>折扣開始時間</th>
		<th>折扣結束時間</th>
		<th>最後修改時間</th>
		<th>最後修改者</th>
		<th>修改</th>	
	</tr>
		<tr>
			<td>${productVO.product_id}</td>
			<td>${productVO.band_id}</td>
			<td>
			<c:choose>
			<c:when test="${productVO.product_type == 1}">
			  上衣
			</c:when>
			<c:when test="${productVO.product_type == 2}">
			  褲子
			</c:when>
			<c:otherwise>
			配件
			</c:otherwise>
			</c:choose>
			</td>
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
			待審核
			</c:otherwise>
			</c:choose>
			</td>
			<td>
			<c:choose>
			<c:when test="${productVO.product_status == 1}">
			  上架
			</c:when>
			<c:otherwise>
			下架
			</c:otherwise>
			</c:choose>
			</td>
			<td><fmt:formatDate value="${productVO.product_on_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${productVO.product_off_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${productVO.product_discount}</td>
			<td><fmt:formatDate value="${productVO.product_discount_on_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${productVO.product_discount_off_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${productVO.product_last_edit_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${productVO.product_last_editor}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" id="submit">
			     <input type="hidden" name="product_id"  value="${productVO.product_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</div>
<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>