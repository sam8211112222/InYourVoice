<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單列表</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/front_end/orders/select_page.jsp">回首頁</a>
	</br>
	
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
				<th align="left">買家編號</th>
				<th align="left">訂單狀態</th>
				<th align="left">下單時間</th>
				<th align="left">買家名稱</th>
				<th align="left">買家信箱</th>
				<th align="left">買家電話</th>
				<th align="left">運送時間</th>
				<th align="left">收到時間</th>
				<th align="left">訂單詳情</th>
				<th align="left">修改</th>
				<th align="left">刪除</th>

			</tr><jsp:useBean id="orders" scope="page" class="com.orders.model.OrdersService" />
			<c:forEach items="${orders.all2}" var="vo">

				<tr>
					<td align="left">${vo.order_id}</td>
					<td align="left">${vo.member_id}</td>

					<td><c:choose>
							<c:when test="${vo.order_status==0}">
						未處理
						</c:when>
							<c:when test="${vo.order_status==1}">
						已出貨
						</c:when>
							<c:when test="${vo.order_status==2}">
						未結帳
						</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose></td>
					
					<td align="left"><fmt:formatDate
							value="${vo.order_place_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
					<td align="left">${vo.order_name}</td>
					<td align="left">${vo.order_mail}</td>
					<td align="left">${vo.order_phone}</td>
					<td align="left"><fmt:formatDate
							value="${vo.order_delivery_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
					<td align="left"><fmt:formatDate
							value="${vo.order_received_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
					<td align="left"><a
						href="${pageContext.request.contextPath}/orderList/orderListServlet?id=${vo.order_id}">點此查看</a></td>
					<td align="left">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/orders/ordersServlet"
							style="margin-bottom: 0px;">
							<input type="submit" value="修改"> <input type="hidden"
								name="order_id" value="${vo.order_id}"> <input
								type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</td>
					<td align="left">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/orders/ordersServlet"
							style="margin-bottom: 0px;">
							<input type="submit" value="刪除"> <input type="hidden"
								name="delete" value="${vo.order_id}"> <input
								type="hidden" name="action" value="delete">
						</FORM>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</Table>

</body>
</html>