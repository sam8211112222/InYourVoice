package com.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.orderlist.model.OrderListVO;


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
	private static final String GET_ORDER = "SELECT product.band_id, orderlist.orderlist_id, orderlist.order_id, orderlist.orderlist_good_amount, orderlist.orderlist_remarks, orderlist.review_score, orderlist.review_msg, orderlist.review_time, orderlist.review_hidden, orderlist.price FROM product LEFT JOIN orderlist ON product.productid=orderlist.product_id";	
	
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
	public List<ProductVO> getOrder(String product_id) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		List<OrderListVO> list2 = new ArrayList<OrderListVO>();
		
		ProductVO productVO = null;
		OrderListVO orderListVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER);
			pstmt.setString(1, product_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productVO = new ProductVO();
				orderListVO = new OrderListVO();
				productVO.setProduct_id(rs.getString("product_id"));
				productVO.setBand_id(rs.getString("band_id"));
				orderListVO.setOrderlist_id(rs.getString("orderlist_id"));
				orderListVO.setOrder_id(rs.getString("order_id"));
				orderListVO.setOrderlist_goods_amount(rs.getInt("orderlist_goods_amount"));
				orderListVO.setOrderlist_remarks(rs.getString("orderlist_remarks"));
				orderListVO.setReview_score(rs.getInt("review_score"));
				orderListVO.setReview_msg(rs.getString("review_msg"));
				orderListVO.setReview_time(rs.getTimestamp("review_time"));
				orderListVO.setReview_hidden(rs.getInt("review_hidden"));
				orderListVO.setPrice(rs.getInt("price"));
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
}
