<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Select Orders</title>
</head>
<body>
<FORM METHOD="get" ACTION="${pageContext.request.contextPath}/front-end/orders/addOrders.jsp" >
        
        <input type="hidden" name="action" value="insert">
        <input type="submit" value="新增訂單">
    </FORM>
<%-- <a href="${pageContext.request.contextPath}/orders/NewOrdersServlet">新增訂單</a></br> --%>
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
  <li><a href='${pageContext.request.contextPath}/front-end/orders/listAllOrders.jsp'>List</a> all Orders.  <br><br></li>
	
	<li>
    <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/orders/ordersServlet" >
        <b>輸入您的Email帳號:</b>
        <input type="text" name="order_mail">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

<%-- <jsp:useBean id="ordersSvc" scope="page" class="com.orders.model.OrdersService" /> --%>
   
<!--   <li> -->
<%--      <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/orders/ordersServlet" > --%>
<!--        <b>選擇訂單編號:</b> -->
<!--        <select size="1" name="order_id"> -->
<%--          <c:forEach var="ordersVO" items="${ordersSvc.all2}">  --%>
<%--           <option value="${ordersVO.order_id}">${ordersVO.order_id} --%>
<%--          </c:forEach>    --%>
<!--        </select> -->
<!--        <input type="hidden" name="action" value="getOne_For_Display"> -->
<!--        <input type="submit" value="送出"> -->
<!--     </FORM> -->
<!--   </li> -->
  
<!--   <li> -->
<%--      <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/orders/ordersServlet" > --%>
<!--        <b>選擇會員編號:</b> -->
<!--        <select size="1" name="order_id"> -->
<%--          <c:forEach var="ordersVO" items="${ordersSvc.all2}">  --%>
<%--           <option value="${ordersVO.member_id}">${ordersVO.member_id} --%>
<%--          </c:forEach>    --%>
<!--        </select> -->
<!--        <input type="hidden" name="action" value="getOne_For_Display"> -->
<!--        <input type="submit" value="送出"> -->
<!--     </FORM> -->
<!--   </li> -->
  
  
</ul>
</body>
</html>