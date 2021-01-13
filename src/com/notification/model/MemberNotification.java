package com.notification.model;




public class MemberNotification{
	private String title;
	private String content;
	private String receiver;
	private String senderId;
	private java.util.Date sendTime;
	private String type;
	private String link;

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public MemberNotification() {
		
	};
	public MemberNotification(String title, String content, String receiver, java.util.Date sendTime,String type) {
		this.content = content;
		this.title = title;
		this.receiver = receiver;
		this.sendTime = sendTime;
		this.type = type;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public java.util.Date getSendTime() {
		return sendTime;
	}



	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}



	public String getSenderId() {
		return senderId;
	}



	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}



	


	public String getReceiver() {
		return receiver;
	}



	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
