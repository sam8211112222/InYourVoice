package com.favorites.model;

import java.util.List;


public class FavoritesService {
	private FavoritesDAO_interface dao;

	public FavoritesService() {
		dao = new FavoritesJDBCDAO();
	}
	public FavoritesVO addFavorites( String member_id, int favorite_type,String favorite_id,	
			java.sql.Timestamp favorite_add_time) {

		FavoritesVO favoritesVO = new FavoritesVO();
		
		favoritesVO.setMember_id(member_id);
		favoritesVO.setFavorite_type(favorite_type);
		favoritesVO.setFavorite_id(favorite_id);
		favoritesVO.setFavorite_add_time(favorite_add_time);
		dao.insert(favoritesVO);
		
		return favoritesVO;
	}

	public FavoritesVO updateFavorites(String uniqueid, String member_id, int favorite_type,
			String favorite_id, java.sql.Timestamp favorite_add_time) {

		FavoritesVO favoritesVO = new FavoritesVO();
		
		favoritesVO.setUniqueid(uniqueid);
		favoritesVO.setMember_id(member_id);
		favoritesVO.setFavorite_type(favorite_type);
		favoritesVO.setFavorite_id(favorite_id);
		favoritesVO.setFavorite_add_time(favorite_add_time);
		dao.update(favoritesVO);
		
		return favoritesVO;
	}


	public void deleteFavorites(String uniqueid) {
		dao.delete(uniqueid);
	}

	public FavoritesVO getOneFavorites(String uniqueid) {
		return dao.findByPrimaryKey(uniqueid);
	}

	public List<FavoritesVO> getAll() {
		return dao.getAll();
	}
	
	public static void main(String[] args) {
		FavoritesService favoritesService = new FavoritesService();
		
		// �s�W
//		favoritesService.addFavorites("1007", 3, "XXX==", java.sql.Timestamp.valueOf("2021-12-11 21:37:13"));
		// �ק�
//		favoritesService.updateFavorites("FAVORITES00100", "1006", 3, "XXX", java.sql.Timestamp.valueOf("2020-12-11 21:37:13"));
		// �R��
//		favoritesService.deleteFavorites("FAVORITES00100");
		// �d�� (�浧)
//		favoritesService.getOneFavorites("FAVORITES00050");
		// �d�� (����)
//		favoritesService.getAll();
		

	}
	
}
