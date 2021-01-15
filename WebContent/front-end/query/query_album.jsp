<%@page import="java.util.List"%>
<%@ page import="com.album.model.AlbumService"%>
<%@ page import="com.album.model.AlbumVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="albumSvc" scope="page"
	class="com.album.model.AlbumService"></jsp:useBean>
<jsp:useBean id="bandSvc" scope="page"
	class="com.band.model.BandService"></jsp:useBean>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Query_album</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/homepage.css" />
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

	<%@include file="/front-end/header_footer/header.jsp"%>

	<%
		List<AlbumVO> list = albumSvc.getAllAlbums();
		pageContext.setAttribute("list", list);
	%>


	<div class="title-song wrap">
		<h2 class="wrap-title">
			<i class="fas fa-search"></i>【關鍵字】搜尋結果
		</h2>
		<div class="leatitle-all">
			<div class="lea-time"></div>
		</div>
		<div class="lea-song-list">

			<c:forEach var="albumVO" items="${albumSvc.getAlbumByName(name)}">

				<div class="song-line">
					<div class="num"></div>
					<div class="lea-img" style="cursor: pointer;"
						onclick="location.href='<%= request.getContextPath() %>/band/band.do?action=getBandMain&band_id=${albumVO.band_id}';">
						<img
							src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}"
							alt="">

						<div class="in-play" style="display: none;">
							<i class="far fa-play-circle img-play"></i>
						</div>
					</div>
					<div class="song-name">
						${albumVO.album_name}<br> <span>${bandSvc.getOneBand(albumVO.band_id).band_name}</span>
					</div>
					<div class="add">
						<i class="fas fa-plus"></i>
					</div>
					<!-- 					<div class="heart"> -->
					<!-- 						<i class="fas fa-heartbeat"></i> xxx -->
					<!-- 					</div> -->
					<div class="play-mu"
						onclick="location.href='<%= request.getContextPath() %>/band/band.do?action=getBandMain&band_id=${albumVO.band_id}';">
						<i class="far fa-play-circle"></i>
					</div>
				</div>
				<hr class="songlist-hr">

			</c:forEach>

		</div>
	</div>


	<%@include file="/front-end/header_footer/footer.jsp"%>
</body>
</html>