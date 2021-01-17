package com.eventorder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.eventorderlist.model.EventOrderListDAO;
import com.eventorderlist.model.EventOrderListJNDIDAO;
import com.eventorderlist.model.EventOrderListVO;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class EventOrderJNDIDAO implements EventOrderDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String INSERT_STMT = "INSERT INTO eventorder (event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone) VALUES ('EVENT_ORDER'||LPAD(EVENTORDER_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone FROM eventorder order by event_order_id ";
	private static final String GET_ONE_STMT = "SELECT event_order_id,member_id,event_id,order_place_time,order_name,order_mail,order_phone FROM  eventorder WHERE event_order_id = ?";
	private static final String DELETE = "DELETE FROM eventorder where event_order_id = ?";
	private static final String UPDATE = "UPDATE eventorder set member_id = ?,event_id = ?,order_place_time = ?,order_name = ?,order_mail = ?,order_phone = ? where event_order_id=? ";

	@Override
	public Map<String, List<String>> insert(EventOrderVO eventOrderVO,List<EventOrderListVO> eventOrderList) {

		Connection con = null;
		PreparedStatement pstmt = null;
		EventOrderListDAO dao = new EventOrderListJNDIDAO();
		List<String> orderListIds = new ArrayList<String>();
		String pk = null;
		Map<String,List<String>> orders = new HashMap<String,List<String>>();

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			String[] cols = { "event_order_id" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, eventOrderVO.getMember_id());
			pstmt.setString(2, eventOrderVO.getEvent_id());
			pstmt.setTimestamp(3, eventOrderVO.getOrder_place_time());
			pstmt.setString(4, eventOrderVO.getOrder_name());
			pstmt.setString(5, eventOrderVO.getOrder_mail());
			pstmt.setString(6, eventOrderVO.getOrder_phone());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			
			while (rs.next()) {
				 pk = rs.getString(1);
			}
			
			for(EventOrderListVO eventOrderListVO:eventOrderList) {
				for(int i = 0 ; i<eventOrderListVO.getOrderlist_goods_amount();i++) {
					EventOrderListVO newVO = new EventOrderListVO();
					newVO.setOrderlist_goods_amount(1);
					newVO.setEvent_order_id(pk);
					newVO.setOrderlist_remarks(eventOrderListVO.getOrderlist_remarks());
					newVO.setOrderlist_status(eventOrderListVO.getOrderlist_status());
					newVO.setTicket_id(eventOrderListVO.getTicket_id());
					String orderlist_id = dao.insert(con, newVO);
					orderListIds.add(orderlist_id);
				}
			}
			
			
			orders.put(pk, orderListIds);
			
			con.commit();

			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return orders;

	}

	@Override
	public void update(EventOrderVO eventOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventOrderVO.getMember_id());
			pstmt.setString(2, eventOrderVO.getEvent_id());
			pstmt.setTimestamp(3, eventOrderVO.getOrder_place_time());
			pstmt.setString(4, eventOrderVO.getOrder_name());
			pstmt.setString(5, eventOrderVO.getOrder_mail());
			pstmt.setString(6, eventOrderVO.getOrder_phone());
			pstmt.setString(7, eventOrderVO.getEvent_order_id());

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
	public void delete(String event_order_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, event_order_id);

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
	public EventOrderVO findByPrimaryKey(String event_order_id) {
		EventOrderVO eventOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
