<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="com.product.model.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO"); //ProductServlet.java(Controller), 存入req的empVO物件
%>

<html>
<head>
<title>商品資料 - listOneProduct.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>商品資料 - listOneProduct.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/front-end/product/select_page.jsp"><img src="<%=request.getContextPath()%>/images/pic.jpg" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
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
		<th>修改</th>
	</tr>
	<tr>
		<td>${productVO.product_id}</td>
		<td>${productVO.band_id}</td>
		<td>${productVO.product_type}</td>
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
		<td><fmt:formatDate value="${productVO.product_add_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${productVO.product_discount}</td>
		<td><fmt:formatDate value="${productVO.product_discount_on_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${productVO.product_discount_off_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${productVO.product_last_edit_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${productVO.product_last_editor}</td>
		<td>
		  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" style="margin-bottom: 0px;">
		     <input type="submit" value="修改">
		     <input type="hidden" name="product_id"  value="${productVO.product_id}">
		     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
		</td>
	</tr>

</table>
</body>
</html>