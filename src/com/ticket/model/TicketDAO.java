package com.ticket.model;

import java.sql.Connection;
import java.util.List;

public interface TicketDAO {
	public void insert(TicketVO ticketVO);
	
	public void insert(TicketVO ticketVO ,Connection con );

	public void update(TicketVO ticketVO);

	public int delete(String ticket_id);

	public TicketVO findByPrimaryKey(String ticket_id);

	public List<TicketVO> getAll();
	
	public List<TicketVO> findByEventId(String event_id);
}
