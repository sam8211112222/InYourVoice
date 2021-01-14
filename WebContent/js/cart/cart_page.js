var el;

        $("tr").each(function () {
            var subtotal = parseFloat($(this).children(".price").text().replace("NT$","").replace(",",""));
            var amount = parseFloat($(this).children(".amount").children("input").val());
            var total = (Math.round( subtotal * amount * 100 ) / 100);
            $(this).children(".pricesubtotal").text(" NT$" + numberWithCommas(total));
        });

//        $(".amount > input").bind("change keyup", function () {
//            if (parseFloat($(this).val()) < 1) {
//                $(this).val(1);
//                el = $(this).parents("td").parents("tr").children(".remove");
//                el.addClass("hey");
//                setTimeout(function () {
//                    el.removeClass("hey");
//                });
//            }
//            var subtotal = parseFloat($(this).parents("td").parents("tr").children(".price").text());
//            var amount = parseFloat($(this).parents("td").parents("tr").children(".amount").children("input").val());
//            var total = (Math.round( subtotal * amount * 100 ) / 100);
//            $(this).parents("td").parents("tr").children(".pricesubtotal").text("NT$" +numberWithCommas(total));
//            changed();
//        });

//        $(".remove > div").click(function () {
//            $(this).parents("td").parents("tr").remove();
//            changed();
//        });
//
        function changed() {
            var subtotal = 0;
            $(".p").each(function () {
                subtotal = subtotal + parseFloat($(this).children(".pricesubtotal").text().replace("NT$", "").replace(",",""));
            });
            var totalpricesubtotal = (Math.round(subtotal * 100) / 100);
            $(".totalpricesubtotal").text(" NT$" + numberWithCommas(totalpricesubtotal));
            var a = (subtotal / 100 * 100) + parseFloat($(".shipping").text())
            var total = (Math.round(a * 100) / 100);
            $(".realtotal").text(total);
            $(".taxval").text("( NT$" + (Math.round(subtotal * 1) / 100) + ") ");
        }
        
        $("#checkout").click(function () {
           
        });

        changed();

        $("#expand").click(function () {
            $("#coolstuff").toggle();
        });
        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }