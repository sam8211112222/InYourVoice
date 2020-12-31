$(function(){

    // 展開專輯
    $(document).on("click", ".my_folder_arrow", function(){
        console.log("click")
        $(this).closest(".arrow ").find(".my_folder_arrow").toggleClass("hide");
        $(this).closest(".my_album_card").find(".my_album_body").toggleClass("hide");
    })

    // 全選按鈕
    $(".check_all").on("click", function(){

        if($(".check_all").prop("checked")){
            $(".check_one_album").each(function(){
                $(this).prop("checked",true);
            })
            $(".check_one_piece").each(function(){
                $(this).prop("checked",true);
            })
        }else{
            $(".check_one_album").each(function(){
                $(this).prop("checked",false);
            })
            $(".check_one_piece").each(function(){
                $(this).prop("checked",false);
            })
        }
    })

    // 全選一張專輯
    $(".check_one_album").on("click", function(){

        if($(this).prop("checked")){
            $(this).closest(".my_album_card").find(".check_one_piece").each(function(){
                $(this).prop("checked",true);
            })
        }else{
            $(this).closest(".my_album_card").find(".check_one_piece").each(function(){
                $(this).prop("checked",false);
            })
        }
    })

    //刪除專輯按鈕
    $(document).on("click", ".delete_album_btn", function(){
        if(confirm("確定要刪除專輯嗎?")==true){
            let album_id = $(this).closest(".my_album_card").attr("data-album_id");
            let path = $(".add_new_album_btn").attr("data-path");
            deleteAlbum(path, album_id);
            $(this).closest(".my_album_card").remove();

        }
    })

    //刪除歌曲按鈕
    $(document).on("click", ".delete_piece_btn", function(){
        if(confirm("確定要刪除歌曲嗎?")==true){

            let piece_id = $(this).closest(".my_album_body_item").attr("data-piece_id");
            let path = $(".add_new_album_btn").attr("data-path");
            deletePiece(path, piece_id);
            $(this).closest(".my_album_body_item").remove();
        }
    })

    // 編輯專輯按鈕
    $(document).on("click", ".update_album_btn", function(){
        let btn_box = $(this).closest(".btn_box");
        btn_box.find(".update_album_btn").addClass("hide");
        btn_box.find(".add_piece_btn").removeClass("hide");
        btn_box.find(".delete_album_btn").removeClass("hide");
        btn_box.find(".cancel_update_album_btn").removeClass("hide");
        btn_box.find(".confirm_update_album_btn").removeClass("hide");
        // 專輯名稱可編輯
        btn_box.closest(".album_header").find(".album_name span.name_text").addClass("hide");
        btn_box.closest(".album_header").find(".album_name input.name_text").removeClass("hide");
        // 專輯介紹可編輯
        btn_box.closest(".my_album_card").find(".my_album_body_intro span.name_text").addClass("hide");
        btn_box.closest(".my_album_card").find(".my_album_body_intro textarea.name_text").removeClass("hide");
        
    })

    // 取消編輯專輯按鈕
    $(document).on("click", ".cancel_update_album_btn", function(){
        let btn_box = $(this).closest(".btn_box");
        // 切換按鈕顯示
        btn_box.find(".update_album_btn").removeClass("hide");
        // btn_box.find(".add_piece_btn").addClass("hide");
        btn_box.find(".delete_album_btn").addClass("hide");
        btn_box.find(".cancel_update_album_btn").addClass("hide");
        btn_box.find(".confirm_update_album_btn").addClass("hide");
        // 切換圖片顯示
        btn_box.find(".album_header").find(".album_photo_thumb").removeClass("hide");
        btn_box.find(".album_header").find(".preview").addClass("hide");
        // 取消編輯時，圖片內容應以原本為主
        $(this).closest(".album_header").find("img.preview").attr("src", $(this).closest(".album_header").find("img.album_photo_thumb").attr("src"))
        $(this).closest(".album_header").find("img.album_photo_thumb").removeClass("hide");
        $(this).closest(".album_header").find("img.preview").addClass("hide");
        // 專輯名稱不可編輯，恢復原本的內容
        btn_box.closest(".album_header").find(".album_name input.name_text").val(btn_box.closest(".album_header").find(".album_name span.name_text").text());
        btn_box.closest(".album_header").find(".album_name span.name_text").removeClass("hide");
        btn_box.closest(".album_header").find(".album_name input.name_text").addClass("hide");
        // 專輯介紹不可編輯，恢復原本的內容
        btn_box.closest(".my_album_card").find(".my_album_body_intro textarea.name_text").val(btn_box.closest(".my_album_card").find(".my_album_body_intro span.name_text").text());
        btn_box.closest(".my_album_card").find(".my_album_body_intro span.name_text").removeClass("hide");
        btn_box.closest(".my_album_card").find(".my_album_body_intro textarea.name_text").addClass("hide");
        

    })

    // 確定編輯專輯按鈕
    $(document).on("click", ".confirm_update_album_btn", function(){
        let btn_box = $(this).closest(".btn_box");

        // 傳送資料
        let path = $(".add_new_album_btn").attr("data-path");
        let album_id = btn_box.closest(".my_album_card").attr("data-album_id");
        let band_id = btn_box.closest(".my_album_card").attr("data-band_id");
        let album_name = btn_box.closest(".my_album_card").find(".album_name input").val().trim();
        let album_intro = btn_box.closest(".my_album_card").find(".my_album_body_intro textarea").val().trim();
        let album_photo = btn_box.closest(".my_album_card").find(".album_photo_file")[0].files[0];
        // let album_photo_preview = btn_box.closest(".my_album_card").find("img.preview").attr("src");
        let album_photo_thumb = btn_box.closest(".my_album_card").find("img.album_photo_thumb").attr("src");
        // 若是新增的專輯，拿到資料庫的回傳pk後，要替代album_id的地方
        let album_id_tag1 = btn_box.closest(".my_album_card");
        let album_id_tag2 = btn_box.closest(".my_album_card").find(".update_photo");
        let album_id_tag3 = btn_box.closest(".my_album_card").find(".album_photo_file");

        // 用album_id確認是否為new
        let is_new;
        if(album_id.startsWith("new")){
            is_new = "new";
        }else{
            is_new = "new";
        }

        // 如果trim完都非空白
        if(album_name!="" && album_intro!="" && (album_photo_thumb!="" || album_photo!=null)){
            confirm_album(is_new, path, album_id, band_id, album_name, album_intro, album_photo, album_id_tag1, album_id_tag2, album_id_tag3);

            // 切換按鈕顯示
            btn_box.find(".update_album_btn").removeClass("hide");
            btn_box.find(".add_piece_btn").removeClass("hide");
            btn_box.find(".delete_album_btn").addClass("hide");
            btn_box.find(".cancel_update_album_btn").addClass("hide");
            btn_box.find(".confirm_update_album_btn").addClass("hide");
            // 切換圖片顯示
            btn_box.find(".album_header").find(".album_photo_thumb").removeClass("hide");
            btn_box.find(".album_header").find(".preview").addClass("hide");
            btn_box.find(".album_header").find(".album_photo_thumb").attr("src", btn_box.find(".album_header").find(".preview").attr("src"));
            // 確認編輯時，圖片內容應以新的為主
            if($(this).closest(".album_header").find("img.preview").attr("src")!=""){
                $(this).closest(".album_header").find("img.album_photo_thumb").attr("src", $(this).closest(".album_header").find("img.preview").attr("src"));
            }
            $(this).closest(".album_header").find("img.album_photo_thumb").removeClass("hide");
            $(this).closest(".album_header").find("img.preview").addClass("hide");
            // 專輯名稱不可編輯，內容更新
            if(btn_box.closest(".album_header").find(".album_name input.name_text").val().trim()!=""){
                btn_box.closest(".album_header").find(".album_name span.name_text").text(btn_box.closest(".album_header").find(".album_name input.name_text").val());
            }else{
                btn_box.closest(".album_header").find(".album_name input.name_text").val(btn_box.closest(".album_header").find(".album_name span.name_text").text());
            }
            btn_box.closest(".album_header").find(".album_name span.name_text").removeClass("hide");
            btn_box.closest(".album_header").find(".album_name input.name_text").addClass("hide");
            // 專輯介紹不可編輯，內容更新
            btn_box.closest(".my_album_card").find(".my_album_body_intro span.name_text").text(btn_box.closest(".my_album_card").find(".my_album_body_intro textarea.name_text").val())
            btn_box.closest(".my_album_card").find(".my_album_body_intro span.name_text").removeClass("hide");
            btn_box.closest(".my_album_card").find(".my_album_body_intro textarea.name_text").addClass("hide");

        }else{
            let errMsg = "";
            if(album_photo_thumb=="" && album_photo==null){
                errMsg += "請選擇專輯圖片";
            }

            if(album_name==""){
                errMsg += "請輸入專輯名稱";
            }
            if(album_intro==""){
                errMsg += "請輸入專輯介紹";
            }
            alert(errMsg);
        }

        

    })

    // 編輯歌曲按鈕
    $(document).on("click", ".update_piece_btn", function(){
        let btn_box = $(this).closest(".btn_box");
        btn_box.find(".update_piece_btn").addClass("hide");
        btn_box.find(".delete_piece_btn").removeClass("hide");
        btn_box.find(".cancel_update_piece_btn").removeClass("hide");
        btn_box.find(".confirm_update_piece_btn").removeClass("hide");
        // 顯示選擇檔案
        btn_box.closest(".my_album_body_item").find(".upload_audio").removeClass("hide");
        // 可編輯歌曲名稱
        btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").addClass("hide");
        btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").removeClass("hide");
    })

    // 取消編輯歌曲按鈕
    $(document).on("click", ".cancel_update_piece_btn", function(){
        let btn_box = $(this).closest(".btn_box");
        btn_box.find(".update_piece_btn").removeClass("hide");
        btn_box.find(".delete_piece_btn").addClass("hide");
        btn_box.find(".cancel_update_piece_btn").addClass("hide");
        btn_box.find(".confirm_update_piece_btn").addClass("hide");
        // 可編輯歌曲名稱，恢復原本的內容
        btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").val(btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").text());
        btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").removeClass("hide");
        btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").addClass("hide");
        // 隱藏選擇檔案
        btn_box.closest(".my_album_body_item").find(".upload_audio").addClass("hide");

    })

    // 確認編輯歌曲按鈕
    $(document).on("click", ".confirm_update_piece_btn", function(){

        let btn_box = $(this).closest(".btn_box");
        let path = $(".add_new_album_btn").attr("data-path");
        let album_id = $(this).closest(".my_album_card").attr("data-album_id");
        let piece_id = $(this).closest(".my_album_body_item").attr("data-piece_id");
        let band_id = $(this).closest(".my_album_card").attr("data-band_id");
        let my_album_body_item_tag = $(this).closest(".my_album_body_item");

        // 確認欄位不為空才可以按確認
        let piece_name = $(this).closest(".my_album_body_item").find("input.name_text").val().trim();
        let piece_file = $(this).closest(".my_album_body_item").find("input.upload_audio")[0].files[0];
        // 用piece_id確認是否為new
        let is_new;
        if(piece_id.startsWith("new")){
            is_new = "new";
        }else{
            is_new = "";
        }
        // 如果trim完都非空白
        if(piece_name!="" && (is_new == "" || piece_file!=null)){

            confirm_piece(is_new, path, album_id, piece_name, piece_file, piece_id, band_id, my_album_body_item_tag);

            btn_box.find(".update_piece_btn").removeClass("hide");
            btn_box.find(".delete_piece_btn").addClass("hide");
            btn_box.find(".cancel_update_piece_btn").addClass("hide");
            btn_box.find(".confirm_update_piece_btn").addClass("hide");
            // 可編輯歌曲名稱，內容更新
            if(btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").val().trim()!=""){
                btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").text(btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").val());
            }else{
                btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").val(btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").text());
            }
            btn_box.closest(".my_album_body_item").find(".piece_name span.name_text").removeClass("hide");
            btn_box.closest(".my_album_body_item").find(".piece_name input.name_text").addClass("hide");
            // 隱藏選擇檔案
            btn_box.closest(".my_album_body_item").find(".upload_audio").addClass("hide");

        }else{
            let errMsg = "";
            if(piece_name==""){
                errMsg += "請輸入歌曲名稱";
            }

            if(piece_file==null){
                errMsg += "請選擇歌曲檔案";
            }
         
            alert(errMsg);
        }
        

    })

    // 上傳專輯圖片
    $(document).on("click", ".update_photo", function(e){
        // 非編輯狀態要阻擋預設行為
        if(!$(this).closest(".album_header").find(".update_album_btn").hasClass("hide")){
            e.preventDefault();
        }
        
    })

    // 上傳專輯圖片預覽
    $(document).on("change", ".album_photo_file", function(e){
        let that = $(this);
        let reader = new FileReader();
        reader.readAsDataURL(this.files[0]);
        reader.addEventListener("load", function(e) {
            that.closest(".update_photo").find("img.preview").attr("src", reader.result);
        })
        $(this).closest(".update_photo").find(".album_photo_thumb").addClass("hide");
        $(this).closest(".update_photo").find(".preview").removeClass("hide");
    })

    // 新增專輯
    var i = 0;
    let path = $(".add_new_album_btn").attr("data-path");
    let band_id = $(".my_album_card").attr("data-band_id");
    $(".add_new_album_btn").on("click", function(){
        console.log("add_new_album")
        $(".album_area").prepend(`
            <!-- for each album -->
            <div class="row col-12 my_album_card justify-content-center" data-album_id="new_album_${i}" data-band_id="${band_id}">
                <div class="row col-12 album_header justify-content-end">
                    <div class="arrow col-1">
                        <i class="fa-2x fas fa-caret-right my_folder_arrow hide"></i>
                        <i class="fa-2x fas fa-caret-down my_folder_arrow"></i>
                    </div>
                    <div class="album_photo col-2">
                        <label class="update_photo" for="new_album_${i}">
                            <img class="album_photo_thumb hide" src="">
                            <!-- <img class="preview" src="<%= request.getContextPath() %>/images/choose.jfif" alt=""> -->
                            <img class="preview" src="${path}/images/無圖片.png" alt="">
                            <!-- <div class="choose_pic hide">請選擇圖片</div> -->
                            <input type="file" id="new_album_${i++}" class="album_photo_file hide" accept="image/*">
                        </label>
                    </div>
                    <div class="album_name col-4">
                        <span class="name_text hide" placeholder="請輸入專輯名稱"></span>
                        <input type="text" class="name_text" placeholder="請輸入專輯名稱">

                    </div>
                    <!-- <div class="album_status col-2">下架中<input type="checkbox" class="check_one_album" name="check_album"></div> -->
                    <div class="btn_box col-3">
                                    <button type="button" class="update_album_btn btn btn-primary hide"><i class="fas fa-edit"></i></button>

                                    
                                    <button type="button" class="cancel_update_album_btn btn btn-primary hide"><i class="fas fa-ban"></i></button>

                                    <button type="button" class="add_piece_btn btn btn-primary hide"><i class="fas fa-plus"></i>歌曲</button>
                                    
                                    <button type="button" class="delete_album_btn btn btn-primary"><i class="fas fa-trash-alt"></i></button>
                                    <button type="button" class="confirm_update_album_btn btn btn-primary"><i class="fas fa-check"></i></button>
                    </div>
                </div>
                    <!-- div.arrow.onclick  open -->
                <div id="my_album_body" class="row col-10 my_album_body justify-content-end">
                    <div class="row col-12 my_album_body_intro justify-content-around">
                        <div class="col-3">專輯介紹</div>
                        <div class="col-9">
                            <span class="name_text hide">請輸入專輯介紹</span>
                            <!-- <input type="text" class="name_text" value=""> -->
                            <textarea class="name_text" cols="30" rows="5">請輸入專輯介紹</textarea>
                        </div>

                        <hr size="1px" width="100%">
                    </div>
                </div>
            </div>
            <!-- for each album -->
        `)
    })

    // 新增歌曲
    let piece_count = 0;
    $(document).on("click", ".add_piece_btn", function(){

        $(this).closest(".my_album_card").find(".my_album_body_intro").after(`
            <!-- for each piece -->
            <div class="row col-12 my_album_body_item justify-content-center" data-piece_id="new_piece_${piece_count}"  data-album_id="">
                <div class="piece_name col-5">
                    <span class="name_text hide"></span>
                    <input type="text" class="name_text" value="" placeholder="請輸入歌曲名稱">
                    <input style="font-size: 12px; margin-left: 0px;" type="file" name="piece" class="upload_audio" accept="audio/*">
                </div>                            
                <!-- <div class="piece_status col-4">下架中<input type="checkbox" class="check_one_piece" name="check_piece"></div> -->
                <div class="col-3 btn_box">
                    <button type="button" class="update_piece_btn btn btn-primary hide"><i class="fas fa-edit"></i></button>
                    <button type="button" class="delete_piece_btn btn btn-primary"><i class="fas fa-trash-alt"></i></button>
                    <button type="button" class="cancel_update_piece_btn btn btn-primary hide"><i class="fas fa-ban"></i></button>
                    <button type="button" class="confirm_update_piece_btn btn btn-primary"><i class="fas fa-check"></i></button>
                </div>
                <div class="col-3"></div>
                <div class="col-9"></div>
                <hr size="1px" width="100%">
            </div>
            <!-- for each piece -->  
        `)
    })


})


// $.ajax({
//     url: "",           // 資料請求的網址
//     type: "",                  // GET | POST | PUT | DELETE | PATCH
//     data: "",                  // 傳送資料到指定的 url
//     dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
//     timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
//     beforeSend: function(){       // 在 request 發送之前執行
//     },
//     headers: {                    // request 如果有表頭資料想要設定的話
//       // "X-CSRF-Token":"abcde"   // 參考寫法
//     },
//     statusCode: {                 // 狀態碼
//       200: function (res) {
//       },
//       404: function (res) {
//       },
//       500: function (res) {
//       }
//     },
//     success: function(data){      // request 成功取得回應後執行
//       console.log(data);
//     },
//     error: function(xhr){         // request 發生錯誤的話執行
//       console.log(xhr);
//     },
//     complete: function(xhr){      // request 完成之後執行(在 success / error 事件之後執行)
//       console.log(xhr);
//     }
//   });


function confirm_album(is_new, path, album_id, band_id, album_name, album_intro, album_photo, album_id_tag1, album_id_tag2, album_id_tag3){
    console.log(path);
    console.log(album_id);
    console.log(album_name);
    console.log(album_intro);
    console.log(album_photo);
    let action = "updateAlbum";

    let data = new FormData();
    data.append("action", action);
    data.append("album_id", album_id);
    data.append("band_id", band_id);
    data.append("album_name", album_name);
    data.append("album_intro", album_intro);
    data.append("album_photo", album_photo);
    let url = path + "/album/album.do";

    $.ajax({
        url: url,           // 資料請求的網址
        type: "POST",                  // GET | POST | PUT | DELETE | PATCH
        data: data,                  // 傳送資料到指定的 url
        dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
        timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
        contentType: false,  // 若有傳送檔案，設定 false，不設定任何 Content-Type header；預設是 application/x-www-form-urlencoded
        processData: false,  // 不用額外處理資料。例：如果是 GET，預設會將 data 物件資料字串化，放到網址
        cache: false,        // 避免有圖片 cache 狀況
        
        success: function(data){      // request 成功取得回應後執行
            console.log("success");
            console.log(data);
            console.log(data.album_id);
            // 如果是新增專輯時(is_new == "new")，新增的要把album_id置入原本的html裡面
            console.log(is_new)
            if(is_new == "new"){
                console.log(album_id_tag1.attr("data-album_id"));
                console.log(album_id_tag2.attr("for"));
                console.log(album_id_tag3.attr("id"));
                album_id_tag1.attr("data-album_id", data.album_id);
                album_id_tag2.attr("for", data.album_id);
                album_id_tag3.attr("id", data.album_id);
                console.log(album_id_tag1.attr("data-album_id"));
                console.log(album_id_tag2.attr("for"));
                console.log(album_id_tag3.attr("id"));

            }
            
        },
        
      });
    
}


function deleteAlbum(path, album_id){

    let url = path + "/album/album.do"; 
    let action = "deleteAlbum";

    $.ajax({
            url: url,           // 資料請求的網址
            type: "POST",                  // GET | POST | PUT | DELETE | PATCH
            data: { "action" : action,
                    "album_id" : album_id},                  // 傳送資料到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
            
            success: function(data){      // request 成功取得回應後執行
              console.log(data);
            }
            
          });
}


function confirm_piece(is_new, path, album_id, piece_name, piece_file, piece_id, band_id, my_album_body_item_tag){

    let url = path + "/pieces/pieces.do";
    let action = "updatePiece";

    let data = new FormData();
    data.append("action", action);
    data.append("album_id", album_id);
    data.append("piece_id", piece_id);
    data.append("piece_name", piece_name);
    data.append("piece_file", piece_file);
    data.append("piece_last_editor", band_id);

    $.ajax({
            url: url,           // 資料請求的網址
            type: "POST",                  // GET | POST | PUT | DELETE | PATCH
            data: data,                  // 傳送資料到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
            contentType: false,  // 若有傳送檔案，設定 false，不設定任何 Content-Type header；預設是 application/x-www-form-urlencoded
            processData: false,  // 不用額外處理資料。例：如果是 GET，預設會將 data 物件資料字串化，放到網址
            cache: false,        // 避免有圖片 cache 狀況
            
            success: function(data){      // request 成功取得回應後執行
              console.log(data);
              console.log(data.album_id);
              console.log(data.piece_id);
              if(is_new == "new"){
                console.log(my_album_body_item_tag.attr("data-album_id"));
                console.log(my_album_body_item_tag.attr("data-piece_id"));

                my_album_body_item_tag.attr("data-album_id", data.album_id);
                my_album_body_item_tag.attr("data-piece_id", data.piece_id);

                console.log(my_album_body_item_tag.attr("data-album_id"));
                console.log(my_album_body_item_tag.attr("data-piece_id"));

              }


            },
            
          });

}


function deletePiece(path, piece_id){

    let url = path + "/pieces/pieces.do"; 
    let action = "deletePiece";

    $.ajax({
            url: url,           // 資料請求的網址
            type: "POST",                  // GET | POST | PUT | DELETE | PATCH
            data: { "action" : action,
                    "piece_id" : piece_id},                  // 傳送資料到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
            
            success: function(data){      // request 成功取得回應後執行
              console.log(data);
            }
            
          });
}
