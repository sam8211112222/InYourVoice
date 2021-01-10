package com.orders.enums;

public enum OrderStatus {
	
	PROCESSING(0,"處理中"),DELIVERED(1,"已出貨");
	
	private Integer status;
	
	private String name;
	
	
	OrderStatus(Integer status,String name){
		this.status = status;
		this.name = name;
		
	}


	public Integer getStatus() {
		return status;
	}


	public String getName() {
		return name;
	}
	
	
}
