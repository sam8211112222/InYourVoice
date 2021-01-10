<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.enums.ProductType"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*"%>
<%
	List<ProductVO> productList = (List<ProductVO>) request.getAttribute("productList");
	pageContext.setAttribute("productList", productList);
%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>樂團周邊商品(鈺涵)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/product/band_productAll.css">
<style>
div.pagination{
position: absolute;
top: 95%;
left: 50%;
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
							<input name="productName" placeholder="產品名稱" class="form-control" style="margin: 20px auto 0 auto;" /> 
					
							<a id="back_page"
								class="btn btn-custom btn-color-primary" style="margin: 15px; width: 30%;" onclick="queryProduct();">查詢</a>
						
						</form>
						<hr class="line" size="1px" align="center" width="100%">
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
									<div class="info" style="margin:10px;">
									<h4  style="text-align: left !important;color:#444;">${vo.product_name}</h4>
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
