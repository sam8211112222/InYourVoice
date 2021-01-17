package com.eventorder.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.eventorderlist.model.EventOrderListVO;

public class EventOrderService {
	EventOrderDAO dao = null;

	public EventOrderService() {
		dao = new EventOrderJNDIDAO();
//		dao = new EventOrderJDBCDAO();
	}

	public Map<String, List<String>> addOrder(String member_id, String event_id, Timestamp order_place_time,
			String order_name, String order_mail, String order_phone, String orderlist_remarks,
			Map<String, Integer> cartList) {
		EventOrderVO eventOrderVO = new EventOrderVO();

		eventOrderVO.setMember_id(member_id);
		eventOrderVO.setEvent_id(event_id);
		eventOrderVO.setOrder_place_time(order_place_time);
		eventOrderVO.setOrder_name(order_name);
		eventOrderVO.setOrder_mail(order_mail);
		eventOrderVO.setOrder_phone(order_phone);

		List<EventOrderListVO> eventOrderList = new ArrayList<EventOrderListVO>();
		Set<String> orderListKeys = cartList.keySet();
		Iterator<String> it = orderListKeys.iterator();
		while (it.hasNext()) {
			String ticket_id = it.next();
			EventOrderListVO eventOrderListVO = new EventOrderListVO();
			eventOrderListVO.setTicket_id(ticket_id);
			eventOrderListVO.setOrderlist_goods_amount(cartList.get(ticket_id));
			eventOrderListVO.setOrderlist_remarks(orderlist_remarks);
			eventOrderListVO.setOrderlist_status(0);
			eventOrderList.add(eventOrderListVO);
		}
		return dao.insert(eventOrderVO, eventOrderList);
	}

	public List<EventOrderVO> getListByMemberId(String member_id) {
		return dao.getAll().stream().filter(e -> e.getMember_id().equals(member_id)).collect(Collectors.toList());
	}

	public EventOrderVO getOneByEventOrderId(String event_order_id) {
		return dao.findByPrimaryKey(event_order_id);
	}

	public void sendTicketQRcode(String order_mail,  String event_title, String orderlist_id,
			String ticket_name ,String ticketGetQrcodeURL) {
		String targetEmail = order_mail;
		System.out.println(order_mail);
		// Sender's email ID needs to be mentioned
//		String from = "leotseng0807@gmail.com";
//		final String username = "leotseng0807";// change accordingly
//		final String password = "viyokgrzavbyjlgu";// change accordingly
		
		String from = "myemailsender25@gmail.com";
		final String username = "myemailsender25@gmail.com";// change accordingly
		final String password = "!123456aA";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));

			// Set Subject: header field
			message.setSubject("活動門票:" + event_title + "票種:" + ticket_name + "票券編號" + orderlist_id);

			//創建multipart
			Multipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			StringBuilder htmlTextSB = new StringBuilder();
			String htmlTextStr = htmlTextSB.append("<p>進場時，請出示下列QRcode予工作人員做掃描動作</p>")
								.append("<img src='"+ticketGetQrcodeURL+orderlist_id +"'>")
								.append("<img src='cid:image'>")
								.toString();
//			message.setContent("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
//					+ "    <meta charset=\"UTF-8\">\r\n"
//					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//					+ "    <title>Document</title>\r\n" + "</head>\r\n" + "\r\n"
//					+ "<body><div><p>進場時，請出示下列QRcode予工作人員做掃描動作</p></div><div><img src='data:image/png;base64," + qrCode
//					+ "'></div></body>\r\n" + "\r\n" + "</html>", "text/html");
			// Send message
			messageBodyPart.setContent(htmlTextStr, "text/html; charset=UTF-8"); 
			multipart.addBodyPart(messageBodyPart); 
			
			messageBodyPart = new MimeBodyPart();
//			File file = new File("檔案位置");
//			FileDataSource fds = new FileDataSource(file);
//			String urlStr = "http://inyourvoice.ga/TEA102G6/EventPicController?action=send-mail&orderListId="+orderlist_id;
			String urlStr = "http://localhost:8081/TEA102G6/EventPicController?action=send-mail&orderListId="+orderlist_id;
			URL url = new URL(urlStr);
			DataSource ds = new URLDataSource(url);
			messageBodyPart.setDataHandler(new DataHandler(ds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);
			
			
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
