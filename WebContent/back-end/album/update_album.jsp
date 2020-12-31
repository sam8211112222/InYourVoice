<%@ page import="com.album.model.AlbumVO"%>
<%@ page import="com.album.model.AlbumService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	
	session.setAttribute("memberVO", "MEMBER00999");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update_album.jsp</title>
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
  img.preview{
  	width: 200px;
  	
  }
  textarea{
  	width: 300px;
  	height: 200px;
  }
</style>
</head>
<body>
	
	<%@ include file="/back-end/sb/page1.file" %>

	
	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>



	<FORM METHOD="post" enctype="multipart/form-data"
		ACTION="<%=request.getContextPath()%>/album/album.do" name="form1">
		<table>
			<!-- 		<font color=red><b>*</b></font> -->
			<tr>
				<td>專輯ID</td>
				<td>${albumVO.album_id}</td>
			</tr>
			<tr>
				<td>樂團ID</td>
				<td>${albumVO.band_id}</td>
			</tr>
			<tr>
				<td>專輯名稱</td>
				<td>
				
				<input type="TEXT" name="album_name" size="45"	value="${albumVO.album_name}" /></td>
			</tr>
			<tr>
				<td>專輯簡介</td>
				<td><textarea name="album_intro" rows="10" cols="1">${albumVO.album_intro}</textarea></td>
			</tr>
			<tr>
				<td>專輯照片</td>
				<td><input class="upload" type="file" name="album_photo" accept="image/*"> 預覽圖
					<ul class="picture_list">
						<img class="preview" src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}">
					</ul></td>

			</tr>
			<tr>
				<td>專輯狀態</td>
				<td>
					<!-- <input type="TEXT" name="album_status" size="45" value="${albumVO.album_status}" /> -->
					<select name="album_status">
						<option value="0" ${albumVO.album_status==0?"selected":""}>下架</option>
						<option value="1" ${albumVO.album_status==1?"selected":""}>上架</option>
				</select>

				</td>
			</tr>
			<tr>
				<td>專輯新增時間</td>
				<td><fmt:formatDate value="${albumVO.album_add_time}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>專輯上架時間</td> -->
<!-- 				<td><input name="album_release_time" id="f_date1" type="text"> -->
<%-- 										<fmt:formatDate	value="${albumVO.album_release_time}" pattern="yyyy-MM-dd HH:mm:ss" /> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<td>專輯最後編輯時間</td>
				<td><fmt:formatDate value="${albumVO.album_last_edit_time}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>專輯最後編輯者</td>
				<td>${albumVO.album_last_editor}</td>
			</tr>


		</table>

		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="album_id" value="${albumVO.album_id}"> <input
			type="submit" value="送出修改">
	</FORM>
	<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/album/update_album.js"></script>
<!-- 參考網站: https://xdsoft.net/jqplugins/datetimepicker/ -->

<link   rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh'); // kr ko ja en
        $('#f_date1').datetimepicker({
           theme: '',          //theme: 'dark',
           timepicker: true,   //timepicker: false,
           step: 60,            //step: 60 (這是timepicker的預設間隔60分鐘)
	       format: 'Y-m-d H:i',
	       value: "${albumVO.album_add_time}",
           //disabledDates:    ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	        '2017/07/10',  // 起始日
           minDate:           '-1970-01-01', // 去除今日(不含)之前
           //maxDate:           '+1970-01-01'  // 去除今日(不含)之後
        });
</script>
<%@ include file="/back-end/sb/page2.file" %>
</body>
</html>