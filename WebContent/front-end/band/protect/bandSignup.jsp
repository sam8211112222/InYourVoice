<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo.getBandId() != null) {
		BandService bandSvc = new BandService();
		BandVO bandVo = bandSvc.getOneBand(memberVo.getBandId());
		session.setAttribute("bandVo", bandVo);
	}
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
</head>
<style>
.row {
	text-align: center;
}

.col-12 {
	text-align: center;
}

audio {
	position: fixed;
	margin-top: 65px;
	width: 350px;
}

#bannerpic {
	margin: 0 auto;
}

#info {
	margin-top: 90px;
}
</style>

<body>
	<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/band/band.do">
		<div class="col-12">
			<h1>
				樂團申請表單 <span class="badge badge-secondary"><i class="fas fa-music"></i></span>
			</h1>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-5">
					<div class="form-group">
						<label for="bandName">樂團名稱</label>
						<c:if test="${memberVo.bandId!=null}">
							<input type="text" class="form-control" id="bandName" name="bandName" value="${bandVo.band_name}">
							<small id="emailHelp" class="form-text text-muted">請輸入完整名稱</small>
						</c:if>
						<c:if test="${memberVo.bandId==null}">
							<input type="text" class="form-control" id="bandName" name="bandName" required>
							<small id="emailHelp" class="form-text text-muted">請輸入完整名稱</small>
						</c:if>
					</div>
					<c:if test="${memberVo.bandId!=null}">
						<div class="input-group" id="piececheck">
							<div class="custom-file">

								<input type="file" class="custom-file-input" id="bandPieceCheck" name="bandPieceCheck"> <label class="custom-file-label" for="validatedInputGroupCustomFile">更換其他作品</label>
								<canvas id="canvas"></canvas>
								<audio id="audio" controls>
									<source src="<%=request.getContextPath() %>/band/band.do?action=getSong&bandId=${memberVo.bandId}" type="audio/ogg">
								</audio>
							</div>

						</div>
					</c:if>
					<c:if test="${memberVo.bandId==null}">
						<div class="input-group" id="piececheck">
							<div class="custom-file">

								<input type="file" class="custom-file-input" id="bandPieceCheck" name="bandPieceCheck" required> <label class="custom-file-label" for="validatedInputGroupCustomFile">請上傳一首作品</label>
								<canvas id="canvas"></canvas>
								<audio id="audio" controls>
								</audio>
							</div>

						</div>
					</c:if>
					<div class="invalid-feedback">必須上傳一首作品</div>
					<div class="mb-3">
						<label for="bandIntro" id="info">自我介紹</label>
						<c:if test="${memberVo.bandId==null}">
							<textarea class="form-control" id="bandIntro" name="bandIntro" placeholder="請輸入樂團介紹" required></textarea>
						</c:if>
						<c:if test="${memberVo.bandId!=null}">
							<textarea class="form-control" id="bandIntro" name="bandIntro" required>${bandVo.band_intro}</textarea>
						</c:if>
						<div class="invalid-feedback">此欄位不能空白</div>
					</div>
					<c:if test="${memberVo.bandId==null}">
						<button type="submit" class="btn btn-primary" id="submit">申請</button>
						<input type="hidden" name="action" value="bandSignup">
						<input type="hidden" name="memberId" value="${memberVo.memberId}">
					</c:if>
					<c:if test="${bandVo.band_status==3}">
						<div class="alert alert-danger" role="alert">已被退回!!!請重新申請</div>
						<input type="hidden" name="action" value="bandSignupUpdate">
						<input type="hidden" name="bandId" value="${bandVo.band_id}">
						<input type="hidden" name="bandStatus" value="0">
						<button type="submit" class="btn btn-primary" id="submit">重新申請</button>

					</c:if>
					<c:if test="${bandVo.band_status==0}">
						<a href="<%=request.getContextPath()%>/front-end/member/protect/memberCenter2.jsp"><div class="alert alert-warning" role="alert">申請中 請等待回覆</div></a>
					</c:if>
					<c:if test="${bandVo.band_status==1}">
						<a href="<%=request.getContextPath()%>/front-end/member/protect/memberCenter2.jsp"><div class="alert alert-success" role="alert">樂團審核已通過!!</div></a>
					</c:if>
				</div>
				<div class="col-7">

					<label for="bandBanner">上傳封面照片</label>
					<c:if test="${memberVo.bandId==null}">
						<div class="input-group" id="bandbannerpic">
							<div class="custom-file">

								<input type="file" class="custom-file-input" id="bandBanner" name="bandBanner" required> <label class="custom-file-label" for="validatedInputGroupCustomFile">請上傳一張樂團封面照</label>
							</div>

						</div>
						<div class="invalid-feedback">必須上傳一張封面照片</div>
						<div class="col-7" id="bannerpic">
							<img src="<%=request.getContextPath()%>/images/無圖片.png" class="img-fluid" alt="Responsive image">
						</div>
					</c:if>
					<c:if test="${memberVo.bandId!=null}">
						<div class="input-group" id="bandbannerpic">
							<div class="custom-file">

								<input type="file" class="custom-file-input" id="bandBanner" name="bandBanner"> <label class="custom-file-label" for="validatedInputGroupCustomFile">請上傳一張樂團封面照</label>
							</div>

						</div>
						<div class="invalid-feedback">必須上傳一張封面照片</div>
						<div class="col-7" id="bannerpic">
							<img src="<%=request.getContextPath()%>/band/band.do?action=getBanner&bandId=${memberVo.bandId}" class="img-fluid" alt="Responsive image">
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</form>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	<script defer src="https://use.fontawesome.com/releases/v5.0.7/js/all.js">
		
	</script>
	<script>
		$("#bandBannerBtn").click(function(e) {
			e.stopPropagation();
			var fileInput = $('#bandBanner').val();
			if (fileInput == "") {
				$("#bandbannerpic").addClass("is-invalid");
			}
		})
		$('#bandBanner').change(function() {
			var reader = new FileReader();
			reader.readAsDataURL(this.files[0]);
			let pic = document.getElementsByClassName("img-fluid")[0];
			reader.addEventListener("load", function(e) {
				pic.innerHTML = "";
				let base64 = e.target.result;
				console.log(base64);
				pic.setAttribute("src", base64);
			})
		})
		$("#bandPieceCheckBtn").click(function(e) {
			e.stopPropagation();
			var fileInput = $('#bandPieceCheck').val();
			if (fileInput == "") {
				$("#piececheck").addClass("is-invalid");
			}
		})
		$("#bandIntro").blur(function() {
			var fileInput = $('#bandinfo').val();
			if (fileInput == "") {
				$("#info").addClass("is-invalid");
			} else {
				$("#info").removeClass("is-invalid");
			}
		})
		window.onload = function() {

			var file = document.getElementById("bandPieceCheck");
			var audio = document.getElementById("audio");

			file.addEventListener("change", function() {
				var files = this.files;
				audio.src = URL.createObjectURL(files[0]);
				audio.load();
				var context = new AudioContext();
				var src = context.createMediaElementSource(audio);
				var analyser = context.createAnalyser();
				src.connect(analyser);
				analyser.connect(context.destination);
				analyser.fftSize = 256;
				var bufferLength = analyser.frequencyBinCount;
				var dataArray = new Uint8Array(bufferLength);

				// audio.play();

			});
		};
	</script>
</body>


</html>