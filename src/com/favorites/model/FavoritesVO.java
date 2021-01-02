package com.favorites.model;


public class FavoritesVO implements java.io.Serializable{
	
	private String uniqueid;
	private String member_id;
	private Integer favorite_type;
	private String favorite_id;
	private java.sql.Timestamp favorite_add_time;
	
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Integer getFavorite_type() {
		return favorite_type;
	}
	public void setFavorite_type(Integer favorite_type) {
		this.favorite_type = favorite_type;
	}
	public String getFavorite_id() {
		return favorite_id;
	}
	public void setFavorite_id(String favorite_id) {
		this.favorite_id = favorite_id;
	}
	public java.sql.Timestamp getFavorite_add_time() {
		return favorite_add_time;
	}
	public void setFavorite_add_time(java.sql.Timestamp favorite_add_time) {
		this.favorite_add_time = favorite_add_time;
	}

	
	
}
