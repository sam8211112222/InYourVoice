$(function(){

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


})