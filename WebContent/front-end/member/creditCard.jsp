<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	}
	;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.form1 {
	margin: 100px;
}

#confrim {
	float: right;
}

a {
	text-decoration: none;
}

.payment-radioGroup img {
	vertical-align: middle;
	width: 10%;
}

.payment-radioGroup label {
	font-weight: 600;
	margin-right: 3%;
	cursor: pointer;
}

.payment-tab {
	padding: 4%;
	border: 1px solid #d0d0d0;
	margin-bottom: 3%;
	/*background: #ebebeb;	*/
}

.creditCart-form {
	margin: 5% 0% 2%;
}

.textInputGroup {
	margin: 2% 0%;
}

.textInputGroup label {
	display: inline-block;
	width: 30%;
}

.textInputGroup input {
	padding: 2%;
	width: 50%;
	border: 1px solid #e4e4e4;
}

#cardNumber::-webkit-input-placeholder {
	color: #d8d8d8;
}

#ccMonth, #ccYear {
	padding: 2%;
	border: 1px solid #e4e4e4;
}

#cvc {
	width: 10%;
}

.complete-purchase {
	margin: 2% 0% 16%;
}

.complete-purchase a {
	color: #1f9a04;
}

.complete-purchase p {
	display: inline-block;
	width: 55%;
	margin-left: 3%;
	font-size: 14px;
	line-height: 21px;
	color: grey;
	vertical-align: middle;
}

.purchase-button {
	background: #1f9a04;
	border: none;
	padding: 12px 30px;
	color: white !important;
	cursor: pointer;
}
.save_btn{
   text-align: end;
    }
</style>
<body>

				<div class="col-md-9">
				<h2 class="">信用卡資訊</h2>
				<div class="payment-tab" id="payment-tab-1">
					<div class="payment-radioGroup">
						<img class=""
							src="<%=request.getContextPath() %>/images/visalogo.jpg"
							alt="logo"> <img class=""
							src="<%=request.getContextPath() %>/images/MasterCard_Logo.png"
							alt="logo">
					</div>
					<div class="creditCart-form">
						<div class="textInputGroup">
							<label for="cardNumber">信用卡號碼</label> <input
								id="cardNumber" name="cardNumber"
								placeholder="1234 - 5678 - 9876 - 5432" required type="text">
						</div>
						<div class="textInputGroup textInputGroup-halfWidth">
							<label for="expirationDate">到期日</label> <select
								name="cardExpirationMonth" id="ccMonth" title="Month:">
								<option value="" selected="selected">MM</option>
								<option value="1">01</option>
								<option value="2">02</option>
								<option value="3">03</option>
								<option value="4">04</option>
								<option value="5">05</option>
								<option value="6">06</option>
								<option value="7">07</option>
								<option value="8">08</option>
								<option value="9">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
							</select> &nbsp;/&nbsp; <select name="cardExpirationYear" id="ccYear"
								title="Year:">
								<option value="" selected="selected">YYYY</option>
								<option value="2017">2017</option>
								<option value="2018">2018</option>
								<option value="2019">2019</option>
								<option value="2020">2020</option>
								<option value="2021">2021</option>
								<option value="2022">2022</option>
								<option value="2023">2023</option>
								<option value="2024">2024</option>
								<option value="2025">2025</option>
								<option value="2026">2026</option>
								<option value="2027">2027</option>
							</select>
						</div>
						<div class="textInputGroup">
							<label for="nameOnCard">持卡人姓名</label> <input
								id="nameOnCard" name="nameOnCard" required type="text">
						</div>
						<div class="save_btn">
						<button type="button" class="btn btn-success" id="save">儲存</button>
						</div>
					</div>	



	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>


	<script defer
		src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>
</body>
</html>