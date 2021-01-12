<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Tables</title>

<!-- Custom fonts for this template -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css"
	rel="stylesheet">

<!-- Custom styles for this page -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<style>
.-none {
	display: none;
}

.update {
	width: 120px;
}

.addMember {
	float: right;
}
.col-8{
margin: 0 auto;
}
</style>
</head>

<body id="page-top">


	<%@ include file="/back-end/sb/page1.file"%>
	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Page Heading -->
		<h1 class="h3 mb-2 text-gray-800">新增會員資料</h1>
		<!-- DataTales Example -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
<span id="error"></span>
				<input type="button" value="新增會員" class="addMember">
			</div>
			<div class="card-body">
				<div class="col-8">
					<form id="form" method="post">
						<label for="memberAccount">帳號</label> <input id="memberAccount"
							class="form-control" name="memberAccount" type="text"
							placeholder="your@email.com" />
							<p id="errorAccount"></p> 
							<label for="memberPassword">密碼</label>
						<input id="memberPassword" class="form-control" name="memberPassword" type="password" /> 
						<p id="errorPassword"></p> 
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="memberGender"
								id="memberGender" value="M"
								<%=((memberVo == null) || (memberVo.getMemberGender().equals("M"))) ? "checked" : ""%>>
							<label class="form-check-label" for="inlineRadio1">男</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="memberGender"
								id="memberGender" value="F"
								<%=((((memberVo != null) && (memberVo.getMemberGender().equals("F"))))) ? "checked" : ""%>>
							<label class="form-check-label" for="inlineRadio2">女</label>
						</div>
						<br>
						<br>
						<label for="memberBirth">生日</label>
						<input id="memberBirth" class="form-control" name="memberBirth"	type="text" />
						<br>
						 <label for="memberPhone">手機</label> 
						 <input id="memberPhone" class="form-control" name="memberPhone" type="text" placeholder="0912345678" />
						  <p id="errorPhone"></p>
						
						<label for="memberName">名字</label> 
						<input id="memberName" class="form-control" name="memberName" type="text" /> <p
							id="errorName"></p> 
							<br>
							<input class="btn btn-primary form-control"	type="button" name="registered" id="registered" value="新增" />
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->

	<!-- End of Main Content -->

	<%@ include file="/back-end/sb/page2.file"%>
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<!--wrapper-->
	<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>
	<%
		java.sql.Date memberBirth = null;
		try {
			memberBirth = memberVo.getMemberBirth();
		} catch (Exception e) {
			memberBirth = new java.sql.Date(System.currentTimeMillis());
		}
	%>
	<script>
     
        $.datetimepicker.setLocale('zh');
		$('#memberBirth').datetimepicker({
  	 	theme: '',              //theme: 'dark',
   		timepicker:false,       //timepicker:true,
   		step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   		format:'Y-m-d',         //format:'Y-m-d H:i:s',
   		value: '<%=memberBirth%>'
		});
		let memberac = document.getElementById("memberAccount");
        memberac.addEventListener("blur",function(e){
      
        	let memberAccount = $("#memberAccount").val();
	  
        	let data ={
                    action:"accountMatche",
                    "memberAccount":memberAccount,
                   };
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/BackendController",
                     data:data,
                     dataType:"JSON",
                     success: function (result) {
         
                         if(result.error === "null"){
                             $("#errorAccount").text(result.msg).css("color", "red");
                         }else if(result.error==="repeat"){
                        	 $("#errorAccount").text(result.msg).css("color", "red");
                       	 }else if(result.error==="noMatche"){
                       		$("#errorAccount").text(result.msg).css("color", "red");
                       	 }else if(result.error==="true"){
                       		$("#errorAccount").text(result.msg).css("color", "green");
                       	 }
                     },
                     error: function (err) {
                         alert("系統錯誤");
                     }
                 });
        	
        });
        let memberph = document.getElementById("memberPhone");
        memberph.addEventListener("blur",function(e){
        	
        	let memberPhone = $("#memberPhone").val();
	  
        	
        	let data ={
                    action:"phoneMatche",
                    memberPhone:memberPhone,
                   };
  
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/BackendController",
                     data:data,
                     dataType:"JSON",
                     success: function (data) {
//	                    	
                         if(data.msg==="false"){
                             $("#errorPhone").text(data.errorPhone).css("color", "red");
                         }else if(data.msg==="true"){
                        	 $("#errorPhone").text("手機格式正確").css("color", "green");
                        	 
                         }
                     },
                     error: function (err) {
                         alert("系統錯誤");
                     }
                 });
        });
        let memberpwd = document.getElementById("memberPassword");
        memberpwd.addEventListener("blur",function(e){
        	
        	let memberPassword = $("#memberPassword").val();
	  
        	
        	let data ={
                    action:"passwordCheck",
                    memberPassword:memberPassword,
                   };
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/BackendController",
                     data:data,
                     dataType:"JSON",
                     success: function (data) {
//	                    	
                         if(data.msg==="length"){
                             $("#errorPassword").text(data.errorPassword).css("color", "red");
                         }else if(data.msg==="isEmpty"){
                        	 $("#errorPassword").text(data.errorPassword).css("color", "red");
                         }else if(data.msg==="true"){
                        	 $("#errorPassword").text(data.errorPassword).css("color", "green");
                         }
                     },
                     error: function (err) {
                         alert("系統錯誤");
                     }
                 });
 
        });
        let memberNm = document.getElementById("memberName");
        memberNm.addEventListener("blur",function(e){
        	let memberName = $("#memberName").val();
	  
        	
        	let data ={
                    action:"nameCheck",
                    memberName:memberName,
                   };
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/BackendController",
                     data:data,
                     dataType:"JSON",
                     success: function (data) {
//	                    	
                         if(data.msg==="length"){
                             $("#errorName").text(data.errorName).css("color", "red");
                         }else if(data.msg==="true"){
                        	 $("#errorName").text(data.errorName).css("color", "green");
                         }
                     },
                     error: function (err) {
                         alert("系統錯誤");
                     }
                 });
        });
        let registered = document.getElementById("registered");
        registered.addEventListener("click",function(){
        	if($("#errorAccount").text()==="帳號可以使用"&&$("#errorPassword").text()==="密碼符合要求"&&$("#errorPhone").text()==="手機格式正確"&&$("#errorName").text()==="OK"){
        	let memberAccount = $("#memberAccount").val();
        	let memberPassword = $("#memberPassword").val();
        	let memberPhone = $("#memberPhone").val();
        	let memberGender = $("#memberGender").val();
        	let memberName = $("#memberName").val();
        
        	let memberBirth = $("#memberBirth").val();
        	
        	
        	let data = {
        			action : "addMember",
        			memberAccount : memberAccount,
    	        	memberPassword : memberPassword,
    	        	memberPhone : memberPhone,
    	        	memberGender : memberGender,
    	        	memberName : memberName,
    	        
    	        	memberBirth : memberBirth,
    	        	
                   };
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/BackendController",
                     data:data,
                     dataType:"JSON",
                     success: function (data) {
//	                    	
                         if(data.msg==="true"){            	 
                        	 $("#error").text(data.status).css("color", "green");
                        	 $("#memberAccount").val("");
                         	 $("#memberPassword").val("");
                         	 $("#memberPhone").val("");
                         	 $("#memberName").val("");
                         	$("#errorName").text("");
                         	$("#errorPassword").text("");
                         	$("#errorPhone").text("");
                         	$("#errorAccount").text("");
                         }else{
                        	 $("#error").text("新增失敗").css("color", "red");
                         }
                     },
                     error: function (err) {
                         alert("系統錯誤");
                     }
                 });
        	}else{
      			alert("資料欄位有誤請確認");
        	}
        });
	</script>

</body>

</html>