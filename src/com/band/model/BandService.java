package com.band.model;

import java.util.List;

import com.bandtag.model.BandTagDAOJDBC;
import com.bandtag.model.BandTagDAO_interface;

public class BandService {

	private BandDAO_interface dao;

	public BandService() {
		dao = new BandDAO();
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

}
