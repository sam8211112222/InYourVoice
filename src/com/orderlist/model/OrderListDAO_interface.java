package com.orderlist.model;

import java.util.*;

public interface OrderListDAO_interface {
	public void insert(OrderListVO orderListVo);

	public void update(OrderListVO orderListVO);

	public void delete(String orderlist_id);

	public OrderListVO findByPrimaryKey(String orderlist_id);

	public List<OrderListVO> getAll();

	List<OrderListVO> findByOrderId(String order_Id);
	
	List<OrderListVO> findByproductId(String product_Id);
}