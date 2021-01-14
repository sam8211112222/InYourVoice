<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>會員中心-活動新增</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<style>
.container content {
	text-align: -webkit-center;
	border: 2px solid;
	border-radius: 50px;
}

.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

div.inputblock {
	margin-bottom: 8px;
}

div.poster {
	width: 400px;
	height: 210px;
	border: 2px #4e73df solid;
	box-sizing: border-box;
}

div.seat {
	width: 400px;
	height: 500px;
	border: 2px #4e73df solid;
	box-sizing: border-box;
}

#event_poster_img {
	width: 100%;
	max-height: 100%;
}

#event_seat_img {
	width: 100%;
	max-height: 100%;
}

div.ticket {
	margin-bottom: 8px;
}

.delete_exsit,.delete,.inputborder {
	border-width: 2px;
}

input.ticket_onsale_time {
	border-width: 2px;
}

input.ticket_endsale_time {
	border-width: 2px;
}
</style>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />

</head>
<body>
<jsp:include page="/front-end/header_footer/header.jsp" flush="true" />

	<%@ include file="/css/member/member_center_top.file"%>

	<div>
		<!-- Page Heading -->
		<h1 class="h3 mb-4 text-gray-800">活動新增</h1>

		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>

		<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/MemberCenterEventController"  enctype="multipart/form-data">
			<div>
				<div class="inputblock">
					<span>活動標題: <input class="inputborder" type="TEXT"
						name="event_title" size="45"
						value="<%=(eventVO == null) ? "請輸入活動標題" : eventVO.getEvent_title()%>" />
					</span>
				</div>
				<div>
					<span>活動開始時間: <input class="inputborder"
						name="event_start_time" id="f_date1" type="text" size="12"></span>
				</div>
				<div class="inputblock">
					<span>活動詳情:</span>
					<textarea class="inputborder" name="event_detail" rows="20"
						cols="80"><%=(eventVO == null) ? "請輸入活動詳情" : eventVO.getEvent_detail()%></textarea>
				</div>
				<div class="inputblock">
					<span>活動海報:<input type="file" name="event_poster"
						id="event_poster"></span>

					<div>預覽圖</div>
					<div class="poster">
						<img id="event_poster_img"
							<%=(eventVO == null)
					? ""
					: "src=\"" + request.getContextPath() + "/EventPicController?action=getEventPoster&event_id="
							+ eventVO.getEvent_id() + "\""%>>
					</div>
				</div>

				<div class="inputblock">
					<span>活動場地:</span> <span><input class="inputborder"
						type="TEXT" name="event_place" size="45"
						value="<%=(eventVO == null) ? "請輸入活動場地" : eventVO.getEvent_place()%>" /></span>
				</div>

				<div class="inputblock">
					<span>活動區域:</span> <span><select class="inputborder"
						name="event_area" size="1">
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
					</select></span>
				</div>
				活動縣市分區:<div id="twzipcode_ADV" class="inputblock"></div>
				<div class="inputblock">
					<span>活動地址:</span> <span><input class="inputborder"
						type="TEXT" name="event_address" size="45"
						value="<%=(eventVO == null) ? "請輸入活動地址" : eventVO.getEvent_address()%>" /></span>
				</div>
				<div class="inputblock">
					<span>活動上架時間:</span> <span><input class="inputborder"
						name="event_on_time" id="f_date2" type="text"></span>
				</div>
				<div class="inputblock">
					<span>座位圖:</span> <span><input class="inputborder"
						type="file" name="event_seat" size="45" id="event_seat"></span>
					<div>預覽圖</div>
					<div class="seat">
						<img id="event_seat_img"
							<%=(eventVO == null)
					? ""
					: "src=\"" + request.getContextPath() + "/EventPicController?action=getEventSeat&event_id="
							+ eventVO.getEvent_id() + "\""%>>
					</div>
				</div>
			</div>
			<div id="ticket_block">
				<c:if test="${not empty ticketVoList}">
					<c:forEach var="ticketVO" items="${ticketVoList}">
						<div class="ticket">
							<div class="inputblock">
								<span>請輸入票種名稱:<input class="inputborder" type="text"
									name="ticket_name" value="${ticketVO.ticket_name}"></span> <span>票種金額:<input
									class="inputborder" type="text" name="ticket_price"
									value="${ticketVO.ticket_price}" size="5"></span> <span>票種總張數:<input
									class="inputborder" type="text" name="ticket_amount"
									value="${ticketVO.ticket_amount}" size="5"></span> <span>票種排序:<input
									class="inputborder" type="text" name="ticket_sort"
									value="${ticketVO.ticket_sort}" size="3"></span>
							</div>
							<div class="inputblock">
								<span>開始販售時間:<input type="text" name="ticket_onsale_time"
									class="ticket_onsale_time"
									value="${ticketVO.ticket_onsale_time}" size="15"></span>
							</div>
							<div class="inputblock">
								<span>結束販售時間:<input type="text"
									name="ticket_endsale_time" class="ticket_endsale_time"
									value="${ticketVO.ticket_endsale_time}" size="15"></span>
							</div>
							<div class="inputblock">
								<span>票券販售狀態<select class="inputborder"
									name="ticket_status" size="1">
										<option value="0"
											${ticketVO.ticket_status == 0 ? "selected" : ""}>下架</option>
										<option value="1"
											${ticketVO.ticket_status == 1 ? "selected" : ""}>上架</option>

								</select></span>
							</div>
							<input type="hidden" name="ticket_id" class="ticket_id"
								value="${ticketVO.ticket_id}">
							<button type="button" class="delete_exsit">刪除</button>

						</div>
					</c:forEach>
				</c:if>
			</div>
				<input type="hidden" name="event_sort" value="${eventVO.event_sort}">
				<input type="hidden" name="event_id" value="${eventVO.event_id}"> 
				<input type="hidden" name="action" value="insert"> 
				<input type="submit" value="送出新增" name="submit" class="inputborder">
				<input type="submit" value="儲存草稿" name="submit" class="inputborder">
		</FORM>
		<br>
		<button type="button" id="addTicket" class="inputborder">新增票種</button>
	</div>


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-3.5.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>

	<%@ include file="/css/member/member_center_bottom.file"%>
	<%@ include file="/front-end/header_footer/footer.jsp"%>
	
</body>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.css" />
<script
	src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
<script
	src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/plugins/datetimepicker/jquery.datetimepicker.full.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>
<script
	src="<%=request.getContextPath()%>/plugins/ckeditor/ckeditor.js"></script>
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
    	
    	$("input.ticket_endsale_time").datetimepicker({
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
            $("#ticket_block").prepend(`<div class="ticket"><div class="inputblock"><span>請輸入票種名稱:<input type="text" class="inputborder" name="ticket_name"></span><span>票種金額:<input class="inputborder" type="text" name="ticket_price" size="5"></span>
                    <span>票種總張數:<input class="inputborder" type="text" name="ticket_amount" size="5"></span>
                    <span>票種排序:<input type="text" class="inputborder" name="ticket_sort" size="3"></span></div>
                    <div class="inputblock">開始販售時間:<input type="text" name="ticket_onsale_time" class="ticket_onsale_time" size="15"></div>
                    <div class="inputblock">結束販售時間:<input type="text" name="ticket_endsale_time" class="ticket_endsale_time" size="15"></div>
                    <div class="inputblock"><span>票券販售狀態<select class="inputborder" name="ticket_status" size="1">
        			<option value="0" "selected">下架</option>
        			<option value="1">上架</option>
        			</select></span></div>
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
        

        
        
         CKEDITOR.replace( 'event_detail', {});
		
		$("#twzipcode_ADV").twzipcode({
			zipcodeIntoDistrict: true, // 郵遞區號自動顯示在地區
			countyName: "event_city", // 自訂城市 select 標籤的 name 值
			districtName: "event_cityarea", // 自訂地區 select 標籤的 name 值
			css: ["inputborder","inputborder"]
			});
</script>
</html>