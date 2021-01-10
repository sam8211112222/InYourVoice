$(function(){

    $("#album_content").load($("#album_btn").attr("data-href")+"/front-end/band/band_album.jsp", { band_id : $("#album_btn").attr("data-band_id")}, function(result){
        //alert(result);
        //將被載入頁的JavaScript載入到本頁執行
        $result = $(result); 
        $result.find("script").appendTo('#album_content');
    }).hide().fadeIn('slow'); 


    $("#album_btn").on("click", function(e){
        $(".included_content").addClass("content_hide");
        $("#album_content").removeClass("content_hide");
    });

    $("#product_btn").on("click", function(e){

        let band_id = $(this).attr("data-band_id");

        $(".included_content").addClass("content_hide");
        $("#product_content").removeClass("content_hide");

        e.preventDefault();
        console.log("jquery.load");
        let href = $(this).attr("data-href") + `/product/YUproductServlet?action=show_me_band&band_id=${band_id}`;

        $("#product_content").load(href, { band_id : $(this).attr("data-band_id"),action : "show_me_band"}, function(result){
        	//alert(result);
        	//將被載入頁的JavaScript載入到本頁執行
        	$result = $(result); 
        	$result.find("script").appendTo('#product_content');
    	}).hide().fadeIn('slow'); 
    });

    $("#intro_btn").on("click", function(e){

        $(".included_content").addClass("content_hide");
        $("#intro_content").removeClass("content_hide");

        e.preventDefault();
        console.log("jquery.load");
        let href = $(this).attr("data-href") + "/front-end/band/band_intro.jsp";

        $("#intro_content").load(href, { band_id : $(this).attr("data-band_id")}, function(result){
        	//alert(result);
        	//將被載入頁的JavaScript載入到本頁執行
        	$result = $(result); 
        	$result.find("script").appendTo('#intro_content');
    	}).hide().fadeIn('slow');
    });

    $(document).on("click", "a.nav-link", function(){
        $(".nav-link").each(function(index, item){
            $(this).removeClass("active");
        })
        $(this).addClass("active");
    });
    
    $("#event_btn").on("click", function(e){

        $(".included_content").addClass("content_hide");
        $("#event_content").removeClass("content_hide");

        e.preventDefault();
        console.log("jquery.load");
        let href = $(this).attr("data-href") + "/event/EventServlet";

        $("#event_content").load(href, { band_id : $(this).attr("data-band_id"),action : "band_event"}, function(result){
        	//alert(result);
        	//將被載入頁的JavaScript載入到本頁執行
        	$result = $(result); 
        	$result.find("script").appendTo('#event_content');
    	}).hide().fadeIn('slow'); 
    });
     
    
      
    
    
});

