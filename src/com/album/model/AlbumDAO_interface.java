package com.album.model;

import java.sql.Connection;
import java.util.List;
import database.util.ConnectionInfo;

public interface AlbumDAO_interface extends ConnectionInfo{
	
	public String insert(AlbumVO albumVO);
    public void update(AlbumVO albumVO);
    public void delete(String album_id);
	public void delete(Connection con, String album_id);
    public AlbumVO findByPrimaryKey(String album_id);
    public List<AlbumVO> getAll();
    public AlbumVO getAlbumPhoto(String album_id);
    public List<AlbumVO> getAllByBandId(String band_id);
    
    //冠華的方法======================
    
    public List<AlbumVO> findByName(String album_name);

}
