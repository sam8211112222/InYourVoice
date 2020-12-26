package com.eventorderlist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.event.model.EventVO;
import com.eventorder.model.EventOrderVO;

public class EventOrderListJDBCDAO implements EventOrderListDAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO eventorderlist (orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks) VALUES ('EVENTORDERLIST'||LPAD(EVENTORDERLIST_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks FROM eventorderlist order by orderlist_id ";
	private static final String GET_ONE_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks FROM  eventorderlist WHERE orderlist_id = ?";
	private static final String DELETE = "DELETE FROM eventorderlist where orderlist_id = ?";
	private static final String UPDATE = "UPDATE eventorderlist set ticket_id = ?,event_order_id = ?,orderlist_goods_amount = ?,orderlist_remarks = ? where orderlist_id=? ";
	private static final String GET_LIST_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks FROM  eventorderlist WHERE event_order_id = ?";
	
	@Override
	public void insert(EventOrderListVO eventOrderListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eventOrderListVO.getTicket_id());
			pstmt.setString(2, eventOrderListVO.getEvent_order_id());
			pstmt.setInt(3, eventOrderListVO.getOrderlist_goods_amount());
			pstmt.setString(4, eventOrderListVO.getOrderlist_remarks());

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
	public void update(EventOrderListVO eventOrderListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventOrderListVO.getTicket_id());
			pstmt.setString(2, eventOrderListVO.getEvent_order_id());
			pstmt.setInt(3, eventOrderListVO.getOrderlist_goods_amount());
			pstmt.setString(4, eventOrderListVO.getOrderlist_remarks());
			pstmt.setString(5, eventOrderListVO.getOrderlist_id());

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
	public void delete(String orderlist_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, orderlist_id);

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
	public EventOrderListVO findByPrimaryKey(String orderlist_id) {
		EventOrderListVO eventOrderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, orderlist_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventOrderListVO = new EventOrderListVO();
				eventOrderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				eventOrderListVO.setTicket_id(rs.getString("ticket_id"));
				eventOrderListVO.setEvent_order_id(rs.getString("event_order_id"));
				eventOrderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				eventOrderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));

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
		return eventOrderListVO;
	}

	@Override
	public List<EventOrderListVO> getAll() {
		List<EventOrderListVO> list = new ArrayList<EventOrderListVO>();
		EventOrderListVO eventOrderListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventOrderListVO = new EventOrderListVO();
				eventOrderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				eventOrderListVO.setTicket_id(rs.getString("ticket_id"));
				eventOrderListVO.setEvent_order_id(rs.getString("event_order_id"));
				eventOrderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				eventOrderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
				list.add(eventOrderListVO); // Store the row in the list
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

	public static void main(String[] args) {
		EventOrderListJDBCDAO eventOrderListJDBCDAO = new EventOrderListJDBCDAO();

//		// 測試新增
//		EventOrderListVO eventOrderListVO1 = new EventOrderListVO();
//		eventOrderListVO1.setTicket_id("TICKET00000");
//		eventOrderListVO1.setEvent_order_id("EVENT_ORDER00000");
//		eventOrderListVO1.setOrderlist_goods_amount(8);
//		eventOrderListVO1.setOrderlist_remarks("測試新增備註");
//		
//		eventOrderListJDBCDAO.insert(eventOrderListVO1);

//		// 測試修改 
//		
//		EventOrderListVO eventOrderListVO2 = new EventOrderListVO();
//		eventOrderListVO2.setTicket_id("TICKET00050");
//		eventOrderListVO2.setEvent_order_id("EVENT_ORDER00050");
//		eventOrderListVO2.setOrderlist_goods_amount(8);
//		eventOrderListVO2.setOrderlist_remarks("測試修改備註");
//		eventOrderListVO2.setOrderlist_id("EVENTORDERLIST00150");
//		
//		eventOrderListJDBCDAO.update(eventOrderListVO2);

		// 測試刪除
//		eventOrderListJDBCDAO.delete("EVENTORDERLIST00350");

//		//測試查詢單筆
//		EventOrderListVO eventOrderListVO3 =eventOrderListJDBCDAO.findByPrimaryKey("EVENTORDERLIST00000");
//		System.out.println(eventOrderListVO3.toString());

		// 測試查詢全部
		List<EventOrderListVO> list = eventOrderListJDBCDAO.getAll();
		for (EventOrderListVO eventOrderListVO : list) {
			System.out.println(eventOrderListVO.toString());
		}

	}

	@Override
	public List<EventOrderListVO> getListByOrderId(String event_order_id) {
		return null;
//		List<EventOrderListVO> list = new ArrayList<EventOrderListVO>();
//		EventOrderListVO eventOrderListVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_LIST_STMT);
//
//			pstmt.setString(1, event_order_id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				eventOrderListVO = new EventOrderListVO();
//				eventOrderListVO.setOrderlist_id(rs.getString("orderlist_id"));
//				eventOrderListVO.setTicket_id(rs.getString("ticket_id"));
//				eventOrderListVO.setEvent_order_id(rs.getString("event_order_id"));
//				eventOrderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
//				eventOrderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
//
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return eventOrderListVO;
	}
}
