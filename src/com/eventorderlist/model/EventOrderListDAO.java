package com.eventorderlist.model;

import java.util.List;

import com.event.model.EventVO;

public interface EventOrderListDAO {

	public void insert(EventOrderListVO eventOrderListVO);

	public void update(EventOrderListVO eventOrderListVO);

	public void delete(String orderlist_id);

	public EventOrderListVO findByPrimaryKey(String orderlist_id);

	public List<EventOrderListVO> getAll();
	
	public List<EventOrderListVO> getListByOrderId(String event_order_id);
}
