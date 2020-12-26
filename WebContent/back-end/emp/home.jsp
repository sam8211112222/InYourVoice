<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>TEA102G6 Emp: Home</title>
<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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
</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>TEA102G6 Emp: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for TEA102G6 Emp: Home</p>

<h3>��Ƭd��:</h3>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp'>List</a> all Emps.  <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmpServlet" >
        <b>��J���u�s�� (�pEMP00000):</b>
        <input type="text" name="emp_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>
  <jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EmpServlet" >
       <b>��ܭ��u�s��:</b>
       <select size="1" name="emp_id">
         <c:forEach var="empVO" items="${empSvc.all}" > 
          <option value="${empVO.emp_id}">${empVO.emp_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li> 
</ul>

<h3>���u�޲z</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/emp/addEmp.jsp'>Add</a> a new Emp.</li>
</ul>
</body>
</html>