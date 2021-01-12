<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>樂團ID周邊商品(鈺涵)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/*">

<style>

div.col{
margin:0px 0px 5px 0px;
}
</style>

</head>
<body>


	<div class="container">

		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
			<c:forEach items="${bandProduct}" var="vo">
				<div class="col">
					<div class="card shadow-sm">
						<img
							src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${vo.product_id}"
							class="card-img-top" alt="...">
						<div class="card-body">
							<p class="card-text">${vo.product_name}</p>
							<div class="d-flex justify-content-between align-items-center">

								<small class="text-muted">NT:${vo.product_price}</small>
								<a href="${pageContext.request.contextPath}/product/YUproductServlet?action=show_me_one&id=${vo.product_id}" 
							style="margin-bottom: 0px;">點我查看</a> 
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<a href="<%=request.getContextPath()%>/front-end/cart/cart_page.jsp">購物車</a>
<%-- 	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/*"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/vendors/jquery/*"></script> --%>
</body>
</html>
