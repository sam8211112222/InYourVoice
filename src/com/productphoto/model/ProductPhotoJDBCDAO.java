package com.productphoto.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.product.model.ProductVO;

public class ProductPhotoJDBCDAO implements ProductPhotoDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO productphoto(productphoto_id,product_id,productphoto_photo,productphoto_sort,productphoto_add_time)"
			+ "VALUES('PRODUCTPHOTO'||LPAD(PRODUCTPHOTO_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?)";
	private static final String GET_ONE_STMT = "SELECT * FROM productphoto WHERE productphoto_id = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM productphoto ORDER BY productphoto_id";
	private static final String GET_ALL_STMTBYBAND = "SELECT p.band_id, o.productphoto_id, o.product_id, o.productphoto_photo,o.productphoto_sort, o.productphoto_add_time FROM product p, productphoto o WHERE p.product_id=o.product_id AND p.band_id = ?";
	private static final String DELETE = "DELETE FROM productphoto where productphoto_id = ?";
	private static final String UPDATE = "UPDATE productphoto set " + "product_id=?," + "productphoto_photo=?,"
			+ "productphoto_sort=?," + "productphoto_add_time=?" + "where productphoto_id = ?";

	@Override
	public void insert(ProductPhotoVO productPhotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productPhotoVO.getProduct_id());
			pstmt.setBytes(2, productPhotoVO.getProductphoto_photo());
			pstmt.setInt(3, productPhotoVO.getProductphoto_sort());
			pstmt.setTimestamp(4, productPhotoVO.getProductphoto_add_time());
			pstmt.executeUpdate();
			System.out.println("Operation success!");
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
	public void update(ProductPhotoVO productPhotoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productPhotoVO.getProduct_id());
			pstmt.setBytes(2, productPhotoVO.getProductphoto_photo());
			pstmt.setInt(3, productPhotoVO.getProductphoto_sort());
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(5, productPhotoVO.getProductphoto_id());
			pstmt.executeUpdate();

			System.out.println("Operation success!");
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
	public void delete(String productphoto_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, productphoto_id);

			pstmt.executeUpdate();

			System.out.println("Operation success!");
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
	public ProductPhotoVO findByPrimaryKey(String productphoto_id) {
		ProductPhotoVO productPhotoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, productphoto_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProductphoto_id(rs.getString("productphoto_id"));
				productPhotoVO.setProduct_id(rs.getString("product_id"));
				productPhotoVO.setProductphoto_photo(rs.getBytes("productphoto_photo"));
				productPhotoVO.setProductphoto_sort(rs.getInt("productphoto_sort"));
				productPhotoVO.setProductphoto_add_time(rs.getTimestamp("productphoto_add_time"));
			}
			System.out.println("Operation success!");
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
		return productPhotoVO;
	}

	@Override
	public List<ProductPhotoVO> getAllByBand(String band_id) {
		List<ProductPhotoVO> list = new ArrayList<ProductPhotoVO>();
		ProductPhotoVO productPhotoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMTBYBAND);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProductphoto_id(rs.getString("productphoto_id"));
				productPhotoVO.setProduct_id(rs.getString("product_id"));
				productPhotoVO.setProductphoto_photo(rs.getBytes("productphoto_photo"));
				productPhotoVO.setProductphoto_sort(rs.getInt("productphoto_sort"));
				productPhotoVO.setProductphoto_add_time(rs.getTimestamp("productphoto_add_time"));
				list.add(productPhotoVO); // Store the row in the list
			}
			System.out.println("Operation success!");
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
	public List<ProductPhotoVO> getAll() {
		List<ProductPhotoVO> list = new ArrayList<ProductPhotoVO>();
		ProductPhotoVO productPhotoVO = null;

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
				productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProductphoto_id(rs.getString("productphoto_id"));
				productPhotoVO.setProduct_id(rs.getString("product_id"));
				productPhotoVO.setProductphoto_photo(rs.getBytes("productphoto_photo"));
				productPhotoVO.setProductphoto_sort(rs.getInt("productphoto_sort"));
				productPhotoVO.setProductphoto_add_time(rs.getTimestamp("productphoto_add_time"));
				list.add(productPhotoVO); // Store the row in the list
			}
			System.out.println("Operation success!");
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
	
	
	//這是鈺涵的方法
		public byte[] getImage(String product_id) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			 byte[] result = null;
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("select productphoto_photo FROM productphoto where product_id = ?");
				pstmt.setString(1,product_id);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					result = rs.getBytes("productphoto_photo");
				}
				System.out.println("Operation success!");
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
			return result;
		}
		
		//這是鈺涵的方法
		public byte[] getFirstImageByProductId(String product_id) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			byte[] result = null;
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(
						"select productphoto_photo FROM productphoto where product_id = ? and ROWNUM = 1 order by productphoto_sort");
				pstmt.setString(1, product_id);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					result = rs.getBytes("productphoto_photo");
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
			return result;
		}
		
		//這是鈺涵的方法
		public byte[] getImageByPhotoId(String photoId) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			byte[] result = null;
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(
						"select productphoto_photo FROM productphoto where productphoto_id = ?");
				pstmt.setString(1, photoId);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					result = rs.getBytes("productphoto_photo");
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
			return result;
		}
		
		//這是鈺涵的方法
		public List<String> getIdListByProductId(String productId) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<String> result = new ArrayList<String>();
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(
						"select productphoto_id FROM productphoto where product_id = ? and ROWNUM < 6 order by productphoto_sort");
				pstmt.setString(1, productId);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					result.add(rs.getString("productphoto_id"));
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
			return result;
		}

	public static void main(String[] args) {
		ProductPhotoJDBCDAO dao = new ProductPhotoJDBCDAO();

		// 新增
//		ProductPhotoVO productPhotoVO1 = new ProductPhotoVO();
//		productPhotoVO1.setProduct_id("PRODUCT00300");
//		productPhotoVO1.setProductphoto_photo(null);
//		productPhotoVO1.setProductphoto_sort(2);
//		productPhotoVO1.setProductphoto_add_time(java.sql.Timestamp.valueOf("2020-01-01 12:49:45"));
//		dao.insert(productPhotoVO1);

		// 修改
//		ProductPhotoVO productPhotoVO2 = new ProductPhotoVO();
//		productPhotoVO2.setProductphoto_id("PRODUCTPHOTO00250");
//		productPhotoVO2.setProduct_id("PRODUCT00250");
//		productPhotoVO2.setProductphoto_photo(null);
//		productPhotoVO2.setProductphoto_sort(10);
//		productPhotoVO2.setProductphoto_add_time(java.sql.Timestamp.valueOf("2020-01-01 12:49:45"));
//		dao.update(productPhotoVO2);

		// 刪除
//		dao.delete("PRODUCTPHOTO00100");

		// 查詢
//		List<ProductPhotoVO> list = dao.getAll();
//		for(ProductPhotoVO ppv: list) {
//			System.out.println(ppv.getProductphoto_id()+",");
//			System.out.println(ppv.getProduct_id()+",");
//			System.out.println(ppv.getProductphoto_photo()+",");
//			System.out.println(ppv.getProductphoto_sort()+",");
//			System.out.println(ppv.getProductphoto_add_time());
//		    System.out.println("========================================");			
//		}
	}
}
