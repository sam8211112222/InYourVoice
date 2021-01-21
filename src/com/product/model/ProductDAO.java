package com.product.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.eventorderlist.model.EventOrderListVO;
import com.orderlist.model.OrderListVO;
import com.ticket.model.TicketVO;


public class ProductDAO implements ProductDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestTEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO product ("
			+ "product_id,"
			+ "band_id,"
			+ "product_type,"
			+ "product_name,"
			+ "product_intro,"
			+ "product_detail,"
			+ "product_price,"
			+ "product_stock,"
			+ "product_check_status,"
			+ "product_status,"
			+ "product_on_time,"
			+ "product_off_time,"
			+ "product_add_time,"
			+ "product_discount,"
			+ "product_discount_on_time,"
			+ "product_discount_off_time,"
			+ "product_last_edit_time,"
			+ "product_last_editor) "
			+ "VALUES ('PRODUCT'||LPAD(PRODUCT_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT product_id,"
			+ "band_id,"
			+ "product_type,"
			+ "product_name,"
			+ "product_intro,"
			+ "product_detail,"
			+ "product_price,"
			+ "product_stock,"
			+ "product_check_status,"
			+ "product_status,"
			+ "to_char(product_on_time,'yyyy-mm-dd hh:mm:ss')product_on_time,"
			+ "to_char(product_off_time,'yyyy-mm-dd hh:mm:ss')product_off_time,"
			+ "to_char(product_add_time,'yyyy-mm-dd hh:mm:ss')product_add_time,"
			+ "product_discount,"
			+ "to_char(product_discount_on_time,'yyyy-mm-dd hh:mm:ss')product_discount_on_time,"
			+ "to_char(product_discount_off_time,'yyyy-mm-dd hh:mm:ss')product_discount_off_time,"
			+ "to_char(product_last_edit_time,'yyyy-mm-dd hh:mm:ss')product_last_edit_time,"
			+ "product_last_editor "
			+ "FROM product order by product_id";
	private static final String GET_ONE_STMT = 
			"SELECT product_id,"
			+ "band_id,"
			+ "product_type,"
			+ "product_name,"
			+ "product_intro,"
			+ "product_detail,"
			+ "product_price,"
			+ "product_stock,"
			+ "product_check_status,"
			+ "product_status,"
			+ "to_char(product_on_time,'yyyy-mm-dd hh:mm:ss')product_on_time,"
			+ "to_char(product_off_time,'yyyy-mm-dd hh:mm:ss')product_off_time,"
			+ "to_char(product_add_time,'yyyy-mm-dd hh:mm:ss')product_add_time,"
			+ "product_discount,"
			+ "to_char(product_discount_on_time,'yyyy-mm-dd hh:mm:ss')product_discount_on_time,"
			+ "to_char(product_discount_off_time,'yyyy-mm-dd hh:mm:ss')product_discount_off_time,"
			+ "to_char(product_last_edit_time,'yyyy-mm-dd hh:mm:ss')product_last_edit_time,"
			+ "product_last_editor "
			+ "FROM product WHERE product_id = ?";
	private static final String DELETE = 
			"DELETE FROM product WHERE product_id = ?";
	private static final String UPDATE = 
			"UPDATE product set "
					+ "band_id=?,"
					+ "product_type=?,"
					+ "product_name=?,"
					+ "product_intro=?,"
					+ "product_detail=?,"
					+ "product_price=?,"
					+ "product_stock=?,"
					+ "product_check_status=?,"
					+ "product_status=?,"
					+ "to_char(product_on_time,'yyyy-mm-dd') product_on_time=?,"
					+ "to_char(product_off_time,'yyyy-mm-dd')product_off_time=?,"
					+ "to_char(product_add_time,'yyyy-mm-dd')product_add_time=?,"
					+ "product_discount=?,"
					+ "to_char(product_discount_on_time,'yyyy-mm-dd')product_discount_on_time=?,"
					+ "to_char(product_discount_off_time,'yyyy-mm-dd')product_discount_off_time=?,"
					+ "to_char(product_last_edit_time,'yyyy-mm-dd')product_last_edit_time=?,"
					+ "product_last_editor=?"
					+ "WHERE product_id = ?";
	private static final String LAUNCH = "UPDATE product set product_status=1, product_last_edit_time=? WHERE product_id = ?";
	private static final String DISLAUNCH = "UPDATE product set product_status=0, product_last_edit_time=? WHERE product_id = ?";
	private static final String APPROVAL = "UPDATE product set product_check_status=1, product_last_edit_time=? WHERE product_id = ?";
	private static final String GET_APPROVAL = "SELECT * FROM product WHERE product_check_status=1 ORDER BY product_last_edit_time";	
	private static final String GET_UNAPPROVAL = "SELECT * FROM product WHERE product_check_status=0 ORDER BY product_last_edit_time";
	private static final String GET_BAND = "SELECT * FROM product WHERE band_id = ? ORDER BY product_id";
	private static final String GET_TIME = "SELECT * FROM product ORDER BY product_last_edit_time DESC";	
	private static final String GET_ORDER = "SELECT p.band_id, o.orderlist_id, o.order_id, o.product_id, o.orderlist_goods_amount,o.orderlist_remarks,o.review_score, o.review_msg, o.review_time, o.review_hidden, o.price FROM product p,orderlist o WHERE p.product_id=o.product_id AND p.band_id = ?";	
	private static final String GET_BANDLISTBYTIME = "SELECT * FROM product WHERE band_id =? ORDER BY product_last_edit_time DESC";
	//這是鈺涵的方法
	private static final String UPDATE_STOCK = "UPDATE PRODUCT SET PRODUCT_STOCK = PRODUCT_STOCK + ? where PRODUCT_ID = ?";
	//冠華
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

			con = ds.getConnection();
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
			pstmt.setTimestamp(16, productVO.getProduct_last_edit_time());
			pstmt.setString(17, productVO.getProduct_last_editor());
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(LAUNCH);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DISLAUNCH);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(APPROVAL);
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, productVO.getProduct_id());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
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
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, product_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, product_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
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

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

			con = ds.getConnection();
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

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
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
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
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
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
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
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER);
			pstmt.setString(1, band_id);
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

			con = ds.getConnection();
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

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
			
			con = ds.getConnection();
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
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	//這是鈺涵的方法
	@Override
	public void updateStock(String productId, int stockDifference) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STOCK);

			pstmt.setInt(1, stockDifference);
			pstmt.setString(2, productId);

			pstmt.executeUpdate();
			con.commit();
			// Handle any driver errors
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

	//這是鈺涵的方法
	@Override
	public List<ProductVO> findByProductName(String productName) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

	//這是鈺涵的方法
	@Override
	public List<ProductVO> findByProductType(String productType) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
	public List<ProductVO> findProductForList(String productName, String productType) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			
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
				conn = ds.getConnection();
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

			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return list;
		}
		
		// Sam
		@Override
		public List<EventOrderListVO> getEOrder(String band_id) {
			List<EventOrderListVO> list = new ArrayList<EventOrderListVO>();
			EventOrderListVO eventOrderListVO = null;	
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				con = ds.getConnection();
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
				
				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
