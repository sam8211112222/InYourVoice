<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.favorites.model.*"%>
<%@ page import="com.pieces.model.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.google.gson.Gson"%>
<%
	Gson gson = new Gson();
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	FavoritesService favSvc = new FavoritesService();
	PiecesService piecesSvc = new PiecesService();
	List<FavoritesVO> listFav = favSvc.getMemberFav(memberVo.getMemberId());
	session.setAttribute("list", listFav);
	List<String> playList = new ArrayList<String>();
	List<String> nameList = new ArrayList<String>();
	for(FavoritesVO li :listFav){
		if(li.getFavorite_type()==3){
			playList.add(li.getFavorite_id());
			nameList.add(piecesSvc.getOnePiece(li.getFavorite_id()).getPiece_name());
		}
	}
	pageContext.setAttribute("playList", gson.toJson(playList));
	pageContext.setAttribute("nameList", gson.toJson(nameList));
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link href="<%=request.getContextPath()%>/css/member/memberCenter.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.css">
<style>
.wrap {
	width: 80%;
	max-width: 1200px;
	margin: 15px auto 0 auto;
}

#myTab {
	width: 100%;
	display: flex;
	justify-content: space-around;
	border-bottom: none;
}

#myTab .nav-item {
	width: 20%;
	text-align: center;
}

header {
	display: flex;
	justify-content: space-between;
}

.navbar {
	width: calc(100% - 40px);
}

.card {
	width: 150px;
	height: 200px;
	overflow: hidden;
}

.card img {
	width: 148px;
	height: 148px;
}

.btn {
	width: 80px;
	margin: 0 auto;
	margin-top: 5px;
}
</style>
<body>
	<%@ include file="/front-end/header_footer/header.jsp"%>
	<%@ include file="/css/member/member_center_top.file"%>

	<h2 class="mt-5 mb-5">我的最愛</h2>
	<div class="wrap">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation"><a
				class="nav-link active" id="home-tab" data-toggle="tab" href="#home"
				role="tab" aria-controls="home" aria-selected="true">樂團</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="profile-tab" data-toggle="tab" href="#profile" role="tab"
				aria-controls="profile" aria-selected="false">作品</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="contact-tab" data-toggle="tab" href="#contact" role="tab"
				aria-controls="contact" aria-selected="false">商品</a></li>
			<li class="nav-item" role="presentation"><a class="nav-link"
				id="contact-tab" data-toggle="tab" href="#contact1" role="tab"
				aria-controls="contact" aria-selected="false">活動</a></li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="home" role="tabpanel"
				aria-labelledby="home-tab">
				<div class="row row-cols-1 row-cols-md-4">
					<c:forEach var="favVO" items="${list}">
						<c:if test="${favVO.favorite_type==1}">
							<div class="col mb-4">
								<div class="card">
									<a
										href="<%=request.getContextPath()%>/band/band.do?action=getBandMain&band_id=${favVO.favorite_id}"><img
										src="<%=request.getContextPath()%>/band/band.do?action=picDisplay&bandId=${favVO.favorite_id}"
										class="card-img-top" alt="..."></a>
									<button type="button" class="btn btn-danger">移除</button>
									<input type="hidden" value="${favVO.favorite_id}">
								</div>
							</div>

						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="profile" role="tabpanel"
				aria-labelledby="profile-tab">
				<div class="aplayer" id="player1"></div>
			</div>
			<div class="tab-pane fade" id="contact" role="tabpanel"
				aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">
					<c:forEach var="favVO" items="${list}">
						<c:if test="${favVO.favorite_type==2}">
							<div class="col mb-4">
								<div class="card">
									<a
										href="<%=request.getContextPath()%>/product/YUproductServlet?action=show_me_one&id=${favVO.favorite_id}"><img
										src="<%=request.getContextPath()%>/productphoto/productphoto.do?action=findFirst&productId=${favVO.favorite_id}"
										class="card-img-top" alt="..."></a>
									<button type="button" class="btn btn-danger">移除</button>
									<input type="hidden" value="${favVO.favorite_id}">
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="contact1" role="tabpanel"
				aria-labelledby="contact-tab">
				<div class="row row-cols-1 row-cols-md-3">
					<c:forEach var="favVO" items="${list}">
						<c:if test="${favVO.favorite_type==0}">
							<div class="col mb-4">
								<div class="card">
									<a
										href="<%= request.getContextPath() %>/event/EventServlet?action=getOne_For_Display&event_id=${favVO.favorite_id}"><img
										src="<%=request.getContextPath()%>/EventPicController?action=getEventPic&event_id=${favVO.favorite_id}"
										class="card-img-top" alt="..."></a>
									<button type="button" class="btn btn-danger">移除</button>
									<input type="hidden" value="${favVO.favorite_id}">
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->


	<%@ include file="/css/member/member_center_bottom.file"%>
	<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
	<script>
			ap1 = new APlayer({
				element: document.getElementById("player1"),
				narrow: false,
				autoplay: true,
				showlrc: false,
				fixed: false,
				// volume: 0,
				mutex: true,
				listFolded: false,
				listMaxHeight: 90,

			});
			
			let playList=${playList};
			let nameList=${nameList};
			console.log(nameList);
			
			$(function(){	        		 
				for(let i =0;i<playList.length;i++){
					console.log(playList[i]);
			        	ap1.list.add([{
		     			name: nameList[i],
		      			artist: " ",
		                url: "<%=request.getContextPath()%>/pieces/pieces.do?action=getPiece&piece_id="+playList[i],
		                cover: "<%=request.getContextPath()%>/album/album.do?action=getPhotoByPieces&piecesId="
									+ playList[i],
						} ]);
			}
		});
		$(".btn").click(function() {
			$(this).closest("div.col").remove()
			let target = $(this).closest("div").children("input")[0].value;
			let obj = {
				action : "deleteFav",
				favId : target,
			}
			$.ajax({
				type : "POST",
				url : "/TEA102G6/FavoritesServlet",
				data : obj,
				success : function(result) {
				},
				error : function(err) {

				}
			});
		});
	</script>

</body>
</html>