package com.band.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.bandtag.model.BandTagDAOJDBC;
import com.bandtag.model.BandTagDAO_interface;

public class BandService {

	private BandDAO_interface dao;

	public BandService() {
//		dao = new BandDAO();
		dao = new BandDAOJDBC();
	}

	public BandVO insertBand(String band_name, String band_intro, byte[] band_photo, byte[] band_banner, byte[] band_piece_check, java.sql.Timestamp band_add_time, Integer band_status, java.sql.Timestamp band_last_edit_time, String band_last_editor) {

		BandVO bandVO = new BandVO();
		bandVO.setBand_name(band_name);
		bandVO.setBand_intro(band_intro);
		bandVO.setBand_photo(band_photo);
		bandVO.setBand_banner(band_banner);
		bandVO.setBand_piece_check(band_piece_check);
		bandVO.setBand_add_time(band_add_time);
		bandVO.setBand_status(band_status);
		bandVO.setBand_last_edit_time(band_last_edit_time);
		bandVO.setBand_last_editor(band_last_editor);
		dao.insert(bandVO);

		return bandVO;
	}

	public BandVO updateBand(String band_id, String band_name, String band_intro, byte[] band_photo, byte[] band_banner, byte[] band_piece_check, java.sql.Timestamp band_add_time, Integer band_status, java.sql.Timestamp band_last_edit_time, String band_last_editor) {

		BandVO bandVO = new BandVO();

		bandVO.setBand_id(band_id);
		bandVO.setBand_name(band_name);
		bandVO.setBand_intro(band_intro);
		bandVO.setBand_photo(band_photo);
		bandVO.setBand_banner(band_banner);
		bandVO.setBand_piece_check(band_piece_check);
		bandVO.setBand_add_time(band_add_time);
		bandVO.setBand_status(band_status);
		bandVO.setBand_last_edit_time(band_last_edit_time);
		bandVO.setBand_last_editor(band_last_editor);
		dao.update(bandVO);

		return bandVO;

	}

	public void deleteBandtag(String band_id) {
		dao.delete(band_id);
	}

	public BandVO getOneBand(String band_id) {
		return dao.findByPrimaryKey(band_id);
	}

	public List<BandVO> getAllBand() {
		return dao.getAll();
	}
	
	public List<BandVO> getAllBand(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<BandVO> getBySearchKeyWord(String searchKeyWord){
		List<BandVO> listOfSearch = dao.getAll().stream()
				.filter(b -> b.getBand_name().contains(searchKeyWord) || b.getBand_intro().contains(searchKeyWord))
//				.filter(b -> b.getBand_intro().contains(searchKeyWord))
				.collect(Collectors.toList());
			System.out.println(listOfSearch.size());
		return listOfSearch;		
	}
	
// Kevin===========================================================================================================
	public BandVO updateBandIntro(String bandId,String bandIntro) {
		BandVO bandVO = getOneBand(bandId);
		bandVO = dao.updateBandIntro(bandVO,bandIntro);
		return bandVO;
	}
	public byte[] getBandSong(String bandId) {
		BandVO bandVO = getOneBand(bandId);
		byte[] song = bandVO.getBand_piece_check();
		return song;
	}
	public byte[] getBanner(String bandId) {
		BandVO bandVO = getOneBand(bandId);
		byte[] banner = bandVO.getBand_banner();
		return banner;
	}
	public BandVO updateBandStatus(String bandId) {
		BandVO bandVO = getOneBand(bandId);
		bandVO.setBand_status(1);
		dao.update(bandVO);
		return bandVO;
	}
	//取得樂團圖片
	public byte[] getBandpic(String bandId) {
		BandVO bandVO = getOneBand(bandId);
		byte[] pic = bandVO.getBand_photo();
		return pic;
	}


	public BandVO updateBandStatusBackEnd(String bandId,String bandStatus,Timestamp bandLastEditTime,String bandLastEditor) {
		BandVO bandVO = getOneBand(bandId);
		bandVO.setBand_status(Integer.valueOf(bandStatus));
		bandVO.setBand_last_editor(bandLastEditor);
		bandVO.setBand_last_edit_time(bandLastEditTime);
		dao.update(bandVO);
		return bandVO;		
	}

}
