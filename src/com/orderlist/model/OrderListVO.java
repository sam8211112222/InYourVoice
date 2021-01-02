package com.orderlist.model;

import java.sql.Timestamp;

public class OrderListVO implements java.io.Serializable {
	private String orderlist_id;
	private String order_id;
	private String product_id;
	private Integer orderlist_goods_amount;
	private String orderlist_remarks;
	private Integer review_score;
	private String review_msg;
	private Timestamp review_time;
	private Integer review_hidden;
	private Integer price;

	public String getOrderlist_id() {
		return orderlist_id;
	}

	public void setOrderlist_id(String orderlist_id) {
		this.orderlist_id = orderlist_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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

	public Integer getReview_score() {
		return review_score;
	}

	public void setReview_score(Integer review_score) {
		this.review_score = review_score;
	}

	public String getReview_msg() {
		return review_msg;
	}

	public void setReview_msg(String review_msg) {
		this.review_msg = review_msg;
	}

	public Timestamp getReview_time() {
		return review_time;
	}

	public void setReview_time(Timestamp review_time) {
		this.review_time = review_time;
	}

	public Integer getReview_hidden() {
		return review_hidden;
	}

	public void setReview_hidden(Integer review_hidden) {
		this.review_hidden = review_hidden;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderListVO [orderlist_id=" + orderlist_id + ", order_id=" + order_id + ", product_id=" + product_id
				+ ", orderlist_goods_amount=" + orderlist_goods_amount + ", price=" + price + ", orderlist_remarks="
				+ orderlist_remarks + ", review_score=" + review_score + ", review_msg=" + review_msg + ", review_time="
				+ review_time + ", review_hidden=" + review_hidden + "]";
	}

}
