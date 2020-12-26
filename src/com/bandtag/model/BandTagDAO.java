package com.bandtag.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BandTagDAO implements BandTagDAO_interface {
	
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

	private static final String INSERT_PSTMT = "INSERT INTO BANDTAG VALUES ('BANDTAG'||LPAD(BANDTAG_SEQ.NEXTVAL, 5, '0'), ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE BANDTAG SET BAND_ID = ?, TAG_ID = ? WHERE BAND_TAG_ID = ?";
	private static final String DELETE_PSTMT = "DELETE FROM BANDTAG WHERE BAND_TAG_ID = ?";
	private static final String GET_ONE_PSTMT = "SELECT * FROM BANDTAG WHERE BAND_TAG_ID = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM BANDTAG ORDER BY BAND_TAG_ID";
	
	public static void main(String[] args) {

		BandTagDAO dao = new BandTagDAO();
//		
//		// 測試新增
//		dao.testInsert(dao);
//		// 測試修改
//		dao.testUpdate(dao);
//		// 測試刪除
//		dao.testDelete(dao);
//		// 測試查詢一筆
//		dao.testSelectOne(dao);
//		// 測試查詢多筆
//		dao.testSelectAll(dao);
	}

	public void testInsert(BandTagDAO dao) {
		// 測試新增
		BandTagVO insert = new BandTagVO();
		insert.setBand_id("BAND00000");
		insert.setTag_id("TAGS00000");
		dao.insert(insert);
		System.out.println("===inserted===");
	}

	public void testUpdate(BandTagDAO dao) {
		// 測試修改
		BandTagVO update = new BandTagVO();
		update.setBand_tag_id("BANDTAG00250");
		update.setBand_id("BAND00000");
		update.setTag_id("TAGS00000");
		dao.update(update);
		System.out.println("===updated===");
	}

	public void testDelete(BandTagDAO dao) {
		// 測試刪除
		dao.delete("BANDTAG00250");
		System.out.println("===deleted===");
	}

	public void testSelectOne(BandTagDAO dao) {
		// 測試查詢一筆
		System.out.println("===============selectOne===============");
		BandTagVO selectOne = dao.findByPrimaryKey("BANDTAG00250");
		System.out.println(selectOne.toString());
		System.out.println("=======================================");
	}

	public void testSelectAll(BandTagDAO dao) {
		// 測試查詢多筆
		List<BandTagVO> list = dao.getAll();
		System.out.println("===============selectAll===============");
		int i = 0;
		for (BandTagVO VO : list) {
			System.out.println("=============selectAll:" + i + "==============");
			System.out.println(VO.toString());
			i++;
		}

	}
	
	@Override
	public void insert(BandTagVO bandTagVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_PSTMT);

			pstmt.setString(1, bandTagVO.getBand_id());
			pstmt.setString(2, bandTagVO.getTag_id());
			
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
	public void update(BandTagVO bandTagVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, bandTagVO.getBand_id());
			pstmt.setString(2, bandTagVO.getTag_id());
			pstmt.setString(3, bandTagVO.getBand_tag_id());
						
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
	public void delete(String band_tag_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_PSTMT);

			pstmt.setString(1, band_tag_id);

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
	public BandTagVO findByPrimaryKey(String band_tag_id) {
		BandTagVO bandTagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PSTMT);

			pstmt.setString(1, band_tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandTagVO = new BandTagVO();
				bandTagVO.setBand_tag_id(rs.getString("BAND_TAG_ID"));
				bandTagVO.setBand_id(rs.getString("BAND_ID"));
				bandTagVO.setTag_id(rs.getString("TAG_ID"));

			}

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

		return bandTagVO;
	}

	@Override
	public List<BandTagVO> getAll() {
		
		List<BandTagVO> list = new ArrayList();
		BandTagVO bandTagVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandTagVO = new BandTagVO();
				bandTagVO.setBand_tag_id(rs.getString("BAND_TAG_ID"));
				bandTagVO.setBand_id(rs.getString("BAND_ID"));
				bandTagVO.setTag_id(rs.getString("TAG_ID"));
				list.add(bandTagVO);

			}

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

		return list;
	}

}
