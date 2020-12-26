<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="com.productphoto.model.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%
  ProductPhotoVO productPhotoVO = (ProductPhotoVO) request.getAttribute("productPhotoVO"); //ProductPhotoServlet.java(Controller), 存入req的ProductPhotoVO物件
%>

<html>
<head>
<title>商品照片資料 - listOneProductPhoto.jsp</title>

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
		 <h3>商品照片資料 - listOneProductPhoto.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/front-end/productphoto/select_page.jsp"><img src="<%=request.getContextPath()%>/images/pic2.jpg" width="200" height="200" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>照片編號</th>
		<th>商品編號</th>
		<th>照片檔案</th>
		<th>排序</th>
		<th>上傳時間</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<tr>
			<td>${productPhotoVO.productphoto_id}</td>
			<td>${productPhotoVO.product_id}</td>			
			<td><img src="<%=request.getContextPath()%>/ShowProductPhoto?productphoto_id=${productPhotoVO.productphoto_id}" height="120" width="160"></img></td>
			<td>${productPhotoVO.productphoto_sort}</td>
			<td><fmt:formatDate value="${productPhotoVO.productphoto_add_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			
		<td>
		 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="productphoto_id"  value="${productPhotoVO.productphoto_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
		</td>
		<td>
		  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" style="margin-bottom: 0px;">
		     <input type="submit" value="刪除">
		     <input type="hidden" name="productphoto_id"  value="${productphotoVO.productphoto_id}">
		     <input type="hidden" name="action" value="delete"></FORM>
		</td>
	</tr>
</table>

</body>
</html>