<%@page import="java.util.stream.Collectors"%>
<%@page import="com.member.model.MemberVo"%>
<%@page import="com.favorites.model.FavoritesVO"%>
<%@page import="java.util.List"%>
<%@page import="com.favorites.model.FavoritesService"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="com.band.model.BandVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>
<!-- http://localhost:8081/TEA102G6/band/band.do?action=getBandMain&band_id=BAND00000 -->

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>樂團資訊 – 樂團詳情 - 音樂作品 band_album.jsp</title>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://github.hubspot.com/odometer/themes/odometer-theme-minimal.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.2.3/animate.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/band/index_band.css">
        
        <style>
        	.dropdown, .dropleft{
        		display:flex !important;
        	}
        </style>
    </head>
    
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
    
    <%
		MemberVo memeberVO = (MemberVo) session.getAttribute("memberVo");
		
		String band_id = bandVO.getBand_id();
		String member_id = "LogedOutUser";
		FavoritesService favSvc = new FavoritesService();
		
		if(memeberVO!= null){
			pageContext.setAttribute("memeberVO", memeberVO);
			member_id = memeberVO.getMemberId();
			String member_id_effective_final = member_id;
			List<FavoritesVO> favoriteVOList = favSvc.getAll().stream()
				.filter(f -> f.getMember_id().equals( member_id_effective_final ))
				.filter(f -> f.getFavorite_id().equals(band_id))
				.collect(Collectors.toList());
		
			boolean ifExisted = favoriteVOList.size() > 0;
			pageContext.setAttribute("ifExisted", ifExisted);
		}
	%>

<%--     <body ${memberVO != null ? "onload='connect();' onunload='disconnect();'":""}>  --%>
    <body onload='connect();' onunload='disconnect();'> 
		<%@ include file="/front-end/header_footer/header.jsp" %>
        <!-- start of include header -->

        <!-- end of include header -->


        <!-- start of upper content -->

        <div class="width-navbar-dark position-relative profile-page-header-wrapper mb-6" style="background-image: url(<%= request.getContextPath() %>/band/band.do?action=getBandBanner&band_id=${bandVO.band_id}); background-size: 100%;">
            <!-- <div class="profile-cover-block image-upload width-absolute-btn">
                <div class="image-preview">
                    <div class="image-preview-item" id="cover_img"> -->
                        <!-- <img class="band_banner" src="https://i.imgur.com/7HD0bkf.jpg" alt=""> -->
                        <!-- http://fakeimg.pl/440x300/282828/EAE0D0/?text=PJCHENder -->
<!-- 	                    <img class="band_banner" src="http://fakeimg.pl/1920x360?text=Band_Banner" alt=""> -->
						
	                    <!-- <img class="band_banner" src="data:image/gif;base64,<%= band_banner_base64 %>" alt=""> -->
<!--                         
                    </div>
                </div>

            </div> -->
            <div class="container profile-cover-block-up">
                <div class="row">
                    <div class="col-md-3">
                        <!-- <div class="image-upload width-absolute-btn">
                            <div class="image-preview img-square cover-block img-circle">
                                <div class="image-preview-item" id="avatar_img"> -->
                                    <!-- <img class="band_photo" src="https://i.imgur.com/5IWAEdX.jpg" alt=""> -->
<!-- 	                                <img class="band_photo" src="http://fakeimg.pl/600x600?text=Band_Photo" alt=""> -->
	                                <!-- <img class="band_photo" src="data:image/gif;base64,<%= band_photo_base64 %>" alt="">
                                </div>
                            </div>

                        </div> -->
                        <div class="flip" data-band_id="${bandVO.band_id}">
                            <div class="front"
                                style="background-image: url(<%= request.getContextPath() %>/band/band.do?action=getBandPhoto&band_id=${bandVO.band_id})">
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
                                        <h4 class="font-weight-normal" style="color:white;">追蹤數</h4>
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
                        <li class="nav-item ml-auto col-4" style="color:blue; font-size: 20px;">追蹤數<div class="odometer animated fadeIn" id="odometer"></div></li>
                        <li class="nav-item ml-auto col-md-4 col-lg-3 col-xl-2">

						
						
						<c:choose>
						
						
						    <c:when test="${memeberVO !=null && ifExisted }">
								<a data-ga-on="click" data-ga-event-category="follow"
	                                data-ga-event-action="珂拉琪 Collage (2429455)" data-ga-dimension-value="User profile"
	                                data-id="2429455" class="btn btn-primary btn-lg btn-block js-follow delfavorite" >
	                                <span class="follow_text"><i class="fas fa-check"></i>追蹤</span>
	                            </a>
						    </c:when>
						    
						    <c:when test="${memeberVO !=null && not ifExisted }">
							   	<a data-ga-on="click" data-ga-event-category="follow"
	                                data-ga-event-action="珂拉琪 Collage (2429455)" data-ga-dimension-value="User profile"
	                                data-id="2429455" class="btn btn-primary btn-lg btn-block js-follow addfavorite" >
	                                <span class="follow_text"><i class="fas fa-plus"></i>追蹤</span>
	                            </a>
						    </c:when>
							<c:when test="${memeberVO ==null}"></c:when>
						    
						    <c:otherwise>
						    </c:otherwise>
						    
						</c:choose>
                            
                            
                            
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


        <%@ include file="/front-end/header_footer/footer.jsp" %>

        <!-- script from vendors -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/aplayer/1.10.1/APlayer.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/band/index_band.js"></script>
        <!-- script -->
        <script src="https://github.hubspot.com/odometer/odometer.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>

        <script>
            $(function(){

                $(document).on("click", ".addfavorite", function(){
                    $.ajax({
                        url: "<%=request.getContextPath()%>/band/band.do",           // 資料請求的網址
                        type: "POST",                  // GET | POST | PUT | DELETE | PATCH
                        data: {
                            action : "addFavorite",
                            type : "band",
                            band_id : "${bandVO.band_id}"
                        },                  // 傳送資料到指定的 url
                        dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
                        timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
                       
                        success: function(data){      // request 成功取得回應後執行
                            console.log(data);
                            if(data == "added"){
                            	console.log("data added");
                                $(".addfavorite").find("i.fas").addClass("fa-check");
                                $(".addfavorite").find("i.fas").removeClass("fa-plus");
                                $(".addfavorite").addClass("delfavorite");
                                $(".addfavorite").removeClass("addfavorite");
                            }
                        }
                        
                    });
                })
                
                $(document).on("click", ".delfavorite", function(){
                    $.ajax({
                        url: "<%=request.getContextPath()%>/band/band.do",           // 資料請求的網址
                        type: "POST",                  // GET | POST | PUT | DELETE | PATCH
                        data: {
                            action : "delFavorite",
                            type : "band",
                            band_id : "${bandVO.band_id}"
                        },                  // 傳送資料到指定的 url
                        dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
                        timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
                       
                        success: function(data){      // request 成功取得回應後執行
                            console.log(data);
                            if(data == "deleted"){
                            	console.log("data deleted");
                                $(".delfavorite").find("i.fas").addClass("fa-plus");
                                $(".delfavorite").find("i.fas").removeClass("fa-check");
                                $(".delfavorite").addClass("addfavorite");
                                $(".delfavorite").removeClass("delfavorite");
                            }
                        }
                        
                    });
                })
                
				<%
					List<FavoritesVO> followCount = favSvc.getAll().stream()
					.filter(f -> f.getFavorite_id().equals(band_id))
					.collect(Collectors.toList());
                	
                	pageContext.setAttribute("followCount", followCount.size());
				%>
                var odometer = new Odometer({ 
			        el: $('.odometer')[0], 
			        value: 0, 
			        theme: 'minimal',
			        duration: 3000
			      });
			      odometer.render();
			      
			      $('.odometer').text(${followCount});
			      
			      // TODO: To increase all numbers independently
			      // TODO: Randomize fadeIn of different digits
            })
            
           			var MyPoint = "/FolloWS/<%=member_id != null ? member_id:"LogedOutMember"%>";
           			console.log(MyPoint);
					var host = window.location.host;
					var path = window.location.pathname;
					var webCtx = path.substring(0, path.indexOf('/', 1));
					var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
									// ws://localhost:8081/WebSocketChatWeb/TogetherWS/james
				
// 					var statusOutput = document.getElementById("statusOutput");
					var webSocket;
				
					function connect() {
						// create a websocket
						webSocket = new WebSocket(endPointURL);
				
						webSocket.onopen = function(event) {
// 							updateStatus("WebSocket Connected");
// 							document.getElementById('sendMessage').disabled = false;
// 							document.getElementById('connect').disabled = true;
// 							document.getElementById('disconnect').disabled = false;
						};
				
						webSocket.onmessage = function(event) {
// 							var messagesArea = document.getElementById("messagesArea");
// 							var jsonObj = JSON.parse(event.data);
// 							var message = jsonObj.userName + ": " + jsonObj.message + "\r\n";
// 							messagesArea.value = messagesArea.value + message;
// 							messagesArea.scrollTop = messagesArea.scrollHeight;

							// 更新追蹤數
// 							alert("onmessage!");
// 							alert(event.data);
							$('.odometer').text(event.data);
						};
				
						webSocket.onclose = function(event) {
// 							updateStatus("WebSocket Disconnected");
						};
					}
				
// 					var inputUserName = document.getElementById("userName");
// 					inputUserName.focus();
				
// 					function sendMessage() {
// 						var userName = inputUserName.value.trim();
// 						if (userName === "") {
// 							alert("Input a user name");
// 							inputUserName.focus();
// 							return;
// 						}
				
// 						var inputMessage = document.getElementById("message");
// 						var message = inputMessage.value.trim();
				
// 						if (message === "") {
// 							alert("Input a message");
// 							inputMessage.focus();
// 						} else {
// 							var jsonObj = {
// 								"userName" : userName,
// 								"message" : message
// 							};
// 							webSocket.send(JSON.stringify(jsonObj));
// 							inputMessage.value = "";
// 							inputMessage.focus();
// 						}
// 					}
				
					function disconnect() {
						webSocket.close();
// 						document.getElementById('sendMessage').disabled = true;
// 						document.getElementById('connect').disabled = false;
// 						document.getElementById('disconnect').disabled = true;
					}
				
// 					function updateStatus(newStatus) {
// 						statusOutput.innerHTML = newStatus;
// 					}
        </script>

    </body>

    </html>