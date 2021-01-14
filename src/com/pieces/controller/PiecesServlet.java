package com.pieces.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.favorites.model.FavoritesService;
import com.favorites.model.FavoritesVO;
import com.google.gson.Gson;
import com.member.model.MemberVo;
import com.pieces.model.PiecesService;
import com.pieces.model.PiecesVO;

import database.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/pieces/pieces.do")
@MultipartConfig
public class PiecesServlet extends HttpServlet {

	public static JedisPool pool = null;
	private static Timer timer = null;

	@Override
	public void init() throws ServletException {
		pool = JedisUtil.getJedisPool();
		PiecesService piecesSvcForTimer = new PiecesService();
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// 開啟與連線Redis
				Jedis jedis = pool.getResource();
				jedis.auth("123456");
				jedis.select(2);

				String key = "piecePlayCount";
				Map<String, String> piece_play = jedis.hgetAll(key);
				System.out.println(new Date());
				for (String id : piece_play.keySet()) {

					// 將Redis中的播放次數取出並存為int型態
					int count = Integer.parseInt(piece_play.get(id));

					// 如果新增的播放次數都過了10分鐘(timer的週期)還是為0就從set中刪除此屬性
					if (count == 0) {
						jedis.hdel(key, id);
					} else {
						System.out.println(id + ":" + piece_play.get(id));
						// 將新增的播放次數存進資料庫
						piecesSvcForTimer.updatePieces(id, count);
						// Redis內計數器歸零
						jedis.hset(key, id, "0");
					}
				}

				jedis.close();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 10 * 60 * 1000);
//		timer.scheduleAtFixedRate(task, 0, 5 * 1000);

	}

	@Override
	public void destroy() {
		JedisUtil.shutdownJedisPool();
		timer.cancel();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("piece_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入作品ID");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/index_pieces.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/

				String piece_id = str;
				PiecesService piecesSvc = new PiecesService();
				PiecesVO piecesVO = piecesSvc.getOnePiece(piece_id);
				if (piecesVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/index_pieces.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("piecesVO", piecesVO);
				String url = "/back-end/pieces/protect/listOnePiece.jsp";
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/pieces/protect/listOnePiece.jsp");
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/index_pieces.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String piece_id = req.getParameter("piece_id");

				/*************************** 2.開始查詢資料 ****************************************/
				PiecesService piecesSvc = new PiecesService();
				PiecesVO piecesVO = piecesSvc.getOnePiece(piece_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("piecesVO", piecesVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/pieces/protect/update_piece_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/index_pieces.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String piece_id = req.getParameter("piece_id");
				String piece_name = req.getParameter("piece_name");
				String member_id = req.getParameter("member_id");
				PiecesService piecesSvc = new PiecesService();
				PiecesVO piecesVO_original = piecesSvc.getOnePiece(piece_id);

				Part piece_part = req.getPart("piece");
				InputStream in = piece_part.getInputStream();
				byte[] piece = new byte[in.available()]; // 要上傳檔案
				if (in.available() != 0) {
					// 新增
					in.read(piece);
					in.close();

				} else {
					// 用舊的
					piece = piecesVO_original.getPiece();

				}

				Integer piece_status = new Integer(req.getParameter("piece_status"));
//				Integer piece_play_count = Integer.valueOf(req.getParameter("piece_play_count"));
				java.sql.Timestamp piece_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());

				PiecesVO piecesVO = new PiecesVO();
				piecesVO.setPiece_id(piece_id);
				piecesVO.setAlbum_id(piecesVO_original.getAlbum_id());
				piecesVO.setPiece(piece);
				piecesVO.setPiece_name(piece_name);
				piecesVO.setPiece_status(piece_status);
				piecesVO.setPiece_play_count(piecesVO_original.getPiece_play_count());
				piecesVO.setPiece_add_time(piecesVO_original.getPiece_add_time());
				piecesVO.setPiece_last_edit_time(piece_last_edit_time);
//				piecesVO.setPiece_last_editor((String)req.getSession().getAttribute("memberVo"));
				piecesVO.setPiece_last_editor(member_id);
				piecesVO.toString();

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("piecesVO", piecesVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/update_piece_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
//				PiecesService piecesSvc = new PiecesService();
				piecesVO = piecesSvc.updatePieces(piecesVO);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("piecesVO", piecesVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/pieces/protect/piece_manage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/pieces/protect/update_piece_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getPiece".equals(action)) {

			String piece_id = req.getParameter("piece_id");
			ServletOutputStream out = res.getOutputStream();

			PiecesService piecesSvc = new PiecesService();
			PiecesVO piecesVO = piecesSvc.getPiece(piece_id);
			byte[] piece = piecesVO.getPiece();
			
			// 印出所有header
//			Map<String, String> map = new HashMap<String, String>();
//			Enumeration<String> headerNames = req.getHeaderNames();
//			while (headerNames.hasMoreElements()) {
//				String key = (String) headerNames.nextElement();
//				String value = req.getHeader(key);
//				map.put(key, value);
//				System.out.println(key + ":" + value);
//			}

			res.addHeader("Accept-Ranges", "bytes");
			res.setContentType("audio/mpeg");
			res.setContentLength(piece.length);
			out.write(piece);

			// ===
//			String range = req.getHeader("Range");
//          String[] rs = range.split("\\=");
//          range = rs[1].split("\\-")[0];

//			long length = piece.length;
//          res.addHeader("Accept-Ranges", "bytes");
//          res.addHeader("Content-Length", length + "");
//          res.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
//          res.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");
			// ===

		}

		if ("getPieceList".equals(action)) {

			res.setCharacterEncoding("UTF-8");
			String album_id = req.getParameter("album_id");
//			AlbumService albumSvc = new AlbumService();
//			AlbumVO albumVO = albumSvc.getOneAlbum(album_id);
//			long albumReleaseTimeInLong = albumVO.getAlbum_release_time().getTime();
			
			PiecesService piecesSvc = new PiecesService();
			List<PiecesVO> piecesVOs = piecesSvc.getAllByAlbumId(album_id).stream()
					.filter(p -> p.getPiece_status() == 1)
//					.filter(p -> albumReleaseTimeInLong > System.currentTimeMillis())
					.collect(Collectors.toList());
			PrintWriter out = res.getWriter();
			Gson gson = new Gson();
			String jsonStr = gson.toJson(piecesVOs);
			out.write(jsonStr);

		}

		if ("updatePiece".equals(action)) {

			res.setCharacterEncoding("UTF-8");
			// 接收資料
			String album_id = req.getParameter("album_id");
			String band_id = req.getParameter("band_id");
			String piece_id = req.getParameter("piece_id");
			String piece_name = req.getParameter("piece_name");
			String member_id = req.getParameter("member_id");
//			String piece_status = req.getParameter("piece_status");

			Part piece_file = req.getPart("piece_file");
			String filename = piece_file.getSubmittedFileName();
			filename = filename.length() == 0 ? null : filename;
			
			InputStream in = piece_file.getInputStream();
			byte[] piece_file_byte = new byte[in.available()];
			in.read(piece_file_byte);
			in.close();

			// 印出接收資料
			System.out.println(album_id);
			System.out.println(piece_id);
			System.out.println(piece_file.getSubmittedFileName());

			// 用piece_id找看看是否新增過
			PiecesService piecesSvc = new PiecesService();
			PiecesVO piecesVO = piecesSvc.getOnePiece(piece_id);

			if (piecesVO == null) {
				// 若沒有則新增資料

				piecesVO = new PiecesVO();
				piecesVO.setAlbum_id(album_id);
				piecesVO.setPiece_name(piece_name);
				piecesVO.setPiece_last_editor(member_id);
				if (filename != null) {
					piecesVO.setPiece(piece_file_byte);
				}
//				if(piece_status != null) {
//					piecesVO.setPiece_status(Integer.parseInt(piece_status));
//				}

				// 寫入資料庫
				PiecesVO newPiecesVO = piecesSvc.insertPiece(piecesVO);
				newPiecesVO.setPiece(new byte[1]);
				// 回傳json給ajax
				Gson gson = new Gson();
				String jsonStr = gson.toJson(newPiecesVO);
				System.out.println(jsonStr);
				PrintWriter out = res.getWriter();
				out.write(jsonStr);

			} else {
				// 若有則更新資料
				piecesVO.setPiece_id(piece_id);
				piecesVO.setAlbum_id(album_id);
				piecesVO.setPiece_name(piece_name);
				piecesVO.setPiece_last_editor(member_id);
				if (piece_file_byte.length != 0) {
					piecesVO.setPiece(piece_file_byte);
				}
//				if(piece_status != null) {
//					piecesVO.setPiece_status(Integer.parseInt(piece_status));
//				}
//				
				// 寫入資料庫
				piecesSvc.updatePieces(piecesVO);
				piecesVO.setPiece(new byte[1]);
				// 回傳json給ajax
				Gson gson = new Gson();
				String jsonStr = gson.toJson(piecesVO);
				System.out.println(jsonStr);
				PrintWriter out = res.getWriter();
				out.write(jsonStr);

			}

		}

		if ("deletePiece".equals(action)) {

			String piece_id = req.getParameter("piece_id");
			PiecesService piecesSvc = new PiecesService();
			piecesSvc.deletePiece(piece_id);

		}

		if ("delete".equals(action)) {
			String piece_id = req.getParameter("piece_id");
			PiecesService piecesSvc = new PiecesService();
			piecesSvc.deletePiece(piece_id);

			String url = "/back-end/pieces/protect/piece_manage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("piecePlayCount".equals(action)) {

			// 當作品被播放時，傳送請求進來
			String piece_id = req.getParameter("piece_id");
			String key = "piecePlayCount";
			
//			System.out.println("piecePlayCount");

			// 開啟與連線Redis
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			jedis.select(2);

			// 先搜尋是否有存過
			if (jedis.hget(key, piece_id) != null) {
				// 若有則拿出來+1
				jedis.hincrBy(key, piece_id, 1);
//				jedis.persist(key);
//				jedis.expire(key, 15);
			} else {
				// 若無則新增並+1
				jedis.hset(key, piece_id, "1");
//				jedis.expire(key, 15*60);
			}

			// 關閉Redis
			jedis.close();
//			System.out.println("close");

		}
		
		
		if("checkPieceFav".equals(action)) {
			
			res.setCharacterEncoding("UTF-8");
			String piece_id = req.getParameter("piece_id");
			MemberVo memberVO = (MemberVo) req.getSession().getAttribute("memberVo");
			
			FavoritesService favSvc = new FavoritesService();
			List<FavoritesVO> favList = null;
			int favState = -1;
			if(memberVO!=null) {
				favList= favSvc.getAll().stream()
						.filter(f -> (f.getMember_id()).equals(memberVO.getMemberId()))
						.filter(f -> (f.getFavorite_id()).equals(piece_id))
						.collect(Collectors.toList());
				favState = favList.size();
			}
			
			System.out.println("favList size = " + favState);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(favState);
			PrintWriter out = res.getWriter();
			out.write(jsonStr);
			
			
		}

	}

}
