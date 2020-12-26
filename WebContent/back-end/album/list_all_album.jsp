<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.album.model.AlbumService"%>
<%@ page import="com.album.model.AlbumVO"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>listAllAlbum</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/album/list_all_album.css">
</head>
<body>
	<div class="frame">
		<div>
			<h1>作品管理</h1>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
		<div>
			<form method="post" action="<%=request.getContextPath()%>/album/album.do">
				<table>
					<tr>
						<td>專輯編號</td>
						<td><input type="text" name="album_id"></td>
						<td>專輯名稱</td>
						<td><input type="text" name="album_name"></td>
					</tr>
					<tr>
						<td>作品編號</td>
						<td><input type="text" name="piece_id"></td>
						<td>作品名稱</td>
						<td><input type="text" name="piece_name"></td>
					</tr>
					<tr>
						<td>樂團編號</td>
						<td><input type="text" name="band_id"></td>
						<td>樂團名稱</td>
						<td><input type="text" name="band_name"></td>
					</tr>
				</table>
				<div class="form_btn">
					<input type="reset" value="清空">
					<input type="hidden" name="action" value="search">
					<input type="submit" value="搜尋">
					<input type="button" onclick="location.href='<%=request.getContextPath()%>/back-end/album/add_album.jsp'" value="新增專輯">
				</div>
			</form>
		</div>

		<%
			List<AlbumVO> list = albumSvc.getAllAlbums();
			pageContext.setAttribute("list", list);
		%>
		<%@ include file="pages/page1.file"%>
		<table class="list">
			<tr>
				<td><span>專輯ID</span></td>
				<td><span>樂團ID</span></td>
				<td><span>專輯名稱</span></td>
				<td><span>專輯新增時間</span></td>
				<td><span>專輯上架時間</span></td>
				<td><span>最後編輯時間</span></td>
				<td><span>最後編輯者</span></td>
				<td><span>狀態</span></td>
				<td><span>修改</span></td>
			</tr>
			<c:forEach var="albumVO" items="${albumSvc.allAlbums}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<tr>
					<td>${albumVO.album_id}</td>
					<td>${albumVO.band_id}</td>
					<td>${albumVO.album_name}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_add_time}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_release_time}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_last_edit_time}" /></td>
					<td>${albumVO.album_last_editor}</td>
					<td>${albumVO.album_status == 0 ? "下架":"上架"}</td>
					<td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/album/album.do" style="margin-bottom: 0px;">
							<input type="submit" value="修改"> <input type="hidden" name="album_id" value="${albumVO.album_id}"> <input type="hidden" name="action" value="getOne_For_Update">
						</FORM></td>

				</tr>
			</c:forEach>
		</table>
		<%@ include file="pages/page2.file"%>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/album/list_all_album.js"></script>
</body>
</html>