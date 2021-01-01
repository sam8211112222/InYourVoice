<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.ticket.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	if(request.getParameter("event_id")!=null){
		EventService eventSvc = new EventService();
		TicketService ticketSvc = new TicketService();
		request.setAttribute("eventVO", eventSvc.getOneEvent(request.getParameter("event_id")));
	}
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>活動名稱</title>



    <link href="<%=request.getContextPath()%>/front-end/event/css/event.css" media="all" rel="stylesheet">
    <link rel="stylesheet" media="screen" href="<%=request.getContextPath() %>/front-end/event/css/event-font.css">
    <script src="<%=request.getContextPath()%>/front-end/event/vendors/jquery/jquery-3.5.1.min.js"></script>

    <script>
        window.eventIsPublished = true;
    </script>
    <style type="text/css">
        iframe#_hjRemoteVarsFrame {
            display: none !important;
            width: 1px !important;
            height: 1px !important;
            opacity: 0 !important;
            pointer-events: none !important;
        }
    </style>
</head>

<body>

    <div class="outer-wrapper">
        <div class="share-block">
            <div class="container outer">
            </div>
        </div>
        <div class="content-wrapper">
            <div class="content container">
                <div class="header">
                    <div class="header-title">
                        <h1>${eventVO.event_title}</h1>
                    </div>
                </div>
                <div class="event-info">
                    <ul class="info">
                        <li>
                            <span class="info-desc"><i class="fa fa-calendar"></i><span class="timezoneSuffix">2021/01/16(周六) 19:30(+0800)</span>
                            <span>
                        </li>
                        <li>
                            <span class="info-desc"><i class="fa fa-map-marker"></i>台北小巨蛋 / 台北市松山區南京東路四段2號</span>
                        </li>
                        <li>
                            <span class="info-org mobi-only">
                          <i class="fa fa-sitemap"></i>開麗娛樂經紀有限公司
                        </span>
                        </li>
                    </ul>
                </div>
                <div class="og-banner"><img src="./謝金燕 2021 TURN口罩 世界巡迴演唱會_files/___-kktix-1200X630_large.jpg"></div>
                <div class="organizers mobi-hide clearfix">
                    <i class="fa fa-sitemap"></i> 主辦單位 <a href="https://carrier.kktix.cc/">開麗娛樂經紀有限公司</a>

                </div>
                <div class="attend-btn-wrapper mobi-only">
                    <a href="https://kktix.com/events/jeanniehsieh-ewef1/registrations/new" class="btn-point">下一步</a>


                </div>
                <div class="main clearfix">
                    <div class="description">
                        <div class="bg_black">
                            <p style="text-align: center;">&nbsp;</p>

                            <p style="text-align: center;"><span style="color:#ffffff;"><span style="font-size:18px;"><strong>謝金燕 2021 TURN口罩 世界巡迴演唱會</strong></span></span>
                            </p>

                            <div class="bg_black">

                            </div>
                        </div>
                    </div>
                </div>
                <div class="location clearfix">
                    <div id="map-container" class="side-content">
                        <div class="map-wrapper">
                            <div class="btn-wrapper">
                                <button id="view-map-btn" class="btn-open"><span><i class="fa fa-map-marker" aria-hidden="true"></i>
                        檢視地圖
                      </span></button>
                            </div>
                            <div id="map-content" data-lat="25.0507933" data-lng="121.5498896" style="width:280px; height: 280px;" class="map-wrapper">
                            </div>
                        </div>
                    </div>
                    <div class="address">
                        台北小巨蛋 / 台北市忠孝東路2段
                    </div>
                    <div class="btn-group">
                        <a href="https://maps.google.com/?daddr=%E5%8F%B0%E5%8C%97%E5%B8%82%E6%9D%BE%E5%B1%B1%E5%8D%80%E5%8D%97%E4%BA%AC%E6%9D%B1%E8%B7%AF%E5%9B%9B%E6%AE%B52%E8%99%9F" target="_blank" class="btn-view">
                        規劃路線
                    </a>
                        <a href="https://maps.google.com/maps?ll=25.0507933,121.5498896&amp;&amp;q=loc:25.0507933,121.5498896" target="_blank" class="btn-view">
                        檢視較大的地圖
                    </a>
                    </div>
                </div>
                <div class="tickets">
                    <h2>活動票券</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th class="name">票種</th>
                                    <th class="period">販售時間</th>
                                    <th class="price">售價</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="name">全票(特1區)
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">3,800</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票(特2區)
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">3,800</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">3,200</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">2,800</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">2,500</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">2,200</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">1,800</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">1,500</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="name">全票
                                    </td>
                                    <td class="period">
                                        <span class="period-time">
                                        <span class="time"><span class="timezoneSuffix">2020/10/30 12:00(+0800)</span></span>
                                        ~
                                        <span class="time"><span class="timezoneSuffix">2021/01/16 19:30(+0800)</span></span>
                                        </span>
                                    </td>
                                    <td class="price">
                                        <ul>
                                            <li><span class="price"><span class="currency">TWD$</span><span class="currency-value">800</span></span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <a href="https://kktix.com/events/jeanniehsieh-ewef1/registrations/new" class="btn-point">下一步</a>


                </div>
            </div>
        </div>
    </div>


    <script>
        $(document).ready(function() {
            $(".btn-open").click(function() {
                $(".btn-wrapper").fadeOut()
            });
        });
    </script>




    <script>
        var container = document.getElementById("map-container");
        var gmapLoaded = function() {
            var e, t, a = [],
                o = [],
                n, r, l, s, g, i = ["map-content", "map-content2"];
            for (e = 0; e < i.length; e = e + 1) {
                t = document.getElementById(i[e]);
                if (!!t) {
                    a.push(t)
                }
            }
            for (e = 0; e < a.length; e = e + 1) {
                t = a[e];
                if (t.dataset !== undefined) {
                    n = t.dataset.lat;
                    r = t.dataset.lng
                } else {
                    n = t.getAttribute("data-lat");
                    r = t.getAttribute("data-lng")
                }
                if (typeof google !== "undefined" && !!google.maps && !!n && !!r) {
                    l = {
                        zoom: 16,
                        scaleControl: false,
                        panControl: false,
                        zoomControl: false,
                        mapTypeControl: false,
                        streetViewControl: false,
                        scrollwheel: false,
                        draggable: false,
                        disableDoubleClickZoom: true,
                        center: new google.maps.LatLng(n, r)
                    };
                    o[e] = new google.maps.Map(t, l);
                    o[e].marker = new google.maps.Marker({
                        map: o[e],
                        draggable: false,
                        position: l.center
                    })
                } else {
                    container.parentNode.removeChild(container);
                    return
                }
            }
            g = function() {
                var e = 0,
                    t;
                for (e = 0; e < o.length; e = e + 1) {
                    t = o[e].getCenter();
                    google.maps.event.trigger(o[e], "resize");
                    o[e].setCenter(o[e].marker.getPosition())
                }
            };
            google.maps.event.addDomListener(window, "resize", function() {
                clearTimeout(s);
                s = setTimeout(g, 150)
            })
        };
        $('#view-map-btn').click(function() {
            var e;
            if (!!container) {
                e = document.createElement("script");
                e.type = "text/javascript";
                e.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyCfNgtLBXMfJGxhfy4G16RXp5pRIyJlGAY&callback=gmapLoaded";
                document.body.appendChild(e)
            }
        });
    </script>
    <!-- 在活動公開頁點選按鈕才顯示並呼叫 google map 的功能改動，邏輯用 jquery 修改， #show-location 被按下時，該按鈕隱藏，顯示 #event-location 並呼叫 google map api，目前針對此處和 seminar event theme 做些修改讓這功能可以運行，但須等到 Fire 設計完所有 theme 後依據設計套用真正的程式 -->



</body>

</html>