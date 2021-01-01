$("#ticket_block").on("click", "button.minus", function(e) {
    let that = this;
    var now = $(this).next("input.ticket_buycount").val();
    if ($.isNumeric(now)) {
        if (parseInt(now) - 1 >= 0) { now--;}

        $(that).next("input.ticket_buycount").attr("value",now);
    }
})

$("#ticket_block").on("click", "button.plus", function(e) {
    let that = this;
    let now = $(this).prev("input.ticket_buycount").val();
    if ($.isNumeric(now)) {
        $(that).prev("input.ticket_buycount").attr("value", parseInt(now) + 1);
    }
})