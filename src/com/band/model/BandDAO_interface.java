package com.band.model;

import java.util.List;

import database.util.ConnectionInfo;

public interface BandDAO_interface extends ConnectionInfo {
	
	public void insert(BandVO bandVO);
    public void update(BandVO bandVO);
    public void delete(String band_id);
    public BandVO findByPrimaryKey(String band_id);
    public List<BandVO> getAll();

}
