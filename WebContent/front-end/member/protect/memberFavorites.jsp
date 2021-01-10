<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.favorites.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	FavoritesService favSvc = new FavoritesService();
	List<FavoritesVO> listFav = favSvc.getMemberFav(memberVo.getMemberId());
	session.setAttribute("list", listFav);
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
.wrap {
	width: 80%;
	max-width: 1200px;
	margin: 15px auto 0 auto;
}

#myTab {
	width: 100%;
	display: flex;
	justify-content: space-around;
	border-bottom: none;
}

#myTab .nav-item {
	width: 20%;
	text-align: center;
}

header {
	display: flex;
	justify-content: space-between;
}

.navbar {
	width: calc(100% - 40px);
}

.card {
	width: 150px;
	height:200px;
	overflow: hidden;
}

.card img {
	width: 148px;
	height:148px;
}
</style>
<body>
	<%@ include file="/css/member/member_center_top.file"%>
	<h2 class="mt-5 mb-5">我的最愛</h2>
	<div class="wrap">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation"><a
				class="nav-link active" id="home-tab" data-toggle="tab" href="#home"
				role="tab" aria-controls="home" aria-selected="true">樂團</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="profile-tab" data-toggle="tab" href="#profile" role="tab"
				aria-controls="profile" aria-selected="false">作品</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="contact-tab" data-toggle="tab" href="#contact" role="tab"
				aria-controls="contact" aria-selected="false">商品</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="contact-tab" data-toggle="tab" href="#contact1" role="tab"
				aria-controls="contact" aria-selected="false">活動</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="home" role="tabpanel"
				aria-labelledby="home-tab">
				<div class="row row-cols-1 row-cols-md-4">
					<c:forEach var="favVO" items="${list}">
						<c:if test="${favVO.favorite_type==1}">
							<div class="col mb-2">
								<div class="card">
									<img
										src="<%=request.getContextPath()%>/band/band.do?action=picDisplay&bandId=${favVO.favorite_id}"
										class="card-img-top" alt="...">
									<div class="card-body">
										<button>檢視歌手</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel"
				aria-labelledby="profile-tab">
				<div class="row row-cols-1 row-cols-md-3">
					<div class="col mb-4">
						<div class="card">
							<img src="./img/活動圖7.jpg" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">oxo活動</h5>
								<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel"
				aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">
					<div class="col mb-4">
						<div class="card">
							<img src="./img/活動圖3.jpg" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">ooo活動</h5>
								<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="contact1" role="tabpanel"
				aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">
					<div class="col mb-4">
						<div class="card">
							<img src="./img/活動圖3.jpg" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">ooo活動</h5>
								<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
							</div>
						</div>
					</div>
				</div>
			</div>




			<!-- Optional JavaScript -->
			<!-- jQuery first, then Popper.js, then Bootstrap JS -->

			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
				integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
				crossorigin="anonymous"></script>
			<script
				src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>


			<%@ include file="/css/member/member_center_bottom.file"%>
</body>
</html>