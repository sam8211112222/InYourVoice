package com.event.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventVO implements Serializable {

	private String event_id;
	private String band_id;
	private Integer event_type;
	private Integer event_sort;
	private String event_title;
	private String event_detail;
	private byte[] event_poster;
	private Integer event_area;
	private String event_place;
	private String event_city;
	private String event_cityarea;
	private String event_address;
	private Timestamp event_start_time;
	private Timestamp event_on_time;
	private Timestamp event_last_edit_time;
	private String event_last_editor;
	private Integer event_status;
	private byte[] event_seat;

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getBand_id() {
		return band_id;
	}

	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}

	public Integer getEvent_type() {
		return event_type;
	}

	public void setEvent_type(Integer event_type) {
		this.event_type = event_type;
	}

	public Integer getEvent_sort() {
		return event_sort;
	}

	public void setEvent_sort(Integer event_sort) {
		this.event_sort = event_sort;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public String getEvent_detail() {
		return event_detail;
	}

	public void setEvent_detail(String event_detail) {
		this.event_detail = event_detail;
	}

	public byte[] getEvent_poster() {
		return event_poster;
	}

	public void setEvent_poster(byte[] event_poster) {
		this.event_poster = event_poster;
	}

	public Integer getEvent_area() {
		return event_area;
	}

	public void setEvent_area(Integer event_area) {
		this.event_area = event_area;
	}

	public String getEvent_place() {
		return event_place;
	}

	public void setEvent_place(String event_place) {
		this.event_place = event_place;
	}

	public String getEvent_city() {
		return event_city;
	}

	public void setEvent_city(String event_city) {
		this.event_city = event_city;
	}

	public String getEvent_cityarea() {
		return event_cityarea;
	}

	public void setEvent_cityarea(String event_cityarea) {
		this.event_cityarea = event_cityarea;
	}

	public String getEvent_address() {
		return event_address;
	}

	public void setEvent_address(String event_address) {
		this.event_address = event_address;
	}

	public Timestamp getEvent_start_time() {
		return event_start_time;
	}

	public void setEvent_start_time(Timestamp event_start_time) {
		this.event_start_time = event_start_time;
	}

	public Timestamp getEvent_on_time() {
		return event_on_time;
	}

	public void setEvent_on_time(Timestamp event_on_time) {
		this.event_on_time = event_on_time;
	}

	public Timestamp getEvent_last_edit_time() {
		return event_last_edit_time;
	}

	public void setEvent_last_edit_time(Timestamp event_last_edit_time) {
		this.event_last_edit_time = event_last_edit_time;
	}

	public String getEvent_last_editor() {
		return event_last_editor;
	}

	public void setEvent_last_editor(String event_last_editor) {
		this.event_last_editor = event_last_editor;
	}

	public Integer getEvent_status() {
		return event_status;
	}

	public void setEvent_status(Integer event_status) {
		this.event_status = event_status;
	}

	public byte[] getEvent_seat() {
		return event_seat;
	}

	public void setEvent_seat(byte[] event_seat) {
		this.event_seat = event_seat;
	}

	public String toString() {
		return "model.EventVO [" + event_id + band_id + event_type + event_sort + event_title + event_detail
				+ event_poster + event_area + event_place + event_city + event_cityarea + event_address
				+ event_start_time + event_on_time + event_last_edit_time + event_last_editor + event_status
				+ event_seat + "]";
	}

}
