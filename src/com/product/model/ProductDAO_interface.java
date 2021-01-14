package com.product.model;

import java.util.List;

import com.eventorderlist.model.EventOrderListVO;
import com.orderlist.model.OrderListVO;
import com.productphoto.model.ProductPhotoVO;
import com.ticket.model.TicketVO;

public interface ProductDAO_interface {
	public void insert(ProductVO productVO);

	public void update(ProductVO productVO);

	public void delete(String product_id);

	public ProductVO findByPrimaryKey(String product_id);

	public List<ProductVO> getAll();

	public List<ProductVO> getTime();

	public void launch(ProductVO productVO);

	public void dislaunch(ProductVO productVO);

	public void approval(ProductVO productVO);

	public List<ProductVO> getApproval();

	public List<ProductVO> getUnapproval();

	public List<ProductVO> getBand(String band_id);

	public List<ProductVO> getBandListByTime(String band_id);

	public List<OrderListVO> getOrder(String band_id);

	/**
     * 異動產品的庫存數量
     * @param productId 產品id
     * @param stockDifference 庫存變動數
     * //這是鈺涵的方法
     */
    void updateStock(String productId,int stockDifference);
    
    /**
     * //這是鈺涵的方法
     */
	List<ProductVO> findByProductName(String name);
	
	/**

     * //這是鈺涵的方法
     */
	List<ProductVO> findByProductType(String productType);
	
	/**
	 * added by  鈺涵
	 */
	List<ProductVO> findProductForList(String productName, String productType);
	
	
	
	//冠華
	//這是新增的搜尋方法
    public List<ProductVO> findByName(String product_name);
    
    //Sam
    public List<EventOrderListVO> getEOrder(String band_id);
}
