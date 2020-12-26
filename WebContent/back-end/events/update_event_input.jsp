<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.ticket.model.*"%>
<%@ page import="java.util.ArrayList"%>

<%

  EventVO eventVO = (EventVO) request.getAttribute("eventVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
  ArrayList<TicketVO> ticketVoList = (ArrayList<TicketVO>) request.getAttribute("ticketVoList");

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>活動資料修改 - update_event_input.jsp</title>


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
  img{
  width: 100px;
  height: 100px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>

		 <h3>活動資料修改 - update_emp_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/events/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/events/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>活動標題:</td>
		<td><input type="TEXT" name="event_title" size="45" 
			 value="<%= (eventVO==null)? "請輸入活動標題" : eventVO.getEvent_title()%>" /></td>
	</tr>
	<tr>
		<td>樂團編號:</td>
		<td><input type="TEXT" name="band_id" size="45" 
			 value="<%= (eventVO==null)? "請輸入活動標題" : eventVO.getBand_id()%>" /></td>
	</tr>
	<tr>
		<td>活動類型:</td>
		<td><select name="event_type" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 0)?"selected":""%>>一般活動</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 1)?"selected":""%>>放鬆好去處</option>
		<option value="2"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 2)?"selected":""%>>特色活動</option>
		<option value="3"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 3)?"selected":""%>>主打活動</option>
		<option value="4"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 4)?"selected":""%>>最新消息</option>
		</select></td>
	</tr>
	<tr>
		<td>活動開始時間:</td>
		<td><input name="event_start_time" id="f_date1" type="text"></td>
	</tr>
	<tr>
		<td>活動詳情:</td>
		<td><input type="TEXT" name="event_detail"  
			 value="<%= (eventVO==null)? "請輸入活動標題" : eventVO.getEvent_detail()%>" /></td>
	</tr>
	<tr>
		<td>活動排序:</td>
		<td><input type="TEXT" name="event_sort" size="46"
			 value="<%= (eventVO==null)? "請輸入0-99的數字" : eventVO.getEvent_sort()%>" /></td>
	</tr>
	<tr>
		<td>活動海報:</td>
		<td><input type="file" name="event_poster" id="event_poster" ></td>
		<td>預覽圖</td>

     	<td><img id="event_poster_img" src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
	</tr>
	 
	<tr>
		<td>活動場地:</td>
		<td><input type="TEXT" name="event_place" size="45"
			 value="<%= (eventVO==null)? "請輸入活動場地" : eventVO.getEvent_place()%>" /></td>
	</tr>
	<tr>
		<td>活動區域:</td>
		<td><select name="event_area" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 0)?"selected":""%>>北部</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 1)?"selected":""%>>中部</option>
		<option value="2"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 2)?"selected":""%>>南部</option>
		<option value="3"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 3)?"selected":""%>>東部</option>
		<option value="4"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 4)?"selected":""%>>離島</option>
		</select></td>
	</tr>
	<tr>
		<td>活動縣市:</td>
		<td><input type="TEXT" name="event_city" size="45"
			 value="<%= (eventVO==null)? "請輸入活動縣市" : eventVO.getEvent_city()%>" /></td>
	</tr>
	<tr>
		<td>活動縣市分區:</td>
		<td><input type="TEXT" name="event_cityarea" size="45"
			 value="<%= (eventVO==null)? "請輸入縣市區域" : eventVO.getEvent_cityarea()%>" /></td>
	</tr>
	<tr>
		<td>活動地址:</td>
		<td><input type="TEXT" name="event_address" size="45"
			 value="<%= (eventVO==null)? "請輸入活動地址" : eventVO.getEvent_address()%>" /></td>
	</tr>
	<tr>
		<td>活動上架時間:</td>
		<td><input name="event_on_time" id="f_date2" type="text"></td>
	</tr>
	<tr>
		<td>活動狀態:</td>
		<td><select name="event_status" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_status()== 0)?"selected":""%>>下架</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_status()== 1)?"selected":""%>>上架</option>
		</select></td>
	</tr>
	<tr>
		<td>座位圖:</td>
		<td><input type="file" name="event_seat" size="45" id="event_seat"></td>
		<td>預覽圖</td>

     	<td><img id="event_seat_img" src="<%=request.getContextPath()%>/EventPicController?action=getEventSeat&event_id=${eventVO.event_id}"></td>
<%-- 			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_seat()</td> --%>
	</tr>
	

<%-- 	<jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" /> --%>
<!-- 	<tr> -->

<!-- 		<td>部門:<font color=red><b>*</b></font></td> -->
<!-- 		<td><select size="1" name="deptno"> -->
<%-- 			<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%-- 				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname} --%>
<%-- 			</c:forEach> --%>
<!-- 		</select></td> -->
<!-- 	</tr> -->

</table>
<div id="ticket_block">
			<c:if test="${not empty ticketVoList}">
			<c:forEach var="ticketVO" items="${ticketVoList}">
			<div class="ticket"><span>請輸入票種名稱:<input type="text" name="ticket_name" value="${ticketVO.ticket_name}"></span>
            <span>票種金額:<input type="text" name="ticket_price" value="${ticketVO.ticket_price}"></span>
            <span>票種總張數:<input type="text" name="ticket_amount" value="${ticketVO.ticket_amount}"></span>
            <span>票種排序:<input type="text" name="ticket_sort" value="${ticketVO.ticket_sort}"></span>
            <span>開始販售時間:<input type="text" name="ticket_onsale_time" class="ticket_onsale_time" value="${ticketVO.ticket_onsale_time}"></span>
            <span>結束販售時間:<input type="text" name="ticket_endsale_time" class="ticket_endsale_time" value="${ticketVO.ticket_endsale_time}"></span>
            <span>票券販售狀態<select name="ticket_status" size="1">
			<option value="0" ${ticketVO.ticket_status == 0 ? "selected" : ""}>下架</option>
			<option value="1" ${ticketVO.ticket_status == 1 ? "selected" : ""}>上架</option>
			</select></span>
			<input type="hidden" name="ticket_id" class="ticket_id" value="${ticketVO.ticket_id}">
            <button type="button" class="delete_exsit">刪除</button>

            </div>
			</c:forEach>
			</c:if>
</div>
<br>
<input type="hidden" name="event_last_editor" value="members00000">
<input type="hidden" name="event_id" value="${eventVO.event_id}">
<input type="hidden" name="action" value="update">
<input type="submit" value="送出修改" id="submit"></FORM>
<br><button type ="button" id="addTicket">新增票種</button>
</body>




<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->


<% 
  java.sql.Timestamp event_start_time = null;
  java.sql.Timestamp event_on_time = null;
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
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/events/js/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.full.js"></script>

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
        $("#event_poster").on("change",function(e){
             let reader = new FileReader();
             console.log(this.files)
             reader.readAsDataURL(this.files[0]);
             reader.addEventListener("load",function(e){
                 $("#event_poster_img").attr("src",reader.result);
             })
         })
         $("#event_seat").on("change",function(e){
             let reader = new FileReader();
             console.log(this.files)
             reader.readAsDataURL(this.files[0]);
             reader.addEventListener("load",function(e){
                 $("#event_seat_img").attr("src",reader.result);
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
            
	    })
	
		$("#ticket_block").on("click", "button.delete", function (e) {
			
            $(this).closest("div.ticket").remove();
    })


    function addTicket() {
            $("#ticket_block").prepend(`<div class="ticket"><span>請輸入票種名稱:<input type="text" name="ticket_name"></span>
            <span>票種金額:<input type="text" name="ticket_price"></span>
            <span>票種總張數:<input type="text" name="ticket_amount"></span>
            <span>票種排序:<input type="text" name="ticket_sort"></span>
            <span>開始販售時間:<input type="text" name="ticket_onsale_time" class="ticket_onsale_time"></span>
            <span>結束販售時間:<input type="text" name="ticket_endsale_time" class="ticket_endsale_time"></span>
            <span>票券販售狀態<select name="ticket_status" size="1">
			<option value="0" "selected">下架</option>
			<option value="1">上架</option>
			</select></span>
			<input type="hidden" name="ticket_id" value="">
            <button type="button" class="delete">刪除</button>
            </div>`);
    }


		$("#ticket_block").on("click", "button.delete_exsit", function (e) {
			
			let that = this;
            console.log(that);
            let ticket_id = $(this).siblings("input.ticket_id").val();
            console.log(ticket_id);
            $.ajax({
                url: "<%=request.getContextPath()%>/TicketController",
                type: "POST",
                data: {
                    "ticket_id": ticket_id
                },
                dataType: "json",
                success: function (data_obj) {
                    if (data_obj.msg == "success") {
                        $(that).closest("div.ticket").remove();
                    }
                }
            })
        })
        

        
        
         
</script>
</html>