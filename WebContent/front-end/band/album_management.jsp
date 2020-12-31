<%@page import="com.album.model.AlbumVO"%>
<%@page import="java.util.List"%>
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
// 	=============
	MemberVo memberVO = (MemberVo) session.getAttribute("memberVO");
    String band_id = memberVO.getBandId();
    pageContext.setAttribute("band_id", band_id);
	List <AlbumVO> albumVOList = albumSvc.getBandAlbums(band_id);
	pageContext.setAttribute("albumVOList", albumVOList);
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>album_management.jsp</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/band/album_management.css">

</head>

<body>
<%-- 	<%@ include file="/front-end/member/anotherMemberCenter.jsp" %> --%>
	<%@ include file="/front-end/member/member_center_top.file" %>
    <div class="container album_management_container">

        <div class="row search_table_title justify-content-center">
            <div class="title">專輯作品管理</div>
        </div>

        <!-- <form action="">
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
        </form> -->

        <div class="row album_control_btn_area col-12 justify-content-start">
            <div class="col-3">
                <!-- <button type="button" id="cancel_update_data_btn" class="btn btn-primary hide">取消修改</button>
                <button type="button" id="update_data_btn" class="btn btn-primary deactivated">修改</button> -->
                <button type="button" class="add_new_album_btn btn btn-primary" data-path="<%=request.getContextPath()%>"><i class="fas fa-plus"></i>專輯</button>
            </div>
            <div class="col-3"></div>
            <div class="col-3"></div>
            <!-- <div class="col-3">
                <button type="button" id="on_shelf" class="btn btn-primary">上架</button>
                <button type="button" id="off_shelf" class="btn btn-primary">下架</button>
            </div> -->
        </div>
        <div class="row album_control_btn_area col-12 justify-content-start">
            <div class="col-3"></div>
            <div class="col-3"></div>
            <div class="col-3"></div>
            <!-- <div class="col-3">
                <span>全選</span>
                <input type="checkbox" class="check_all" name="check_all">
            </div> -->
        </div>

        <div class="row col-12 album_area justify-content-center">

        <% int i_btn_id = 0; %>
        <c:forEach var="albumVO" items="${albumVOList}">

            <!-- for each album -->
            <div class="row col-12 my_album_card justify-content-center" data-album_id="${albumVO.album_id}" data-band_id="${band_id}" >
                <div class="row col-12 album_header justify-content-end">
                    <div class="arrow col-1">
                        <i class="fa-2x fas fa-caret-right my_folder_arrow"></i>
                        <i class="fa-2x fas fa-caret-down my_folder_arrow hide"></i>
                    </div>
                    <div class="album_photo col-2">
                        <label class="update_photo" for="${albumVO.album_id}">
                            <img class="album_photo_thumb" src="<%= request.getContextPath() %>/album/album.do?action=getAlbumPhoto&album_id=${albumVO.album_id}">
                            <!-- <img class="preview hide" src="<%= request.getContextPath() %>/images/choose.jfif" alt=""> -->
                            <img class="preview hide" src="" alt="">
                            <!-- <div class="choose_pic hide">請選擇圖片</div> -->
                            <input type="file" id="${albumVO.album_id}" class="album_photo_file hide" accept="image/*">
                        </label>
                    </div>
                    <div class="album_name col-4">
                        <span class="name_text">${albumVO.album_name}</span>
                        <input type="text" class="name_text hide" value="${albumVO.album_name}">

                    </div>
                    <!-- <div class="album_status col-2">${albumVO.album_status == 0 ? "下架中":"上架中"}<input type="checkbox" class="check_one_album" name="check_album"></div> -->
                    <div class="btn_box col-5">
                                    <button type="button" class="update_album_btn btn btn-primary"><i class="fas fa-edit"></i></button>
                                    <button type="button" class="cancel_update_album_btn btn btn-primary hide"><i class="fas fa-ban"></i></button>
                                    <button type="button" class="add_piece_btn btn btn-primary"><i class="fas fa-plus"></i>歌曲</button>
                                    <button type="button" class="delete_album_btn btn btn-primary hide"><i class="fas fa-trash-alt"></i></button>
                                    <button type="button" class="confirm_update_album_btn btn btn-primary hide"><i class="fas fa-check"></i></button>
                    </div>
                </div>
                    <!-- div.arrow.onclick  open -->
                <div id="my_album_body" class="row col-10 my_album_body justify-content-end hide">

                    <div class="row col-12 my_album_body_intro justify-content-around">
                        <div class="col-3">專輯介紹</div>
                        <div class="col-9">
                            <span class="name_text">${albumVO.album_intro}</span>
                            <!-- <input type="text" class="name_text hide" value="${albumVO.album_intro}"> -->
                            <textarea class="name_text hide" cols="30" rows="5">${albumVO.album_intro}</textarea>
                        </div>

                        <hr size="1px" width="100%">
                    </div>

                    <c:forEach var="piecesVO" items="${piecesSvc.getAllByAlbumId(albumVO.album_id)}">
                    <!-- for each piece -->
                    <div class="row col-12 my_album_body_item justify-content-center" data-piece_id="${piecesVO.piece_id}" data-album_id="${albumVO.album_id}">
                        <div class="piece_name col-5">
                            <span class="name_text">${piecesVO.piece_name}</span>
                            <input type="text" class="name_text hide" value="${piecesVO.piece_name}">
                        </div>                            
                        <!-- <div class="piece_status col-4">${piecesVO.piece_status == 0 ? "下架中":"上架中"}<input type="checkbox" class="check_one_piece" name="check_piece"></div> -->
                        <div class="col-7 btn_box">
                            <button type="button" class="update_piece_btn btn btn-primary"><i class="fas fa-edit"></i></button>
                            <button type="button" class="delete_piece_btn btn btn-primary hide"><i class="fas fa-trash-alt"></i></button>
                            <button type="button" class="cancel_update_piece_btn btn btn-primary hide"><i class="fas fa-ban"></i></button>
                            <button type="button" class="confirm_update_piece_btn btn btn-primary hide"><i class="fas fa-check"></i></button>
                        </div>
                        <div class="col-3"><input style="font-size: 12px; margin-left: 0px;" type="file" name="piece" class="upload_audio hide" accept="audio/*"></div>
                        <div class="col-9"></div>
                        <hr size="1px" width="100%">
                    </div>
                    <!-- for each piece -->                        
                    </c:forEach>
                </div>
            </div>
            <!-- for each album -->
                        
            </c:forEach>    
            
        </div>

    </div>
	<%@ include file="/front-end/member/member_center_bottom.file" %>

    <!-- script from vendor -->
    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
    <!-- <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script> -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- script from mine -->
    <script src="<%=request.getContextPath()%>/js/album/album_management.js"></script>

</body>

</html>