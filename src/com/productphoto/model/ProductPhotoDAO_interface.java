package com.productphoto.model;

import java.util.List;

public interface ProductPhotoDAO_interface {
	public void insert(ProductPhotoVO productPhotoVO);
    public void update(ProductPhotoVO productPhotoVO);
    public void delete(String productphoto_id);
    public ProductPhotoVO findByPrimaryKey(String productphoto_id);
    public List<ProductPhotoVO> getAllByBand(String band_id);
    public List<ProductPhotoVO> getAll();
  //這是鈺涵的方法
    /**
     * 用產品id取得一張照片資料
     * @param product_id
     * @return
     */
   
    byte[] getImage(String product_id);
    
  //這是鈺涵的方法
    /**
     * 用產品id取得第一張照片資料(依sort排序的第一張)
     * @param product_id
     * @return
     */
    byte[] getFirstImageByProductId(String product_id);
    
  //這是鈺涵的方法
    /**
     * 依產品id取得該產品所有照片的照片id
     * @param productId
     * @return
     */
    List<String> getIdListByProductId(String productId);
    
  //這是鈺涵的方法
    /**
     * 依照片id取得照片資料
     * @param photoId
     * @return
     */
    byte[] getImageByPhotoId(String photoId);
}
