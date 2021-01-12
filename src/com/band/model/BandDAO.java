package com.band.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.MemberVo;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Band;

public class BandDAO implements BandDAO_interface {
	
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

	private static final String INSERT_PSTMT = "INSERT INTO BAND VALUES ('BAND'||LPAD(BAND_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE BAND SET BAND_NAME = ?, BAND_INTRO = ?, BAND_PHOTO = ?, BAND_BANNER = ?, BAND_PIECE_CHECK = ?, BAND_ADD_TIME = ?, BAND_STATUS = ?, BAND_LAST_EDIT_TIME = ?, BAND_LAST_EDITOR = ? WHERE BAND_ID = ?";
	private static final String DELETE_PSTMT = "DELETE FROM BAND WHERE BAND_ID = ?";
	private static final String GET_ONE_PSTMT = "SELECT * FROM BAND WHERE BAND_ID = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM BAND ORDER BY BAND_ID";
	// Kevin========================================================================================================
		private static final String UPDATE_MEMBER_BAND_ID = "UPDATE MEMBERS SET BAND_ID=? WHERE MEMBER_ID=?";
		private static final String UPDATE_BAND_INTRO ="UPDATE BAND SET BAND_INTRO=? WHERE BAND_ID=?";
		private static final String UPDATE_BAND_STATUS_BACKEND ="UPDATE BAND SET BAND_STATUS=? WHERE BAND_ID=?";
	//==============================================================================================================
		// 冠華
		//這是新增的搜尋方法
			private static final String GET_BAND_BYNAME_PSTMT = "SELECT * FROM BAND WHERE BAND_NAME LIKE ?";

	@Override
	public void insert(BandVO bandVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_PSTMT);

			pstmt.setString(1, bandVO.getBand_name());
			pstmt.setString(2, bandVO.getBand_intro());
			pstmt.setBytes(3, bandVO.getBand_photo());
			pstmt.setBytes(4, bandVO.getBand_banner());
			pstmt.setBytes(5, bandVO.getBand_piece_check());
			pstmt.setTimestamp(6, bandVO.getBand_add_time());
			pstmt.setInt(7, bandVO.getBand_status());
			pstmt.setTimestamp(8, bandVO.getBand_last_edit_time());
			pstmt.setString(9, bandVO.getBand_last_editor());

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
	public void update(BandVO bandVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, bandVO.getBand_name());
			pstmt.setString(2, bandVO.getBand_intro());
			pstmt.setBytes(3, bandVO.getBand_photo());
			pstmt.setBytes(4, bandVO.getBand_banner());
			pstmt.setBytes(5, bandVO.getBand_piece_check());
			pstmt.setTimestamp(6, bandVO.getBand_add_time());
			pstmt.setInt(7, bandVO.getBand_status());
			pstmt.setTimestamp(8, bandVO.getBand_last_edit_time());
			pstmt.setString(9, bandVO.getBand_last_editor());
			pstmt.setString(10, bandVO.getBand_id());

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
	public void delete(String band_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_PSTMT);

			pstmt.setString(1, band_id);

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
	public BandVO findByPrimaryKey(String band_id) {
		BandVO bandVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PSTMT);

			pstmt.setString(1, band_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandVO = new BandVO();
				bandVO.setBand_id(rs.getString("BAND_ID"));
				bandVO.setBand_name(rs.getString("BAND_NAME"));
				bandVO.setBand_intro(rs.getString("BAND_INTRO"));
				bandVO.setBand_photo(rs.getBytes("BAND_PHOTO"));
				bandVO.setBand_banner(rs.getBytes("BAND_BANNER"));
				bandVO.setBand_piece_check(rs.getBytes("BAND_PIECE_CHECK"));
				bandVO.setBand_add_time(rs.getTimestamp("BAND_ADD_TIME"));
				bandVO.setBand_status(rs.getInt("BAND_STATUS"));
				bandVO.setBand_last_edit_time(rs.getTimestamp("BAND_LAST_EDIT_TIME"));
				bandVO.setBand_last_editor(rs.getString("BAND_LAST_EDITOR"));

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

		return bandVO;
	}

	@Override
	public List<BandVO> getAll() {
		List<BandVO> list = new ArrayList();
		BandVO bandVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				bandVO = new BandVO();
				bandVO.setBand_id(rs.getString("BAND_ID"));
				bandVO.setBand_name(rs.getString("BAND_NAME"));
				bandVO.setBand_intro(rs.getString("BAND_INTRO"));
				bandVO.setBand_photo(rs.getBytes("BAND_PHOTO"));
				bandVO.setBand_banner(rs.getBytes("BAND_BANNER"));
				bandVO.setBand_piece_check(rs.getBytes("BAND_PIECE_CHECK"));
				bandVO.setBand_add_time(rs.getTimestamp("BAND_ADD_TIME"));
				bandVO.setBand_status(rs.getInt("BAND_STATUS"));
				bandVO.setBand_last_edit_time(rs.getTimestamp("BAND_LAST_EDIT_TIME"));
				bandVO.setBand_last_editor(rs.getString("BAND_LAST_EDITOR"));
				list.add(bandVO);

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
	
	public List<BandVO> getAll(Map<String, String[]> map) {
		List<BandVO> list = new ArrayList<BandVO>();
		BandVO bandVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * from band "
		          + jdbcUtil_CompositeQuery_Band.get_WhereCondition(map)
		          + "order by band_id";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				bandVO = new BandVO();
				bandVO.setBand_id(rs.getString("band_id"));
				bandVO.setBand_name(rs.getString("band_name"));
				bandVO.setBand_intro(rs.getString("band_intro"));
				list.add(bandVO); // Store the row in the List
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	// Kevin===========================================================================================
		@Override
		public BandVO updateBandIntro(BandVO bandVO,String bandIntro) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_BAND_INTRO);

				pstmt.setString(1, bandIntro);
				pstmt.setString(2, bandVO.getBand_id());
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
			return bandVO;

			
		}
		
		@Override //新增交易 申請樂團INSERT 會員BAND_ID
		public void insertBand(BandVO bandVO,MemberVo memberVo) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				con.setAutoCommit(false);
				String[] cols = { "BAND_ID" };
				pstmt = con.prepareStatement(INSERT_PSTMT,cols);
				pstmt.setString(1, bandVO.getBand_name());
				pstmt.setString(2, bandVO.getBand_intro());
				pstmt.setBytes(3, bandVO.getBand_photo());
				pstmt.setBytes(4, bandVO.getBand_banner());
				pstmt.setBytes(5, bandVO.getBand_piece_check());
				pstmt.setTimestamp(6, bandVO.getBand_add_time());
				pstmt.setInt(7, bandVO.getBand_status());
				pstmt.setTimestamp(8, bandVO.getBand_last_edit_time());
				pstmt.setString(9, bandVO.getBand_last_editor());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				String bandKey = null;
				if (rs.next()) {
					bandKey = rs.getString(1);
				}
				rs.close();
				pstmt = con.prepareStatement(UPDATE_MEMBER_BAND_ID);
				pstmt.setString(1, bandKey);
				pstmt.setString(2, memberVo.getMemberId());
				pstmt.executeUpdate();
				con.commit();
				con.setAutoCommit(true);
				// Handle any SQL errors
			} catch (SQLException se) {
				se.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		
		// 冠華
		//這是新增的搜尋方法	與  拿圖片
			@Override
			public List<BandVO> findByName(String band_name) {
				List<BandVO> list = new ArrayList<>();
				BandVO bandVO = null;

				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(GET_BAND_BYNAME_PSTMT);

					pstmt.setString(1, "%" + band_name + "%");
					
					rs = pstmt.executeQuery();

					while (rs.next()) {

						bandVO = new BandVO();
						bandVO.setBand_id(rs.getString("BAND_ID"));
						bandVO.setBand_name(rs.getString("BAND_NAME"));
						bandVO.setBand_intro(rs.getString("BAND_INTRO"));
						bandVO.setBand_photo(rs.getBytes("BAND_PHOTO"));
						bandVO.setBand_banner(rs.getBytes("BAND_BANNER"));
						bandVO.setBand_piece_check(rs.getBytes("BAND_PIECE_CHECK"));
						bandVO.setBand_add_time(rs.getTimestamp("BAND_ADD_TIME"));
						bandVO.setBand_status(rs.getInt("BAND_STATUS"));
						bandVO.setBand_last_edit_time(rs.getTimestamp("BAND_LAST_EDIT_TIME"));
						bandVO.setBand_last_editor(rs.getString("BAND_LAST_EDITOR"));
						list.add(bandVO);

					}

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
			@Override
			public BandVO getBandPhoto(String band_id) {
				
				BandVO bandVO = null;
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(GET_ONE_PSTMT);

					pstmt.setString(1, band_id);

					rs = pstmt.executeQuery();

					while (rs.next()) {

						bandVO = new BandVO();
						bandVO.setBand_photo(rs.getBytes("Band_PHOTO"));

					}

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

				return bandVO;
			}

}
