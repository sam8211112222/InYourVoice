package com.notification.model;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisMessage {
	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static void saveMessage(String receiver, String title, String content,String time,String link) {
		Gson gson = new Gson();
		Map<String,String> msg = new HashMap<String,String>();
		msg.put("title", title);
		msg.put("content", content);	
		msg.put("sendTime",time);
		msg.put("receiver",receiver);
		msg.put("link",link);
		
		Jedis jedis = pool.getResource();
	
		jedis.auth("123456");
		jedis.lpush(receiver+"msg", gson.toJson(msg));
		jedis.close();
	}
	public static List<MemberNotification> getMessageNew5(String receiver){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		List<String> message = jedis.lrange(receiver+"msg", -4, -1);
		jedis.close();
		Gson gson = new Gson();
		List<MemberNotification> mes = new ArrayList<MemberNotification>();
		GsonBuilder builder = new GsonBuilder();
		gson = builder.setDateFormat("yyyy-MM-dd mm:ss").create();
		for(String str :message) {
			
			mes.add(gson.fromJson(str, MemberNotification.class));
		}
		
		return mes;
	}	
	public static List<MemberNotification> getMessage(String receiver){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		List<String> message = jedis.lrange(receiver+"msg", 0, -1);
		jedis.close();
		Gson gson = new Gson();
		List<MemberNotification> mes = new ArrayList<MemberNotification>();
		GsonBuilder builder = new GsonBuilder();
		gson = builder.setDateFormat("yyyy-MM-dd mm:ss").create();
		for(String str :message) {
			mes.add(gson.fromJson(str, MemberNotification.class));
		}
		
		return mes;
	}	
	
	public static void isRead(String receiver) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		try {
			int count = Integer.valueOf(jedis.get(receiver+"isRead"));
		} catch (NumberFormatException e) {
			jedis.set(receiver+"isRead", "0");
		}
		
		jedis.incr(receiver+"isRead");
		jedis.close();
	}
	public static void read(String receiver) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.set(receiver+"isRead", "0");
		jedis.close();
	}
	public static int count(String receiver) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		Integer count;
		try {
			count = Integer.valueOf(jedis.get(receiver+"isRead"));
		} catch (NumberFormatException e) {
			return 0;
		}finally {
			jedis.close();
		}
		if(count != 0||count!= null) {
		return count;
		}
		return 0;
	}
}
