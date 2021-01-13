<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.productphoto.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>

<%
MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
if (memberVo == null) {
	response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
} ;
ProductPhotoVO productPhotoVO = (ProductPhotoVO) request.getAttribute("productPhotoVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>照片資料新增</title>
<link href="<%=request.getContextPath()%>/css/product/product.css" rel="stylesheet" type="text/css">

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/productphoto/productphotoDisplay.css">
</head>
<body bgcolor='white'>
	<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>
<div align="center" style="position:relative" id="table-1">
	新增照片資料 
</div>
<div id="select" align="center">
<a href='<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp')">回樂團商品首頁</button></a>
<a href='<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp')">回商品照片首頁</button></a>
<a href='<%=request.getContextPath()%>/front-end/productphoto/protect/listAllProductPhoto.jsp'><button id="searchTable" onclick="window.location.href('<%=request.getContextPath()%>/front-end/productphoto/protect/listAllProductPhoto.jsp')">所有照片資料</button></a>
</div>

<div align="center">
<h3>照片資料新增:</h3>
</div>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" name="form1" enctype="multipart/form-data">
<table align="center" >
	
	<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />
	<tr>
		<td>商品編號:<font color=red><b>*</b></font></td>
		<td><select size="1" name="product_id">
			<c:forEach var="productVO" items="${productSvc.getBand(memberVo.getBandId())}">
				<option value="${productVO.product_id}" ${(productPhotoVO.product_id==productVO.product_id)? 'selected':'' } >${productVO.product_id}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>照片檔案:</td>
		<td><input type="file" name="productphoto_photo" id="productphoto_photo" accept="image/*" /></td>
	</tr>
	<tr>
	<td class="productphoto_photo_display">	
	<img class="pic_img" src="data:image/gif;base64,"> 
	</td>
	</tr>
	
	<tr>
		<td>排序:</td>
		<td><input type="number" name="productphoto_sort" min="1" size="15"
			 value="<%= (productPhotoVO==null)? "1" : productPhotoVO.getProductphoto_sort()%>" /></td>
	</tr>
	<tr>
		<td>上傳時間:</td>
		<td><input name="productphoto_add_time" id="f_date1" type="text"></td>
	</tr>
</table>
<br>
<div align="center" >
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增" id="submit">
</div>
</FORM>


<%-- <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/productphoto/productphoto.do" name="changepic" enctype="multipart/form-data"> --%>
<!-- <div> -->
<!-- <table> -->
<!-- 	<tr>照片檔案: -->
<!-- 		<td><input type="file" name="productphoto_photo" id="productphoto_photo" accept="image/*" size="45"/></td> -->
<!-- 		<td class="productphoto_photo_display"></td> -->
<!-- 	</tr> -->
<!-- </table>	 -->
<!-- </div> -->
<!-- <input type="hidden" name="action" value="changepic"> -->
<!-- </FORM> -->

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Timestamp productphoto_add_time = null;
  try {
	  productphoto_add_time = productPhotoVO.getProductphoto_add_time();
   } catch (Exception e) {
	   productphoto_add_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>


<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="<%=request.getContextPath() %>/js/productphoto/addProductPhoto.js"></script>


<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=productphoto_add_time%>',
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
        
</script>
<%@ include file="/css/member/member_center_bottom.file"%>
<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
</body>
</html>