<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的訂單</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<style type="text/css">
div.form1 {
	border: 1px solid black;
	border-radius: 30px;
}


</style>

</head>


<body>
	<div class="form1">
		<div class=from2>
			<h1>我的訂單</h1>
			<ul class="nav nav-tabs">
				<li class="nav-item"><a class="nav-link active" href="#">處理中</a></li>
				<li class="nav-item"><a class="nav-link" href="#">已完成訂單</a></li>
			</ul>
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th scope="col">訂單編號</th>
					<th scope="col">訂單時間</th>
					<th scope="col">金額</th>
					<th scope="col">狀態</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<jsp:useBean id="orders" scope="page"
					class="com.orders.model.OrdersService" />
				<c:forEach items="${orders.all2}" var="vo">
					<tr>
						<th scope="row">${vo.order_id}</th>
						<td><fmt:formatDate value="${vo.order_place_time}"
								pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td>金額</td>
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

						<td><a
							href="${pageContext.request.contextPath}/orderList/orderListServlet?id=${vo.order_id}"
							style="margin-bottom: 0px;">點我查看</a></td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>