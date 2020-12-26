<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <style>
      /* Styles for wrapping the search box */

.main {
    width: 50%;
    margin: 50px auto;
}

/* Bootstrap 4 text input with search icon */

.has-search .form-control {
    padding-left: 2.375rem;
}

.has-search .form-control-feedback {
    position: absolute;
    z-index: 2;
    display: block;
    width: 2.375rem;
    height: 2.375rem;
    line-height: 2.375rem;
    text-align: center;
    pointer-events: none;
    color: #aaa;
}
.cardarea{
    border: 2px solid
}
img{
    height:200px;
    border-radius: 5%;
}
    </style>
</head>
<body>
<div>
    <div class="main">
        <!-- Another variation with a button -->
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Search this blog">
          <div class="input-group-append">
            <button class="btn btn-secondary" type="button">
                <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      </div>
      </div>
      <div class="container-fluid">  
        <div class="row">
          <div class="col-3">col-3</div>
          <div class="col-9">
              <div class="row">
            <div class="col-3"><div class="card" >
                <img src="./img/bg-img/學男Q.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">活動標題</h5>
                  <p class="card-text">活動簡介</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
            </div>
            <div class="col-3"><div class="card" >
                <img src="./img/bg-img/速可達硬漢.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">活動標題</h5>
                  <p class="card-text">活動簡介</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
            </div>
            <div class="col-3"><div class="card">
                <img src="./img/bg-img/JCOLE.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">活動標題</h5>
                  <p class="card-text">活動簡介</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        </div>
    </div>
      <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
<script defer
		src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
</body>
</html>