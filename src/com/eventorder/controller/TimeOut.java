package com.eventorder.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/TimeOut/{member_id}")
public class TimeOut {

	public static final Map<String, Session> connectedSessions = Collections
			.synchronizedMap(new ConcurrentHashMap<String, Session>());
//	public static Map<String, Session> connectedSessions = new ConcurrentHashMap<>();

	/*
	 * 如果想取得HttpSession與ServletContext必須實作
	 * ServerEndpointConfig.Configurator.modifyHandshake()，
	 * 參考https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-
	 * httpsession-in-onmessage-of-a-jsr-356-serverendpoint
	 */
	@OnOpen
	public void onOpen(@PathParam("member_id") String member_id, Session userSession) throws IOException {
		System.out.println(member_id);
		connectedSessions.put(member_id, userSession);
		System.out.println("有加入web");
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
//		for (Session session : connectedSessions) {
//			if (session.isOpen())
//				session.getAsyncRemote().sendText(message);
//		}
//		System.out.println("Message received: " + message);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		if (connectedSessions.containsValue(userSession)) {
			Iterator<String> it = connectedSessions.keySet().iterator();
			while (it.hasNext()) {
				String member_id = it.next();
				if (connectedSessions.get(member_id).equals(userSession)) {
					connectedSessions.remove(member_id);
				}

			}
		}
	}

//	@OnError
//	public void onError(Session userSession, Throwable e) {
//		System.out.println("Error: " + e.toString());
//	}

}
