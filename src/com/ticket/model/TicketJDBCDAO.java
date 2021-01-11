package com.ticket.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TicketJDBCDAO implements TicketDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ticket (ticket_id,event_id,ticket_sort,ticket_name,ticket_amount,ticket_price,ticket_onsale_time,ticket_endsale_time,ticket_edit_time,ticket_status) VALUES ('TICKET'||LPAD(TICKET_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT ticket_id,event_id,ticket_sort,ticket_name,ticket_amount,ticket_price,ticket_onsale_time,ticket_endsale_time,ticket_edit_time,ticket_status FROM TICKET ";
	private static final String GET_ONE_STMT = "SELECT TICKET_ID,EVENT_ID,TICKET_SORT,TICKET_NAME,TICKET_AMOUNT,TICKET_PRICE,TICKET_ONSALE_TIME,TICKET_ENDSALE_TIME,TICKET_EDIT_TIME,TICKET_STATUS FROM  TICKET WHERE TICKET_ID= ?";
	private static final String DELETE = "DELETE FROM ticket where ticket_id = ?";
	private static final String UPDATE = "UPDATE ticket set event_id=?,ticket_sort=?,ticket_name=?,ticket_amount=?,ticket_price=?,ticket_onsale_time=?,ticket_endsale_time=?,ticket_edit_time=?,ticket_status=? where ticket_id=? ";
	private static final String GET_LIST_BY_EVENTID = "SELECT TICKET_ID,EVENT_ID,TICKET_SORT,TICKET_NAME,TICKET_AMOUNT,TICKET_PRICE,TICKET_ONSALE_TIME,TICKET_ENDSALE_TIME,TICKET_EDIT_TIME,TICKET_STATUS FROM  TICKET WHERE EVENT_ID= ? ORDER BY TICKET_SORT" ;

	@Override
	public void insert(TicketVO ticketVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	@Override
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
	public void update(TicketVO ticketVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ticketVO.getEvent_id());
			pstmt.setInt(2, ticketVO.getTicket_sort());
			pstmt.setString(3, ticketVO.getTicket_name());
			pstmt.setInt(4, ticketVO.getTicket_amount());
			pstmt.setInt(5, ticketVO.getTicket_price());
			pstmt.setTimestamp(6, ticketVO.getTicket_onsale_time());
			pstmt.setTimestamp(7, ticketVO.getTicket_endsale_time());
			pstmt.setTimestamp(8, ticketVO.getTicket_edit_time());
			pstmt.setInt(9, ticketVO.getTicket_status());
			pstmt.setString(10, ticketVO.getTicket_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public int delete(String ticket_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int success;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ticket_id);

			success = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return success;

	}

	@Override
	public TicketVO findByPrimaryKey(String ticket_id) {
		TicketVO ticketVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ticket_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticketVO = new TicketVO();
				ticketVO.setTicket_id(rs.getString("ticket_id"));
				ticketVO.setEvent_id(rs.getString("event_id"));
				ticketVO.setTicket_sort(rs.getInt("ticket_sort"));
				ticketVO.setTicket_name(rs.getString("ticket_name"));
				ticketVO.setTicket_amount(rs.getInt("ticket_amount"));
				ticketVO.setTicket_price(rs.getInt("ticket_price"));
				ticketVO.setTicket_onsale_time(rs.getTimestamp("ticket_onsale_time"));
				ticketVO.setTicket_endsale_time(rs.getTimestamp("ticket_endsale_time"));
				ticketVO.setTicket_edit_time(rs.getTimestamp("ticket_edit_time"));
				ticketVO.setTicket_status(rs.getInt("ticket_status"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return ticketVO;
	}

	@Override
	public List<TicketVO> getAll() {
		List<TicketVO> list = new ArrayList<TicketVO>();
		TicketVO ticketVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticketVO = new TicketVO();
				ticketVO.setTicket_id(rs.getString("ticket_id"));
				ticketVO.setEvent_id(rs.getString("event_id"));
				ticketVO.setTicket_sort(rs.getInt("ticket_sort"));
				ticketVO.setTicket_name(rs.getString("ticket_name"));
				ticketVO.setTicket_amount(rs.getInt("ticket_amount"));
				ticketVO.setTicket_price(rs.getInt("ticket_price"));
				ticketVO.setTicket_onsale_time(rs.getTimestamp("ticket_onsale_time"));
				ticketVO.setTicket_endsale_time(rs.getTimestamp("ticket_endsale_time"));
				ticketVO.setTicket_edit_time(rs.getTimestamp("ticket_edit_time"));
				ticketVO.setTicket_status(rs.getInt("ticket_status"));
				list.add(ticketVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	@Override
	public List<TicketVO> findByEventId(String event_id) {
		List<TicketVO> list = new ArrayList<TicketVO>();
		TicketVO ticketVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LIST_BY_EVENTID);

			pstmt.setString(1, event_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticketVO = new TicketVO();
				ticketVO.setTicket_id(rs.getString("ticket_id"));
				ticketVO.setEvent_id(rs.getString("event_id"));
				ticketVO.setTicket_sort(rs.getInt("ticket_sort"));
				ticketVO.setTicket_name(rs.getString("ticket_name"));
				ticketVO.setTicket_amount(rs.getInt("ticket_amount"));
				ticketVO.setTicket_price(rs.getInt("ticket_price"));
				ticketVO.setTicket_onsale_time(rs.getTimestamp("ticket_onsale_time"));
				ticketVO.setTicket_endsale_time(rs.getTimestamp("ticket_endsale_time"));
				ticketVO.setTicket_edit_time(rs.getTimestamp("ticket_edit_time"));
				ticketVO.setTicket_status(rs.getInt("ticket_status"));
				list.add(ticketVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
