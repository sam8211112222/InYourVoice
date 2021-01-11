package idv.david.websocketchat.jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
	
	public static List<String> getReceiverList(String sender) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
 
        Set<String> keys = jedis.keys(sender + ":*");
        List<String> receiverList = new ArrayList<>();
        Iterator<String> it=keys.iterator() ;   
        while(it.hasNext()){   
            String key = it.next();
            receiverList.add(key.split(":")[1]);  
        }
        return receiverList;
	}

}
