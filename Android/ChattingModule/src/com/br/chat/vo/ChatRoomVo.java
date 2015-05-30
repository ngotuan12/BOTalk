/**
 * 채팅방정보 VO
 */
package com.br.chat.vo;

import java.util.Comparator;

import android.database.Cursor;

import com.br.chat.ChattingApplication;
import com.br.chat.util.WriteFileLog;

public class ChatRoomVo {

	public String Room_Seq; // 방번호 
	public String Room_Owner; // 방만든 사람
	public String[] Room_Member; //방 멤버(본인포함)
	public String Room_UpdateDate; //방업데이트 시간
	public int Room_Type; // 방타입
	public int Room_MsgCnt; // 방밓
	public String  Room_NewMsg; // 방 최근 메세지
	public long Room_Regdate; // 방 만든시간
	public String Room_Title;
	public String[] Room_MemberName;
	
	
	
	//public String Room_Receiver;
	
public String[] getRoom_MemberName() {
		return Room_MemberName;
	}
	public void setRoom_MemberName(String[] room_MemberName) {
		Room_MemberName = room_MemberName;
	}
public String getRoom_Title() {
		return Room_Title;
	}
	public void setRoom_Title(String room_Title) {
		Room_Title = room_Title;
	}
	/*	public String getRoom_Receiver() {
		return Room_Receiver;
	}
	public void setRoom_Receiver(String room_Receiver) {
		Room_Receiver = room_Receiver;
	}*/
	public String getRoom_Seq() {
		return Room_Seq;
	}
	public void setRoom_Seq(String room_Seq) {
		Room_Seq = room_Seq;
	}
	public String getRoom_Owner() {
		return Room_Owner;
	}
	public void setRoom_Owner(String room_Owner) {
		Room_Owner = room_Owner;
	}
	/*public String getRoom_Member() {
		return Room_Member;
	}
	public void setRoom_Member(String room_Member) {
		Room_Member = room_Member;
	}*/
	public String getRoom_UpdateDate() {
		return Room_UpdateDate;
	}
	public void setRoom_UpdateDate(String room_UpdateDate) {
		Room_UpdateDate = room_UpdateDate;
	}
	public int getRoom_Type() {
		return Room_Type;
	}
	public void setRoom_Type(int room_Type) {
		Room_Type = room_Type;
	}
	public int getRoom_MsgCnt() {
		return Room_MsgCnt;
	}
	public void setRoom_MsgCnt(int room_MsgCnt) {
		Room_MsgCnt = room_MsgCnt;
	}
	public String getRoom_NewMsg() {
		return Room_NewMsg;
	}
	public void setRoom_NewMsg(String room_NewMsg) {
		Room_NewMsg = room_NewMsg;
	}
	public long getRoom_Regdate() {
		return Room_Regdate;
	}
	public void setRoom_Regdate(long room_Regdate) {
		Room_Regdate = room_Regdate;
	}
	
	public String[] getRoom_Member() {
		return Room_Member;
	}
	public void setRoom_Member(String[] room_Member) {
		Room_Member = room_Member;
	}
	public static void ParseCusor(Cursor cursor){
		try {
			if(cursor != null){
				ChattingApplication.getInstance().room.clear();
				if(cursor.moveToFirst()){
					do{
						ChatRoomVo vo = new ChatRoomVo();
						vo.setRoom_Seq(cursor.getString(0));
						vo.setRoom_Owner(cursor.getString(1));
						String[] members = ChatVo.getRoomMember(cursor.getString(2));
						vo.setRoom_Member(members);
						vo.setRoom_UpdateDate(cursor.getString(3));
						vo.setRoom_Type(cursor.getInt(4));
						vo.setRoom_NewMsg(cursor.getString(5));
						vo.setRoom_Regdate(cursor.getLong(6));
						
						vo.setRoom_Title(cursor.getString(7));
						String[] membersName = ChatVo.getRoomMember(cursor.getString(8));
						vo.setRoom_MemberName(membersName);
						vo.setRoom_MsgCnt(cursor.getInt(9));
						/*vo.setRoom_MsgCnt(cursor.getInt(6));
						vo.setRoom_Receiver(cursor.getString(3));*/
						
						ChattingApplication.getInstance().room.put(vo.getRoom_Seq(), vo);
					}while(cursor.moveToNext());	
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 이름 내림차순
	 * @author falbb
	 *
	 */
	public static class  NameDescCompare implements Comparator<ChatRoomVo> {

		/**
		 * 내림차순(DESC)
		 */
		@Override
		public int compare(ChatRoomVo arg0, ChatRoomVo arg1) {
			// TODO Auto-generated method stub
			return arg1.getRoom_UpdateDate().compareTo(arg0.getRoom_UpdateDate());
		}

	}
}
