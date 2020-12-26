package com.eventorder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ticket.model.TicketVO;

public class EventOrderJDBCDAO implements EventOrderDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO eventorder (event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone) VALUES ('EVENT_ORDER'||LPAD(EVENTORDER_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone FROM eventorder order by event_order_id ";
	private static final String GET_ONE_STMT = "SELECT event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone FROM  eventorder WHERE event_order_id = ?";
	private static final String DELETE = "DELETE FROM eventorder where event_order_id = ?";
	private static final String UPDATE = "UPDATE eventorder set member_id = ?,event_id = ?,order_place_time = ?,order_name = ?,order_mail = ?,order_phone = ? where event_order_id=? ";
	@Override
	public void insert(EventOrderVO eventOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eventOrderVO.getMember_id());
			pstmt.setString(2, eventOrderVO.getEvent_id());
			pstmt.setTimestamp(3, eventOrderVO.getOrder_place_time());
			pstmt.setString(4, eventOrderVO.getOrder_name());
			pstmt.setString(5, eventOrderVO.getOrder_mail());
			pstmt.setString(6, eventOrderVO.getOrder_phone());

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
	public void update(EventOrderVO eventOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventOrderVO.getMember_id());
			pstmt.setString(2, eventOrderVO.getEvent_id());
			pstmt.setTimestamp(3, eventOrderVO.getOrder_place_time());
			pstmt.setString(4, eventOrderVO.getOrder_name());
			pstmt.setString(5, eventOrderVO.getOrder_mail());
			pstmt.setString(6, eventOrderVO.getOrder_phone());
			pstmt.setString(7, eventOrderVO.getEvent_order_id());

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
	public void delete(String event_order_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, event_order_id);

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
	public EventOrderVO findByPrimaryKey(String event_order_id) {
		EventOrderVO eventOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, event_order_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventOrderVO = new EventOrderVO();
				eventOrderVO.setEvent_order_id(rs.getString("event_order_id"));
				eventOrderVO.setMember_id(rs.getString("member_id"));
				eventOrderVO.setEvent_id(rs.getString("event_id"));
				eventOrderVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
				eventOrderVO.setOrder_name(rs.getString("order_name"));
				eventOrderVO.setOrder_mail(rs.getString("order_mail"));
				eventOrderVO.setOrder_phone(rs.getString("order_phone"));

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
		return eventOrderVO;
	}

	@Override
	public List<EventOrderVO> getAll() {
		List<EventOrderVO> list = new ArrayList<EventOrderVO>();
		EventOrderVO eventOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventOrderVO = new EventOrderVO();
				eventOrderVO.setEvent_order_id(rs.getString("event_order_id"));
				eventOrderVO.setMember_id(rs.getString("member_id"));
				eventOrderVO.setEvent_id(rs.getString("event_id"));
				eventOrderVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
				eventOrderVO.setOrder_name(rs.getString("order_name"));
				eventOrderVO.setOrder_mail(rs.getString("order_mail"));
				eventOrderVO.setOrder_phone(rs.getString("order_phone"));
				list.add(eventOrderVO); // Store the row in the list
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
//	public static void main(String[] args) {
//		Date date = new Date();
//		Timestamp timestamp = new Timestamp(date.getTime());
//		EventOrderJDBCDAO eventOrderJDBCDAO = new EventOrderJDBCDAO();
//
//		// ���շs�W
//		EventOrderVO eventOrderVO1 = new EventOrderVO();
//		eventOrderVO1.setEvent_id("EVENT00000");
//		eventOrderVO1.setMember_id("MEMBERS00000");
//		eventOrderVO1.setOrder_name("�p�Q");
//		eventOrderVO1.setOrder_place_time(timestamp);
//		eventOrderVO1.setOrder_mail("leo@ap");
//		eventOrderVO1.setOrder_phone("0912345678");
//
//		eventOrderJDBCDAO.insert(eventOrderVO1);

//		// ���խק�
//		EventOrderVO eventOrderVO2 = new EventOrderVO();
//		eventOrderVO2.setEvent_order_id("EVENT_ORDER00350");
//		eventOrderVO2.setEvent_id("EVENT00100");
//		eventOrderVO2.setMember_id("MEMBERS00000");
//		eventOrderVO2.setOrder_name("�p�Q");
//		eventOrderVO2.setOrder_place_time(timestamp);
//		eventOrderVO2.setOrder_mail("leo@ap");
//		eventOrderVO2.setOrder_phone("0912345678");
//
//		eventOrderJDBCDAO.update(eventOrderVO2);

		//���էR��
//		eventOrderJDBCDAO.delete("EVENT_ORDER00350");
//		//���ճ浧�d��
//		EventOrderVO eventOrderVO3 = eventOrderJDBCDAO.findByPrimaryKey("EVENT_ORDER00000");
//		System.out.println(eventOrderVO3.toString());
		
		
		//���լd�ߥ���
//		List<EventOrderVO> list = eventOrderJDBCDAO.getAll();
//		
//		for(EventOrderVO eventOrderVO:list) {
//			System.out.println(eventOrderVO.toString());
//		}
//		
//	}


}
