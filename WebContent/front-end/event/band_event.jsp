<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!doctype html>
        <html lang="en">

        <head>
            <meta charset="utf-8">
            <title>樂團活動(柏崴)</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

            <style>
                div.col {
                    margin: 0px 0px 5px 0px;
                }
            </style>

        </head>

        <body>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>


            <jsp:useBean id="product" scope="page" class="com.product.model.ProductService" />

            <div class="container">

                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                    <c:forEach items="${band_event}" var="eventVO">
                        <div class="col" style="cursor:pointer;" onclick="location.href='<%=request.getContextPath()%>/event/EventServlet?action=getOne_For_Display&event_id=${eventVO.event_id}'">
                            <div class="card shadow-sm">
                                <img src="<%=request.getContextPath()%>/EventPicController?action=getEventPoster&event_id=${eventVO.event_id}" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <p class="card-text">${eventVO.event_title}</p>
                                    <div class="d-flex justify-content-between align-items-center">

                                        <small class="text-muted">活動時間${eventVO.event_start_time}</small>
                                        <%-- 								<a href="${pageContext.request.contextPath}/product/YUproductServlet?id=${vo.product_id}"  --%>
                                            <!-- 							style="margin-bottom: 0px;">點我查看</a>  -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </body>

        </html>