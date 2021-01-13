<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>購票成功</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/eventorder/css/5_cart4.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
</head>
<body>
	<%@ include file="/front-end/header_footer/header.jsp"%>
<script>
	        
function goOrders(){
    window.location.href = "<%=request.getContextPath()%>/orders/myOrderServlet";
}
function backToHomePage(){
    window.location.href = "<%=request.getContextPath()%>/index.jsp";
}
</script>


	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
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
						<p>日期</p>
						<p><fmt:formatDate value="${order_place_time}"
								pattern="yyyy/MM/dd" /></p>
					</li>
					<li class="det-1">
						<p>總金額</p>
						<p>${ticketPrice}</p>
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
	<%@ include file="/front-end/header_footer/footer.jsp"%>
</body>
</html>