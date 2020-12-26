package com.orderlist.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.utils.DataSourceUtils;

public class OrderListJNDIDAO implements OrderListDAO_interface {

	private static final String FIND_BY_ORDER_ID_STMT = "SELECT * FROM ORDERLIST where ORDER_ID = ?";

	private static final String FIND_BY_PRODUCT_ID_STMT = "SELECT * FROM ORDERLIST where PRODUCT_ID = ?";
	
	@Override
	public void insert(OrderListVO orderListVo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OrderListVO orderListVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String orderlist_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public OrderListVO findByPrimaryKey(String orderlist_id) {
		return null;
	}

	@Override
	public List<OrderListVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderListVO> findByOrderId(String orderId) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DataSourceUtils.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_ORDER_ID_STMT);
			pstmt.setString(1, orderId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects
				OrderListVO vo = new OrderListVO();
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrderlist_goods_amount(rs.getInt("ORDERLIST_GOODS_AMOUNT"));
				vo.setOrderlist_id(rs.getString("ORDERLIST_ID"));
				vo.setOrderlist_remarks(rs.getString("ORDERLIST_REMARKS"));
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setReview_hidden(rs.getInt("REVIEW_HIDDEN"));
				vo.setReview_msg(rs.getString("REVIEW_MSG"));
				vo.setReview_score(rs.getInt("REVIEW_SCORE"));
				vo.setReview_time(rs.getTimestamp("REVIEW_TIME"));
				list.add(vo); // Store the row in the list
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
	public List<OrderListVO> findByproductId(String product_Id) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DataSourceUtils.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_ORDER_ID_STMT);
			pstmt.setString(1, product_Id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// vo �]�٬� Domain objects
				OrderListVO vo = new OrderListVO();
				vo.setOrder_id(rs.getString("ORDER_ID"));
				vo.setOrderlist_goods_amount(rs.getInt("ORDERLIST_GOODS_AMOUNT"));
				vo.setOrderlist_id(rs.getString("ORDERLIST_ID"));
				vo.setOrderlist_remarks(rs.getString("ORDERLIST_REMARKS"));
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setReview_hidden(rs.getInt("REVIEW_HIDDEN"));
				vo.setReview_msg(rs.getString("REVIEW_MSG"));
				vo.setReview_score(rs.getInt("REVIEW_SCORE"));
				vo.setReview_time(rs.getTimestamp("REVIEW_TIME"));
				list.add(vo); // Store the row in the list
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

}
