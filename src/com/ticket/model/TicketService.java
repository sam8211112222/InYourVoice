package com.ticket.model;

import java.sql.Timestamp;
import java.util.List;

public class TicketService {
	private TicketDAO dao;

	public TicketService() {
//		dao = new TicketJDBCDAO();
		dao = new TicketJNDIDAO();
	}

	public TicketVO addTicket(String event_id, Integer ticket_sort, String ticket_name, Integer ticket_amount,
			Integer ticket_price, Timestamp ticket_onsale_time, Timestamp ticket_endsale_time, Timestamp ticket_edit_time, Integer ticket_status) {
		TicketVO ticketVO = new TicketVO();
		ticketVO.setEvent_id(event_id);
		ticketVO.setTicket_sort(ticket_sort);
		ticketVO.setTicket_name(ticket_name);
		ticketVO.setTicket_amount(ticket_amount);
		ticketVO.setTicket_price(ticket_price);
		ticketVO.setTicket_onsale_time(ticket_onsale_time);
		ticketVO.setTicket_endsale_time(ticket_endsale_time);
		ticketVO.setTicket_edit_time(ticket_edit_time);
		ticketVO.setTicket_status(ticket_status);

		dao.insert(ticketVO);

		return ticketVO;

	}

	public TicketVO updateTicket(String ticket_id ,String event_id, Integer ticket_sort, String ticket_name, Integer ticket_amount,
			Integer ticket_price,Timestamp ticket_onsale_time, Timestamp ticket_endsale_time, Timestamp ticket_edit_time, Integer ticket_status) {
		TicketVO ticketVO = new TicketVO();
		ticketVO.setTicket_id(ticket_id);
		ticketVO.setEvent_id(event_id);
		ticketVO.setTicket_sort(ticket_sort);
		ticketVO.setTicket_name(ticket_name);
		ticketVO.setTicket_amount(ticket_amount);
		ticketVO.setTicket_price(ticket_price);
		ticketVO.setTicket_onsale_time(ticket_onsale_time);
		ticketVO.setTicket_endsale_time(ticket_endsale_time);
		ticketVO.setTicket_edit_time(ticket_edit_time);
		ticketVO.setTicket_status(ticket_status);

		dao.update(ticketVO);

		return ticketVO;

	}
	
	public int deleteTicket(String ticket_id) {
		return dao.delete(ticket_id);
	}
	
	public List<TicketVO> getTicketByEventId(String event_id){
		return dao.findByEventId(event_id);
	}
	
	public TicketVO getOneTicket(String ticket_id) {
		return dao.findByPrimaryKey(ticket_id);
		
	}
	
	public List<TicketVO> getAll(){
		return dao.getAll();
	}
}
