package com.eventorder.model;

import java.util.List;
import java.util.Map;

import com.eventorderlist.model.EventOrderListVO;


public interface EventOrderDAO {
	public Map<String,List<String>> insert(EventOrderVO eventOrderVO,List<EventOrderListVO> eventOrderList);

	public void update(EventOrderVO eventOrderVO);

	public void delete(String event_order_id);

	public EventOrderVO findByPrimaryKey(String event_order_id);

	public List<EventOrderVO> getAll();
	
}
