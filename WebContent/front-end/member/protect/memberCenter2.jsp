<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<body>
	<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />
	<%@ include file="/css/member/member_center_top.file"%>

				<div class="container content clear-fix">

					<h2 class="mt-5 mb-5">個人檔案</h2>

					<div class="row" style="height: 100%">

						<div class="col-md-12">

							<div href=# class="d-inline"></div>
							<img
								src="<%=request.getContextPath()%>/Login?memberpic=picDisplay&memberId=${memberVo.memberId}"
								width=130px style="margin: 0; border-radius: 50%;" id="piczone"><br>
							<p class="pl-2 mt-2">
								<a href="#pic" class="btn"
									style="color: #8f9096; font-weight: 600" id="pic2">更換頭相</a>
							</p>
						</div>
						<form action="<%=request.getContextPath()%>/Login" method="POST"
							id="upload" enctype="multipart/form-data">
							<input type="file" name="pic" id="pic">
						</form>
					</div>

					<div class="col-md-12">

						

							<form>
								<div class="form-group">

									<label for=memberAccount>會員帳號</label> <input type="email"
										class="form-control" id="memberAccount"
										value="${memberVo.memberAccount}" readonly>

								</div>
								<div class="form-group">

									<label for=memberName>會員名字</label> <input type="text"
										class="form-control" id="memberName"
										value="${memberVo.memberName}" readonly>

								</div>
								<div class="form-group ">

									<label for=memberBirth>生日</label> <input type="text"
										class="form-control" id="memberBirth"
										value="<fmt:formatDate value="${memberVo.memberBirth}" pattern="yyyy-MM-dd"/>"
										readonly>

								</div>
								<div class="form-group ">

									<label for=memberAddress>地址</label> <input type="text"
										class="form-control" id="memberAddress" value="${memberVo.memberAddress}">

								</div>
								<div class="form-group ">

									<label for=memberPhone>手機號碼</label> <input type="text"
										class="form-control" id="memberPhone"
										value="${memberVo.memberPhone}">
								</div>
								<div class="form-group ">

									<input type="hidden" class="memberac" id="memberCardNumber"
										value="${memberVo.memberCardNumber}">
								</div>
								<div class="form-group ">

									<input type="hidden" class="memberac" id="memberCardExpyear"
										value="${memberVo.memberCardExpyear}">
								</div>
								<div class="form-group ">

									<input type="hidden" class="memberac" id="memberCardExpmonth"
										value="${memberVo.memberCardExpmonth}">
								</div>
								<div class="form-group ">

									<input type="hidden" class="memberPassword" id="memberPassword"
										value="${memberVo.memberPassword}">
								</div>
								<div class="row mt-5">

									<div class="col">

										<button type="button" class="btn btn-primary btn-block"
											id="update" name="update">更新</button>

									</div>



								</div>

							</form>

						</div>

					</div>

	
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
	<script>
		$("#pic2").on("click", function() {
			$("#pic").click();
		})
		let pic = document.getElementById("pic");

		pic.addEventListener("change", function() {
			let piczone = document.getElementById("piczone");

			var reader = new FileReader();
			reader.readAsDataURL(this.files[0]);
			reader.addEventListener("load", function(e) {
				piczone.innerHTML = "";
				let base64 = e.target.result;
				piczone.setAttribute("src", base64);
				let obj = new FormData($("#upload")[0]);
				obj.append("action", "updatePic");
				$.ajax({
					type : "POST",
					url : "/TEA102G6/Login",
					contentType : false,
					processData : false,
					cache : false,
					data : obj,

					success : function(result) {
						let photo = document.createElement("Img");
						piczone.setAttribute("src", base64);
					},
					error : function(err) {
						alert("系統錯誤");
					}
				});
			});
		});
		let update = document.getElementById("update");
		update.addEventListener("click", function() {
			let memberName = $("#memberName").val();
			let memberNickname = $("#memberNickname").val()
			let memberGender = $("#memberGender").val()
			let memberPhone = $("#memberPhone").val()
			let memberAddress = $("#memberAddress").val()
			let memberCardNumber = $("#memberCardNumber").val()
			let memberCardExpyear = $("#memberCardExpyear").val()
			let memberCardExpmonth = $("#memberCardExpmonth").val()
			const phoneReg = new RegExp("^0(9)[0-9]{8}$");
			if(memberPhone.match(phoneReg)){
			let obj = {
				action : "updateac",
				memberName : memberName,
				memberNickname : memberNickname,
				memberGender : memberGender,
				memberPhone : memberPhone,
				memberAddress : memberAddress,
				memberCardNumber : memberCardNumber,
				memberCardExpyear : memberCardExpyear,
				memberCardExpmonth : memberCardExpmonth,
			}
	
			$.ajax({
				type : "POST",
				url : "/TEA102G6/Login",
				data : obj,
				success : function(result) {
					alert("已成功更改資訊");
				},
				error : function(err) {
					alert("系統錯誤");
				}
			});
			}else{
				alert("手機格式錯誤");
			}
		});
	</script>
</body>
</html>