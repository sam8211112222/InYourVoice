<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.band.model.*"%>
<%@ page import="java.util.*"%>
<%
	MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
	if (memberVo == null) {
		response.sendRedirect(request.getContextPath() + "/front-end/member/Login.jsp");
	} ;
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<style>
    .row {
        text-align: center;
    }

    .col-12 {
        text-align: center;
    }



    audio {
        position: fixed;
        right: 0;
        bottom: 10px;
        width: 500px;
    }

    #bannerpic {
        margin: 0 auto;
    }
</style>

<body>
<form id="form" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/band/band.do">
    <div class="col-12">
        <h1>樂團申請表單 <span class="badge badge-secondary"><i class="fas fa-music"></i></span></h1>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-5">
                <form>
                    <div class="form-group">
                        <label for="bandName">樂團名稱</label>
                        <input type="text" class="form-control" id="bandName" name="bandName">
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                            else.</small>
                    </div>
                    <div class="input-group" id="piececheck">
                        <div class="custom-file">

                            <input type="file" class="custom-file-input" id="bandPieceCheck" name="bandPieceCheck"required>
                            <label class="custom-file-label" for="validatedInputGroupCustomFile">請上傳一首作品</label>
                            <canvas id="canvas"></canvas>
                                <audio id="audio" controls></audio>
                        </div>
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" id="bandPieceCheckBtn" type="button">上傳</button>
                        </div>
                    </div>
                    <div class="invalid-feedback">
                        必須上傳一首作品
                    </div>
                    <div class="mb-3">
                        <label for="bandIntro" id="info">自我介紹</label>
                        <textarea class="form-control" id="bandIntro" name="bandIntro"placeholder="Required example textarea"
                            required></textarea>
                        <div class="invalid-feedback">
                            此欄位不能空白
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary"  id="submit">申請</button>
                   <input type="hidden" name="action" value="bandSignup">
                   <input type="hidden" name="memberId" value="${memberVo.memberId}">
                </form>
            </div>

            <div class="col-7">
                <label for="bandBanner">上傳封面照片</label>
                <div class="input-group" id="bandbannerpic">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="bandBanner" name="bandBanner"required>
                        <label class="custom-file-label" for="validatedInputGroupCustomFile">請上傳一張樂團封面照</label>
                    </div>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="bandBannerBtn">上傳</button>
                    </div>
                </div>
                <div class="invalid-feedback">
                    必須上傳一張封面照片
                </div>
                <div class="col-7" id="bannerpic">
                    <img src="<%=request.getContextPath() %>/images/無圖片.png" class="img-fluid" alt="Responsive image">
                </div>
            </div>
        </div>
    </div>
    </div>
    </form>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.7/js/all.js">
    </script>
    <script>
        $("#bandBannerBtn").click(function (e) {
            e.stopPropagation();
            var fileInput = $('#bandBanner').val();
            if (fileInput == "") {
                $("#bandbannerpic").addClass("is-invalid");
            }
        })
        $('#bandBanner').change(function () {
            var reader = new FileReader();
            reader.readAsDataURL(this.files[0]);
            let pic = document.getElementsByClassName("img-fluid")[0];
            reader.addEventListener("load", function (e) {
                pic.innerHTML = "";
                let base64 = e.target.result;
                console.log(base64);
                pic.setAttribute("src", base64);
            })
        })
        $("#bandPieceCheckBtn").click(function (e) {
            e.stopPropagation();
            var fileInput = $('#bandPieceCheck').val();
            if (fileInput == "") {
                $("#piececheck").addClass("is-invalid");
            }
        })
        $("#bandIntro").blur(function () {
            var fileInput = $('#bandinfo').val();
            if (fileInput == "") {
                $("#info").addClass("is-invalid");
            } else {
                $("#info").removeClass("is-invalid");
            }
        })
        window.onload = function () {

            var file = document.getElementById("bandPieceCheck");
            var audio = document.getElementById("audio");

            file.addEventListener("change", function () {
                var files = this.files;
                audio.src = URL.createObjectURL(files[0]);
                audio.load();
                var context = new AudioContext();
                var src = context.createMediaElementSource(audio);
                var analyser = context.createAnalyser();
                src.connect(analyser);
                analyser.connect(context.destination);
                analyser.fftSize = 256;
                var bufferLength = analyser.frequencyBinCount;
                var dataArray = new Uint8Array(bufferLength);




                // audio.play();

            });
        };
    </script>
</body>


</html>