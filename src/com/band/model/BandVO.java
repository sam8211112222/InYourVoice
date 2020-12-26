package com.band.model;

import java.io.Serializable;
import java.util.Arrays;

public class BandVO implements Serializable {
	
	private String band_id;
	private String band_name;
	private String band_intro;
	private byte[] band_photo;
	private byte[] band_banner;
	private byte[] band_piece_check;
	private java.sql.Timestamp band_add_time;
	private Integer band_status;
	private java.sql.Timestamp band_last_edit_time;
	private String band_last_editor;
	public String getBand_id() {
		return band_id;
	}
	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}
	public String getBand_name() {
		return band_name;
	}
	public void setBand_name(String band_name) {
		this.band_name = band_name;
	}
	public String getBand_intro() {
		return band_intro;
	}
	public void setBand_intro(String band_intro) {
		this.band_intro = band_intro;
	}
	public byte[] getBand_photo() {
		return band_photo;
	}
	public void setBand_photo(byte[] band_photo) {
		this.band_photo = band_photo;
	}
	public byte[] getBand_banner() {
		return band_banner;
	}
	public void setBand_banner(byte[] band_banner) {
		this.band_banner = band_banner;
	}
	public byte[] getBand_piece_check() {
		return band_piece_check;
	}
	public void setBand_piece_check(byte[] band_piece_check) {
		this.band_piece_check = band_piece_check;
	}
	public java.sql.Timestamp getBand_add_time() {
		return band_add_time;
	}
	public void setBand_add_time(java.sql.Timestamp band_add_time) {
		this.band_add_time = band_add_time;
	}
	public Integer getBand_status() {
		return band_status;
	}
	public void setBand_status(Integer band_status) {
		this.band_status = band_status;
	}
	public java.sql.Timestamp getBand_last_edit_time() {
		return band_last_edit_time;
	}
	public void setBand_last_edit_time(java.sql.Timestamp band_last_edit_time) {
		this.band_last_edit_time = band_last_edit_time;
	}
	public String getBand_last_editor() {
		return band_last_editor;
	}
	public void setBand_last_editor(String band_last_editor) {
		this.band_last_editor = band_last_editor;
	}
	@Override
	public String toString() {
		return "BandVO [band_id=" + band_id + ", band_name=" + band_name + ", band_intro=" + band_intro
				+ ", band_photo=" + Arrays.toString(band_photo) + ", band_banner=" + Arrays.toString(band_banner)
				+ ", band_piece_check=" + Arrays.toString(band_piece_check) + ", band_add_time=" + band_add_time
				+ ", band_status=" + band_status + ", band_last_edit_time=" + band_last_edit_time
				+ ", band_last_editor=" + band_last_editor + "]";
	}
	
	
	
}
