<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.favorites.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	FavoritesService favSvc = new FavoritesService();
    List<FavoritesVO> list = favSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有收藏資料 - listAllFavorites.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有收藏資料 - listAllEmp.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/favorites/F_home.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>身份</th>
		<th>會員ID</th>
		<th>收藏類型</th>
		<th>收藏ID</th>
		<th>加入時間</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="favoritesVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${favoritesVO.uniqueid}</td>
			<td>${favoritesVO.member_id}</td>
			<td>${favoritesVO.favorite_type}</td>
			<td>${favoritesVO.favorite_id}</td>
			<td>${favoritesVO.favorite_add_time}</td>
	
	
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FavoritesServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="uniqueid"  value="${favoritesVO.uniqueid}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FavoritesServlet" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="uniqueid"  value="${favoritesVO.uniqueid}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

</body>
</html>