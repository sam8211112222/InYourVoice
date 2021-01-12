<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
// 	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>header</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css">


<style>


div.logo  img{
    width: 100%;
    height: 100%;
}

header{
    display: flex;
    justify-content: space-between;
}
.navbar{
    width: calc(100% - 40px);
}
#dropdownMenuButton{
    width: 40px;
    height: 22px;
    margin-right: 10px;
    border-radius: 50%;
    position: relative;
    cursor: pointer;
}
.fa-user:before{
    font-size: 25px;
}

#dropdownMenuButton img, #dropdownMenuButton .fa-user{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
#dropdownMenuButton img{
    width: 100%;
}


.userAvatar{
    width: 80px;
    height: 80px;
    border: 1px solid #000;
    margin-left: 40px;
    border-radius: 50%;
    overflow: hidden;
    position: relative;
    cursor: pointer;
}

.userAvatar img{
    width: 100%;
}

.dropdown-menu.show {
    display: block;
    text-align: center;
    top: 45px !important;
    left: -116px !important;
}

.logo{
    width: 250px;
    margin-left: 40px;
    position: relative;
    cursor: pointer;
    margin: auto;
    display: flex;
    align-items: center;
}

.logo a{
    margin: 0;
    padding: 0;
}

.fa-bell:before {
    content: "\f0f3";
    font-size: 25px;
}

.dropdown, .dropleft, .dropright, .dropup {
    display: flex;
    align-items: center;
}
.notice{
    position: relative;
}

.notice-number{
    width: 20px;
    height: 20px;
    /* border: 1px solid #000; */
    border-radius: 50%;
    position: absolute;
    top: -12px;
    left: 12px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 6px;
    color: #fff;
    background: red;
}

.user{
    width: 80px;
    height: 80px;
    border: 1px solid #000;
    margin-left: 40px;
    border-radius: 50%;
    overflow: hidden;
    position: relative;
    cursor: pointer;
}

.user img{
    width: 100%;
}

</style>

</head>
<body>


	<header class="bg-light">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="logo">
				<a class="navbar-brand" href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/images/logo.png"></a>
			</div>
			<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<!-- <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li> -->
					<li class="nav-item"><a class="nav-link" href="#">活動資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/band/band.do?action=listAllBand">樂團資訊</a></li>
					<li class="nav-item"><a class="nav-link" href="#">周邊商品</a></li>

					<!-- <li class="nav-item">
                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                    </li> -->
					<form action="<%=request.getContextPath()%>/album/album.do" method="get" class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search" placeholder="搜尋" aria-label="Search" name="search" id="search"> <input type="hidden" name="action" value="searchName">
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜尋</button>
					</form>
				</ul>
			</div>

<!-- 		</nav> -->
		
		<!-- 小鈴鐺   -->
        <div class="dropdown dropleft">
             
            <div id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <div class="notice">
                    <span class="notice-number">1</span>
                <i class="fas fa-bell" style="color: dimgrey;"></i>   
                </div>
            </div>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">通知</a>
                <a class="dropdown-item" href="#">通知</a>
                <a class="dropdown-item" href="#">通知</a>
                <a class="dropdown-item" href="#">通知</a>
                <a class="dropdown-item" href="#">通知</a>
            </div>
        </div> 
		
		
		<div class="dropdown dropleft">
			<div id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				<!-- <img src="./images/logo.jpg"> -->
				<i class="far fa-user"></i>
			</div>
			<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				<c:if test="${memberVo.memberId==null}">
					<div class="userAvatar">
						<!-- <img src="./images/logo.jpg"> -->
						<i class="fas fa-meh" style="font-size: 79px;color: #888;"></i>
					</div>
				</c:if>
				<c:if test="${memberVo.memberId!=null}">
					<div class="userAvatar">
					
					</div>	
				</c:if>
				<div style= "color: #2cbcf4">${memberVo.memberName}</div>
				<a class="dropdown-item" href="#">會員中心</a> <a class="dropdown-item" href="#">通知中心</a> <a class="dropdown-item" href="#">購物車</a> <a class="dropdown-item" href="#">我的最愛</a> 
				<c:if test="${memberVo.memberId==null}">
					<a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/member/Login.jsp">登入</a>
				</c:if>
				<c:if test="${memberVo.memberId!=null}">
					<a id="logoutBtn" class="dropdown-item" href="#">登出</a>
				</c:if>
			</div>
		</div>
		</nav>
	</header>

	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
	
	<script>
	//登出
	$("#logoutBtn").click(function() {
		let obj = new FormData();
		obj.append("action", "logout");
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/Login",
			contentType : false,
			processData : false,
			cache : false,
			data : obj,

			success : function(result) {
				location.reload();
			},
			error : function(err) {
				alert("系統錯誤");
			}
		});
	})
	</script>
</body>
</html>