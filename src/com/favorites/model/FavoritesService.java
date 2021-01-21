package com.favorites.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FavoritesService {
	private FavoritesDAO_interface dao;

	public FavoritesService() {
//		dao = new FavoritesJDBCDAO();
		dao = new FavoritesDAO();
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
	
// Kevin======================================================================================================
	public List<FavoritesVO> getMemberFav(String memberId) {
		
		List<FavoritesVO> list  = dao.getAll();
		Predicate<FavoritesVO> member = f -> f.getMember_id().equals(memberId);
		List<FavoritesVO> fav = list.stream().filter(member).collect(Collectors.toList());

		return fav;
	}
	
	/**
	 * added by 鈺涵
	 * @param memberId
	 * @param productId
	 * @return
	 */
	public boolean deleteByMemberIdAndProductId(String memberId,String productId) {
		try {
			dao.deleteByMemberIdAndProductId(memberId, productId);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * added by  鈺涵
	 * @param memberId
	 * @param productId
	 * @return
	 */
	public FavoritesVO findByMemberIdAndProductId(String memberId,String productId) {
		try {
			return dao.findByMemberIdAndProductId(memberId, productId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deleteFav(String favId) {
		FavoritesService favsvc = new FavoritesService();
		List<FavoritesVO> favList = favsvc.getAll();
		Optional<FavoritesVO> fav = favList.stream().filter(f -> f.getFavorite_id().equals(favId)).findFirst();
		dao.delete(fav.get().getUniqueid());
	}
	
	
	//新增收藏的方法
	public void addfav(String memberId,String favId , Integer type) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		addFavorites(memberId, type, favId, ts);
	}
	//移除收藏的方法
	public void deletefav17(String memberId,String favId) {
		dao.delete_fav17(memberId, favId);
	}
	//查使用者的全部收藏
	public List<FavoritesVO> getAllMeberfav(String memberId) {
		List<FavoritesVO> listMeberId  = dao.getAllMeberfav(memberId);
		
		return listMeberId;
	}
	
// Test=======================================================================================================	
//	public static void main(String[] args) {
//		FavoritesService favoritesService = new FavoritesService();
//		
//	
//		
//		 �s�W
//		favoritesService.addFavorites("1007", 3, "XXX==", java.sql.Timestamp.valueOf("2021-12-11 21:37:13"));
//		 �ק�
//		favoritesService.updateFavorites("FAVORITES00100", "1006", 3, "XXX", java.sql.Timestamp.valueOf("2020-12-11 21:37:13"));
//		 �R��
//		favoritesService.deleteFavorites("FAVORITES00100");
//		 �d�� (�浧)
//		favoritesService.getOneFavorites("FAVORITES00050");
//		 �d�� (����)
//		favoritesService.getAll();
//		
//
//	}
	
}
