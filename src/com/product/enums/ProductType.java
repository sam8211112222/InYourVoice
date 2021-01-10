package com.product.enums;

public enum ProductType {
	
	TYPE1(1,"衣服"),TYPE2(2,"褲子"),TYPE3(3,"配件");
	
	private Integer code;
	
	private String name;
	
	
	ProductType(Integer code,String name){
		this.code = code;
		this.name = name;
		
	}


	public Integer getCode() {
		return code;
	}


	public String getName() {
		return name;
	}
	
	
}
