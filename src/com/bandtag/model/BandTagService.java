package com.bandtag.model;

import java.sql.Timestamp;
import java.util.List;


public class BandTagService {
	
	private BandTagDAO_interface dao;
	
	public BandTagService() {
		dao = new BandTagDAO();
		dao = new BandTagDAOJDBC();
	}
	
	public static void main(String[] args) {
//		BandTagService service = new BandTagService();
//		service.insertBandtag("BAND00000", "TAGS00000");
//		service.updateBandtag("BANDTAG00250", "BAND00200", "TAGS00150");
//		service.deleteBandtag("BANDTAG00250");
//		System.out.println(service.getOneBandTag("BANDTAG00000"));
//		System.out.println(service.getAllBandTags());
	}
	
	public BandTagVO insertBandtag(String band_id, String tag_id) {
		
		BandTagVO bandTagVO = new BandTagVO();
		
		bandTagVO.setBand_id(band_id);;
		bandTagVO.setTag_id(tag_id);;
		
		dao.insert(bandTagVO);
		
		return bandTagVO;
	}
	
	public BandTagVO updateBandtag(String band_tag_id, String band_id, String tatag_idgId) {
		
		BandTagVO bandTagVO = new BandTagVO();
		
		bandTagVO.setBand_tag_id(band_tag_id);;
		bandTagVO.setBand_id(band_id);;
		bandTagVO.setTag_id(tatag_idgId);;
		
		dao.update(bandTagVO);
		
		return bandTagVO;
		
	}
	
	public void deleteBandtag(String band_tag_id) {
		dao.delete(band_tag_id);
	}
	
	public BandTagVO getOneBandTag(String band_tag_id) {
		return dao.findByPrimaryKey(band_tag_id);
	}
	
	public List<BandTagVO> getAllBandTags(){
		return dao.getAll();		
	}

}
