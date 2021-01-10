package com.event.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.event.model.EventService;
import com.event.model.EventVO;
import com.member.model.MemberVo;
import com.ticket.model.TicketService;
import com.ticket.model.TicketVO;

@WebServlet("/MemberCenterEventController")
@MultipartConfig
public class MemberCenterEventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		//確認是否登入
		HttpSession session = req.getSession();
		if (session.getAttribute("memberVo") == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/member/Login.jsp");
			return;
		}
		//確認欲執行的行為
		String action = req.getParameter("action");
		
		
		//呈現要更新的活動
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String event_id = req.getParameter("event_id");

				/*************************** 2.開始查詢資料 ****************************************/
				EventService eventSvc = new EventService();
				EventVO eventVO = eventSvc.getOneEvent(event_id);

				TicketService ticketSvc = new TicketService();
				List<TicketVO> ticketVoList = ticketSvc.getTicketByEventId(event_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("eventVO", eventVO); // 資料庫取出的eventVO物件,存入req
				req.setAttribute("ticketVoList", ticketVoList);
				String url = "/front-end/event/protect/update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/events/protect/bandmanage.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) { // 來自update.jsp的請求
			
			MemberVo memberVo =(MemberVo) session.getAttribute("memberVo");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String event_id = req.getParameter("event_id").trim();
				if (event_id == null || event_id.trim().length() == 0) {
					errorMsgs.add("出現未知錯誤");
				}

				String event_title = req.getParameter("event_title");
				if (event_title == null || event_title.trim().length() == 0) {
					errorMsgs.add("活動標題: 請勿空白");
				} 

				String band_id = memberVo.getBandId();
				if (band_id == null || band_id.trim().length() == 0) {
					errorMsgs.add("請先申請會員資格");
				}

				java.sql.Timestamp event_start_time = null;
				try {
					event_start_time = new java.sql.Timestamp(
							sdf.parse(req.getParameter("event_start_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					errorMsgs.add("請輸入日期!");
				}

				Integer event_type = new Integer(2);
				

				String event_detail = req.getParameter("event_detail").trim();
				if (event_detail == null || event_detail.trim().length() == 0) {
					errorMsgs.add("活動詳情不得空白");
				}

				Integer event_sort = null;
				try {
					event_sort = new Integer(req.getParameter("event_sort").trim());
				} catch (NumberFormatException e) {
					event_sort = 99;
					errorMsgs.add("請選擇活動區域.");
				}

				EventService eventSvc = new EventService();

				Part part1 = req.getPart("event_poster");
				byte[] event_poster = null;
				InputStream in = part1.getInputStream();
				if (in.available() != 0) {
					event_poster = new byte[in.available()];
					in.read(event_poster);
					in.close();
				} else {
					event_poster = eventSvc.getOneEvent(event_id).getEvent_poster();
				}

				String event_place = req.getParameter("event_place").trim();
				if (event_place == null || event_place.trim().length() == 0) {
					errorMsgs.add("活動場地請勿空白");
				}

				Integer event_area = null;
				try {
					event_area = new Integer(req.getParameter("event_area").trim());
				} catch (NumberFormatException e) {
					event_area = 0;
					errorMsgs.add("請選擇活動區域.");
				}

				String event_city = req.getParameter("event_city").trim();
				if (event_city == null || event_city.trim().length() == 0) {
					errorMsgs.add("請選擇活動縣市");
				}

				String event_cityarea = req.getParameter("event_cityarea").trim();
				if (event_cityarea == null || event_cityarea.trim().length() == 0) {
					errorMsgs.add("請選擇活動縣市分區");
				}

				String event_address = req.getParameter("event_address").trim();
				if (event_address == null || event_address.trim().length() == 0) {
					errorMsgs.add("活動地址不得空白");
				}

				java.sql.Timestamp event_on_time = null;
				try {
					event_on_time = new java.sql.Timestamp(
							sdf.parse(req.getParameter("event_on_time").trim()).getTime());
				} catch (IllegalArgumentException e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					errorMsgs.add("請輸入日期!");
				}

				Integer event_status = new Integer(2);
				

				Part part2 = req.getPart("event_seat");
				byte[] event_seat = null;
				InputStream in2 = part2.getInputStream();
				if (in2.available() != 0) {
					event_seat = new byte[in2.available()];
					in2.read(event_seat);
					in2.close();
				} else {
					event_seat = eventSvc.getOneEvent(event_id).getEvent_seat();
				}

				java.sql.Timestamp event_last_edit_time = null;
				event_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());

				String event_last_editor = memberVo.getMemberId();
				if (event_last_editor == null || event_last_editor.trim().length() == 0) {
					System.out.println("使用者錯誤");
					errorMsgs.add("網頁出現未知錯誤");
				}

				EventVO eventVO = new EventVO();
				eventVO.setEvent_id(event_id);
				eventVO.setBand_id(band_id);
				eventVO.setEvent_type(event_type);
				eventVO.setEvent_sort(event_sort);
				eventVO.setEvent_title(event_title);
				eventVO.setEvent_detail(event_detail);
				eventVO.setEvent_poster(event_poster);
				eventVO.setEvent_area(event_area);
				eventVO.setEvent_place(event_place);
				eventVO.setEvent_city(event_cityarea);
				eventVO.setEvent_cityarea(event_cityarea);
				eventVO.setEvent_address(event_address);
				eventVO.setEvent_start_time(event_start_time);
				eventVO.setEvent_on_time(event_on_time);
				eventVO.setEvent_last_edit_time(event_last_edit_time);
				eventVO.setEvent_last_editor(event_last_editor);
				eventVO.setEvent_status(event_status);
				eventVO.setEvent_seat(event_seat);

				/***************************
				 * 活動資料驗證完成,開始驗證票券資料
				 ***************************************/

				String[] ticket_name_list = req.getParameterValues("ticket_name");
				List<TicketVO> ticketVoList = new ArrayList<TicketVO>();
				if (ticket_name_list != null) {
					String[] ticket_id_list = req.getParameterValues("ticket_id");
					String[] ticket_price_list = req.getParameterValues("ticket_price");
					String[] ticket_amount_list = req.getParameterValues("ticket_amount");
					String[] ticket_sort_list = req.getParameterValues("ticket_sort");
					String[] ticket_onsale_time_list = req.getParameterValues("ticket_onsale_time");
					String[] ticket_endsale_time_list = req.getParameterValues("ticket_endsale_time");
					String[] ticket_status_list = req.getParameterValues("ticket_status");

					for (int i = 0; i < ticket_name_list.length; i++) {

						String ticket_id = ticket_id_list[i];

						String ticket_name = ticket_name_list[i];
						if (ticket_name == null || ticket_name.trim().length() == 0) {
							errorMsgs.add("票種名稱請勿空白");
						}

						Integer ticket_price = null;
						try {
							ticket_price = new Integer(ticket_price_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_price = 0;
							errorMsgs.add("請輸入票券金額");
						}

						Integer ticket_amount = null;
						try {
							ticket_amount = new Integer(ticket_amount_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_amount = 0;
							errorMsgs.add("請輸入票券張數");
						}

						Integer ticket_sort = null;
						try {
							ticket_sort = new Integer(ticket_sort_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_sort = 0;
							errorMsgs.add("請輸入票券排序");
						}

						java.sql.Timestamp ticket_onsale_time = null;
						try {
							ticket_onsale_time = new java.sql.Timestamp(
									sdf.parse(ticket_onsale_time_list[i].trim()).getTime());
						} catch (Exception e) {
							ticket_onsale_time = new java.sql.Timestamp(System.currentTimeMillis());
							errorMsgs.add("請輸入開始售票日期!");
						}

						java.sql.Timestamp ticket_endsale_time = null;
						try {
							ticket_endsale_time = new java.sql.Timestamp(
									sdf.parse(ticket_endsale_time_list[i].trim()).getTime());
						} catch (Exception e) {
							ticket_endsale_time = new java.sql.Timestamp(System.currentTimeMillis());
							errorMsgs.add("請輸入結束售票日期!");
						}

						Integer ticket_status = null;
						try {
							ticket_status = new Integer(ticket_status_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_status = 0;
						}

						java.sql.Timestamp ticket_edit_time = null;
						try {
							ticket_edit_time = new java.sql.Timestamp(System.currentTimeMillis());
						} catch (Exception e) {
							errorMsgs.add("發生未知錯誤");
						}

						TicketVO ticketVO = new TicketVO();

						ticketVO.setTicket_id(ticket_id);
						ticketVO.setEvent_id(event_id);
						ticketVO.setTicket_name(ticket_name);
						ticketVO.setTicket_price(ticket_price);
						ticketVO.setTicket_amount(ticket_amount);
						ticketVO.setTicket_sort(ticket_sort);
						ticketVO.setTicket_onsale_time(ticket_onsale_time);
						ticketVO.setTicket_endsale_time(ticket_endsale_time);
						ticketVO.setTicket_edit_time(ticket_edit_time);
						ticketVO.setTicket_status(ticket_status);

						ticketVoList.add(ticketVO);
					}
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eventVO", eventVO); // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("ticketVoList", ticketVoList);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/event/protect/update.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/

				eventVO = eventSvc.updateEvent(event_id, band_id, event_type, event_sort, event_title, event_detail,
						event_poster, event_area, event_place, event_city, event_cityarea, event_address,
						event_start_time, event_on_time, event_last_edit_time, event_last_editor, event_status,
						event_seat);

				TicketService ticketSvc = new TicketService();
				for (int i = 0; i < ticketVoList.size(); i++) {

					TicketVO ticketVO = ticketVoList.get(i);

					String ticket_name = ticketVO.getTicket_name();
					Integer ticket_sort = ticketVO.getTicket_sort();
					Integer ticket_amount = ticketVO.getTicket_amount();
					Integer ticket_price = ticketVO.getTicket_price();
					java.sql.Timestamp ticket_onsale_time = ticketVO.getTicket_onsale_time();
					java.sql.Timestamp ticket_endsale_time = ticketVO.getTicket_endsale_time();
					java.sql.Timestamp ticket_edit_time = ticketVO.getTicket_edit_time();
					Integer ticket_status = ticketVO.getTicket_status();

					String ticket_id = ticketVO.getTicket_id();

					if (ticket_id == null || ticket_id.trim().length() == 0) {
						ticketSvc.addTicket(event_id, ticket_sort, ticket_name, ticket_amount, ticket_price,
								ticket_onsale_time, ticket_endsale_time, ticket_edit_time, ticket_status);
					} else {
						ticketSvc.updateTicket(ticket_id, event_id, ticket_sort, ticket_name, ticket_amount,
								ticket_price, ticket_onsale_time, ticket_endsale_time, ticket_edit_time, ticket_status);
					}
				}
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("eventVO", eventVO); // 資料庫update成功後,正確的的eventVO物件,存入req
				String url = "/front-end/event/protect/bandmanage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/event/protect/update.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求
			
			MemberVo memberVo =(MemberVo) session.getAttribute("memberVo");

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String event_title = req.getParameter("event_title");
				if (event_title == null || event_title.trim().length() == 0) {
					errorMsgs.add("活動標題: 請勿空白");
				}

				String band_id = memberVo.getBandId();

				java.sql.Timestamp event_start_time = null;
				try {
					event_start_time = new java.sql.Timestamp(
							sdf.parse(req.getParameter("event_start_time").trim()).getTime());
				} catch (Exception e) {
					event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer event_type = new Integer(0);

				String event_detail = req.getParameter("event_detail").trim();
				if (event_detail == null || event_detail.trim().length() == 0) {
					errorMsgs.add("活動詳情不得空白");
				}

				Integer event_sort = new Integer(99);
				

				Part part1 = req.getPart("event_poster");
				byte[] event_poster = null;
				if (part1 == null) {
					errorMsgs.add("請選擇活動海報.");
				} else {
					InputStream in = part1.getInputStream();
					event_poster = new byte[in.available()];
					in.read(event_poster);
					in.close();
				}

				String event_place = req.getParameter("event_place").trim();
				if (event_place == null || event_place.trim().length() == 0) {
					errorMsgs.add("活動場地請勿空白");
				}

				Integer event_area = null;
				try {
					event_area = new Integer(req.getParameter("event_area").trim());
				} catch (NumberFormatException e) {
					event_area = 0;
					errorMsgs.add("請選擇活動區域.");
				}

				String event_city = req.getParameter("event_city").trim();
				if (event_city == null || event_city.trim().length() == 0) {
					errorMsgs.add("請選擇活動縣市");
				}

				String event_cityarea = req.getParameter("event_cityarea").trim();
				if (event_cityarea == null || event_cityarea.trim().length() == 0) {
					errorMsgs.add("請選擇活動縣市分區");
				}

				String event_address = req.getParameter("event_address").trim();
				if (event_address == null || event_address.trim().length() == 0) {
					errorMsgs.add("活動地址不得空白");
				}

				java.sql.Timestamp event_on_time = null;
				try {
					event_on_time = new java.sql.Timestamp(
							sdf.parse(req.getParameter("event_on_time").trim()).getTime());
				} catch (Exception e) {
					event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
					System.out.println("錯誤");
					errorMsgs.add("請輸入日期!");
				}
				
				Integer event_status = null;
				if(req.getParameter("submit").equals("送出新增")) {
					event_status = new Integer(2);
				}else {
					event_status = new Integer(3);
				}
				

				Part part2 = req.getPart("event_seat");
				byte[] event_seat = null;
				if (part2 == null) {
					errorMsgs.add("請選擇座位圖.");
				} else {
					InputStream in = part2.getInputStream();
					event_seat = new byte[in.available()];
					in.read(event_seat);
					in.close();
				}

				java.sql.Timestamp event_last_edit_time = null;
				event_last_edit_time = new java.sql.Timestamp(System.currentTimeMillis());

				String event_last_editor = memberVo.getMemberId();

				EventVO eventVO = new EventVO();
				eventVO.setBand_id(band_id);
				eventVO.setEvent_type(event_type);
				eventVO.setEvent_sort(event_sort);
				eventVO.setEvent_title(event_title);
				eventVO.setEvent_detail(event_detail);
				eventVO.setEvent_poster(event_poster);
				eventVO.setEvent_area(event_area);
				eventVO.setEvent_place(event_place);
				eventVO.setEvent_city(event_cityarea);
				eventVO.setEvent_cityarea(event_cityarea);
				eventVO.setEvent_address(event_address);
				eventVO.setEvent_start_time(event_start_time);
				eventVO.setEvent_on_time(event_on_time);
				eventVO.setEvent_last_edit_time(event_last_edit_time);
				eventVO.setEvent_last_editor(event_last_editor);
				eventVO.setEvent_status(event_status);
				eventVO.setEvent_seat(event_seat);

				/***************************
				 * 活動資料驗證完成,開始驗證票券資料
				 ***************************************/
				String[] ticket_name_list = req.getParameterValues("ticket_name");
				List<TicketVO> ticketVoList = new ArrayList<TicketVO>();

				if (ticket_name_list != null) {
					String[] ticket_price_list = req.getParameterValues("ticket_price");
					String[] ticket_amount_list = req.getParameterValues("ticket_amount");
					String[] ticket_sort_list = req.getParameterValues("ticket_sort");
					String[] ticket_onsale_time_list = req.getParameterValues("ticket_onsale_time");
					String[] ticket_endsale_time_list = req.getParameterValues("ticket_endsale_time");
					String[] ticket_status_list = req.getParameterValues("ticket_status");

					for (int i = 0; i < ticket_name_list.length; i++) {

						String ticket_name = ticket_name_list[i];
						if (ticket_name == null || ticket_name.trim().length() == 0) {
							errorMsgs.add("票種名稱請勿空白");
						}

						Integer ticket_price = null;
						try {
							ticket_price = new Integer(ticket_price_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_price = 0;
							errorMsgs.add("請輸入票券金額");
						}

						Integer ticket_amount = null;
						try {
							ticket_amount = new Integer(ticket_amount_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_amount = 0;
							errorMsgs.add("請輸入票券張數");
						}

						Integer ticket_sort = null;
						try {
							ticket_sort = new Integer(ticket_sort_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_sort = 0;
							errorMsgs.add("請輸入票券排序");
						}

						java.sql.Timestamp ticket_onsale_time = null;
						try {
							ticket_onsale_time = new java.sql.Timestamp(
									sdf.parse(ticket_onsale_time_list[i].trim()).getTime());
						} catch (Exception e) {
							ticket_onsale_time = new java.sql.Timestamp(System.currentTimeMillis());
							errorMsgs.add("請輸入開始售票日期!");
						}

						java.sql.Timestamp ticket_endsale_time = null;
						try {
							ticket_endsale_time = new java.sql.Timestamp(
									sdf.parse(ticket_endsale_time_list[i].trim()).getTime());
						} catch (Exception e) {
							ticket_endsale_time = new java.sql.Timestamp(System.currentTimeMillis());
							errorMsgs.add("請輸入結束售票日期!");
						}

						Integer ticket_status = null;
						try {
							ticket_status = new Integer(ticket_status_list[i].trim());
						} catch (NumberFormatException e) {
							ticket_status = 0;
						}

						java.sql.Timestamp ticket_edit_time = null;
						try {
							ticket_edit_time = new java.sql.Timestamp(System.currentTimeMillis());
						} catch (Exception e) {
							errorMsgs.add("發生未知錯誤");
						}

						TicketVO ticketVO = new TicketVO();

						ticketVO.setTicket_name(ticket_name);
						ticketVO.setTicket_price(ticket_price);
						ticketVO.setTicket_amount(ticket_amount);
						ticketVO.setTicket_sort(ticket_sort);
						ticketVO.setTicket_onsale_time(ticket_onsale_time);
						ticketVO.setTicket_endsale_time(ticket_endsale_time);
						ticketVO.setTicket_edit_time(ticket_edit_time);
						ticketVO.setTicket_status(ticket_status);

						ticketVoList.add(ticketVO);
					}
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eventVO", eventVO); // 含有輸入格式錯誤的eventVO物件,也存入req
					req.setAttribute("ticketVoList", ticketVoList);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/event/protect/add.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EventService eventSvc = new EventService();
				eventVO = eventSvc.addEvent(band_id, event_type, event_sort, event_title, event_detail, event_poster,
						event_area, event_place, event_city, event_cityarea, event_address, event_start_time,
						event_on_time, event_last_edit_time, event_last_editor, event_status, event_seat);

				/***************************
				 * (1).開始新增票券資料
				 ***************************************/

				String event_id = eventVO.getEvent_id();
				TicketService ticketSvc = new TicketService();

				for (int i = 0; i < ticketVoList.size(); i++) {

					TicketVO ticketVO = ticketVoList.get(i);

					String ticket_name = ticketVO.getTicket_name();
					Integer ticket_sort = ticketVO.getTicket_sort();
					Integer ticket_amount = ticketVO.getTicket_amount();
					Integer ticket_price = ticketVO.getTicket_price();
					java.sql.Timestamp ticket_onsale_time = ticketVO.getTicket_onsale_time();
					java.sql.Timestamp ticket_endsale_time = ticketVO.getTicket_endsale_time();
					java.sql.Timestamp ticket_edit_time = ticketVO.getTicket_edit_time();
					Integer ticket_status = ticketVO.getTicket_status();

					ticketSvc.addTicket(event_id, ticket_sort, ticket_name, ticket_amount, ticket_price,
							ticket_onsale_time, ticket_endsale_time, ticket_edit_time, ticket_status);

				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/event/bandmanage.jsp";
				res.sendRedirect(req.getContextPath()+"/front-end/event/protect/bandmanage.jsp");
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交bandmanage.jsp
//				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/event/protect/add.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
