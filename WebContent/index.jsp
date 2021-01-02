<%@page import="com.event.model.EventVO"%>
<%@page import="java.util.List"%>
<%@page import="com.event.model.EventService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- new 一個AlbumService來用 -->
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>



<!-- AlbumService albumSvc = new AlbumService(); -->
<!-- AlbumVO albumVO = albumSvc.getOneAlbum("ALBUM00000"); -->
<!-- String album_name = albumVO.getAlbum_name(); -->



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>InYourVoice</title>
<link rel="stylesheet" href="./vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" href="./vendors/slick/slick.css" />
<link rel="stylesheet" href="./vendors/slick/slick-theme.css" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<link rel="stylesheet" href="./css/homepage.css" />
</head>
<body onload="connect();" onunload="disconnect();">


	<header class="bg-light">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="logo">
				<a class="navbar-brand" href="#"><img src="./images/logo2.jpg"></a>
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
					<form action="<%=request.getContextPath()%>/album/album.do" method="get" class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search" placeholder="搜尋" aria-label="Search" name="search" id="search"> <input type="hidden" name="action" value="searchName">
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

				<div class="userAvatar">
					<img src="./images/logo.jpg">
				</div>
				<div>帳號xxxx</div>
				<a class="dropdown-item" href="#">會員中心</a> <a class="dropdown-item" href="#">通知中心</a> <a class="dropdown-item" href="#">購物車</a> <a class="dropdown-item" href="#">我的最愛</a> <a class="dropdown-item"
					href="#">登出</a>
			</div>
		</div>
	</header>

	<%
		//step 1 拿到所有活動資料
		EventService eventSvc = new EventService();
		List<EventVO> eventVOList = eventSvc.getAll();
		pageContext.setAttribute("eventVOList", eventVOList);//EL

		//step 2 跑迴圈，並判斷是否為主打活動
		//step 3 若確定是主打活動 此次迴圈才加上輪播的圖片
		//step 4加上輪播圖片所需的html程式碼時 記得替換src的內容
	%>

	<!-- 輪播 -->
	<div class="banner">

		<c:forEach var="eventVO" items="${eventVOList}">
			<c:if test="${eventVO.event_type==3}">
				<div class="ls-slide">
					<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}">
				</div>
			</c:if>
		</c:forEach>

		<!--最小輪播圖片單位 -->

		<!-- 		<div class="ls-slide"> -->
		<!-- 			<img src="./images/輪播1.jpg"> -->
		<!-- 		</div> -->

		<!--最小輪播圖片單位 -->


		<!-- 		<div class="ls-slide"> -->
		<!-- 			<img src="./images/輪播2.jpg"> -->
		<!-- 		</div> -->

		<!-- 		<div class="ls-slide"> -->
		<!-- 			<img src="./images/輪播3.jpg"> -->
		<!-- 		</div> -->

		<!-- 		<div class="ls-slide"> -->
		<!-- 			<img src="./images/輪播4.jpg"> -->
		<!-- 		</div> -->

		<!-- 		<div class="ls-slide"> -->
		<!-- 			<img src="./images/輪播5.jpg"> -->
		<!-- 		</div> -->
	</div>
	<!-- 輪播 -->

	<!-- 最新消息 -->
	<div class="wrap">
		<h2 class="wrap-title">最新消息</h2>
		<table class="table table-dark table-hover">
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="text-align: right;"><a href="#">More<i class="fas fa-angle-double-right"></i></i></a></th>
				</tr>
			</thead>
			<tbody>


				<c:forEach var="eventVO" items="${eventVOList}">
					<c:if test="${eventVO.event_type==4}">
						<tr>
							<th scope="row"><img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="latest-events"></th>
							<td>${eventVO.event_title}</td>
						</tr>
					</c:if>
				</c:forEach>

				<!--             <tr> -->
				<!--                 <th scope="row"> -->
				<!--                 <img src="" alt="" class="latest-events"></th> -->
				<!--                 <td>內容內容內容內容內容內容內容內容內容</td> -->
				<!--             </tr> -->
				<!--             <tr> -->
				<!--                 <th scope="row"> -->
				<!--                 <img src="" alt="" class="latest-events"></th> -->
				<!--                 <td>內容內容內容內容內容內容內容內容內容</td> -->
				<!--             </tr> -->
				<!--             <tr> -->
				<!--                 <th scope="row"> -->
				<!--                 <img src="" alt="" class="latest-events"></th> -->
				<!--                 <td>內容內容內容內容內容內容內容內容內容</td> -->
				<!--             </tr> -->
				<!--             <tr> -->
				<!--                 <th scope="row"> -->
				<!--                 <img src="" alt="" class="latest-events"></th> -->
				<!--                 <td>內容內容內容內容內容內容內容內容內容</td> -->
				<!--             </tr> -->
			</tbody>
		</table>
	</div>


	<!-- 熱門排行榜 -->
	<div class="title-song wrap">
		<h2 class="wrap-title">熱門排行榜</h2>
		<div class="leatitle-all">

			<!-- <div class="lea-title">
                熱門排行榜
            </div> -->

			<div class="playall">
				<i class="fas fa-play"></i> 全部播放
			</div>
			<div class="lea-time">2020 / xx / xx ~ 2020 / xx / xx</div>
		</div>
		<div class="lea-song-list">
			<div class="song-line">
				<div class="num">
					1 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<!-- 					<img src="./images/排行榜歌圖1.png" alt=""> -->
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00000" alt="">

					<div class="in-play" style="display: none;">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00000").album_name}<br> <span>${bandSvc.getOneBand("BAND00000").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 18.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					2 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00050" alt="">
					<div class="in-play" style="display: none;">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00050").album_name}<br> <span>${bandSvc.getOneBand("BAND00050").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 17.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					3 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00100" alt="">
					<div class="in-play" style="display: none;">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00100").album_name}<br> <span>${bandSvc.getOneBand("BAND00100").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 16.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					4 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00150" alt="">
					<div class="in-play" style="display: none;">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00150").album_name}<br> <span>${bandSvc.getOneBand("BAND00150").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 15.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					5 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00200" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00200").album_name}<br> <span>${bandSvc.getOneBand("BAND00200").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 14.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					6 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00250" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00250").album_name}<br> <span>${bandSvc.getOneBand("BAND00250").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 13.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					7 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00300" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00300").album_name}<br> <span>${bandSvc.getOneBand("BAND00300").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 12.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					8 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00350" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00350").album_name}<br> <span>${bandSvc.getOneBand("BAND00350").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 11.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					9 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00400" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00400").album_name}<br> <span>${bandSvc.getOneBand("BAND00400").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 10.8k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
			<div class="song-line">
				<div class="num">
					10 <i class="fas fa-sort-up"></i>
				</div>
				<div class="lea-img">
					<img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=ALBUM00450" alt="">
					<div class="in-play">
						<i class="far fa-play-circle img-play"></i>
					</div>
				</div>
				<div class="song-name">
					${albumSvc.getOneAlbum("ALBUM00450").album_name}<br> <span>${bandSvc.getOneBand("BAND00450").band_name}</span>
				</div>
				<div class="add">
					<i class="fas fa-plus"></i>
				</div>
				<div class="heart">
					<i class="fas fa-heartbeat"></i> 10.5k
				</div>
				<div class="play-mu">
					<i class="far fa-play-circle"></i>
				</div>
			</div>
			<hr class="songlist-hr">
		</div>
	</div>


	<!-- 每日好聲音 -->
	<div class="wrap">
		<h2 class="wrap-title">每日好聲音</h2>
		<div class="card-deck">
			<div class="card">
				<!-- 			<img src="./images/美日好聲音1.jpg" class="card-img-top" alt="..."> -->
				<img src="<%=request.getContextPath()%>/photoProvider/photoProvider.do?action=getPhoto&band_id=BAND00000" class="card-img-top" alt="">
				<div class="card-body">
					<h5 class="card-title">${bandSvc.getOneBand("BAND00000").band_name}</h5>
					<!-- 					<p class="card-text">歌手</p> -->
					<!-- 					<p class="card-text"> -->
					<!-- 						<small class="text-muted">2020/xx/oo</small> -->
					<!-- 					</p> -->
				</div>
			</div>
			<div class="card">
				<img src="<%=request.getContextPath()%>/photoProvider/photoProvider.do?action=getPhoto&band_id=BAND00050" class="card-img-top" alt="">
				<div class="card-body">
					<h5 class="card-title">${bandSvc.getOneBand("BAND00050").band_name}</h5>
				</div>
			</div>
			<div class="card">
				<img src="<%=request.getContextPath()%>/photoProvider/photoProvider.do?action=getPhoto&band_id=BAND00100" class="card-img-top" alt="">
				<div class="card-body">
					<h5 class="card-title">${bandSvc.getOneBand("BAND00100").band_name}</h5>
				</div>
			</div>
			<div class="card">
				<img src="<%=request.getContextPath()%>/photoProvider/photoProvider.do?action=getPhoto&band_id=BAND00150" class="card-img-top" alt="">
				<div class="card-body">
					<h5 class="card-title">${bandSvc.getOneBand("BAND00150").band_name}</h5>
				</div>
			</div>
			<div class="card">
				<img src="<%=request.getContextPath()%>/photoProvider/photoProvider.do?action=getPhoto&band_id=BAND00200" class="card-img-top" alt="">
				<div class="card-body">
					<h5 class="card-title">${bandSvc.getOneBand("BAND00200").band_name}</h5>
				</div>
			</div>
		</div>
	</div>


	<!-- 近期活動，放鬆好去處，特色活動 -->
	<div class="wrap">
		<h2 class="wrap-title">推薦活動</h2>
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation"><a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">近期活動</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">放鬆好去處</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">特色活動</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}" begin="1" end="6">
						<c:if test="${eventVO.event_status==1}">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn">檢視活動</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>



					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖2.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖3.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖4.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖5.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖6.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->

				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}">
						<c:if test="${eventVO.event_type==1}">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn">檢視活動</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>

					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖8.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖9.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖10.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖11.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖3.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->

				</div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">

					<c:forEach var="eventVO" items="${eventVOList}">
						<c:if test="${eventVO.event_type==2}">
							<div class="col mb-4">
								<div class="card">
									<img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top">
									<div class="card-body">
										<i class="fas fa-calendar-alt"><fmt:formatDate value="${eventVO.event_start_time}" pattern="yyyy-MM-dd HH:mm" /></i>
										<button class="view-event-btn">檢視活動</button>
										<!-- <p class="card-text">活動內容活動內容活動內容活動內容</p> -->
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>

					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖6.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖9.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖11.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖1.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->
					<!-- 					<div class="col mb-4"> -->
					<!-- 						<div class="card"> -->
					<!-- 							<img src="./images/活動圖7.jpg" class="card-img-top" alt="..."> -->
					<!-- 							<div class="card-body"> -->
					<!-- 								<i class="fas fa-calendar-alt">2020/10/28 19:00</i> -->
					<!-- 								<button class="view-event-btn">檢視活動</button> -->
					<!-- 								<p class="card-text">活動內容活動內容活動內容活動內容</p> -->
					<!-- 							</div> -->
					<!-- 						</div> -->
					<!-- 					</div> -->

				</div>
			</div>
		</div>
	</div>

	<!-- 客服聊天室窗 -->
	<div id="chat-circle" class="btn btn-raised">
		<div id="chat-overlay"></div>
		<i class="far fa-comment-dots"></i>
	</div>

	<div class="chat-box">
		<div class="chat-box-header">
			客服 <span class="chat-box-toggle"><i class="fas fa-times"></i></span>
		</div>
		<div class="chat-box-body">
			<div class="chat-box-overlay"></div>
			<div class="chat-logs" id="chat-logs"></div>
			<!--chat-log -->
		</div>
		<div class="chat-input">
			<input type="text" id="chat-input" placeholder="請輸入您的問題" />
			<button type="submit" class="chat-submit" id="chat-submit">
				<i class="fas fa-paper-plane"></i>
			</button>
		</div>
	</div>




	<hr>
	<!-- footer -->
	<div class="wrap">
		<footer class="footer">
			<nav class="navfoot">
				<div>
					<h1 style="color: white">關於</h1>
					<a href="#"><li style="color: white">關於我們</li></a> 
				</div>
				<div>
					<h1 style="color: white">其他</h1>
					<a href="#"><li style="color: white">聯絡我們</li></a>
				</div>
			</nav>
		</footer>
	</div>


	<script src="./vendors/jquery/jquery-3.4.1.min.js"></script>
	<script src="./vendors/popper/popper.min.js"></script>
	<script src="./vendors/bootstrap/js/bootstrap.min.js"></script>


	<script type="text/javascript" src="./vendors/slick/slick.min.js"></script>

	<!-- 輪播 -->
	<script>
		$('.banner').slick({});
	</script>
	<!-- 客服聊天 -->
	<script>
		var INDEX = 0;
		$("#chat-submit").click(function(e) {
			e.preventDefault();
			sendMessage();
		})

		function generate_message(msg, type) {
			INDEX++;
			var str = "";
			str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg "+type+"\">";
			str += "          <div class=\"cm-msg-text\">";
			str += msg;
			str += "          <\/div>";
			str += "        <\/div>";
			$(".chat-logs").append(str);
			$("#cm-msg-" + INDEX).hide().fadeIn(300);
			if (type == 'self') {
				$("#chat-input").val('');
			}
			$(".chat-logs").stop().animate({
				scrollTop : $(".chat-logs")[0].scrollHeight
			}, 1000);
		}

		function generate_button_message(msg, buttons) {
			/* Buttons should be object array 
			[
			    {
			    name: 'Existing User',
			    value: 'existing'
			    },
			    {
			    name: 'New User',
			    value: 'new'
			    }
			]
			 */
			INDEX++;
			var btn_obj = buttons
					.map(
							function(button) {
								return "              <li class=\"button\"><a href=\"javascript:;\" class=\"btn btn-primary chat-btn\" chat-value=\""+button.value+"\">"
										+ button.name + "<\/a><\/li>";
							}).join('');
			var str = "";
			str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg user\">";
			str += "          <div class=\"cm-msg-text\">";
			str += msg;
			str += "          <\/div>";
			str += "          <div class=\"cm-msg-button\">";
			str += "            <ul>";
			str += btn_obj;
			str += "            <\/ul>";
			str += "          <\/div>";
			str += "        <\/div>";
			$(".chat-logs").append(str);
			$("#cm-msg-" + INDEX).hide().fadeIn(300);
			$(".chat-logs").stop().animate({
				scrollTop : $(".chat-logs")[0].scrollHeight
			}, 1000);
			$("#chat-input").attr("disabled", true);
		}

		$(document).delegate(".chat-btn", "click", function() {
			var value = $(this).attr("chat-value");
			var name = $(this).html();
			$("#chat-input").attr("disabled", false);
			generate_message(name, 'self');
		})

		$("#chat-circle").click(function() {
			$("#chat-circle").toggle('scale');
			$(".chat-box").toggle('scale');
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		})

		$(".chat-box-toggle").click(function() {
			$("#chat-circle").toggle('scale');
			$(".chat-box").toggle('scale');
		})

		//=======改========
		var userName = 'Roy';
		var friend = '123';
		var endPointURL = 'ws://localhost:8081/WebSocketChatWeb/FriendWS/' + userName;
		//=================
			
		var messagesArea = document.getElementById("chat-logs");
		var self = userName;
		var webSocket;
		
		function connect() {
			// create a websocket
			webSocket = new WebSocket(endPointURL);

			webSocket.onopen = function(event) {
				console.log("Connect Success!");
			};

			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("open" === jsonObj.type) {
// 					refreshFriendList(jsonObj);
				} else if ("history" === jsonObj.type) {
					messagesArea.innerHTML = '';
					// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
					var messages = JSON.parse(jsonObj.message);
					for (var i = 0; i < messages.length; i++) {
						var historyData = JSON.parse(messages[i]);
						var showMsg = historyData.message;
						
						// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
						generate_message(showMsg, historyData.sender === self ? 'self' : 'user');
					}
					messagesArea.scrollTop = messagesArea.scrollHeight;
				} else if ("chat" === jsonObj.type) {
					generate_message(jsonObj.message, jsonObj.sender === self ? 'self' : 'user');
					messagesArea.scrollTop = messagesArea.scrollHeight;
				} else if ("close" === jsonObj.type) {
// 					refreshFriendList(jsonObj);
				}
				
			};

			webSocket.onclose = function(event) {
				console.log("Disconnected!");
			};
		}
		
		function sendMessage() {
			var inputMessage = document.getElementById("chat-input");
			var message = inputMessage.value.trim();

			if (message === "") {
				alert("Input a message");
				inputMessage.focus();
			} else if (friend === "") {
				alert("Choose a friend");
			} else {
				var jsonObj = {
					"type" : "chat",
					"sender" : self,
					"receiver" : friend,
					"message" : message
				};
				webSocket.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
			}
		}
		
		function disconnect() {
			webSocket.close();
		}
	</script>
</body>
</html>