<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單詳情</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<style type="text/css">
div.outer_ring{
border: 1px solid black;
	border-radius: 30px;
}
div.form1 {
	border: 1px solid black;
	border-radius: 30px;
}

div.total {
	text-align: right;
}

div.btn_back {
	float: right;
}

div.orders_title {
	text-align: center;
}
</style>

</head>
<body>
	<div class="outer_ring">
		<div class="orders_title">
			<h1>訂單詳情</h1>
		</div>
		<h1>訂單明細</h1>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
			crossorigin="anonymous"></script>
		<div class="form1">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">商品名稱</th>
						<th scope="col">單價</th>
						<th scope="col">數量</th>
						<th scope="col">小計</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${productItem}" var="vo">
						<tr>
							<th scope="row">${vo.product_name}</th>
							<td>${vo.product_price}</td>
							<td>數量</td>
							<td>小計</td>


						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>
		<div class="total">
			<h1>總金額:NT</h1>
		</div>
		<br>
		<div class="btn_back">
			<button type="button" id="back_page">返回</button>
		</div>
	</div>
	<script>
		$("#back_page").on("click", function() {
			history.back();
		})
	</script>
</body>
</html>