var el;

        $("tr").each(function () {
            var subtotal = parseFloat($(this).children(".price").text().replace("NT$", ""));
            var amount = parseFloat($(this).children(".amount").children("input").val());
            $(this).children(".pricesubtotal").text(" $" +
                (Math.round(
                    subtotal * amount * 100
                ) / 100));
        });

        $(".amount > input").bind("change keyup", function () {
            if (parseFloat($(this).val()) < 1) {
                $(this).val(1);
                el = $(this).parents("td").parents("tr").children(".remove");
                el.addClass("hey");
                setTimeout(function () {
                    el.removeClass("hey");
                });
            }
            var subtotal = parseFloat($(this).parents("td").parents("tr").children(".price").text().replace("$", ""));
            var amount = parseFloat($(this).parents("td").parents("tr").children(".amount").children("input").val());
            $(this).parents("td").parents("tr").children(".pricesubtotal").text("$" +
                (Math.round(
                    subtotal * amount * 100
                ) / 100));
            changed();
        });

        $(".remove > div").click(function () {
            $(this).parents("td").parents("tr").remove();
            changed();
        });

        function changed() {
            var subtotal = 0;
            $(".p").each(function () {
                subtotal = subtotal + parseFloat($(this).children(".pricesubtotal").text().replace("$", ""));
            });
            $(".totalpricesubtotal").text(" $" + (Math.round(subtotal * 100) / 100));
            var a = (subtotal / 100 * 100) + parseFloat($(".shipping").text())
            var total = (Math.round(a * 100) / 100);
            $(".realtotal").text(total);
            $(".taxval").text("( $" + (Math.round(subtotal * 1) / 100) + ") ");
        }
        
        $("#checkout").click(function () {
           
        });

        changed();

        $("#expand").click(function () {
            $("#coolstuff").toggle();
        });