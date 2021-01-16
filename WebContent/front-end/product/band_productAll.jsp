<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.enums.ProductType"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.product.model.*,com.orderlist.model.ReviewVO"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	List<ProductVO> productList = (List<ProductVO>) request.getAttribute("productList");
	pageContext.setAttribute("productList", productList);
	
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
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>樂團周邊商品(鈺涵)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/product/band_productAll.css">
<style>
body{
background-color:rgb(199 188 147 / 38%) !improtant;
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
h4.productName{
font-size:15px;
}
</style>
</head>


<script>
	function queryProduct() {

		document.getElementById("queryForm").submit();
	}
</script>
<body>
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	<jsp:useBean id="product" scope="page" class="com.product.model.ProductService" />
	<c:set var="productTypeList" value="ProductType.values()" />
	<div>
		<div class="page">
			<%@ include file="bandProductAll_page1.file"%>
		</div>
		<div class="form1">
			<div class="container">
				<div class="row">
					<div class="col-sm-3" id="productType">

						<form action="<%=request.getContextPath()%>/product/YUproductServlet" method="GET" id="queryForm">
							<input name="action" value="findByProductName" type="hidden" /> 
							<input name="productName" placeholder="請輸入關鍵字或產品名稱" class="form-control" style="margin: 20px auto 0 auto;" /> 
					
							<a id="back_page"
								class="btn btn-custom btn-color-primary-search" style="margin: 15px 60px; width: 30%;" onclick="queryProduct();">查詢</a>
						
						</form>
						<hr class="line" size="1px" align="center" width="100%" >
						<h5 class="productType">產品分類</h5>
						<div>
							<a id="back_page" class="btn btn-custom btn-color-primary" style="margin-bottom: 5px; width: 40%; border-radius: 30px;" href="<%=request.getContextPath()%>/product/YUproductServlet">全部商品</a>&nbsp;&nbsp;
							<c:forEach items="${ProductType.values()}" var="productType">
								<a id="back_page" class="btn btn-custom btn-color-primary" style="margin-bottom: 5px; width: 30%; border-radius: 30px;"
									href="<%=request.getContextPath()%>/product/YUproductServlet?action=findByProductType&productType=${productType.code}">${productType.name}</a>&nbsp;&nbsp;

</c:forEach>
						</div>

					</div>

					<div class="col-sm-12" id="productList">
						<div class="row justify-content-around">
							<c:forEach var="vo" items="${productList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

								<div class="flip" data-band_id="${vo.product_id}" style="cursor: pointer;"
									onclick="location.href='<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${vo.product_id}';">
									<div class="front" style="background-image: url(<%= request.getContextPath() %>/productphoto/YUproductPhotoServlet?id=${vo.product_id})"></div>
									<div class="reviews-counter" style="margin:auto 17px;">
									<div class="rate2">
										<input type="hidden" id="star1" name="rate" value="1" /> <span class="<c:if test='${vo.review_score>0}'>shine</c:if>">1 star</span> <input type="hidden" id="star2" name="rate" value="2" /> <span
											class="<c:if test='${vo.review_score>1}'>shine</c:if>">2 stars</span> <input type="hidden" id="star3" name="rate" value="3" /> <span class="<c:if test='${vo.review_score>2}'>shine</c:if>">3
											stars</span> <input type="hidden" id="star4" name="rate" value="4" /> <span class="<c:if test='${vo.review_score>3}'>shine</c:if>">4 stars</span> <input type="hidden" id="star5" name="rate"
											value="5" /> <span class="<c:if test='${vo.review_score>4}'>shine</c:if>">5 stars</span>
									</div>
									<span style="font-size: 14px;">${vo.review_count} Review<c:if test="${vo.review_count>1}">s</c:if></span>
								</div>
									<div class="info" style="margin:10px;">
									<h4 class="productName" style="text-align: left !important;color:#444;">${vo.product_name}</h4>
									<h6 style="text-align: left !important;color:#888;">NT$<fmt:formatNumber value="${vo.product_price}" pattern="#,###" />
										</h6>
									</div>
									<div class="back">
<%--<h2>${bandVO.band_name}</h2> --%>

										<h1 class="text-shadow" style="margin-top:40%;font-size:30px;text-align:center;">點我查看</h1>
<!-- 										<p> -->
<!-- 											NT: -->
<%-- 											<fmt:formatNumber value="${vo.product_price}" pattern="#,###" /> --%>
<!-- 										</p> -->
									</div>
								</div>

							</c:forEach>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div class="pagination">
		<%@ include file="bandProductAll_page2.file"%>
	</div>

<!-- 	<div class="footer_btn"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-6"></div> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="order_btn"> -->
<%-- 					<a href="<%=request.getContextPath()%>/cart/cartServlet"> --%>
<!-- 						<button id="place-order-btn" class="btn btn-success  "> -->
<!-- 							<span class="ladda-label"> 查看購物車 </span><span class="ladda-spinner"></span> -->
<!-- 						</button> -->

<!-- 					</a> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>
