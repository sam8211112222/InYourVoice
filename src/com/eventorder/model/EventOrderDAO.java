package com.eventorder.model;

import java.util.List;

import com.ticket.model.TicketVO;

public interface EventOrderDAO {
	public void insert(EventOrderVO eventOrderVO);

	public void update(EventOrderVO eventOrderVO);

	public void delete(String event_order_id);

	public EventOrderVO findByPrimaryKey(String event_order_id);

	public List<EventOrderVO> getAll();
	
}
