package com.productphoto.model;

import java.sql.Date;
import java.sql.Timestamp;

public class ProductPhotoVO implements java.io.Serializable{
	private String productphoto_id;
	private String product_id;
	private byte[] productphoto_photo;
	private Integer productphoto_sort;
	private Timestamp productphoto_add_time;
	
	public String getProductphoto_id() {
		return productphoto_id;
	}
	public void setProductphoto_id(String productphoto_id) {
		this.productphoto_id = productphoto_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public byte[] getProductphoto_photo() {
		return productphoto_photo;
	}
	public void setProductphoto_photo(byte[] productphoto_photo) {
		this.productphoto_photo = productphoto_photo;
	}
	public Integer getProductphoto_sort() {
		return productphoto_sort;
	}
	public void setProductphoto_sort(Integer productphoto_sort) {
		this.productphoto_sort = productphoto_sort;
	}
	public Timestamp getProductphoto_add_time() {
		return productphoto_add_time;
	}
	public void setProductphoto_add_time(Timestamp productphoto_add_time) {
		this.productphoto_add_time = productphoto_add_time;
	}
}
