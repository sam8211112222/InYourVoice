package com.orders.model;

import java.sql.Timestamp;

public class OrdersVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String member_id;
	private Integer order_status;
	private Timestamp order_place_time;
	private String order_name;
	private String order_mail;
	private String order_phone;
	private Timestamp order_delivery_time;
	private Timestamp order_received_time;
	private Integer total_price;

	public OrdersVO() {

	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
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

	public Timestamp getOrder_delivery_time() {
		return order_delivery_time;
	}

	public void setOrder_delivery_time(Timestamp order_delivery_time) {
		this.order_delivery_time = order_delivery_time;
	}

	public Timestamp getOrder_received_time() {
		return order_received_time;
	}

	public void setOrder_received_time(Timestamp order_received_time) {
		this.order_received_time = order_received_time;
	}

	public Integer getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}

	@Override
	public String toString() {
		return "OrdersVO [order_id=" + order_id + ", member_id=" + member_id + ", order_status=" + order_status
				+ ", order_place_time=" + order_place_time + ", order_name=" + order_name + ", order_mail=" + order_mail
				+ ", order_phone=" + order_phone + ", order_delivery_time=" + order_delivery_time
				+ ", order_received_time=" + order_received_time + "]";
	}

}
