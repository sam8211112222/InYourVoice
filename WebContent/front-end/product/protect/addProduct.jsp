<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>商品資料新增</title>
<link href="<%=request.getContextPath()%>/css/product/product.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>
</head>
<body bgcolor='white'>
		<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>
	<div id="table-1">商品資料新增</div>

	<div id="select" align="center">
		<a
			href='<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp'><button
				id="searchTable"
				onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/select_page.jsp')">回商品首頁</button></a>
		<a
			href='<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp'><button
				id="searchTable"
				onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllProduct.jsp')">列出全部商品</button></a>
		<a
			href='<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp'><button
				id="searchTable"
				onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllOrdersBandView.jsp')">列出商品訂單</button></a>
	</div>
	<div id="select" align="center">
		<a
			href='<%=request.getContextPath()%>/front-end/product/protect/listAllTicketBandView.jsp'><button
				id="searchTable"
				onclick="window.location.href('<%=request.getContextPath()%>/front-end/product/protect/listAllTicketBandView.jsp')">列出票卷訂單</button></a>
		<a
			href='<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp'><button
				id="searchTable"
				onclick="window.location.href('<%=request.getContextPath()%>/front-end/productphoto/protect/select_page.jsp')">商品照片首頁</button></a>
	</div>

	<div id="info" align="center">
		<h4>請輸入以下商品資訊:</h4>
	</div>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/product/product.do" name="form1">
		<table align="center">
			<jsp:useBean id="productSvc" scope="page"
				class="com.product.model.ProductService" />
			<tr>
				<td>樂團編號:</td>
				<td><input type="text" name="band_id" size="15"
					value="${memberVo.bandId}" /></td>
			</tr>
			<tr>
				<td>商品分類:</td>
				<td><select name="product_type">
						<option value="1">上衣</option>
						<option value="2">褲子</option>
						<option value="3">配件</option>
				</select></td>
			</tr>
			<tr>
				<td>商品名稱:</td>
				<td><input type="TEXT" name="product_name" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_name()%>" /></td>
			</tr>
			<tr>
				<td>商品簡介:</td>
				<td><input type="TEXT" name="product_intro" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_intro()%>" /></td>
			</tr>
			<tr>
				<td>商品詳細說明:</td>
				<td><input type="TEXT" name="product_detail" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_detail()%>" /></td>
			</tr>
			<tr>
				<td>商品單價:</td>
				<td><input type="TEXT" name="product_price" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_price()%>" /></td>
			</tr>
			<tr>
				<td>商品庫存量:</td>
				<td><input type="number" min="1" name="product_stock" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_stock()%>" /></td>
			</tr>
			<tr>
				<td>審核狀態:</td>
				<td><select name="product_check_status">
						<option value="0">待審核</option>
				</select></td>
			</tr>
			<tr>
				<td>上下架狀態:</td>
				<td><select name="product_status">
						<option value="1">上架</option>
				</select></td>
			</tr>
			<tr>
				<td>預計上架時間:</td>
				<td><input name="product_on_time" id="f_date1" type="text"
					size="15"></td>
			</tr>
			<tr>
				<td>預計下架時間:</td>
				<td><input name="product_off_time" id="f_date2" type="text"
					size="15"></td>
			</tr>
			<tr>
				<td>提交時間:</td>
				<td><input name="product_add_time" id="f_date6" type="text"
					size="15"></td>
			</tr>
			<tr>
				<td>折扣:</td>
				<td><input type="number" name="product_discount"
					placeholder="1.0" step="0.01" min="0.0" max="1.0" size="15"
					value="<%=(productVO == null) ? "" : productVO.getProduct_discount()%>" /></td>
			</tr>
			<tr>
				<td>折扣開始時間:</td>
				<td><input name="product_discount_on_time" id="f_date3"
					type="text" size="15"></td>
			</tr>
			<tr>
				<td>折扣結束時間:</td>
				<td><input name="product_discount_off_time" id="f_date4"
					type="text" size="15"></td>
			</tr>
			<tr>
				<td>最後修改時間:</td>
				<td><input name="product_last_edit_time" id="f_date5"
					type="text" size="15"></td>
			</tr>

			<tr>
				<td>最後修改者:</td>
				<td><input type="text" name="product_last_editor" size="15"
					value="${memberVo.bandId }" /></td>
			</tr>

		</table>
		<br>
		<div align="center">
			<input type="hidden" name="action" value="insert"> 
			<input type="hidden" name="product_id" value="${param.product_id}">
			<input type="submit" value="送出審核" id="submit">
		</div>
	</FORM>

	<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

	<%
		java.sql.Timestamp product_on_time = null;
		try {
			product_on_time = productVO.getProduct_on_time();
		} catch (Exception e) {
			product_on_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Timestamp product_off_time = null;
		try {
			product_off_time = productVO.getProduct_off_time();
		} catch (Exception e) {
			product_off_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Timestamp product_add_time = null;
		try {
			product_add_time = productVO.getProduct_add_time();
		} catch (Exception e) {
			product_add_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Timestamp product_discount_on_time = null;
		try {
			product_discount_on_time = productVO.getProduct_discount_on_time();
		} catch (Exception e) {
			product_discount_on_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Timestamp product_discount_off_time = null;
		try {
			product_discount_off_time = productVO.getProduct_discount_off_time();
		} catch (Exception e) {
			product_discount_off_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Timestamp product_last_edit_time = null;
		try {
			product_last_edit_time = productVO.getProduct_last_edit_time();
		} catch (Exception e) {
			product_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());
		}
	%>
		
		<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />

	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>
	<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=product_on_time%>',
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        $.datetimepicker.setLocale('zh');
        $('#f_date2').datetimepicker({
	       theme: '',             
	       timepicker:false,       
	       step: 1,               
	       format:'Y-m-d H:i:s',         
		   value: '<%=product_off_time%>',
        });
        $.datetimepicker.setLocale('zh');
        $('#f_date3').datetimepicker({
	       theme: '',             
	       timepicker:false,       
	       step: 1,               
	       format:'Y-m-d H:i:s',         
		   value: '<%=product_discount_on_time%>',
        });
        $.datetimepicker.setLocale('zh');
        $('#f_date4').datetimepicker({
	       theme: '',             
	       timepicker:false,       
	       step: 1,               
	       format:'Y-m-d H:i:s',         
		   value: '<%=product_discount_off_time%>',
        });
        $.datetimepicker.setLocale('zh');
        $('#f_date5').datetimepicker({
	       theme: '',             
	       timepicker:false,       
	       step: 1,               
	       format:'Y-m-d H:i:s',         
		   value: '<%=product_last_edit_time%>',
        });
        $.datetimepicker.setLocale('zh');
        $('#f_date6').datetimepicker({
	       theme: '',             
	       timepicker:false,       
	       step: 1,               
	       format:'Y-m-d H:i:s',         
		   value: '<%=product_add_time%>',});

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

</body>
</html>