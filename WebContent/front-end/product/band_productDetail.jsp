<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page import="com.orderlist.model.ReviewVO"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.enums.ProductType"%>
<%
	List<ReviewVO> reviewList = (List<ReviewVO>) request.getAttribute("reviewList");
	int review_score = 0;
	int total = 0;
	if (reviewList != null&&!reviewList.isEmpty()) {
		for (ReviewVO tmp : reviewList) {
			total += tmp.getReview_score();
		}
		review_score = total / reviewList.size();
	}

	pageContext.setAttribute("review_score", review_score);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>樂團商品詳情頁(鈺涵)</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/fontawesome.css" integrity="sha384-eHoocPgXsiuZh+Yy6+7DsKAerLXyJmu2Hadh4QYyt+8v86geixVYwFqUvMU8X90l"
	crossorigin="anonymous" />
<style>
body {
	background-color: #fafafa ! important;
}
}

div.product-info-tabs {
	margin: 50px 50px 50px 0px;
	padding: 0px, 50px, 50px, 0px;
}

div.shoppingcart {
	width: 50px;
	height: 50px;
	color: #888;
	position: fixed;
	right: 50px;
	top: 50%;
	margin-top: -2.5em;
	
}

hr.new3 {
	border: 1px solid #f9595f;
}

.rate2 {
	float: left;
	height: 30px;
}

.rate2>input {
	position: absolute;
	top: -9999px;
}

.rate2>span {
	float: left;
	width: 1em;
	overflow: hidden;
	white-space: nowrap;
	cursor: pointer;
	font-size: 20px;
	color: #ccc;
}

.rate2>span:before {
	content: '★ ';
}

.rate2>.shine {
	color: #ffc700;
}
span.free {
	color: red;
}

div.intro {
	margin: 15px;
}

p.comment-text {
	margin: auto 0px auto 72px;
}

div.productType {
	background-color: #fff;
	margin: 50px 50px 50px 50px;
	padding: 0px, 50px, 50px, 50px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
	border-radius: .4rem;
	border: 1px solid #e4e4e4;
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1)
}

a.btn-custom {
	font-weight: 200;
	width: 100px!important;
	
	color: white !important;
	margin: 0 auto 0 auto;
	display: inline-block;
}

a.btn-color-primary:focus {
	border-width: 1px;
	border-style: solid color: white !important;
	border-color: #f9595f;
}

a.btn-color-primary:hover {
	background-color: #f9595f;
}

a.btn-color-primary {
	background-color: #f5b6b8;
}

a.btn-custom {
	font-weight: 200;
	width: 100px!important;
	
	color: white !important;
	margin: 0 auto 0 auto;
	display: inline-block;
}

a.btn-color-primary-search:focus {
	border-width: 1px;
	border-style: solid color: white !important;
	border-color: #f9595f;
}

a.btn-color-primary-search:hover {
	background-color: #f563ac;
}

a.btn-color-primary-search {
	background-color: #f9595f;
	
}

input.form-control {
    display: inline-block;
    width: 210px;
    height: calc(1.5em + .75rem + 2px);
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: .25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
div.type{
 display: inline-block !important;
}

button.round-black-btn-end{
border-radius: 4px;
    background: #9c979a;
    color: #fff;
    padding: 7px 45px;
    display: inline-block;
    margin-top: 20px;
    border: solid 2px #9c979a ; 
    transition: all 0.5s ease-in-out 0s;
}
</style>
</head>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
<!-- <div class="pd-wrap"> -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/product/band_productDetail.css">



<body>
 <jsp:include page="/front-end/header_footer/header.jsp" flush="true" /> 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/product/band_productDetail.js"></script>
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

function updateFavorite(action){
	var actionText = '';
	if(action=='addFavorite'){
		actionText="加入我的最愛";
	}else if(action='removeFavorite'){
		actionText="移除我的最愛";
	}
	$.ajax({
	    type: "post",
	    url: "<%=request.getContextPath()%>/product/YUproductServlet",
				dataType : "json",
				data : {
					"action" : action,
					"productId" : "${productVO.product_id}"
				},
				success : function(response) {
					if (true === response) {
						alert(actionText+"成功");
						location.reload();
					} else if (false === response) {
						alert(actionText+"失敗");
					} else {
						alert(response);
					}
				},
				error : function(thrownError) {
					console.log(thrownError);
					alert("請先登入會員");
				}
			});

}		
	</script>
	<script>
	function queryProduct() {

		document.getElementById("queryForm").submit();
	}
</script>
	<jsp:useBean id="product" scope="page" class="com.product.model.ProductService" />
	<c:set var="productTypeList" value="ProductType.values()" />
	<div class="productType">
		<form action="<%=request.getContextPath()%>/product/YUproductServlet" method="GET" id="queryForm">
			<input name="action" value="findByProductName" type="hidden" /> <input name="productName" placeholder="請輸入關鍵字或產品名稱" class="form-control" style="margin: 20px auto 0 auto;" /> <a id="back_page"
				class="btn btn-custom btn-color-primary-search" style="margin: 15px; width: 30%;" onclick="queryProduct();">查詢</a>

			<a id="back_page" class="btn btn-custom btn-color-primary" style="margin-bottom: 5px; width: 40%; border-radius: 30px;" href="<%=request.getContextPath()%>/product/YUproductServlet">全部商品</a>&nbsp;&nbsp;
			<c:forEach items="${ProductType.values()}" var="productType">
				<a id="back_page" class="btn btn-custom btn-color-primary" style="margin-bottom: 5px; width: 30%; border-radius: 30px;"
					href="<%=request.getContextPath()%>/product/YUproductServlet?action=findByProductType&productType=${productType.code}">${productType.name}</a>&nbsp;&nbsp;

</c:forEach>
		</form>
		
		

	</div>
	
	
	<div class="form1">
		<div class="container">
			<div class="heading-section"></div>

			<form action="<%=request.getContextPath()%>/cart/cartServlet" method="post">
				<div class="row">
					<div class="col-md-6">
						<div id="slider" class="owl-carousel product-slider">
							<c:forEach items="${photoIdList}" var="photoId" varStatus="status">
								<c:choose>
									<c:when test="${status.index==1}">
										<div class="itemBig">
											<img class="img" src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="itemBig">
											<img class="img" src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>

						</div>
						<div id="thumb" class="owl-carousel product-thumb">
							<c:forEach items="${photoIdList}" var="photoId" varStatus="status">
								<div class="item">
									<img src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?photoId=${photoId}" />
								</div>
							</c:forEach>
						</div>

					</div>
					<div class="col-md-6">
						<div class="product-dtl" id="productInfo">

							<div class="product-info">
								<hr class="new3" size="12px" align="left" width="80%" color="#f9595f" style="margin: 0px 0px 35px 0px;">

								<div class="product-name">${productVO.product_name}</div>
								<div class="reviews-counter">
									<div class="rate2">
										<input type="hidden" id="star1" name="rate" value="1" /> <span class="<c:if test='${review_score>0}'>shine</c:if>">1 star</span> <input type="hidden" id="star2" name="rate" value="2" /> <span
											class="<c:if test='${review_score>1}'>shine</c:if>">2 stars</span> <input type="hidden" id="star3" name="rate" value="3" /> <span class="<c:if test='${review_score>2}'>shine</c:if>">3
											stars</span> <input type="hidden" id="star4" name="rate" value="4" /> <span class="<c:if test='${review_score>3}'>shine</c:if>">4 stars</span> <input type="hidden" id="star5" name="rate"
											value="5" /> <span class="<c:if test='${review_score>4}'>shine</c:if>">5 stars</span>
									</div>
									<span style="font-size: 18px;">${fn:length(reviewList)} Review<c:if test="${fn:length(reviewList)>1}">s</c:if></span>
								</div>
								<div class="product-price-discount">
									<span class="product-price-discount"><span style="color: #444">NT</span>
									<fmt:formatNumber value="${productVO.product_price}" pattern="$#,###" /></span>
								</div>
								<div>
									<span>結帳方式 : 信用卡</span><br /> <span>配送方式 : 宅配</span>&nbsp&nbsp<span class="free">限時優惠，全館免運!!</span><br>
								</div>
							</div>
							<div class="intro">
								<p>${productVO.product_intro}</p>
							</div>


										<div class="product-count">
								<label for="size">數量</label>
								<div class="display-flex">
									<div class="qtyminus" id="qtyminus">-</div>
									<input type="text" id="quantity" name="quantity" value="1" class="qty">
									<c:if test="${productVO.product_stock>0}">
									<div class="qtyplus" id="qtyplus">+</div>
									</c:if>
									<c:if test="${productVO.product_stock<=0}">
									<div class="qtyplus" id="qtyplus" style="pointer-events:none;"><i class="fas fa-ban"></i></div>
									</c:if>
									&nbsp&nbsp <p>庫存量:${productVO.product_stock}</p>
								</div>
								
								
								<c:if test="${productVO.product_stock>0}">
								<input type="hidden" name="productId" value="${productVO.product_id}" /> <input type="hidden" name="productName" value="${productVO.product_name}" /> <input type="hidden" name="productPrice"
									value="${productVO.product_price}" /> <input type="hidden" name="productPhotoId" value="" /> <input type="submit" value="立即購買" class="round-black-btn-now" /> <input type="hidden"
									name="action" value="goToCart" />
								<button class="round-black-btn-add" onclick="addToCart();return false;">
									<i class="fas fa-cart-plus"></i>&nbsp 加入購物車
								</button>
								</c:if>
								<c:if test="${productVO.product_stock<=0}">
								<button class="round-black-btn-end" disabled="disabled" >售完補貨中</button>
								
								</c:if>
								<c:if test="${empty favoritesVO}">
									<button class="round-black-btn" onclick="updateFavorite('addFavorite');return false;">
										<i class="fa-1.5x fas fa-heart"></i>&nbsp 加入我的最愛
									</button>
								</c:if>
								<c:if test="${not empty favoritesVO}">
									<button class="round-black-btn" onclick="updateFavorite('removeFavorite');return false;">
										<i class="fa-1.5x fas fa-heart"></i>&nbsp 移除我的最愛
									</button>
								</c:if>
							

							</div>

						</div>
					</div>
				</div>

			</form>
			<div class="product-info-tabs">
				<ul class="nav nav-tabs" id="myTab" role="tablist">
					<li class="nav-item"><a class="nav-link active" id="description-tab" data-toggle="tab" href="#description" role="tab" aria-controls="description" aria-selected="true">商品詳情</a></li>
					<li class="nav-item"><a class="nav-link" id="review-tab" data-toggle="tab" href="#review" role="tab" aria-controls="review" aria-selected="false">評價</a></li>
				</ul>
				<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade active show" id="description" role="tabpanel" aria-labelledby="description-tab">${productVO.product_detail}</div>
					<div class="tab-pane fade" id="review" role="tabpanel" aria-labelledby="review-tab">
						
				<div class="comment-list">
							<ul class="comments">
								<c:if test="${not empty reviewList}">
									<c:forEach items="${reviewList}" var="reviewVo">
										<li class="comment"><c:if test="${not empty reviewVo.photoData}">
												<img class="project-collaborator" src="data:image/png;base64,${reviewVo.photoData}">
											</c:if> <c:if test="${ empty reviewVo.photoData}">
												<img class="project-collaborator" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/454262/default.png">
											</c:if> <c:if test="${ not empty reviewVo.member_nickname}">
												<h1 class="comment-username">${reviewVo.member_nickname}</h1>
											</c:if> <c:if test="${empty reviewVo.member_nickname}">
												<div>
													<h6>匿名</h6>
												</div>
											</c:if> <time class="comment-date">
												<fmt:formatDate value="${reviewVo.review_time}" pattern="yyyy-MM-dd HH:mm:ss" />
											</time> <header class="comment-body-header">
												<div>
													<div class="rate2">
														<input type="hidden" id="star1" name="rate" value="1" /> <span class="<c:if test='${reviewVo.review_score>0}'>shine</c:if>">1 star</span> <input type="hidden" id="star2" name="rate"
															value="2" /> <span class="<c:if test='${reviewVo.review_score>1}'>shine</c:if>">2 stars</span> <input type="hidden" id="star3" name="rate" value="3" /> <span
															class="<c:if test='${reviewVo.review_score>2}'>shine</c:if>">3 stars</span> <input type="hidden" id="star4" name="rate" value="4" /> <span
															class="<c:if test='${reviewVo.review_score>3}'>shine</c:if>">4 stars</span> <input type="hidden" id="star5" name="rate" value="5" /> <span
															class="<c:if test='${reviewVo.review_score>4}'>shine</c:if>">5 stars</span>
													</div>
													<div></div>
												</div>

											</header>
											<p class="comment-text">${reviewVo.review_msg}</p>
											<hr color="#FFB6C1" size=10px></li>
									</c:forEach>
								</c:if>
								<c:if test="${empty reviewList}">
									<h1>此商品暫無評論</h1>
								</c:if>

							</ul>
						</div>


					</div>
				</div>
			</div>
		</div>
	</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="	sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


<%--  <jsp:include page="/front-end/header_footer/footer.jsp" flush="true" /> --%>
</body>
</html>