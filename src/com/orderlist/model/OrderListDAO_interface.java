package com.orderlist.model;

import java.util.*;



public interface OrderListDAO_interface {
	public OrderListVO insert(OrderListVO orderListVo);

	public void update(OrderListVO orderListVO);

	public void delete(String orderlist_id);
	
	public List<OrderListVO> insertList(List<OrderListVO> list);

	public OrderListVO findByPrimaryKey(String orderlist_id);

	public List<OrderListVO> getAll();

	List<OrderListVO> findByOrderId(String order_Id);
	
	List<OrderListVO> findByproductId(String product_Id);
	
	public void updeteReview(OrderListVO orderListVO);
	
	List<ReviewVO> findReviewByProductId(String productId);
	
	//Sam added
		List<OrderListVO> findByOrderIdB(String order_Id);
}
