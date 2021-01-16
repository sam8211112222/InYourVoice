package com.event.model;

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

public class EventJNDIDAO implements EventDAO {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO event (event_id," + "band_id," + "event_type,"
			+ "event_sort," + "event_title," + "event_detail," + "event_poster," + "event_area," + "event_place,"
			+ "event_city," + "event_cityarea," + "event_address," + "event_start_time," + "event_on_time,"
			+ "event_last_edit_time," + "event_last_editor," + "event_status,"
			+ "event_seat) VALUES ('EVENT'||LPAD(EVENT_SEQ.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT event_id, band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city,event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time,event_last_editor, event_status, event_seat FROM event ORDER BY EVENT_ID";
	private static final String GET_ONE_STMT = "SELECT " + "band_id," + "event_type," + "event_sort," + "event_title,"
			+ "event_detail," + "event_poster," + "event_area," + "event_place," + "event_city," + "event_cityarea,"
			+ "event_address," + "event_start_time," + "event_on_time," + "event_last_edit_time," + "event_last_editor,"
			+ "event_status," + "event_seat FROM event where event_id = ?";
	private static final String DELETE = "DELETE FROM event where event_id = ?";
	private static final String UPDATE = "UPDATE event set band_id=?," + "event_type=?," + "event_sort=?,"
			+ "event_title=?," + "event_detail=?," + "event_poster=?," + "event_area=?," + "event_place=?,"
			+ "event_city=?," + "event_cityarea=?," + "event_address=?," + "event_start_time=?," + "event_on_time=?,"
			+ "event_last_edit_time=?," + "event_last_editor=?," + "event_status=?,"
			+ "event_seat=? where event_id = ?";
	private static final String GET_LIST_BY_BANDID = "SELECT event_id, band_id, event_type, event_sort, event_title, event_detail, event_poster, event_area, event_place, event_city,event_cityarea, event_address, event_start_time, event_on_time, event_last_edit_time,event_last_editor, event_status, event_seat FROM event WHERE BAND_ID = ?  ORDER BY EVENT_SORT";
	// Kevin
	private static final String GET_ALL_EVENT = "select * from event order by event_start_time desc";
	// 冠華
	//這是新增的搜尋方法
		private static final String GET_EVENT_BYNAME_PSTMT = "SELECT * FROM EVENT WHERE EVENT_TITLE LIKE ?";
	
	@Override
	public String insert(EventVO eventVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String pk = null;
		ResultSet rs=null;

		try {

			con = ds.getConnection();
			String[] cols= {"event_id"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setString(1, eventVO.getBand_id());
			pstmt.setInt(2, eventVO.getEvent_type());
			pstmt.setInt(3, eventVO.getEvent_sort());
			pstmt.setString(4, eventVO.getEvent_title());
			pstmt.setString(5, eventVO.getEvent_detail());
			pstmt.setBytes(6, eventVO.getEvent_poster());
			pstmt.setInt(7, eventVO.getEvent_area());
			pstmt.setString(8, eventVO.getEvent_place());
			pstmt.setString(9, eventVO.getEvent_city());
			pstmt.setString(10, eventVO.getEvent_cityarea());
			pstmt.setString(11, eventVO.getEvent_address());
			pstmt.setTimestamp(12, eventVO.getEvent_start_time());
			pstmt.setTimestamp(13, eventVO.getEvent_on_time());
			pstmt.setTimestamp(14, eventVO.getEvent_last_edit_time());
			pstmt.setString(15, eventVO.getEvent_last_editor());
			pstmt.setInt(16, eventVO.getEvent_status());
			pstmt.setBytes(17, eventVO.getEvent_seat());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			while (rs.next()) {
				pk = rs.getString(1);
			}

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
		return pk;
	}

	@Override
	public void update(EventVO eventVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eventVO.getBand_id());
			pstmt.setInt(2, eventVO.getEvent_type());
			pstmt.setInt(3, eventVO.getEvent_sort());
			pstmt.setString(4, eventVO.getEvent_title());
			pstmt.setString(5, eventVO.getEvent_detail());
			pstmt.setBytes(6, eventVO.getEvent_poster());
			pstmt.setInt(7, eventVO.getEvent_area());
			pstmt.setString(8, eventVO.getEvent_place());
			pstmt.setString(9, eventVO.getEvent_city());
			pstmt.setString(10, eventVO.getEvent_cityarea());
			pstmt.setString(11, eventVO.getEvent_address());
			pstmt.setTimestamp(12, eventVO.getEvent_start_time());
			pstmt.setTimestamp(13, eventVO.getEvent_on_time());
			pstmt.setTimestamp(14, eventVO.getEvent_last_edit_time());
			pstmt.setString(15, eventVO.getEvent_last_editor());
			pstmt.setInt(16, eventVO.getEvent_status());
			pstmt.setBytes(17, eventVO.getEvent_seat());
			pstmt.setString(18, eventVO.getEvent_id());

			pstmt.executeUpdate();

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
	public void delete(String event_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con= ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, event_id);

			pstmt.executeUpdate();

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
	public EventVO findByPrimaryKey(String event_id) {
		EventVO eventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, event_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventVO();

				eventVO.setEvent_id(event_id);
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));
			}

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
		return eventVO;
	}

	@Override
	public List<EventVO> getAll() {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));

				list.add(eventVO); // Store the row in the list
			}

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
	public List<EventVO> findByBandId(String band_id) {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_BANDID);
			pstmt.setString(1, band_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));

				list.add(eventVO); // Store the row in the list
			}

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
	public List<EventVO> eventListOrderBy() {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_EVENT);		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));
			

				list.add(eventVO); // Store the row in the list
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
	public List<EventVO> eventSelcet(String sql) {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
			

				list.add(eventVO); // Store the row in the list
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
	
	// 冠華
	@Override
	public List<EventVO> findByName(String event_title) {
		List<EventVO> list = new ArrayList<>();
		EventVO eventVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EVENT_BYNAME_PSTMT);

			pstmt.setString(1, "%" + event_title + "%");
			
			rs = pstmt.executeQuery();

			while (rs.next()) {

				eventVO = new EventVO();
				eventVO.setEvent_id(rs.getString("event_id"));
				eventVO.setBand_id(rs.getString("band_id"));
				eventVO.setEvent_type(rs.getInt("event_type"));
				eventVO.setEvent_sort(rs.getInt("event_sort"));
				eventVO.setEvent_title(rs.getString("event_title"));
				eventVO.setEvent_detail(rs.getString("event_detail"));
				eventVO.setEvent_poster(rs.getBytes("event_poster"));
				eventVO.setEvent_area(rs.getInt("event_area"));
				eventVO.setEvent_place(rs.getString("event_place"));
				eventVO.setEvent_city(rs.getString("event_city"));
				eventVO.setEvent_cityarea(rs.getString("event_cityarea"));
				eventVO.setEvent_address(rs.getString("event_address"));
				eventVO.setEvent_start_time(rs.getTimestamp("event_start_time"));
				eventVO.setEvent_on_time(rs.getTimestamp("event_on_time"));
				eventVO.setEvent_last_edit_time(rs.getTimestamp("event_last_edit_time"));
				eventVO.setEvent_last_editor(rs.getString("event_last_editor"));
				eventVO.setEvent_status(rs.getInt("event_status"));
				eventVO.setEvent_seat(rs.getBytes("event_seat"));
				list.add(eventVO);
			}

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
}
