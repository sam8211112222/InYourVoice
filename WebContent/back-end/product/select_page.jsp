<%@page import="java.util.List"%>
<%@page import="com.product.model.ProductVO"%>
<%@page import="com.product.model.ProductService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Product: Management</title>

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
   <tr><td><h3>後台商品首頁</h3><img src="<%=request.getContextPath()%>/images/nPkopIv.jpg" width="200" height="200" border="0"></td></tr>
</table>

<h3>這是後台商品首頁</h3>

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
  <li><a href='<%=request.getContextPath()%>/back-end/product/listAllProductManagement.jsp'><b>列出全部商品</b></a></li>
  
  <li><a href='<%=request.getContextPath()%>/back-end/product/listAllProductUnapproval.jsp'><b>列出待審核商品</b></a></li>
  
  <jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />
<!--   <li> -->
<%--     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" > --%>
<!--         <b>未審核商品:</b> -->
<!--         <select size="1" name="product_id"> -->
 
<%--          <c:forEach var="productVO" items="${productSvc.all}">  --%>
<%--          <c:if test="product_check_status==0"></c:if> --%>
<%--            <option value="${productVO.product_id}"> --%>
<%--            <c:if test="${productVO.product_check_status == 1}">${productVO.product_id}已審核</c:if> --%>
<%--            <c:if test="${productVO.product_check_status == 0}">${productVO.product_id}未審核</c:if> --%>
<!--            </option> -->
<%--          </c:forEach> --%>
          
<!--        </select> -->
<!--        <input type="hidden" name="action" value="getone_For_Display_Management"> -->
<!--        <input type="submit" value="送出">        -->
<!--     </FORM> -->
<!--   </li>  -->
</ul>

<h3>訂單管理:</h3>
<ul>
<li><a href='<%=request.getContextPath()%>/back-end/orders/listAllOrdersMView.jsp'><b>列出商品訂單</b></a></li>
<li><a href='<%=request.getContextPath()%>/back-end/orders/listAllTicketBandView.jsp'><b>列出票卷訂單</b></a></li>
<jsp:useBean id="orders" scope="page" class="com.orders.model.OrdersService" />
</ul>

</body>
</html>