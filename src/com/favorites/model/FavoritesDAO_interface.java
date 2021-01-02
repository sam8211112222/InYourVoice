package com.favorites.model;

import java.util.*;


public interface FavoritesDAO_interface {
	public void insert(FavoritesVO favoritesVO);
    public void update(FavoritesVO favoritesVO);
    public void delete(String uniqueid);
    public FavoritesVO findByPrimaryKey(String uniqueid);
    public List<FavoritesVO> getAll();
//    �U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//    public List<FavoritesVO> getAll(Map<String, String[]> map); 
}
