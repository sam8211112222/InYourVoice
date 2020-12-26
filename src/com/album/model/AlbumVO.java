package com.album.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class AlbumVO implements Serializable {
	
	private String album_id;
	private String band_id;
	private String album_name;
	private String album_intro;
	private byte[] album_photo;
	private Integer album_status;
	private Timestamp album_add_time;
	private Timestamp album_release_time;
	private Timestamp album_last_edit_time;
	private String album_last_editor;
	
	public AlbumVO() {
	}
	public String getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}
	public String getBand_id() {
		return band_id;
	}
	public void setBand_id(String band_id) {
		this.band_id = band_id;
	}
	public String getAlbum_name() {
		return album_name;
	}
	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}
	public String getAlbum_intro() {
		return album_intro;
	}
	public void setAlbum_intro(String album_intro) {
		this.album_intro = album_intro;
	}
	public byte[] getAlbum_photo() {
		return album_photo;
	}
	public void setAlbum_photo(byte[] album_photo) {
		this.album_photo = album_photo;
	}
	public Integer getAlbum_status() {
		return album_status;
	}
	public void setAlbum_status(Integer album_status) {
		this.album_status = album_status;
	}
	public Timestamp getAlbum_add_time() {
		return album_add_time;
	}
	public void setAlbum_add_time(Timestamp album_add_time) {
		this.album_add_time = album_add_time;
	}
	public Timestamp getAlbum_release_time() {
		return album_release_time;
	}
	public void setAlbum_release_time(Timestamp album_release_time) {
		this.album_release_time = album_release_time;
	}
	public Timestamp getAlbum_last_edit_time() {
		return album_last_edit_time;
	}
	public void setAlbum_last_edit_time(Timestamp album_last_edit_time) {
		this.album_last_edit_time = album_last_edit_time;
	}
	public String getAlbum_last_editor() {
		return album_last_editor;
	}
	public void setAlbum_last_editor(String album_last_editor) {
		this.album_last_editor = album_last_editor;
	}
	
	@Override
	public String toString() {
		return "AlbumVO [album_id=" + album_id + ", band_id=" + band_id + ", album_name=" + album_name
				+ ", album_intro=" + album_intro + ", album_photo=" + Arrays.toString(album_photo) + ", album_status="
				+ album_status + ", album_add_time=" + album_add_time + ", album_release_time=" + album_release_time
				+ ", album_last_edit_time=" + album_last_edit_time + ", album_last_editor=" + album_last_editor + "]";
	}
	
}
