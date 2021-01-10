<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>樂團周邊商品</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<style>
</style>

</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	<form METHOD="post"
		ACTION="${pageContext.request.contextPath}/product/productServlet">
		<jsp:useBean id="product" scope="page"
			class="com.product.model.ProductJDBCDAO" />
			
		<%-- 		<c:forEach items="${product.all}" var="vo" varStatus="status">
		
			<div class="card" style="width: 18rem;">
				<img src="https://via.placeholder.com/300x200" class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">${vo.product_name}${status.index}<c:if test="${status.index>0&&status.index %3 ==0}"> 換行</c:if></h5>
					<p class="card-text">NT:${vo.product_price}</p>
					<a href="#" class="btn btn-primary">Go somewhere</a>
				</div>
			</div>
		</c:forEach>
		 --%>

		<div class="container">

			<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
				<c:forEach items="${product.all}" var="vo">
					<div class="col">
						<div class="card shadow-sm">
							<img src="${pageContext.request.contextPath}/productphoto/productPhotoServlet?id=${vo.product_id}"
								class="card-img-top" alt="...">
<!-- 							<img -->
<%-- 								src="${pageContext.request.contextPath}/productphoto/productPhotoServlet?id=${vo.product_id}" /> --%>
							<div class="card-body">
								<p class="card-text">${vo.product_name}</p>
								<div class="d-flex justify-content-between align-items-center">

									<small class="text-muted">NT:${vo.product_price}</small>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</form>
</body>
</html>
