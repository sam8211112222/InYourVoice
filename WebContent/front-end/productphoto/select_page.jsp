<%@page import="java.util.List"%>
<%@page import="com.productphoto.model.ProductPhotoVO"%>
<<<<<<< HEAD
=======
<%@page import="com.productphoto.model.ProductPhotoService"%>
>>>>>>> sam
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>ProductPhoto: Home</title>

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
   <tr><td><h3>商品照片首頁</h3><img src="<%=request.getContextPath()%>/images/pic3.jpg" width="400" height="250" border="0"></td></tr>
</table>

<p>這是商品照片首頁</p>

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
  <li><a href='<%=request.getContextPath()%>/front-end/productphoto/listAllProductPhoto.jsp'><b>所有照片資料</b> </a></li>
  
  <jsp:useBean id="productPhotoSvc" scope="page" class="com.productphoto.model.ProductPhotoService" />
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" >
        <b>選擇照片編號:</b>
        <select size="1" name="productphoto_id">
         <c:forEach var="productPhotoVO" items="${productPhotoSvc.all}"> 
           <option value="${productPhotoVO.productphoto_id}">${productPhotoVO.productphoto_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">       
    </FORM>
  </li>
 
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" >
       <b>選擇產品編號:</b>
       <select size="1" name="productphoto_id">
         <c:forEach var="productPhotoVO" items="${productPhotoSvc.all}"> 
           <option value="${productPhotoVO.productphoto_id}">${productPhotoVO.product_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
</ul>

<h3>商品管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/productphoto/addProductPhoto_input.jsp'><b>新增商品照片</b></a></li>
</ul>


</body>
</html>