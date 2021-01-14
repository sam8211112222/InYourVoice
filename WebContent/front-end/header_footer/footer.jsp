<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>footer</title>
</head>


<style>

footer.footer {
	background-color: #270d38;
	color: white;
	width: 100%;
	box-sizing: border-box;
	text-align: left;
	padding: 50px 0px;
}

.navfoot {
	width: 90%;
	display: flex;
	flex-wrap: wrap;
	justify-content: space-around;
}


</style>

<body>

	<!-- footer -->
	<div>
		<footer class="footer">
			<nav class="navfoot">
				<div>
					<h1 style="color: white ; background-color: unset;">關於</h1>
					<a href="#"><li style="color: white">關於我們</li></a>
				</div>
				<div>
					<h1 style="color: white ; background-color: unset;">其他</h1>
					<a href="<%=request.getContextPath()%>/front-end/band/protect/bandSignup.jsp"><li style="color: white">成為樂團</li></a>
				</div>
			</nav>
		</footer>
	</div>
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
</body>
</html>