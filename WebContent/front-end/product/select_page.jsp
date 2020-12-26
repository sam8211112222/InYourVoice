<%@page import="java.util.List"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Product: Band</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>樂團商品首頁</h3><img src="<%=request.getContextPath()%>/images/nPkopIv.jpg" width="200" height="200" border="0"></td></tr>
</table>

<h3>這是樂團商品首頁</h3>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/product/listAllProduct.jsp'><b>列出全部商品</b></a></li>
  
  <jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" >
        <b>以商品編號查詢:</b>
        <select size="1" name="product_id">
         <c:forEach var="productVO" items="${productSvc.all}"> 
           <option value="${productVO.product_id}">${productVO.product_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">       
    </FORM>
  </li> 
</ul>

<h3>商品管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/product/addProduct.jsp'><b>新增商品</b></a></li>
</ul>


</body>
</html>