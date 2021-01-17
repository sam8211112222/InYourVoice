package com.eventorder.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eventorderlist.model.EventOrderListService;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ticket.model.TicketService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.util.JedisPoolUtil;

public class TicketRedisThread implements Runnable {
	private String order_mail;
	private String event_title;
	private String ticketCheckInURL = "http://10.2.0.67:8081/TEA102G6/CheckTicketController?action=check-in&orderListId=";
	private String ticketGetQrcodeURL = "http://10.2.0.67:8081/TEA102G6/EventPicController?action=send-mail&orderListId=";
	private Map<String, List<String>> orders;

	public TicketRedisThread(String order_mail, String event_title, Map<String, List<String>> orders) {
		this.order_mail = order_mail;
		this.event_title = event_title;
		this.orders = orders;
	}

	@Override
	public void run() {
		if (!orders.isEmpty()) {
			// 取得連線資源
			JedisPool pool = JedisPoolUtil.getJedisPool();
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(10);

			EventOrderService eventOrderSvc = new EventOrderService();
			EventOrderListService eventOrderListSvc = new EventOrderListService();
			TicketService ticketSvc = new TicketService();

			Gson gson = new Gson();

			// 設定QRcode相關資訊
			int width = 300;
			int height = 300;
			String format = "png";
			Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

			Set<String> set = orders.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String order_id = it.next();
				List<String> orderListIds = orders.get(order_id);
				for (String orderlist_id : orderListIds) {
					String ticketQRcodeUrl = ticketCheckInURL + orderlist_id;
					System.out.println(ticketQRcodeUrl);
					try {
						BitMatrix matrix = new MultiFormatWriter().encode(ticketQRcodeUrl, BarcodeFormat.QR_CODE, width,
								height, hints);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						MatrixToImageWriter.writeToStream(matrix, format, os);
						byte[] qrCodeByte = os.toByteArray();
						String qrCode = gson.toJson(qrCodeByte);
//						String qrCode = new String(Base64.getEncoder().encodeToString(b));

						jedis.set(orderlist_id, qrCode);

						String ticket_name = ticketSvc
								.getOneTicket(eventOrderListSvc.getOneByOrderListId(orderlist_id).getTicket_id())
								.getTicket_name();

						eventOrderSvc.sendTicketQRcode(order_mail, event_title, orderlist_id, ticket_name,
								ticketGetQrcodeURL);

					} catch (WriterException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				jedis.close();
			}

		}
	}

}
