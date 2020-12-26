package com.bandtag.model;

import java.sql.*;
import java.util.*;

public class BandTagDAOJDBC implements BandTagDAO_interface {

	private static final String INSERT_PSTMT = "INSERT INTO BANDTAG VALUES ('BANDTAG'||LPAD(BANDTAG_SEQ.NEXTVAL, 5, '0'), ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE BANDTAG SET BAND_ID = ?, TAG_ID = ? WHERE BAND_TAG_ID = ?";
	private static final String DELETE_PSTMT = "DELETE FROM BANDTAG WHERE BAND_TAG_ID = ?";
	private static final String GET_ONE_PSTMT = "SELECT * FROM BANDTAG WHERE BAND_TAG_ID = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM BANDTAG ORDER BY BAND_TAG_ID";
	
	public static void main(String[] args) {

		BandTagDAOJDBC dao = new BandTagDAOJDBC();
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

	public void testInsert(BandTagDAOJDBC dao) {
		// 測試新增
		BandTagVO insert = new BandTagVO();
		insert.setBand_id("BAND00000");
		insert.setTag_id("TAGS00000");
		dao.insert(insert);
		System.out.println("===inserted===");
	}

	public void testUpdate(BandTagDAOJDBC dao) {
		// 測試修改
		BandTagVO update = new BandTagVO();
		update.setBand_tag_id("BANDTAG00250");
		update.setBand_id("BAND00000");
		update.setTag_id("TAGS00000");
		dao.update(update);
		System.out.println("===updated===");
	}

	public void testDelete(BandTagDAOJDBC dao) {
		// 測試刪除
		dao.delete("BANDTAG00250");
		System.out.println("===deleted===");
	}

	public void testSelectOne(BandTagDAOJDBC dao) {
		// 測試查詢一筆
		System.out.println("===============selectOne===============");
		BandTagVO selectOne = dao.findByPrimaryKey("BANDTAG00250");
		System.out.println(selectOne.toString());
		System.out.println("=======================================");
	}

	public void testSelectAll(BandTagDAOJDBC dao) {
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
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT_PSTMT);

			pstmt.setString(1, bandTagVO.getBand_id());
			pstmt.setString(2, bandTagVO.getTag_id());
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
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
	}

	@Override
	public void update(BandTagVO bandTagVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, bandTagVO.getBand_id());
			pstmt.setString(2, bandTagVO.getTag_id());
			pstmt.setString(3, bandTagVO.getBand_tag_id());
						
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
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

	}

	@Override
	public void delete(String band_tag_id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE_PSTMT);

			pstmt.setString(1, band_tag_id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
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
	}

	@Override
	public BandTagVO findByPrimaryKey(String band_tag_id) {
		BandTagVO bandTagVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(GET_ONE_PSTMT);

			pstmt.setString(1, band_tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandTagVO = new BandTagVO();
				bandTagVO.setBand_tag_id(rs.getString("BAND_TAG_ID"));
				bandTagVO.setBand_id(rs.getString("BAND_ID"));
				bandTagVO.setTag_id(rs.getString("TAG_ID"));

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

		return bandTagVO;
	}

	@Override
	public List<BandTagVO> getAll() {
		
		List<BandTagVO> list = new ArrayList();
		BandTagVO bandTagVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(GET_ALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandTagVO = new BandTagVO();
				bandTagVO.setBand_tag_id(rs.getString("BAND_TAG_ID"));
				bandTagVO.setBand_id(rs.getString("BAND_ID"));
				bandTagVO.setTag_id(rs.getString("TAG_ID"));
				list.add(bandTagVO);

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

}
