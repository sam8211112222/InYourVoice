<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<%
	MemberService memberSvc = new MemberService();
	List<MemberVo> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Tables</title>

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
.-none {
	display: none;
}
.update{
width:120px;
}
.addMember{
float:right;
}
</style>
</head>

<body id="page-top">


	<%@ include file="/back-end/sb/page1.file"%>
	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Page Heading -->
		<h1 class="h3 mb-2 text-gray-800">會員資料</h1>
		<!-- DataTales Example -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">會員資料表</h6>
				<input type="button" value="新增會員" class="addMember">
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered" id="dataTable" width="100%"
						cellspacing="0">
						<thead>
							<tr>
								<th>會員ID</th>
								<th>會員名字</th>
								<th>會員性別</th>
								<th>會員電話</th>
								<th>會員生日</th>
								<th>加入日期</th>
								<th></th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="memberVo" items="${list}">
								<tr>
									<td><p>${memberVo.memberId}</p></td>
									<td><p class="para">${memberVo.memberName}</p> <input
										type="text" class="update -none"
										value="${memberVo.memberName}"></td>
									<td><p class="para">${memberVo.memberGender}</p> <input
										type="text" class="update -none"
										value="${memberVo.memberGender}"></td>
									<td><p class="para">${memberVo.memberPhone}</p> <input
										type="text" class="update -none"
										value="${memberVo.memberPhone}"></td>
									<td><fmt:formatDate value="${memberVo.memberBirth}"
											pattern="yyyy-MM-dd" /></td>
									<td><fmt:formatDate value="${memberVo.addTime}"
											pattern="yyyy-MM-dd" /></td>
									<td><input type="button" class="updatebtn" value="修改"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	<!-- /.container-fluid -->

	</div>
	<!-- End of Main Content -->

	<%@ include file="/back-end/sb/page2.file"%>
	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	
	<script>
		$(".updatebtn")
				.click(
						function(e) {
							var that = $(this);
							var length = $(this).closest("tr").find(
									"input.update").length;
							var list = [];
							for (var i = 0; i < length; i++) {
								let info = (that.closest("tr").find(
										"input.update")[i].value.trim());
								if (info != "") {
									list.push(info);
								}
							}
							if (list.length == 3) {
								const phoneReg = new RegExp("^0(9)[0-9]{8}$");
								if (list[2].match(phoneReg)) {
									let memberPhone = list[2];
									let memberId = that.closest("td").closest(
											"tr")[0].children[0].innerText;
									let memberName = list[0];
									let memberGender = list[1];
									that.closest("td").closest("tr").find(
											"input.update")
											.toggleClass("-none");
									that.closest("td").closest("tr").find(
											"p.para").toggleClass("-none");
									let l = e.target.closest("td")
											.closest("tr");
									for (var i = 0; i < list.length; i++) {
										let v = that.closest("tr").find(
												"p.para")[i];
										let newV = that.closest("tr").find(
												"input.update")[i];
										v.innerText = newV.value;
									}
									//ajax請求開始修改

									let obj = {
										action : "update",
										memberId : memberId,
										memberName : memberName,
										memberGender : memberGender,
										memberPhone : memberPhone,
									}
									$.ajax({
										type : "POST",
										url : "/TEA102G6/BackendController",
										data : obj,
										success : function(result) {

										},
										error : function(err) {
											alert("系統錯誤");
										}
									});
								} else {
									alert("請確認資料");
								}
							}

						})
	</script>
</body>

</html>