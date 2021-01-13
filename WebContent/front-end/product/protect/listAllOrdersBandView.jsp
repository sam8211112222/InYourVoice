<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>  
<%@ page import="com.orderlist.model.*"%> 
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
	ProductService ProductSvc = new ProductService();
    List<OrderListVO> list = ProductSvc.getOrder(memberVo.getBandId());
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>依樂團列出商品訂單資料 </title>
<link href="<%=request.getContextPath()%>/css/product/product.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor='white'>
<%@ include file="/front-end/header_footer/header.jsp"%>
<%@ include file="/css/member/member_center_top.file"%>
<div id="table-1">
	列出商品訂單
</div>

<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp')">回商品首頁</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp')">列出全部商品</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp')">新增商品</button></a>
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

<div class="table" >
<table width = "100%">
	<tr>
		<th>訂單明細編號</th>
		<th>訂單編號</th>
		<th>商品編號</th>
		<th>商品數量</th>
		<th>備註</th>
		<th>評分星等</th>
		<th>評語</th>
		<th>評價時間</th>
		<th>金額</th>			
	</tr>
	<%@ include file="page1.file" %> 
<%-- 	<jsp:useBean id="p" class="com.product.model.ProductVO"></jsp:useBean> --%>
<%-- 	<jsp:useBean id="o" class="com.orderlist.model.OrderListVO"></jsp:useBean> --%>
	<c:forEach var="ol" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${ol.orderlist_id}</td>
			<td>${ol.order_id}</td>
			<td>${ol.product_id}</td>
			<td>${ol.orderlist_goods_amount}</td>
			<td>${ol.orderlist_remarks}</td>
			<td>${ol.review_score}</td>
			<td>${ol.review_msg}</td>
			<td>${ol.review_time}</td>
			<td>${ol.price}</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</div>
<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>