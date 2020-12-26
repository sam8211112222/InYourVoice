package com.tags.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class TagsService {
	
	private TagsDAO_interface dao;
	
	public TagsService() {
		dao = new TagsDAOJDBC();
	}
	
	public static void main(String[] args) {
//		TagsService service = new TagsService();
//		service.insertTags("testServiceInsert", new Timestamp(Calendar.getInstance().getTimeInMillis()));
//		service.updateTags("TAGS00250", "testServiceUpdate", new Timestamp(Calendar.getInstance().getTimeInMillis()));
//		service.deleteTag("TAGS00250");
//		System.out.println(service.getOneTag("TAGS00000"));
//		System.out.println(service.getAllTags());
	}
	
	public TagsVO insertTags(String tag_name, Timestamp tag_add_time) {
		
		TagsVO tagsVO = new TagsVO();
		tagsVO.setTag_name(tag_name);
		tagsVO.setTag_add_time(tag_add_time);
		
		dao.insert(tagsVO);
		
		return tagsVO;
		
	}
	
	public TagsVO updateTags(String tag_id, String tag_name, Timestamp tag_add_time) {
		
		TagsVO tagsVO = new TagsVO();
		
		tagsVO.setTag_id(tag_id);
		tagsVO.setTag_name(tag_name);
		tagsVO.setTag_add_time(tag_add_time);
		
		dao.update(tagsVO);
		
		return tagsVO;
	}
	
	public void deleteTag(String tag_id) {
		dao.delete(tag_id);
	}
	
	public TagsVO getOneTag(String tag_id) {
		return dao.findByPrimaryKey(tag_id);
	}
	
	public List<TagsVO> getAllTags(){
		return dao.getAll();
	}

}
