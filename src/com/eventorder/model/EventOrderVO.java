package com.eventorder.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventOrderVO implements Serializable {

	private String event_order_id;
	private String member_id;
	private String event_id;
	private Timestamp order_place_time;
	private String order_name;
	private String order_mail;
	private String order_phone;

	public String getEvent_order_id() {
		return event_order_id;
	}

	public void setEvent_order_id(String event_order_id) {
		this.event_order_id = event_order_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public Timestamp getOrder_place_time() {
		return order_place_time;
	}

	public void setOrder_place_time(Timestamp order_place_time) {
		this.order_place_time = order_place_time;
	}

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}

	public String getOrder_mail() {
		return order_mail;
	}

	public void setOrder_mail(String order_mail) {
		this.order_mail = order_mail;
	}

	public String getOrder_phone() {
		return order_phone;
	}

	public void setOrder_phone(String order_phone) {
		this.order_phone = order_phone;
	}

	public String toString() {
		return "model.TicketVO [" + event_order_id + member_id + event_id + order_place_time + order_name + order_mail
				+ order_phone + "]";
	}
}
