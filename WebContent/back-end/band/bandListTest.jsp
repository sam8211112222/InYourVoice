<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.band.model.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<%
	EmpVO empVO = (EmpVO) session.getAttribute("empVO");
	BandService bandrSvc = new BandService();
	List<BandVO> list = bandrSvc.getAllBand();
	pageContext.setAttribute("list", list);
%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Tables</title>

<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>
.-none {
	display: none;
}

.update {
	width: 80px;
}
</style>
</head>

<body id="page-top">


	<%@ include file="/back-end/sb/page1.file"%>
	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Page Heading -->
		<h1 class="h3 mb-2 text-gray-800">樂團資料</h1>
		<!-- DataTales Example -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">樂團資料表</h6>
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
						<thead>
							<tr>
								<th>樂團ID</th>
								<th>樂團名字</th>
								<th>加入日期</th>
								<th>審核狀態</th>
								<th>編輯時間</th>
								<th>編輯者</th>
								<th></th>
								<th></th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="bandVO" items="${list}">
								<tr>
									<td><p>${bandVO.band_id}</p></td>
									<td>${bandVO.band_name}</p></td>
									<td><fmt:formatDate value="${bandVO.band_add_time}" pattern="yyyy-MM-dd" /></td>
									<td>
									<c:if test="${bandVO.band_status==0}">
									<p class="para">審核未通過</p>
									</c:if>
									<c:if test="${bandVO.band_status==1}">
									<p class="para">審核通過</p>
									</c:if>
									<c:if test="${bandVO.band_status==2}">
									<p class="para">樂團下架</p>
									</c:if>
									
									<select class="update -none">	
											<option ${bandVO.band_status == 0 ? "selected":""} value="0">審核未通過</option>
											<option ${bandVO.band_status == 1 ? "selected":""} value="1">審核通過</option>
											<option ${bandVO.band_status == 2 ? "selected":""} value="2">樂團下架</option>										
									</select>
								
									</td>
									<td><fmt:formatDate value="${bandVO.band_last_edit_time}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td>${bandVO.band_last_editor}</td>
									<td><input type="button" class="updatebtn" value="修改"></td>

									<c:choose>
										<c:when test="${bandVO.band_status==0}">
											<td><a href="<%=request.getContextPath()%>/band/band.do?action=getAuditPage&bandId=${bandVO.band_id}"><input type="button" class="auditbtn" value="審核"></a></td>
										</c:when>
										<c:when test="${bandVO.band_status!=0}">
											<td>通過審核</td>
										</c:when>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	<!-- /.container-fluid -->

	<!-- End of Main Content -->
	<%@ include file="/back-end/sb/page2.file"%>
	<script src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script>
		$(".updatebtn")
				.click(function(e) {
						var that = $(this);
						let status = that.closest("tr").find("select.update")[0].value;
							console.log(status);
							if (status != "") {
								that.closest("tr").find("select.update").toggleClass("-none");
								that.closest("tr").find("p.para").toggleClass("-none");
								let val = that.closest("tr").find("p.para")[0].innerText;
								if(status==0){
								that.closest("tr").find("p.para")[0].innerText = "審核未通過";
								}else if(status==1){
									that.closest("tr").find("p.para")[0].innerText = "審核通過";
								}else if(status==2){
									that.closest("tr").find("p.para")[0].innerText = "樂團下架";
								}
								var bandId = that.closest("td").closest("tr")[0].children[0].innerText;
								var bandStatus = status;
								var bandLastEditor = "${empVO.emp_id}";

								if (val != status) {
									let obj = {
										action : "updateBackEnd",
										bandId : bandId,
										bandLastEditor : bandLastEditor,
										bandStatus : bandStatus,
									}
									$
											.ajax({
												type : "POST",
												url : "/TEA102G6/band/band.do",
												dataType : "JSON",
												data : obj,
												success : function(result) {

													that.closest("td").closest(
															"tr")[0].children[5].innerText = result.bandLastEditor;
													that.closest("td").closest(
															"tr")[0].children[4].innerText = result.bandLastEditTime;
												},
												error : function(err) {
													alert("系統錯誤");
												}
											});
								}
							}
						})
	</script>
</body>

</html>