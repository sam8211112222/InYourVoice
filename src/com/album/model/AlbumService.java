package com.album.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class AlbumService {
	
	private AlbumDAO_interface dao;

	public AlbumService() {
		dao = new AlbumDAO();
//		dao = new AlbumDAOJDBC();
	}
	
	public static void main(String[] args) {
		AlbumService service = new AlbumService();
//		service.insertAlbum("BAND00000", "testServiceInsert", "testServiceInsert", new byte[1024], 0, new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis()), "testServiceInsert");
//		service.updateAlbum("ALBUM00250", "BAND00000", "testServiceUpdate", "testServiceUpdate", new byte[1024], 0, new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis()), "testServiceUpdate");
//		service.deleteAlbum("ALBUM00250");
//		System.out.println(service.getOneAlbum("ALBUM00000"));
//		System.out.println(service.getAllAlbums());
	}
	public AlbumVO insertAlbum(AlbumVO albumVO) {
		
		String pk = dao.insert(albumVO);
		albumVO.setAlbum_id(pk);
		
		return albumVO;
	}
	
	
	public AlbumVO insertAlbum(String band_id, String album_name, String album_intro, byte[] album_photo, Integer album_status, Timestamp album_add_time, Timestamp album_release_time, Timestamp album_last_edit_time, String album_last_editor) {
		
		AlbumVO albumVO = new AlbumVO();
		
		albumVO.setBand_id(band_id);
		albumVO.setAlbum_name(album_name);
		albumVO.setAlbum_intro(album_intro);
		albumVO.setAlbum_photo(album_photo);
//		albumVO.setAlbum_status(album_status);
//		albumVO.setAlbum_add_time(album_add_time);
//		albumVO.setAlbum_release_time(album_release_time);
//		albumVO.setAlbum_last_edit_time(album_last_edit_time);
		albumVO.setAlbum_last_editor(album_last_editor);
		
		String pk = dao.insert(albumVO);
		albumVO.setAlbum_id(pk);
		
		return albumVO;
	}
	
	public AlbumVO updateAlbum(String album_id, String album_name, String album_intro, byte[] album_photo, Integer album_status, Timestamp album_release_time, Timestamp album_last_edit_time, String album_last_editor) {
		
		AlbumVO albumVO = new AlbumVO();
		
		albumVO.setAlbum_id(album_id);
		albumVO.setAlbum_name(album_name);
		albumVO.setAlbum_intro(album_intro);
		albumVO.setAlbum_photo(album_photo);
		albumVO.setAlbum_status(album_status);
		albumVO.setAlbum_release_time(album_release_time);
		albumVO.setAlbum_last_edit_time(album_last_edit_time);
		albumVO.setAlbum_last_editor(album_last_editor);
		
		
		dao.update(albumVO);
		
		return albumVO;
		
	}
	
	public AlbumVO updateAlbum(AlbumVO albumVO) {
		
		dao.update(albumVO);
		
		return albumVO;
		
	}
	
	public void deleteAlbum(String album_id) {
		dao.delete(album_id);
	}
	
	public void deleteAlbum(Connection con, String album_id) {
		dao.delete(con, album_id);
	}
	
	public AlbumVO getOneAlbum(String album_id) {
		return dao.findByPrimaryKey(album_id);
	}
	
	public List<AlbumVO> getAllAlbums(){
		return dao.getAll();		
	}
	
	public AlbumVO getAlbumPhoto(String album_id) {
		return dao.getAlbumPhoto(album_id);
	}
	
	public List<AlbumVO> getBandAlbums(String band_id){
		return dao.getAllByBandId(band_id);
	}
	
	public List<AlbumVO> getBandAlbumsReleasable(String band_id){
			
		return dao.getAllByBandId(band_id).stream()
				.filter(a -> a.getAlbum_status() != 0) // 非下架狀態
				.filter(a -> a.getAlbum_release_time().getTime() <= System.currentTimeMillis()) // 
				.collect(Collectors.toList());
		
	}
	
	//冠華的方法===============================
	
	public List<AlbumVO> getAlbumByName(String album_name) {
		return dao.findByName(album_name);
	}
}
