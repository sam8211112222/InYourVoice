package com.notification.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.model.MemberVo;

public class NotificationDaoJDBC implements NotificationDao_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "TEA102G6";
	String passwd = "123456";
	private static final String INSERT_STMT="insert into notification (notification_Id,notification_Content,notification_Link,notification_Add,notification_Isread,member_Id) values('notification'||LPAD(member_seq.NEXTVAL, 5, '0'),?,?,?,?)";
	private static final String DELETE_BYPRIMARYKEY="delete from member where notification_Id =?";
	private static final String UPDATE_STMT="update notification set notification_Content=?,notification_Link=? where member_id = ?";
	private static final String FIND_BY_PRIMARY_KEY="select * from notification where member_id = ?";
	private static final String GET_ALL="select * from notification order by member_id";
	@Override
	public void insert(NotificationVo notificationVo) {
		// TODO Auto-generated method stub
				Connection con = null;
				PreparedStatement psmt = null;
				try {
					Class.forName(driver);
					con = DriverManager.getConnection(url,userid,passwd);
					psmt = con.prepareStatement(INSERT_STMT);
					psmt.setString(1, notificationVo.getNotificationContent());
					psmt.setString(2, notificationVo.getNotificationLink());
					psmt.setDate(3, notificationVo.getNotificationAddTime());
					psmt.setInt(4,notificationVo.getNotificationIsread());
					psmt.setString(5, notificationVo.getMemberId());
					psmt.executeUpdate();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if (psmt != null) {
						try {
							psmt.close();
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
	public void update(NotificationVo notificationVo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(String notificationId) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			psmt = con.prepareStatement(DELETE_BYPRIMARYKEY);
			psmt.setString(1, notificationId);
			psmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (psmt != null) {
				try {
					psmt.close();
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
	public NotificationVo findByPrimaryKey(String notificationId) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		NotificationVo notificationVo = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			psmt = con.prepareStatement(FIND_BY_PRIMARY_KEY);
			psmt.setString(1, notificationId);
			
			rs = psmt.executeQuery();
			while(rs.next()) {
				notificationVo = new NotificationVo();
				notificationVo.setNotificationId(rs.getString("notification_Id"));
				notificationVo.setNotificationContent(rs.getString("notification_Content"));
				notificationVo.setNotificationLink(rs.getString("notification_Link"));
				notificationVo.setNotificationAddTime(rs.getDate("notification_Add_time"));
				notificationVo.setNotificationIsread(rs.getInt("notification_Isread"));
				notificationVo.setMemberId(rs.getString("member_id"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (psmt != null) {
				try {
					psmt.close();
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
		return notificationVo;
	}
	@Override
	public List<NotificationVo> getAll() {
	List<NotificationVo> list = new ArrayList<NotificationVo>();
		
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		NotificationVo notificationVo = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			psmt = con.prepareStatement(GET_ALL);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				notificationVo = new NotificationVo();
				notificationVo.setNotificationId(rs.getString("notification_Id"));
				notificationVo.setNotificationContent(rs.getString("notification_Content"));
				notificationVo.setNotificationLink(rs.getString("notification_Link"));
				notificationVo.setNotificationAddTime(rs.getDate("notification_Add_time"));
				notificationVo.setNotificationIsread(rs.getInt("notification_Isread"));
				notificationVo.setMemberId(rs.getString("member_id"));
				list.add(notificationVo);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (psmt != null) {
				try {
					psmt.close();
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
