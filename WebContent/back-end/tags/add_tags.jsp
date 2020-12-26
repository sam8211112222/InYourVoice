<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.tags.model.TagsVO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	TagsVO tagsVO = (TagsVO) request.getAttribute("tagsVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tag資料新增 addTags.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tags/add_tags.css">
</head>
<body>
	<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>Tag資料新增 add_tags.jsp</h3>
			</td>
			<td>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back-end/tags/select_tags.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100"
						height="32" border="0">回首頁 </a>
				</h4>
			</td>
		</tr>
	</table>
	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/tags/tags.do" name="form1">
		<table>
			<tr>
				<td>Tag Name:</td>
				<td><input type="TEXT" name="tag_name" size="45"
					value="<%=(tagsVO == null) ? "" : tagsVO.getTag_name()%>" /></td>
			</tr>
			<tr>
				<td>Tag Add Time:</td>
				<td><input type="TEXT" readonly name="tag_add_time" size="45"
					value="<%=(tagsVO == null) ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())): tagsVO.getTag_add_time()%>" /></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>

</body>

</html>