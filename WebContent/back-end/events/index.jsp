<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.event.model.*"%>

<%
	EventService eventSvc = new EventService();
	List<EventVO> list = eventSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Leo_Tseng">

<title>後台管理-活動列表</title>

<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
<style>
img {
	width: 100%;
}

body {
	font-family: "Open Sans", sans-serif;
	line-height: 1.25;
}

table {
	border: 1px solid #ccc;
	border-collapse: collapse;
	margin: 0;
	padding: 0;
	width: 100%;
	table-layout: fixed;
}

table caption {
	font-size: 1.5em;
	margin: .5em 0 .75em;
}

table tr {
	background-color: #f8f8f8;
	border: 1px solid #ddd;
	padding: .35em;
}

table th, table td {
	padding: .625em;
	text-align: center;
}

table th {
	font-size: .85em;
	letter-spacing: .1em;
	text-transform: uppercase;
}

@media screen and (max-width: 600px) {
	table {
		border: 0;
	}
	table caption {
		font-size: 1.3em;
	}
	table thead {
		border: none;
		clip: rect(0, 0, 0, 0);
		height: 1px;
		margin: -1px;
		overflow: hidden;
		padding: 0;
		position: absolute;
		width: 1px;
	}
	table tr {
		border-bottom: 3px solid #ddd;
		display: block;
		margin-bottom: .625em;
	}
	table td {
		border-bottom: 1px solid #ddd;
		display: block;
		font-size: .8em;
		text-align: right;
	}
	table td::before {
		/*
    * aria-label has no advantage, it won't be read inside a table
    content: attr(aria-label);
    */
		content: attr(data-label);
		float: left;
		font-weight: bold;
		text-transform: uppercase;
	}
	table td:last-child {
		border-bottom: 0;
	}
}
</style>
</head>

<body id="page-top">
	<%@ include file="sb/page1.file"%>
	
		<div>
			<!-- Page Heading -->
			<h1 class="h3 mb-2 text-gray-800">活動列表</h1>
			<p class="mb-4">
				DataTables is a third party plugin that is used to generate the demo
				table below. For more information about DataTables, please visit the
				<a target="_blank" href="https://datatables.net">official
					DataTables documentation</a>.
			</p>
			<!-- DataTales Example -->
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">DataTables
						Example</h6>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%"
							cellspacing="0">
							<thead>
								<tr>
									<th>活動編號</th>
									<th>樂團編號</th>
									<th>活動類型</th>
									<th>活動排序</th>
									<th>活動標題</th>
									<th>活動海報</th>
									<th>活動區域</th>
									<th>活動場地</th>
									<th>活動開始時間</th>
									<th>活動上架時間</th>
									<th>最後修改時間</th>
									<th>最後修改者</th>
									<th>活動狀態</th>
									<th>修改</th>
									<th>刪除</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>活動編號</th>
									<th>樂團編號</th>
									<th>活動類型</th>
									<th>活動排序</th>
									<th>活動標題</th>
									<th>活動海報</th>
									<th>活動區域</th>
									<th>活動場地</th>
									<th>活動開始時間</th>
									<th>活動上架時間</th>
									<th>最後修改時間</th>
									<th>最後修改者</th>
									<th>活動狀態</th>
									<th>修改</th>
									<th>刪除</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach var="eventVO" items="${list}">
									<tr>
										<td>${eventVO.event_id}</td>
										<td>${eventVO.band_id}</td>
										<td>${eventVO.event_type}</td>
										<td>${eventVO.event_sort}</td>
										<td>${eventVO.event_title}</td>
										<td><img
											src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
										<td>${eventVO.event_area}</td>
										<td>${eventVO.event_place}</td>
										<td>${eventVO.event_start_time}</td>
										<td>${eventVO.event_on_time}</td>
										<td>${eventVO.event_last_edit_time}</td>
										<td>${eventVO.event_last_editor}</td>
										<td>${eventVO.event_status}</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/event/EventServlet"
												style="margin-bottom: 0px;">
												<input type="submit" value="修改"> <input
													type="hidden" name="event_id" value="${eventVO.event_id}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/event/EventServlet"
												style="margin-bottom: 0px;">
												<input type="submit" value="刪除"> <input
													type="hidden" name="event_id" value="${eventVO.event_id}">
												<input type="hidden" name="action" value="delete">
											</FORM>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

		</div>
		

	


	<%@ include file="sb/page2.file"%>


</body>

</html>