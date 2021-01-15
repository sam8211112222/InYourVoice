package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDao implements MemberDao_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "insert into members (member_Id,member_Account,member_Password,member_Gender,member_Phone,member_Address,member_Name,member_Nickname,member_Birth,member_Photo,member_Msg_Auth,member_Card_Number,member_Card_Expyear,member_Card_Expmonth,member_add_Time,band_Id) values('MEMBERS'||LPAD(members_seq.NEXTVAL, 5, '0'),?,?,?,?,?,?,?,?,?,1,?,?,?,?,?)";
	private static final String DELETE_BYPRIMARYKEY = "delete from members where member_Id =?";
	private static final String UPDATE_STMT = "update members set member_phone=?,member_address=?,member_nickname=?,member_birth=? where member_id = ?";
	private static final String FIND_BY_PRIMARY_KEY = "select * from members where member_id = ?";
	private static final String GET_ALL = "select * from members order by member_id";
	private static final String UPDATE_PASSWORD = "update members set member_password=? where member_id=?";
	private static final String UPDATE_MEMBER_PHOTO = "update members set member_photo=? where member_id=?";
	private static final String FIND_BY_Account = "select * from members where member_Account = ?";
	private static final String UPDATE_MEMBER_INFO = "update members set member_Gender=?,member_phone=?,member_address=?,member_Name=?,member_nickname=?,Member_Card_Number=?,member_Card_Expyear=?,member_Card_Expmonth=? where member_id = ?";
	private static final String UPDATE_MEMBER_INFO_BACK = "update members set member_Name=?, member_Nickname=?, member_Gender=?, member_Phone=?, member_Address=?,band_Id=?,member_Msg_Auth=?,member_Birth=?  where member_id = ?";
	private static final String UPDATE_MEMBER_AUTH = "update members set member_Msg_Auth=? where member_Id = ?";
	private static final String UPDATE_MEMBER_FORMBACKEND = "update members set member_name=?, member_Gender=?,member_phone=? where member_id=?";
	private static final String FIND_BY_BANDID ="select * from members where band_id = ?";
	@Override
	public void insert(MemberVo memberVo) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(INSERT_STMT);
			psmt.setString(1, memberVo.getMemberAccount());
			psmt.setString(2, memberVo.getMemberPassword());
			psmt.setString(3, memberVo.getMemberGender());
			psmt.setString(4, memberVo.getMemberPhone());
			psmt.setString(5, memberVo.getMemberAddress());
			psmt.setString(6, memberVo.getMemberName());
			psmt.setString(7, memberVo.getMemberNickname());
			psmt.setDate(8, memberVo.getMemberBirth());
			psmt.setBytes(9, memberVo.getMemberPhoto());
			psmt.setString(10, memberVo.getMemberCardNumber());
			psmt.setInt(11, memberVo.getMemberCardExpyear());
			psmt.setInt(12, memberVo.getMemberCardExpmonth());
			psmt.setTimestamp(13, memberVo.getAddTime());
			psmt.setString(14, memberVo.getBandId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void update(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_STMT);
			psmt.setString(1, memberVo.getMemberPhone());
			psmt.setString(2, memberVo.getMemberAddress());
			psmt.setString(3, memberVo.getMemberNickname());
			psmt.setDate(4, memberVo.getMemberBirth());
			psmt.setString(5, memberVo.getMemberId());
			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void delete(String memberId) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(DELETE_BYPRIMARYKEY);
			psmt.setString(1, memberId);
			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public MemberVo findByPrimaryKey(String memberId) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		MemberVo memberVo = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(FIND_BY_PRIMARY_KEY);
			psmt.setString(1, memberId);

			rs = psmt.executeQuery();
			while (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setMemberId(rs.getString("Member_Id"));
				memberVo.setMemberAccount(rs.getString("Member_Account"));
				memberVo.setMemberPassword(rs.getString("Member_Password"));
				memberVo.setMemberGender(rs.getString("Member_Gender"));
				memberVo.setMemberPhone(rs.getString("Member_Phone"));
				memberVo.setMemberAddress(rs.getString("Member_Address"));
				memberVo.setMemberName(rs.getString("Member_Name"));
				memberVo.setMemberNickname(rs.getString("Member_Nickname"));
				memberVo.setMemberPhoto(rs.getBytes("Member_Photo"));
				memberVo.setMemberMsgAuth(rs.getInt("Member_Msg_Auth"));
				memberVo.setMemberCardNumber(rs.getString("Member_Card_Number"));
				memberVo.setMemberCardExpyear(rs.getInt("Member_Card_Expyear"));
				memberVo.setMemberCardExpmonth(rs.getInt("Member_Card_Expmonth"));
				memberVo.setAddTime(rs.getTimestamp("Member_add_time"));
				memberVo.setBandId(rs.getString("band_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return memberVo;
	}

	@Override
	public List<MemberVo> getAll() {
		List<MemberVo> list = new ArrayList<MemberVo>();

		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		MemberVo memberVo = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(GET_ALL);

			rs = psmt.executeQuery();

			while (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setMemberId(rs.getString("Member_Id"));
				memberVo.setMemberAccount(rs.getString("Member_Account"));
				memberVo.setMemberPassword(rs.getString("Member_Password"));
				memberVo.setMemberGender(rs.getString("Member_Gender"));
				memberVo.setMemberPhone(rs.getString("Member_Phone"));
				memberVo.setMemberBirth(rs.getDate("Member_Birth"));
				memberVo.setMemberAddress(rs.getString("Member_Address"));
				memberVo.setMemberName(rs.getString("Member_Name"));
				memberVo.setMemberNickname(rs.getString("Member_Nickname"));
				memberVo.setMemberPhoto(rs.getBytes("Member_Photo"));
				memberVo.setMemberMsgAuth(rs.getInt("Member_Msg_Auth"));
				memberVo.setMemberCardNumber(rs.getString("Member_Card_Number"));
				memberVo.setMemberCardExpyear(rs.getInt("Member_Card_Expyear"));
				memberVo.setMemberCardExpmonth(rs.getInt("Member_Card_Expmonth"));
				memberVo.setAddTime(rs.getTimestamp("Member_add_time"));
				memberVo.setBandId(rs.getString("band_id"));
				list.add(memberVo);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

	@Override
	public void updatePassword(MemberVo memberVo, String memberPassword) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_PASSWORD);
			psmt.setString(2, memberVo.getMemberId());
			psmt.setString(1, memberPassword);
			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public MemberVo findByAccount(String memberAccount) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		MemberVo memberVo = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(FIND_BY_Account);
			psmt.setString(1, memberAccount);

			rs = psmt.executeQuery();
			while (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setMemberId(rs.getString("Member_Id"));
				memberVo.setMemberAccount(rs.getString("Member_Account"));
				memberVo.setMemberPassword(rs.getString("Member_Password"));
				memberVo.setMemberGender(rs.getString("Member_Gender"));
				memberVo.setMemberPhone(rs.getString("Member_Phone"));
				memberVo.setMemberAddress(rs.getString("Member_Address"));
				memberVo.setMemberName(rs.getString("Member_Name"));
				memberVo.setMemberNickname(rs.getString("Member_Nickname"));
				memberVo.setMemberPhoto(rs.getBytes("Member_Photo"));
				memberVo.setMemberMsgAuth(rs.getInt("Member_Msg_Auth"));
				memberVo.setMemberCardNumber(rs.getString("Member_Card_Number"));
				memberVo.setMemberCardExpyear(rs.getInt("Member_Card_Expyear"));
				memberVo.setMemberCardExpmonth(rs.getInt("Member_Card_Expmonth"));
				memberVo.setAddTime(rs.getTimestamp("Member_add_time"));
				memberVo.setBandId(rs.getString("band_id"));
				memberVo.setMemberBirth(rs.getDate("member_birth"));
			}

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return memberVo;
	}

	@Override
	public void updatePic(MemberVo memberVo, byte[] pic) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_MEMBER_PHOTO);
			psmt.setBytes(1, pic);
			psmt.setString(2, memberVo.getMemberId());
			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void updateInfo(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_MEMBER_INFO);
			psmt.setString(2, memberVo.getMemberPhone());
			psmt.setString(3, memberVo.getMemberAddress());
			psmt.setString(5, memberVo.getMemberNickname());
			psmt.setString(4, memberVo.getMemberName());
			psmt.setString(1, memberVo.getMemberGender());
			psmt.setString(6, memberVo.getMemberCardNumber());
			psmt.setInt(7, memberVo.getMemberCardExpyear());
			psmt.setInt(8, memberVo.getMemberCardExpmonth());
			psmt.setString(9, memberVo.getMemberId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void uddateInfoBack(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_MEMBER_INFO_BACK);
			psmt.setString(4, memberVo.getMemberPhone());
			psmt.setString(5, memberVo.getMemberAddress());
			psmt.setString(2, memberVo.getMemberNickname());
			psmt.setString(1, memberVo.getMemberName());
			psmt.setString(3, memberVo.getMemberGender());
			psmt.setString(6, memberVo.getBandId());
			psmt.setInt(7, memberVo.getMemberMsgAuth());
			psmt.setDate(8, memberVo.getMemberBirth());
			psmt.setString(9, memberVo.getMemberId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void updateAuth(MemberVo memberVo, Integer auth) {

		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_MEMBER_AUTH);
			psmt.setString(2, memberVo.getMemberId());
			psmt.setInt(1, auth);
			psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void updateFromBackEnd(MemberVo memberVo) {

		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(UPDATE_MEMBER_FORMBACKEND);
			psmt.setString(3, memberVo.getMemberPhone());
			psmt.setString(1, memberVo.getMemberName());
			psmt.setString(2, memberVo.getMemberGender());
			psmt.setString(4, memberVo.getMemberId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public MemberVo findByBandId(String bandId) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		MemberVo memberVo = null;
		try {
			con = ds.getConnection();
			psmt = con.prepareStatement(FIND_BY_BANDID);
			psmt.setString(1, bandId);

			rs = psmt.executeQuery();
			while (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setMemberId(rs.getString("Member_Id"));
				memberVo.setMemberAccount(rs.getString("Member_Account"));
				memberVo.setMemberPassword(rs.getString("Member_Password"));
				memberVo.setMemberGender(rs.getString("Member_Gender"));
				memberVo.setMemberPhone(rs.getString("Member_Phone"));
				memberVo.setMemberAddress(rs.getString("Member_Address"));
				memberVo.setMemberName(rs.getString("Member_Name"));
				memberVo.setMemberNickname(rs.getString("Member_Nickname"));
				memberVo.setMemberPhoto(rs.getBytes("Member_Photo"));
				memberVo.setMemberMsgAuth(rs.getInt("Member_Msg_Auth"));
				memberVo.setMemberCardNumber(rs.getString("Member_Card_Number"));
				memberVo.setMemberCardExpyear(rs.getInt("Member_Card_Expyear"));
				memberVo.setMemberCardExpmonth(rs.getInt("Member_Card_Expmonth"));
				memberVo.setAddTime(rs.getTimestamp("Member_add_time"));
				memberVo.setBandId(rs.getString("band_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		return memberVo;
	}
}

