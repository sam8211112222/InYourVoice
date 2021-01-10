<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 	productDAO.getAll(); -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品詳情(鈺涵)</title>

</head>
<body>
	<br /> 商品照片:
	<br /> 商品名稱:${productVO.product_name}
	<br /> 商品描述:${productVO.product_intro}
	<br /> 商品詳情:${productVO.product_detail}
	<br /> 商品價格:${productVO.product_price}
	<br />
	<Table width="60%" border="1">
		<tbody>
			<tr>
				<th>商品評價分數</th>
				<th>商品評價</th>
			</tr>
			<c:forEach items="${orderListVO}" var="orderListVO">
				<tr>
					<td>${orderListVO.review_score}</td>
					<td>${orderListVO.review_msg}</td>
				</tr>
			</c:forEach>
		</tbody>
	</Table>

</body>
</html>