<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.event.model.*"%>
<fmt:setLocale value="zh_tw" />
<%
	MemberVo memberVo = (MemberVo) request.getSession().getAttribute("memberVo");
	EventService eventSvc = new EventService();
	List<EventVO> bandEvent = eventSvc.getEventsForBandManage(memberVo.getBandId());
	pageContext.setAttribute("bandEvent", bandEvent);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>會員中心-活動管理</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<style>
.container content {
	text-align: -webkit-center;
	border: 2px solid;
	border-radius: 50px;
}

.title_width {
	min-width: 250px;
}

.poster_width {
	min-width: 200px;
}

.place_width {
	min-width: 200px;
}

.time_width {
	min-width: 200px;
}

.status_width {
	min-width: 100px;
}

.update_width {
	max-width: 100px;
}

form.update_width {
	max-width: 100%;
}

table {
	text-align: center;
}
</style>
<body>
<%@ include file="/front-end/header_footer/header.jsp"%>

	<%@ include file="/css/member/member_center_top.file"%>

	<div>
		<!-- Page Heading -->
		<h1 class="h3 mb-2 text-gray-800">活動列表</h1>
		<!-- DataTales Example -->
		<div class="card shadow mb-4">

			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered" id="dataTable" width="100%"
						cellspacing="0">
						<thead>
							<tr>
								<th class="title_width"><span>活動標題</span></th>
								<th class="poster_width">活動海報</th>
								<th class="place_width">活動場地</th>
								<th class="time_width">開始時間</th>
								<th class="time_width">上架時間</th>
								<th class="time_width">最後修改時間</th>
								<th class="status_width">活動狀態</th>
								<th class="update_width">修改</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="eventVO" items="${bandEvent}">
								<tr>
									<td class="title_width"><span>${eventVO.event_title}</span></td>
									<td class="poster_width"><img
										src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
									<td class="place_width"><span>${eventVO.event_place}</span></td>
									<td class="time_width"><span><fmt:formatDate
												pattern="yyyy-MM-dd EEE HH:MM"
												value="${eventVO.event_start_time}" /></span></td>
									<td class="time_width"><span><fmt:formatDate
												pattern="yyyy-MM-dd EEE HH:MM"
												value="${eventVO.event_on_time}" /></span></td>
									<td class="time_width"><span><fmt:formatDate
												pattern="yyyy-MM-dd EEE HH:MM"
												value="${eventVO.event_last_edit_time}" /></span></td>
									<td class="status_width"><span>${eventVO.event_status == 1 ? "上架中":eventVO.event_status == 0 ? "下架": eventVO.event_status == 2 ? "審核中": eventVO.event_status == 3 ? "草稿":"未通過"}</span></td>
									<td class="update_width"><span>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/MemberCenterEventController"
												style="margin-bottom: 0px;" class="update_width">
												<input type="submit" value="修改"> <input
													type="hidden" name="event_id" value="${eventVO.event_id}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
									</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<a style="width : 100px; margin: 10px auto;" href="<%=request.getContextPath()%>/front-end/event/protect/add.jsp " class="btn btn-primary">新增活動</a>
		</div>

	</div>


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>

	<%@ include file="/css/member/member_center_bottom.file"%>
	<%@ include file="/front-end/header_footer/footer.jsp"%>
	
</body>
</html>