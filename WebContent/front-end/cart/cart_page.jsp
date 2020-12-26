<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>購物車清單</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
	integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
	integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
	crossorigin="anonymous"></script>
<script src="<%=request.getContextPath()%>/js/cart/cart_page.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/cart/cart_page.css">

</head>
<h1 class="cart_title">購物車清單</h1>
<body>

	<div id="container">

		<div>
			<table>
				<tr style="text-align: center;">
					<td>商品照片</td>
					<td>商品名稱</td>
					<td>商品單價</td>
					<td>數量</td>
					<td>小計</td>
					<td></td>
				</tr>
				<tr class="p">

					<td class="image"><img
						src="http://cdn2.ubergizmo.com/wp-content/uploads/2012/09/ipod-touch-5g.jpg" />
					</td>
					<td class="name">iPod touch</td>
					<td class="price">$50.0</td>
					<td class="amount"><input type="number" value="1" min="0" /></td>
					<td class="pricesubtotal"></td>
					<td class="remove">
						<div>&times</div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td>總價:</td>
					<td class="totalpricesubtotal"></td>
					<td></td>
				</tr>
			</table>
		</div>
		<hr size="10px" align="center" width="100%" Color="gray">
		<table>

			<tr>
				<td style="border-top: 1px solid white" colspan="6"><br /> <span
					class="taxval"></span> <span class="shipping">10</span>$ shipping:<br />
					<span class="big">總金額: $<span class="realtotal">0</span></span></td>
			</tr>
		</table>


		<div id="continue_shopping">
			繼續購物<span> &rarr;</span>
		</div>
		<div id="checkout">
			立即結帳<span> &rarr;</span>
		</div>
	</div>


	<script>
		var el;

		$("tr").each(
				function() {
					var subtotal = parseFloat($(this).children(".price").text()
							.replace("$", ""));
					var amount = parseFloat($(this).children(".amount")
							.children("input").val());
					$(this).children(".pricesubtotal")
							.text(
									"$"
											+ (Math.round(subtotal * amount
													* 100) / 100).toFixed(2));
				});

		$(".amount > input").bind(
				"change keyup",
				function() {
					if (parseFloat($(this).val()) < 1) {
						$(this).val(1);
						el = $(this).parents("td").parents("tr").children(
								".remove");
						el.addClass("hey");
						setTimeout(function() {
							el.removeClass("hey");
						}, 200);
					}
					var subtotal = parseFloat($(this).parents("td").parents(
							"tr").children(".price").text().replace("$", ""));
					var amount = parseFloat($(this).parents("td").parents("tr")
							.children(".amount").children("input").val());
					$(this).parents("td").parents("tr").children(
							".pricesubtotal")
							.text(
									"$"
											+ (Math.round(subtotal * amount
													* 100) / 100).toFixed(2));
					changed();
				});

		$(".remove > div").click(function() {
			$(this).parents("td").parents("tr").remove();
			changed();
		});

		function changed() {
			var subtotal = 0;
			$(".p").each(
					function() {
						subtotal = subtotal
								+ parseFloat($(this).children(".pricesubtotal")
										.text().replace("$", ""));
					});
			$(".totalpricesubtotal").text(
					"$" + (Math.round(subtotal * 100) / 100).toFixed(2));
			var a = (subtotal / 100 * 100) + parseFloat($(".shipping").text())
			var total = (Math.round(a * 100) / 100).toFixed(2);
			$(".realtotal").text(total);
			$(".taxval").text(
					"($" + (Math.round(subtotal * 1) / 100).toFixed(2) + ") ");
		}

		$("#continue_shopping").click(function() {
			alert("And that's $" + $(".realtotal").text() + ", please.");
		});

		$("#checkout").click(function() {
			alert("And that's $" + $(".realtotal").text() + ", please.");
		});

		changed();

		$("#expand").click(function() {
			$("#coolstuff").toggle();
		});
	</script>
</body>

</html>