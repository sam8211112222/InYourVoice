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

	//移除收藏
	public void delete_fav17(String memberId , String favoriteId);
	//查使用者的全部收藏
	public List<FavoritesVO> getAllMeberfav(String memberId);
	
}
