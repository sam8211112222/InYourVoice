<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.cart.model.CartVO,com.member.model.*"%>
<%@ page import="static com.cart.controller.CartServlet.SESSION_CART_KEY"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>確認訂單</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/cart/confirm_order.css">

</head>

<%
	List<CartVO> cartList = (List<CartVO>) session.getAttribute(SESSION_CART_KEY);
	
	request.setAttribute("cartList", cartList);
	if(cartList!=null){
		double total = 0;
		for (int i = 0; i < cartList.size(); i++) {
			CartVO order = cartList.get(i);
			double price = order.getProduct_price();
			int quantity = order.getProduct_quantity();
			total += (price * quantity);
		}
	
		
	
		String amount = String.valueOf(total);
		// 	System.out.println(amount);
		// 	Double amount = new Double(total);
		pageContext.setAttribute("amount", amount);
	}
	MemberVo loginMember = (MemberVo) session.getAttribute("memberVo");
	request.setAttribute("member", loginMember);
// 	String member_id = loginMember.getMemberId();

// 	List<MemberVo> orderList = service.findByMemberId(member_id);
// 	request.setAttribute("orderList", service.getAll());
%>


<script>

function defaultMemberData(){
	if($("#defalutMemberDataCb").is(':checked')){
		$("#order-customer-name").val("${member.memberName}");
		$("#order-customer-mail").val("${member.memberAccount}");
		$("#order-customer-phone").val("${member.memberPhone}");
		$("#order-customer-address").val("${member.memberAddress}");
	}
}

</script>
<body>
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />	
	<script src="<%=request.getContextPath()%>/js/jquery/jquery.validate.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/cart/confirm_order.js"></script>

	<div class="container">
		<div class="row">
			<ul class="progressbar">
				<li class="cart"><span>購物車 </span></li>
				<li class="active"><span>結帳 </span></li>
				<li class="finish"><span>完成</span></li>
			</ul>
		</div>
	</div>
	<div class="productList">
		<div id="container">

			<div>
				<table>
					<tr style="text-align: center;">
						<td class="tdVal">商品照片</td>
						<td class="tdVal">商品名稱</td>
						<td class="tdVal">商品單價</td>
						<td class="tdVal">數量</td>
						<td class="tdVal">小計</td>
						<td class="tdVal"></td>
					</tr>

					<c:forEach items="${cartList}" var="order">

						<tr class="p">

							<td class="image"><a href="<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${order.product_id}"><img class="img1" src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${order.product_id}" /></a></td>
							<td class="name">${order.product_name}</td>
							<td class="price">NT<fmt:formatNumber value="${order.product_price}" pattern="$#,###" /></td>
							<td class="amount">${order.product_quantity}</td>
							<td class="pricesubtotal">NT<fmt:formatNumber value="${order.product_price*order.product_quantity}" pattern="$#,###" /></td>

						</tr>
					</c:forEach>


					<tr>

						<td></td>
						<td></td>
						<td></td>
						<td>總價:</td>
						<td class="totalpricesubtotal">NT<fmt:formatNumber value="${amount}" pattern="$#,###" /></td>
						<td></td>
					</tr>


				</table>

			</div>
		</div>
	</div>

	<form name="customerForm" id="customerForm" method="POST" action="<%=request.getContextPath()%>/orders/ordersServlet">
		<div class="deliveryForm">

			<div class="row">
				<div class="col-sm-6">

					<!-- Customer Infomation -->

					<div class="section-header">
						<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
						<h3 class="title">顧客資料</h3>
						<label> <input id="defalutMemberDataCb" type="checkbox" onclick="defaultMemberData(this);" /> <span>同會員資料</span>
						</label>
					</div>
					<div class="section-body">
						<div class="form-group">
							<label for="order-customer-name" class="control-label">顧客名稱</label> &nbsp;&nbsp;<span id="order-customer-name-span" style="color: red;"></span> <input id="order-customer-name" type="text"
								class="form-control" name="orderName" value="">
						</div>
						<div class="form-group">
							<label for="order-customer-mail" class="control-label">電子信箱</label> &nbsp;&nbsp;<span id="order-customer-mail-span" style="color: red;"></span> <input id="order-customer-mail" type="text"
								class="form-control" name="orderMail" value="">
						</div>
						<div class="form-group">
							<label for="order-customer-phone" class="control-label">連絡電話</label> &nbsp;&nbsp;<span id="order-customer-phone-span" style="color: red;"></span> <input id="order-customer-phone" type="text"
								class="form-control" name="orderPhone" value="">
						</div>
						<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
						<h3 class="title">送貨資訊</h3>
						<div class="section-body">
							<div class="form-group">
								<label for="order-customer-city" class="control-label">縣市/地區</label>
								<div id="twzipcode_ADV" class="control-label"></div>
							</div>

							<div class="form-group">
								<label for="order-customer-address" class="control-label">地址</label> &nbsp;&nbsp;<span id="order-customer-address-span" style="color: red;"></span><input id="order-customer-address"
									type="text" class="form-control" name="orderAddress" value="">
							</div>
						</div>
					</div>
					<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
					<section class="remark-form">
						<div class="section-header">
							<h3 class="title">訂單備註</h3>
						</div>
						<div class="section-body">
							<div class="form-group">
								<textarea id="order-remarks" class="form-control" name="remarks" placeholder="有什麼想告訴賣家嗎？" rows="3"></textarea>
							</div>

						</div>
					</section>
				</div>

				<div class="col-sm-6">
					<div class="section-header">
						<hr class="new3" size="12px" align="center" width="100%" color="#f9595f">
						<h3 class="title">付款資訊</h3>
						<div id="twzipcode_ADV"></div>
					</div>
					<label for="creditcardBank" class="control-label">請選擇發卡銀行
					 <select name="select" class="form-control col-sm-6" style="width: 1100px;">
							<option value="" selected="selected">-請選擇-</option>
							<option value="004">004 - 臺灣銀行</option>
							<option value="005">005 - 土地銀行</option>
							<option value="006">006 - 合作商銀</option>
							<option value="007">007 - 第一銀行</option>
							<option value="008">008 - 華南銀行</option>
							<option value="009">009 - 彰化銀行</option>
							<option value="011">011 - 上海商業銀行</option>
							<option value="012">012 - 台北富邦銀行</option>
							<option value="013">013 - 國泰世華銀行</option>
							<option value="016">016 - 高雄銀行</option>
							<option value="017">017 - 兆豐商業銀行</option>
							<option value="018">018 - 農業金庫</option>
							<option value="021">021 - 花旗商業銀行</option>
							<option value="025">025 - 首都銀行</option>
							<option value="039">039 - 澳商澳盛銀行</option>
							<option value="040">040 - 中華開發工業銀行</option>
							<option value="050">050 - 臺灣企銀</option>
							<option value="052">052 - 渣打國際商業銀行</option>
							<option value="053">053 - 台中商業銀行</option>
							<option value="054">054 - 京城商業銀行</option>
							<option value="072">072 - 德意志銀行</option>
							<option value="075">075 - 東亞銀行</option>
							<option value="081">081 - 匯豐商業銀行</option>
							<option value="085">085 - 新加坡華僑銀行</option>
							<option value="101">101 - 大台北銀行</option>
							<option value="102">102 - 華泰銀行</option>
							<option value="103">103 - 臺灣新光商銀</option>
							<option value="104">104 - 台北五信</option>
							<option value="106">106 - 台北九信</option>
							<option value="108">108 - 陽信商業銀行</option>
							<option value="114">114 - 基隆一信</option>
							<option value="115">115 - 基隆二信</option>
							<option value="118">118 - 板信商業銀行</option>
							<option value="119">119 - 淡水一信</option>
							<option value="120">120 - 淡水信合社</option>
							<option value="124">124 - 宜蘭信合社</option>
							<option value="127">127 - 桃園信合社</option>
							<option value="130">130 - 新竹一信</option>
							<option value="132">132 - 新竹三信</option>
							<option value="146">146 - 台中二信</option>
							<option value="147">147 - 三信商業銀行</option>
							<option value="158">158 - 彰化一信</option>
							<option value="161">161 - 彰化五信</option>
							<option value="162">162 - 彰化六信</option>
							<option value="163">163 - 彰化十信</option>
							<option value="165">165 - 鹿港信合社</option>
							<option value="178">178 - 嘉義三信</option>
							<option value="179">179 - 嘉義四信</option>
							<option value="188">188 - 台南三信</option>
							<option value="204">204 - 高雄三信</option>
							<option value="215">215 - 花蓮一信</option>
							<option value="216">216 - 花蓮二信</option>
							<option value="222">222 - 澎湖一信</option>
							<option value="223">223 - 澎湖二信</option>
							<option value="224">224 - 金門信合社</option>
							<option value="512">512 - 雲林區漁會</option>
							<option value="515">515 - 嘉義區漁會</option>
							<option value="517">517 - 南市區漁會</option>
							<option value="518">518 - 南縣區漁會</option>
							<option value="520">520 - 高雄區漁會</option>
							<option value="521">521 - 永安區漁會</option>
							<option value="523">523 - 琉球區漁會</option>
							<option value="524">524 - 新港區漁會</option>
							<option value="525">525 - 澎湖區漁會</option>
							<option value="605">605 - 高雄市農會</option>
							<option value="612">612 - 豐原市農會</option>
							<option value="613">613 - 名間農會</option>
							<option value="614">614 - 彰化地區農會</option>
							<option value="616">616 - 雲林地區農會</option>
							<option value="617">617 - 嘉義地區農會</option>
							<option value="618">618 - 台南地區農會</option>
							<option value="619">619 - 高雄地區農會</option>
							<option value="620">620 - 屏東地區農會</option>
							<option value="621">621 - 花蓮地區農會</option>
							<option value="622">622 - 台東地區農會</option>
							<option value="624">624 - 澎湖農會</option>
							<option value="625">625 - 台中市農會</option>
							<option value="627">627 - 連江縣農會</option>
							<option value="700">700 - 中華郵政</option>
							<option value="803">803 - 聯邦商業銀行</option>
							<option value="805">805 - 遠東銀行</option>
							<option value="806">806 - 元大銀行</option>
							<option value="807">807 - 永豐銀行</option>
							<option value="808">808 - 玉山銀行</option>
							<option value="809">809 - 萬泰銀行</option>
							<option value="810">810 - 星展銀行</option>
							<option value="812">812 - 台新銀行</option>
							<option value="814">814 - 大眾銀行</option>
							<option value="815">815 - 日盛銀行</option>
							<option value="816">816 - 安泰銀行</option>
							<option value="822">822 - 中國信託</option>
							<option value="901">901 - 大里市農會</option>
							<option value="903">903 - 汐止農會</option>
							<option value="904">904 - 新莊農會</option>
							<option value="912">912 - 冬山農會</option>
							<option value="916">916 - 草屯農會</option>
							<option value="922">922 - 台南市農會</option>
							<option value="928">928 - 板橋農會</option>
							<option value="951">951 - 北農中心</option>
							<option value="954">954 - 中南部農漁會</option>
					</select>
					</label>
					<jsp:include page="/front-end/cart/pay_page.jsp" flush="true" />

				</div>
			</div>
		</div>


		<input type="hidden" name="action" value="checkout" />
	</form>
	<div class="footer_btn">
		<div class="row">
			<div class="col-sm-6">
				<a style="text-align: center; margin: 15px 0px 15px 50px;color:#FFB6C1" class="btn btn-link pull-left" href="<%=request.getContextPath()%>/front-end/cart/protect/cart_page.jsp">&lt; 返回購物車</a>
			</div>
			<div class="col-sm-6">

				<div id="checkout">
					<a class="btn btn-custom btn-color-primary btn-link pull-right" href="#" style="margin: 0px 100px 0px ; width: 100px;" onclick="checkOut();">結帳</a>
				</div>

			</div>
		</div>
	</div>
<%-- <jsp:include page="/front-end/header_footer/footer.jsp" flush="true" /> --%>
</body>

</html>