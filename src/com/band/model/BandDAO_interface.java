package com.band.model;

import java.util.List;
import java.util.Map;

import com.member.model.MemberVo;

import database.util.ConnectionInfo;

public interface BandDAO_interface extends ConnectionInfo {
	
	public void insert(BandVO bandVO);
    public void update(BandVO bandVO);
    public void delete(String band_id);
    public BandVO findByPrimaryKey(String band_id);
    public List<BandVO> getAll();
  //萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<BandVO> getAll(Map<String, String[]> map);
	public BandVO updateBandIntro(BandVO bandVO, String bandIntro);
	void insertBand(BandVO bandVO, MemberVo memberVo);
	
	// 冠華
	//這是新增的搜尋方法  與  拿圖片
    public List<BandVO> findByName(String band_name);
    public BandVO getBandPhoto(String band_id);
	

}
