package com.orderlist.model;

import java.sql.Timestamp;
import java.util.Base64;

public class ReviewVO {
		private Integer review_score;
		
		private String review_msg;
		
		private Timestamp review_time;
		
		private String member_id;
		
		private String member_name;
		
		private String member_nickname;
		
		private byte[] member_photo;

		public Integer getReview_score() {
			return review_score;
		}

		public void setReview_score(Integer review_score) {
			this.review_score = review_score;
		}

		public String getReview_msg() {
			return review_msg;
		}

		public void setReview_msg(String review_msg) {
			this.review_msg = review_msg;
		}

		public Timestamp getReview_time() {
			return review_time;
		}

		public void setReview_time(Timestamp review_time) {
			this.review_time = review_time;
		}

		public String getMember_id() {
			return member_id;
		}

		public void setMember_id(String member_id) {
			this.member_id = member_id;
		}

		public String getMember_name() {
			return member_name;
		}

		public void setMember_name(String member_name) {
			this.member_name = member_name;
		}

		public String getMember_nickname() {
			return member_nickname;
		}

		public void setMember_nickname(String member_nickname) {
			this.member_nickname = member_nickname;
		}

		public byte[] getMember_photo() {
			return member_photo;
		}

		public void setMember_photo(byte[] member_photo) {
			this.member_photo = member_photo;
		}
		
		public String getPhotoData() {
			if(member_photo.length>0) {
				return Base64.getEncoder().encodeToString(member_photo);
			}
			return null;
		}
}
