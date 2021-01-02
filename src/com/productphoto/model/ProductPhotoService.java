package com.productphoto.model;

import java.sql.Timestamp;
import java.util.List;

import com.productphoto.model.ProductPhotoVO;

public class ProductPhotoService {
	
	private ProductPhotoDAO_interface dao;
	
	public ProductPhotoService() {
		dao = new ProductPhotoJDBCDAO();
	}
	
	public ProductPhotoVO addProductPhoto(String product_id,byte[] productphoto_photo, Integer productphoto_sort,Timestamp productphoto_add_time) {
		ProductPhotoVO productPhotoVO = new ProductPhotoVO();
		productPhotoVO.setProduct_id(product_id);
		productPhotoVO.setProductphoto_photo(productphoto_photo);
		productPhotoVO.setProductphoto_sort(productphoto_sort);
		productPhotoVO.setProductphoto_add_time(productphoto_add_time);
		dao.insert(productPhotoVO);
		return productPhotoVO;
	}
	
	public ProductPhotoVO updateProductPhoto(String productphoto_id,String product_id,byte[] productphoto_photo, Integer productphoto_sort,Timestamp productphoto_add_time) {
		ProductPhotoVO productPhotoVO = new ProductPhotoVO();
		productPhotoVO.setProductphoto_id(productphoto_id);
		productPhotoVO.setProduct_id(product_id);
		productPhotoVO.setProductphoto_photo(productphoto_photo);
		productPhotoVO.setProductphoto_sort(productphoto_sort);
		productPhotoVO.setProductphoto_add_time(productphoto_add_time);
		dao.update(productPhotoVO);
		return productPhotoVO;
	}
	
	public void deleteProductPhoto(String productphoto_id) {
		dao.delete(productphoto_id);
	}

	public ProductPhotoVO getOneProductPhoto(String productphoto_id) {
		return dao.findByPrimaryKey(productphoto_id);
	}

	public List<ProductPhotoVO> getAll() {
		return dao.getAll();
	}
}
