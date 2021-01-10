package com.band.model;

public class Reply {
	private String title;
	private String reply;
	public Reply(String title,String reply) {
		this.title = title;
		this.reply = reply;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
}
