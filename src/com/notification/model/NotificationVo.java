package com.notification.model;

public class NotificationVo {
	private String notificationId;
	private String notificationContent;
	private String notificationLink;
	private java.sql.Date notificationAddTime;
	private Integer notificationIsread;
	private String memberId;
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public String getNotificationLink() {
		return notificationLink;
	}
	public void setNotificationLink(String notificationLink) {
		this.notificationLink = notificationLink;
	}
	public java.sql.Date getNotificationAddTime() {
		return notificationAddTime;
	}
	public void setNotificationAddTime(java.sql.Date notificationAddTime) {
		this.notificationAddTime = notificationAddTime;
	}
	public Integer getNotificationIsread() {
		return notificationIsread;
	}
	public void setNotificationIsread(Integer notificationIsread) {
		this.notificationIsread = notificationIsread;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
