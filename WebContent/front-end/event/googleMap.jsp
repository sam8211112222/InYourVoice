<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.event.model.*"%>
<%@ page import="java.util.*"%>
<% 
	EventService eventSvc = new EventService();
	List<EventVO> list = eventSvc.getAll();
	session.setAttribute("list", list);
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/band/listAllBand.css">
<style>
#map {
	height: 500px;
	width: 500px;
}
</style>
</head>

<body>
<%@ include file="/front-end/header_footer/header.jsp"%>
	<div class="container">

		<div class="row justify-content-center">
			<div class="event_title">活動地點總攬</div>
		</div>

		<div class="row justify-content-center">
			<form action="<%=request.getContextPath()%>/event/EventServlet">
				<div class="search_box">
					<input type="hidden" name="action" value="getEvent"> <input
						type="text" id="keyword" name="searchKeyWord" placeholder="search">
					<button type="submit" class="btn btn-search" id="searchBtn">
						<i class="fa fa-search"></i>
					</button>
					<span><a
						href="<%=request.getContextPath()%>/front-end/events/googleMap.jsp"><i
							class="fas fa-map-marker-alt"></i></a></span>
				</div>
			</form>
		</div>
</div>

		<div class="row justify-content-around">
			<div id="map" class="embed-responsive embed-responsive-16by9"></div>
		</div>

<jsp:include page="/front-end/header_footer/footer.jsp" flush="true" />
		<script async defer
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDGybO6zwEF7iK4WGAo6GpIoBKkZlJF3IA&callback=initMap">
        </script>
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
		<script>
		
		$('img').on("click", function(e){
			e.preventDefault();
			location.href("<%=request.getContextPath()%>/EventPicController?action=getEventPic&event_id=${eventVO.event_id}");
			
		})

        var map;
        function initMap() {
//         	navigator.geolocation.getCurrentPosition(function(position){
//         		   lat = position.coords.latitude;
//         	        lng = position.coords.longitude;
//   			  console.log(position);
//   			});
  var markers = [];
  var infoWindows = [];
  var loaction;
  var geocoder = new google.maps.Geocoder();
  var info_config = [
	<c:forEach var="eventVO" items="${list}">
	<c:if test="${eventVO.event_status==1}">
    `<h2>`+"${eventVO.event_place}"+'</h2>'+
    '<span>'+"${eventVO.event_title}"+'</span><br/>'+
    '<a href="<%=request.getContextPath()%>/EventPicController?action=getEventPic&event_id=${eventVO.event_id}"><img class="infoImg" style="max-width:100%;" src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></a>'
    ,
    </c:if>
    </c:forEach>
    ];


  //建立地圖 marker 的集合
  var marker_config = [
	<c:forEach var="eventVO" items="${list}">
	<c:if test="${eventVO.event_status==1}">
	{
    address: "${eventVO.event_city}${eventVO.event_cityarea}${eventVO.event_address}",
    title: "${eventVO.event_place}"
    },
    </c:if>
  </c:forEach>];  

  //geocoder主程式
  function _geocoder(address, callback){
    geocoder.geocode({
      address: address
    }, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        loaction = results[0].geometry.location;
        callback(loaction); //用一個 callback 就不用每次多寫上面這段
      }
    });
  }

  //使用地址或名稱標出位置
  _geocoder('台北市南京復興捷運站',function(address){
    var map = new google.maps.Map(document.getElementById('map'), {
      center: address,
      zoom: 14,
  	mapTypeControl: false,
	streetViewControl: false,
	fullscreenControl: false,
	styles:  [{
		  featureType: 'poi.business',
		  stylers: [{ visibility: 'off'
		  }]
		}],
    });

    //設定資訊視窗內容
    info_config.forEach(function(e,i){
      infoWindows[i] = new google.maps.InfoWindow({
        content: e
      });
    });

    //標出 marker
    marker_config.forEach(function(e,i){
      _geocoder(e.address,function(address){
        var marker = {
          position:address,
          map:map,
          title: e.title
        }
        markers[i] = new google.maps.Marker(marker);
        markers[i].setMap(map);
        markers[i].addListener('click', function() {
          infoWindows[i].open(map, markers[i]);
        });
      });
    });
  });
}
    </script>
</body>

</html>