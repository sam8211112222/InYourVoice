<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>樂團商品詳情頁(鈺涵)</title>
</head>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
<!-- <div class="pd-wrap"> -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/product/band_productDetail.css">



<body>

	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/product/band_productDetail.js"></script>
	<!------ Include the above in your HEAD tag ---------->


	<script>

function addToCart(){
	
	$.ajax({
	    type: "post",
	    url: "<%=request.getContextPath()%>/cart/cartServlet",
				dataType : "json",
				data : {
					"action" : "addToCart",
					"productId" : "${productVO.product_id}",
					"productName" : "${productVO.product_name}",
					"productPrice" : "${productVO.product_price}",
					"productPhotoId" : "",
					"quantity" : $("#quantity").val()
				},
				success : function(response) {
					if (true == response) {
						alert("加入購物車成功");
					} else if (false == response) {
						alert("加入購物車失敗");
					}
				},
				error : function(thrownError) {
					console.log(thrownError);
					alert("加入購物車失敗");
				}
			});

		}
	</script>

	<div class="container">
		<div class="heading-section">
	
		</div>

		<form action="<%=request.getContextPath()%>/cart/cartServlet"
			method="post">
			<div class="row">
				<div class="col-md-6" >
					<div id="slider" class="owl-carousel product-slider">
						<c:forEach items="${photoIdList}" var="photoId" varStatus="status">
							<c:choose>
								<c:when test="${status.index==1}">
									<div class="itemBig">
										<img class="img"
											src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
									</div>
								</c:when>
								<c:otherwise>
								<div class="itemBig">
										<img class="img"
											src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
<%-- 						<div class="itemBig">
							<img class="img"
								src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${productVO.product_id}" />
						</div>
						<div class="item">
							<img src="https://i.ytimg.com/vi/PJ_zomNMK_s/maxresdefault.jpg" />
						</div>
						<div class="item">
							<img
								src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQI6nUmObt62eDkqNSmIvCN_KkQExtbpJmUbVx_eTh_Y3v3r-Jw" />
						</div>
						<div class="item">
							<img src="https://i.ytimg.com/vi/PJ_zomNMK_s/maxresdefault.jpg" />
						</div>
						<div class="item">
							<img
								src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQI6nUmObt62eDkqNSmIvCN_KkQExtbpJmUbVx_eTh_Y3v3r-Jw" />
						</div>
						<div class="item">
							<img src="https://i.ytimg.com/vi/PJ_zomNMK_s/maxresdefault.jpg" />
						</div>
						<div class="item">
							<img
								src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQI6nUmObt62eDkqNSmIvCN_KkQExtbpJmUbVx_eTh_Y3v3r-Jw" />
						</div> --%>
					</div>
					<div id="thumb" class="owl-carousel product-thumb">
						<c:forEach items="${photoIdList}" var="photoId" varStatus="status">
							<div class="item">
								<img
									src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
							</div>
						</c:forEach>
					</div>

				</div>
				<div class="col-md-6">
					<div class="product-dtl" id="productInfo">

						<div class="product-info">

							<div class="product-name">${productVO.product_name}</div>
							<div class="reviews-counter">
								<div class="rate">
									<input type="radio" id="star5" name="rate" value="5" checked />
									<label for="star5" title="text">5 stars</label> <input
										type="radio" id="star4" name="rate" value="4" checked /> <label
										for="star4" title="text">4 stars</label> <input type="radio"
										id="star3" name="rate" value="3" checked /> <label
										for="star3" title="text">3 stars</label> <input type="radio"
										id="star2" name="rate" value="2" /> <label for="star2"
										title="text">2 stars</label> <input type="radio" id="star1"
										name="rate" value="1" /> <label for="star1" title="text">1
										star</label>
								</div>
								<span>3 Reviews</span>
							</div>
							<div class="product-price-discount">
								<span>NT:${productVO.product_price}</span>
							</div>
						</div>
						<p>${productVO.product_intro}</p>


						<div class="product-count">
							<label for="size">數量</label>
							<div class="display-flex">
								<div class="qtyminus" id="qtyminus">-</div>
								<input type="text" id="quantity" name="quantity" value="1"
									class="qty">
								<div class="qtyplus" id="qtyplus">+</div>
							</div>
							<input type="hidden" name="productId"
								value="${productVO.product_id}" /> <input type="hidden"
								name="productName" value="${productVO.product_name}" /> <input
								type="hidden" name="productPrice"
								value="${productVO.product_price}" /> <input type="hidden"
								name="productPhotoId" value="" /> <input type="submit"
								value="立即購買" class="round-black-btn" /> <input type="hidden"
								name="action" value="goToCart" /> <input type="button"
								value="加入購物車" class="round-black-btn" onclick="addToCart();" />
							<a
								href="<%=request.getContextPath()%>/front-end/product/band_productAll.jsp">返回商品列表</a>
							<a
								href="<%=request.getContextPath()%>/front-end/cart/cart_page.jsp">購物車</a>
						
						</div>

					</div>
				</div>
			</div>

		</form>
		<div class="product-info-tabs">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item"><a class="nav-link active"
					id="description-tab" data-toggle="tab" href="#description"
					role="tab" aria-controls="description" aria-selected="true">商品詳情</a>
				</li>
				<li class="nav-item"><a class="nav-link" id="review-tab"
					data-toggle="tab" href="#review" role="tab" aria-controls="review"
					aria-selected="false">評價 (0)</a></li>
			</ul>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="description"
					role="tabpanel" aria-labelledby="description-tab">${productVO.product_detail}</div>
				<div class="tab-pane fade" id="review" role="tabpanel"
					aria-labelledby="review-tab">
					<div class="review-heading">評價</div>
					<p class="mb-20">There are no reviews yet.</p>
					<form class="review-form">
						<div class="form-group">
							<label>Your rating</label>
							<div class="reviews-counter">
								<div class="rate">
									<input type="radio" id="star5" name="rate" value="5" /> <label
										for="star5" title="text">5 stars</label> <input type="radio"
										id="star4" name="rate" value="4" /> <label for="star4"
										title="text">4 stars</label> <input type="radio" id="star3"
										name="rate" value="3" /> <label for="star3" title="text">3
										stars</label> <input type="radio" id="star2" name="rate" value="2" />
									<label for="star2" title="text">2 stars</label> <input
										type="radio" id="star1" name="rate" value="1" /> <label
										for="star1" title="text">1 star</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label>Your message</label>
							<textarea class="form-control" rows="10"></textarea>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<input type="text" name="" class="form-control"
										placeholder="Name*">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<input type="text" name="" class="form-control"
										placeholder="Email Id*">
								</div>
							</div>
						</div>
						<button class="round-black-btn">Submit Review</button>
					</form>
				</div>


			</div>
		</div>
		<!-- 		<article class="task_container"> -->
		<!-- 			<button type="button" class="btn_empty">清空</button> -->
		<!-- 			<h1 class="title1">商品評價</h1> -->

		<!-- 			<div class="task_add_block"> -->
		<!-- 				<input type="text" class="task_name" placeholder="輸入待辦事項…"> -->
		<!-- 				<button type="button" class="task_add">新增</button> -->
		<!-- 			</div> -->

		<!-- 			<div class="task_list_parent"> -->
		<!-- 				<ul class="task_list"> -->
		<!-- 				</ul> -->
		<!-- 			</div> -->
		<!-- 		</article> -->

	</div>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="./vendors/jquery/jquery-3.4.1.min.js"></script>
	<!-- <script src="./js/index.js"></script> -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity=" sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>

</body>
</html>