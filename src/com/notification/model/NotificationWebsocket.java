package com.notification.model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.member.model.MemberService;
import com.member.model.MemberVo;

@ServerEndpoint("/notification/{memberId}")
public class NotificationWebsocket {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("memberId") String memberId, Session userSession) throws IOException {
		sessionsMap.put(memberId, userSession);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		gson = builder.setDateFormat("yyyy-MM-dd").create();
		MemberNotification mnt = gson.fromJson(message, MemberNotification.class);
		String content = mnt.getContent();
		String title = mnt.getTitle();
		String senderId = mnt.getSenderId();
		java.util.Date sendTime = mnt.getSendTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formatTime = sdf.format(sendTime);

		if ("audit".equals(mnt.getType())) {
			String receiver = mnt.getReceiver();
			String link = mnt.getLink();

			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				Gson gson = new Gson();
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("title", title);
				msg.put("content", content);
				msg.put("receiver", receiver);
				msg.put("sendTime", formatTime);
				msg.put("link", link);
				JedisMessage.isRead(receiver);
				receiverSession.getAsyncRemote().sendText(gson.toJson(msg));
			}
			JedisMessage.saveMessage(receiver, title, content, formatTime, link);
			return;
		}
		if ("isRead".equals(mnt.getType())) {
			JedisMessage.read(senderId);

			return;
		}
		if ("newband".equals(mnt.getType())) {
			String link = mnt.getLink();
			MemberService memberSvc = new MemberService();
			List<MemberVo> memberVo = memberSvc.getAll();
			for (MemberVo Vo : memberVo) {
				System.out.println(Vo.getMemberId());
				Session receiverSession = sessionsMap.get(Vo.getMemberId());
				if (receiverSession != null && receiverSession.isOpen()) {
					Gson gson = new Gson();
					Map<String, String> msg = new HashMap<String, String>();
					msg.put("title", title);
					msg.put("content", content);
					msg.put("receiver", Vo.getMemberId());
					msg.put("sendTime", formatTime);
					msg.put("link", link);
					JedisMessage.isRead(Vo.getMemberId());
					receiverSession.getAsyncRemote().sendText(gson.toJson(msg));
				}
				JedisMessage.saveMessage(Vo.getMemberId(), title, content, formatTime, link);
			}
			return;
		}

//		List<String> receiver = new ArrayList<String>();
//
//		FavoritesService favoSvc = new FavoritesService();
//
//		List<FavoritesVO> favo = favoSvc.getAll();
//
//		Predicate<FavoritesVO> list = i -> i.getFavorite_id().equals(senderId);
//
//		List<FavoritesVO> memberList = favo.stream().filter(list).collect(Collectors.toList());
//
//		for (FavoritesVO f : memberList) {
//			receiver.add(f.getMember_id());
//			System.out.println(f.getMember_id());
//		}
//
//		for (String member : receiver) {
//			Session receiverSession = sessionsMap.get(member);
//
//			if (receiverSession != null && receiverSession.isOpen()) {
//				Gson gson = new Gson();
//				Map<String, String> msg = new HashMap<String, String>();
//				msg.put("title", title);
//				msg.put("content", content);
//				msg.put("sendTime",formatTime);
//				receiverSession.getAsyncRemote().sendText(gson.toJson(msg));
//			}
//
//			JedisMessage.saveMessage(receiver, title, content, formatTime, "1");
//
//		}
	}

	@OnClose
	public void onClose(Session userSession) {

	}
}
