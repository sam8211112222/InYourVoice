<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="com.band.model.BandVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>
<!-- http://localhost:8081/TEA102G6/band/band.do?action=getBandMain&band_id=BAND00000 -->

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>樂團資訊 – 樂團詳情 - 音樂作品 band_album.jsp</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/band/index_band.css">
    </head>

    <body>

        <!-- start of include header -->

        <!-- end of include header -->


        <!-- start of upper content -->

        <div class="width-navbar-dark position-relative profile-page-header-wrapper mb-6">
            <div class="profile-cover-block image-upload width-absolute-btn">
                <div class="image-preview">
                    <div class="image-preview-item" id="cover_img">
                        <!-- <img class="band_banner" src="https://i.imgur.com/7HD0bkf.jpg" alt=""> -->
                        <!-- http://fakeimg.pl/440x300/282828/EAE0D0/?text=PJCHENder -->
<!-- 	                    <img class="band_banner" src="http://fakeimg.pl/1920x360?text=Band_Banner" alt=""> -->
						<%
							BandVO bandVO = (BandVO) request.getAttribute("bandVO");
						
							byte[] band_banner = bandVO.getBand_banner();
							String band_banner_base64 = null;
							if(band_banner.length!=0){
								band_banner_base64 = new String (Base64.encodeBase64(band_banner));
							}

							byte[] band_photo = bandVO.getBand_photo();
							String band_photo_base64 = null;
							if(band_photo.length!=0){
								band_photo_base64 = new String (Base64.encodeBase64(band_photo));
							}
							
						%>
	                    <img class="band_banner" src="data:image/gif;base64,<%= band_banner_base64 %>" alt="">
                        
                    </div>
                </div>

            </div>
            <div class="container profile-cover-block-up">
                <div class="row">
                    <div class="col-md-3">
                        <div class="image-upload width-absolute-btn">
                            <div class="image-preview img-square cover-block img-circle">
                                <div class="image-preview-item" id="avatar_img">
                                    <!-- <img class="band_photo" src="https://i.imgur.com/5IWAEdX.jpg" alt=""> -->
<!-- 	                                <img class="band_photo" src="http://fakeimg.pl/600x600?text=Band_Photo" alt=""> -->
	                                <img class="band_photo" src="data:image/gif;base64,<%= band_photo_base64 %>" alt="">
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-9">

                        <div class="row align-items-end mt-3">
                            <div class="col-md-8 col-lg-7 col-xl-8 text-center text-md-left">
                                <h1 class="text-break text-white">
                                    ${bandVO.band_name}

                                </h1>
                                <!-- <h3 class="text-white mb-0">
                                    <span class="mr-3">
                                        <span class="icon-music mr-1"></span>音樂人(身分)
                                    </span>

                                    <span class="mr-3">
                                        <span class="icon-map-marker mr-1"></span>臺北市(地區)
                                    </span>

                                </h3> -->
                            </div>

                            <div class="col-md-4 col-lg-5 col-xl-4 text-center text-md-right">
                                <!-- 追蹤按鈕的資訊 -->
                                <!-- <a data-ga-on="click" data-ga-event-category="follow"
                                    data-ga-event-action="珂拉琪 Collage (2429455)" data-ga-dimension-value="User profile"
                                    data-id="2429455" class="btn btn-primary btn-lg mt-3 ml-2 js-follow" href="#"> -->
                                <!-- <span class="glyphicon glyphicon-none"></span><span class="follow_text">A＋ 追蹤</span> -->
                                <!-- </a> -->
                            </div>
                        </div>

                        <div class="profile-page-header-info row">
                            <div class="col-lg-7">


                                <div class=" dynamic-height text-read text-break" data-max-height="90">
                                    <!-- 樂團簡介 -->
                                    <p class="max-three intro">

                                        ${bandVO.band_intro}

                                    </p>
                                    <!-- <a href="/collage7275/about/" class="text-red read-more d-none">
                                        <span class="show-more">…查看更多</span>
                                    </a> -->
                                </div>


                            </div>
                            <div class="col-lg-5 text-right">
                                <!-- 計算各種數據 -->
                                <ul class="list-inline justify-content-end">
                                    <!-- 音樂作品數量 -->
                                    <!-- <li class="list-inline-item">
                                        <h4 class="font-weight-normal">音樂</h4>
                                        <a href="/collage7275/songs/">
                                            <h4 class="mb-0 font-size-h1" id="countup-music"><div class="odometer animated fadeIn" id="odometer"></div></h4>
                                        </a>

                                    </li> -->
                                    <!-- 被追蹤數量 -->
                                    <!-- <li class="list-inline-item ml-3">
                                        <h4 class="font-weight-normal">粉絲</h4>
                                        <a href="/collage7275/followers/" class="modal_box" data-toggle="modal"
                                            data-target="#modal-subscription">
                                            <h4 class="mb-0 font-size-h1" id="countup-follower">0</h4>
                                        </a>
                                    </li>
                                    <li class="list-inline-item ml-3">
                                        <h4 class="font-weight-normal">追蹤中</h4>
                                        <a href="/collage7275/following/" class="modal_box" data-toggle="modal"
                                            data-target="#modal-subscription">
                                            <h4 class="mb-0 font-size-h1" id="countup-following">0</h4>
                                        </a>
                                    </li> -->
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sticky-anchor"></div>



        <div id="sticky" class="nav-profile stick">
            <div class="container">
                <nav class="transformer-tabs">
                    <!-- 頁籤按鈕 -->
                    <ul class="nav nav-pills mt-3 row">
                        <li class="nav-item pl-3"><a class="nav-link active js-scroll-0" id="album_btn" data-band_id="${bandVO.band_id}" data-href="<%=request.getContextPath()%>">音樂作品</a></li>
                        <li class="nav-item"><a class="nav-link" id="event_btn" data-band_id="${bandVO.band_id}" data-href="<%=request.getContextPath()%>">演出活動</a></li>
                        <li class="nav-item"><a class="nav-link" id="product_btn" data-band_id="${bandVO.band_id}" data-href="<%=request.getContextPath()%>">周邊商品</a></li>
                        <li class="nav-item"><a class="nav-link" id="intro_btn" data-band_id="${bandVO.band_id}" data-href="<%=request.getContextPath()%>">樂團介紹</a></li>
                        <li class="nav-item ml-auto col-md-4 col-lg-3 col-xl-2">

                            <a data-ga-on="click" data-ga-event-category="follow"
                                data-ga-event-action="珂拉琪 Collage (2429455)" data-ga-dimension-value="User profile"
                                data-id="2429455" class="btn btn-primary btn-lg btn-block js-follow" href="#">
                                <span class="follow_text">＋ 追蹤</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>





        <!-- end of upper content -->

        <!-- start of content -->
        <div id="album_content" class="included_content"></div>
        <div id="event_content" class="included_content content_hide"></div>
        <div id="product_content" class="included_content content_hide"></div>
        <div id="intro_content" class="included_content content_hide"></div>
<!--         <div class="container stick-container pb-4"> -->
<!--             <div class="border-top mb-6"></div> -->

<!--             <div class="row justify-content-center"> -->
<!--                 <div class="col-lg-10"> -->
<!--                     <div class="border-block text-read mb-4 p-4 p-7-lg"> -->
<!--                         <div class="maxw-720p ml-auto mr-auto"> -->
<!-- <%--                             <h2 class="mb-4">關於 ${bandVO.band_name}</h2> --%> -->
<!-- <%--                             <p>${bandVO.band_intro}</p> --%> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->

<!--             <div class="text-center pb-5 d-none d-lg-block"> -->
<!--                 <div class="adunit" data-adunit="sv_profile_footer_pc_970x250"></div> -->
<!--             </div> -->

<!--         </div> -->
        <!-- end of content -->


        <!-- start of include footer -->
        <footer class="footer bg-dark text-white">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-4 col-md-4 text-center">
                        <h3>關於</h3>
                        <ul class="list-unstyled">
                            <li><a href="">關於In Your Voice</a></li>
                            <li><a href="">會員服務條款</a></li>
                            <li><a href="">著作權保護措施</a></li>
                            <li><a href="">隱私權保護政策</a></li>
                        </ul>
                    </div>
<!--                     <div class="col-4 col-md"> -->
<!--                         <h3>商務</h3> -->
<!--                         <ul class="list-unstyled"> -->
<!--                             <li><a href="/service/marketing/">行銷業務合作</a></li> -->
<!--                             <li><a href="/service/on_air/">合作媒體</a></li> -->
<!--                         </ul> -->
<!--                     </div> -->
                    <div class="col-4 col-md-4 text-center">
                        <h3>其他</h3>
                        <ul class="list-unstyled">
                            <li><a href="">聯絡我們</a></li>
                            <li><a href="">常見問題</a></li>
                        </ul>
                    </div>
<!--                     <div class="col-12 col-md-5 text-center text-md-left mt-3 mt-md-0"> -->
<!--                         <ul class="list-inline"> -->
<!--                             <li class="list-inline-item"><a href="https://www.facebook.com/StreetVoiceTaiwan/" -->
<!--                                     target="_blank" class="btn btn-outline-white btn-circle"><span -->
<!--                                         class="icon-fb"></span></a></li> -->
<!--                             <li class="list-inline-item"><a href="https://www.instagram.com/streetvoice_tw/" -->
<!--                                     target="_blank" class="btn btn-outline-white btn-circle"><span -->
<!--                                         class="icon-instagram"></span></a></li> -->
<!--                             <li class="list-inline-item"><a href="https://www.youtube.com/user/StreetVoiceTV" -->
<!--                                     target="_blank" class="btn btn-outline-white btn-circle"><span -->
<!--                                         class="icon-youtube"></span></a></li> -->
<!--                         </ul> -->
<!--                         <p> -->
<!--                             <a href="/svapp/open_in_web/0/">開啟手機版</a> -->
<!--                         </p> -->
<!--                         <p class="text-muted"><small>Copyright © 2006-2020 StreetVoice 街聲.</small></p> -->
<!--                     </div> -->
                </div>
            </div>
        </footer>
        <!-- end of include footer -->

        <!-- script from vendors -->
        <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/band/index_band.js"></script>
        <!-- script -->

    </body>

    </html>