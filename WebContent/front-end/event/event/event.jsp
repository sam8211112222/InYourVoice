<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.event.model.*"%>
<%@ page import="com.ticket.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%	
	EventVO eventVO = (EventVO)request.getAttribute("eventVO");
	BandService bandSvc = new BandService();
	BandVO bandVO = bandSvc.getOneBand(eventVO.getBand_id());
%>

<!DOCTYPE html>

<html lang="zh-tw" class="js blobconstructor blob-constructor cookies websockets filereader xhr2 localstorage sessionstorage cssanimations indexeddb indexeddb-deletedatabase">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${eventVO.event_title }</title>



    <link href="./css/event.css" media="all" rel="stylesheet">
    <link rel="stylesheet" media="screen" href="./css/event-font.css">
    <script src="./vendors/jquery/jquery-3.5.1.min.js"></script>

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
                        <h1>${eventVO.event_title}</h1>
                    </div>
                </div>
                <div class="event-info">
                    <ul class="info">
                        <li>
                            <span class="info-desc"><i class="fa fa-calendar"></i><span class="timezoneSuffix">${eventVO.event_on_time}</span>
                            <span>
                        </li>
                        <li>
                            <span class="info-desc"><i class="fa fa-map-marker"></i>${eventVO.event_place}/${eventVO.event_address }</span>
                        </li>
                        <li>
                            <span class="info-org mobi-only">
                          <i class="fa fa-sitemap"></i>
                        </span>
                        </li>
                    </ul>
                </div>
                <div class="og-banner"><img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}"></div>
                <div class="organizers mobi-hide clearfix">
                    <i class="fa fa-sitemap"></i>表演者 <a href="<%=request.getContextPath()%>/front-end/band/band_intro.jsp"><%=bandVO.getBand_name()%></a>

                </div>
                <div class="attend-btn-wrapper mobi-only">
                    <a href="https://kktix.com/events/jeanniehsieh-ewef1/registrations/new" class="btn-point">ä¸ä¸æ­¥</a>


                </div>
                <div class="main clearfix">
                    <div class="description">
                        <div class="bg_black">
                            <p style="text-align: center;">&nbsp;</p>

                            <p style="text-align: center;"><span style="color:#ffffff;"><span style="font-size:18px;"><strong>è¬éç 2021 TURNå£ç½© ä¸çå·¡è¿´æ¼å±æ</strong></span></span>
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
                        æª¢è¦å°å
                      </span></button>
                            </div>
                            <div id="map-content" data-lat="25.0507933" data-lng="121.5498896" style="width:280px; height: 280px;" class="map-wrapper">
                            </div>
                        </div>
                    </div>
                    <div class="address">
                        å°åå°å·¨è / å°åå¸å¿ å­æ±è·¯2æ®µ
                    </div>
                    <div class="btn-group">
                        <a href="https://maps.google.com/?daddr=%E5%8F%B0%E5%8C%97%E5%B8%82%E6%9D%BE%E5%B1%B1%E5%8D%80%E5%8D%97%E4%BA%AC%E6%9D%B1%E8%B7%AF%E5%9B%9B%E6%AE%B52%E8%99%9F" target="_blank" class="btn-view">
                        è¦åè·¯ç·
                    </a>
                        <a href="https://maps.google.com/maps?ll=25.0507933,121.5498896&amp;&amp;q=loc:25.0507933,121.5498896" target="_blank" class="btn-view">
                        æª¢è¦è¼å¤§çå°å
                    </a>
                    </div>
                </div>
                <div class="tickets">
                    <h2>æ´»åç¥¨å¸</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th class="name">ç¥¨ç¨®</th>
                                    <th class="period">è²©å®æé</th>
                                    <th class="price">å®å¹</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="name">å¨ç¥¨(ç¹1å)
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
                                    <td class="name">å¨ç¥¨(ç¹2å)
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                                    <td class="name">å¨ç¥¨
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
                    <a href="https://kktix.com/events/jeanniehsieh-ewef1/registrations/new" class="btn-point">ä¸ä¸æ­¥</a>


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
//                 e.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyCfNgtLBXMfJGxhfy4G16RXp5pRIyJlGAY&callback=gmapLoaded";
                document.body.appendChild(e)
            }
        });
    </script>



</body>

</html>