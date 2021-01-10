package com.favorites.model;

import java.util.*;


public interface FavoritesDAO_interface {
	public void insert(FavoritesVO favoritesVO);
    public void update(FavoritesVO favoritesVO);
    public void delete(String uniqueid);
    public FavoritesVO findByPrimaryKey(String uniqueid);
    public List<FavoritesVO> getAll();
    
    /**
	 * added by 鈺涵
	 */
	void deleteByMemberIdAndProductId(String memberId, String productId);

	/**
	 * added by 鈺涵
	 */
	FavoritesVO findByMemberIdAndProductId(String memberId, String productId);
//    �U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//    public List<FavoritesVO> getAll(Map<String, String[]> map); 
}
