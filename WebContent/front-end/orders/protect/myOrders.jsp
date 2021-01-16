<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.member.model.MemberVo,com.orders.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService"></jsp:useBean>
<% List<OrdersVO> orderList  = (List<OrdersVO>) request.getAttribute("orderList");
	pageContext.setAttribute("orderList", orderList); 
	
	
	%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的訂單</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css.map">

<style type="text/css">
body {
	background-color: #fafafa;
}

div.pd-wrap {
	background-color: white;
	border-color: #f3f3f3;
	margin: 10px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
}

a.btn-custom {
	font-weight: 300;
	width: 100%;
	max-width: 100%;
	color: white !important;
}

a.btn-color-primary:focus {
	border-width: 1px;
	border-style: solid color: white !important;
	border-color: #f9595f;
}

a.btn-color-primary:hover {
	background-color: #a5d489;
}

a.btn-color-primary {
	background-color: #f9595f;
}

.table td {
	vertical-align: middle; /** 设置垂直方向居中 */
	text-align: center;
}

.table th {
	vertical-align: middle; /** 设置垂直方向居中 */
	text-align: center;
}

hr.new3 {
	border: 1px solid #f9595f;
}

h2 {
	font-size: 35px;
}

li.nav-item {
	font-size: 20px;
}
div.pagination{

text-align:center;
margin:20px auto;
  width: 350px;
}
div.pagination2{

text-align:center;
margin:20px auto;
  width: 400px;
}
</style>

</head>
<body>
	<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	<%@ include file="/css/member/member_center_top.file"%>
	<div class="pd-wrap">
		<h2>我的訂單</h2>
		<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
		<div class="product-info-tabs">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item"><a class="nav-link active" id="description-tab" data-toggle="tab" href="#description" role="tab" aria-controls="description" aria-selected="true">商品訂單</a></li>
				<li class="nav-item"><a class="nav-link" id="review-tab" data-toggle="tab" href="#review" role="tab" aria-controls="review" aria-selected="false">票券訂單</a></li>
			</ul>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="description" role="tabpanel" aria-labelledby="description-tab">
					<table class="table table-hover">
						<thead>
						<tr><div class="pagination">

	</div></tr>
							<tr>
								<th scope="col" class="font">訂單編號</th>
								<th scope="col" class="font">訂單時間</th>
								<th scope="col" class="font">金額</th>
								<th scope="col" class="font">訂單狀態</th>
								<th scope="col" class="font"></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${orderList}" var="vo">
								<tr>
									<th scope="row">${vo.order_id}</th>
									<td class="font"><fmt:formatDate value="${vo.order_place_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
									<td class="font"><fmt:formatNumber value="${vo.total_price}" pattern="$#,###" /></td>
									<td class="font"><c:choose>
											<c:when test="${vo.order_status==0}"> 
	 						處理中 
 						</c:when>
											<c:when test="${vo.order_status==1}">
							已出貨
							</c:when>

											<c:otherwise>
											</c:otherwise>
										</c:choose></td>

									<td class="font"><a class="btn btn-custom btn-color-primary" href="${pageContext.request.contextPath}/orderList/orderListServlet?id=${vo.order_id}&totalPrice=${vo.total_price}"
										style="margin-bottom: 0px; width: 50%;">查閱</a></td>
										<td></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
						<div class="pagination2">
	
</div>
				</div>
				<div class="tab-pane fade" id="review" role="tabpanel" aria-labelledby="review-tab">
					<table class="table table-hover">
						<thead>
							<tr>
								<th scope="col">訂單編號</th>
								<th scope="col">活動名稱</th>
								<th scope="col">訂單時間</th>
								<th scope="col">金額</th>
								<th scope="col"></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${eventOrderList}" var="event">
								<tr>
									<th scope="row">${event.event_order_id}</th>
									<td>${eventSvc.getOneEvent(event.event_id).event_title}</td>
									<td><fmt:formatDate value="${event.order_place_time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
									<td><fmt:formatNumber value="${amountPrice.get(event.event_order_id)}" pattern="$#,###" /></td>
									<td><a class="btn btn-custom btn-color-primary" href="${pageContext.request.contextPath}/EventOrderController?action=getOrderDetail&event_order_id=${event.event_order_id}"
										style="margin-bottom: 0px; width: 50%;">查閱</a></td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>



	</div>



	<script src="<%=request.getContextPath()%>/js/ordrs/myOrders.js"></script>

	<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>