<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.* , com.cart.model.CartVO"%>
<%@ page import="static com.cart.controller.CartServlet.SESSION_CART_KEY"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>購物車清單</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<%--  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/cart/cart_page.css" />  --%>

<style type="text/css">
body {
	background-color: #f3f3f3;
}

div.productList {
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
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
	border: 1px solid #e4e4e4;
}

/* 購物進度列 */
.progressbar {
	width: 100%;
	height: 80px;
	counter-reset: step;
	margin-top: 2rem;
	padding-left: 4rem;
}

.progressbar li {
	list-style-type: none;
	float: left;
	width: 30.33%;
	position: relative;
	text-align: center;
}

.progressbar li:before {
	content: counter(step);
	counter-increment: step;
	width: 40px;
	height: 40px;
	line-height: 40px;
	border: 1px solid #e6e6e6;
	display: block;
	border-radius: 50%;
	text-align: center;
	margin: 0 auto 0 auto;
	color: white;
}

.progressbar li.active span {
	color: #000;
}

.progressbar li.active::before {
	border-color: #f9595f;
	background-color: #f9595f;
}

li.check {
	color: #ccc;
}

li.finish {
	color: #ccc;
}

div.deliveryForm {
width:30%;
height:350px;
	background-color: #fff;
	border-color: #fafafa;
	border-style: solid;
	margin: 0px 50px 15px 10px;
	padding: 0px, 50px, 15px, 0px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
	border-radius: .4rem;
	border: 1px solid #e4e4e4;
	box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
}

div.orderForm {
	background-color: white;
	border-color: #f3f3f3;
	border-style: solid;
	margin: 5px 50px 5px 50px;
	padding: 0px, 50px, 15px, 0px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
}

div.form-group {
	line-height: 22px;
	margin-bottom: 10px;
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

.btn-success, .page-checkout .btn-success, .page-order-show .btn-success,
	.btn-color-primary, .confirmation .confirmation-block .confirmation-button
	{
	width:100%;
	background-color: #f9595f !important;
	border-color: #f9595f !important;
	border-radius: 10px;
	justify-content: center;
    margin-top: 35px;
}

hr.new3 {
	border: 1px solid #f9595f;
}

div.order_btn {
	margin: 15px 30px auto 200px;
}

a.btn-link {
	color: #f5a9aa;
}
.pull-left {
    float: left !important;
}
.pull-right {
    float: right !important;
}
#pull-right {
    float: right !important;
}
hr.new2{
border: 1px solid #f3f3f3;
}
.derlivery{
    width:80%;
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
body {
	background:#fafafa !important;
	font-family: "Avant Garde", Avantgarde, "Century Gothic", CenturyGothic,
		"AppleGothic", sans-serif;
	color:black;
	font-size: 16px;
	overflow-y: scroll;
}

#expand {
	cursor: pointer;
}

#coolstuff {
	display: none;
}

#container {
	width: 100%;
	max-width: 920px;
	margin: auto;
	text-align: center;
}

h1.cart_title {
	text-align: center;
}

ul {
	text-align: left;
}

hr {
	border-color: white;
	border-bottom: 0;
}

table {
	width: 100%;
	margin: auto;
	text-align: center;
}

tr {
	padding: 0;
	margin: 0;
	border: 0;
}

td {
	no-border-top: 1px solid white;
	padding: 5px 2px;
}

.image {
	width: 100px;
	height:100px;
}

.image>img {
	width: 100px;
    height: 80px;
	background: white;

	
}

.amount {
	width: 50px;
}

.amount>input {
	font-family: "Avant Garde", Avantgarde, "Century Gothic", CenturyGothic,
		"AppleGothic", sans-serif;
	color: black;
	font-size: 16px;
	background: transparent;
	border: none;
	width: 100%;
	text-align: center;
	outline: none;
}

.remove {
	width: 30px;
}

.remove>div {
	border-radius: 10px;
	width: 16px;
	height: 16px;
	line-height: 16px;
	padding: 2px;
	transition: background .3s, border .3s, transform .3s;
	cursor: pointer;
	text-align: center;
	margin: auto;
	border: 1px solid white;
}

.remove.hey>div {
	background: red;
	border-color: red;
	transform: scale(1.3);
}

.remove:hover>div {
	background: red;
	border-color: red;
}

.big {
	font-size: 2em;
	font-weight: bold;
}

#continue_shopping {
	padding: 10px;
	font-size: 16px;
	color: white;
	display: inline-block;
	margin: 2px;
	border-radius: 3px;
	text-align: center;
	transition: background 0.2s;
	background: #0a8f6c;
	cursor: pointer;
}

#checkout {
	padding: 10px;
	font-size: 16px;
	color: white;
	display: inline-block;
	margin: 2px;
	border-radius: 3px;
	text-align: center;
	transition: background 0.2s;
	background: #0a8f6c;
	cursor: pointer;
}

#checkout>span {
	width: 0;
	display: inline-block;
	overflow: hidden;
	transition: width .3s;
	text-align: right;
}

#checkout:hover>span {
	width: 20px;
}

#footer {
	opacity: 0.6;
	border-top: 1px solid white;
	padding-top: 10px;
	text-align: center;
	margin-top: 15px;
}

#footer a {
	color: white;
}


/* 信用卡＆付款資訊 */

.ship-pay-area{
    width: 100%;
    /* background-color: #0a8f6c; */
    display: flex;
}


</style>

</head>

<%
	List<CartVO> cartList = (List<CartVO>) session.getAttribute(SESSION_CART_KEY);

	request.setAttribute("cartList", cartList);
%>



<body>

<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
<%@ include file="/css/member/member_center_top.file"%>

	
	<div class="container">
		<div class="row">
			<ul class="progressbar">
				<li class="active"><span>購物車 </span></li>
				<li class="check"><span>結帳 </span></li>
				<li class="finish"><span>完成</span></li>
			</ul>
		</div>


	</div>

	<div class="productList">
		<div class="section-header">
			<h2 style="color: rgb(70, 70, 70); margin: 15px;">購物車</h2>
		</div>
		<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
		<div id="container">
			<div>
				<table>
				<c:if test="${cartList!=null}">
					<tr style="text-align: center;">
						<td style="margin:0px 0px 0px 50px">商品照片</td>
						<td>商品名稱</td>
						<td>商品單價</td>
						<td>數量</td>
						<td>小計</td>
						<td></td>
					</tr>
				
					<c:forEach items="${cartList}" var="order">

						<tr class="p">

							<td class="image"><a href="<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${order.product_id}"><img src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${order.product_id}" /></a></td>
							<td class="name">${order.product_name}</td>
							<td class="price"><fmt:formatNumber value="${order.product_price}" pattern="NT$#,###"/></td>
							<td class="amount">${order.product_quantity}<input type="hidden" value="${order.product_quantity}" /> <%-- 							<input type="number"
								name="quantity" value="${order.product_quantity}" min="0"
								class="qty" /> --%>
							</td>

							<td class="pricesubtotal">${order.product_price*order.product_quantity}</td>
							<td class="remove">
								<form action="<%=request.getContextPath()%>/cart/cartServlet" method="post">
									<input type=hidden name="action" value="delete"> <input type=hidden name="del" value="${order.product_id}"> <input type=submit value="&times">
								</form>
							</td>
						</tr>
					</c:forEach>

					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td>總價:</td>
						<td class="totalpricesubtotal"></td>
						<td></td>
					</tr>
					
</c:if >
<c:if test="${cartList==null}">
<h3>目前尚無商品</h3>
</c:if>
				</table>
			</div>
		</div>
	</div>
	<div class="ship-pay-area" >
	<div class="derlivery" >

		<div class="row">
			<div class="col">

				<div class="section-header">
					<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
					<h3 class="title">選擇送貨和付款方式</h3>
	
				</div>
				<div class="section-body">
					 <label for="order-delivery-country">送貨地點</label>
					  <span class="select-cart-form">
        <select id="order-delivery-country" class="form-control" ng-disabled="multiSelect &amp;&amp; state &amp;&amp; state.isCartLoading">
            <option value="AU">澳大利亞</option>
            <option value="BE">比利時</option>
            <option value="KH">柬埔寨</option>
            <option value="CA">加拿大</option>
            <option value="CN">中國</option>
            <option value="CZ">捷克共和國</option>
            <option value="FR">法國</option>
            <option value="DE">德國</option>
            <option value="HK">香港</option>
            <option value="ID">印度尼西亞</option>
            <option value="JP">日本</option>
            <option value="KR">韓國</option>
            <option value="MO">澳門</option>
            <option value="NL">荷蘭</option>
            <option value="NZ">新西蘭</option>
            <option value="PL">波蘭</option>
            <option value="SG">新加坡</option>
            <option value="SE">瑞典</option>
            <option value="CH">瑞士</option>
            <option value="TW" selected="">台灣</option>
            <option value="TH">泰國</option>
            <option value="US">美國</option>
            <option value="VN">越南</option>
        </select>
      </span>
					
					<div class="form-group">
      <label for="order-delivery-method">送貨方式</label>
      <span class="select-cart-form">
        <select id="order-delivery-method" class="form-control" ng-disabled="multiSelect &amp;&amp; state &amp;&amp; state.isCartLoading">
              <option value="5d4a7cad766094003ed8abf8" >宅配到府（信用卡 ）</option>
             
        </select>
      </span>
       <div class="form-group">
      <label for="order-payment-method">付款方式</label>
        <span class="select-cart-form">
          <select id="order-payment-method" class="form-control" ng-disabled="multiSelect &amp;&amp; state &amp;&amp; state.isCartLoading">
              <option value="5e16edd64d9f35002781c3e1" selected="" ng-non-bindable="">信用卡</option>
             
          </select>
        </span>
          <span class="help-block" ng-non-bindable="">支援國內外 Visa, Mastercard, JCB 等信用卡。</span>
    </div>
        <span class="help-block" >最省時快速的方法！我們將在訂單成立後的下一個工作日出貨，以宅配方式寄送您的商品。</span>
    </div>
  
				</div>
			</div>
		</div>
	</div>
	<div class="deliveryForm">

		<div class="row">
			<div class="col">

				<div class="section-header">
					<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
					<h3 class="title">訂單資訊</h3>
					
				
				</div>
				<div class="section-body">
					<span class="pull-left">小計:</span>
					<span class="totalpricesubtotal" id="pull-right"></span><br><br>
					<span class="pull-left">運費:</span>
					<span class="pull-right">NT$0</span><br><br>
					<hr class="new2" size="12px" align="center" width="100%" color="#f3f3f3">
					<span class="pull-left">合計:</span>
					<span class="totalpricesubtotal" id="pull-right"></span><br>
					<span id="pull-right" style="color:#d2d2d2"> *將以新台幣NT計算</span>
					
					<a href='${pageContext.request.contextPath}/front-end/cart/protect/confirm_order.jsp'>
						<button id="place-order-btn" class="btn btn-success  ">
							<span class="ladda-label"> 提交訂單 </span><span class="ladda-spinner"></span>
						</button>

					</a>
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- Forbid customer return previous page in express checkout page -->
	<div class="footer_btn">
		<div class="row">
			<div class="col-sm-6">
				<a style="text-align: center; margin: 15px 50px; color: #f5a9aa;" class="btn btn-link pull-left" href="<%=request.getContextPath()%>/product/YUproductServlet">&lt; 返回商品頁</a>
			</div>
			<div class="col-sm-6">
				<div class="order_btn">
					
				</div>
			</div>
		</div>
	</div>




	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
 	<script src="<%=request.getContextPath()%>/js/cart/cart_page.js"></script> 
	<%@ include file="/css/member/member_center_bottom.file"%> 
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />

</body>


</html>