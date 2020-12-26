<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.tags.model.TagsVO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tag資料修改 - update_tag_input.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tags/update_tag_input.css">
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>Tag資料修改 - update_tag_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/tags/index_tags.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

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

	<FORM METHOD="post" ACTION="tags.do" name="form1">
		<table>
			<tr>
				<td>Tag ID:<font color=red><b>*</b></font></td>
				<td>${tagsVO.tag_id}</td>
			</tr>
			<tr>
				<td>Tag Name:</td>
				<td><input type="TEXT" name="tag_name" size="45"
					value="${tagsVO.tag_name}" /></td>
			</tr>
			<tr>
				<td>Tag Add Time:</td>
				<td><fmt:formatDate
								value="${tagsVO.tag_add_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>

		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="tag_id" value="${tagsVO.tag_id}"> <input
			type="submit" value="送出修改">
	</FORM>

</body>
</html>