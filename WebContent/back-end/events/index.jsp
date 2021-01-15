<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>
<fmt:setLocale value="zh_tw" />
<%
	EventService eventSvc = new EventService();
	List<EventVO> list = eventSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>後台管理-活動列表</title>
<style>
</style>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom fonts for this template -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css"
	rel="stylesheet">

<!-- Custom styles for this page -->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<style>
.column_width {
	min-width: 50px;
}

.table {
	width: auto;
	overflow-x: auto !important;
	word-break: break-all !important;
}

img {
	width: 207.5px;
	height: 110px;
}

.title_width {
	max-width: 100px;
	word-break:break-all;
	WORD-WRAP: break-word;
}

.place_width {
	min-width: 200px;
}

</style>
</head>

<body>
	<%@ include file="/back-end/sb/page1.file"%>
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">
				活動列表 
			</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<table class="table table-bordered nowrap" id="dataTable" cellspacing="0">
					<thead>
						<tr>
							<th>活動編號</th>
							<th>樂團編號</th>
							<th>活動類型</th>
							<th>活動排序</th>
							<th><span>活動標題</span></th>
							<th>活動海報</th>
							<th>活動區域</th>
							<th class="place_width">活動場地</th>
							<th>活動開始時間</th>
							<th>活動上架時間</th>
							<th>最後修改時間</th>
							<th>最後修改者</th>
							<th>活動狀態</th>
							<th>修改</th>
							<th>下架</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="eventVO" items="${list}">
							<tr>
								<td>${eventVO.event_id}</td>
								<td>${eventVO.band_id}</td>
								<td>${eventVO.event_type == 0 ? "一般活動": eventVO.event_type == 1 ? "放鬆好去處":eventVO.event_type == 3 ? "特色活動":eventVO.event_type == 4 ? "主打活動":"最新消息"}</td>
								<td>${eventVO.event_sort}</td>
								<td><span>${eventVO.event_title}</span></td>
								<td><img
									src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
								<td>${eventVO.event_area == 0 ? "北部":eventVO.event_area == 1 ? "中部": eventVO.event_area == 2 ? "南部": eventVO.event_area == 3 ?"東部":"離島"}</td>
								<td class="place_width"><span>${eventVO.event_place}</span></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd EEE HH:MM"
										value="${eventVO.event_start_time}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd EEE HH:MM"
										value="${eventVO.event_on_time}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd EEE HH:MM"
										value="${eventVO.event_last_edit_time}" /></td>
								<td>${eventVO.event_last_editor}</td>
								<td>${eventVO.event_status == 1 ? "上架中":eventVO.event_status == 0 ? "下架": eventVO.event_status == 2 ? "審核中": eventVO.event_status == 3 ? "草稿":"未通過"}</td>
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/event/EventServlet"
										style="margin-bottom: 0px;">
										<input type="submit" value="修改"> <input type="hidden"
											name="event_id" value="${eventVO.event_id}"> <input
											type="hidden" name="action" value="getOne_For_Update">
									</FORM>
								</td>
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/event/EventServlet"
										style="margin-bottom: 0px;">
										<input type="submit" value="下架" class="del_btn"> <input
											type="hidden" name="event_id" value="${eventVO.event_id}">
										<input type="hidden" name="action" value="off-shelf">
									</FORM>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/sb/page2.file"%>
	<!-- Bootstrap core JavaScript-->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/jquery.dataTables.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.js"></script>

	<!-- Page level custom scripts -->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/js/demo/datatables-demo.js"></script>



<script>
	$(document).ready(function() {
		$('#dataTable').DataTable();
	});

	$(function() {
		$(document).on("click", ".del_btn", function(e) {
			if (!confirm("確定要下架嗎?")) {
				e.preventDefault();
				alert("已取消操作!")
			}
		})
	})
</script>

</body>
</html>