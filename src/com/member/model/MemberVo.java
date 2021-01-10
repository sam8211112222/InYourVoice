package com.member.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberVo implements Serializable{
	private String memberId;
	private String memberAccount;
	private String memberPassword;
	private String memberGender;
	private String memberPhone;
	private String memberAddress;
	private String memberName;
	private String memberNickname;
	private java.sql.Date memberBirth;
	private byte[] memberPhoto;
	private Integer memberMsgAuth;
	private String memberCardNumber;
	private Integer memberCardExpyear;
	private Integer memberCardExpmonth;
	private Timestamp addTime;
	private String bandId;
	public MemberVo() {
		
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberAccount() {
		return memberAccount;
	}
	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNickname() {
		return memberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	public java.sql.Date getMemberBirth() {
		return memberBirth;
	}
	public void setMemberBirth(java.sql.Date memberBirth) {
		this.memberBirth = memberBirth;
	}
	public byte[] getMemberPhoto() {
		return memberPhoto;
	}
	public void setMemberPhoto(byte[] memberPhoto) {
		this.memberPhoto = memberPhoto;
	}
	public Integer getMemberMsgAuth() {
		return memberMsgAuth;
	}
	public void setMemberMsgAuth(Integer memberMsgAuth) {
		this.memberMsgAuth = memberMsgAuth;
	}
	public String getMemberCardNumber() {
		return memberCardNumber;
	}
	public void setMemberCardNumber(String memberCardNumber) {
		this.memberCardNumber = memberCardNumber;
	}
	public Integer getMemberCardExpyear() {
		return memberCardExpyear;
	}
	public void setMemberCardExpyear(Integer memberCardExpyear) {
		this.memberCardExpyear = memberCardExpyear;
	}
	public Integer getMemberCardExpmonth() {
		return memberCardExpmonth;
	}
	public void setMemberCardExpmonth(Integer memberCardExpmonth) {
		this.memberCardExpmonth = memberCardExpmonth;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public String getBandId() {
		return bandId;
	}
	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

}
