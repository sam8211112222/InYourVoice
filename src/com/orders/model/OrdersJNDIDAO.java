package com.orders.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utils.DataSourceUtils;

public class OrdersJNDIDAO implements OrdersDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO orders (order_id,member_id,order_status,order_place_time,order_name,order_mail,order_phone,order_delivery_time,order_received_time)VALUES(ORDERS_SEQ:NEXTVAL,?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM orders";

	private static final String GET_ONE_STMT_MAIL = "SELECT * FROM orders where order_mail=?";
	
	private static final String GET_ONE_STMT = "SELECT * FROM orders where order_id=?";

	@Override
	public OrdersVO insert(OrdersVO ordersVO) {
		return null;
	}

	@Override
	public void update(OrdersVO ordersVO) {

	}

	@Override
	public void delete(String order_id) {

	}

	@Override
	public OrdersVO findByPrimaryKey(String order_id) {
		OrdersVO vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DataSourceUtils.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects

				vo = new OrdersVO();
				vo.setMember_id(rs.getString("MEMBER_ID"));
				vo.setOrder_delivery_time(rs.getTimestamp("ORDER_DELIVERY_TIME"));
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrder_mail(rs.getString("ORDER_MAIL"));
				vo.setOrder_name(rs.getString("ORDER_NAME"));
				vo.setOrder_phone(rs.getString("ORDER_PHONE"));
				vo.setOrder_place_time(rs.getTimestamp("ORDER_PLACE_TIME"));
				vo.setOrder_received_time(rs.getTimestamp("ORDER_RECEIVED_TIME"));
				vo.setOrder_status(rs.getInt("ORDER_STATUS"));
			}

			// Handle any driver errors
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
		return vo;
	}

	@Override
	public List<OrdersVO> getAll() {
		List<OrdersVO> list = new ArrayList<OrdersVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DataSourceUtils.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects

				OrdersVO vo = new OrdersVO();
				vo.setMember_id(rs.getString("MEMBER_ID"));
				vo.setOrder_delivery_time(rs.getTimestamp("ORDER_DELIVERY_TIME"));
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrder_mail(rs.getString("ORDER_MAIL"));
				vo.setOrder_name(rs.getString("ORDER_NAME"));
				vo.setOrder_phone(rs.getString("ORDER_PHONE"));
				vo.setOrder_place_time(rs.getTimestamp("ORDER_PLACE_TIME"));
				vo.setOrder_received_time(rs.getTimestamp("ORDER_RECEIVED_TIME"));
				vo.setOrder_status(rs.getInt("ORDER_STATUS"));
				list.add(vo); // Store the row in the list
			}

			// Handle any driver errors
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

	public List<OrdersVO> findByEmail(String order_mail) {
		List<OrdersVO> list = new ArrayList<OrdersVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DataSourceUtils.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_MAIL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects

				OrdersVO vo = new OrdersVO();
				vo.setMember_id(rs.getString("MEMBER_ID"));
				vo.setOrder_delivery_time(rs.getTimestamp("ORDER_DELIVERY_TIME"));
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrder_mail(rs.getString("ORDER_MAIL"));
				vo.setOrder_name(rs.getString("ORDER_NAME"));
				vo.setOrder_phone(rs.getString("ORDER_PHONE"));
				vo.setOrder_place_time(rs.getTimestamp("ORDER_PLACE_TIME"));
				vo.setOrder_received_time(rs.getTimestamp("ORDER_RECEIVED_TIME"));
				vo.setOrder_status(rs.getInt("ORDER_STATUS"));
				list.add(vo); // Store the row in the list
			}

			// Handle any driver errors
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
	public List<OrdersVO> findByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}




}
