<%@ page import="com.album.model.AlbumVO"%>
<%@ page import="com.album.model.AlbumService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 	session.setAttribute("memberVO", "MEMBER00999");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update_album.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

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

h3 {
	color: blue;
	display: inline;
	text-align:center;
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

img.preview {
	width: 200px;
}

textarea {
	width: 500px;
	height: 200px;
}
div.btn_area, .title_area{
	width: 150px;
	margin: 0 auto 30px;
}
.content_area{
	width: 80%;
	margin: 0 auto;
}
</style>
</head>
<body>

	<%@ include file="/back-end/sb/page1.file"%>


	<div class="content_area">
	<div class="title_area"><h3>資料修改</h3></div>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>


	<FORM METHOD="post" enctype="multipart/form-data" ACTION="<%=request.getContextPath()%>/album/album.do" name="form1">
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
				<td><input type="TEXT" name="album_name" size="20" value="${albumVO.album_name}" /></td>
			</tr>
			<tr>
				<td>專輯簡介</td>
				<td><textarea name="album_intro" rows="10" cols="10">${albumVO.album_intro}</textarea></td>
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
					<!-- <input type="TEXT" name="album_status" size="45" value="${albumVO.album_status}" /> --> <select name="shelf_status">
						<option value="0" ${albumVO.album_status==0?"selected":""}>下架</option>
						<option value="1" ${albumVO.album_status==1?"selected":""}>上架</option>
						<option value="2" ${albumVO.album_status==2?"selected":""}>預約上架</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>專輯新增時間</td>
				<td><fmt:formatDate value="${albumVO.album_add_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>專輯上架時間</td>
				<td><input name="on_shelf_time" id="f_date1" type="text"></td>
			</tr>
			<tr>
				<td>最後編輯時間</td>
				<td><fmt:formatDate value="${albumVO.album_last_edit_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>最後編輯者</td>
				<td>${albumVO.album_last_editor}</td>
			</tr>


		</table>

		<br> 
			<input type="hidden" name="action" value="updateAlbum"> 
			<input type="hidden" name="action_type" value="updateAlbumPage"> 
			<input type="hidden" name="member_id" value="${memberVo.memberId}"> 
			<input type="hidden" name="album_id" value="${albumVO.album_id}"> 
			<div class="btn_area">
				<input type="button" onclick="history.back()" value="取消">
				<input type="submit" value="送出修改">
			</div>
	</FORM>
	</div>
	<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/album/update_album.js"></script>
	<!-- 參考網站: https://xdsoft.net/jqplugins/datetimepicker/ -->

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
	<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>

	<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

	<script>
		$.datetimepicker.setLocale('zh'); // kr ko ja en
		$('#f_date1').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : true, //timepicker: false,
			step : 60, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'Y-m-d H:i',
			value : new Date(),
			//disabledDates:    ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
			//startDate:	        '2017/07/10',  // 起始日
			minDate : '-1970-01-01', // 去除今日(不含)之前
		//maxDate:           '+1970-01-01'  // 去除今日(不含)之後
		});
	</script>
	<%@ include file="/back-end/sb/page2.file"%>
</body>
</html>