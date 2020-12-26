package com.orderlist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.orders.model.OrdersDAO_interface;
import com.orders.model.OrdersJDBCDAO;
import com.orders.model.OrdersVO;

public class OrderListJDBCDAO implements OrderListDAO_interface {
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String password = "123456";

	private static final String INSERT_STMT = "INSERT INTO orderlist (orderlist_id,order_id,product_id,orderlist_goods_amount,orderlist_remarks,review_score,review_msg,review_time,review_hidden)VALUES('ORDERLIST'||LPAD(ORDERLIST_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM  orderlist order by orderlist_id";

	private static final String GET_ONE_STMT = "SELECT orderlist_id,order_id,product_id,orderlist_goods_amount,orderlist_remarks,review_score,review_msg,review_time,review_hidden FROM orderlist where orderlist_id=?";

	private static final String DELETE = "DELETE FROM orderlist where orderlist_id = ?";

	private static final String UPDATE = "UPDATE orderlist set orderlist_goods_amount=?, orderlist_remarks=?, review_score=?, review_msg=?, review_time=?, review_hidden=? where orderlist_id=?";

	private static final String FIND_BY_ORDER_ID_STMT = "SELECT * FROM ORDERLIST where ORDER_ID = ?";

	private static final String FIND_BY_POUDUCT_ID_STMT = "SELECT * FROM ORDERLIST where PRODUCT_ID = ?";

	@Override
	public void insert(OrderListVO orderListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderListVO.getOrder_id());
			pstmt.setString(2, orderListVO.getProduct_id());
			pstmt.setInt(3, orderListVO.getOrderlist_goods_amount());
			pstmt.setString(4, orderListVO.getOrderlist_remarks());
			pstmt.setInt(5, orderListVO.getReview_score());
			pstmt.setString(6, orderListVO.getReview_msg());
			pstmt.setTimestamp(7, orderListVO.getReview_time());
			pstmt.setInt(8, orderListVO.getReview_hidden());

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
	public void update(OrderListVO orderListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, orderListVO.getOrderlist_goods_amount());
			pstmt.setString(2, orderListVO.getOrderlist_remarks());
			pstmt.setInt(3, orderListVO.getReview_score());
			pstmt.setString(4, orderListVO.getReview_msg());
			pstmt.setTimestamp(5, orderListVO.getReview_time());
			pstmt.setInt(6, orderListVO.getReview_hidden());
			pstmt.setString(7, orderListVO.getOrderlist_id());
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
	public void delete(String orderlist_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
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
	public OrderListVO findByPrimaryKey(String orderlist_id) {
		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, orderlist_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderListVO = new OrderListVO();
				orderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				orderListVO.setOrder_id(rs.getString("order_id"));
				orderListVO.setProduct_id(rs.getString("product_id"));
				orderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				orderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
				orderListVO.setReview_score(rs.getInt("review_score"));
				orderListVO.setReview_msg(rs.getString("review_msg"));
				orderListVO.setReview_time(rs.getTimestamp("review_time"));
				orderListVO.setReview_hidden(rs.getInt("review_hidden"));

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
		return orderListVO;
	}

	@Override
	public List<OrderListVO> getAll() {
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderListVO = new OrderListVO();
				orderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				orderListVO.setOrder_id(rs.getString("order_id"));
				orderListVO.setProduct_id(rs.getString("product_id"));
				orderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				orderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
				orderListVO.setReview_score(rs.getInt("review_score"));
				orderListVO.setReview_msg(rs.getString("review_msg"));
				orderListVO.setReview_time(rs.getTimestamp("review_time"));
				orderListVO.setReview_hidden(rs.getInt("review_hidden"));
				list.add(orderListVO);
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
	public List<OrderListVO> findByOrderId(String order_Id) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(FIND_BY_ORDER_ID_STMT);
			pstmt.setString(1, order_Id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects
				OrderListVO vo = new OrderListVO();
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrderlist_goods_amount(rs.getInt("ORDERLIST_GOODS_AMOUNT"));
				vo.setOrderlist_id(rs.getString("ORDERLIST_ID"));
				vo.setOrderlist_remarks(rs.getString("ORDERLIST_REMARKS"));
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setReview_hidden(rs.getInt("REVIEW_HIDDEN"));
				vo.setReview_msg(rs.getString("REVIEW_MSG"));
				vo.setReview_score(rs.getInt("REVIEW_SCORE"));
				vo.setReview_time(rs.getTimestamp("REVIEW_TIME"));
				list.add(vo); // Store the row in the list
			}

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
	public List<OrderListVO> findByproductId(String product_Id) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(FIND_BY_POUDUCT_ID_STMT);
			pstmt.setString(1, product_Id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects
				OrderListVO vo = new OrderListVO();
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrderlist_goods_amount(rs.getInt("ORDERLIST_GOODS_AMOUNT"));
				vo.setOrderlist_id(rs.getString("ORDERLIST_ID"));
				vo.setOrderlist_remarks(rs.getString("ORDERLIST_REMARKS"));
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setReview_hidden(rs.getInt("REVIEW_HIDDEN"));
				vo.setReview_msg(rs.getString("REVIEW_MSG"));
				vo.setReview_score(rs.getInt("REVIEW_SCORE"));
				vo.setReview_time(rs.getTimestamp("REVIEW_TIME"));
				list.add(vo); // Store the row in the list
			}

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
//		OrderListJDBCDAO dao = new OrderListJDBCDAO();
////		GregorianCalendar gc = new GregorianCalendar(2020, 12, 7, 12, 07, 30);
//		Timestamp timestamp = new Timestamp(new Date().getTime());
//
//		// �s�W
//		OrderListVO orderListVO = new OrderListVO();
//		orderListVO.setOrder_id("ORDERS00250");
//		orderListVO.setProduct_id("PRODUCT00000");
//		orderListVO.setOrderlist_goods_amount(1);
//		orderListVO.setOrderlist_remarks("不要發票");
//		orderListVO.setReview_score(4);
//		orderListVO.setReview_msg("很好");
//		orderListVO.setReview_time(timestamp);
//		orderListVO.setReview_hidden(0);
//		dao.insert(orderListVO);

		// �ק�
//		OrderListVO orderListVO2 = new OrderListVO();
//
//		orderListVO2.setOrderlist_goods_amount(30);
//		orderListVO2.setOrderlist_remarks("���n�o��");
//		orderListVO2.setReview_score(4);
//		orderListVO2.setReview_msg("�@�Ŵ�");
//		orderListVO2.setReview_time(timestamp);
//		orderListVO2.setReview_hidden(0);
//		orderListVO2.setOrderlist_id("ORDERLIST00200");
//		dao.update(orderListVO2);

		// �R��
//		dao.delete("ORDERLIST00200");

		// �d�߳浧
//		OrderListVO orderListVO3 =dao.findByPrimaryKey("ORDERLIST00350");
//		System.out.print(orderListVO3.getOrderlist_id()+",");
//		System.out.print(orderListVO3.getOrder_id()+",");
//		System.out.print(orderListVO3.getProduct_id()+",");
//		System.out.print(orderListVO3.getOrderlist_goods_amount()+",");
//		System.out.print(orderListVO3.getOrderlist_remarks()+",");
//		System.out.print(orderListVO3.getReview_score()+",");
//		System.out.print(orderListVO3.getReview_msg()+",");
//		System.out.print(orderListVO3.getReview_time()+",");
//		System.out.print(orderListVO3.getReview_hidden());
//		
		// �d�ߥ���
//		List<OrderListVO> list = dao.getAll();
//		for (OrderListVO orderList : list) {
//			System.out.print(orderList.getOrderlist_id() + ",");
//			System.out.print(orderList.getOrder_id() + ",");
//			System.out.print(orderList.getProduct_id() + ",");
//			System.out.print(orderList.getOrderlist_goods_amount() + ",");
//			System.out.print(orderList.getOrderlist_remarks() + ",");
//			System.out.print(orderList.getReview_score() + ",");
//			System.out.print(orderList.getReview_msg() + ",");
//			System.out.print(orderList.getReview_time() + ",");
//			System.out.print(orderList.getReview_hidden());
//			System.out.println(orderList.toString());
//			System.out.println();
//		}
	}


