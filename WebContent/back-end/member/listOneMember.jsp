<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.member.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  MemberVo memberVo = (MemberVo) request.getAttribute("memberVo"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

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
	width: 600px;
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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneEmp.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>會員編號</th>
		<th>會員帳號</th>
		<th>會員密碼</th>
		<th>會員姓名</th>
		<th>會員暱稱</th>
		<th>性別</th>
		<th>電話</th>
		<th>地址</th>
		<th>生日</th>
		<th>會員權限</th>
		<th>藝人編號</th>
		<th>加入日期</th>	
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<tr>
			<td>${memberVo.memberId}</td>
			<td>${memberVo.memberAccount}</td>
			<td>${memberVo.memberPassword}</td>
			<td>${memberVo.memberName}</td>
			<td>${memberVo.memberNickname}</td>
			<td>${memberVo.memberGender}</td>
			<td>${memberVo.memberPhone}</td>
			<td>${memberVo.memberAddress}</td>
			<td>${memberVo.memberBirth}</td>
			<td>${memberVo.memberMsgAuth}</td>
			<td>${memberVo.bandId}</td>
			<td><fmt:formatDate value="${memberVo.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>

</body>
</html>