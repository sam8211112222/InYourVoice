package com.member.model;


import java.util.*;



public interface MemberDao_interface {
	public void insert(MemberVo memberVo);
	public void update(MemberVo memberVo);
	public void delete(String memberId);
	public MemberVo findByPrimaryKey(String memberId);
    public List<MemberVo> getAll();
    public MemberVo findByAccount(String memberAccount);
    public void updatePic(MemberVo memberVo,byte[] pic);
    public void updateInfo(MemberVo memberVo);
    public void uddateInfoBack(MemberVo memberVo);
    public void updatePassword(MemberVo memberVo,String memberPassword);
    public void updateAuth(MemberVo memberVo,Integer auth);
    public void updateFromBackEnd(MemberVo memberVo);
    public MemberVo findByBandId(String bandId);

}
