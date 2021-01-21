package com.orders.model;

import java.sql.Timestamp;
import java.util.List;

public class OrdersService {
	private OrdersDAO_interface dao;

	public OrdersService() {
//		dao = new OrdersJDBCDAO();
		dao = new OrdersDAO();
	}
	
	public List<OrdersVO> getAll2() {
		try {
			return dao.getAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<OrdersVO> findByMemberId(String memberId){
		try {
			return dao.findByMemberId(memberId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public OrdersVO addOrders(String member_id, Integer order_status, Timestamp order_place_time, String order_name,
			String order_mail, String order_phone, Timestamp order_delivery_time, Timestamp order_received_time) {
		OrdersVO ordersVO = new OrdersVO();
		ordersVO.setMember_id(member_id);
		ordersVO.setOrder_status(order_status);
		ordersVO.setOrder_place_time(order_place_time);
		ordersVO.setOrder_name(order_name);
		ordersVO.setOrder_mail(order_mail);
		ordersVO.setOrder_phone(order_phone);
		ordersVO.setOrder_delivery_time(order_delivery_time);
		ordersVO.setOrder_received_time(order_received_time);
		dao.insert(ordersVO);

		return ordersVO;
	}
	//�w�d�� Struts 2 �Ϊ�
	public void addOrders(OrdersVO addOrders) {
		dao.insert(addOrders);
	}
	
	public OrdersVO updateOrders(String member_id,Integer order_status, Timestamp order_place_time, String order_name,
			String order_mail, String order_phone, Timestamp order_delivery_time, Timestamp order_received_time,String order_id) {
		OrdersVO ordersVO = new OrdersVO();
		
		ordersVO.setMember_id(member_id);
		ordersVO.setOrder_status(order_status);
		ordersVO.setOrder_place_time(order_place_time);
		ordersVO.setOrder_name(order_name);
		ordersVO.setOrder_mail(order_mail);
		ordersVO.setOrder_phone(order_phone);
		ordersVO.setOrder_delivery_time(order_delivery_time);
		ordersVO.setOrder_received_time(order_received_time);
		ordersVO.setOrder_id(order_id);
		dao.update(ordersVO);
		
		return ordersVO;
	}
	
	//�w�d�� Struts 2 �Ϊ�
	public void updateOrders(OrdersVO ordersVO) {
		dao.update(ordersVO);
	}
	
	public void deleteOrders(String order_id) {
		dao.delete(order_id);
	}
	
	public OrdersVO getOneOrders(String order_id) {
		return dao.findByPrimaryKey(order_id);
	}
	
	public List<OrdersVO> findByEmail(String order_mail) {
		return dao.findByEmail(order_mail);
	}
	
	public List<OrdersVO> getAll(){
		return dao.getAll();
	}
}