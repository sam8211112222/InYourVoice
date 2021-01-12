<%@page import="com.tags.model.TagsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
// 	session.setAttribute("bandVO", "Editor666");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>作品資料修改 - update_piece_input.jsp</title>
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

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pieces/update_piece_input.css">
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
<body bgcolor='white'>

<!-- 	<table id="table-1"> -->
<!-- 		<tr> -->
<!-- 			<td> -->
<!-- 				<h3>作品資料修改 - update_piece_input.jsp</h3> -->
<!-- 				<h4> -->
<%-- 					<a href="<%=request.getContextPath()%>/back-end/pieces/index_pieces.jsp"><img src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a> --%>
<!-- 				</h4> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
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

	<FORM METHOD="post" enctype="multipart/form-data" ACTION="<%=request.getContextPath()%>/pieces/pieces.do" name="form1">
		<table>
			<tr>
				<td>piece_id:</font></td>
				<td>${piecesVO.piece_id}</td>
			</tr>
			<tr>
				<td>album_id:</td>
				<td>${piecesVO.album_id}</td>
			</tr>
			<tr>
				<td>piece:</td>
				<td>
					<input type="file" name="piece" size="45" accept="audio/*"/>
				</td>
			</tr>
			<tr>
				<td>piece_name:</td>
				<td>
					<input type="text" id="piece_file" name="piece_name" size="25" value="${piecesVO.piece_name}" />
				</td>
			</tr>
			<tr>
				<td>piece_status:</td>
				<td><select name="piece_status">
						<option ${piecesVO.piece_status==0?"selected":""} value="0">下架</option>
						<option ${piecesVO.piece_status==1?"selected":""} value="1">上架</option>
				</select></td>
			</tr>
			<tr>
				<td>piece_play_count:</td>
				<td>${piecesVO.piece_play_count}</td>
			</tr>
			<tr>
				<td>piece_add_time:</td>
				<td><fmt:formatDate value="${piecesVO.piece_add_time}" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<td>piece_last_edit_time:</td>
				<td><fmt:formatDate value="${piecesVO.piece_last_edit_time}" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<td>piece_last_editor:</td>
				<td>${piecesVO.piece_last_editor}</td>
			</tr>
		</table>

		<br> 
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="member_id" value="${memberVo.memberId}"> 
		<input type="hidden" name="piece_id" value="${piecesVO.piece_id}"> <input id="submit_btn" type="submit" value="送出修改">
	</FORM>
	 <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	
<%@ include file="/back-end/sb/page2.file" %>

</body>
</html>