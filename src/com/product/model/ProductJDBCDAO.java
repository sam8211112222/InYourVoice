package com.product.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.eventorderlist.model.EventOrderListVO;
import com.orderlist.model.OrderListVO;
import com.productphoto.model.ProductPhotoVO;
import com.ticket.model.TicketVO;

public class ProductJDBCDAO implements ProductDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO product (product_id,band_id,product_type,"
			+ "product_name,product_intro,product_detail,product_price,product_stock,"
			+ "product_check_status,product_status,product_on_time,product_off_time,"
			+ "product_add_time,product_discount,product_discount_on_time,product_discount_off_time,"
			+ "product_last_edit_time,product_last_editor) "
			+ "VALUES ('PRODUCT'||LPAD(PRODUCT_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT product_id," + "band_id," + "product_type," + "product_name,"
			+ "product_intro," + "product_detail," + "product_price," + "product_stock," + "product_check_status,"
			+ "product_status," + "to_char(product_on_time,'yyyy-mm-dd hh:mm:ss')product_on_time,"
			+ "to_char(product_off_time,'yyyy-mm-dd hh:mm:ss')product_off_time,"
			+ "to_char(product_add_time,'yyyy-mm-dd hh:mm:ss')product_add_time," + "product_discount,"
			+ "to_char(product_discount_on_time,'yyyy-mm-dd hh:mm:ss')product_discount_on_time,"
			+ "to_char(product_discount_off_time,'yyyy-mm-dd hh:mm:ss')product_discount_off_time,"
			+ "to_char(product_last_edit_time,'yyyy-mm-dd hh:mm:ss')product_last_edit_time," + "product_last_editor "
			+ "FROM product order by product_id";
	private static final String GET_ONE_STMT = "SELECT product_id," + "band_id," + "product_type," + "product_name,"
			+ "product_intro," + "product_detail," + "product_price," + "product_stock," + "product_check_status,"
			+ "product_status," + "to_char(product_on_time,'yyyy-mm-dd hh:mm:ss')product_on_time,"
			+ "to_char(product_off_time,'yyyy-mm-dd hh:mm:ss')product_off_time,"
			+ "to_char(product_add_time,'yyyy-mm-dd hh:mm:ss')product_add_time," + "product_discount,"
			+ "to_char(product_discount_on_time,'yyyy-mm-dd hh:mm:ss')product_discount_on_time,"
			+ "to_char(product_discount_off_time,'yyyy-mm-dd hh:mm:ss')product_discount_off_time,"
			+ "to_char(product_last_edit_time,'yyyy-mm-dd hh:mm:ss')product_last_edit_time," + "product_last_editor "
			+ "FROM product where product_id = ?";

	private static final String DELETE = "DELETE FROM product where product_id = ?";
	private static final String UPDATE = "UPDATE product set " + "band_id=?," + "product_type=?," + "product_name=?,"
			+ "product_intro=?," + "product_detail=?," + "product_price=?," + "product_stock=?,"
			+ "product_check_status=?," + "product_status=?," + "product_on_time=?," + "product_off_time=?,"
			+ "product_add_time=?," + "product_discount=?," + "product_discount_on_time=?,"
			+ "product_discount_off_time=?," + "product_last_edit_time=?," + "product_last_editor=?"
			+ "where product_id = ?";
	private static final String LAUNCH = "UPDATE product set product_status=1, product_last_edit_time=? WHERE product_id = ?";
	private static final String DISLAUNCH = "UPDATE product set product_status=0, product_last_edit_time=? WHERE product_id = ?";
	private static final String APPROVAL = "UPDATE product set product_check_status=1, product_last_edit_time=? WHERE product_id = ?";
	private static final String GET_APPROVAL = "SELECT * FROM product WHERE product_check_status=1 ORDER BY product_last_edit_time";
	private static final String GET_UNAPPROVAL = "SELECT * FROM product WHERE product_check_status=0 ORDER BY product_last_edit_time";
	private static final String GET_BAND = "SELECT * FROM product WHERE band_id =? ORDER BY product_id";
	private static final String GET_BANDLISTBYTIME = "SELECT * FROM product WHERE band_id =? ORDER BY product_last_edit_time DESC";
	private static final String GET_TIME = "SELECT * FROM product ORDER BY product_last_edit_time DESC";
	private static final String GET_ORDER = "SELECT p.band_id, o.orderlist_id, o.order_id, o.product_id, o.orderlist_goods_amount,o.orderlist_remarks,o.review_score, o.review_msg, o.review_time, o.review_hidden, o.price FROM product p,orderlist o WHERE p.product_id=o.product_id AND p.band_id = ?";	
	//這是新增的搜尋方法
		private static final String GET_PRODUCT_BYNAME_PSTMT = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME LIKE ?";
	// Sam
		private static final String GET_EORDER = "SELECT * FROM eventorderlist JOIN ticket ON eventorderlist.ticket_id = ticket.ticket_id  JOIN event ON ticket.event_id = event.event_id AND event.band_id = ?";	
	
	
		/**
		 * added by  鈺涵
		 */
		private static final String FIND_BY_NAME_STMT = "SELECT * FROM product where upper(product_name) LIKE upper(?)";
		
		/**
		 * added by  鈺涵
		 */
		private static final String FIND_BY_TYPE_STMT = "SELECT * FROM product where product_type = ?";
		
		/**
		 * added by  鈺涵
		 */
		private static final String UPDATE_STOCK = "UPDATE PRODUCT SET PRODUCT_STOCK = PRODUCT_STOCK + ? where PRODUCT_ID = ?";

		/**
		 * added by  鈺涵
		 */
		private static final String SELECT_PRODUCT_FOR_LIST_STMT = "SELECT P.PRODUCT_ID,P.BAND_ID,P.PRODUCT_TYPE,P.PRODUCT_NAME,P.PRODUCT_INTRO,P.PRODUCT_PRICE,P.PRODUCT_STOCK,P.PRODUCT_CHECK_STATUS,P.PRODUCT_STATUS,P.PRODUCT_ON_TIME," + 
				"P.PRODUCT_OFF_TIME,P.PRODUCT_ADD_TIME,P.PRODUCT_DISCOUNT,P.PRODUCT_DISCOUNT_ON_TIME,P.PRODUCT_DISCOUNT_OFF_TIME,P.PRODUCT_LAST_EDIT_TIME,P.PRODUCT_LAST_EDITOR," + 
				"AVG(case when o.review_time is not null then o.review_score else null end) AS REVIEW_SCORE,COUNT(case when o.review_time is not null then 1 end) AS REVIEW_COUNT  FROM PRODUCT P left outer JOIN ORDERLIST O ON P.PRODUCT_ID = O.PRODUCT_ID %S GROUP BY " + 
				"P.PRODUCT_ID,P.BAND_ID,P.PRODUCT_TYPE,P.PRODUCT_NAME,P.PRODUCT_INTRO,P.PRODUCT_PRICE,P.PRODUCT_STOCK,P.PRODUCT_CHECK_STATUS,P.PRODUCT_STATUS,P.PRODUCT_ON_TIME," + 
				"P.PRODUCT_OFF_TIME,P.PRODUCT_ADD_TIME,P.PRODUCT_DISCOUNT,P.PRODUCT_DISCOUNT_ON_TIME,P.PRODUCT_DISCOUNT_OFF_TIME,P.PRODUCT_LAST_EDIT_TIME,P.PRODUCT_LAST_EDITOR order by product_on_time DESC";

	@Override
	public void insert(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productVO.getBand_id());
			pstmt.setInt(2, productVO.getProduct_type());
			pstmt.setString(3, productVO.getProduct_name());
			pstmt.setString(4, productVO.getProduct_intro());
			pstmt.setString(5, productVO.getProduct_detail());
			pstmt.setDouble(6, productVO.getProduct_price());
			pstmt.setInt(7, productVO.getProduct_stock());
			pstmt.setInt(8, productVO.getProduct_check_status());
			pstmt.setInt(9, productVO.getProduct_status());
			pstmt.setTimestamp(10, productVO.getProduct_on_time());
			pstmt.setTimestamp(11, productVO.getProduct_off_time());
			pstmt.setTimestamp(12, productVO.getProduct_add_time());
			pstmt.setDouble(13, productVO.getProduct_discount());
			pstmt.setTimestamp(14, productVO.getProduct_discount_on_time());
			pstmt.setTimestamp(15, productVO.getProduct_discount_off_time());
			pstmt.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(17, productVO.getProduct_last_editor());

			pstmt.executeUpdate();
			con.commit();

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
	public void update(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productVO.getBand_id());
			pstmt.setInt(2, productVO.getProduct_type());
			pstmt.setString(3, productVO.getProduct_name());
			pstmt.setString(4, productVO.getProduct_intro());
			pstmt.setString(5, productVO.getProduct_detail());
			pstmt.setDouble(6, productVO.getProduct_price());
			pstmt.setInt(7, productVO.getProduct_stock());
			pstmt.setInt(8, productVO.getProduct_check_status());
			pstmt.setInt(9, productVO.getProduct_status());
			pstmt.setTimestamp(10, productVO.getProduct_on_time());
			pstmt.setTimestamp(11, productVO.getProduct_off_time());
			pstmt.setTimestamp(12, productVO.getProduct_add_time());
			pstmt.setDouble(13, productVO.getProduct_discount());
			pstmt.setTimestamp(14, productVO.getProduct_discount_on_time());
			pstmt.setTimestamp(15, productVO.getProduct_discount_off_time());
			pstmt.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(17, productVO.getProduct_last_editor());
			pstmt.setString(18, productVO.getProduct_id());

			pstmt.executeUpdate();
			con.commit();
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
	public void launch(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(LAUNCH);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());

			pstmt.executeUpdate();
			con.commit();
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
	public void dislaunch(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DISLAUNCH);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());
			pstmt.executeUpdate();
			con.commit();
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
	public void approval(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(APPROVAL);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());
			pstmt.executeUpdate();
			con.commit();
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
	public void delete(String product_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, product_id);

			pstmt.executeUpdate();
			con.commit();
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
	public ProductVO findByPrimaryKey(String product_id) {
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, product_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
			}
			con.commit();
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
		return productVO;
	}

	@Override
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	public List<ProductVO> getApproval() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_APPROVAL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	public List<ProductVO> getUnapproval() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_UNAPPROVAL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	public List<ProductVO> getBand(String band_id) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_BAND);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	public List<OrderListVO> getOrder(String band_id) {
		List<OrderListVO> list =new ArrayList<OrderListVO>();

		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ORDER);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
//				productVO = new ProductVO();
//				productVO.setBand_id(rs.getString("band_id"));
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
//				list.add(productVO.getBand_id());
//				list.add(orderListVO.getOrderlist_id());
//				list.add(orderListVO.getOrder_id());
//				list.add(orderListVO.getProduct_id());
//				list.add(orderListVO.getOrderlist_goods_amount());
//				list.add(orderListVO.getOrderlist_remarks());
//				list.add(orderListVO.getReview_score());
//				list.add(orderListVO.getReview_msg());
//				list.add(orderListVO.getReview_time());
//				list.add(orderListVO.getReview_hidden());
//				list.add(orderListVO.getPrice());
//				
			}
			con.commit();
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
	public List<ProductVO> getTime() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_TIME);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	public List<ProductVO> getBandListByTime(String band_id) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_BANDLISTBYTIME);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
			}
			con.commit();
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
	
	/**
	 * added by  鈺涵
	 */
	public void updateStock(String productId, int stockDifference) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STOCK);

			pstmt.setInt(1, stockDifference);
			pstmt.setString(2, productId);

			pstmt.executeUpdate();
			con.commit();
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

	
	/**
	 * added by  鈺涵
	 */
	@Override
	public List<ProductVO> findByProductName(String productName) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_NAME_STMT);
			pstmt.setString(1, "%" + productName + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
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

	
	/**
	 * added by  鈺涵
	 */
	@Override
	public List<ProductVO> findByProductType(String productType) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_TYPE_STMT);
			pstmt.setString(1, productType);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				list.add(productVO); // Store the row in the list
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
	
	/**
	 * added by  鈺涵
	 */
	@Override
	public List<ProductVO> findProductForList(String productName ,String productType) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			StringBuilder clause = new StringBuilder();
			
			if(productName!=null) {
				if(clause.toString().length()==0) {
					clause.append(" where ");
				}
				clause.append(" upper(P.product_name) LIKE upper(?) ");
			}
			if(productType!=null) {
				if(clause.toString().length()==0) {
					clause.append(" where ");
				}else {
					clause.append(" and ");
				}
				clause.append(" P.product_type = ? ");
			}
			
			String stmt = String.format(SELECT_PRODUCT_FOR_LIST_STMT, clause.toString());
			
			pstmt = con.prepareStatement(stmt);
			int index = 1;
			
			if(productName!=null) {
				pstmt.setString(index++, "%"+productName+"%");
			}

			if(productType!=null) {
				pstmt.setString(index, productType);
			} 
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				productVO.setProduct_type(rs.getInt("product_type"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_intro(rs.getString("product_intro"));
//				productVO.setProduct_detail(rs.getString("product_detail"));
				productVO.setProduct_price(rs.getDouble("product_price"));
				productVO.setProduct_stock(rs.getInt("product_stock"));
				productVO.setProduct_check_status(rs.getInt("product_check_status"));
				productVO.setProduct_status(rs.getInt("product_status"));
				productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
				productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
				productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
				productVO.setProduct_discount(rs.getDouble("product_discount"));
				productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
				productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
				productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
				productVO.setProduct_last_editor(rs.getString("product_last_editor"));
				productVO.setReview_count(rs.getInt("review_count"));
				productVO.setReview_score(rs.getInt("review_score"));
				list.add(productVO); // Store the row in the list
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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
	
	//冠華
	//這是新增的搜尋方法
		@Override
		public List<ProductVO> findByName(String product_name) {
			List<ProductVO> list = new ArrayList<>();
			ProductVO productVO = null;

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, userid, passwd);
				pstmt = conn.prepareStatement(GET_PRODUCT_BYNAME_PSTMT);

				pstmt.setString(1, "%" + product_name + "%");
				
				rs = pstmt.executeQuery();

				while (rs.next()) {

					productVO = new ProductVO();
					productVO.setProduct_id(rs.getString("product_id"));
					productVO.setBand_id(rs.getString("band_id"));
					productVO.setProduct_type(rs.getInt("product_type"));
					productVO.setProduct_name(rs.getString("product_name"));
					productVO.setProduct_intro(rs.getString("product_intro"));
					productVO.setProduct_detail(rs.getString("product_detail"));
					productVO.setProduct_price(rs.getDouble("product_price"));
					productVO.setProduct_stock(rs.getInt("product_stock"));
					productVO.setProduct_check_status(rs.getInt("product_check_status"));
					productVO.setProduct_status(rs.getInt("product_status"));
					productVO.setProduct_on_time(rs.getTimestamp("product_on_time"));
					productVO.setProduct_off_time(rs.getTimestamp("product_off_time"));
					productVO.setProduct_add_time(rs.getTimestamp("product_add_time"));
					productVO.setProduct_discount(rs.getDouble("product_discount"));
					productVO.setProduct_discount_on_time(rs.getTimestamp("product_discount_on_time"));
					productVO.setProduct_discount_off_time(rs.getTimestamp("product_discount_off_time"));
					productVO.setProduct_last_edit_time(rs.getTimestamp("product_last_edit_time"));
					productVO.setProduct_last_editor(rs.getString("product_last_editor"));
					list.add(productVO);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace(System.err);
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace(System.err);
					}
				}

			}

			return list;
		}
		
		//Sam
		@Override
		public List<EventOrderListVO> getEOrder(String band_id) {
			List<EventOrderListVO> list =new ArrayList<EventOrderListVO>();
			
			EventOrderListVO eventOrderListVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(GET_EORDER);
				pstmt.setString(1, band_id);
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
				con.commit();
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
//
//		ProductJDBCDAO dao = new ProductJDBCDAO();
//
//		// 新增
//		ProductVO productVO1 = new ProductVO();
//		productVO1.setBand_id("BAND00350");
//		productVO1.setProduct_type(2);
//		productVO1.setProduct_name("洪正出櫃");
//		productVO1.setProduct_intro("洪正出櫃海報");
//		productVO1.setProduct_detail("洪正出櫃海報");
//		productVO1.setProduct_price(200.00);
//		productVO1.setProduct_stock(30);
//		productVO1.setProduct_check_status(1);
//		productVO1.setProduct_status(1);
//		productVO1.setProduct_on_time(java.sql.Timestamp.valueOf("2020-12-10 12:49:45"));
//		productVO1.setProduct_off_time(java.sql.Timestamp.valueOf("2021-12-10 12:49:45"));
//		productVO1.setProduct_add_time(java.sql.Timestamp.valueOf("2020-12-4 12:49:45"));
//		productVO1.setProduct_discount(0.25);
//		productVO1.setProduct_discount_on_time(java.sql.Timestamp.valueOf("2020-12-20 12:49:45"));
//		productVO1.setProduct_discount_off_time(java.sql.Timestamp.valueOf("2021-5-20 12:49:45"));
//		productVO1.setProduct_last_edit_time(new Timestamp(System.currentTimeMillis()));
//		productVO1.setProduct_last_editor("BAND00350");
//		dao.insert(productVO1);

		// 修改
//		ProductVO productVO2 = new ProductVO();
//
//		productVO2.setBand_id("BAND00250");
//		productVO2.setProduct_type(1);
//		productVO2.setProduct_name("Poster");
//		productVO2.setProduct_intro("修改樂團海報");
//		productVO2.setProduct_detail("這是樂團海報");
//		productVO2.setProduct_price(300.00);
//		productVO2.setProduct_stock(30);
//		productVO2.setProduct_check_status(1);
//		productVO2.setProduct_status(1);
//		productVO2.setProduct_on_time(java.sql.Timestamp.valueOf("2020-12-10 12:49:45"));
//		productVO2.setProduct_off_time(java.sql.Timestamp.valueOf("2020-12-10 12:49:45"));
//		productVO2.setProduct_add_time(java.sql.Timestamp.valueOf("2020-12-10 12:49:45"));
//		productVO2.setProduct_discount(0.25);
//		productVO2.setProduct_discount_on_time(java.sql.Timestamp.valueOf("2020-12-20 12:49:45"));
//		productVO2.setProduct_discount_off_time(java.sql.Timestamp.valueOf("2021-5-20 12:49:45"));
//		productVO2.setProduct_last_edit_time(java.sql.Timestamp.valueOf("2020-12-5 12:49:45"));
//		productVO2.setProduct_last_editor("BAND00350");
//		productVO2.setProduct_id("PRODUCT00250");
//
//		dao.update(productVO2);

		// 刪除
//		dao.delete("PRODUCT00300");

		// 以主鍵查詢
//		ProductVO productVO3 = dao.findByPrimaryKey("PRODUCT00250");
//		System.out.println(productVO3.getProduct_id()+",");
//		System.out.println(productVO3.getBand_id()+",");
//		System.out.println(productVO3.getProduct_type()+",");
//		System.out.println(productVO3.getProduct_name()+",");
//		System.out.println(productVO3.getProduct_intro()+",");
//		System.out.println(productVO3.getProduct_detail()+",");
//		System.out.println(productVO3.getProduct_price()+",");
//		System.out.println(productVO3.getProduct_stock()+",");
//		System.out.println(productVO3.getProduct_check_status()+",");
//		System.out.println(productVO3.getProduct_status()+",");
//		System.out.println(productVO3.getProduct_on_time()+",");
//		System.out.println(productVO3.getProduct_off_time()+",");
//		System.out.println(productVO3.getProduct_add_time()+",");
//		System.out.println(productVO3.getProduct_discount()+",");
//		System.out.println(productVO3.getProduct_discount_on_time()+",");
//		System.out.println(productVO3.getProduct_discount_off_time()+",");
//		System.out.println(productVO3.getProduct_last_edit_time()+",");
//		System.out.println(productVO3.getProduct_last_editor());
//		System.out.println("======================================");

		// 上架
//		ProductVO productVO4 = new ProductVO();
//		productVO4.setProduct_id("PRODUCT00050");
//		dao.launch(productVO4);
		
		// 下架
//		ProductVO productVO5 = new ProductVO();
//		productVO5.setProduct_id("PRODUCT00100");
//		dao.launch(productVO5);
		
		// 審核
//		ProductVO productVO6 = new ProductVO();
//		productVO6.setProduct_id("PRODUCT00100");
//		dao.approval(productVO6);

		// 查詢
//		List<ProductVO> list = dao.getAll();
//		for (ProductVO pv : list) {
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getBand_id() + ",");
//			System.out.println(pv.getProduct_type() + ",");
//			System.out.println(pv.getProduct_name() + ",");
//			System.out.println(pv.getProduct_intro() + ",");
//			System.out.println(pv.getProduct_detail() + ",");
//			System.out.println(pv.getProduct_price() + ",");
//			System.out.println(pv.getProduct_stock() + ",");
//			System.out.println(pv.getProduct_check_status() + ",");
//			System.out.println(pv.getProduct_status() + ",");
//			System.out.println(pv.getProduct_on_time() + ",");
//			System.out.println(pv.getProduct_off_time() + ",");
//			System.out.println(pv.getProduct_add_time() + ",");
//			System.out.println(pv.getProduct_discount() + ",");
//			System.out.println(pv.getProduct_discount_on_time() + ",");
//			System.out.println(pv.getProduct_discount_off_time() + ",");
//			System.out.println(pv.getProduct_last_edit_time() + ",");
//			System.out.println(pv.getProduct_last_editor());
//			System.out.println("======================================");
//		}

//		List<ProductVO> list1 = dao.getApproval();
//		for (ProductVO pv : list1) {
//			System.out.println("我只列出已審核的商品喔");
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getBand_id() + ",");
//			System.out.println(pv.getProduct_type() + ",");
//			System.out.println(pv.getProduct_name() + ",");
//			System.out.println(pv.getProduct_intro() + ",");
//			System.out.println(pv.getProduct_detail() + ",");
//			System.out.println(pv.getProduct_price() + ",");
//			System.out.println(pv.getProduct_stock() + ",");
//			System.out.println(pv.getProduct_check_status() + ",");
//			System.out.println(pv.getProduct_status() + ",");
//			System.out.println(pv.getProduct_on_time() + ",");
//			System.out.println(pv.getProduct_off_time() + ",");
//			System.out.println(pv.getProduct_add_time() + ",");
//			System.out.println(pv.getProduct_discount() + ",");
//			System.out.println(pv.getProduct_discount_on_time() + ",");
//			System.out.println(pv.getProduct_discount_off_time() + ",");
//			System.out.println(pv.getProduct_last_edit_time() + ",");
//			System.out.println(pv.getProduct_last_editor());
//			System.out.println("======================================");
//		}

//		List<ProductVO> list2 = dao.getUnapproval();
//		for (ProductVO pv : list2) {
//			System.out.println("我只列出未審核的商品喔");
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getBand_id() + ",");
//			System.out.println(pv.getProduct_type() + ",");
//			System.out.println(pv.getProduct_name() + ",");
//			System.out.println(pv.getProduct_intro() + ",");
//			System.out.println(pv.getProduct_detail() + ",");
//			System.out.println(pv.getProduct_price() + ",");
//			System.out.println(pv.getProduct_stock() + ",");
//			System.out.println(pv.getProduct_check_status() + ",");
//			System.out.println(pv.getProduct_status() + ",");
//			System.out.println(pv.getProduct_on_time() + ",");
//			System.out.println(pv.getProduct_off_time() + ",");
//			System.out.println(pv.getProduct_add_time() + ",");
//			System.out.println(pv.getProduct_discount() + ",");
//			System.out.println(pv.getProduct_discount_on_time() + ",");
//			System.out.println(pv.getProduct_discount_off_time() + ",");
//			System.out.println(pv.getProduct_last_edit_time() + ",");
//			System.out.println(pv.getProduct_last_editor());
//			System.out.println("======================================");
//		}

		// 以樂團編號查詢
//		List<ProductVO> list3 = dao.getBand("BAND00000");
//		for (ProductVO pv : list3) {
//			System.out.println("我只列出單一樂團的商品喔");
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getBand_id() + ",");
//			System.out.println(pv.getProduct_type() + ",");
//			System.out.println(pv.getProduct_name() + ",");
//			System.out.println(pv.getProduct_intro() + ",");
//			System.out.println(pv.getProduct_detail() + ",");
//			System.out.println(pv.getProduct_price() + ",");
//			System.out.println(pv.getProduct_stock() + ",");
//			System.out.println(pv.getProduct_check_status() + ",");
//			System.out.println(pv.getProduct_status() + ",");
//			System.out.println(pv.getProduct_on_time() + ",");
//			System.out.println(pv.getProduct_off_time() + ",");
//			System.out.println(pv.getProduct_add_time() + ",");
//			System.out.println(pv.getProduct_discount() + ",");
//			System.out.println(pv.getProduct_discount_on_time() + ",");
//			System.out.println(pv.getProduct_discount_off_time() + ",");
//			System.out.println(pv.getProduct_last_edit_time() + ",");
//			System.out.println(pv.getProduct_last_editor() + ",");
//			System.out.println("======================================");
//		}
		
		// 以樂團編號查詢訂單資訊
//			List<OrderListVO> list = dao.getOrder("BAND00000");
//			System.out.println("hi");
//			for(OrderListVO o : list) {
//				System.out.println(o.getOrder_id());
//			}
		// 以產品編號查詢
//		List<ProductVO> list3 = dao.getOrder("BAND00000");
//		for (ProductVO pv : list3) {
//			System.out.println("我是JOIN測試喔");
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getBand_id() + ",");
//			System.out.println("======================================");
//		}
		
		//以最後修改時間列出
//		List<ProductVO> list4 = dao.getTime();
//		for (ProductVO pv : list4) {
//			System.out.println(pv.getProduct_id() + ",");
//			System.out.println(pv.getProduct_last_edit_time() + ",");
//			System.out.println(pv.getProduct_last_editor());
//			System.out.println("======================================");
//		}
		
//	}
}
