package com.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class TicketVO implements Serializable {

	private String ticket_id;
	private String event_id;
	private Integer ticket_sort;
	private String ticket_name;
	private Integer ticket_amount;
	private Integer ticket_price;
	private Timestamp ticket_onsale_time;
	private Timestamp ticket_endsale_time;
	private Timestamp ticket_edit_time;
	private Integer ticket_status;
	
	public TicketVO() {
		
	}
	
	public TicketVO(String ticket_id, String event_id, Integer ticket_sort, String ticket_name, Integer ticket_amount,
			Integer ticket_price, Timestamp ticket_onsale_time, Timestamp ticket_endsale_time,
			Timestamp ticket_edit_time, Integer ticket_status) {
		super();
		this.ticket_id = ticket_id;
		this.event_id = event_id;
		this.ticket_sort = ticket_sort;
		this.ticket_name = ticket_name;
		this.ticket_amount = ticket_amount;
		this.ticket_price = ticket_price;
		this.ticket_onsale_time = ticket_onsale_time;
		this.ticket_endsale_time = ticket_endsale_time;
		this.ticket_edit_time = ticket_edit_time;
		this.ticket_status = ticket_status;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public Integer getTicket_sort() {
		return ticket_sort;
	}

	public void setTicket_sort(Integer ticket_sort) {
		this.ticket_sort = ticket_sort;
	}

	public String getTicket_name() {
		return ticket_name;
	}

	public void setTicket_name(String ticket_name) {
		this.ticket_name = ticket_name;
	}

	public Integer getTicket_amount() {
		return ticket_amount;
	}

	public void setTicket_amount(Integer ticket_amount) {
		this.ticket_amount = ticket_amount;
	}

	public Integer getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(Integer ticket_price) {
		this.ticket_price = ticket_price;
	}

	public Timestamp getTicket_endsale_time() {
		return ticket_endsale_time;
	}

	public void setTicket_endsale_time(Timestamp ticket_endsale_time) {
		this.ticket_endsale_time = ticket_endsale_time;
	}

	public Timestamp getTicket_edit_time() {
		return ticket_edit_time;
	}

	public void setTicket_edit_time(Timestamp ticket_edit_time) {
		this.ticket_edit_time = ticket_edit_time;
	}

	public Integer getTicket_status() {
		return ticket_status;
	}

	public void setTicket_status(Integer ticket_status) {
		this.ticket_status = ticket_status;
	}

	public Timestamp getTicket_onsale_time() {
		return ticket_onsale_time;
	}

	public void setTicket_onsale_time(Timestamp ticket_onsale_time) {
		this.ticket_onsale_time = ticket_onsale_time;
	}

	public String toString() {
		return "model.TicketVO [" + ticket_id + event_id + ticket_sort + ticket_name + ticket_amount + ticket_price
				+ ticket_endsale_time + ticket_edit_time + ticket_status + "]";
	}

}
