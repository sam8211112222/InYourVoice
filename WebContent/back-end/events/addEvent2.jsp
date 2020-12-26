<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.ticket.model.*"%>
<%@ page import="java.util.ArrayList"%>

<%
	EventVO eventVO = (EventVO) request.getAttribute("eventVO");
	ArrayList<TicketVO> ticketVoList = (ArrayList<TicketVO>) request.getAttribute("ticketVoList");
%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>活動新增</title>


<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

div.inputblock	{
	margin-bottom: 8px;
}
</style>
<script
	src="<%=request.getContextPath()%>/back-end/events/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.full.js"></script>
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
</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul
			class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
			id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a
				class="sidebar-brand d-flex align-items-center justify-content-center"
				href="index.html">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3">管理後台</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">





			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseTwo"
				aria-expanded="true" aria-controls="collapseTwo"> <i
					class="fas fa-fw fa-cog"></i> <span>活動管理</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
					data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">管理選項:</h6>
						<a class="collapse-item" href="buttons.html">活動列表</a> <a
							class="collapse-item" href="buttons.html">活動新增</a> <a
							class="collapse-item" href="cards.html">活動修改</a>
					</div>
				</div></li>

			<!-- Nav Item - Utilities Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseUtilities"
				aria-expanded="true" aria-controls="collapseUtilities"> <i
					class="fas fa-fw fa-wrench"></i> <span>票券匣管理</span>
			</a>
				<div id="collapseUtilities" class="collapse"
					aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Custom Utilities:</h6>
						<a class="collapse-item" href="utilities-color.html">Colors</a> <a
							class="collapse-item" href="utilities-border.html">Borders</a> <a
							class="collapse-item" href="utilities-animation.html">Animations</a>
						<a class="collapse-item" href="utilities-other.html">Other</a>
					</div>
				</div></li>






			<!-- Divider -->
			<hr class="sidebar-divider d-none d-md-block">

			<!-- Sidebar Toggler (Sidebar) -->
			<div class="text-center d-none d-md-inline">
				<button class="rounded-circle border-0" id="sidebarToggle"></button>
			</div>

		</ul>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Topbar Search -->
					<form
						class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
						<div class="input-group">
							<input type="text" class="form-control bg-light border-0 small"
								placeholder="Search for..." aria-label="Search"
								aria-describedby="basic-addon2">
							<div class="input-group-append">
								<button class="btn btn-primary" type="button">
									<i class="fas fa-search fa-sm"></i>
								</button>
							</div>
						</div>
					</form>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..." aria-label="Search"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<!-- Nav Item - Alerts -->
						<li class="nav-item dropdown no-arrow mx-1"><a
							class="nav-link dropdown-toggle" href="#" id="alertsDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-bell fa-fw"></i> <!-- Counter - Alerts -->
								<span class="badge badge-danger badge-counter">3+</span>
						</a> <!-- Dropdown - Alerts -->
							<div
								class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="alertsDropdown">
								<h6 class="dropdown-header">Alerts Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-primary">
											<i class="fas fa-file-alt text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 12, 2019</div>
										<span class="font-weight-bold">A new monthly report is
											ready to download!</span>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-success">
											<i class="fas fa-donate text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 7, 2019</div>
										$290.29 has been deposited into your account!
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-warning">
											<i class="fas fa-exclamation-triangle text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 2, 2019</div>
										Spending Alert: We've noticed unusually high spending for your
										account.
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500"
									href="#">Show All Alerts</a>
							</div></li>

						<!-- Nav Item - Messages -->
						<li class="nav-item dropdown no-arrow mx-1"><a
							class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i>
								<!-- Counter - Messages --> <span
								class="badge badge-danger badge-counter">7</span>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="messagesDropdown">
								<h6 class="dropdown-header">Message Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle" src="img/undraw_profile_1.svg"
											alt="">
										<div class="status-indicator bg-success"></div>
									</div>
									<div class="font-weight-bold">
										<div class="text-truncate">Hi there! I am wondering if
											you can help me with a problem I've been having.</div>
										<div class="small text-gray-500">Emily Fowler · 58m</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle" src="img/undraw_profile_2.svg"
											alt="">
										<div class="status-indicator"></div>
									</div>
									<div>
										<div class="text-truncate">I have the photos that you
											ordered last month, how would you like them sent to you?</div>
										<div class="small text-gray-500">Jae Chun · 1d</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle" src="img/undraw_profile_3.svg"
											alt="">
										<div class="status-indicator bg-warning"></div>
									</div>
									<div>
										<div class="text-truncate">Last month's report looks
											great, I am very happy with the progress so far, keep up the
											good work!</div>
										<div class="small text-gray-500">Morgan Alvarez · 2d</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="">
										<div class="status-indicator bg-success"></div>
									</div>
									<div>
										<div class="text-truncate">Am I a good boy? The reason I
											ask is because someone told me that people say this to all
											dogs, even if they aren't good...</div>
										<div class="small text-gray-500">Chicken the Dog · 2w</div>
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500"
									href="#">Read More Messages</a>
							</div></li>

						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas
									McGee</span> <img class="img-profile rounded-circle"
								src="img/undraw_profile.svg">
						</a> <!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item" href="#"> <i
									class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
									Settings
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
									Activity Log
								</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal"
									data-target="#logoutModal"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									Logout
								</a>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<h1 class="h3 mb-4 text-gray-800">Blank Page</h1>

					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/event/EventServlet"
						name="form1" enctype="multipart/form-data">
						<div>
							<div class="inputblock">
								<span>活動標題:
								<input type="TEXT" name="event_title" size="45"
									value="<%=(eventVO == null) ? "請輸入活動標題" : eventVO.getEvent_title()%>" />
									</span>
							</div>
							<div class="inputblock">
								<span>樂團編號:
								<input type="TEXT" name="band_id" size="45"
									value="<%=(eventVO == null) ? "請輸入活動標題" : eventVO.getBand_id()%>" /></span>
							</div>
							<div class="inputblock">
								<span>活動類型:
								<select name="event_type" size="1">
										<option value="0"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_type() == 0) ? "selected" : ""%>>一般活動</option>
										<option value="1"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_type() == 1) ? "selected" : ""%>>放鬆好去處</option>
										<option value="2"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_type() == 2) ? "selected" : ""%>>特色活動</option>
										<option value="3"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_type() == 3) ? "selected" : ""%>>主打活動</option>
										<option value="4"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_type() == 4) ? "selected" : ""%>>最新消息</option>
								</select></span>
								<span>活動開始時間:
								<input name="event_start_time" id="f_date1" type="text"></span>
							</div>
							<div class="inputblock">
								<span>活動詳情:</span>
								<textarea name="event_detail" rows="20" cols="80"></textarea>
							</div>
							<div class="inputblock">
								<span>活動排序:
								<input type="TEXT" name="event_sort" size="46"
									value="<%=(eventVO == null) ? "請輸入0-99的數字" : eventVO.getEvent_sort()%>" /></span>
							</div>
							<tr>
								<td>活動海報:</td>
								<td><input type="file" name="event_poster"
									id="event_poster"></td>
								<td>預覽圖</td>
								<td><img id="event_poster_img"
									<%=(eventVO == null)
					? ""
					: "src=\"" + request.getContextPath() + "/EventPicController?action=getEventPoster&event_id="
							+ eventVO.getEvent_id() + "\""%>></td>
							</tr>

							<tr>
								<td>活動場地:</td>
								<td><input type="TEXT" name="event_place" size="45"
									value="<%=(eventVO == null) ? "請輸入活動場地" : eventVO.getEvent_place()%>" /></td>
							</tr>
							<tr>
								<td>活動區域:</td>
								<td><select name="event_area" size="1">
										<option value="0"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_area() == 0) ? "selected" : ""%>>北部</option>
										<option value="1"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_area() == 1) ? "selected" : ""%>>中部</option>
										<option value="2"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_area() == 2) ? "selected" : ""%>>南部</option>
										<option value="3"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_area() == 3) ? "selected" : ""%>>東部</option>
										<option value="4"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_area() == 4) ? "selected" : ""%>>離島</option>
								</select></td>
							</tr>
							<tr>
								<td>活動縣市:</td>
								<td><input type="TEXT" name="event_city" size="45"
									value="<%=(eventVO == null) ? "請輸入活動縣市" : eventVO.getEvent_city()%>" /></td>
							</tr>
							<tr>
								<td>活動縣市分區:</td>
								<td><input type="TEXT" name="event_cityarea" size="45"
									value="<%=(eventVO == null) ? "請輸入縣市地區" : eventVO.getEvent_cityarea()%>" /></td>
							</tr>
							<tr>
								<td>活動地址:</td>
								<td><input type="TEXT" name="event_address" size="45"
									value="<%=(eventVO == null) ? "請輸入活動地址" : eventVO.getEvent_address()%>" /></td>
							</tr>
							<tr>
								<td>活動上架時間:</td>
								<td><input name="event_on_time" id="f_date2" type="text"></td>
							</tr>
							<tr>
								<td>活動狀態:</td>
								<td><select name="event_status" size="1">
										<option value="0"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_status() == 0) ? "selected" : ""%>>下架</option>
										<option value="1"
											<%=(eventVO == null) ? "" : (eventVO.getEvent_status() == 1) ? "selected" : ""%>>上架</option>
								</select></td>
							</tr>
							<tr>
								<td>座位圖:</td>
								<td><input type="file" name="event_seat" size="45"
									id="event_seat"></td>
								<td>預覽圖</td>
								<td><img id="event_seat_img"
									<%=(eventVO == null)
					? ""
					: "src=\"" + request.getContextPath() + "/EventPicController?action=getEventSeat&event_id="
							+ eventVO.getEvent_id() + "\""%>></td>
							</tr>





						</div>
						<div id="ticket_block">
							<c:if test="${not empty ticketVoList}">
								<c:forEach var="ticketVO" items="${ticketVoList}">
									<div class="ticket">
										<span>請輸入票種名稱:<input type="text" name="ticket_name"
											value="${ticketVO.ticket_name}"></span> <span>票種金額:<input
											type="text" name="ticket_price"
											value="${ticketVO.ticket_price}"></span> <span>票種總張數:<input
											type="text" name="ticket_amount"
											value="${ticketVO.ticket_amount}"></span> <span>票種排序:<input
											type="text" name="ticket_sort"
											value="${ticketVO.ticket_sort}"></span> <span>開始販售時間:<input
											type="text" name="ticket_onsale_time"
											class="ticket_onsale_time"
											value="${ticketVO.ticket_onsale_time}"></span> <span>結束販售時間:<input
											type="text" name="ticket_endsale_time"
											class="ticket_endsale_time"
											value="${ticketVO.ticket_endsale_time}"></span> <span>票券販售狀態<select
											name="ticket_status" size="1">
												<option value="0"
													${ticketVO.ticket_status == 0 ? "selected" : ""}>下架</option>
												<option value="1"
													${ticketVO.ticket_status == 1 ? "selected" : ""}>上架</option>

										</select></span>
										<button type="button" class="delete">刪除</button>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<br> <input type="hidden" name="event_last_editor"
							value="members00000"> <input type="hidden" name="action"
							value="insert"> <input type="submit" value="送出新增"
							id="submit">
					</FORM>
					<br><button type ="button" id="addTicket">新增票種</button>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; Your Website 2020</span>
					</div>
				</div>
			</footer>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript-->
	<%
		java.sql.Timestamp event_start_time = null;
		java.sql.Timestamp event_on_time = null;
		java.sql.Timestamp[] ticket_onsale_time = null;

		try {
			event_start_time = eventVO.getEvent_start_time();
		} catch (Exception e) {
			event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
		try {
			event_on_time = eventVO.getEvent_on_time();
		} catch (Exception e) {
			event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
		}

		if (ticketVoList != null) {
			ticket_onsale_time = new java.sql.Timestamp[ticketVoList.size()];
			for (int i = 0; i < ticketVoList.size(); i++) {
				try {
					ticket_onsale_time[i] = ticketVoList.get(i).getTicket_onsale_time();
				} catch (Exception e) {
					ticket_onsale_time[i] = new java.sql.Timestamp(System.currentTimeMillis());
				}
			}

		}
	%>
	<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=event_start_time%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        $('#f_date2').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:true,       //timepicker:true,
 	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
 		   value: '<%=event_on_time%>', // value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});
//        	var onsale_time = $("input.ticket_onsale_time").length;
//        	var ticket_onsale_time = {<>}
			
			
	       	$("input.ticket_onsale_time").datetimepicker({
	      	       theme: '',              //theme: 'dark',
	      	       timepicker:true,       //timepicker:true,
	      	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
	      	       format:'Y-m-d H:i'         //format:'Y-m-d H:i:s',
	      		 // value:   new Date(),
	     	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	     	//startDate:	            '2017/07/10',  // 起始日
	     	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	     	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	     		}); 
	
		
	
 	

	// ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

	//      1.以下為某一天之前的日期無法選擇
	//      var somedate1 = new Date('2017-06-15');
	//      $('#f_date1').datetimepicker({
	//          beforeShowDay: function(date) {
	//        	  if (  date.getYear() <  somedate1.getYear() || 
	//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
	//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
	//              ) {
	//                   return [false, ""]
	//              }
	//              return [true, ""];
	//      }});

	//      2.以下為某一天之後的日期無法選擇
	//      var somedate2 = new Date('2017-06-15');
	//      $('#f_date1').datetimepicker({
	//          beforeShowDay: function(date) {
	//        	  if (  date.getYear() >  somedate2.getYear() || 
	//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
	//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
	//              ) {
	//                   return [false, ""]
	//              }
	//              return [true, ""];
	//      }});

	//      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
	//      var somedate1 = new Date('2017-06-15');
	//      var somedate2 = new Date('2017-06-25');
	//      $('#f_date1').datetimepicker({
	//          beforeShowDay: function(date) {
	//        	  if (  date.getYear() <  somedate1.getYear() || 
	//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
	//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
	//		             ||
	//		            date.getYear() >  somedate2.getYear() || 
	//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
	//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
	//              ) {
	//                   return [false, ""]
	//              }
	//              return [true, ""];
	//      }});
	$("#event_poster").on("change", function(e) {
		let reader = new FileReader();
		reader.readAsDataURL(this.files[0]);
		reader.addEventListener("load", function(e) {
			$("#event_poster_img").attr("src", reader.result);
		})
	})
	$("#event_seat").on("change", function(e) {
		let reader = new FileReader();
		reader.readAsDataURL(this.files[0]);
		reader.addEventListener("load", function(e) {
			$("#event_seat_img").attr("src", reader.result);
		})
	})
	
	
	
    $("#addTicket").on("click", function () {
            addTicket();
            $("#ticket_block").find("input.ticket_onsale_time").eq(0).datetimepicker({
       	       theme: '',              //theme: 'dark',
       	       timepicker:true,       //timepicker:true,
       	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
       	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
       		   value: new Date()
      	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
      	//startDate:	            '2017/07/10',  // 起始日
      	//minDate:               '-1970-01-01', // 去除今日(不含)之前
      	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
      		});
            $("#ticket_block").find("input.ticket_endsale_time").eq(0).datetimepicker({
        	       theme: '',              //theme: 'dark',
        	       timepicker:true,       //timepicker:true,
        	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
        	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
        		   value: new Date()
       	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       	//startDate:	            '2017/07/10',  // 起始日
       	//minDate:               '-1970-01-01', // 去除今日(不含)之前
       	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
       		});
            
            i++;
    })
	
	$("#ticket_block").on("click", "button.delete", function (e) {
            $(this).closest("div.ticket").remove();
    })


    function addTicket() {
            $("#ticket_block").prepend(`<div class="ticket"><span>請輸入票種名稱:<input type="text" name="ticket_name"></span><span>票種金額:<input type="text" name="ticket_price"></span>
            <span>票種總張數:<input type="text" name="ticket_amount"></span>
            <span>票種排序:<input type="text" name="ticket_sort"></span>
            <span>開始販售時間:<input type="text" name="ticket_onsale_time" class="ticket_onsale_time"></span>
            <span>結束販售時間:<input type="text" name="ticket_endsale_time" class="ticket_endsale_time"></span>
            <span>票券販售狀態<select name="ticket_status" size="1">
			<option value="0" "selected">下架</option>
			<option value="1">上架</option>
			</select></span>
            <button type="button" class="delete">刪除</button>
            </div>`);
    }
	
	CKEDITOR.replace( 'event_detail', {});
</script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script
		src="<%=request.getContextPath()%>/vendors/sb-admin-2/js/sb-admin-2.min.js"></script>
		<script
	src="<%=request.getContextPath()%>/back-end/events/js/jquery-3.5.1.min.js"></script>
</body>

</html>