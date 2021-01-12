<%@page import="com.notification.model.JedisMessage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.notification.model.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
	
	pageContext.setAttribute("mesg", JedisMessage.getMessage(memberVo.getMemberId()));
	pageContext.setAttribute("isRead", JedisMessage.count(memberVo.getMemberId()));
	pageContext.setAttribute("new5", JedisMessage.getMessageNew5(memberVo.getMemberId()));
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<body>
<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>

	<div class="container content clear-fix">

		<h2 class="mt-5 mb-5">通知中心</h2>

		<div class="row" style="height: 100%">
			<div class="col-md-10" style="margin-left: 60px;">
				<table class="table">
					<thead>
						<tr>
							<th scope="col"></th>
							<th scope="col">標題</th>
							<th scope="col">接收人</th>
							<th scope="col">時間</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="memNoti" items="${mesg}">
						<tr>
							<th scope="row">1</th>
							<td>${memNoti.title}</td>
							<td>${memNoti.receiver}</td>
							<td><fmt:formatDate value="${memNoti.sendTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>



			</div>

		</div>

	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>