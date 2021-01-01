package com.eventorder.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eventorderlist.model.EventOrderListVO;

public class EventOrderService {
	EventOrderDAO dao = null;
	
	public EventOrderService() {
		dao = new EventOrderJNDIDAO();
	}
	
	public void addOrder(String member_id,String event_id, Timestamp order_place_time,String order_name,String order_mail,String order_phone,Map<String,Integer> cartList) {
		EventOrderVO eventOrderVO = new EventOrderVO();
		
		eventOrderVO.setMember_id(member_id);
		eventOrderVO.setEvent_id(event_id);
		eventOrderVO.setOrder_place_time(order_place_time);
		eventOrderVO.setOrder_name(order_name);
		eventOrderVO.setOrder_mail(order_mail);
		eventOrderVO.setOrder_phone(order_phone);
		
		List<EventOrderListVO> eventOrderList = new ArrayList<EventOrderListVO>();
		Set<String> orderListKeys = cartList.keySet();
		Iterator<String> it =orderListKeys.iterator();
		while(it.hasNext()) {
			String ticket_id = it.next();
			EventOrderListVO eventOrderListVO = new EventOrderListVO();
			eventOrderListVO.setTicket_id(ticket_id);
			eventOrderListVO.setOrderlist_goods_amount(cartList.get(ticket_id));
			eventOrderListVO.setOrderlist_remarks("");
			eventOrderList.add(eventOrderListVO);
		}
		dao.insert(eventOrderVO,eventOrderList);
	}

}
