<%@page import="java.util.List"%>
<%@ page import="com.band.model.BandService"%>
<%@ page import="com.band.model.BandVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>   


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Query_band</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/homepage.css" />
</head>

<style>

.view-event-btn {
    border-radius: 30px;
    padding: 10px;
    background-color: #f1c29d;
    margin-left: 19px;
}

</style>

<body>

<header class="bg-light">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="logo">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp"><img src="<%=request.getContextPath()%>/images/logo2.jpg"></a>
			</div>
			<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<!-- <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li> -->
					<li class="nav-item"><a class="nav-link" href="#">活動資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="#">樂團資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="#">專輯作品</a></li>
					<li class="nav-item"><a class="nav-link" href="#">周邊商品</a></li>

					<!-- <li class="nav-item">
                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                    </li> -->
					<form action="<%= request.getContextPath() %>/album/album.do" method="get" class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search" placeholder="搜尋" aria-label="Search" name="search" id="search" value="${name}">
						<input type="hidden" name="action" value="searchName">
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜尋</button>
					</form>
				</ul>
			</div>

		</nav>
		<div class="dropdown dropleft">
			<div class="notice">
				<a href="#"> <span class="notice-number">1</span> <i class="fas fa-bell"></i>
				</a>
			</div>
			<div id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				<!-- <img src="./images/logo.jpg"> -->
				<i class="far fa-user"></i>
			</div>
			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">

				<div class="user">
					<img src="./images/logo.jpg">
				</div>
				<div>帳號xxxx</div>
				<a class="dropdown-item" href="#">會員中心</a> <a class="dropdown-item" href="#">通知中心</a> <a class="dropdown-item" href="#">購物車</a> <a class="dropdown-item" href="#">我的最愛</a> <a class="dropdown-item"
					href="#">登出</a>
			</div>
		</div>
	</header>
	
		<%
			List<BandVO> list = bandSvc.getAllBand();
			pageContext.setAttribute("list", list);
		%>


	<div class="title-song wrap">
		<h2 class="wrap-title"><i class="fas fa-search"></i>【關鍵字】搜尋結果</h2>
		<a href="<%= request.getContextPath() %>/album/album.do?action=searchName&search=${name}"><button class="view-event-btn">歌曲</button></a>
		<a href="<%= request.getContextPath() %>/product/product.do?action=searchName&search=${name}"><button class="view-event-btn">商品</button></a>
		<a href="<%= request.getContextPath() %>/event/EventServlet?action=searchName&search=${name}"><button class="view-event-btn">活動</button></a>
		<a href="<%= request.getContextPath() %>/band/band.do?action=searchName&search=${name}"><button class="view-event-btn">樂團</button></a>
		<div class="leatitle-all">				

			<div class="lea-time"></div>
		</div>
		<div class="lea-song-list">
						
			<c:forEach var="bandVO" items="${bandSvc.getBandByName(name)}">

				<div class="song-line">
					<div class="num"></div>
					<div class="lea-img">
						<img src="<%=request.getContextPath()%>/band/band.do?action=getPhoto&band_id=${bandVO.band_id}" alt="">

						<div class="in-play" style="display: none;">
							<i class="far fa-play-circle img-play"></i>
						</div>
					</div>
					<div class="song-name">
						${bandVO.band_name}<br> <span>${bandVO.band_intro}</span>
					</div>
					<div class="add">
						<i class="fas fa-plus"></i>
					</div>
				</div>
				<hr class="songlist-hr">

			</c:forEach>
			
		</div>
	</div>
	
	
	<div class="wrap">
		<footer class="footer">
			<nav class="navfoot">
				<div>
					<h1 style="color: white">關於</h1>
					<a href="#"><li style="color: white">關於我們</li></a> <a href="#"><li style="color: white">會員服務條款</li></a> <a href="#"><li style="color: white">著作權保護措施</li></a> <a href="#"><li
						style="color: white">隱私權保護政策</li></a>
				</div>
				<div>
					<h1 style="color: white">其他</h1>
					<a href="#"><li style="color: white">聯絡我們</li></a> <a href="#"><li style="color: white">常見問題</li></a>
				</div>
			</nav>
		</footer>
	</div>

	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	
</body>
</html>