package com.product.model;
import java.sql.Date;
import java.sql.Timestamp;

public class ProductVO implements java.io.Serializable {
	private String product_id;
	private String band_id;
	private Integer product_type;
	private String product_name;
	private String product_intro;
	private String product_detail;
	private Double product_price;
	private Integer product_stock;
	private Integer product_check_status;
	private Integer product_status;
	private Timestamp product_on_time;
	private Timestamp product_off_time;
	private Timestamp product_add_time;
	private Double product_discount;
	private Timestamp product_discount_on_time;
	private Timestamp product_discount_off_time;
	private Timestamp product_last_edit_time;
	private String product_last_editor;
	
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	private Integer review_score;
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	private Integer review_count;
	
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getBand_id() {
		return band_id;
	}
	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}
	public Integer getProduct_type() {
		return product_type;
	}
	public void setProduct_type(Integer product_type) {
		this.product_type = product_type;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_intro() {
		return product_intro;
	}
	public void setProduct_intro(String product_intro) {
		this.product_intro = product_intro;
	}
	public String getProduct_detail() {
		return product_detail;
	}
	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}
	public Double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_stock() {
		return product_stock;
	}
	public void setProduct_stock(Integer product_stock) {
		this.product_stock = product_stock;
	}
	public Integer getProduct_check_status() {
		return product_check_status;
	}
	public void setProduct_check_status(Integer product_check_status) {
		this.product_check_status = product_check_status;
	}
	public Integer getProduct_status() {
		return product_status;
	}
	public void setProduct_status(Integer product_status) {
		this.product_status = product_status;
	}
	public Timestamp getProduct_on_time() {
		return product_on_time;
	}
	public void setProduct_on_time(Timestamp product_on_time) {
		this.product_on_time = product_on_time;
	}
	public Timestamp getProduct_off_time() {
		return product_off_time;
	}
	public void setProduct_off_time(Timestamp product_off_time) {
		this.product_off_time = product_off_time;
	}
	public Timestamp getProduct_add_time() {
		return product_add_time;
	}
	public void setProduct_add_time(Timestamp product_add_time) {
		this.product_add_time = product_add_time;
	}
	public Double getProduct_discount() {
		return product_discount;
	}
	public void setProduct_discount(Double product_discount) {
		this.product_discount = product_discount;
	}
	public Timestamp getProduct_discount_on_time() {
		return product_discount_on_time;
	}
	public void setProduct_discount_on_time(Timestamp product_discount_on_time) {
		this.product_discount_on_time = product_discount_on_time;
	}
	public Timestamp getProduct_discount_off_time() {
		return product_discount_off_time;
	}
	public void setProduct_discount_off_time(Timestamp product_discount_off_time) {
		this.product_discount_off_time = product_discount_off_time;
	}
	public Timestamp getProduct_last_edit_time() {
		return product_last_edit_time;
	}
	public void setProduct_last_edit_time(Timestamp product_last_edit_time) {
		this.product_last_edit_time = product_last_edit_time;
	}
	public String getProduct_last_editor() {
		return product_last_editor;
	}
	public void setProduct_last_editor(String product_last_editor) {
		this.product_last_editor = product_last_editor;
	}
	@Override
	public String toString() {
		return "ProductVO [product_id=" + product_id + ", band_id=" + band_id + ", product_type=" + product_type
				+ ", product_name=" + product_name + ", product_intro=" + product_intro + ", product_detail="
				+ product_detail + ", product_price=" + product_price + ", product_stock=" + product_stock
				+ ", product_check_status=" + product_check_status + ", product_status=" + product_status
				+ ", product_on_time=" + product_on_time + ", product_off_time=" + product_off_time
				+ ", product_add_time=" + product_add_time + ", product_discount=" + product_discount
				+ ", product_discount_on_time=" + product_discount_on_time + ", product_discount_off_time="
				+ product_discount_off_time + ", product_last_edit_time=" + product_last_edit_time
				+ ", product_last_editor=" + product_last_editor + "]";
	}
	
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	public Integer getReview_score() {
		return review_score;
	}
	
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	public void setReview_score(Integer review_score) {
		this.review_score = review_score;
	}
	
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	public Integer getReview_count() {
		return review_count;
	}
	
	/**
	 * added by Sophia (DB無須新增欄位)
	 */
	public void setReview_count(Integer review_count) {
		this.review_count = review_count;
	}
	
}
