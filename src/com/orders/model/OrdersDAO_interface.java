package com.orders.model;

import java.util.*;

import com.orderlist.model.OrderListVO;



public interface OrdersDAO_interface {
	//�s�W
	public OrdersVO insert(OrdersVO ordersVO);
	
	//�ק��s
	public void update(OrdersVO ordersVO);

	//�R��
	public void delete(String order_id);
	
	//��D��
	public OrdersVO findByPrimaryKey(String order_id);
	
	//用email查詢
	public List<OrdersVO> findByEmail(String order_mail);
	//�d�ߥ���
	public List<OrdersVO> getAll();
	
	List<OrdersVO> findByMemberId(String memberId);
	
	 
}
