package com.eventorderlist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EventOrderListJNDIDAO implements EventOrderListDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO eventorderlist (orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks,orderlist_status) VALUES ('EVENTORDERLIST'||LPAD(EVENTORDERLIST_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks ,orderlist_status FROM eventorderlist order by orderlist_id ";
	private static final String GET_ONE_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks ,orderlist_status FROM  eventorderlist WHERE orderlist_id = ?";
	private static final String DELETE = "DELETE FROM eventorderlist where orderlist_id = ?";
	private static final String UPDATE = "UPDATE eventorderlist set ticket_id = ?,event_order_id = ?,orderlist_goods_amount = ?,orderlist_remarks = ?,orderlist_status=? where orderlist_id=? ";
	private static final String GET_LIST_STMT = "SELECT orderlist_id,ticket_id,event_order_id,orderlist_goods_amount,orderlist_remarks ,orderlist_status FROM  eventorderlist WHERE event_order_id = ?";

	@Override
	public String insert(Connection con, EventOrderListVO eventOrderListVO) {

		PreparedStatement pstmt = null;
		String pk = null;
		try {

			String[] cols = { "orderlist_id" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, eventOrderListVO.getTicket_id());
			pstmt.setString(2, eventOrderListVO.getEvent_order_id());
			pstmt.setInt(3, eventOrderListVO.getOrderlist_goods_amount());
			pstmt.setString(4, eventOrderListVO.getOrderlist_remarks());
			pstmt.setInt(5, eventOrderListVO.getOrderlist_status());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			while (rs.next()) {
				pk = rs.getString(1);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		
		return pk;
	}

	@Override
	public void update(EventOrderListVO eventOrderListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventOrderListVO.getTicket_id());
			pstmt.setString(2, eventOrderListVO.getEvent_order_id());
			pstmt.setInt(3, eventOrderListVO.getOrderlist_goods_amount());
			pstmt.setString(4, eventOrderListVO.getOrderlist_remarks());
			pstmt.setInt(5, eventOrderListVO.getOrderlist_status());
			pstmt.setString(6, eventOrderListVO.getOrderlist_id());

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, orderlist_id);

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

			con = ds.getConnection();
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
				eventOrderListVO.setOrderlist_status(rs.getInt("orderlist_status"));

			}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventOrderListVO = new EventOrderListVO();
				eventOrderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				eventOrderListVO.setTicket_id(rs.getString("ticket_id"));
				eventOrderListVO.setEvent_order_id(rs.getString("event_order_id"));
				eventOrderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				eventOrderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
				eventOrderListVO.setOrderlist_status(rs.getInt("orderlist_status"));
				list.add(eventOrderListVO); // Store the row in the list
			}

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
//			
//			con = ds.getConnection();
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
