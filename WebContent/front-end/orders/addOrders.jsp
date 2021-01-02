<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.orders.model.*"%>
<%
	OrdersVO ordersVO = (OrdersVO) request.getAttribute("ordersVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增訂單</title>
</head>
<body>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="${pageContext.request.contextPath}/orders/ordersServlet"
		name="form1">
		<table>
			<tr>
				<td>買家編號:</td>
				<td><input name="member_id" type="text" value="${ordersVO.member_id} "></td>
			</tr>
			
			<jsp:useBean id="ordersSvc" scope="page" class="com.orders.model.OrdersService" />
			<tr>
				<td>訂單狀態:</td>
				<td><select name="order_status">
						<option value="0">處理中</option>
						<option value="1">已出貨</option>
						<option value="2">未結帳</option>
			
				</select></td>
			</tr>

			<tr>
				<td>下單時間:</td>
				<td><input name="order_place_time" id="place_time" type="text" value="${ordersVO.order_place_time}"></td>
			</tr>

			<tr>
				<td>買家名稱:</td>
				<td><input name="order_name" type="text" value="${ordersVO.order_name}"></td>
			</tr>

			<tr>
				<td>買家信箱:</td>
				<td><input name="order_mail" type="text" value="${ordersVO.order_mail}"></td>
			</tr>
			<tr>
				<td>買家電話:</td>
				<td><input name="order_phone" type="text" value="${ordersVO.order_phone}"></td>
			</tr>
			<tr>
				<td>運送時間:</td>
				<td><input name="order_delivery_time" id="delivery_time" type="text" value="${ordersVO.order_delivery_time}"></td>
			</tr>
			<tr>
				<td>收到時間:</td>
				<td><input name="order_received_time" id="received_time" type="text" value="${ordersVO.order_received_time}"></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="insert" > <input
			type="submit" value="送出新增">
	</FORM>
<a href="${pageContext.request.contextPath}/front_end/orders/select_page.jsp">回首頁</a>

</body>

<% 
  java.sql.Timestamp order_place_time = null;
  try {
	  order_place_time = ordersVO.getOrder_place_time();
   } catch (Exception e) {
	   order_place_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>

<% 
  java.sql.Timestamp order_delivery_time = null;
  try {
	  order_place_time = ordersVO.getOrder_place_time();
   } catch (Exception e) {
	   order_place_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>

<% 
  java.sql.Timestamp order_received_time = null;
  try {
	  order_place_time = ordersVO.getOrder_place_time();
   } catch (Exception e) {
	   order_place_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

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
        $("#place_time").datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=order_place_time%>', // value:   new Date(),
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
        
        $.datetimepicker.setLocale('zh');
        $("#delivery_time").datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
	       value: '<%=order_delivery_time%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        $.datetimepicker.setLocale('zh');
        $("#received_time").datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=order_received_time%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
</script>


</html>