<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	ProductService ProductSvc = new ProductService();
    List<ProductVO> list = ProductSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有商品資料 - listAllProductManagement.jsp</title>
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
	width: 800px;
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

<<<<<<< HEAD

=======
>>>>>>> sam
<table id="table-1">
	<tr><td>
		 <h3>所有商品資料 - listAllProductManagement.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/product/select_page.jsp"><img src="<%=request.getContextPath()%>/images/pic.jpg" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

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
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
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
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="product_id"  value="${productVO.product_id}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
</body>
</html>