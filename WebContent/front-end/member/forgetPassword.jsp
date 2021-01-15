<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
	String memberAccount = (String)request.getParameter("memberAccount");
	MemberService memberSvc = new MemberService();
	MemberVo memberVo = memberSvc.findByAccount(memberAccount);
	session.setAttribute("memberVo",memberVo);
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Jekyll v4.1.1">
<title>更改密碼</title>



<!-- Bootstrap core CSS -->
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
</style>
<!-- Custom styles for this template -->
</head>
<body class="text-center">
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	<form class="form1">
		<div class="form-group row">
			<label for="colFormLabelSm"
				class="col-sm-2 col-form-label col-form-label-sm">新密碼</label>
			<div class="col-sm-10">
				<input type="password" class="form-control form-control-sm"
					id="newPassword1" placeholder="請輸入新密碼"><span
					class="errorPassword2"></span>
			</div>
		</div>
		<div class="form-group row">
			<label for="colFormLabelSm"
				class="col-sm-2 col-form-label col-form-label-sm">確認密碼</label>
			<div class="col-sm-10">
				<input type="password" class="form-control form-control-sm"
					id="newPassword2" placeholder="請輸入新密碼">
			</div>
		</div>
		<button type="button" class="btn btn-outline-success" id="confrim">確認</button>
	</form>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
		<script src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>
			<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
	<script>
	
	 
	let confrim = document.getElementById("confrim");
	
	
	confrim.addEventListener("click",function(){
		let password1 = $("#newPassword1").val();
		let password2 = $("#newPassword2").val();
        		if(password1===password2){
        		 $.ajax({
                     type: "POST",
                     url: "/TEA102G6/Login",
                     dataType:"JSON",
                     data:{
                    	 "action":"changePassword",
                    	 "memberPassword":password1},
                     success: function (result) {                 
                         alert("已更改完成,請用新密碼登入");
                         window.location.replace("<%=request.getContextPath()%>/front-end/member/Login.jsp");             
                         },
					 error : function(err) {
								alert("系統錯誤");
							}	
				});
        		}else{
        			$(".errorPassword2").text("請確認密碼").css("color", "red");
        		}
						});
	
	</script>
</body>
</html>
