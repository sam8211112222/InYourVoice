<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>  
<%@ page import="com.ticket.model.*"%>  
<%@ page import="com.eventorderlist.model.*"%>  
<%@ page import="com.product.model.*"%> 
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	ProductService ProductSvc = new ProductService();
    List<EventOrderListVO> list = ProductSvc.getEOrder(memberVo.getBandId());
    pageContext.setAttribute("list",list);
    TicketVO ticketVO = (TicketVO) request.getAttribute("ticketVO");
%>
<html>
<head>
<title>票卷訂單資料</title>
<link href="<%=request.getContextPath()%>/css/product/product.css" rel="stylesheet" type="text/css">
</head>
<body>
<%-- <%@ include file="/front-end/header_footer/header.jsp"%> --%>
<%@ include file="/css/member/member_center_top.file"%>
<div align="center" style="position:relative" id="table-1">
	票卷訂單資料 
</div>

<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp')">回商品首頁</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp')">列出全部商品</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/addProduct.jsp')">新增商品</button></a>
</div>
<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp')">列出商品訂單</button></a>
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

<div class="table" align="center">
<table>
	<%@ include file="page1.file" %> 
	<c:forEach var="eVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
<%-- 	<c:forEach var="tVO" items="${list2}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">	 --%>
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
<%-- 	</c:forEach> --%>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</div>
<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>