package com.product.model;

import java.util.List;

public interface ProductDAO_interface {
	public void insert(ProductVO productVO);
    public void update(ProductVO productVO);
    public void delete(String product_id);
    public ProductVO findByPrimaryKey(String product_id);
    public List<ProductVO> getAll();
    public void launch(ProductVO productVO);
    public List<ProductVO> getApproval();
    public List<ProductVO> getUnapproval();
}
