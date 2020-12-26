package com.productphoto.model;

import java.util.List;

public interface ProductPhotoDAO_interface {
	public void insert(ProductPhotoVO productPhotoVO);
    public void update(ProductPhotoVO productPhotoVO);
    public void delete(String productphoto_id);
    public ProductPhotoVO findByPrimaryKey(String productphoto_id);
    public List<ProductPhotoVO> getAll();
}
