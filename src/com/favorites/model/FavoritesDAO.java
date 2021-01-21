package com.favorites.model;

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


public class FavoritesDAO implements FavoritesDAO_interface {
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
			private static DataSource ds = null;
			static {
				try {
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
	
	private static final String INSERT_STMT = 
		"INSERT INTO favorites (uniqueid,member_id,favorite_type,favorite_id,favorite_add_time) VALUES ('FAVORITES'||LPAD(FAVORITES_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT uniqueid,member_id,favorite_type,favorite_id,to_char(Favorite_add_time,'yyyy-mm-dd hh24:mi:ss') Favorite_add_time FROM favorites order by uniqueid";
	private static final String GET_ONE_STMT = 
		"SELECT uniqueid,member_id,favorite_type,favorite_id,to_char(Favorite_add_time,'yyyy-mm-dd hh24:mi:ss') Favorite_add_time FROM favorites where uniqueid = ?";
	private static final String DELETE = 
		"DELETE FROM favorites where uniqueid = ?";
	private static final String UPDATE = 
		"UPDATE favorites set member_id=?, favorite_type=?, favorite_id=?, Favorite_add_time=? where uniqueid = ?";
	
	/**
	 * added by  鈺涵
	 */
	private static final String DELETE_BY_MEMBERID_AND_PRODUCTID = "DELETE FROM favorites where member_id = ? and favorite_id=? and favorite_type=2";
	
	/**
	 * added by  鈺涵
	 */
	private static final String GET_ONE_BY_MEMBERID_AND_PRODUCTID_STMT = 
			"SELECT * FROM favorites where member_id = ? and favorite_id=? and favorite_type=2";
	
	//移除收藏
	private static final String DELETE_FAV = "DELETE FROM FAVORITES WHERE MEMBER_ID = ? AND FAVORITE_ID=?";
	
	//查使用者的全部收藏
	private static final String GET_MEMBER_FAV = "SELECT * FROM FAVORITES WHERE MEMBER_ID = ?"; 
	
	
	
	@Override
	public void insert(FavoritesVO favoritesVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, favoritesVO.getMember_id());
			pstmt.setInt(2, favoritesVO.getFavorite_type());
			pstmt.setString(3, favoritesVO.getFavorite_id());
			pstmt.setTimestamp(4, favoritesVO.getFavorite_add_time());

			pstmt.executeUpdate();
			
//			System.out.println("�s�W���\");

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
	public void update(FavoritesVO favoritesVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, favoritesVO.getMember_id());
			pstmt.setInt(2, favoritesVO.getFavorite_type());
			pstmt.setString(3, favoritesVO.getFavorite_id());
			pstmt.setTimestamp(4, favoritesVO.getFavorite_add_time());
			pstmt.setString(5, favoritesVO.getUniqueid());

			pstmt.executeUpdate();
			
//			System.out.println("�ק令�\");

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
	public void delete(String uniqueid) {

		Connection con = null;
		PreparedStatement pstmt = null;
		


		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, uniqueid);

			pstmt.executeUpdate();
			
//			System.out.println("�R�����\");

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
	public FavoritesVO findByPrimaryKey(String uniqueid) {
		FavoritesVO favoritesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, uniqueid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				favoritesVO = new FavoritesVO();
				favoritesVO.setUniqueid(rs.getString("uniqueid"));
				favoritesVO.setMember_id(rs.getString("member_id"));
				favoritesVO.setFavorite_type(rs.getInt("favorite_type"));
				favoritesVO.setFavorite_id(rs.getString("favorite_id"));
				favoritesVO.setFavorite_add_time(rs.getTimestamp("favorite_add_time"));
			}
//			System.out.print(favoritesVO.getUniqueid() + ",");
//			System.out.print(favoritesVO.getMember_id() + ",");
//			System.out.print(favoritesVO.getFavorite_type() + ",");
//			System.out.print(favoritesVO.getFavorite_id() + ",");
//			System.out.println(favoritesVO.getFavorite_add_time() + ",");
//			System.out.println("---------------------");

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
		return favoritesVO;
	}

	@Override
	public List<FavoritesVO> getAll() {
		List<FavoritesVO> list = new ArrayList<FavoritesVO>();
		FavoritesVO favoritesVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				favoritesVO = new FavoritesVO();
				favoritesVO.setUniqueid(rs.getString("uniqueid"));
				favoritesVO.setMember_id(rs.getString("member_id"));
				favoritesVO.setFavorite_type(rs.getInt("favorite_type"));
				favoritesVO.setFavorite_id(rs.getString("favorite_id"));
				favoritesVO.setFavorite_add_time(rs.getTimestamp("favorite_add_time"));
				list.add(favoritesVO); // Store the row in the list
			}
//			for (FavoritesVO favoritesVO4 : list) {
//				System.out.print(favoritesVO4.getUniqueid() + ",");
//				System.out.print(favoritesVO4.getMember_id() + ",");
//				System.out.print(favoritesVO4.getFavorite_type() + ",");
//				System.out.print(favoritesVO4.getFavorite_id() + ",");
//				System.out.print(favoritesVO4.getFavorite_add_time() + ",");
//				System.out.println();
//			}

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
	
	/**
	 * added by Sophia
	 */
	@Override
	public void deleteByMemberIdAndProductId(String memberId,String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_BY_MEMBERID_AND_PRODUCTID);

			pstmt.setString(1, memberId);
			pstmt.setString(2, productId);

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

	/**
	 * added by Sophia
	 */
	@Override
	public FavoritesVO findByMemberIdAndProductId(String memberId,String productId) {
		FavoritesVO favoritesVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MEMBERID_AND_PRODUCTID_STMT);
			pstmt.setString(1, memberId);
			pstmt.setString(2, productId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				favoritesVO = new FavoritesVO();
				favoritesVO.setUniqueid(rs.getString("uniqueid"));
				favoritesVO.setMember_id(rs.getString("member_id"));
				favoritesVO.setFavorite_type(rs.getInt("favorite_type"));
				favoritesVO.setFavorite_id(rs.getString("favorite_id"));
				favoritesVO.setFavorite_add_time(rs.getTimestamp("favorite_add_time"));
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
		return favoritesVO;
	}
	
	//移除收藏
	@Override
	public void delete_fav17(String memberId , String favoriteId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		


		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_FAV);

			pstmt.setString(1, memberId);
			pstmt.setString(2, favoriteId);

			pstmt.executeUpdate();
			


		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<FavoritesVO> getAllMeberfav(String memberId ) {
		List<FavoritesVO> list = new ArrayList<FavoritesVO>();
		FavoritesVO favoritesVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_FAV);
			
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				favoritesVO = new FavoritesVO();
				favoritesVO.setUniqueid(rs.getString("uniqueid"));
				favoritesVO.setMember_id(rs.getString("member_id"));
				favoritesVO.setFavorite_type(rs.getInt("favorite_type"));
				favoritesVO.setFavorite_id(rs.getString("favorite_id"));
				favoritesVO.setFavorite_add_time(rs.getTimestamp("favorite_add_time"));
				list.add(favoritesVO); 
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
//		FavoritesJDBCDAO dao = new FavoritesJDBCDAO();
//
//		// �s�W
//		FavoritesVO favoritesVO1 = new FavoritesVO();
//		favoritesVO1.setMember_id("1005");
//		favoritesVO1.setFavorite_type(1);
//		favoritesVO1.setFavorite_id("OOO");
//		favoritesVO1.setFavorite_add_time(java.sql.Timestamp.valueOf("2020-12-08 21:37:13"));
//		dao.insert(favoritesVO1);
//
//		// �ק�
//		FavoritesVO favoritesVO2 = new FavoritesVO();
//		favoritesVO2.setUniqueid("FAVORITES00100");
//		favoritesVO2.setMember_id("1006");
//		favoritesVO2.setFavorite_type(3);
//		favoritesVO2.setFavorite_id("XXX");
//		favoritesVO2.setFavorite_add_time(java.sql.Timestamp.valueOf("2020-12-11 21:37:13"));
//		dao.update(favoritesVO2);
//
//		// �R��
////		dao.delete("FAVORITES00100");
//
//		// �d��(�浧)
//		FavoritesVO favoritesVO3 = dao.findByPrimaryKey("FAVORITES00050");
//		System.out.print(favoritesVO3.getUniqueid() + ",");
//		System.out.print(favoritesVO3.getMember_id() + ",");
//		System.out.print(favoritesVO3.getFavorite_type() + ",");
//		System.out.print(favoritesVO3.getFavorite_id() + ",");
//		System.out.println(favoritesVO3.getFavorite_add_time() + ",");
//		System.out.println("---------------------");
//
//		// �d��(����)
//		List<FavoritesVO> list = dao.getAll();
//		for (FavoritesVO favoritesVO4 : list) {
//			System.out.print(favoritesVO4.getUniqueid() + ",");
//			System.out.print(favoritesVO4.getMember_id() + ",");
//			System.out.print(favoritesVO4.getFavorite_type() + ",");
//			System.out.print(favoritesVO4.getFavorite_id() + ",");
//			System.out.print(favoritesVO4.getFavorite_add_time() + ",");
//			System.out.println();
//		}
//	}
}
