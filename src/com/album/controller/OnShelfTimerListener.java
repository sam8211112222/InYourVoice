package com.album.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.member.model.MemberService;
import com.member.model.MemberVo;

@WebListener
public class OnShelfTimerListener implements ServletContextListener {

	/*
	 * Server啟動後，定時(10分鐘)確認資料庫中: 如果Album的預約上架時間到了， 則將status由2設定為1
	 */

	Timer timer = null;
	TimerTask task = null;

	public OnShelfTimerListener() {

	}

	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}

	public void contextInitialized(ServletContextEvent sce) {

		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {

				System.out.println("OnShelfTimerListener : working, " + new Date());
				AlbumService albumSvc = new AlbumService();
				List<AlbumVO> albumVOList = albumSvc.getAllAlbums();
				for (AlbumVO albumVO : albumVOList) {

					// 如果status是2 且 上架時間已經到了
					if (albumVO.getAlbum_status() == 2
							&& albumVO.getAlbum_release_time().getTime() < System.currentTimeMillis()) {

						String band_id = albumVO.getBand_id();
//						System.out.println("band_id = " + band_id);
						
						MemberService memSvc = new MemberService();
						List<MemberVo> memberVOList = memSvc.getAll().stream()
								.filter(m -> band_id.equals(m.getBandId())) // 順序相反會報錯
								.collect(Collectors.toList());
						System.out.println("memberVOList length = " + memberVOList.size());
						String memberAccount = memberVOList.get(0).getMemberAccount();
//						System.out.println(memberAccount);

						String album_id = albumVO.getAlbum_id();
						String album_name = albumVO.getAlbum_name();
						java.sql.Timestamp album_release_time = albumVO.getAlbum_release_time();
						sendMail(memberAccount, album_name, album_release_time, album_id);

						// 將status改為1
						albumVO.setAlbum_status(1);
						albumVO.setAlbum_last_edit_time(albumVO.getAlbum_last_edit_time());
						albumSvc.updateAlbum(albumVO);
						System.out.println("album status update:" + albumVO.toString());
					}

				}

			}
		};

		timer.scheduleAtFixedRate(task, 0, 10 * 60 * 1000);
//		timer.scheduleAtFixedRate(task, 0, 5 *1000);
//		timer.scheduleAtFixedRate(task, 0, 15 *1000);

	}
	
	
						// memberAccount 是收件者email必須要有，其餘參數為自己需要再調整
	public void sendMail(String memberAccount, String album_name, java.sql.Timestamp album_release_time, String album_id) {
		String targetEmail = memberAccount; // 傳入收件人的email
		System.out.println("memberAccount = " + memberAccount);
		// 填入寄信用的email
		// Sender's email ID needs to be mentioned
		String from = "myemailsender25@gmail.com";
		final String username = "myemailsender25@gmail.com";// change accordingly
		final String password = "!123456aA";// change accordingly
		
		// 這裡開始都不用改=============================================================
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
			// 這裡以前都不用改=============================================================
			
			// 這裡開始編輯email內容=============================================================
			// Set Subject: header field
			message.setSubject("您預約上架的專輯已經上架");

			String onShelfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.format(new Date(album_release_time.getTime()));

//			// Now set the actual message
			// 這裡以前在編輯email內容=============================================================
			// 嵌入圖片附件 再以拿取附件的方式嵌入圖片
			// Create a multi-part to combine the parts 
			Multipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = null;
			// Create your new message part 
			messageBodyPart = new MimeBodyPart();
			
			StringBuilder htmlTextSB = new StringBuilder();
			String htmlTextStr = htmlTextSB.append("<p>您新增的專輯:" + album_name + "已於您預約的時間:" + onShelfTime + "上架。</p>")
								.append("<img style='width:200px; height:200px'  src='cid:image'>")
//								.append("<img style='width:200px; height:200px' src='http://inyourvoice.ga/TEA102G6/album/album.do?action=getAlbumPhoto&album_id=" + album_id + "'>")
								.toString();
//			System.out.println(htmlTextStr);
			messageBodyPart.setContent(htmlTextStr, "text/html; charset=UTF-8"); 
			multipart.addBodyPart(messageBodyPart); 
			
			// Create part for the image 
			messageBodyPart = new MimeBodyPart(); 
			// Fetch the image and associate to part 
			
//			DataSource fds = new FileDataSource(file); 
			String urlStr = "http://localhost:8081/TEA102G6/album/album.do?action=getAlbumPhoto&album_id=" + album_id;
			URL url = new URL(urlStr);
			DataSource fds = new URLDataSource(url);
			messageBodyPart.setDataHandler(new DataHandler(fds)); 
			messageBodyPart.setHeader("Content-ID","<image>"); 
			// Add part to multi-part 
			multipart.addBodyPart(messageBodyPart); 
			
			// Associate multi-part with message 
			message.setContent(multipart); 
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
