package com.bandtag.model;

import java.io.Serializable;

public class BandTagVO implements Serializable {

	private String band_tag_id;
	private String band_id;
	private String tag_id;
		
	public BandTagVO() {
	}
	public String getBand_tag_id() {
		return band_tag_id;
	}
	public void setBand_tag_id(String band_tag_id) {
		this.band_tag_id = band_tag_id;
	}
	public String getBand_id() {
		return band_id;
	}
	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	@Override
	public String toString() {
		return "BandTagVO [band_tag_id=" + band_tag_id + ", band_id=" + band_id + ", tag_id=" + tag_id + "]";
	}
	
	
}
