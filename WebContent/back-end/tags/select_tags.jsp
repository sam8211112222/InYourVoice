<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="tagsSvc" scope="page" class="com.tags.model.TagsService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tags/select_tags.css">
<title>select_tags.jsp</title>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td><h3>Tag搜尋</h3></td>
		</tr>
	</table>

	<p>This is select_tags.jsp</p>

	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
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
			href='<%=request.getContextPath()%>/back-end/tags/list_all_tags.jsp'>List</a>
			all Tags. <br>
		<br></li>


		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/tags/tags.do">
				<b>輸入Tag ID (如TAGS00000):</b> <input type="text" name="tag_id">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/tags/tags.do">
				<b>選擇 Tag ID:</b> <select size="1" name="tag_id">
					<c:forEach var="tagsVO" items="${tagsSvc.allTags}">
						<option value="${tagsVO.tag_id}">${tagsVO.tag_id}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/tags/tags.do">
				<b>選擇Tag NAME:</b> <select size="1" name="tag_id">
					<c:forEach var="tagsVO" items="${tagsSvc.allTags}">
						<option value="${tagsVO.tag_id}">${tagsVO.tag_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/tags/tags.do">
				<b>選擇Tag Add Time:</b> <select size="1" name="tag_id">
					<c:forEach var="tagsVO" items="${tagsSvc.allTags}">
						<option value="${tagsVO.tag_id}"><fmt:formatDate
								value="${tagsVO.tag_add_time}" pattern="yyyy-MM-dd HH:mm:ss" />
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

	</ul>


	<h3>Tag管理</h3>

	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/tags/add_tags.jsp'>Add</a>
			a new Tag.</li>
	</ul>


</body>
</html>