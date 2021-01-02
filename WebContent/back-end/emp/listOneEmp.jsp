<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%EmpVO empVO = (EmpVO) request.getAttribute("empVO");%>

<html>
<head>
<title>listOneEmp.jsp</title>

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

</style>

<style>
  table {
	width: 800px;
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
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneEmp.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/emp/home.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>員工編號</th>
		<th>員工密碼</th>
		<th>加入時間</th>
		<th>信箱</th>
		<th>手機號碼</th>
		<th>狀態</th>
		<th>認證</th>
		<th>最後編輯時間</th>
		<th>編輯</th>
	</tr>
	<tr>
		<td><%=empVO.getEmp_id()%></td>
		<td><%=empVO.getEmp_password()%></td>
		<td><%=empVO.getEmp_add_time()%></td>
		<td><%=empVO.getEmp_mail()%></td>
		<td><%=empVO.getEmp_phone()%></td>
		<td><%=empVO.getEmp_status()%></td>
		<td><%=empVO.getEmp_auth()%></td>
		<td><%=empVO.getEmp_last_edit_time()%></td>
		<td><%=empVO.getEmp_last_editor()%></td>
	</tr>
</table>

</body>
</html>