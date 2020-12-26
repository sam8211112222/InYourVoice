<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>listOnePiece.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pieces/listOnePiece.css">
</head>
<body>

	<table id="table-1">
		<tr>
			<td>
				<h3>作品資料 - listOnePiece.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/pieces/index_pieces.jsp"><img src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>piece_id</th>
			<th>album_id</th>
			<th>piece</th>
			<th>piece_status</th>
			<th>piece_play_count</th>
			<th>piece_add_time</th>
			<th>piece_last_edit_time</th>
			<th>piece_last_editor</th>
		</tr>
		<tr>
			<td>${piecesVO.piece_id}</td>
			<td>${piecesVO.album_id}</td>
			<td>
				<audio controls>
				<!-- controls autoplay loop  -->
				<source src="<%=request.getContextPath()%>/pieces/pieces.do?action=getPiece&piece_id=${piecesVO.piece_id}" type="audio/mpeg">
				</audio>
			</td>
			<td>${piecesVO.piece_status==0?"下架":"上架"}</td>
			<td>${piecesVO.piece_play_count}</td>
			<td><fmt:formatDate value="${piecesVO.piece_add_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td><fmt:formatDate value="${piecesVO.piece_last_edit_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td>${piecesVO.piece_last_editor}</td>
		</tr>
	</table>


</body>
</html>