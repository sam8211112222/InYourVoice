package com.emp.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class EmpDAO implements EmpDAO_interface {
	
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
		"INSERT INTO emp (emp_id,emp_password,emp_add_time,emp_mail,emp_phone,emp_status,emp_auth,emp_last_edit_time,emp_last_editor) VALUES ('EMP'||LPAD(EMP_SEQ.NEXTVAL, 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT emp_id,emp_password,to_char(emp_add_time,'yyyy-mm-dd hh24:mi:ss') emp_add_time,emp_mail,emp_phone,emp_status,emp_auth,to_char(emp_last_edit_time,'yyyy-mm-dd hh24:mi:ss') emp_last_edit_time,emp_last_editor FROM emp order by emp_id";
	private static final String GET_ONE_STMT = 
		"SELECT emp_id,emp_password,to_char(emp_add_time,'yyyy-mm-dd hh24:mi:ss') emp_add_time,emp_mail,emp_phone,emp_status,emp_auth,to_char(emp_last_edit_time,'yyyy-mm-dd hh24:mi:ss') emp_last_edit_time,emp_last_editor FROM emp where emp_id = ?";
	private static final String DELETE = 
		"DELETE FROM emp where emp_id = ?";
	private static final String UPDATE = 
		"UPDATE emp set emp_password=?, emp_add_time=?, emp_mail=?, emp_phone=?, emp_status=?, emp_auth=?, emp_last_edit_time=?, emp_last_editor=? where emp_id = ?";
	private static final String FIND_BY_Account="select * from emp where emp_Mail = ?";
	
	@Override
	public void insert(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, empVO.getEmp_password());
			pstmt.setTimestamp(2, empVO.getEmp_add_time());
			pstmt.setString(3, empVO.getEmp_mail());
			pstmt.setString(4, empVO.getEmp_phone());
			pstmt.setInt(5, empVO.getEmp_status());
			pstmt.setInt(6, empVO.getEmp_auth());
			pstmt.setTimestamp(7, empVO.getEmp_last_edit_time());
			pstmt.setString(8, empVO.getEmp_last_editor());		

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
	public void update(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, empVO.getEmp_password());
			pstmt.setTimestamp(2, empVO.getEmp_add_time());
			pstmt.setString(3, empVO.getEmp_mail());
			pstmt.setString(4, empVO.getEmp_phone());
			pstmt.setInt(5, empVO.getEmp_status());
			pstmt.setInt(6, empVO.getEmp_auth());
			pstmt.setTimestamp(7, empVO.getEmp_last_edit_time());
			pstmt.setString(8, empVO.getEmp_last_editor());	
			pstmt.setString(9, empVO.getEmp_id());

			pstmt.executeUpdate();
			
//			System.out.println("�ק令�\");

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
	public void delete(String emp_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
//		System.out.println("�R�����\");
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_id);

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
	public EmpVO findByPrimaryKey(String emp_id) {

		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emp_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				empVO = new EmpVO();
								
				empVO.setEmp_id(rs.getString("emp_id"));
				empVO.setEmp_password(rs.getString("emp_password"));
				empVO.setEmp_add_time(rs.getTimestamp("emp_add_time"));
				empVO.setEmp_mail(rs.getString("emp_mail"));
				empVO.setEmp_phone(rs.getString("emp_phone"));
				empVO.setEmp_status(rs.getInt("emp_status"));
				empVO.setEmp_auth(rs.getInt("emp_auth"));
				empVO.setEmp_last_edit_time(rs.getTimestamp("emp_last_edit_time"));
				empVO.setEmp_last_editor(rs.getString("emp_last_editor"));
			}

//			System.out.print(empVO.getEmp_id() + ",");
//			System.out.print(empVO.getEmp_password() + ",");
//			System.out.print(empVO.getEmp_add_time() + ",");
//			System.out.print(empVO.getEmp_mail() + ",");
//			System.out.print(empVO.getEmp_phone() + ",");
//			System.out.print(empVO.getEmp_status() + ",");
//			System.out.print(empVO.getEmp_auth() + ",");
//			System.out.print(empVO.getEmp_last_edit_time() + ",");
//			System.out.println(empVO.getEmp_last_editor());
//			
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
		return empVO;
	}

	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				empVO = new EmpVO();
				empVO.setEmp_id(rs.getString("emp_id"));
				empVO.setEmp_password(rs.getString("emp_password"));
				empVO.setEmp_add_time(rs.getTimestamp("emp_add_time"));
				empVO.setEmp_mail(rs.getString("emp_mail"));
				empVO.setEmp_phone(rs.getString("emp_phone"));
				empVO.setEmp_status(rs.getInt("emp_status"));
				empVO.setEmp_auth(rs.getInt("emp_auth"));
				empVO.setEmp_last_edit_time(rs.getTimestamp("emp_last_edit_time"));
				empVO.setEmp_last_editor(rs.getString("emp_last_editor"));
				list.add(empVO); // Store the row in the list
			}
//			for (EmpVO empVO4 : list) {
//				System.out.print(empVO4.getEmp_id() + ",");
//				System.out.print(empVO4.getEmp_password() + ",");
//				System.out.print(empVO4.getEmp_add_time() + ",");
//				System.out.print(empVO4.getEmp_mail() + ",");
//				System.out.print(empVO4.getEmp_phone() + ",");
//				System.out.print(empVO4.getEmp_status() + ",");
//				System.out.print(empVO4.getEmp_auth() + ",");
//				System.out.print(empVO4.getEmp_last_edit_time() + ",");
//				System.out.print(empVO4.getEmp_last_editor());
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
	//登入
	@Override
	public EmpVO findByAccount(String empMail) {
	

			EmpVO empVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_BY_Account);

				pstmt.setString(1, empMail);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo �]�٬� Domain objects
					empVO = new EmpVO();
									
					empVO.setEmp_id(rs.getString("emp_id"));
					empVO.setEmp_password(rs.getString("emp_password"));
					empVO.setEmp_add_time(rs.getTimestamp("emp_add_time"));
					empVO.setEmp_mail(rs.getString("emp_mail"));
					empVO.setEmp_phone(rs.getString("emp_phone"));
					empVO.setEmp_status(rs.getInt("emp_status"));
					empVO.setEmp_auth(rs.getInt("emp_auth"));
					empVO.setEmp_last_edit_time(rs.getTimestamp("emp_last_edit_time"));
					empVO.setEmp_last_editor(rs.getString("emp_last_editor"));
				}

//				System.out.print(empVO.getEmp_id() + ",");
//				System.out.print(empVO.getEmp_password() + ",");
//				System.out.print(empVO.getEmp_add_time() + ",");
//				System.out.print(empVO.getEmp_mail() + ",");
//				System.out.print(empVO.getEmp_phone() + ",");
//				System.out.print(empVO.getEmp_status() + ",");
//				System.out.print(empVO.getEmp_auth() + ",");
//				System.out.print(empVO.getEmp_last_edit_time() + ",");
//				System.out.println(empVO.getEmp_last_editor());
//				
//				System.out.println("---------------------");

				
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
			return empVO;
		}
	
	
	
	public static void main(String[] args) {
//		
//		EmpDAO dao = new EmpDAO();
//
//		// �s�W
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEmp_password("kjijij");
//		empVO1.setEmp_add_time(java.sql.Timestamp.valueOf("2020-12-11 21:37:13"));
//		empVO1.setEmp_mail("dsaf@gmail.com");
//		empVO1.setEmp_phone("0900-00007");
//		empVO1.setEmp_status(1);
//		empVO1.setEmp_auth(2);
//		empVO1.setEmp_last_edit_time(java.sql.Timestamp.valueOf("2020-11-11 21:37:13"));
//		empVO1.setEmp_last_editor("123214");
//		dao.insert(empVO1);
//
//		// �ק�
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmp_id("250");
//		empVO2.setEmp_password("jiji");
//		empVO2.setEmp_add_time(java.sql.Timestamp.valueOf("2020-12-11 21:37:13"));
//		empVO2.setEmp_mail("XXXXX@gmail.com");
//		empVO2.setEmp_phone("0900-06060");
//		empVO2.setEmp_status(1);
//		empVO2.setEmp_auth(2);
//		empVO2.setEmp_last_edit_time(java.sql.Timestamp.valueOf("2020-11-11 21:37:13"));
//		empVO2.setEmp_last_editor("1");
//		dao.update(empVO2);
//
//		// �R��
//		dao.delete("EMP00150");
//
//		// �d��
//		EmpVO empVO3 = dao.findByPrimaryKey("EMP00100");
//		System.out.print(empVO3.getEmp_id() + ",");
//		System.out.print(empVO3.getEmp_password() + ",");
//		System.out.print(empVO3.getEmp_add_time() + ",");
//		System.out.print(empVO3.getEmp_mail() + ",");
//		System.out.print(empVO3.getEmp_phone() + ",");
//		System.out.print(empVO3.getEmp_status() + ",");
//		System.out.print(empVO3.getEmp_auth() + ",");
//		System.out.print(empVO3.getEmp_last_edit_time() + ",");
//		System.out.println(empVO3.getEmp_last_editor());
//		
//		System.out.println("---------------------");
//
//		// �d��
//		List<EmpVO> list = dao.getAll();
//		for (EmpVO empVO4 : list) {
//			System.out.print(empVO4.getEmp_id() + ",");
//			System.out.print(empVO4.getEmp_password() + ",");
//			System.out.print(empVO4.getEmp_add_time() + ",");
//			System.out.print(empVO4.getEmp_mail() + ",");
//			System.out.print(empVO4.getEmp_phone() + ",");
//			System.out.print(empVO4.getEmp_status() + ",");
//			System.out.print(empVO4.getEmp_auth() + ",");
//			System.out.print(empVO4.getEmp_last_edit_time() + ",");
//			System.out.print(empVO4.getEmp_last_editor());
//			System.out.println();
//		}
		
	}


}