package com.cart.model;

public class CartVO implements java.io.Serializable{
	private String product_id;
	private String productphoto_id;
	private String product_name;
	private Double product_price;
	private Integer product_quantity;
	
	
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProductphoto_id() {
		return productphoto_id;
	}
	public void setProductphoto_id(String productphoto_id) {
		this.productphoto_id = productphoto_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double product_price) {
		this.product_price = product_price;
	}
	public Integer getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(Integer product_quantity) {
		this.product_quantity = product_quantity;
	}
	@Override
	public String toString() {
		return "CartVO [product_id=" + product_id + ", productphoto_id=" + productphoto_id + ", product_name="
				+ product_name + ", product_price=" + product_price + ", product_quantity=" + product_quantity + "]";
	}
	
}
