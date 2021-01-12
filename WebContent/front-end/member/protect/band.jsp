<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
	BandService bandService = new BandService();
	BandVO bandVO = bandService.getOneBand(memberVo.getBandId());
	session.setAttribute("bandVO", bandVO);
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
#pic {
	display: none;
}

.container content {
	text-align: -webkit-center;
	border: 2px solid;
	border-radius: 50px;
}
</style>
<body>
	<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>
	<div class="container content clear-fix">

		<h2 class="mt-5 mb-5">樂團檔案</h2>

		<div class="row" style="height: 100%">

			<div class="col-md-3">

				<div href=# class="d-inline"></div>
				<img
					src="<%=request.getContextPath()%>/Login?memberpic=picDisplay&memberId=${memberVo.memberId}"
					width=130px style="margin: 0; border-radius: 50%;" id="piczone"><br>
				<p class="pl-2 mt-2">
					<a href="#pic" class="btn" style="color: #8f9096; font-weight: 600"
						id="pic2">更換頭相</a>
				</p>
			</div>
			<form action="<%=request.getContextPath()%>/Login" method="POST"
			id="upload" enctype="multipart/form-data">
			<input type="file" name="pic" id="pic">
		</form>

		<div class="col-md-8">
			<form>
				<div class="form-group">

					<label for=bandId>樂團編號</label> <input type="text"
						class="form-control" id="bandId" value="${bandVO.band_id}"
						readonly>

				</div>
				<div class="form-group">

					<label for=bandName>樂團名稱</label> <input type="text"
						class="form-control" id="bandName" value="${bandVO.band_name}"
						readonly>
				</div>

				<div class="form-group">

					<label for=bandId>樂團加入時間</label> <input type="text"
						class="form-control" id="bandId"
						value="<fmt:formatDate value="${bandVO.band_add_time}" pattern="yyyy-MM-dd" />"
						readonly>

				</div>
				<div class="mb-3">
					<label for="exampleFormControlTextarea1" class="form-label">樂團簡介</label>
					<textarea class="form-control" id="bandIntro"
						rows="3">${bandVO.band_intro}</textarea>
				</div>
				<div class="row mt-5">
					<div class="col">
						<button type="button" class="btn btn-primary btn-block"
							id="updateBandBtn" name="updateBandBtn">更新</button>
					</div>
				</div>
			</form>
		</div>
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
		let updateBandBtn = document.getElementById("updateBandBtn");
		updateBandBtn.addEventListener("click", function() {
			let bandId = $("#bandId").val()
			let bandIntro = $("#bandIntro").val()
			let obj = {
				action : "updateBand",
				bandId : bandId,
				"bandIntro" : bandIntro,
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
		});
	</script>

</body>
</html>