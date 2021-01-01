package com.eventorderlist.model;

import java.util.List;

public class EventOrderListService {
	
	EventOrderListDAO dao = null;
	
	public EventOrderListService() {
		dao = new EventOrderListJNDIDAO();
	}
	
	public List<EventOrderListVO> getAll(){
		return dao.getAll();
	}
}
