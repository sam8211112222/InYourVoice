<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
	MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<style>
#wrapper {
	margin: auto;
	width: 600px;
}

form input {
	margin: 5px 0;
	padding: 10px 5px;
}

form label {
	display: block;
}

.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

#cities {
	height: 35px;
}

#areas {
	height: 35px;
	margin-left: 10px;
}

#addresss {
	height: 35px;
	width: 430px;
	margin-left: 10px;
}

#logo {
	width: 200px;
	height: 60px;
}
</style>
</head>

<body>
	<div id="wrapper">
		<div class="logo">
			<a href="<%=request.getContextPath()%>/index.jsp"><img src="<%=request.getContextPath()%>/images/logo.png"
				id="logo" alt="InYourVoice"></a>
		</div>
		<div class="title">
			<p>Sign up to InYourVoice</p>
		</div>
		<form id="form" method="post">
			<label for="memberAccount">帳號</label> <input id="memberAccount"
				class="form-control" name="memberAccount" type="text"
				placeholder="your@email.com" value=""/> <span class="error">${errors.account}</span><span
				id="errorAccount"></span> <label for="memberPassword">密碼</label> <input
				id="memberPassword" class="form-control" name="memberPassword"
				type="password" value=""/> <span id="errorPassword"></span> <label
				for="memberBirth">生日</label> <input id="memberBirth"
				class="form-control" name="memberBirth" type="text" /> <label
				for="memberPhone">手機</label> <input id="memberPhone"
				class="form-control" name="memberPhone" type="text"
				placeholder="請輸入手機號碼" value=""/> <span id="errorPhone"></span> <br>
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
			<label for="memberName">名字</label> <input id="memberName"
				class="form-control" name="memberName" type="text" /> <span
				id="errorName"></span> <label for="memberNickname">會員暱稱</label> <input
				id="memberNickname" class="form-control" name="memberNickname"
				type="text" /> <label for="memberAddress">地址</label> <input
				id="address" class="twaddress" name="memberAddress" value="" /> <input
				class="btn btn-primary form-control" type="button" name="registered"
				id="registered" value="註冊" /> <input id="reset"
				class="btn btn-danger form-control" type="reset" />
		</form>
	</div>
	<button class="btn" id="val">神奇小按鈕</button>
	<!--wrapper-->

	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/member/address.js"></script>
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
                     url: "/TEA102G6/Login",
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
                     url: "/TEA102G6/Login",
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
                     url: "/TEA102G6/Login",
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
                     url: "/TEA102G6/Login",
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
        	let memberGender = $("#memberGenderM").val();
        	let memberName = $("#memberName").val();
        	let memberNickname = $("#memberNickname").val();
        	let memberBirth = $("#memberBirth").val();
        	let memberAddress = $("#address").val();
        	
        	let data = {
        			action : "registered",
        			memberAccount : memberAccount,
    	        	memberPassword : memberPassword,
    	        	memberPhone : memberPhone,
    	        	memberGender : memberGender,
    	        	memberName : memberName,
    	        	memberNickname : memberNickname,
    	        	memberBirth : memberBirth,
    	        	memberAddress : memberAddress
                   };
            $.ajax({
                     type: "POST",
                     url: "/TEA102G6/Login",
                     data:data,
                     dataType:"JSON",
                     success: function (data) {
//	                    	
                         if(data.msg==="true"){
                             alert(data.status);
                             window.location.replace("<%=request.getContextPath()%>/front-end/member/Login.jsp");
												} else {
													alert("notgood");
												}
											},
											error : function(err) {
												alert("系統錯誤");
											}
										});
							} else {
								alert("資料欄位有誤請確認");
							}
						});
        $("#val").click(function(){
        	$("#memberAccount")[0].value = "kevinwei326@gmail.com";
        	$("#memberPassword")[0].value = "1234";
        	$("#memberPhone")[0].value = "0921199234";
        	$("#memberName")[0].value = "王曉明";
        	$("#memberNickname")[0].value = "曉明";
        	$("#addresss")[0].value = "南京西路";
        })
	</script>
</body>

</html>