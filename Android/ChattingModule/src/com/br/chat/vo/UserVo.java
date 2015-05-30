package com.br.chat.vo;

import java.io.Serializable;

import com.br.chat.listview.ContactItemInterface;

public class UserVo implements Serializable, ContactItemInterface{

	/**
	 * 시리얼라이즈 시 사용되는 버전명으로서 안쓰면 경고 뜸
	 */
	private static final long serialVersionUID = 1L;
	
	public String UserId = "";
	public String UserName = "";
	public String UserNick = "";
	public String UserPhone = "";
	public String UserStMSg = "";
	public String UserPhoto = "";
	public String UserSeq = "";
	

	@Override
	public String getItemForIndex() {
		// TODO Auto-generated method stub
		return UserName;
	}
	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return String.valueOf(UserSeq);
		
	}
}
