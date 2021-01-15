package com.eventorderlist.model;

import java.util.List;
import java.util.stream.Collectors;

public class EventOrderListService {
	
	EventOrderListDAO dao = null;
	
	public EventOrderListService() {
		dao = new EventOrderListJNDIDAO();
//		dao = new EventOrderListJDBCDAO();
	}
	
	public List<EventOrderListVO> getAll(){
		return dao.getAll();
	}
	
	public EventOrderListVO getOneByOrderListId(String orderlist_id) {
		return dao.findByPrimaryKey(orderlist_id);
	}
	
	public void updateOrderStatus(Integer status,EventOrderListVO eventOrderListVO) {
		eventOrderListVO.setOrderlist_status(status);
		dao.update(eventOrderListVO);
	}
	
	public List<EventOrderListVO> getListByEventOrderId(String event_order_id){
		return dao.getAll().stream()
		.filter(e -> e.getEvent_order_id().equals(event_order_id))
		.collect(Collectors.toList());
	}
}
