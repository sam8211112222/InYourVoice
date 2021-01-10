<%@page import="java.util.List"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>樂團商品首頁</title>

<style>
  div#table-1 {
	width: 330px;
	background-color: #dee2e6;
	font-size: 40px;
	margin-top: 10px;
	margin-bottom: 10px;
	padding: 10px 20px;
    border: 5px groove gray;
    height: 50px;
    text-align: center;
    margin:0px auto;
    color: #4f5d75;
  }
  
  button#searchTable{
  margin-right:8px;
  margin-left:8px;
  background-color: #dee2e6;
  border: none;
  color: #4f5d75;
  font-size: 25px;
  padding: 15px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  border-radius: 15px;;
  width:190px;
  height:60px;
  }
  
  button#searchTable:hover{
  margin-right:8px;
  margin-left:8px;
  background-color: #3d5a80;
  border: none;
  color: #f8f9fa;
  font-size: 25px;
  padding: 15px 15px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  border-radius: 15px;;
  width:190px;
  height:60px;
  }

</style>

</head>
<body bgcolor='#f8f9fa'>

<div align="center" style="position:relative" id="table-1">
   樂團商品首頁
</div>

<div align="center" style="position:relative;top:10px;">
<a href='<%=request.getContextPath()%>/front-end/product/listAllProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/listAllProduct.jsp')">列出全部商品</button></a>
<a href='<%=request.getContextPath()%>/front-end/product/addProduct.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/addProduct.jsp')">新增商品</button></a>
<a href='<%=request.getContextPath()%>/front-end/orders/listAllOrdersBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/orders/listAllOrdersBandView.jsp')">列出商品訂單</button></a>
<a href='<%=request.getContextPath()%>/front-end/orders/listAllTicketBandView.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/orders/listAllTicketBandView.jsp')">列出票卷訂單</button></a>
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

</body>
</html>