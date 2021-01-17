<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ page import="com.event.model.*"%>
        <%@ page import="com.ticket.model.*"%>
            <%@ page import="java.util.*"%>
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
                    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

                        <%
	if (request.getParameter("event_id") != null) {
		EventService eventSvc = new EventService();
		TicketService ticketSvc = new TicketService();
		request.setAttribute("eventVO", eventSvc.getOneEvent(request.getParameter("event_id")));
	}
%>


                            <html>

                            <head>
                                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

                                <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
                                <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->

                                <title>${eventVO.event_title}</title>


                                <!-- <link rel="stylesheet" -->
                                <!-- 	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" /> -->
                                <link href="<%=request.getContextPath()%>/front-end/event/css/event.css" media="all" rel="stylesheet">
                                <link rel="stylesheet" media="screen" href="<%=request.getContextPath()%>/front-end/event/css/event-font.css">
                                <script src="<%=request.getContextPath()%>/front-end/event/vendors/jquery/jquery-3.5.1.min.js"></script>

                                <style>
                                    iframe#_hjRemoteVarsFrame {
                                        display: none !important;
                                        width: 1px !important;
                                        height: 1px !important;
                                        opacity: 0 !important;
                                        pointer-events: none !important;
                                    }
                                    
                                    .container {
                                        width: 860px !important;
                                    }
                                    
                                    .display-flex {
                                        display: flex;
                                    }
                                    
                                    .qty {
                                        width: 60px;
                                        text-align: center;
                                        height: 34px;
                                    }
                                    
                                    .qtyminus,
                                    .qtyplus {
                                        width: 34px;
                                        height: 34px;
                                        background: #212529;
                                        text-align: center;
                                        font-size: 19px;
                                        line-height: 36px;
                                        color: #fff;
                                        cursor: pointer;
                                    }
                                    
                                    .qtyminus {
                                        border-radius: 3px 0 0 3px;
                                    }
                                    
                                    .qtyplus {
                                        border-radius: 0 3px 3px 0;
                                    }
                                    
                                    #orderSubmit {
                                        width: 120px;
                                        margin: 30px auto 10px;
                                        text-align: center;
                                        padding: 0 30px;
                                        background-color: #59ab21;
                                        box-shadow: #4e951d 0 -2px 0 inset;
                                        color: #fff;
                                        outline: 0;
                                        border-radius: 0;
                                        transition: all .1s ease-in-out;
                                        display: block;
                                        border: 0;
                                        height: 48px;
                                        font-size: 18px;
                                        font-weight: bold;
                                        line-height: 48px;
                                        text-decoration: none;
                                        cursor: pointer;
                                    }
                                    
                                    .location {
                                        background: #337fa3;
                                    }
                                    
                                    a {
                                        color: black;
                                    }
                                    
                                    a:hover {
                                        color: #337fa3;
                                    }
                                    
                                    #orderSubmit {
                                        background-color: #337fa3;
                                        box-shadow: #556199 0 -2px 0 inset;
                                    }
                                    
                                    .og-banner {
                                        max-height: 830px;
                                    }
                                    
                                    .seat-banner img {
                                        display: block;
                                        width: 100%;
                                        height: auto;
                                        margin: 0 auto;
                                    }
                                </style>
                            </head>

                            <body>
                                <%@ include file="/front-end/header_footer/header.jsp"%>

                                    <div class="outer-wrapper">
                                        <div class="content-wrapper">
                                            <div class="content container">
                                                <div class="header">
                                                    <div class="header-title">
                                                        <h1>${eventVO.event_title}</h1>
                                                    </div>
                                                </div>
                                                <div class="event-info">
                                                    <ul class="info">
                                                        <li><span class="info-desc"><i
								class="fas fa-calendar-alt"></i><span class="timezoneSuffix"><fmt:formatDate value="${eventVO.event_start_time}"
								pattern="yyyy/MM/dd HH:mm" /></span> </span>
                                                        </li>
                                                        <li><span class="info-desc"><i
								class="fas fa-map-marker-alt"></i>${eventVO.event_place} /
								${eventVO.event_address}</span></li>
                                                        <li><span class="info-org mobi-only"> <i
								class="fas fa-guitar"></i>${bandVO.band_name}
						</span></li>
                                                    </ul>
                                                </div>
                                                <div class="og-banner">
                                                    <img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}">
                                                </div>
                                                <div class="organizers mobi-hide clearfix">
                                                    <i class="fas fa-guitar"></i>主辦單位 <a href="<%=request.getContextPath()%>/band/band.do?action=getBandMain&band_id=${eventVO.band_id}">${bandVO.band_name}</a>

                                                </div>
                                                <div class="attend-btn-wrapper mobi-only">
                                                </div>
                                                <div class="main clearfix">
                                                    <div class="description">
                                                        <div class="bg_black">
                                                            <p style="text-align: center;">&nbsp;</p>

                                                            <p style="text-align: center;">
                                                                <span style="color: #ffffff;"><span
									style="font-size: 18px;"><strong>${eventVO.event_title}</strong></span></span>
                                                            </p>

                                                            <div class="bg_black">${eventVO.event_detail}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="seat-banner">
                                                    <img src="<%=request.getContextPath()%>/EventPicController?action=getEventSeat&event_id=${eventVO.event_id}">
                                                </div>
                                                <div class="location clearfix">
                                                    <div id="map-container" class="side-content">
                                                        <div class="map-wrapper">
                                                            <div class="btn-wrapper">
                                                                <button id="view-map-btn" class="btn-open">
									<span><i class="fas fa-map-marked-alt"></i> 檢視地圖 </span>
								</button>
                                                            </div>
                                                            <div id="map-content" data-lat="" data-lng="" style="width: 280px; height: 280px;" class="map-wrapper"></div>
                                                        </div>
                                                    </div>
                                                    <div class="address">${eventVO.event_place}/ ${eventVO.event_address}
                                                    </div>
                                                    <div class="btn-group">
                                                        <a href="https://maps.google.com/?daddr=${eventVO.event_address}" target="_blank" class="btn-view"> 規劃路線 </a> <a href="https://maps.google.com/maps?q=${eventVO.event_place}" target="_blank" class="btn-view"> 檢視較大的地圖 </a>
                                                    </div>
                                                </div>
                                                <div>
                                                    <c:choose>
                                                        <c:when test="${not empty ticketList}">
                                                            <form method="GET" action="<%=request.getContextPath()%>/EventOrderController">
                                                                <div class="tickets">
                                                                    <h2>活動票券</h2>
                                                                    <div class="table-wrapper">
                                                                        <table>
                                                                            <thead>
                                                                                <tr>
                                                                                    <th class="name">票種</th>
                                                                                    <th class="period">販售時間</th>
                                                                                    <th class="price">售價</th>
                                                                                    <th class="rest">剩餘張數</th>
                                                                                    <th class="select">選擇張數</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <c:forEach var="ticketVO" items="${ticketList}">
                                                                                    <c:if test="${ticketRestAmount.get(ticketVO.ticket_id)>0}">
                                                                                        <tr>
                                                                                            <td class="name">${ticketVO.ticket_name}</td>
                                                                                            <td class="period"><span class="period-time">
																<span class="time"><span class="timezoneSuffix"><fmt:formatDate value="${ticketVO.ticket_onsale_time}"
								pattern="yyyy/MM/dd HH:mm" /></span></span>
                                                                                                ~ <span class="time"><span class="timezoneSuffix"><fmt:formatDate value="${ticketVO.ticket_endsale_time}"
								pattern="yyyy/MM/dd HH:mm" /></span></span>
                                                                                                </span>
                                                                                            </td>
                                                                                            <td class="price">
                                                                                                <ul>
                                                                                                    <li><span class="price"><span
																		class="currency">TWD$</span><span class="currency-value">${ticketVO.ticket_price}</span></span>
                                                                                                    </li>
                                                                                                </ul>
                                                                                            </td>
                                                                                            <td class="rest">${ticketRestAmount.get(ticketVO.ticket_id)}</td>
                                                                                            <td>
                                                                                                <div class="display-flex">
                                                                                                    <span class="qtyminus" id="qtyminus">-</span> <input type="text" id="quantity" name="orderlist_goods_amount" value="0" class="qty" readonly> <span class="qtyplus"
                                                                                                        id="qtyplus">+</span> <input type="hidden" name="ticket_id" value="${ticketVO.ticket_id}">
                                                                                                </div>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </c:if>
                                                                                </c:forEach>
                                                                            </tbody>
                                                                        </table>

                                                                    </div>
                                                                </div>
                                                                <input type="hidden" name="event_id" value="${eventVO.event_id}">
                                                                <input type="hidden" name="action" value="go-check-out">
                                                                <button id="orderSubmit" type="submit">下一步</button>
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="tickets">
                                                                <div class="table-wrapper"></div>
                                                            </div>
                                                            <a href="<%=request.getContextPath() %>/front-end/event/allevent.jsp"><button id="orderSubmit" type="button">返回</button></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <script>
                                        $(document).ready(function() {
                                            $.ajax({
                                                url: "https://maps.googleapis.com/maps/api/geocode/json?address=${eventVO.event_address}&key=AIzaSyCfNgtLBXMfJGxhfy4G16RXp5pRIyJlGAY",
                                                type: "GET",
                                                dataType: "json",
                                                success: function(data_obj) {
                                                    let lat = data_obj.results[0].geometry.location.lat.toString();
                                                    let lng = data_obj.results[0].geometry.location.lng.toString();
                                                    $("#map-content").attr("data-lat", lat);
                                                    $("#map-content").attr("data-lng", lng);

                                                }
                                            })
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

                                        $(".qtyminus").on("click", function() {
                                            var now = $(this).siblings(".qty").val();
                                            if ($.isNumeric(now)) {
                                                if (parseInt(now) - 1 >= 0) {
                                                    now--;
                                                }
                                                $(this).siblings(".qty").attr("value", now);
                                            }
                                        })
                                        $(".qtyplus").on("click", function() {
                                            var now = $(this).siblings(".qty").val();
                                            var restticket = $(this).closest("td").prev().text();
                                            if ($.isNumeric(now)) {
                                                if (parseInt(now) < parseInt(restticket)) {
                                                    $(this).siblings(".qty").attr("value", parseInt(now) + 1);
                                                }
                                            }
                                        });
                                    </script>
                                    <!-- 在活動公開頁點選按鈕才顯示並呼叫 google map 的功能改動，邏輯用 jquery 修改， #show-location 被按下時，該按鈕隱藏，顯示 #event-location 並呼叫 google map api，目前針對此處和 seminar event theme 做些修改讓這功能可以運行，但須等到 Fire 設計完所有 theme 後依據設計套用真正的程式 -->


                                    <%@ include file="/front-end/header_footer/footer.jsp"%>

                            </body>

                            </html>