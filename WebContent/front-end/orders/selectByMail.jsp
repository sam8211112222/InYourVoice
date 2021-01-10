<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.orders.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>訂單資料</h3>
	</FORM>
	<Table width="100%" border="1">
		<tbody>
			<tr>
				<!-- 				<th align="left">訂單編號</th> -->
				<!-- 				<th align="left">商品名稱</th> -->
				<!-- 				<th align="left">賣家名稱</th> -->
				<!-- 				<th align="left">訂單時間</th> -->
				<!-- 				<th align="left">金額</th> -->
				<!-- 				<th align="left">狀態</th> -->

				<th align="left">訂單編號</th>
				<th align="left">會員編號</th>
				<th align="left">訂單狀態</th>
				<th align="left">下單時間</th>
				<th align="left">買家名稱</th>
				<th align="left">買家信箱</th>
				<th align="left">買家電話</th>
				<th align="left">運送時間</th>
				<th align="left">收到時間</th>
				
			</tr>
			<a href="${pageContext.request.contextPath}/front_end/orders/select_page.jsp">回首頁</a>
			

			<c:forEach items="${orderMail}" var="ordersVO">
          
				<tr>
					<td align="left">${ordersVO.order_id}</td>
					<td align="left">${ordersVO.member_id}</td>
					<td align="left">${ordersVO.order_status eq 0 ?"未處理":"已出貨"}</td>
					<td align="left"><fmt:formatDate value="${ordersVO.order_place_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td align="left">${ordersVO.order_name}</td>
					<td align="left">${ordersVO.order_mail}</td>
					<td align="left">${ordersVO.order_phone}</td>
					<td align="left"><fmt:formatDate value="${ordersVO.order_delivery_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td align="left"><fmt:formatDate value="${ordersVO.order_received_time}" pattern="yyyy/MM/dd HH:mm:ss"/></td>

				</tr>
			</c:forEach>    
			
		</tbody>
	</Table>
</body>
</html>