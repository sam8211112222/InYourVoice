package com.band.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.band.model.BandService;
import com.band.model.BandVO;
import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVo;

import com.utils.DataSourceUtils;
import com.utils.ImageUtil;

@WebServlet("/band/band.do")
@ServerEndpoint("/FolloWS/{userName}")
@MultipartConfig
public class bandServlet extends HttpServlet {

	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		connectedSessions.add(userSession);
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), userName);
		System.out.println(text);
	}

//	@OnMessage
//	public void onMessage(Session userSession, String message) {
//		for (Session session : connectedSessions) {
//			if (session.isOpen())
//				session.getAsyncRemote().sendText(message);
//		}
//		System.out.println("Message received: " + message);
//	}

	public void updateFollowCount(String followCount) {
		for (Session session : connectedSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(followCount);
		}
		System.out.println("Message pushed: follow count is " + followCount);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		res.setCharacterEncoding("UTF-8");

// 取得樂團頁面上半部
		if ("getBandMain".equals(action)) {
			String band_id = req.getParameter("band_id");
			BandService bandSvc = new BandService();
			BandVO bandVO = bandSvc.getOneBand(band_id);
			req.setAttribute("bandVO", bandVO);

			RequestDispatcher bandIndexView = req.getRequestDispatcher("/front-end/band/index_band.jsp");
			bandIndexView.forward(req, res);
		}

// 頁籤中的"關於樂團"被按下後，回傳下半部關於樂團的頁面
//		if("getBandIntro".equals(action)) {
//			String band_id = req.getParameter("band_id");
//			BandVO bandVO = new BandVO();
//			bandVO.setBand_id(band_id);
//			RequestDispatcher bandIntroView = req.getRequestDispatcher("/front-end/band/band_intro.jsp");
//			bandIntroView.forward(req, res);
//		}

		if ("getBandPhoto".equals(action)) {

			String band_id = req.getParameter("band_id");
			ServletOutputStream out = res.getOutputStream();

			try {
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(band_id);
				res.setContentType("image/gif");
				res.setContentLength(bandVO.getBand_photo().length);
				out.write(bandVO.getBand_photo());
			} catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/images/fileNotFound.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}

		}

		if ("getBandBanner".equals(action)) {

			String band_id = req.getParameter("band_id");
			ServletOutputStream out = res.getOutputStream();

			try {
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(band_id);
				res.setContentType("image/gif");
				res.setContentLength(bandVO.getBand_banner().length);
				out.write(bandVO.getBand_banner());
			} catch (Exception e) {
				InputStream in = getServletContext().getResourceAsStream("/images/fileNotFound.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}

		}

//		
		if ("listAllBand".equals(action)) {

			BandService bandSvc = new BandService();
			DataSource ds = null;

			// 取得關鍵字
			// 關鍵字
			String searchKeyWord = req.getParameter("searchKeyWord");
			System.out.println(searchKeyWord);
			// band tag

			// 若無關鍵字則list all
			if (searchKeyWord == null || searchKeyWord.length() == 0) {
				System.out.println("no searchKeyWord");
				// list all
				List<BandVO> bandVOList = bandSvc.getAllBand().stream().filter(b -> b.getBand_status() == 1)
						.collect(Collectors.toList());
				req.setAttribute("bandVOList", bandVOList);
				String url = "/front-end/band/listAllBand.jsp";
				RequestDispatcher listAllView = req.getRequestDispatcher(url);
				listAllView.forward(req, res);

			} else {
				// 依照關鍵字及band tag進資料庫搜尋
				System.out.println("yes searchKeyWord");

				List<BandVO> bandVOList = bandSvc.getBySearchKeyWord(searchKeyWord);
				req.setAttribute("bandVOList", bandVOList);
				String url = "/front-end/band/listAllBand.jsp";
				RequestDispatcher searchView = req.getRequestDispatcher(url);
				searchView.forward(req, res);
			}

		}

		if ("addFavorite".equals(action)) {

			res.setCharacterEncoding("UTF-8");

			// 用type區別新增的最愛類別
			String type = req.getParameter("type");
			// 從session的memberVo取得member_id
			HttpSession session = req.getSession();
			MemberVo memberVO = (MemberVo) session.getAttribute("memberVo");
			String member_id = memberVO.getMemberId();
			// 下方依照最愛類別取得相對應的id，命名為favorite_id(配合DAO)
			String favorite_id = null;
			int favorite_type = -1;
			java.sql.Timestamp favorite_add_time = new Timestamp(System.currentTimeMillis());

			switch (type) {

			case "band":

				favorite_id = req.getParameter("band_id");
				favorite_type = 1;
				break;

			case "piece":

				favorite_id = req.getParameter("piece_id");
				favorite_type = 3;
				break;

			case "album":

				favorite_id = req.getParameter("album_id");
				favorite_type = 4;
				break;

			default:
				break;
			}

			String favorite_id_effective_final = favorite_id;

			FavoritesService favSvc = new FavoritesService();
			System.out.println("member_id = " + member_id + ", favorite_type = " + favorite_type + ", favorite_id = "
					+ favorite_id + ", favorite_add_time = " + favorite_add_time);

			List<FavoritesVO> favoriteVOList = favSvc.getAll().stream().filter(f -> f.getMember_id().equals(member_id))
					.filter(f -> f.getFavorite_id().equals(favorite_id_effective_final)).collect(Collectors.toList());

			for (FavoritesVO favVO : favoriteVOList) {
				System.out.println(favVO.getUniqueid());
			}

			// 回傳json
			Gson gson = new Gson();
			PrintWriter out = res.getWriter();
//			String jsonStr = gson.toJson("member_id = " + member_id + ", favorite_type = " + favorite_type + ", favorite_id = " + favorite_id + ", favorite_add_time = " + favorite_add_time);
			String jsonStr = "";

			if (favoriteVOList.size() > 0) {
				jsonStr = "existed";
			} else {
				favSvc.addFavorites(member_id, favorite_type, favorite_id, favorite_add_time);
				jsonStr = "added";
				// 如果是新增追蹤專輯，就push追蹤數
				if (favorite_type == 1) {
					String followCount = String.valueOf(
							favSvc.getAll().stream().filter(f -> f.getFavorite_id().equals(favorite_id_effective_final))
									.collect(Collectors.toList()).size());

					updateFollowCount(followCount);
				}
			}
			System.out.println(jsonStr);
			jsonStr = gson.toJson(jsonStr);
			out.write(jsonStr);

		}

		if ("delFavorite".equals(action)) {

			// 用type區別新增的最愛類別
			String type = req.getParameter("type");
			// 從session的memberVo取得member_id
			HttpSession session = req.getSession();
			MemberVo memberVO = (MemberVo) session.getAttribute("memberVo");
			String member_id = memberVO.getMemberId();
			// 下方依照最愛類別取得相對應的id，命名為favorite_id(配合DAO)
			String favorite_id = "";
			int favorite_type = -1;

			switch (type) {

			case "band": {
				favorite_id = req.getParameter("band_id");
				favorite_type = 1;
				break;
			}

			case "piece": {
				favorite_id = req.getParameter("piece_id");
				favorite_type = 3;
				break;
			}
			case "album": {
				favorite_id = req.getParameter("album_id");
				favorite_type = 4;
				break;
			}
			default: {
				favorite_id = "";
			}
			}
			String favorite_id_effective_final = favorite_id;

			FavoritesService favSvc = new FavoritesService();
			List<FavoritesVO> favoriteVOList = favSvc.getAll().stream().filter(f -> f.getMember_id().equals(member_id))
					.filter(f -> f.getFavorite_id().equals(favorite_id_effective_final)).collect(Collectors.toList());

			System.out.println(favoriteVOList.size());

			for (FavoritesVO favVO : favoriteVOList) {
				System.out.println(favVO.getUniqueid());
				favSvc.deleteFavorites(favVO.getUniqueid());
				// 樂團
				if (favorite_type == 1) {
					String followCount = String.valueOf(
							favSvc.getAll().stream().filter(f -> f.getFavorite_id().equals(favorite_id_effective_final))
									.collect(Collectors.toList()).size());

					updateFollowCount(followCount);
				}

			}

			// 回傳json
			Gson gson = new Gson();
			PrintWriter out = res.getWriter();
			String jsonStr = "deleted";
			jsonStr = gson.toJson(jsonStr);
			out.write(jsonStr);
		}

		// Kevin===========================================================================
		// 註冊樂團
		if ("bandSignup".equals(action)) {

			String bandName = (String) req.getParameter("bandName");
			String bandIntro = req.getParameter("bandIntro");
			String memberId = (String) req.getParameter("memberId"); // 取得會員ID
			Integer bnadStatus = new Integer(0);// 預設申請狀態為0
			Part part1 = req.getPart("bandPieceCheck");
			InputStream in1 = part1.getInputStream();
			byte[] bandPieceCheck = new byte[in1.available()];
			in1.read(bandPieceCheck);
			Part part2 = req.getPart("bandBanner");
			InputStream in2 = part2.getInputStream();
			byte[] bandBanner = new byte[in2.available()];
			in2.read(bandBanner);
			in1.close();
			in2.close();
			// 取得會員頭相
			MemberService memberSvc = new MemberService();
			MemberVo memberVo = memberSvc.getOne(memberId);
			byte[] bandPhoto = memberVo.getMemberPhoto();
			Timestamp bandAddTime = new Timestamp(System.currentTimeMillis());
			Timestamp bandLastEditTime = new Timestamp(System.currentTimeMillis());
			BandService bandSvc = new BandService();
			String band_last_editor = null;
			bandSvc.insertBand(bandName, bandIntro, bandPhoto, bandBanner, bandPieceCheck, bandAddTime, bnadStatus,
					bandLastEditTime, band_last_editor, memberId);
			MemberVo newmemberVo = memberSvc.getOne(memberId);
			HttpSession session = req.getSession();
			session.setAttribute("memberVo", newmemberVo);
			String path = req.getContextPath();
			res.sendRedirect(path + "/front-end/member/protect/memberCenter2.jsp");
		}
		// 取得試聽作品
		if ("getSong".equals(action)) {
			String bandId = req.getParameter("bandId");
			BandService bandSvc = new BandService();
			byte[] song = bandSvc.getBandSong(bandId);
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(song.length);
			out.write(song);
			out.close();
		}
//	取得樂團橫幅
		if ("getBanner".equals(action)) {
			String bandId = req.getParameter("bandId");
			BandService bandSvc = new BandService();
			byte[] banner = bandSvc.getBanner(bandId);
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(banner.length);
			out.write(banner);
			out.close();
		}
		// 更新樂團STATUS 通過 和退回
		if ("updateStatus".equals(action)) {
			String bandId = req.getParameter("bandId");
			String bandLastEditor = req.getParameter("bandLastEditor");
			String bandStatus = req.getParameter("bandStatus");
			BandService bandSvc = new BandService();
			Timestamp bandLastEditTime = new Timestamp(System.currentTimeMillis());
			bandSvc.updateBandStatusBackEnd(bandId, bandStatus, bandLastEditTime, bandLastEditor);
		}
	
		if ("picDisplay".equals(action)) {
			String bandId = req.getParameter("bandId");
			BandService bandSvc = new BandService();
			byte[] pic = bandSvc.getBandpic(bandId);
			byte[] pic2 = ImageUtil.shrink(pic, 150);
			OutputStream sout = res.getOutputStream();
			res.setContentLength(pic2.length);
			sout.write(pic2);
			sout.close();
		}
		if ("getband".equals(action)) {

			String bandId = req.getParameter("bandId");
			BandService bandSvc = new BandService();
			BandVO bandVO = bandSvc.getOneBand(bandId);
			req.setAttribute("bandVO", bandVO);
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/band/bandSignupRelpy.jsp");
			failureView.forward(req, res);
		}
		if ("updateBackEnd".equals(action)) {
			String bandId = req.getParameter("bandId");
			String bandStatus = req.getParameter("bandStatus");
			String bandLastEditor = req.getParameter("bandLastEditor");
			System.out.println(bandId);
			System.out.println(bandStatus);
			System.out.println(bandLastEditor);
			Map<String, String> msg = new HashMap<String, String>();
			Gson gson = new Gson();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.util.Date date = new java.util.Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			String d = sdf.format(time);
			System.out.println(d);
			msg.put("bandLastEditor", bandLastEditor);
			msg.put("bandLastEditTime", d);
			PrintWriter pw = res.getWriter();
			pw.write(gson.toJson(msg));
			pw.close();
			BandService bandSvc = new BandService();
			bandSvc.updateBandStatusBackEnd(bandId, bandStatus, ts, bandLastEditor);
		}
		if ("getAuditPage".equals(action)) {
			String bandId = req.getParameter("bandId");
			BandService bandSvc = new BandService();
			BandVO bandVO = bandSvc.getOneBand(bandId);
			req.setAttribute("bandVO", bandVO);
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/band/protect/bandSignupRelpy.jsp");
			failureView.forward(req, res);
		}

		if ("bandSignupUpdate".equals(action)) {
			byte[] bandBanner = null;
			byte[] bandPieceCheck =null;
			String bandName = (String) req.getParameter("bandName");
			String bandIntro = req.getParameter("bandIntro");
			String bandId = (String) req.getParameter("bandId"); // 取得會員ID
			Part part1 = req.getPart("bandPieceCheck");
			Integer bandStatus = Integer.valueOf(req.getParameter("bandStatus"));
				
			InputStream in1 = part1.getInputStream();
			bandPieceCheck = new byte[in1.available()];
			in1.read(bandPieceCheck);
			in1.close();
			if(bandPieceCheck.length==0) {
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(bandId);
				bandPieceCheck = bandVO.getBand_piece_check();	
			}
			
			Part part2 = req.getPart("bandBanner");
			InputStream in2 = part2.getInputStream();
			bandBanner = new byte[in2.available()];
			in2.read(bandBanner);
			in2.close();
			if(bandBanner.length==0){
				BandService bandSvc = new BandService();
				BandVO bandVO = bandSvc.getOneBand(bandId);
				bandBanner = bandVO.getBand_banner();
			
			}
			
			// 取得會員頭相

			BandService bandSvc = new BandService();

			bandSvc.updateSignup(bandId, bandName, bandIntro, bandBanner, bandPieceCheck,bandStatus);
			BandVO newbandVo = bandSvc.getOneBand(bandId); // 取得新的樂團資料回傳
			HttpSession session = req.getSession();
			session.setAttribute("bandVo", newbandVo);
			String path = req.getContextPath();
			res.sendRedirect(path + "/front-end/member/protect/memberCenter2.jsp");
		}
			if("bandreply".equals(action)) {
				String memberId = req.getParameter("memberId");
				MemberService memberSvc = new MemberService();
				MemberVo memberVo = memberSvc.getOne(memberId);
				HttpSession session = req.getSession();
				session.setAttribute("memberVo", memberVo);
				res.sendRedirect(req.getContextPath()+"/front-end/band/protect/bandSignup.jsp");
			}
		// 冠華
		// 這是新增的搜尋方法
		if ("searchName".equals(action)) {

					String name = req.getParameter("search");

					req.getSession().setAttribute("name", name);
					res.sendRedirect(req.getContextPath() + "/front-end/query/query_band.jsp");
				}
	}
}
