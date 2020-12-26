$("input.upload").on("change", function () {

    $("ul.picture_list").html("");

    let reader = new FileReader(); // 用來讀取檔案的物件
    reader.readAsDataURL(this.files[0]);

    reader.addEventListener("load", function () {
        let li_html = `<img src="${reader.result}" class="preview" >`;
        $("ul.picture_list").html(li_html); //加進節點

    });
})