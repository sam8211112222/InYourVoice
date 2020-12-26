package com.tags.model;

import java.sql.*;
import java.util.*;

public class TagsDAOJDBC implements TagsDAO_interface{
	
	private static final String INSERT_PSTMT = "INSERT INTO TAGS VALUES ('TAGS'||LPAD(TAGS_SEQ.NEXTVAL, 5, '0'), ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE TAGS SET TAG_NAME = ?, TAG_ADD_TIME = ? WHERE TAG_ID = ?";
	private static final String DELETE_PSTMT = "DELETE FROM TAGS WHERE TAG_ID = ?";
	private static final String GET_ONE_PSTMT = "SELECT * FROM TAGS WHERE TAG_ID = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM TAGS ORDER BY TAG_ID";
	
	public static void main(String[] args) {

		TagsDAOJDBC dao = new TagsDAOJDBC();
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

	public void testInsert(TagsDAOJDBC dao) {
		// 測試新增
		TagsVO insert = new TagsVO();
		insert.setTag_name("testInsert");
		insert.setTag_add_time(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		dao.insert(insert);
		System.out.println("===inserted===");
	}

	public void testUpdate(TagsDAOJDBC dao) {
		// 測試修改
		TagsVO update = new TagsVO();
		update.setTag_id("TAGS00250");
		update.setTag_name("testUpdate");
		update.setTag_add_time(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		dao.update(update);
		System.out.println("===updated===");
	}

	public void testDelete(TagsDAOJDBC dao) {
		// 測試刪除
		dao.delete("TAGS00250");
		System.out.println("===deleted===");
	}

	public void testSelectOne(TagsDAOJDBC dao) {
		// 測試查詢一筆
		System.out.println("===============selectOne===============");
		TagsVO selectOne = dao.findByPrimaryKey("TAGS00050");
		System.out.println(selectOne.toString());
		System.out.println("=======================================");
	}

	public void testSelectAll(TagsDAOJDBC dao) {
		// 測試查詢多筆
		List<TagsVO> list = dao.getAll();
		System.out.println("===============selectAll===============");
		int i = 0;
		for (TagsVO VO : list) {
			System.out.println("=============selectAll:" + i + "==============");
			System.out.println(VO.toString());
			i++;
		}

	}

	@Override
	public void insert(TagsVO tagsVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(INSERT_PSTMT);
			
			pstmt.setString(1, tagsVO.getTag_name());
			pstmt.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			
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
	public void update(TagsVO tagsVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, tagsVO.getTag_name());
			pstmt.setTimestamp(2, tagsVO.getTag_add_time());			
			pstmt.setString(3, tagsVO.getTag_id());
			
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
	public void delete(String tag_id) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(DELETE_PSTMT);

			pstmt.setString(1, tag_id);

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
	public TagsVO findByPrimaryKey(String tag_id) {
		
		TagsVO tagsVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(GET_ONE_PSTMT);

			pstmt.setString(1, tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				tagsVO = new TagsVO();
				tagsVO.setTag_id(rs.getString("TAG_ID"));
				tagsVO.setTag_name(rs.getString("TAG_NAME"));
				tagsVO.setTag_add_time(rs.getTimestamp("TAG_ADD_TIME"));
				
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

		return tagsVO;
	}

	@Override
	public List<TagsVO> getAll() {
		List<TagsVO> list = new ArrayList();
		TagsVO tagsVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = conn.prepareStatement(GET_ALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				tagsVO = new TagsVO();
				tagsVO.setTag_id(rs.getString("TAG_ID"));
				tagsVO.setTag_name(rs.getString("TAG_NAME"));
				tagsVO.setTag_add_time(rs.getTimestamp("TAG_ADD_TIME"));
				list.add(tagsVO);

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
