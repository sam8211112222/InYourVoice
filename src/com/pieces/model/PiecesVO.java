package com.pieces.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class PiecesVO implements Serializable{
	
	private String piece_id;
	private String album_id;
	private String piece_name;
	private byte[] piece;
	private Integer piece_status;
	private Integer piece_play_count;
	private Timestamp piece_add_time;
	private Timestamp piece_last_edit_time;
	private String piece_last_editor;
	
	public PiecesVO() {
	}

	public String getPiece_id() {
		return piece_id;
	}

	public void setPiece_id(String piece_id) {
		this.piece_id = piece_id;
	}

	public String getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}

	public String getPiece_name() {
		return piece_name;
	}

	public void setPiece_name(String piece_name) {
		this.piece_name = piece_name;
	}

	public byte[] getPiece() {
		return piece;
	}

	public void setPiece(byte[] piece) {
		this.piece = piece;
	}

	public Integer getPiece_status() {
		return piece_status;
	}

	public void setPiece_status(Integer piece_status) {
		this.piece_status = piece_status;
	}

	public Integer getPiece_play_count() {
		return piece_play_count;
	}

	public void setPiece_play_count(Integer piece_play_count) {
		this.piece_play_count = piece_play_count;
	}

	public Timestamp getPiece_add_time() {
		return piece_add_time;
	}

	public void setPiece_add_time(Timestamp piece_add_time) {
		this.piece_add_time = piece_add_time;
	}

	public Timestamp getPiece_last_edit_time() {
		return piece_last_edit_time;
	}

	public void setPiece_last_edit_time(Timestamp piece_last_edit_time) {
		this.piece_last_edit_time = piece_last_edit_time;
	}

	public String getPiece_last_editor() {
		return piece_last_editor;
	}

	public void setPiece_last_editor(String piece_last_editor) {
		this.piece_last_editor = piece_last_editor;
	}

	@Override
	public String toString() {
		return "PiecesVO [piece_id=" + piece_id + ", album_id=" + album_id + ", piece_name=" + piece_name + ", piece="
				+ Arrays.toString(piece) + ", piece_status=" + piece_status + ", piece_play_count=" + piece_play_count
				+ ", piece_add_time=" + piece_add_time + ", piece_last_edit_time=" + piece_last_edit_time
				+ ", piece_last_editor=" + piece_last_editor + "]";
	}
	
	
	
	
}
