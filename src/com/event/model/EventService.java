package com.event.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class EventService {

	private EventDAO dao;

	public EventService() {
		dao = new EventJNDIDAO(); 
//		dao = new EventJDBCDAO();
	}

	public EventVO addEvent(String band_id, Integer event_type, Integer event_sort, String event_title,
			String event_detail, byte[] event_poster, Integer event_area, String event_place, String event_city,
			String event_cityarea, String event_address, Timestamp event_start_time, Timestamp event_on_time,
			Timestamp event_last_edit_time, String event_last_editor, Integer event_status, byte[] event_seat) {

		EventVO eventVO = new EventVO();

		eventVO.setBand_id(band_id);
		eventVO.setEvent_type(event_type);
		eventVO.setEvent_sort(event_sort);
		eventVO.setEvent_title(event_title);
		eventVO.setEvent_detail(event_detail);
		eventVO.setEvent_poster(event_poster);
		eventVO.setEvent_area(event_area);
		eventVO.setEvent_place(event_place);
		eventVO.setEvent_city(event_city);
		eventVO.setEvent_cityarea(event_cityarea);
		eventVO.setEvent_address(event_address);
		eventVO.setEvent_start_time(event_start_time);
		eventVO.setEvent_on_time(event_on_time);
		eventVO.setEvent_last_edit_time(event_last_edit_time);
		eventVO.setEvent_last_editor(event_last_editor);
		eventVO.setEvent_status(event_status);
		eventVO.setEvent_seat(event_seat);

		String pk = dao.insert(eventVO);

		eventVO.setEvent_id(pk);

		return eventVO;

	}

	public EventVO updateEvent(String event_id, String band_id, Integer event_type, Integer event_sort,
			String event_title, String event_detail, byte[] event_poster, Integer event_area, String event_place,
			String event_city, String event_cityarea, String event_address, Timestamp event_start_time,
			Timestamp event_on_time, Timestamp event_last_edit_time, String event_last_editor, Integer event_status,
			byte[] event_seat) {

		EventVO eventVO = new EventVO();

		eventVO.setEvent_id(event_id);
		eventVO.setBand_id(band_id);
		eventVO.setEvent_type(event_type);
		eventVO.setEvent_sort(event_sort);
		eventVO.setEvent_title(event_title);
		eventVO.setEvent_detail(event_detail);
		eventVO.setEvent_poster(event_poster);
		eventVO.setEvent_area(event_area);
		eventVO.setEvent_place(event_place);
		eventVO.setEvent_city(event_city);
		eventVO.setEvent_cityarea(event_cityarea);
		eventVO.setEvent_address(event_address);
		eventVO.setEvent_start_time(event_start_time);
		eventVO.setEvent_on_time(event_on_time);
		eventVO.setEvent_last_edit_time(event_last_edit_time);
		eventVO.setEvent_last_editor(event_last_editor);
		eventVO.setEvent_status(event_status);
		eventVO.setEvent_seat(event_seat);

		dao.update(eventVO);

		return eventVO;

	}

	public void deleteEvent(String event_id) {
		dao.delete(event_id);
	}

	public List<EventVO> getAll() {
		return dao.getAll();
	}

	public EventVO getOneEvent(String event_id) {
		return dao.findByPrimaryKey(event_id);
	}

	public List<EventVO> getEventsByBandId(String band_id) {
		Long nowTime = System.currentTimeMillis();
		List<EventVO> bandEvent = dao.getAll().stream().filter(e -> e.getBand_id().equals(band_id))
				.filter(e -> e.getEvent_on_time().getTime() < nowTime)
				.filter(e -> e.getEvent_start_time().getTime() > nowTime).filter(e -> e.getEvent_status() == 1)
				.collect(Collectors.toList());

		return bandEvent;

	}

	public List<EventVO> getEventsForBandManage(String band_id) {
		List<EventVO> bandEvent = dao.getAll().stream().filter(e -> e.getBand_id().equals(band_id))
				.collect(Collectors.toList());

		return bandEvent;
	}

	public List<EventVO> getEventsToConfirm() {
		return dao.getAll().stream().filter(e -> e.getEvent_status() == 2).collect(Collectors.toList());
	}
	
	public void setEventStatus(EventVO eventVO) {
		dao.update(eventVO);
	}
	public void eventOffShelf(String event_id,String emp_id) {
		EventVO eventVO = dao.findByPrimaryKey(event_id);
		Integer event_status = new Integer(0);
		eventVO.setEvent_last_editor(emp_id);
		eventVO.setEvent_status(event_status);
		
		dao.update(eventVO);
		
	}
	
	// Kevin================================================================================================
	public List<EventVO> getListEventOrderby(){
		return dao.eventListOrderBy();
	}
	public List<EventVO> getResult(String searchKeyWord){
	String get_result = "select * from event where event_detail like '%"+searchKeyWord+"%' order by event_start_time desc";
		return dao.eventSelcet(get_result);
	}
	
	//冠華
	//這是新增的搜尋方法
		public List<EventVO> getEventByName(String event_title) {
			return dao.findByName(event_title);
		}
}
