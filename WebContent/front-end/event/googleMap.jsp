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
<style>
#map {
	height: 300px;
	width: 300px;
}
</style>
</head>

<body>

	<div id="map" class="embed-responsive embed-responsive-16by9"></div>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDGybO6zwEF7iK4WGAo6GpIoBKkZlJF3IA&callback=initMap">
        </script>
	<script>
        var map;
        function initMap() {
  var markers = [];
  var infoWindows = [];
  var loaction;
  var geocoder = new google.maps.Geocoder();
  var info_config = [
	<c:forEach var="eventVO" items="${list}">
    '<h2>'+"${eventVO.event_place}"+'</h2>'+
    '<span>'+"${eventVO.event_title}"+'</span><br/>'+
    '<img class="infoImg" src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"><br/>'
    ,
    </c:forEach>
    ];


  //建立地圖 marker 的集合
  var marker_config = [
	<c:forEach var="eventVO" items="${list}">
	{
    address: "${eventVO.event_city}${eventVO.event_cityarea}${eventVO.event_address}",
    title: "${eventVO.event_place}"
    },
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
  _geocoder('南京復興捷運站',function(address){
    var map = new google.maps.Map(document.getElementById('map'), {
      center: address,
      zoom: 14
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