package com.eventorderlist.model;

import java.io.Serializable;

public class EventOrderListVO implements Serializable {

	private String orderlist_id;
	private String ticket_id;
	private String event_order_id;
	private Integer orderlist_goods_amount;
	private String orderlist_remarks;

	public String getOrderlist_id() {
		return orderlist_id;
	}

	public void setOrderlist_id(String orderlist_id) {
		this.orderlist_id = orderlist_id;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getEvent_order_id() {
		return event_order_id;
	}

	public void setEvent_order_id(String event_order_id) {
		this.event_order_id = event_order_id;
	}

	public Integer getOrderlist_goods_amount() {
		return orderlist_goods_amount;
	}

	public void setOrderlist_goods_amount(Integer orderlist_goods_amount) {
		this.orderlist_goods_amount = orderlist_goods_amount;
	}

	public String getOrderlist_remarks() {
		return orderlist_remarks;
	}

	public void setOrderlist_remarks(String orderlist_remarks) {
		this.orderlist_remarks = orderlist_remarks;
	}
	
	public String toString() {
		return "model.EventOrderListVO [" + orderlist_id +ticket_id+event_order_id+orderlist_goods_amount+orderlist_remarks+ "]";
	}

}
