<%@ page import="com.band.model.BandVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="bandSvc" scope="page" class="com.band.model.BandService"></jsp:useBean>
<%
	String band_id = request.getParameter("band_id");
	BandVO bandVO = bandSvc.getOneBand(band_id);
	request.setAttribute("bandVO", bandVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>band_intro.jsp</title>
</head>
<body>

		<div class="container stick-container pb-4">
            <div class="border-top mb-6"></div>

            <div class="row justify-content-center">
                <div class="col-lg-10">
                    <div class="border-block text-read mb-4 p-4 p-7-lg">
                        <div class="maxw-720p ml-auto mr-auto">
                            <h2 class="mb-4">關於 ${bandVO.band_name}</h2>
                            <p>${bandVO.band_intro}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="text-center pb-5 d-none d-lg-block">
                <div class="adunit" data-adunit="sv_profile_footer_pc_970x250"></div>
            </div>

        </div>

</body>
</html>