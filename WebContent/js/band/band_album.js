ap1 = new APlayer({
    element: document.getElementById("player1"),
    narrow: false,
    autoplay: true,
    showlrc: false,
    fixed: true,
    // volume: 0,
    mutex: true,
    listFolded: true,
    listMaxHeight: 90,
//    music: [{
//        title: "test",
//        author: "test",
//        url: "http://localhost:8081/TEA102G6/pieces/pieces.do?action=getPiece&piece_id=PIECES00150",
//        cover: "http://localhost:8081/TEA102G6/album/album.do?action=getAlbumPhoto&album_id=ALBUM00200",
//        lrc: ""
//    }, {
//        title: "test",
//        author: "test",
//        url: "http://localhost:8081/TEA102G6/pieces/pieces.do?action=getPiece&piece_id=PIECES00450",
//        cover: "http://localhost:8081/TEA102G6/album/album.do?action=getAlbumPhoto&album_id=ALBUM00200",
//        lrc: ""
//    }]
});




$(function(){
	
	ap1.on('emptied', function(){
		sessionStorage.audioCurrentTime = 0;
	})
	ap1.on('durationchange', function(){
		sessionStorage.audioCurrentTime = 0;
	})
	 
	ap1.on('loadeddata', function () {
	    console.log('loadeddata');
	    if(sessionStorage.audioCurrentTime){
			console.log("hi")
			ap1.seek(sessionStorage.audioCurrentTime);
			ap1.play();
		}
    });
    
    ap1.on('play', function(){
        console.log("play")
        let src = ap1.audio.src;
        let piece_id = src.substr(src.lastIndexOf("=")+1);
        let path = src.substr(0, src.lastIndexOf("?"));
//        console.log(ap1.audio.src)
//        console.log(ap1.audio.src.substr(ap1.audio.src.lastIndexOf("=")+1))
        console.log(piece_id);
        console.log(path);
        let url = path + "?action=piecePlayCount&piece_id=" + piece_id;
        console.log(url);
        
        $.ajax({
            url:  url,           // 資料請求的網址
            type: "GET",                            // GET | POST | PUT | DELETE | PATCH
            data: "",                         // 傳送資料到指定的 url
            // processData: false,
            // contentType : false,
            // cache: false,
            dataType: "json",                        // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                              //  request 可等待的毫秒數 | 0 代表不設定 timeout
            success: function(data){    
               
            }
        });
        
    })
	

	setInterval(function(){
		sessionStorage.audioCurrentTime = ap1.audio.currentTime;
    },1000);
    
    // 載入追蹤狀態
    function piece_status(){

        $("li.piece_card").each(function(index, item){
    
            let that = $(this);
            let piece_id = $(this).attr("data-piece_id");
            console.log("data-piece_id = " + piece_id);
            let path = $(this).attr("data-path");
            console.log("data-path = " + path);
            
            url = path + "/pieces/pieces.do?action=checkPieceFav&piece_id=" + piece_id;
            
            // 檢查歌曲追蹤狀態
            $.ajax({
                url:  url,           // 資料請求的網址
                type: "GET",                            // GET | POST | PUT | DELETE | PATCH
                data: "",                         // 傳送資料到指定的 url
                // processData: false,
                // contentType : false,
                // cache: false,
                dataType: "json",                        // 預期會接收到回傳資料的格式： json | xml | html
                timeout: 0,                              //  request 可等待的毫秒數 | 0 代表不設定 timeout
                success: function(data){    
                    console.log("favState = " + data);
                    that.attr("data-fav", data);
                    // return data;
                    if(data==-1){
                        that.find("div.piece_btn").css("display","none");
                    }else if(data==0){
                        that.find("div.piece_btn").html('<i class="far fa-heart"></i>');
                        that.addClass("addFav");
                    }else if(data==1){
                        that.find("div.piece_btn").html('<i class="fas fa-heart"></i>');
                        that.addClass("delFav");
                    }
                }
            })
        
            
        })
    }
    piece_status();

	
	
    $("li.piece_card").each(function(index, item){
    	
        ap1.list.add([{
            name: $(this).attr("data-title"),
            // artist: $(this).attr("data-title"),
            url: $(this).attr("data-url"),
            artist: " ",
            cover: $(this).attr("data-pic"),
            // lrc: $(this).attr("data-title")
        }]);
        
    })
    
    $("div.album_card").click(function(){

        // console.log($(this).attr("data-piece_href"));
        let path = $(this).attr("data-contextpath");
        let album_id = $(this).attr("data-album_id");
        $("#album_photo_main").find(".front").attr("style", `background-image: url(${path}/album/album.do?action=getAlbumPhoto&album_id=${album_id})`);

        $.ajax({
            url:  $(this).attr("data-piece_href"),           // 資料請求的網址
            type: "post",                            // GET | POST | PUT | DELETE | PATCH
            data: {},                         // 傳送資料到指定的 url
            // processData: false,
            // contentType : false,
            // cache: false,
            dataType: "json",                        // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                              //  request 可等待的毫秒數 | 0 代表不設定 timeout
            success: function(data){                 // request 成功取得回應後執行


                // console.log(data.length);
                // console.log(data[0]);
                // console.log(data[0].piece_id);
                // console.log(data[0].piece_name);
                
                if(data.length!=0){
                    // 先清空ul內容
                    $("ul.pieces_list").html("");
                    // 清空播放清單
                    ap1.list.clear();
                    for(let i = 0; i < data.length ; i++){
                        // 歌曲加入播放清單
                        ap1.list.add([{
                            name: data[i].piece_name,
                            artist: " ",
                            url: path + "/pieces/pieces.do?action=getPiece&piece_id=" + data[i].piece_id,
                            cover: path + "/album/album.do?action=getAlbumPhoto&album_id=" + data[i].album_id,
                            // lrc: $(this).attr("data-title")
                        }]);

                        // 產生歌曲清單
                        $("ul.pieces_list").append(`
                            <li class="piece_card" data-path="${path}" data-piece_id="${data[i].piece_id}"  data-title="${data[i].piece_name}" data-author="" data-url="${path}/pieces/pieces.do?action=getPiece&piece_id=${data[i].piece_id}" data-pic="${path}/album/album.do?action=getAlbumPhoto&album_id=${data[i].album_id}" data-lrc="" data-fav="">
                                        <div class="row piece_card justify-content-between">
                                            <div class="piece_order col-3 outer">${i+1}</div>
                                            <div class="piece_name col-3 outer">${data[i].piece_name}</div>
                                            <!-- <div class="piece_audio col-6"></div> -->
                                            <!-- <div class="piece_btn col-1"><i class="far fa-play-circle"></i></div>  -->
                                            <!-- <div class="piece_btn col-1"><i class="fas fa-plus-square"></i></div>  -->
                                            <div class="col-1"></div>
                                            <div class="piece_btn col-1"></div>
                                            <div class="col-1"></div>
                                        </div>
                                        <hr>
                                    </li>
                        
                        `);




                    }
                    piece_status();
                }
            }
        });

        $.ajax({
            url:  path + "/album/album.do?action=getAlbumIntro&album_id=" + album_id,           // 資料請求的網址
            type: "post",                            // GET | POST | PUT | DELETE | PATCH
            data: {},                         // 傳送資料到指定的 url
            // processData: false,
            // contentType : false,
            // cache: false,
            dataType: "json",                        // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                              //  request 可等待的毫秒數 | 0 代表不設定 timeout
            success: function(data){    
                console.log("here");  
                console.log(data);       // request 成功取得回應後執行
                console.log("here");  

                $("div.album_intro").text(data);
            }
        });

        

    })

    

    // 點擊歌曲清單可以換首
    $(document).on("click", "li.piece_card", function(){
        let piece_order = $(this).find("div.piece_order").text();
        console.log("piece_order = " + piece_order);
        ap1.list.switch(piece_order - 1);
    })

    // 追蹤相關
    $(document).on("click", "li.piece_card .piece_btn", function(e){
        e.stopPropagation();
        let that = $(this);
        let piece_id = $(this).closest("li.piece_card").attr("data-piece_id");
        console.log("data-piece_id = " + piece_id);
        let path = $(this).closest("li.piece_card").attr("data-path");
        console.log("data-path = " + path);
        let hasAddFav = $(this).closest("li.piece_card").hasClass("addFav");
        let hasDelFav = $(this).closest("li.piece_card").hasClass("delFav");
        let favStatus;
        if(hasAddFav){
            favStatus = "addFavorite";
        }else if(hasDelFav){
            favStatus = "delFavorite";
        }
        
        url = path + "/band/band.do?action=" + favStatus + "&piece_id=" + piece_id + "&type=piece";
        
        // 檢查歌曲追蹤狀態 及更新
        $.ajax({
            url:  url,           // 資料請求的網址
            type: "GET",                            // GET | POST | PUT | DELETE | PATCH
            data: "",                         // 傳送資料到指定的 url
            // processData: false,
            // contentType : false,
            // cache: false,
            dataType: "json",                        // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                              //  request 可等待的毫秒數 | 0 代表不設定 timeout
            success: function(data){    
                console.log("favState = " + data);
                // return data;
                if(data==-1){
                    alert("請先登入會員再使用追蹤功能!");
                }else if(data=="existed"){
                    alert("已新增過!");
                }else if(data=="added"){
                    alert("已新增!");
                    that.closest("li.piece_card").removeClass("addFav");
                    that.closest("li.piece_card").addClass("delFav");
                    that.html('<i class="fas fa-heart"></i>');
                }else if(data=="deleted"){
                    alert("已刪除!");
                    that.closest("li.piece_card").removeClass("delFav");
                    that.closest("li.piece_card").addClass("addFav");
                    that.html('<i class="far fa-heart"></i>');

                }
            }
        });

        // $(document).on("click", ".addfavorite", function(){
        //             $.ajax({
        //                 url: "<%=request.getContextPath()%>/band/band.do",           // 資料請求的網址
        //                 type: "POST",                  // GET | POST | PUT | DELETE | PATCH
        //                 data: {
        //                     action : "addFavorite",
        //                     type : "band",
        //                     band_id : "${bandVO.band_id}"
        //                 },                  // 傳送資料到指定的 url
        //                 dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
        //                 timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
                       
        //                 success: function(data){      // request 成功取得回應後執行
        //                     console.log(data);
        //                     if(data == "added"){
        //                     	console.log("data added");
        //                         $(".addfavorite").find("i.fas").addClass("fa-check");
        //                         $(".addfavorite").find("i.fas").removeClass("fa-plus");
        //                         $(".addfavorite").addClass("delfavorite");
        //                         $(".addfavorite").removeClass("addfavorite");
        //                     }
        //                 }
                        
        //             });
        //         })
                
        // $(document).on("click", ".delfavorite", function(){
        //     $.ajax({
        //         url: "<%=request.getContextPath()%>/band/band.do",           // 資料請求的網址
        //         type: "POST",                  // GET | POST | PUT | DELETE | PATCH
        //         data: {
        //             action : "delFavorite",
        //             type : "band",
        //             band_id : "${bandVO.band_id}"
        //         },                  // 傳送資料到指定的 url
        //         dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
        //         timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
                
        //         success: function(data){      // request 成功取得回應後執行
        //             console.log(data);
        //             if(data == "deleted"){
        //                 console.log("data deleted");
        //                 $(".delfavorite").find("i.fas").addClass("fa-plus");
        //                 $(".delfavorite").find("i.fas").removeClass("fa-check");
        //                 $(".delfavorite").addClass("addfavorite");
        //                 $(".delfavorite").removeClass("delfavorite");
        //             }
        //         }
                
        //     });
        // })
    })



})


    
    

