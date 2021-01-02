package com.notification.model;

import java.util.List;


public interface NotificationDao_interface {
	public void insert(NotificationVo notificationVo);
	public void update(NotificationVo notificationVo);
	public void delete(String notificationId);
	public NotificationVo findByPrimaryKey(String notificationId);
    public List<NotificationVo> getAll();
}
