<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.* , com.cart.model.CartVO"%>
<%@ page
	import="static com.cart.controller.CartServlet.SESSION_CART_KEY"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單詳情</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<style type="text/css">
body {
	background-color: #f3f3f3;
}

div.form1 {
	border: 1px solid black;
	background-color: white;
	border-color: #f3f3f3;
	border-style: solid;
	border-width: 2px;
	padding: 0px, 50px, 50px, 50px;
	position: relative;
	min-height: 1px;
	padding-right: 15px;
	padding-left: 15px;
	margin: 50px;
}

div.total {
	text-align: right;
}

div.btn_back {
	float: right;
}

div.orders_title {
	text-align: center;
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
	background-color: #f5b3b5;
}

a.btn-color-primary {
	background-color: #f9595f;
}

th {
	text-align: center; /** 设置水平方向居中 */
	vertical-align: middle /** 设置垂直方向居中 */
}

td {
	text-align: center; /** 设置水平方向居中 */
	vertical-align: middle /** 设置垂直方向居中 */
}

*{
    margin: 0;
    padding: 0;
}
.rate {
    float: left;
    height: 46px;
    padding: 0 10px;
}
.rate:not(:checked) > input {
    position:absolute;
    top:-9999px;
}
.rate:not(:checked) > label {
    float:right;
    width:1em;
    overflow:hidden;
    white-space:nowrap;
    cursor:pointer;
    font-size:30px;
    color:#ccc;
}
.rate:not(:checked) > label:before {
    content: '★ ';
}
.rate > input:checked ~ label {
    color: #ffc700;    
}
.rate:not(:checked) > label:hover,
.rate:not(:checked) > label:hover ~ label {
    color: #deb217;  
}
.rate > input:checked + label:hover,
.rate > input:checked + label:hover ~ label,
.rate > input:checked ~ label:hover,
.rate > input:checked ~ label:hover ~ label,
.rate > label:hover ~ input:checked ~ label {
    color: #c59b08;
}



div.outer_ring {
	border: 1px solid black;
}
hr.new3 {
	border: 1px solid #f9595f;
}
</style>

</head>

<body>

<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	
	<%@ include file="/css/member/member_center_top.file" %>


	<div class="form1">
		<div class="orders_title">
			<h1>訂單詳情</h1>
		</div>
		<h2>訂單明細</h2>
		<hr class="new3" size="12px" align="center" width="100%"
						color="#f9595f">
						
	
		
		<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/*"></script>
		<script src="<%=request.getContextPath()%>/vendors/jquery/*"></script>
		<script src="http://malsup.github.io/jquery.blockUI.js"></script>
		<script>
		var orderlist_id = '';
		function showReviewDialog(id,review_msg,review_score){
			$("#review_msg").val(review_msg);
			if(review_score){
				$("input[name='rate'][value='"+review_score+"']").attr('checked', 'checked');
			}else{
				$("input[name='rate'][value='3']").attr('checked', 'checked');
			}
			
            $.blockUI(
            		{ 
            			message: $('#reviewDiv'), 
            			css: { width: '500px' } }
            		); 
            orderlist_id = id;
		}
		

		</script>
		<div class="form2">
			<table class="table table-hover">
				<thead>
					<tr>

						<th scope="col">票種名稱</th>
						<th scope="col">單價</th>
						<th scope="col">數量</th>
						<th scope="col">小計</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="orderListVO">
						<tr>

							<th scope="row">${ticketSvc.getOneTicket(orderListVO.ticket_id).ticket_name}</th>
							<td><fmt:formatNumber value="${ticketSvc.getOneTicket(orderListVO.ticket_id).ticket_price}"
									pattern="$#,###" /></td>
							<td>${orderListVO.orderlist_goods_amount}</td>
							<td><fmt:formatNumber
									value="${ticketSvc.getOneTicket(orderListVO.ticket_id).ticket_price*orderListVO.orderlist_goods_amount}"
									pattern="$#,###" /></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>

		<div class="total">
			<h3>總金額:NT${amountPrice}</h3>
		</div>
		<br>
		<div class="btn_back">
			<a id="back_page" class="btn btn-custom btn-color-primary"
				style="margin-bottom: 0px;">返回我的訂單</a>
		</div>
		
		<%@ include file="/css/member/member_center_bottom.file" %>
		<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />

		<script>
			$("#back_page").on("click", function() {
				console.log("history.back()");
				history.back();
			})
		</script>

</body>
</html>