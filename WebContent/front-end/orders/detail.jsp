<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單詳情</title>
</head>
<body>
訂單編號:${order.order_id}<br/>
下單時間:${order.order_place_time}<br/>
買家名稱:${order.order_name}<br/>
買家信箱:${order.order_mail}<br/>
買家電話:${order.order_phone}<br/>


訂單明細:<br/>
	<Table width="60%" border="1">
		<tbody>
			<tr>
				<th align="left">商品編號</th>
				<th align="left">數量</th>
				<th align="left">評價</th>
				<th align="left">備註</th>
			</tr>
			<c:forEach items="${details}" var="detailVo">
				<tr>
					<td align="left">${detailVo.product_id}</td>
					<td align="left">${detailVo.orderlist_goods_amount}</td>
					<td align="left">${detailVo.review_msg}</td>
					<td align="left">${detailVo.orderlist_remarks}</td>
				</tr>
			</c:forEach>
		</tbody>
	</Table>


</body>
</html>