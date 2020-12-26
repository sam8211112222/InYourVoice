package com.tags.model;

import java.util.List;
import database.util.ConnectionInfo;

public interface TagsDAO_interface extends ConnectionInfo {

	public void insert(TagsVO tagsVO);
    public void update(TagsVO tagsVO);
    public void delete(String tag_id);
    public TagsVO findByPrimaryKey(String tag_id);
    public List<TagsVO> getAll();
}
