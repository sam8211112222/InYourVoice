<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.orderlist.model.ReviewVO,com.product.model.*"%>

<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
List<ProductVO> productList = (List<ProductVO>) request.getAttribute("productList");
pageContext.setAttribute("productList", productList);

	List<ReviewVO> reviewList = (List<ReviewVO>)request.getAttribute("reviewList");
	int review_score = 0;
	int total = 0;
	if(reviewList!=null){
		for(ReviewVO tmp:reviewList){
			total+=tmp.getReview_score();
		}
		review_score = total/reviewList.size();
	}

	pageContext.setAttribute("review_score", review_score);
%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>樂團ID周邊商品(鈺涵)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<style>


h1 {
	font-size: 1.5em;
	background-color: rgba(139, 139, 139, 0.493);
	border-radius: 10px;
}

.YUflip {
	border-radius: 10px;
	position: relative;
	display: inline-block;
	margin-right: 2px;
	margin-bottom: 50px;
	width: 280px;
	height: 300px;
}

.YUflip>.front, .YUflip>.back {
	display: inline-block;
/* 	transition-timing-function: cubic-bezier(0.175, 0.885, 0.32, 1.275); */
/* 	transition-duration: .5s; */
/* 	transition-property: transform, opacity; */
}

.YUflip>.front {
/* 	transform: rotateY(0deg); */
}

.YUflip>.back {
	position: absolute;
	opacity: 0;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
/* 	transform: rotateY(-180deg); */
}

.YUflip:hover>.front {
/* 	transform: rotateY(180deg); */
}

.YUflip:hover>.back {
	opacity: 1;
/* 	transform: rotateY(0deg); */
}

.YUflip.YUflip-vertical>.back {
/* 	transform: rotateX(-180deg); */
}

.YUflip.YUflip-vertical:hover>.front {
/* 	transform: rotateX(180deg); */
}

.YUflip.YUflip-vertical:hover>.back {
/* 	transform: rotateX(0deg); */
}

.YUflip>.front, .YUflip>.back {
	display: block;
	color: white;
	width: inherit;
	background-size: cover !important;
	background-position: center !important;
	height: 200px;
	padding: 1em 2em;
	background: #313131;
	border-radius: 10px;
	height: 250px;
	border: 1px solid #e4e4e4;
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
}

.YUflip>.front p, .YUflip>.back p {
	font-size: 0.9125rem;
	line-height: 160%;
	color: #999;
}

.text-shadow {
	text-shadow: 1px 1px rgba(0, 0, 0, 0.04), 2px 2px rgba(0, 0, 0, 0.04),
		3px 3px rgba(0, 0, 0, 0.04), 4px 4px rgba(0, 0, 0, 0.04), 0.125rem
		0.125rem rgba(0, 0, 0, 0.04), 6px 6px rgba(0, 0, 0, 0.04), 7px 7px
		rgba(0, 0, 0, 0.04), 8px 8px rgba(0, 0, 0, 0.04), 9px 9px
		rgba(0, 0, 0, 0.04), 0.3125rem 0.3125rem rgba(0, 0, 0, 0.04), 11px
		11px rgba(0, 0, 0, 0.04), 12px 12px rgba(0, 0, 0, 0.04), 13px 13px
		rgba(0, 0, 0, 0.04), 14px 14px rgba(0, 0, 0, 0.04), 0.625rem 0.625rem
		rgba(0, 0, 0, 0.04), 16px 16px rgba(0, 0, 0, 0.04), 17px 17px
		rgba(0, 0, 0, 0.04), 18px 18px rgba(0, 0, 0, 0.04), 19px 19px
		rgba(0, 0, 0, 0.04), 1.25rem 1.25rem rgba(0, 0, 0, 0.04);
}

.search_box {
	margin: 30px;
}

.product_title {
	font-size: 30px;
	font-weight: bolder;
	color: darkslateblue;
	margin: 30px;
}

div.form1 {
	background-color: #fff;
	margin: 0px 50px 15px 320px;
	padding: 0px, 50px, 15px, 0px;
	min-height: 1px;

	/* 	border-radius: .4rem; */
	/* 	border: 1px solid #e4e4e4; */
	/* 	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1); */
}

div.order_btn {
	margin: 15px auto 15px 200px;
}

div.footer_btn {
	background-color: #fff;
	border-color: #fafafa;
	border-style: solid;
	margin: 0px 50px 15px 50px;
	padding: 0px, 50px, 15px, 0px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
	border-radius: .4rem;
	border: 1px solid #e4e4e4;
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
}

button.btn-success {
	color: #fff;
	background-color: #f9595f;
	border-color: #f9595f;
}

div#productList {
	margin: 40px 0px 40px 0px;
}

div#product {
	width: 250px;
	height: 300px;
	background-color: green;
}

div.info {
	position: absolute;
	left: 10px;
}

div.page {
	margin: 15px 0px 15px 600px;
}
div.pagination{
    margin: auto 600px;
    width: 100%;
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
	background-color:  #f5b6b8;
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
</style>

</head>
<body>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<div class="col-sm-12" id="productList">
						<div class="row justify-content-around">
							<c:forEach var="vo" items="${bandProduct}" >

								<div class="YUflip" data-band_id="${vo.product_id}" style="cursor: pointer;"
									onclick="location.href='<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${vo.product_id}';">
									<div class="front" style="background-image: url(<%= request.getContextPath() %>/productphoto/YUproductPhotoServlet?id=${vo.product_id})"></div>
<!--  									<div class="reviews-counter">  -->
<!--  									<div class="rate2"> -->
<%--  										<input type="hidden" id="star1" name="rate" value="1" /> <span class="<c:if test='${vo.review_score>0}'>shine</c:if>">1 star</span> <input type="hidden" id="star2" name="rate" value="2" /> <span  --%>
<%-- 											class="<c:if test='${vo.review_score>1}'>shine</c:if>">2 stars</span> <input type="hidden" id="star3" name="rate" value="3" /> <span class="<c:if test='${vo.review_score>2}'>shine</c:if>">3  --%>
<%--  											stars</span> <input type="hidden" id="star4" name="rate" value="4" /> <span class="<c:if test='${vo.review_score>3}'>shine</c:if>">4 stars</span> <input type="hidden" id="star5" name="rate"  --%>
<%--  											value="5" /> <span class="<c:if test='${vo.review_score>4}'>shine</c:if>">5 stars</span>  --%>
<!--  									</div>  -->
<%--  									<span style="font-size: 14px;">${vo.review_count} Review<c:if test="${vo.review_count>1}">s</c:if></span>  --%>
<!-- 								</div>  -->
									<div class="info" style="margin:10px;">
									<h4  style="text-align: left !important;color:#444;">${vo.product_name}</h4>
									<h6 style="text-align: left !important;color:#888;">NT$<fmt:formatNumber value="${vo.product_price}" pattern="#,###" />
										</h6>
									</div>
									
								</div>

							</c:forEach>

						</div>
					</div>



<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="	sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
