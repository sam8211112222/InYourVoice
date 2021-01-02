<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.favorites.model.*"%>

<%
	FavoritesVO favoritesVO = (FavoritesVO) request.getAttribute("favoritesVO");
%>



<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title> Favorites新增 - addFavorites.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>addFavorites.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/back-end/favorites/F_home.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/FavoritesServlet" name="form1">
<table>
	<tr>
		<td>會員編號:</td>
		<td><input type="TEXT" name="member_id" size="45"  
			 value="<%= (favoritesVO==null)? "ooo" : favoritesVO.getMember_id()%>" /></td> 
	</tr>
	<tr>
		<td>收藏類型:</td>
		<td><input type="TEXT" name="favorite_type" size="45"
								 value="<%= (favoritesVO==null)? 1 : favoritesVO.getFavorite_type()%>"></td>
	</tr>
	<tr>
		<td>收藏編號:</td>
		<td><input type="TEXT" name="favorite_id" size="45" 
								 value="<%=(favoritesVO==null)? "oooo" :favoritesVO.getFavorite_id()%>" /></td>
	</tr>
	<tr>
		<td>加入時間:</td>
		<td><input name="favorite_add_time" id="f_date1" type="text"></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

<% 
	java.sql.Timestamp Favorite_add_time = null;
  try {
	  Favorite_add_time = favoritesVO.getFavorite_add_time();
   } catch (Exception e) {
	  Favorite_add_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/favorites/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/favorites/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/back-end/favorites/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=Favorite_add_time%>',       
        });
               
</script>     
</html>