package com.tags.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class TagsVO implements Serializable{
	
	private String tag_id;
	private String tag_name;
	private Timestamp tag_add_time;
	
	public TagsVO() {
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public Timestamp getTag_add_time() {
		return tag_add_time;
	}
	public void setTag_add_time(Timestamp tag_add_time) {
		this.tag_add_time = tag_add_time;
	}
	@Override
	public String toString() {
		return "TagsVO [tag_id=" + tag_id + ", tag_name=" + tag_name + ", tag_add_time=" + tag_add_time + "]";
	}
	
	

}
