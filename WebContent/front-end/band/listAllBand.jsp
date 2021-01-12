<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>listAllBand.jsp</title>
            <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/band/listAllBand.css">
            <style>
                #heyvoice{
                    color: black;
                    background-color: aliceblue;
                    position:fixed; 
                    z-index:1; 
                    right:0; 
                    bottom: 0;
                }
            </style>
        </head>

        <body>
			<jsp:include page="/front-end/header_footer/header.jsp"></jsp:include>
            <div class="container">

                <div class="row justify-content-center">
                    <div class="band_title">
                        樂團總覽
                    </div>
                </div>

                <div class="row justify-content-center">
                    <form action="<%= request.getContextPath() %>/band/band.do">
                        <div class="search_box">
                            <input type="hidden" name="action" value="listAllBand">
                            <input type="text" name="searchKeyWord" placeholder="search">
                            <button type="submit" class="btn btn-search">
                            <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </form>
                </div>


                <div class="row justify-content-around">

                    <c:forEach var="bandVO" items="${bandVOList}">

                        <div class="flip" data-band_id="${bandVO.band_id}" style="cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/band/band.do?action=getBandMain&band_id=${bandVO.band_id}';">
                            <div class="front"
                                style="background-image: url(<%= request.getContextPath() %>/band/band.do?action=getBandPhoto&band_id=${bandVO.band_id})">
                                <h1 class="text-shadow">${bandVO.band_name}</hi>
                            </div>
                            <div class="back">
                                <!-- <h2>${bandVO.band_name}</h2> -->
                                <p>${bandVO.band_intro}</p>
                            </div>
                        </div>

                    </c:forEach>
                    <c:if test="${empty bandVOList}">
                    	<span style="color : black; font-size: 30px;">查無資料!</span>
                    </c:if>

                    <div id="heyvoice">
                        
                    </div>
                    





                </div>
            </div>
			<jsp:include page="/front-end/header_footer/footer.jsp"></jsp:include>
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
			<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.5.1.min.js"></script>
            <script src="<%= request.getContextPath() %>/js/band/listAllBand.js"></script>
            <script>
//  	            var heyvoice = document.getElementById('heyvoice');
//  	            var recognition = new webkitSpeechRecognition();
	
//  	            recognition.continuous=true;
//  	            recognition.interimResults=true;
//  	            recognition.lang="cmn-Hant-TW";
	
//  	            recognition.onstart=function(){
//  	              console.log('開始辨識...');
//  	            };
//  	            recognition.onend=function(){
//  	              console.log('停止辨識!');
//  	            };
	
//  	            recognition.onresult=function(event){
//  	              var i = event.resultIndex;
//  	              var j = event.results[i].length-1;
//  	              heyvoice.innerHTML = event.results[i][j].transcript;
//  	            };
	
//  	            recognition.start();  
            </script>

        </body>

        </html>