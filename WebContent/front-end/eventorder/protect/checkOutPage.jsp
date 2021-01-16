<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ page import="com.member.model.*"%>
            <%@ page import="com.ticket.model.*"%>
                <%@ page import="java.util.*"%>
                    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
                        <%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	Map<String, Integer> orderList = (HashMap<String, Integer>) session
			.getAttribute("ticket" + memberVo.getMemberId());
	TicketService ticketSvc = new TicketService();
	Set<String> orderKey = orderList.keySet();
	int amount = 0;
	for (String key : orderKey) {
		amount += ((ticketSvc.getOneTicket(key).getTicket_price()) * orderList.get(key));
	}

	pageContext.setAttribute("amount", amount);
	pageContext.setAttribute("ticketSvc", ticketSvc);
	pageContext.setAttribute("orderList", orderList);
%>
                            <!DOCTYPE html>
                            <html lang="en">

                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>確認訂單</title>

                                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
                                <link rel="stylesheet" href="<%=request.getContextPath()%>/css/cart/confirm_order.css">

                                <style>
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
                                        border: 1px solid #e4e4e4;
                                        box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
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
                                    
                                    hr.new3 {
                                        border: 1px solid #f9595f;
                                    }
                                    
                                    .fake-select {
                                        position: relative;
                                    }
                                    
                                    .fake-select>select {
                                        width: 100%;
                                        height: 100%;
                                        left: 0;
                                        top: 0;
                                        position: absolute;
                                        background: transparent !important;
                                        color: transparent !important;
                                        z-index: 1;
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
                                    
                                    a.btn-custom {
                                        font-weight: 400;
                                        width: 100%;
                                        max-width: 100%;
                                        color: white !important;
                                    }
                                    
                                    a.btn-color-primary:focus {
                                        border-width: 1px;
                                        border-style: solid color: white !important;
                                        border-color: #f9595f;
                                    }
                                    
                                    a.btn-color-primary:hover {
                                        background-color: #a5d489;
                                    }
                                    
                                    a.btn-color-primary {
                                        background-color: #f9595f;
                                    }
                                    
                                    div#checkout {
                                        background-color: white;
                                        position: absolute;
                                        right: 0;
                                    }
                                    
                                    h3.title {
                                        color: rgb(70, 70, 70);
                                    }
                                    
                                    table td.tdVal {
                                        margin: 15px !important;
                                    }
                                    
                                    body {
                                        background: #fafafa !important;
                                        font-family: "Avant Garde", Avantgarde, "Century Gothic", CenturyGothic, "AppleGothic", sans-serif;
                                        color: black;
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
                                        width: 150px;
                                        height: 150px;
                                    }
                                    
                                    .image>img {
                                        width: 100%;
                                        height: 120px;
                                        background: white;
                                        border-radius: 2px;
                                        /* 	box-shadow: 0 2px 7px rgba(0, 0, 0, 0.4); */
                                        a-border: 1px solid black;
                                    }
                                    
                                    .amount {
                                        width: 50px;
                                    }
                                    
                                    .amount>input {
                                        font-family: "Avant Garde", Avantgarde, "Century Gothic", CenturyGothic, "AppleGothic", sans-serif;
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
                                        border: 1px solid #e4e4e4;
                                        box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
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
                                    
                                    hr.new3 {
                                        border: 1px solid #f9595f;
                                    }
                                    
                                    .fake-select {
                                        position: relative;
                                    }
                                    
                                    .fake-select>select {
                                        width: 100%;
                                        height: 100%;
                                        left: 0;
                                        top: 0;
                                        position: absolute;
                                        background: transparent !important;
                                        color: transparent !important;
                                        z-index: 1;
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
                                    
                                    a.btn-custom {
                                        font-weight: 400;
                                        width: 100%;
                                        max-width: 100%;
                                        color: white !important;
                                    }
                                    
                                    a.btn-color-primary:focus {
                                        border-width: 1px;
                                        border-style: solid color: white !important;
                                        border-color: #f9595f;
                                    }
                                    
                                    a.btn-color-primary:hover {
                                        background-color: #a5d489;
                                    }
                                    
                                    a.btn-color-primary {
                                        background-color: #f9595f;
                                    }
                                    
                                    div#checkout {
                                        background-color: white;
                                        position: absolute;
                                        right: 0;
                                    }
                                    
                                    h3.title {
                                        color: rgb(70, 70, 70);
                                    }
                                    
                                    table td.tdVal {
                                        margin: 15px !important;
                                    }
                                    
                                    img.td {
                                        width: 200px;
                                        heigh: 100px;
                                    }
                                    /* div.control-label{ */
                                    /* display: block; */
                                    /*     width: 100%; */
                                    /*     height: calc(2.25rem + 2px); */
                                    /*     padding: .375rem .75rem; */
                                    /*     font-size: 1rem; */
                                    /*     font-weight: 400; */
                                    /*     line-height: 1.5; */
                                    /*     color: #495057; */
                                    /*     background-color: #fff; */
                                    /*     background-clip: padding-box; */
                                    /*     border: 1px solid #ced4da; */
                                    /*     border-radius: .25rem; */
                                    /*     transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out; */
                                    /* } */
                                </style>

                            </head>


                            <body>
                                <h1 class="cart_title">確認訂單</h1>
                                <%@ include file="/front-end/header_footer/header.jsp"%>
                                    <script src="<%=request.getContextPath()%>/js/jquery/jquery.validate.min.js"></script>
                                    <script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
                                    <script src="<%=request.getContextPath()%>/js/cart/confirm_order.js"></script>

                                    <div class="container">
                                    </div>
                                    <div class="productList">
                                        <div id="container">

                                            <div>
                                                <table>
                                                    <tr style="text-align: center;">
                                                        <td class="tdVal"></td>
                                                        <td class="tdVal">票種名稱</td>
                                                        <td class="tdVal">票種單價</td>
                                                        <td class="tdVal">數量</td>
                                                        <td class="tdVal">小計</td>
                                                        <td class="tdVal"></td>
                                                    </tr>

                                                    <c:forEach items="${orderList}" var="order">

                                                        <tr class="p">

                                                            <td class="image"><img class="td" src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${event_id}" /></td>
                                                            <td class="name">${ticketSvc.getOneTicket(order.key).ticket_name}</td>
                                                            <td class="price">NT
                                                                <fmt:formatNumber value="${ticketSvc.getOneTicket(order.key).ticket_price}" pattern="$#,###" />
                                                            </td>
                                                            <td class="amount">${order.value}</td>
                                                            <td class="pricesubtotal">NT
                                                                <fmt:formatNumber value="${ticketSvc.getOneTicket(order.key).ticket_price * order.value}" pattern="$#,###" />
                                                            </td>

                                                        </tr>
                                                    </c:forEach>


                                                    <tr>

                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>總價:</td>
                                                        <td class="totalpricesubtotal">NT$${amount}</td>
                                                        <td></td>
                                                    </tr>


                                                </table>

                                            </div>
                                        </div>
                                    </div>

                                    <form name="customerForm" id="customerForm" method="POST" action="<%=request.getContextPath()%>/EventOrderController">
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
                                                            <label for="order-customer-name" class="control-label">顧客名稱</label> &nbsp;&nbsp;
                                                            <span id="order-customer-name-span" style="color: red;"></span> <input id="order-customer-name" type="text" class="form-control" name="orderName" value="">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="order-customer-mail" class="control-label">電子信箱</label> &nbsp;&nbsp;
                                                            <span id="order-customer-mail-span" style="color: red;"></span> <input id="order-customer-mail" type="text" class="form-control" name="orderMail" value="">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="order-customer-phone" class="control-label">連絡電話</label> &nbsp;&nbsp;
                                                            <span id="order-customer-phone-span" style="color: red;"></span> <input id="order-customer-phone" type="text" class="form-control" name="orderPhone" value="">
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
                                                    </div>
                                                    <jsp:include page="/front-end/cart/pay_page.jsp" flush="true" />

                                                </div>
                                            </div>
                                        </div>

                                        <!-- Order Information -->

                                        <!-- Remark Form -->

                                        <!-- 		<input type="hidden" name="action" value="checkout" /> -->
                                        <%-- 		<%-- 			<jsp:include page="<%=request.getContextPath()%>/front-end/cart/pay_page.jsp" flush="true"/> --%>

                                            <!-- 		<div id="checkout"> -->
                                            <!-- 			<a href="#" onclick="checkOut();">結帳</a> -->
                                            <!-- 		</div> -->
                                            <input type="hidden" name="action" value="check-out"> <input type="hidden" name="event_id" value="${event_id}">
                                    </form>
                                    <div class="footer_btn">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <a style="text-align: center; margin: 15px;" class="btn btn-link pull-left" href="<%=request.getContextPath()%>/EventOrderController?action=cancel&event_id=${event_id}"><button
							type="button" class="card-form__button" id="cancelbutton" style="width: 200px; background:#2cbcf4;">取消</button></a>
                                            </div>
                                            <div class="col-sm-6">

                                                <div id="checkout">
                                                    <a class="btn btn-custom  btn-link pull-right" href="#" style="margin-bottom: 0px; width: 100%;"><button type="button" class="card-form__button" id="cancelbutton btn-color-primary" style="width: 200px; background:#f9595f;" onclick="checkOut();">結帳</button></a>
                                                </div>
                                                <!-- 				<a -->
                                                <%-- 					href='${pageContext.request.contextPath}/front-end/cart/confirm_order.jsp'> --%>
                                                    <!-- 					<button id="place-order-btn" class="btn btn-success" onclick="checkOut();"> -->
                                                    <!-- 						<span class="ladda-label"> 提交訂單 </span><span class="ladda-spinner"></span> -->
                                                    <!-- 					</button> -->

                                                    <!-- 				</a> -->
                                            </div>
                                        </div>
                                    </div>
                                    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->

                                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
                                    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
                                    <script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
                                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
                                    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
                                    <script>
                                        function checkOut() {
                                            document.getElementById("customerForm").submit();
                                        }


                                        //判斷email
                                        function checkUserEntryMail() {

                                            let userEntry = $("#order-customer-mail").val();
                                            console.log(userEntry);
                                            let errMsg = $("#order-customer-mail-span");
                                            if (userEntry.length === 0) {
                                                errMsg.text('*請輸入字串');
                                            } else if (userEntry.indexOf('@') === -1) {
                                                errMsg.text('*請提供合法的Email住址');
                                            }
                                        }

                                        $("#order-customer-mail").on("blur", function() {
                                            checkUserEntryMail();
                                        });


                                        //判斷名字
                                        // 		function checkUserEntryName() {

                                        // 			let userEntry = $("#order-customer-name").val();
                                        // 			let errMsg = $("#order-customer-name-span");
                                        // 			if (userEntry.length === 0) {
                                        // 				errMsg.text('*名字請勿空白');
                                        // 			}
                                        // 		}
                                        // 		$("#order-customer-name").on("blur", function() {
                                        // 			checkUserEntryName();
                                        // 		});
                                    </script>
                                    <script>
                                        var MyPointLeo = "/TimeOut/<%=memberVo.getMemberId()%>";
                                        var hostLeo = window.location.host;
                                        var pathLeo = window.location.pathname;
                                        var webCtxLeo = pathLeo.substring(0, pathLeo.indexOf('/', 1));
                                        var endPointURLLeo = "ws://" + window.location.host + webCtxLeo + MyPointLeo;

                                        var webSocketLeo;


                                        function connectLeo() {
                                            // create a websocket
                                            console.log(endPointURLLeo)
                                            webSocketLeo = new WebSocket(endPointURLLeo);

                                            webSocketLeo.onopen = function(event) {}

                                            webSocketLeo.onmessage = function(event) {
                                                var message = event.data;
                                                if (message == "go-out") {
                                                    alert("已超過購票時間,請返回前頁重新選擇");
                                                    history.back();
                                                }
                                            }

                                            webSocketLeo.onclose = function(event) {}
                                        }

                                        function defaultMemberData() {
                                            if ($("#defalutMemberDataCb").is(':checked')) {
                                                $("#order-customer-name").val("${memberVo.memberName}");
                                                $("#order-customer-mail").val("${memberVo.memberAccount}");
                                                $("#order-customer-phone").val("${memberVo.memberPhone}");
                                                $("#order-customer-address").val("${memberVo.memberAddress}");
                                            }
                                        }

                                        $(function() {
                                            alert(
                                                '您所選擇的訂單票券將為您保留10分鐘,請您在時限內完成結帳手續。如欲重新選擇票券,請點擊下方的取消按鈕!'
                                            )
                                            connectLeo();
                                        });


                                        function disconnect() {
                                            webSocket.close();
                                        }

                                        // 		function validateForm(){
                                        // 			return $("#customerForm").validate({
                                        // 				  rules: {
                                        // 					  orderName: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  orderMail: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  orderPhone: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  cardNumber: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  cardName: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  cardMonth: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  cardYear: {
                                        // 						  required: true
                                        // 					  },
                                        // 					  cardCvv: {
                                        // 						  required: true
                                        // 					  }

                                        // 				  },
                                        // 				  messages:{
                                        // 					  orderName: {
                                        // 					      required: "<span style='color:red'>請輸入顧客名稱</span>"
                                        // 					  },
                                        // 					  orderMail: {
                                        // 						  required: "<span style='color:red'>請輸入電子信箱</span>"
                                        // 					  },
                                        // 					  orderPhone: {
                                        // 						  required: "<span style='color:red'>請輸入連絡電話</span>"
                                        // 					  },


                                        // 					  cardNumber: {
                                        // 						  required: "<span style='color:red'>請輸入卡號</span>"
                                        // 					  },
                                        // 					  cardName: {
                                        // 						  required: "<span style='color:red'>請輸入持卡者姓名</span>"
                                        // 					  },
                                        // 					  cardMonth: {
                                        // 						  required: "<span style='color:red'>請輸入有效月份</span>"
                                        // 					  },
                                        // 					  cardYear: {
                                        // 						  required: "<span style='color:red'>請輸入有效年份</span>"
                                        // 					  },
                                        // 					  cardCvv: {
                                        // 						  required: "<span style='color:red'>請輸入安全碼</span>"
                                        // 					  }

                                        // 				  }
                                        // 				}).form();
                                        // 		}

                                        // 		function checkOut() {
                                        // 			if(validateForm()){
                                        // 				document.getElementById("customerForm").submit();
                                        // 			}

                                        // 		}
                                        // 		// 判斷email
                                        // 		function checkUserEntryMail() {

                                        // 			let userEntry = $("#order-customer-mail").val();

                                        // 			let errMsg = $("#order-customer-mail-span");
                                        // 			if (userEntry.length === 0) {
                                        // 				errMsg.text('*請輸入字串');
                                        // 			} else if (userEntry.indexOf('@') === -1) {
                                        // 				errMsg.text('*請提供合法的Email帳號');

                                        // 			} else if (userEntry.length >= 1 && userEntry.indexOf('@') != -1) {
                                        // 				errMsg.text('');
                                        // 			}
                                        // 		}

                                        // 		$("#order-customer-mail").on("blur", function() {
                                        // 			checkUserEntryMail();
                                        // 		});

                                        // 		// 判斷電話
                                        // 		function checkUserEntryPhone() {

                                        // 			let userEntry = $("#order-customer-phone").val();
                                        // 			let errMsg = $("#order-customer-phone-span");
                                        // 			if (userEntry.length === 0) {
                                        // 				errMsg.text('*連絡電話請勿空白');
                                        // 			} else if (!(/^1[34578]\d{9}$/.test(userEntry))) {
                                        // 				errMsg.text('*連絡電話格式不對');
                                        // 			} else if (userEntry.length > 11) {
                                        // 				errMsg.text('');
                                        // 			}
                                        // 		}
                                        // 		$("#order-customer-phone").on("blur", function() {
                                        // 			checkUserEntryPhone();
                                        // 		});

                                        // 		// 判斷名字
                                        // 		function checkUserEntryName() {

                                        // 			let userEntry = $("#order-customer-name").val();
                                        // 			let errMsg = $("#order-customer-name-span");
                                        // 			if (userEntry.length === 0) {
                                        // 				errMsg.text('*名字請勿空白');
                                        // 			} else if (userEntry.length >= 1) {
                                        // 				errMsg.text('');
                                        // 			}
                                        // 		}
                                        // 		$("#order-customer-name").on("blur", function() {
                                        // 			checkUserEntryName();
                                        // 		});
                                    </script>
                                    <%@ include file="/front-end/header_footer/footer.jsp"%>
                            </body>

                            </html>