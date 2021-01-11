<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list_one_album.jsp</title>
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
  img{
  width:200px;
  }
</style>
</head>
<body>
<table id="table-1">
		<tr>
			<td>
				<h3>專輯資料 - list_one_album.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/album/list_all_album.jsp">
					<img src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table class="list">
			<tr>
				<td><span>專輯ID</span></td>
				<td><span>樂團ID</span></td>
				<td><span>專輯名稱</span></td>
				<td><span>專輯簡介</span></td>
				<td><span>專輯照片</span></td>
				<td><span>專輯新增時間</span></td>
				<td><span>專輯上架時間</span></td>
				<td><span>最後編輯時間</span></td>
				<td><span>最後編輯者</span></td>
				<td><span>狀態</span></td>
				<td><span>修改</span></td>
			</tr>
				<tr>
					<td>${albumVO.album_id}</td>
					<td>${albumVO.band_id}</td>
					<td>${albumVO.album_name}</td>
					<td>${albumVO.album_intro}</td>
					<td><img src="<%=request.getContextPath()%>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}"></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_add_time}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_release_time}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_last_edit_time}" /></td>
					<td>${albumVO.album_last_editor}</td>
					<td>${albumVO.album_status == 0 ? "下架":"上架"}</td>
					<td>
						<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/album/album.do" style="margin-bottom: 0px;">
							<input type="submit" value="修改"> <input type="hidden"	name="album_id" value="${albumVO.album_id}">
							<input type="hidden" name="action" value="getOne_For_Update">
						</FORM>
					</td>

				</tr>
		</table>
</body>
</html>