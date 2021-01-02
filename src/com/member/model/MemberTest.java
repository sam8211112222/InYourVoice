package com.member.model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MemberTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
//		MemberVo memberVo = new MemberVo();
//		memberVo.setMemberId("1003");
//		memberVo.setMemberAccount("kevin2");
//		memberVo.setMemberPassword("aabb");
//		memberVo.setMemberPhone("12343367890");
//		memberVo.setMemberGender("M");
//		memberVo.setMemberAddress("3332444423123");
//		memberVo.setMemberName("tsao");
//		memberVo.setMemberNickname("weiwei");
//		memberVo.setMemberMsgAuth(1);
//		memberVo.setMemberCardNumber("s0001231");
//		memberVo.setMemberCardExpyear(12);
//		memberVo.setMemberCardExpmonth(4);
//		memberVo.setBandId("b1120021");
//		Calendar cd = new GregorianCalendar(1991,02,21);
//		java.sql.Date jsd = new java.sql.Date(cd.getTime().getTime());
//		memberVo.setMemberBirth(jsd);
//		Date date = new Date();
//		Timestamp tsp = new Timestamp(date.getTime());
//		memberVo.setAddTime(tsp);
		MemberDaoJDBC mjdbc = new MemberDaoJDBC();
//		mjdbc.insert(memberVo);
//		InputStream is = new FileInputStream("C:\\JavaFramework\\123.png");
//		byte[] b = new byte[is.available()];
//		is.read(b);
//		memberVo.setMemberPhoto(b);
		//////////////
		
//		mjdbc.update(memberVo);
//		MemberVo mv =mjdbc.findByPrimaryKey("1003");
//		List<MemberVo> li = mjdbc.getAll();
//		MemberVo membervo = mjdbc.findByAccount("s00122");
		MemberService msvc = new MemberService();
		MemberVo membervo = msvc.login("s0002", "bb");
		if(membervo == null) {
			System.out.println("沒有此帳號");
		}else {
		System.out.println(membervo.getMemberId());
		}
//		System.out.println(mv.getMemberNickname());
//		for(MemberVo mv:li) {
//			System.out.println(mv.getMemberId());
//		}
		System.out.println("ok");
	}

}
