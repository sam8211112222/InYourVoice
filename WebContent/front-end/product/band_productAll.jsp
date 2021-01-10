<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>樂團周邊商品(鈺涵)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/product/band_productAll.css">

</head>
<body>



	<jsp:useBean id="product" scope="page"
		class="com.product.model.ProductService" />
<div class="container">

<!--                 <div class="row justify-content-center"> -->
<!--                     <div class="product_title"> -->
<!--                        商品總覽 -->
<!--                     </div> -->
<!--                 </div> -->

<!--                 <div class="row justify-content-center"> -->
<%--                     <form action="<%= request.getContextPath() %>/band/band.do"> --%>
<!--                         <div class="search_box"> -->
<!--                             <input type="hidden" name="action" value="listAllBand"> -->
<!--                             <input type="text" name="searchKeyWord" placeholder="search"> -->
<!--                             <button type="submit" class="btn btn-search"> -->
<!--                             <i class="fa fa-search"></i> -->
<!--                             </button> -->
<!--                         </div> -->
<!--                     </form> -->
<!--                 </div> -->
<!-- 	<div class="container"> -->

<!-- 		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3"> -->
<%-- 			<c:forEach items="${product.all}" var="vo"> --%>
<!-- 				<div class="col"> -->
<!-- 					<div class="card shadow-sm"> -->
<!-- 						<img -->
<%-- 							src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${vo.product_id}" --%>
<!-- 							class="card-img-top" alt="..."> -->
<!-- 						<div class="card-body"> -->
<%-- 							<p class="card-text">${vo.product_name}</p> --%>
<!-- 							<div class="d-flex justify-content-between align-items-center"> -->

<%-- 								<small class="text-muted">NT:${vo.product_price}</small> <a --%>
<%-- 									href="${pageContext.request.contextPath}/product/YUproductServlet?action=show_me_one&id=${vo.product_id}" --%>
<!-- 									style="margin-bottom: 0px;">點我查看</a> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<%-- 			</c:forEach> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<div class="row justify-content-around">

                    <c:forEach var="vo" items="${product.all}">

                        <div class="flip" data-band_id="${vo.product_id}" style="cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${vo.product_id}';">
                            <div class="front"
                                style="background-image: url(<%= request.getContextPath() %>/productphoto/YUproductPhotoServlet?id=${vo.product_id})">
                            </div>
                            <div class="back">
                                <!-- <h2>${bandVO.band_name}</h2> -->
                                
                                <h1 class="text-shadow">${vo.product_name}</h1>
                                <p>NT:${vo.product_price}</p>
                            </div>
                        </div>

                    </c:forEach>

                </div>
	<a href="<%=request.getContextPath()%>/front-end/cart/cart_page.jsp">購物車</a>
	
<!-- 	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script> -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
