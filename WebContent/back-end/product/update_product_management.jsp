<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商品資料修改 - update_product_management.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

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

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>商品資料修改 - update_product_management.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/product/select_page.jsp"><img src="<%=request.getContextPath()%>/images/pic.jpg" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="product.do" name="form1">
<table>
	<tr>
		<td>商品編號:</td>
		<td><%=productVO.getProduct_id() %></td>
	</tr>
	<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />
	<tr>
		<td>樂團編號:</td>
		<td>
			<select size="1" name="band_id">
	         <c:forEach var="productVO" items="${productSvc.all}"> 
	           <option value="${productVO.band_id}">${productVO.band_id}
	         </c:forEach>   
	       </select>
       </td>
	</tr>
	<tr>
		<td>商品分類:</td>
		<td><input type="number" name="product_type" min="0" max="1" size="45"
			 value="<%= (productVO==null)? "1" : productVO.getProduct_type()%>" /></td>
	</tr>
	<tr>
		<td>商品名稱:</td>
		<td><input type="TEXT" name="product_name" size="45"
			 value="<%= (productVO==null)? "Towel" : productVO.getProduct_name()%>" /></td>
	</tr>
	<tr>
		<td>商品簡介:</td>
		<td><input type="TEXT" name="product_intro" size="45"
			 value="<%= (productVO==null)? "樂團毛巾" : productVO.getProduct_intro()%>" /></td>
	</tr>
	<tr>
		<td>商品詳細說明:</td>
		<td><input type="TEXT" name="product_detail" size="45"
			 value="<%= (productVO==null)? "這是樂團毛巾" : productVO.getProduct_detail()%>" /></td>
	</tr>
	<tr>
		<td>商品單價:</td>
		<td><input type="TEXT" name="product_price" size="45"
			 value="<%= (productVO==null)? "300.0" : productVO.getProduct_price()%>" /></td>
	</tr>
	<tr>
		<td>商品庫存量:</td>
		<td><input type="number" min="0" name="product_stock" size="45"
			 value="<%= (productVO==null)? "8" : productVO.getProduct_stock()%>" /></td>
	</tr>
	<tr>
		<td>審核狀態:</td>
		<td><select name="product_check_status">
		<option value="1">已審核</option>
		<option value="0">待審核</option>
		</select></td>
	</tr>
	<tr>
		<td>上下架狀態:</td>
		<td><select name="product_status">
		<option value="1">上架</option>
		<option value="0">下架</option>
		</select></td>
	</tr>
	<tr>
		<td>預計上架時間:</td>
		<td><input name="product_on_time" id="f_date1" type="text"></td>
	</tr>
	<tr>
		<td>預計下架時間:</td>
		<td><input name="product_off_time" id="f_date2" type="text"></td>
	</tr>
	<tr>
		<td>提交時間:</td>
		<td><input name="product_add_time" id="f_date6" type="text"></td>
	</tr>
	<tr>
		<td>折扣:</td>
		<td><input type="number" name="product_discount" placeholder="1.0" step="0.01" min="0.0" max="1.0" size="45"
			 value="<%= (productVO==null)? "0.8" : productVO.getProduct_discount()%>" /></td>
	</tr>
	<tr>
		<td>折扣開始時間:</td>
		<td><input name="product_discount_on_time" id="f_date3" type="text"></td>
	</tr>
	<tr>
		<td>折扣結束時間:</td>
		<td><input name="product_discount_off_time" id="f_date4" type="text"></td>
	</tr>
	<tr>
		<td>最後修改時間:</td>
		<td><input name="product_last_edit_time" id="f_date5" type="text"></td>
	</tr>
	<tr>
		<td>最後修改者:</td>
		<td>
			<select size="1" name="product_last_editor">
	         <c:forEach var="productVO" items="${productSvc.all}"> 
	           <option value="${productVO.product_last_editor}">${productVO.product_last_editor}
	         </c:forEach>   
	       </select>
       </td>
    </tr>

</table>
<br>
<input type="hidden" name="action" value="update_Management">
<input type="hidden" name="product_id" value="<%= productVO.getProduct_id() %>">
<input type="submit" value="送出修改"></FORM>
</body>



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
<% 
  java.sql.Timestamp product_add_time = null;
  try {
	  product_add_time = productVO.getProduct_add_time();
   } catch (Exception e) {
	   product_add_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>

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
	   value: '<%=product_add_time%>',
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
</html>