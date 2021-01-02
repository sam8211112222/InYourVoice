<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>TEA102G6 Favorites: Home</title>
<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td><h3>TEA102G6 Favorites: Home</h3>
				<h4>( MVC )</h4></td>
		</tr>
	</table>

	<p>This is the Home page for TEA102G6 Favorites: Home</p>

	<h3>資料查詢:</h3>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back-end/favorites/listAllFavorites.jsp'>List</a>
			all Favorites. <br> <br></li>
		<li>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FavoritesServlet">
				<b>輸入編號 (如FAVORITES00000):</b> <input type="text" name="uniqueid">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="favSvc" scope="page"
			class="com.favorites.model.FavoritesService" />
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/FavoritesServlet">
				<b>選擇編號:</b> <select size="1" name="uniqueid">
					<c:forEach var="favoritesVO" items="${favSvc.all}">
						<option value="${favoritesVO.uniqueid}">${favoritesVO.uniqueid}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>

	<h3>收藏管理</h3>

	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back-end/favorites/addFavorites.jsp'>Add</a>
			a new Favorites.</li>
	</ul>
</body>
</html>