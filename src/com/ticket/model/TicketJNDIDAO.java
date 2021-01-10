package com.ticket.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TicketJNDIDAO implements TicketDAO {
	
	private static final String INSERT_STMT = "INSERT INTO ticket (ticket_id,event_id,ticket_sort,ticket_name,ticket_amount,ticket_price,ticket_onsale_time,ticket_endsale_time,ticket_edit_time,ticket_status) VALUES ('TICKET'||LPAD(TICKET_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT ticket_id,event_id,ticket_sort,ticket_name,ticket_amount,ticket_price,ticket_onsale_time,ticket_endsale_time,ticket_edit_time,ticket_status FROM TICKET ";
	private static final String GET_ONE_STMT = "SELECT TICKET_ID,EVENT_ID,TICKET_SORT,TICKET_NAME,TICKET_AMOUNT,TICKET_PRICE,TICKET_ONSALE_TIME,TICKET_ENDSALE_TIME,TICKET_EDIT_TIME,TICKET_STATUS FROM  TICKET WHERE TICKET_ID= ?";
	private static final String DELETE = "DELETE FROM ticket where ticket_id = ?";
	private static final String UPDATE = "UPDATE ticket set event_id=?,ticket_sort=?,ticket_name=?,ticket_amount=?,ticket_price=?,ticket_onsale_time=?,ticket_endsale_time=?,ticket_edit_time=?,ticket_status=? where ticket_id=? ";
	private static final String GET_LIST_BY_EVENTID = "SELECT TICKET_ID,EVENT_ID,TICKET_SORT,TICKET_NAME,TICKET_AMOUNT,TICKET_PRICE,TICKET_ONSALE_TIME,TICKET_ENDSALE_TIME,TICKET_EDIT_TIME,TICKET_STATUS FROM  TICKET WHERE EVENT_ID= ? ORDER BY TICKET_SORT" ;

	
	public void insert(TicketVO ticketVO ,Connection con ) {

		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ticketVO.getEvent_id());
			pstmt.setInt(2, ticketVO.getTicket_sort());
			pstmt.setString(3, ticketVO.getTicket_name());
			pstmt.setInt(4, ticketVO.getTicket_amount());
			pstmt.setInt(5, ticketVO.getTicket_price());
			pstmt.setTimestamp(6, ticketVO.getTicket_onsale_time());
			pstmt.setTimestamp(7, ticketVO.getTicket_endsale_time());
			pstmt.setTimestamp(8, ticketVO.getTicket_edit_time());
			pstmt.setInt(9, ticketVO.getTicket_status());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void insert(TicketVO ticketVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TicketVO ticketVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(String ticket_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TicketVO findByPrimaryKey(String ticket_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TicketVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TicketVO> findByEventId(String event_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
