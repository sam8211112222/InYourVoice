<%@page import="com.pieces.model.PiecesVO"%>
<%@ page import="java.util.List"%>
<%@ page import="com.album.model.AlbumVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
<jsp:useBean id="piecesSvc" scope="page" class="com.pieces.model.PiecesService"></jsp:useBean>
<%
	String band_id = request.getParameter("band_id");
// 	String album_id = request.getParameter("album_id");
	// 用band id 找出屬於這個樂團的所有專輯
	List<AlbumVO> albumVOList = albumSvc.getBandAlbumsReleasable(band_id);
	pageContext.setAttribute("albumVOList", albumVOList);
	// 列出所有專輯 並預設顯示第一個
	// 找出第一個專輯的所有歌曲 並列出歌曲
	String album_id = albumVOList.get(0).getAlbum_id();
	List<PiecesVO> piecesVOList = piecesSvc.getAllByAlbumId(album_id);
	pageContext.setAttribute("piecesVOList", piecesVOList);
	// 按專輯 替換專輯 跟歌曲
%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>band_album.jsp</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.css">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/band/band_album.css">
    </head>

    <body>

        <div class="container album_container">

            <div class="row wrapper justify-content-center">

                <div class="col-6">

                    <div class="row justify-content-center">
                        <!-- <div class="album_photo col-12 col-md-5"><img class="album_photo"
                                src="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVOList[0].album_id}" alt=""></div> -->

                        <div class="flip album_photo" id="album_photo_main" data-contextpath="<%= request.getContextPath() %>" data-album_id="${albumVO.album_id}" data-href="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" data-piece_href="<%= request.getContextPath() %>/pieces/pieces.do?action=getPieceList&album_id=${albumVO.album_id}" >
                            <div class="front"
                                style="background-image: url(<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVOList[0].album_id})">
                                <h1 class="text-shadow"></hi>
                            </div>
                            <!-- <div class="back">
                                <h2>${bandVO.band_name}</h2>
                                <p></p>
                            </div> -->
                        </div>
                        
                        <div class="album_intro .d-none .d-md-block col-md-5">${albumVOList[0].album_intro}</div>
                    </div>

                    <div class="row justify-content-center">
                        <div class="albums col-10">
                            <div class="row justify-content-between album_cards">
								<c:forEach var="albumVO" items="${albumVOList}">
								<c:if test="${albumVO.album_status == 1 }">

	                                <!-- <div class="album_card col-10 col-md-3" data-contextpath="<%= request.getContextPath() %>" data-album_id="${albumVO.album_id}" data-href="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" data-piece_href="<%= request.getContextPath() %>/pieces/pieces.do?action=getPieceList&album_id=${albumVO.album_id}">
	                                    <img class="album_photo"
	                                        src="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" alt="">
                                    </div> -->
                                    
                                    <div class="flip album_card" data-contextpath="<%= request.getContextPath() %>" data-album_id="${albumVO.album_id}" data-href="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" data-piece_href="<%= request.getContextPath() %>/pieces/pieces.do?action=getPieceList&album_id=${albumVO.album_id}" style="cursor: pointer;" >
                                        <div class="front"
                                            style="background-image: url(<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id})">
                                            <h1 class="text-shadow"></hi>
                                        </div>
                                        <!-- <div class="back">
                                            <h2>${bandVO.band_name}</h2>
                                            <p></p>
                                        </div> -->
                                    </div>
								</c:if>
								</c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-6 albums_wrapper">
                    <div class="album_pieces">
<!--                     	<a href=""><button>將此專輯的歌曲加入我的最愛</button></a> -->
                            <div id="player1" class="aplayer"></div>
                        <h2 style="margin-top: 20px;">歌曲清單</h2>
                        <ul class="pieces_list">
                            <% int i = 1; %>
                            <hr>
                            <c:forEach var="piecesVO" items="${piecesVOList}" >
                            <c:if test="${piecesVO.piece_status == 1 }">
	                            <li style="cursor:pointer;" class="piece_card" data-path="<%= request.getContextPath() %>" data-piece_id="${piecesVO.piece_id}" data-title="${piecesVO.piece_name}" data-author="" data-url="<%= request.getContextPath() %>/pieces/pieces.do?action=getPiece&piece_id=${piecesVO.piece_id}" data-pic="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${piecesVO.album_id}" data-lrc="" data-fav="">
	                                <div class="row piece_card justify-content-between">
 	                                    <div class="piece_order col-3 outer"><%= i++ %></div>
 	                                    <div class="piece_name col-3 outer">${piecesVO.piece_name}</div>
<!--  	                                    <div class="piece_audio col-6"></div> -->
<!--  	                                    <div class="piece_btn col-1"><i class="far fa-play-circle"></i></div>  -->
<!--  	                                    <div class="piece_btn col-1"><i class="fas fa-plus-square"></i></div>  -->  
                                         <div class="col-1"></div>
                                         <div class="piece_btn col-1"></div>
                                         <div class="col-1"></div>
                                         
 	                                </div>
 	                                <hr>
	                            </li>
<!-- 	                            <audio controls muted autoplay> -->
<%-- 								<source src="<%= request.getContextPath() %>/pieces/pieces.do?action=getPiece&piece_id=${piecesVO.piece_id}" type="audio/mpeg"> --%>
<!-- 								</audio> -->
							</c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

            </div>

        </div>




	
		

        <!-- script from vendors -->
        <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.js"></script>
<%--         <script src="<%=request.getContextPath()%>/plugins/aplayer/aplayer.js"></script> --%>
        <!-- script from self -->
        <script src="<%=request.getContextPath()%>/js/band/band_album.js"></script>

    </body>

    </html>