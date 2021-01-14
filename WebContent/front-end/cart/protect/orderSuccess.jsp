<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>付款成功</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/cart/orderSuccess.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/*">
</head>

<body>
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />	

<script>
	        
function goOrders(){
    window.location.href = "<%=request.getContextPath()%>/orders/myOrderServlet";
}
function backToHomePage(){
    window.location.href = "<%=request.getContextPath()%>/index.jsp";
}
</script>
	<!-- 進度列 -->
	<div class="container">
		<div class="row">
			<ul class="progressbar">
				<li class="cart"><span>購物車 </span></li>
				<li class="check"><span>結帳 </span></li>
				<li class="active"><span>完成</span></li>
			</ul>
		</div>
	</div>
	<!-- 訂單明細 -->
	<div class="container">
		<div class="top-title">
			<h5>謝謝！我們已經收到您的訂單。</h5>
		</div>

		<div>
			<div class="product-detail">
				<ul class="detail-line">
					<li class="det-1">
						<p>訂單編號</p>
						<p>${orderId}</p>
					</li>
					<li class="det-1">
						<p class="date">日期</p>
						<p class="date">
							<fmt:formatDate value="${order_place_time}"
								pattern="yyyy/MM/dd" />
						</p>
					</li>
					<li class="det-1">
						<p>總金額</p>
						<p>NT:${totalPrice}</p>
					</li>
					<li>
						<p>付款方式</p>
						<p>信用卡付款</p>
					</li>
				</ul>
			</div>


		</div>

		<div class="top-title">
			<h5>若您有任何問題，請透過訂單系統客服表單與我們聯繫。謝謝!</h5>
		</div>

		<div class="btn-all">
			<button class="btn-buy" onclick="goOrders();" >查看訂單紀錄</button>
			<button class="btn-back" onclick="backToHomePage();" >回到首頁</button>
		</div>

	</div>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>