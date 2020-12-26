<%@page import="com.member.model.MemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="albumSvc" scope="page" class="com.album.model.AlbumService"></jsp:useBean>
<jsp:useBean id="piecesSvc" scope="page" class="com.pieces.model.PiecesService"></jsp:useBean>

<!-- 樂團登入後台後 取得memberVO 中的 band_id 出來用 -->
<%
// 	測試
	MemberVo memberVoTest = new MemberVo();
	memberVoTest.setBandId("BAND00200");
	session.setAttribute("memberVO", memberVoTest);
// 	測試
	MemberVo memberVO = (MemberVo) session.getAttribute("memberVO");
	String band_id = memberVO.getBandId();
	pageContext.setAttribute("band_id", band_id);
%>


    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>album_management.jsp</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/band/album_management.css">
    </head>

    <body>

        <div class="container album_management_container">

            <div class="row search_table_title justify-content-center">
                <div>專輯作品管理</div>
            </div>

            <form action="">
                <div class="row search_table justify-content-center">
                    <div class="col-2">專輯編號:</div>
                    <div class="col-3"><input class="search_box" type="text" name="album_id"></div>
                    <div class="col-2">專輯名稱:</div>
                    <div class="col-3"><input class="search_box" type="text" name="album_name"></div>
                </div>
                <div class="row search_table justify-content-center">
                    <div class="col-2">作品編號:</div>
                    <div class="col-3"><input class="search_box" type="text" name="piece_id"></div>
                    <div class="col-2">作品名稱:</div>
                    <div class="col-3"><input class="search_box" type="text" name="piece_name"></div>
                </div>
                <div class="row search_table justify-content-center">
                    <div class="col-2"><button type="reset" class="btn btn-secondary">重填</button></div>
                    <div class="col-2"><button type="button" class="btn btn-primary">搜尋</button></div>
                </div>
            </form>

            <div class="row album_control_btn_area col-10 justify-content-start">
                <div class="col-3"></div>
                <div class="col-3"></div>
                <div class="col-3"></div>
                <div class="col-3">
                    <button type="button" id="on_shelf" class="btn btn-primary">上架</button>
                    <button type="button" id="off_shelf" class="btn btn-primary">下架</button>
                </div>
            </div>
            <div class="row album_control_btn_area col-10 justify-content-start">
                <div class="col-3"></div>
                <div class="col-3"></div>
                <div class="col-3"></div>
                <div class="col-3">
                    <span>狀態</span>
                    <input type="checkbox" class="check_all" name="check_all">
                </div>
            </div>

            <div class="row col-12 album_area justify-content-center">


			<c:forEach var="albumVO" items="${albumSvc.getBandAlbums(band_id)}">

                <!-- for each album -->
                <div class="row col-10 my_album_card justify-content-center">
                    <div class="row col-12 album_header justify-content-end">
                        <div class="arrow col-1">
                            <i class="fa-2x fas fa-caret-right my_folder_arrow"></i>
                            <i class="fa-2x fas fa-caret-down my_folder_arrow hide"></i>
                        </div>
                        <div class="album_photo col-2"><img class="album_photo_thumb" src="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}" alt=""></div>
                        <div class="album_name col-6">${albumVO.album_name}</div>
                        <div class="album_status col-2">${albumVO.album_status == 0 ? "下架中":"上架中"}</div>
                        <div class="check_box col-1"><input type="checkbox" class="check_one_album" name="check_album"></div>
                    </div>
                        <!-- div.arrow.onclick  open -->
                    <div id="my_album_body" class="row col-10 my_album_body justify-content-end hide">
                    	<% int i = 1; %>
                    	<c:forEach var="piecesVO" items="${piecesSvc.getAllByAlbumId(albumVO.album_id)}">
                        <!-- for each piece -->
                        <hr size="1px" width="100%">
                        <div class="row col-12 my_album_body_item justify-content-center">
                            <div class="piece_name col-1"><%= i++  %></div>
                            <div class="piece_name col-7">${piecesVO.piece_name}</div>
                            <div class="piece_status col-2">${piecesVO.piece_status}</div>
                            <div class="check_box col-2"><input type="checkbox" class="check_one_piece" name="check_piece"></div>
                        </div>
                        <!-- for each piece -->                        
                        </c:forEach>
                        <hr size="1px" width="100%">
                    </div>
                </div>
                <!-- for each album -->
                    
                            
            	</c:forEach>    
                
            </div>

        </div>


        <!-- script from vendor -->
        <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
        <!-- script from mine -->
        <script src="<%=request.getContextPath()%>/js/album/album_management.js"></script>

    </body>

    </html>