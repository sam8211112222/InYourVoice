<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>專輯管理</title>
<style>
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

<style>
    .column_width{
        min-width: 50px;
    }
    .table{
        width:auto;
        overflow-x:auto !important;
    }
</style>
</head>
<body>
	<%@ include file="/back-end/sb/page1.file" %>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">專輯作品管理</h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" cellspacing="0">
                    <thead>
                        <tr>
                            <th>專輯編號</th>
                            <th>樂團編號</th>
                            <th>專輯名稱</th>
                            <th>狀態</th>
                            <th>新增時間</th>
                            <th>最後編輯時間</th>
                            <th>最後編輯者</th>
                            <th>編輯</th>
                            <th>刪除</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="albumVO" items="${albumSvc.allAlbums}">
                        
                            <tr>
                                <td>${albumVO.album_id}</td>
                                <td>${albumVO.band_id}</td>
                                <td>${albumVO.album_name}</td>
                                <td>${albumVO.album_status == 0 ? "下架":"上架"}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_add_time}" /></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${albumVO.album_last_edit_time}" /></td>
                                <td>${albumVO.album_last_editor}</td>
                                <td  class="column_width">
                                    <form action="<%= request.getContextPath() %>/album/album.do">
                                        <input type="hidden" name="album_id" value="${albumVO.album_id}">
                                        <button type="submit" name="action" value="getOne_For_Update">編輯</button>
                                    </form>
                                </td>
                                <td  class="column_width">
                                    <form action="<%= request.getContextPath() %>/album/album.do">
                                        <input type="hidden" name="album_id" value="${albumVO.album_id}">
                                        <button class="del_btn" type="submit" name="action" value="deleteAlbum">刪除</button>
                                    </form>
                                </td>
                            </tr>  
                            
                        </c:forEach>          
                    </tbody>
                    <!-- <thead>
                        <tr>
                            <th>Name</th>
                            <th>Position</th>
                            <th>Office</th>
                            <th>Age</th>
                            <th>Start date</th>
                            <th>Salary</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>Name</th>
                            <th>Position</th>
                            <th>Office</th>
                            <th>Age</th>
                            <th>Start date</th>
                            <th>Salary</th>
                        </tr>
                    </tfoot>
                    <tbody>                        
                        <tr>
                            <td>Michael Bruce</td>
                            <td>Javascript Developer</td>
                            <td>Singapore</td>
                            <td>29</td>
                            <td>2011/06/27</td>
                            <td>$183,000</td>
                        </tr>
                        <tr>
                            <td>Donna Snider</td>
                            <td>Customer Support</td>
                            <td>New York</td>
                            <td>27</td>
                            <td>2011/01/25</td>
                            <td>$112,000</td>
                        </tr>
                    </tbody> -->
                </table>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/sb/page2.file" %>
    <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="<%=request.getContextPath()%>/vendors/sb-admin-2/js/demo/datatables-demo.js"></script>

    <script>
           $(document).ready( function () {
               $('#album').DataTable();
           } );

           $(function(){
               $(document).on("click", ".del_btn", function(e){
                if(!confirm("確定要刪除嗎?")){
        			   e.preventDefault();
        			   alert("已取消操作!")
        		   }
               })
           })
    </script>

</body>
</html>