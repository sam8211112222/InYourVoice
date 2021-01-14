package com.orders.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersJDBCDAO implements OrdersDAO_interface {
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String password = "123456";

	private static final String INSERT_STMT = "INSERT INTO orders (order_id,member_id,order_status,order_place_time,order_name,order_mail,order_phone,order_delivery_time,order_received_time)VALUES('ORDERS'||LPAD(ORDERS_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM  orders order by order_place_time DESC";

	private static final String GET_ONE_STMT = "SELECT order_id,member_id,order_status,order_place_time,order_name,order_mail,order_phone,order_delivery_time,order_received_time FROM orders where order_id=?";

	private static final String GET_ONE_STMT_MAIL = "SELECT * FROM orders where order_mail=?";

	private static final String GET_ONE_STMT_MEMBER_ID = "SELECT * FROM orders where member_id=?";
	
	private static final String DELETE = "DELETE FROM orders where order_id = ?";

	private static final String UPDATE = "UPDATE orders set member_id=?, order_status=?, order_place_time=?, order_name=?, order_mail=?, order_phone=?, order_delivery_time=?, order_received_time=? where order_id=? ";

	private static final String GET_ONE_WITH_TOTAL_STMT_MEMBER_ID = "SELECT order_id,ORDERS.order_status,orders.order_place_time,sum(orderlist_goods_amount*price) as total_price FROM ORDERS INNER JOIN ORDERLIST USING(order_id) where orders.member_id = ? GROUP BY order_id,ORDERS.order_status,orders.order_place_time order by order_place_time DESC";
	
	@Override
	public OrdersVO insert(OrdersVO ordersVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "order_id" });

			pstmt.setString(1, ordersVO.getMember_id());
			pstmt.setInt(2, ordersVO.getOrder_status());
			pstmt.setTimestamp(3, ordersVO.getOrder_place_time());
			pstmt.setString(4, ordersVO.getOrder_name());
			pstmt.setString(5, ordersVO.getOrder_mail());
			pstmt.setString(6, ordersVO.getOrder_phone());
			pstmt.setTimestamp(7, ordersVO.getOrder_delivery_time());
			pstmt.setTimestamp(8, ordersVO.getOrder_received_time());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				String orderId = rs.getString(1);
				ordersVO.setOrder_id(orderId);
			}

			return ordersVO;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	public void update(OrdersVO ordersVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, ordersVO.getMember_id());
			pstmt.setInt(2, ordersVO.getOrder_status());
			pstmt.setTimestamp(3, ordersVO.getOrder_place_time());
			pstmt.setString(4, ordersVO.getOrder_name());
			pstmt.setString(5, ordersVO.getOrder_mail());
			pstmt.setString(6, ordersVO.getOrder_phone());
			pstmt.setTimestamp(7, ordersVO.getOrder_delivery_time());
			pstmt.setTimestamp(8, ordersVO.getOrder_received_time());
			pstmt.setString(9, ordersVO.getOrder_id());

			pstmt.executeUpdate();
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
	public void delete(String order_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, order_id);

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
	public OrdersVO findByPrimaryKey(String order_id) {

		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, order_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrder_id(rs.getString("order_id"));
				ordersVO.setMember_id(rs.getString("member_id"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				ordersVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
				ordersVO.setOrder_name(rs.getString("order_name"));
				ordersVO.setOrder_mail(rs.getString("order_mail"));
				ordersVO.setOrder_phone(rs.getString("order_phone"));
				ordersVO.setOrder_delivery_time(rs.getTimestamp("order_delivery_time"));
				ordersVO.setOrder_received_time(rs.getTimestamp("order_received_time"));

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
		return ordersVO;
	}

	@Override
	public List<OrdersVO> getAll() {
		List<OrdersVO> list = new ArrayList<OrdersVO>();

		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrder_id(rs.getString("order_id"));
				ordersVO.setMember_id(rs.getString("member_id"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				ordersVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
				ordersVO.setOrder_name(rs.getString("order_name"));
				ordersVO.setOrder_mail(rs.getString("order_mail"));
				ordersVO.setOrder_phone(rs.getString("order_phone"));
				ordersVO.setOrder_delivery_time(rs.getTimestamp("order_delivery_time"));
				ordersVO.setOrder_received_time(rs.getTimestamp("order_received_time"));
				list.add(ordersVO);
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
	public List<OrdersVO> findByEmail(String order_mail) {
		List<OrdersVO> list = new ArrayList<OrdersVO>();

		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT_MAIL);
			pstmt.setString(1, order_mail);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrder_id(rs.getString("order_id"));
				ordersVO.setMember_id(rs.getString("member_id"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				ordersVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
				ordersVO.setOrder_name(rs.getString("order_name"));
				ordersVO.setOrder_mail(rs.getString("order_mail"));
				ordersVO.setOrder_phone(rs.getString("order_phone"));
				ordersVO.setOrder_delivery_time(rs.getTimestamp("order_delivery_time"));
				ordersVO.setOrder_received_time(rs.getTimestamp("order_received_time"));
				list.add(ordersVO);
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
	public List<OrdersVO> findByMemberId(String memberId) {
		List<OrdersVO> list = new ArrayList<OrdersVO>();

		OrdersVO ordersVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_WITH_TOTAL_STMT_MEMBER_ID);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ordersVO = new OrdersVO();
				ordersVO.setOrder_id(rs.getString("order_id"));
//				ordersVO.setMember_id(rs.getString("member_id"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				ordersVO.setOrder_place_time(rs.getTimestamp("order_place_time"));
//				ordersVO.setOrder_name(rs.getString("order_name"));
//				ordersVO.setOrder_mail(rs.getString("order_mail"));
//				ordersVO.setOrder_phone(rs.getString("order_phone"));
//				ordersVO.setOrder_delivery_time(rs.getTimestamp("order_delivery_time"));
//				ordersVO.setOrder_received_time(rs.getTimestamp("order_received_time"));
				ordersVO.setTotal_price(rs.getInt("total_price"));
				list.add(ordersVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

//	public static void main(String[] args) {
//		OrdersJDBCDAO dao = new OrdersJDBCDAO();
////		GregorianCalendar gc = new GregorianCalendar(2020, 12, 7, 12, 07, 30);
//		Timestamp timestamp = new Timestamp(new Date().getTime());
//
//		// �s�W
//		OrdersVO ordersVO = new OrdersVO();
//		ordersVO.setMember_id("MEMBER00250");
//		ordersVO.setOrder_status(0);
//		ordersVO.setOrder_place_time(timestamp);
//		ordersVO.setOrder_name("�x౲[");
//		ordersVO.setOrder_mail("aa@gmail.com");
//		ordersVO.setOrder_phone("0989232637");
//		ordersVO.setOrder_delivery_time(timestamp);
//		ordersVO.setOrder_received_time(new Timestamp(new Date().getTime()));
//		dao.insert(ordersVO);
//
//		// �ק�
//		OrdersVO ordersVO2 = new OrdersVO();
//		ordersVO2.setOrder_status(1);
//		ordersVO2.setOrder_place_time(timestamp);
//		ordersVO2.setOrder_name("�d�ç�");
//		ordersVO2.setOrder_mail("bb@gmail.com");
//		ordersVO2.setOrder_phone("0911111111");
//		ordersVO2.setOrder_delivery_time(timestamp);
//		ordersVO2.setOrder_received_time(new Timestamp(new Date().getTime()));
//		ordersVO2.setOrder_id("ORDERS00600");
//		dao.update(ordersVO2);
//
//		// �R��
//		dao.delete("ORDERS00800");
//
//		// �d�߳浧
//		OrdersVO ordersVO3 = dao.findByPrimaryKey("ORDERS00750");
//		System.out.print(ordersVO3.getOrder_id() + ",");
//		System.out.print(ordersVO3.getMember_id() + ",");
//		System.out.print(ordersVO3.getOrder_status() + ",");
//		System.out.print(ordersVO3.getOrder_place_time() + ",");
//		System.out.print(ordersVO3.getOrder_name() + ",");
//		System.out.print(ordersVO3.getOrder_mail() + ",");
//		System.out.print(ordersVO3.getOrder_phone() + ",");
//		System.out.print(ordersVO3.getOrder_delivery_time() + ",");
//		System.out.print(ordersVO3.getOrder_delivery_time());
//
//		// �d�ߥ���
//		List<OrdersVO> list = dao.getAll();
//		for (OrdersVO orders : list) {
//			System.out.print(orders.getOrder_id() + ",");
//			System.out.print(orders.getMember_id() + ",");
//			System.out.print(orders.getOrder_status() + ",");
//			System.out.print(orders.getOrder_place_time() + ",");
//			System.out.print(orders.getOrder_name() + ",");
//			System.out.print(orders.getOrder_mail() + ",");
//			System.out.print(orders.getOrder_phone() + ",");
//			System.out.print(orders.getOrder_delivery_time() + ",");
//			System.out.print(orders.getOrder_delivery_time());
//			System.out.println();
//		}
//
//	}
