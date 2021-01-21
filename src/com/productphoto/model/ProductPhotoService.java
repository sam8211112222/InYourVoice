package com.productphoto.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.productphoto.model.ProductPhotoVO;

public class ProductPhotoService {
	
	private ProductPhotoDAO_interface dao;
	
	public ProductPhotoService() {
//		dao = new ProductPhotoJDBCDAO();
		dao = new ProductPhotoDAO();
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
	
	public List<ProductPhotoVO> getAllByBand(String band_id) {
		return dao.getAllByBand(band_id);
	}
	
	public List<ProductPhotoVO> getAll() {
		return dao.getAll();
	}
	
	//這是鈺涵的方法
			public byte[] getImage(String id) {
				byte[] result = null;
				try {
					result = dao.getFirstImageByProductId(id);
				}catch(Exception e) {
					e.printStackTrace();
				}
				return result;
			}
	//這是鈺涵的方法
			public byte[] getImageByPhotoId(String id) {
				byte[] result = null;
				try {
					result = dao.getImageByPhotoId(id);
				}catch(Exception e) {
					e.printStackTrace();
				}
				return result;
			}
			
			//這是鈺涵的方法
			public List<String> getIdListByProductId(String productId){
				List<String> result = null;
				try {
					result = dao.getIdListByProductId(productId);
				}catch(Exception e) {
					e.printStackTrace();
				}
				return result;
			}
			public byte[] findFirst(String productId) {
				ProductPhotoService  ppSvc = new ProductPhotoService();
				List<ProductPhotoVO> list = ppSvc.getAll();
				Optional<ProductPhotoVO> productphoto = list.stream().filter(p -> p.getProduct_id().equals(productId)).findFirst();
				ProductPhotoVO ph = productphoto.get();
				byte[] photo = ph.getProductphoto_photo();				
				return photo;			
			}
	}