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
	

	setInterval(function(){
		sessionStorage.audioCurrentTime = ap1.audio.currentTime;
	},1000);

	
	
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

        console.log($(this).attr("data-piece_href"));
        let path = $(this).attr("data-contextpath");

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
                console.log(data.length);
                console.log(data[0]);
                console.log(data[0].piece_id);
                console.log(data[0].piece_name);
                
                if(data.length!=0){
                    ap1.list.clear();
                    for(let i = 0; i < data.length ; i++){
                        ap1.list.add([{
                            name: data[i].piece_name,
                            artist: " ",
                            url: path + "/pieces/pieces.do?action=getPiece&piece_id=" + data[i].piece_id,
                            cover: path + "/album/album.do?action=getAlbumPhoto&album_id=" + data[i].album_id,
                            // lrc: $(this).attr("data-title")
                        }]);
                    }
                }
            }
        });

        

    })

})

	ap1 = new APlayer({
		element: document.getElementById("player1"),
		narrow: false,
		autoplay: true,
		showlrc: false,
		fixed: false,
		// volume: 0,
		mutex: true,
		listFolded: false,
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

