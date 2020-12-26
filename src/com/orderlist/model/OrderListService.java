package com.orderlist.model;

import java.sql.Timestamp;
import java.util.List;

public class OrderListService {
	private OrderListDAO_interface dao;
	
	public OrderListService() {
		dao=new OrderListJDBCDAO();
	}
	
	public OrderListVO addOrderList(String order_id,String product_id,Integer orderlist_goods_amount,String orderlist_remarks,Integer review_score,String review_msg,Timestamp review_time,Integer review_hidden) {
		OrderListVO orderListVO=new OrderListVO();
		
		orderListVO.setOrder_id(order_id);
		orderListVO.setProduct_id(product_id);
		orderListVO.setOrderlist_goods_amount(orderlist_goods_amount);
		orderListVO.setOrderlist_remarks(orderlist_remarks);
		orderListVO.setReview_score(review_score);
		orderListVO.setReview_msg(review_msg);
		orderListVO.setReview_time(review_time);
		orderListVO.setReview_hidden(review_hidden);
	return orderListVO;
	}
	
	//�w�d�� Struts 2 �Ϊ�
	public void addOrderList(OrderListVO addOrderList) {
		dao.insert(addOrderList);
	}
	
	
	public OrderListVO updateOrderList( Integer orderlist_goods_amount,String orderlist_remarks,Integer review_score,String review_msg,Timestamp review_time,Integer review_hidden,String orderlist_id) {
		OrderListVO orderListVO=new OrderListVO();
		
		orderListVO.setOrderlist_goods_amount(orderlist_goods_amount);
		orderListVO.setOrderlist_remarks(orderlist_remarks);
		orderListVO.setReview_score(review_score);
		orderListVO.setReview_msg(review_msg);
		orderListVO.setReview_time(review_time);
		orderListVO.setReview_hidden(review_hidden);
		orderListVO.setOrderlist_id(orderlist_id);
		dao.update(orderListVO);
		return orderListVO;
	}
	
	//�w�d�� Struts 2 �Ϊ�
	public void updateOrderList(OrderListVO orderListVO) {
		dao.update(orderListVO);
	}
	
	
	public void deleteOrderList(String orderlist_id) {
		dao.delete(orderlist_id);
	}
	
	public OrderListVO getOneOrderList (String orderlist_id) {
		return dao.findByPrimaryKey(orderlist_id);
	}
	
	public List<OrderListVO> getAll(){
		return dao.getAll();
	}
	
	public List<OrderListVO> findByOrderId(String order_Id){
		return dao.findByOrderId(order_Id);
	}
	
	public List<OrderListVO> findByProductId(String product_Id){
		return dao.findByproductId(product_Id);
	}
	
}