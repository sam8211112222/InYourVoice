package com.orderlist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderListJDBCDAO implements OrderListDAO_interface {
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String password = "123456";

	private static final String INSERT_STMT = "INSERT INTO orderlist (orderlist_id,order_id,product_id,orderlist_goods_amount,orderlist_remarks,review_score,review_msg,review_time,review_hidden,price)VALUES('ORDERLIST'||LPAD(ORDERLIST_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?,?)";

	private static final String GET_ALL_STMT = "SELECT * FROM  orderlist order by orderlist_id";

	private static final String GET_ONE_STMT = "SELECT * FROM orderlist where orderlist_id=?";

	private static final String DELETE = "DELETE FROM orderlist where orderlist_id = ?";

	private static final String UPDATE = "UPDATE orderlist set orderlist_goods_amount=?, orderlist_remarks=?, review_score=?, review_msg=?, review_time=?, review_hidden=?,price=? where orderlist_id=?";

	private static final String FIND_BY_ORDER_ID_STMT = "SELECT * FROM ORDERLIST where ORDER_ID = ?";

	private static final String FIND_BY_POUDUCT_ID_STMT = "SELECT * FROM ORDERLIST where PRODUCT_ID = ?";

	private static final String UPDATE_REVIEW = "UPDATE orderlist set review_score=?, review_msg=?, review_time=? where orderlist_id=?";

	private static final String FIND_REVIEW_BY_PRODUCT_ID = "select ol.REVIEW_SCORE,ol.REVIEW_MSG,ol.REVIEW_TIME,o.member_id,m.member_name,m.member_nickname,m.member_photo from orderlist ol join orders o on ol.order_id=o.order_id join members m on o.member_id=m.member_id where ol.product_id=? and ol.review_time is not null order by ol.review_time desc";
	
	@Override
	public OrderListVO insert(OrderListVO orderListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT, new String[] { "ORDERLIST_ID" });

			pstmt.setString(1, orderListVO.getOrder_id());
			pstmt.setString(2, orderListVO.getProduct_id());
			pstmt.setInt(3,
					orderListVO.getOrderlist_goods_amount() != null ? orderListVO.getOrderlist_goods_amount() : 0);
			pstmt.setString(4, orderListVO.getOrderlist_remarks());
			pstmt.setInt(5, orderListVO.getReview_score() != null ? orderListVO.getReview_score() : 0);
			pstmt.setString(6, orderListVO.getReview_msg());
			pstmt.setTimestamp(7, orderListVO.getReview_time());
			pstmt.setInt(8, orderListVO.getReview_hidden() != null ? orderListVO.getReview_hidden() : 0);
			pstmt.setInt(9, orderListVO.getPrice() != null ? orderListVO.getPrice() : 0);

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				String orderlist_id = rs.getString(1);
				orderListVO.setOrderlist_id(orderlist_id);
			}

			return orderListVO;

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

			pstmt.setInt(1,
					orderListVO.getOrderlist_goods_amount() != null ? orderListVO.getOrderlist_goods_amount() : 0);
			pstmt.setString(2, orderListVO.getOrderlist_remarks());
			pstmt.setInt(3, orderListVO.getReview_score() != null ? orderListVO.getReview_score() : 0);
			pstmt.setString(4, orderListVO.getReview_msg());
			pstmt.setTimestamp(5, orderListVO.getReview_time());
			pstmt.setInt(6, orderListVO.getReview_hidden() != null ? orderListVO.getReview_hidden() : 0);
			pstmt.setString(7, orderListVO.getOrderlist_id());
			pstmt.setInt(8, orderListVO.getPrice() != null ? orderListVO.getPrice() : 0);
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
				orderListVO.setPrice(rs.getInt("price"));

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
				orderListVO.setPrice(rs.getInt("PRICE"));
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
				vo.setOrderlist_id(rs.getString("ORDERLIST_ID"));
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setOrderlist_goods_amount(rs.getInt("ORDERLIST_GOODS_AMOUNT"));
				vo.setOrderlist_remarks(rs.getString("ORDERLIST_REMARKS"));
				vo.setReview_score(rs.getInt("REVIEW_SCORE"));
				vo.setReview_msg(rs.getString("REVIEW_MSG"));
				vo.setReview_time(rs.getTimestamp("REVIEW_TIME"));
				vo.setReview_hidden(rs.getInt("REVIEW_HIDDEN"));
				vo.setPrice(rs.getInt("PRICE"));
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
				vo.setPrice(rs.getInt("PRICE"));
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

//	@Override
//	public void insertOrderList(OrderListVO orderListVO, Connection con) {
//		PreparedStatement pstmt = null;
//
//		try {
//			pstmt = con.prepareStatement(INSERT_STMT);
//			pstmt.setString(1, orderListVO.getOrder_id());
//			pstmt.setString(2, orderListVO.getProduct_id());
//			pstmt.setInt(3, orderListVO.getOrderlist_goods_amount()!=null? orderListVO.getOrderlist_goods_amount():0);
//			pstmt.setString(4, orderListVO.getOrderlist_remarks());
//			pstmt.setInt(5, orderListVO.getReview_score()!=null? orderListVO.getReview_score():0);
//			pstmt.setString(6, orderListVO.getReview_msg());
//			pstmt.setTimestamp(7, orderListVO.getReview_time());
//			pstmt.setInt(8, orderListVO.getReview_hidden()!=null? orderListVO.getReview_hidden():0);
//			pstmt.setInt(9, orderListVO.getPrice()!=null? orderListVO.getPrice():0);
//
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			if (con != null) {
//				try {
//					// 3●設定於當有exception發生時之catch區塊內
//					System.err.print("Transaction is being ");
//					System.err.println("rolled back-由-emp");
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. "
//							+ excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}

	@Override
	public List<OrderListVO> insertList(List<OrderListVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			con.setAutoCommit(false);
			for (OrderListVO orderListVO : list) {
				pstmt = con.prepareStatement(INSERT_STMT, new String[] { "ORDERLIST_ID" });

				pstmt.setString(1, orderListVO.getOrder_id());
				pstmt.setString(2, orderListVO.getProduct_id());
				pstmt.setInt(3,
						orderListVO.getOrderlist_goods_amount() != null ? orderListVO.getOrderlist_goods_amount() : 0);
				pstmt.setString(4, orderListVO.getOrderlist_remarks());
				pstmt.setInt(5, orderListVO.getReview_score() != null ? orderListVO.getReview_score() : 0);
				pstmt.setString(6, orderListVO.getReview_msg());
				pstmt.setTimestamp(7, orderListVO.getReview_time());
				pstmt.setInt(8, orderListVO.getReview_hidden() != null ? orderListVO.getReview_hidden() : 0);
				pstmt.setInt(9, orderListVO.getPrice() != null ? orderListVO.getPrice() : 0);

				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					String orderlist_id = rs.getString(1);
					orderListVO.setOrderlist_id(orderlist_id);
				}
			}
			con.commit();
			con.setAutoCommit(true);
			return list;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-OrderItem");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	public void updeteReview(OrderListVO orderListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE_REVIEW);

			pstmt.setInt(1, orderListVO.getReview_score() != null ? orderListVO.getReview_score() : 0);
			pstmt.setString(2, orderListVO.getReview_msg());
			pstmt.setTimestamp(3, orderListVO.getReview_time());
			pstmt.setString(4, orderListVO.getOrderlist_id());
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
	
	public List<ReviewVO> findReviewByProductId(String productId) {
		List<ReviewVO> list = new ArrayList<ReviewVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(FIND_REVIEW_BY_PRODUCT_ID);

			pstmt.setString(1, productId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewVO vo = new ReviewVO();
				vo.setMember_id(rs.getString("member_id"));
				vo.setMember_name(rs.getString("member_name"));
				vo.setMember_nickname(rs.getString("member_nickname"));
				vo.setReview_msg(rs.getString("review_msg"));
				vo.setReview_score(rs.getInt("review_score"));
				vo.setReview_time(rs.getTimestamp("review_time"));
				vo.setMember_photo(rs.getBytes("member_photo"));
				list.add(vo);
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
	
	
	// Sam=======================================================================================
	@Override
	public List<OrderListVO> findByOrderIdB(String order_Id) {
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
				vo.setPrice(rs.getInt("PRICE"));
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
