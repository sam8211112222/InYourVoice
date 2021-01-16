<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
       <!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>驗票成功</title>	
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/eventorder/success.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>

<body>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>


    <div class="success">


        <div id="container">
            <div id="success-box">
                <div class="dot"></div>
                <div class="dot two"></div>
                <div class="face">
                    <div class="eye"></div>
                    <div class="eye right"></div>
                    <div class="mouth happy"></div>
                </div>
                <div class="shadow scale"></div>
                <div class="message">
                    <h1 class="alert"> 驗票成功!</h1>
                </div>
            </div>
        </div>
    </div>
    <!-- 訂單明細 -->
    <div class="container">
        <div class="top-title">
            <h5>驗票成功</h5>
        </div>

        <div>
            <div class="product-detail">
                <ul class="detail-line">
                    <li class="det-1">
                        <p>票券帳號</p>
                        <p>${ticketOwner}</p>
                    </li>
                    <li class="det-1">
                        <p>使用時間</p>
                        <p><fmt:formatDate value="${time}" pattern="yyyy/MM/dd HH:mm" /></p>
                    </li>
                    <li class="det-1">
                        <p>票種</p>
                        <p>${ticketVO.ticket_name}</p>
                    </li>
                </ul>
            </div>


        </div>

        <div class="top-title">
            <h5>若您有任何問題，請透過訂單系統客服表單與我們聯繫。謝謝!</h5>
        </div>

    </div>
</body>

</html>