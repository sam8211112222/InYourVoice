<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.* , com.cart.model.CartVO,com.orders.model.OrdersVO"%>
<%@ page import="static com.cart.controller.CartServlet.SESSION_CART_KEY"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單詳情</title>
<style type="text/css">
body {
	background-color: #f3f3f3;
}

div.form1 {
	
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
	border-style: solid white !important;
	border-color: #f9595f;
}

a.btn-color-primary:hover {
	background-color: #f5b3b5;
}

a.btn-color-primary {
	background-color: #f9595f;
}

th.form2 {
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
td.detailimg{
    text-align: center ; /** 设置水平方向居中 */
	vertical-align: middle;
	
}
img.pimg {
    width: 80px;
    height: 80px;
    margin:auto ;
</style>

</head>

<body>
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	
	<%@ include file="/css/member/member_center_top.file" %>
<script>

function addToCart(){
	
	$.ajax({
	    type: "post",
	    url: "<%=request.getContextPath()%>/cart/cartServlet",
				dataType : "json",
				data : {
					"action" : "addToCart",
					"productId" : "${productVO.product_id}",
					"productName" : "${productVO.product_name}",
					"productPrice" : "${productVO.product_price}",
					"productPhotoId" : "",
					"quantity" : $("#quantity").val()
				},
				success : function(response) {
					if (true == response) {
						alert("加入購物車成功");
					} else if (false == response) {
						alert("加入購物車失敗");
					}
				},
				error : function(thrownError) {
					console.log(thrownError);
					alert("加入購物車失敗");
				}
			});

		}
	</script>


	<div class="form1">
		<div class="orders_title">
			<h1>訂單詳情</h1>
		</div>
		<h2>訂單明細</h2>
		<hr class="new3" size="12px" align="center" width="100%"
						color="#f9595f">
		
		
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
		

		function reviewItem(){
			
			$.ajax({
			    type: "post",
			    url: "<%=request.getContextPath()%>/orderList/orderListServlet",
							dataType : "json",
							data : {
								"action" : "reviewItem",
								"orderlist_id" : orderlist_id,
								"review_msg" : $("#review_msg").val(),
								"review_score" : $("input[name=rate]:checked").val()
							},
							success : function(response) {
								if (true == response) {
									alert("評價成功");
									$.unblockUI();
									location.reload();
								} else if (false == response) {
									alert("評價失敗");
								}
							},
							error : function(thrownError) {
								console.log(thrownError);
								alert("評價失敗");
							}
						});

			}
		
		
		
		</script>
		<div class="form2">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col" class="form2">商品照片</th>
						<th scope="col" class="form2">商品名稱</th>
						<th scope="col" class="form2">單價</th>
						<th scope="col" class="form2">數量</th>
						<th scope="col" class="form2">小計</th>
						<th scope="col" class="form2">評價</th>

					</tr>
				</thead>
				<tbody>

					<c:forEach items="${list}" var="orderListVO">
						<tr>

							<td class="dsetailimg"><a href="<%= request.getContextPath() %>/product/YUproductServlet?action=show_me_one&id=${orderListVO.product_id}"><img class="pimg" src="${pageContext.request.contextPath}/productphoto/YUproductPhotoServlet?id=${orderListVO.product_id}" /></a></td>
							<td class="form2">${productSvc.getOneProduct(orderListVO.product_id).product_name}</td>
							<td class="form2">NT<fmt:formatNumber value="${orderListVO.price}"
									pattern="$#,###" /></td>
							<td class="form2">${orderListVO.orderlist_goods_amount}</td>
							<td class="form2">NT<fmt:formatNumber
									value="${orderListVO.price*orderListVO.orderlist_goods_amount}"
									pattern="$#,###" /></td>
							<td class="form2" align="center" valign="center">
<%--  							<c:if test="${orderListVO.order_status==0}"> --%>
<!--  							<h3>尚未可以評價</h3>						  -->
<%--      						</c:if>  --%>
								<c:if test="${order.order_status==1}">
									<c:if test="${orderListVO.review_time==null}">
										<a class="btn btn-custom btn-color-primary"
											onclick="showReviewDialog('${orderListVO.orderlist_id}');">評價商品</a>
									</c:if> 
									<c:if test="${orderListVO.review_time!=null}">
										<a class="btn btn-custom btn-color-primary"
											onclick="showReviewDialog('${orderListVO.orderlist_id}','${orderListVO.review_msg}','${orderListVO.review_score}');">修改評價</a>
									</c:if>
								</c:if>
								
								</td>
						
						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>

		<div class="total">
			<h3>總金額:NT<fmt:formatNumber
									value="${realTotalPrice}"
									pattern="$#,###" /> </h3>
		</div>
		<br>
<!-- 		<div class="btn_back"> -->
<!-- 			<a id="back_page" class="btn btn-custom btn-color-primary" -->
<!-- 				style="margin-bottom: 0px;">返回我的訂單</a> -->
<!-- 		</div> -->

		<script>
			$("#back_page").on("click", function() {
				history.back();
			})
		</script>

		<div id="reviewDiv" style="display: none">
		<h3>商品評價</h3>
			<div class="rate">
				<input type="radio" id="star5" name="rate" value="5" /> <label
					for="star5">5 stars</label> <input type="radio" id="star4"
					name="rate" value="4" /> <label for="star4">4 stars</label> <input
					type="radio" id="star3" name="rate" value="3" /> <label
					for="star3">3 stars</label> <input type="radio" id="star2"
					name="rate" value="2" /> <label for="star2">2 stars</label> <input
					type="radio" id="star1" name="rate" value="1" /> <label
					for="star1">1 star</label>
			</div>
			<script >
			$('#review_msg').on("input", function(){
				  var ct= $(this).val();
				  ct=ct.replace(/^(\r\n|\n|\r|\t| )+/gm, "");
				  $('#review_msg').val(ct);
				});
			
			</script>
			<textarea id="review_msg" name="review_msg" rows="10" cols="40" placeholder="請輸入評價" ></textarea>
			
			<br />
			
			<a id="back_page" class="btn btn-custom btn-color-primary"
				style="margin-bottom: 5px; width:30%;" onclick="reviewItem();">送出評價</a>
			<a id="back_page" class="btn btn-custom btn-color-primary"
				style="margin-bottom: 5px;width:30%;" onclick="$.unblockUI();">取消</a>

		</div>
	</div>
	<%@ include file="/css/member/member_center_bottom.file" %>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
	<script src="<%=request.getContextPath()%>/js/jquery/jquery.blockUI.js"></script>
</html>