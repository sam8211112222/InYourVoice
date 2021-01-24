package com.product.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import com.eventorderlist.model.EventOrderListVO;
import com.orderlist.model.OrderListVO;
import com.ticket.model.TicketVO;

public class ProductService {

	private ProductDAO_interface dao;

	public ProductService() {
//		dao = new ProductJDBCDAO();
		dao = new ProductDAO();
	}

	public ProductVO addProduct(String band_id, Integer product_type, String product_name, String product_intro,
			String product_detail, Double product_price, Integer product_stock, Integer product_check_status,
			Integer product_status, Timestamp product_on_time, Timestamp product_off_time, Timestamp product_add_time,
			Double product_discount, Timestamp product_discount_on_time, Timestamp product_discount_off_time,
			Timestamp product_last_edit_time, String product_last_editor) {
		ProductVO productVO = new ProductVO();

		productVO.setBand_id(band_id);
		productVO.setProduct_type(product_type);
		productVO.setProduct_name(product_name);
		productVO.setProduct_intro(product_intro);
		productVO.setProduct_detail(product_detail);
		productVO.setProduct_price(product_price);
		productVO.setProduct_stock(product_stock);
		productVO.setProduct_check_status(product_check_status);
		productVO.setProduct_status(product_status);
		productVO.setProduct_on_time(product_on_time);
		productVO.setProduct_off_time(product_off_time);
		productVO.setProduct_add_time(product_add_time);
		productVO.setProduct_discount(product_discount);
		productVO.setProduct_discount_on_time(product_discount_on_time);
		productVO.setProduct_discount_off_time(product_discount_off_time);
		productVO.setProduct_last_edit_time(product_last_edit_time);
		productVO.setProduct_last_editor(product_last_editor);

		dao.insert(productVO);
		return productVO;
	}

	public ProductVO updateProduct(String product_id, String band_id, Integer product_type, String product_name,
			String product_intro, String product_detail, Double product_price, Integer product_stock,
			Integer product_check_status, Integer product_status, Timestamp product_on_time, Timestamp product_off_time,
			Timestamp product_add_time, Double product_discount, Timestamp product_discount_on_time,
			Timestamp product_discount_off_time, Timestamp product_last_edit_time, String product_last_editor) {
		ProductVO productVO = new ProductVO();

		productVO.setProduct_id(product_id);
		productVO.setBand_id(band_id);
		productVO.setProduct_type(product_type);
		productVO.setProduct_name(product_name);
		productVO.setProduct_intro(product_intro);
		productVO.setProduct_detail(product_detail);
		productVO.setProduct_price(product_price);
		productVO.setProduct_stock(product_stock);
		productVO.setProduct_check_status(product_check_status);
		productVO.setProduct_status(product_status);
		productVO.setProduct_on_time(product_on_time);
		productVO.setProduct_off_time(product_off_time);
		productVO.setProduct_add_time(product_add_time);
		productVO.setProduct_discount(product_discount);
		productVO.setProduct_discount_on_time(product_discount_on_time);
		productVO.setProduct_discount_off_time(product_discount_off_time);
		productVO.setProduct_last_edit_time(product_last_edit_time);
		productVO.setProduct_last_editor(product_last_editor);

		dao.update(productVO);
		return productVO;
	}

	public ProductVO launchProduct(String product_id) {
		ProductVO productVO = new ProductVO();
		productVO.setProduct_id(product_id);
		dao.launch(productVO);
		return productVO;
	}

	public ProductVO approvalProduct(String product_id) {
		ProductVO productVO = new ProductVO();
		productVO.setProduct_id(product_id);
		dao.approval(productVO);
		return productVO;
	}

	public ProductVO dislaunchProduct(String product_id) {
		ProductVO productVO = new ProductVO();
		productVO.setProduct_id(product_id);
		dao.dislaunch(productVO);
		return productVO;
	}

	public void deleteProduct(String product_id) {
		dao.delete(product_id);
	}

	public ProductVO getOneProduct(String product_id) {
		return dao.findByPrimaryKey(product_id);
	}

	public List<ProductVO> getAll() {
		return dao.getAll();
	}

	public List<ProductVO> getApproval() {
		return dao.getApproval();
	}

	public List<ProductVO> getUnapproval() {
		return dao.getUnapproval();
	}

	public List<ProductVO> getBand(String band_id) {
		return dao.getBand(band_id);
	}

	public List<ProductVO> getBandListByTime(String band_id) {
		return dao.getBandListByTime(band_id);
	}

	public List<ProductVO> getTime() {
		return dao.getTime();
	}

	public List<OrderListVO> getOrder(String band_id) {
		return dao.getOrder(band_id);
	}

	// 這是鈺涵的
		public List<ProductVO> getAllByBand(String band_id) {
			Long nowTime = System.currentTimeMillis();
			List<ProductVO> bandProduct = dao.getAll().stream().filter(e -> e.getBand_id().equals(band_id))
					.filter(e -> e.getProduct_on_time().getTime() < nowTime)
					.filter(e -> e.getProduct_off_time().getTime() > nowTime).filter(e -> e.getProduct_status() == 1)
					.collect(Collectors.toList());
			return bandProduct;
		}

		// 這是鈺涵的
		public List<ProductVO> getAllAvailableProduct() {
			Long nowTime = System.currentTimeMillis();

			
			List<ProductVO> productList = dao.getAll().stream().filter(e -> e.getProduct_on_time().getTime() < nowTime)
					.filter(e -> e.getProduct_off_time().getTime() > nowTime).filter(e -> e.getProduct_status() == 1)
					.collect(Collectors.toList());
			Map<Long,String> productTime = new HashMap<Long, String>();
			long[] timeStampArray = new long[productList.size()];
			
			for(ProductVO product:productList) {
				productTime.put(product.getProduct_on_time().getTime(),product.getProduct_id());
			}
			
			for(int i = 0 ;i<timeStampArray.length;i++) {
				timeStampArray[i]=productList.get(i).getProduct_on_time().getTime();
			}
			
			Arrays.sort(timeStampArray);
			
			List<ProductVO> productListOrderByTime = new ArrayList<ProductVO>();
			
			for(int k  =timeStampArray.length-1 ; k>=0 ;k--) {
				for (int i = 0; i < productList.size(); i++) {
					if (productTime.get(timeStampArray[k]).equals(productList.get(i).getProduct_id())) {
						productListOrderByTime.add(productList.get(i));
					}
				}
			}
			
			return productListOrderByTime;
		}

		// 這是鈺涵的
		public void updateStock(String productId, int stockDifference) {
			try {

				dao.updateStock(productId, stockDifference);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 這是鈺涵的
		public List<ProductVO> findByProductName(String productName) {
			try {
				Long nowTime = System.currentTimeMillis();
				return dao.findByProductName(productName).stream().filter(e -> e.getProduct_on_time().getTime() < nowTime)
						.filter(e -> e.getProduct_off_time().getTime() > nowTime).filter(e -> e.getProduct_status() == 1)
						.collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 這是鈺涵的
		public List<ProductVO> findByProductType(String productType) {
			Long nowTime = System.currentTimeMillis();
			try {
				return dao.findByProductType(productType).stream().filter(e -> e.getProduct_on_time().getTime() < nowTime)
						.filter(e -> e.getProduct_off_time().getTime() > nowTime).filter(e -> e.getProduct_status() == 1)
						.collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 這是鈺涵的
		public List<ProductVO> findProductForList(String productName ,String productType){

			Long nowTime = System.currentTimeMillis();
			try {
				return dao.findProductForList(productName, productType).stream().filter(e -> e.getProduct_on_time().getTime() < nowTime)
						.filter(e -> e.getProduct_off_time().getTime() > nowTime).filter(e -> e.getProduct_status() == 1)
						.collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ArrayList<ProductVO>();
		}
	
	//冠華
	//這是新增的搜尋方法
		public List<ProductVO> getProductByName(String product_name) {
			return dao.findByName(product_name);
		}
		
	//Sam
		//new
		public List<EventOrderListVO> getEOrder(String band_id) {
			return dao.getEOrder(band_id);
		}
}
