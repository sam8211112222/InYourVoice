package com.product.model;

import java.sql.Timestamp;
import java.util.List;

public class ProductService {
	
	private ProductDAO_interface dao;

	public ProductService() {
		dao = new ProductJDBCDAO();
	}

	public ProductVO addProduct(String band_id, Integer product_type, String product_name, String product_intro,
			String product_detail, Double product_price, Integer product_stock, Integer product_check_status,
			Integer product_status, Timestamp product_on_time, Timestamp product_off_time, Timestamp product_add_time,
			Double product_discount, Timestamp product_discount_on_time, Timestamp product_discount_off_time, Timestamp product_last_edit_time,
			String product_last_editor) {
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
			Timestamp product_add_time, Double product_discount, Timestamp product_discount_on_time, Timestamp product_discount_off_time, Timestamp product_last_edit_time,
			String product_last_editor) {
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
}
