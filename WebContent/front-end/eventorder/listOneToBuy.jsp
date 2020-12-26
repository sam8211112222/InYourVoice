<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.ticket.model.*"%>
<%@ page import="java.util.ArrayList"%>
<%	EventService eventSvc = new EventService();
	TicketService ticketSvc = new TicketService();
	EventVO eventVO = eventSvc.getOneEvent("EVENT00000");
	ArrayList<TicketVO> ticketList = (ArrayList<TicketVO>)ticketSvc.getTicketByEventId("EVENT00000");
	request.setAttribute("eventVO",eventVO);
%>


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

<div>
<h1><%=${eventVO.event_name} %></h1>
</div>

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