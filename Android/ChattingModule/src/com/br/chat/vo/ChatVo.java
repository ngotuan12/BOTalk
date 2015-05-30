package com.br.chat.vo;

import java.io.Serializable;
import java.util.Date;

import com.br.chat.util.WriteFileLog;

public class ChatVo implements Serializable {

	/**
	 * 시리얼라이즈 시 사용되는 버전명으로서 안쓰면 경고 뜸
	 */
	private static final long serialVersionUID = 1L;
	
	public int chatRoomType = 0; // 0이면 1대1, 1이면 단체
	public String chatRoomOwner = ""; // 만든사람 id or seq
	//public String chatRoomMember = ""; // 방의 모든사람
	public String chatRoomSeq = ""; // 방의 seq
	public String[] chatRoomMember = null; // 방의 모든사람
	public Date updateDate ;
	//1.29추가
	public String chatRoomOwnerName = "";//만든사람 이름
	public String chatRoomRevName = "";//받는사람 이름
	public String[] chatRoomMemberName = null;//방에 멤버들 이름
	/*
	 * UI변수
	 */
	public int newMsgCnt = 0;
	public String  recentMessage = "";
	//public String chatRoomName = "";
	public ChatVo(){}
	
	public static String[] getRoomMember(String member){
		try {
			return member.split("_");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	public static String setRoomMember(String[] member){
		try {
			String members = "";
			for(int i = 0 ; i < member.length;i++){
				if(members.length() == 0){
					members = member[i];
				}else{
					members = members + "_" + member[i];
				}
			}
			return members;
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
}
