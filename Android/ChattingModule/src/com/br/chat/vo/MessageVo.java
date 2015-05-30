/**
 * 메세지 VO 클래스
 */
package com.br.chat.vo;

import java.io.Serializable;
import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.br.chat.util.WriteFileLog;

public class MessageVo implements Serializable{

	/**
	 * 시리얼라이즈 시 사용되는 버전명으로서 안쓰면 경고 뜸
	 */
	private static final long serialVersionUID = 1L;
	
	public String msgkey; //메세지 키
	public String[] memberseq; // 
	//public String recevseq;
	public String sendseq; //보낸사람 키
	public String msg; //메세지
	public String sendname; //보낸사람 닉네임
	public int msgtype; // 메세지 타입
	public String msgregdate; // 메세지 타임
	public String roomseq ; // 방 번호
	public int roomtype ;
	//ui 
	public int status = 0; // 메세지 상태
	public int cnt = 0; // 메세지 안읽은 숫자
	
	//1.29 추가
	public String revname = "";
	public String[] membername ;
	public MessageVo(){}
	
	public String[] getMembername() {
		return membername;
	}
	public void setMembername(String[] membername) {
		this.membername = membername;
	}
	public String getRevname() {
		return revname;
	}
	public void setRevname(String revname) {
		this.revname = revname;
	}
	public int getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(int roomtype) {
		this.roomtype = roomtype;
	}
	public String getMsgkey() {
		return msgkey;
	}
	public void setMsgkey(String msgkey) {
		this.msgkey = msgkey;
	}
	public String[] getMemberseq() {
		return memberseq;
	}
	public void setMemberseq(String[] memberseq) {
		this.memberseq = memberseq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
/*	public String getRecevseq() {
		return recevseq;
	}
	public void setRecevseq(String recevseq) {
		this.recevseq = recevseq;
	}*/
	public String getSendseq() {
		return sendseq;
	}
	public void setSendseq(String sendseq) {
		this.sendseq = sendseq;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getMsgregdate() {
		return msgregdate;
	}
	public void setMsgregdate(String msgregdate) {
		this.msgregdate = msgregdate;
	}

	public int getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}
	public String getRoomseq() {
		return roomseq;
	}
	public void setRoomseq(String roomseq) {
		this.roomseq = roomseq;
	}
	
	
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public static void ParseCusor(Cursor cursor, ArrayList<MessageVo> arraylist){
		try {
			if(cursor != null){
				arraylist.clear();
				//int position = 0; 
				ArrayList<MessageVo>nArray = new ArrayList<MessageVo>();
				if(cursor.moveToFirst()){
					do{
						MessageVo vo = new MessageVo();
						vo.setMsgkey(cursor.getString(0));
						vo.setRoomseq(cursor.getString(1));
						vo.setMsgtype(cursor.getInt(2));
						vo.setMsg(cursor.getString(3));
						vo.setSendseq(cursor.getString(4));
						vo.setMsgregdate(cursor.getString(5));
						vo.setStatus(cursor.getInt(6));
						vo.setSendname(cursor.getString(7));
						//vo.setRoomtype(cursor.getInt(8));
						
						vo.setCnt(cursor.getInt(8));
						//vo.setRoomtype(cursor.getInt(8));
						/*vo.setMemberseq(ChatVo.getRoomMember(cursor.getString(3)));
						vo.setRecevseq(cursor.getString(5));*/
						//nArray.add(vo);
						//arraylist.add(position,vo);
						arraylist.add(vo);
						//position++;
					//	ChattingApplication.getInstance().room.put(vo.getRoom_Seq(), vo);
					}while(cursor.moveToNext());	
				}
				Log.e("", "" + arraylist.size());
				/*for(int i = 0 ; i < nArray.size(); i++){
					arraylist.add(i,nArray.get(i));
				}*/
				
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
}

