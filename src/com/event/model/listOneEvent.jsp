<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.event.model.*"%>
<%-- <%@ page import="com.dept.model.*"%> --%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%EventVO eventVO = (EventVO) request.getAttribute("eventVO");%>

<%-- 取出 對應的DeptVO物件--%>
<%-- <% 
//   DeptService deptSvc = new DeptService();
//   DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
%> --%>

<html>
<head>
<title>活動資料 - listOneEvent.jsp</title>

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
	width: 900px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  img{
  width: 100px;
  height: 100px;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>活動資料 - ListOneEvent.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/events/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/events/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>活動編號</th>
		<th>樂團編號</th>
		<th>活動類型</th>
		<th>活動排序</th>
		<th>活動標題</th>
		<th>活動詳情</th>
		<th>活動海報</th>
		<th>活動區域</th>
		<th>活動場地</th>
		<th>活動縣市</th>
		<th>活動縣市分區</th>
		<th>活動地址</th>
		<th>活動開始時間</th>
		<th>活動上架時間</th>
		<th>最後修改時間</th>
		<th>最後修改者</th>
		<th>活動狀態</th>
		<th>座位圖</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<tr>
		<td><%=eventVO.getEvent_id()%></td>
		<td><%=eventVO.getBand_id()%></td>
		<td><%=eventVO.getEvent_type()%></td>
		<td><%=eventVO.getEvent_sort()%></td>
		<td><%=eventVO.getEvent_title()%></td>
		<td><%=eventVO.getEvent_detail()%></td>
		<td><img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></td>
		<td><%=eventVO.getEvent_area()%></td>
		<td><%=eventVO.getEvent_place()%></td>
		<td><%=eventVO.getEvent_city()%></td>
		<td><%=eventVO.getEvent_cityarea()%></td>
		<td><%=eventVO.getEvent_address()%></td>
		<td><%=eventVO.getEvent_start_time()%></td>
		<td><%=eventVO.getEvent_on_time()%></td>
		<td><%=eventVO.getEvent_last_edit_time()%></td>
		<td><%=eventVO.getEvent_last_editor()%></td>
		<td><%=eventVO.getEvent_status()%></td>
		<td><img src="<%=request.getContextPath()%>/EventPicController?action=getEventSeat&event_id=${eventVO.event_id}"></td>
		<td>
		  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" style="margin-bottom: 0px;">
		     <input type="submit" value="修改">
		     <input type="hidden" name="event_id"  value="${eventVO.event_id}">
		     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
		</td>
		<td>
		  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" style="margin-bottom: 0px;">
		     <input type="submit" value="刪除">
		     <input type="hidden" name="event_id"  value="${eventVO.event_id}">
		     <input type="hidden" name="action" value="delete"></FORM>
		</td>
	</tr>
</table>

</body>
</html>