package com.br.chat.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.adapter.ChatType;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.MessageVo;
import com.chattingmodule.R;
import com.google.android.gms.internal.cl;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChattingDBHeler {

	
	String TAG = "ChattingDBHeler";
	SQLiteDatabase chattingdb = null;
	
	/**
	 * DB쓰기 오픈
	 * @param context
	 * @return
	 * @throws SQLException
	 */
	public SQLiteDatabase openWrite(Context context) throws SQLException {
		try {
			if(chattingdb == null){
				chattingdb = ChattingDB.getWriteDB(context);
				if(chattingdb !=null){
					return chattingdb;
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * DB읽기 오픈
	 * @param context
	 * @return
	 * @throws SQLException
	 */
	public SQLiteDatabase openRead(Context context) throws SQLException {
		try {
			if(chattingdb == null){
				chattingdb = ChattingDB.getReadDB(context);
				if(chattingdb !=null){
					return chattingdb;
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * DB 닫기
	 * @return
	 */
	public boolean Close(){
		try {
			if(chattingdb != null){
				chattingdb.close();
				chattingdb = null;
				return true;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return false;
	}
	
	/**
	 * query 로 실행
	 * @param query
	 * @return
	 */
	public boolean rawQuery(String query){
		try {
			if(chattingdb != null){
				chattingdb.execSQL(query);
				return true;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return false;
	}
	
	/**
	 * 쿼리로 cursor 가져오기
	 * @param query
	 * @return
	 */
	public Cursor rawQuerygetCursur(String query){
		try {
			if(chattingdb != null){
				Cursor cursor = chattingdb.rawQuery(query, null);
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param context
	 */
	public void DeleteMember(Context context){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				chattingdb.execSQL("DELETE FROM member");
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 내정보저장
	 * @param context
	 * @param userseq
	 * @param userid
	 * @param username
	 * @param usernick
	 * @param userphone
	 * @param stmsg
	 */
	public void InsertMyInfo(Context context, int userseq, String userid, String username, String usernick, String userphone, String stmsg){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
			
				ContentValues values = new ContentValues();
				values.put("myseq", userseq);
				values.put("myid", userid);
				values.put("myphone", userphone);
				values.put("myname", username);
				values.put("mynick", usernick);
				values.put("mystmsg", stmsg);
				long result  = chattingdb.insert("myinfo", null, values);
				
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 멤버저장
	 * @param context
	 * @param userseq
	 * @param userid
	 * @param username
	 * @param usernick
	 * @param userhone
	 * @param stmsg
	 * @param photo
	 * @param section
	 * @param header
	 */
	public void InsertMember(Context context,int userseq , String userid, String username,String usernick, String userhone,String stmsg, String photo,int section,boolean header){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				ContentValues values = new ContentValues();
				values.put("userseq", userseq);
				values.put("userid", userid);
				values.put("userphone", userhone);
				values.put("username", username);
				values.put("usernick", usernick);
				values.put("userstmsg", stmsg);
				values.put("userphoto", photo);
				values.put("usersection", section);
				values.put("userheader", header);
				long result  = chattingdb.insert("member", null, values);
				Close();
				/*if(result >0){
					return true;	
				}else{
					return false;
				}*/
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 실패메세지 저장
	 * @param context
	 * @param msgvo
	 */
	public void InsertFailMSg(Context context, MessageVo msgvo){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				ContentValues values = new ContentValues();
				values.put(DBContans.SEND_SEQ, msgvo.getSendseq());
				values.put(DBContans.MEMBER_SEQ, ChatVo.setRoomMember(msgvo.getMemberseq()));
				values.put(DBContans.MESSAGE , msgvo.getMsg());
				values.put(DBContans.SEND_NAME, msgvo.getSendname());
				values.put(DBContans.SEND_TYPE, msgvo.getMsgtype());
				values.put(DBContans.ROOM_SEQ, msgvo.getRoomseq());
				values.put(DBContans.ROOM_TYPE, msgvo.getRoomtype());
				values.put(DBContans.REV_NAME, msgvo.getRevname());
				values.put(DBContans.MEM_NAME, ChatVo.setRoomMember(msgvo.getMembername()));
				values.put(DBContans.REG_DATE, msgvo.getMsgregdate());
				long result  = chattingdb.insert("failmsg", null, values);
			}
			Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 실패메세지 가져오기와서 삭제
	 * @param context
	 * @param msgkey
	 */
	public MessageVo SelectFailMsg(Context context, String msgkey){
		openRead(context);
		MessageVo msgvo = new MessageVo();
		if(chattingdb != null && chattingdb.isOpen()){
			String query = "SELECT * FROM failmsg WHERE msgkey = ?;";
			Cursor cursor = chattingdb.rawQuery(query, new String[]{msgkey});
			if(cursor != null){
				if(cursor.moveToFirst()){
					do{
						
						msgvo.setMsgkey(cursor.getString(0));
						msgvo.setSendseq(cursor.getString(1));
						msgvo.setMemberseq(ChatVo.getRoomMember(cursor.getString(2)));
						msgvo.setMsg(cursor.getString(3));
						msgvo.setSendname(cursor.getString(4));
						msgvo.setMsgtype(Integer.valueOf(cursor.getString(5)));
						msgvo.setRoomseq(cursor.getString(6));
						msgvo.setRoomtype(Integer.valueOf(cursor.getString(7)));
						msgvo.setRevname(cursor.getString(8));
						msgvo.setMembername(ChatVo.getRoomMember(cursor.getString(9)));
						msgvo.setMsgregdate(cursor.getString(10));
						
					}while(cursor.moveToNext());
				}
			}
		}
		Close();
		return msgvo;
	}
	
	/**
	 * 멤버업데이트
	 * @param context
	 * @param vo
	 */
	public void UpdateMember(Context context, MessageVo vo){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "UPDATE member SET username = '" + vo.getSendname() + "' WHERE userseq = " + vo.getSendseq() +";";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 내정보가져오기
	 * @param context
	 * @return
	 */
	public Cursor getMyInfo(Context context){
		try {
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("SELECT * from myinfo ", null);
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * 즐겨찾기 여부 확인
	 * @param context
	 * @param userseq
	 * @return
	 */
	public boolean isFavor(Context context, String userseq){
		try {
			int _count = 0;
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("SELECT COUNT(*) FROM favor WHERE userseq = ? ;",new String[]{userseq});
				if(cursor !=  null){
					if(cursor.moveToFirst()){
						_count = cursor.getInt(0);
					}
				}
				Close();
				if(_count > 0){
					return true;
				}else{
					return false;
				}
			}
			Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	/**
	 * 즐겨찾기 추가
	 * @param context
	 * @param userseq
	 */
	public void addFavor(Context context,String userseq){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				ContentValues values = new ContentValues();
				values.put(DBContans.Favor_seq, userseq);
				long result  = chattingdb.insert("favor", null, values);
			}
			Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 즐겨찾기 삭제
	 * @param context
	 * @param userseq
	 */
	public void	 deleteFavor(Context context, String userseq){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "DELETE FROM favor WHERE " + DBContans.Favor_seq + " = '" + userseq + "';";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 즐겨찾기 리스트
	 * @param context
	 * @return
	 */
	public Cursor getFavor(Context context){
		try {
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("SELECT * from favor ", null);
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * 멤버리스트정보
	 * @param context
	 * @return
	 */
	public Cursor getMember(Context context){
		try {
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("SELECT * from member ORDER BY (CASE WHEN substr(username ,0, 1) BETWEEN 'ㄱ' AND '힣' THEN 1 WHEN substr(username, 0, 1) BETWEEN 'A' AND 'Z' THEN 2 " +
						" WHEN substr(username, 0, 1) BETWEEN 'a' AND 'z' THEN 2  ELSE 3   END), " +
						"username COLLATE LOCALIZED ASC", null);
				
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 멤버 이미지를 변경한다.
	 * @param context
	 * @param vo
	 */
	public void UpdateMemberImg(Context context , MessageVo vo){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "UPDATE member SET userphoto = '" + vo.getMsg() + "' WHERE userseq = " + vo.getSendseq() +";";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	/**
	 * 멤버 상태메세지를 변경한다.
	 * @param context
	 * @param vo
	 */
	public void UpdateMemberStMsg(Context context, MessageVo vo){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "UPDATE member SET userstmsg = '" + vo.getMsg() + "' WHERE userseq = " + vo.getSendseq() +";";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 멤버 상태메세지를 변경한다.
	 * @param context
	 * @param vo
	 */
	public void UpdateMyStMsg(Context context, MessageVo vo){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "UPDATE myinfo SET mystmsg = '" + vo.getMsg() + "' WHERE myseq = " + vo.getSendseq() +";";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 선택한 유저와 채팅방유무
	 * @param context
	 * @param seq
	 * @return
	 */
	public boolean IsRoom(Context context, String seq){
		try {
			int _count = 0;
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				//Cursor cursor = chattingdb.rawQuery("SELECT COUNT(*) FROM room WHERE roomseq = ? and roomtype = ? ;",new String[]{seq,type});
				Cursor cursor = chattingdb.rawQuery("SELECT COUNT(*) FROM room WHERE roomseq = ? ;",new String[]{seq});
				if(cursor !=  null){
					if(cursor.moveToFirst()){
						_count = cursor.getInt(0);
					}
				}
				Close();
				if(_count > 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return false;
	}
	
	/**
	 * 1:1채팅방을 만든다
	 * @param context
	 * @param owner
	 * @param member
	 * @param type
	 * @param cnt
	 * @param newmsg
	 */
	public void CreateRoom(Context context,String roomseq, String owner, String[] member,/*String receiver,*/ int type,  String newmsg,String roomtitle ,String[] membername){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				ContentValues values = new ContentValues();
				values.put(DBContans.Room_Seq, roomseq);
				values.put(DBContans.Room_Owner, owner);
				values.put(DBContans.Room_Member, ChatVo.setRoomMember(member));
				//values.put(DBContans.Room_Receiver, receiver);
				values.put(DBContans.Room_Type, type);
				//values.put(DBContans.Room_MsgCnt, cnt);
				values.put(DBContans.Room_NewMsg, newmsg);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
				Date date = new Date();
				values.put(DBContans.Room_UpdateDate, dateFormat.format(date));
				values.put(DBContans.Room_Regdate, dateFormat.format(date));
				values.put(DBContans.Room_Title, roomtitle);
				values.put(DBContans.Room_MemberName, ChatVo.setRoomMember(membername));
				/*values.put(DBContans.Room_UpdateDate, String.valueOf(System.currentTimeMillis()));
				values.put(DBContans.Room_Regdate, String.valueOf(System.currentTimeMillis()));*/
				long result = chattingdb.insert("room", null, values);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 메세지를 전송 저장
	 * @param context
	 * @param msgvo
	 */
	public void InsertMessage(Context context, MessageVo msgvo,boolean isMy){
		try {
			
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				
				ContentValues values = new ContentValues();
				values.put(DBContans.Msg_Key, msgvo.getMsgkey());
				if(isMy){//내가보낸메세지라면 방번호는 받는사람
					values.put(DBContans.Room_Seq, msgvo.getRoomseq());
				}else{ // 남이보낸메세지라면 방번호는  보낸사람 이지만, 단체라면 룸시퀀스
					String query = "";
					if(msgvo.getRoomtype() == ChatGlobal.chatTypeGroup){
						values.put(DBContans.Room_Seq, msgvo.getRoomseq());
						query = "UPDATE msg SET msgsendname = '" + msgvo.getSendname() + 
								"' WHERE " +
								"msgsender = '" + msgvo.getSendseq() + 
								"' and roomseq = '" + msgvo.getRoomseq() + "';";
					}else if(msgvo.getRoomtype() == ChatGlobal.chatTypeSingle){
						values.put(DBContans.Room_Seq, msgvo.getSendseq());
						query = "UPDATE msg SET msgsendname = '" + msgvo.getSendname() + 
								"' WHERE " +
								"msgsender = '" + msgvo.getSendseq() + 
								"' and roomseq = '" + msgvo.getSendseq() + "';";
					}
						
					chattingdb.execSQL(query);
				}
				values.put(DBContans.Msg_Type, msgvo.getMsgtype());
				values.put(DBContans.Msg_Content, msgvo.getMsg());
				//values.put(DBContans.Msg_Receiver, msgvo.getRecevseq());
				values.put(DBContans.Msg_Sender, msgvo.getSendseq());
			//	values.put(DBContans.Msg_Regdate, msgvo.getMsgregdate());
				//String member = ChatVo.setRoomMember(msgvo.getMemberseq());
				//values.put(DBContans.Msg_Member, member);
				values.put(DBContans.Msg_SenderName, msgvo.getSendname());
				values.put(DBContans.Msg_Status, msgvo.getStatus());
				values.put(DBContans.Msg_Regdate, msgvo.getMsgregdate());
				long result = chattingdb.insert("msg", null, values);
				  // ImageLoader.getInstance().clearMemoryCache();
			      //  ImageLoader.getInstance().clearDiskCache();
			}
			Close();
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
/*	public void UpdateMessage(Context context, MessageVo msgvo, boolean isMy){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "UPDATE msg SET msgstatus = '" + msgvo.getStatus() + 
						"' , roomupdate = '" + msgvo.getMsgregdate() + 
						"' WHERE " +
						"roomseq = " + msgvo.getRoomseq() + ";";	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	*/
	/**
	 * 메세지 멤버를 저장한다.
	 * @param context
	 * @param roomseq
	 * @param roomtype
	 * @param revseq
	 * @param regdate
	 */
	
	public void InsertMsgMember(Context context,String roomseq, String msgkey, String[] member, String regdate){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				for(int i = 0 ; i < member.length; i++){
					ContentValues values = new ContentValues();
					values.put(DBContans.Msg_Key, msgkey);
					values.put(DBContans.Room_Seq, roomseq);
					//values.put(DBContans.Room_Type, msgvo.getro);
					values.put(DBContans.Msg_Revseq, member[i]);
					values.put(DBContans.Msg_Regdate, regdate);	
					long result = chattingdb.insert("msgmem", null, values);
					Log.e("", ""+result);
				}
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 메세지 멤버를 삭제한다
	 * @param context
	 * @param roomseq
	 * @param roomtype
	 * @param revseq
	 * @param regdate
	 */
	
	public void DeleteMsgMember(Context context, String roomseq, String readseq,String regdate ){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				
				String query = "DELETE FROM msgmem WHERE roomseq = '"+roomseq +"' and memrevseq = '" +readseq +"' and msgregdate <= '" + regdate + "';";
				chattingdb.execSQL(query);
			/*		ContentValues values = new ContentValues();
					values.put(DBContans.Room_Seq, roomseq);
					values.put(DBContans.Room_Type, roomtype);
					values.put(DBContans.Msg_Revseq, readseq);
					values.put(DBContans.Msg_Regdate, regdate);*/
				/*	String where = "roomseq = ? and memrevseq = ? and DATETIME(msgregdate) = DATETIME(?)";
					chattingdb.delete("msgmem",where, new String[]{roomseq,readseq,regdate});*/
					//long result = chattingdb.insert("msgmem", null, values);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 방정보 업데이트
	 * @param context
	 * @param msgvo
	 * @param isMy
	 */
	public void UpdateRoom(Context context,MessageVo msgvo,boolean isMy){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "";
				if(isMy){//내가보낸메세지라면 방번호는 받는사람
					if(msgvo.getRoomtype() == ChatGlobal.chatTypeGroup){//그룹메세지
						if(msgvo.getMsgtype() == ChatType.add){
							query = "UPDATE room SET roomnewmsg = '" + getAddMsg(context, msgvo.getSendseq(), msgvo.getMsg()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() + 
									"', roommember = '" + ChatVo.setRoomMember(msgvo.getMemberseq()) +
									"', roommemname = '" + ChatVo.setRoomMember(msgvo.getMembername()) +
									/*"', roomtitle = '"+ msgvo.getSendname() +*/
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";
						}else if(msgvo.getMsgtype() == ChatType.leave){
							query = "UPDATE room SET roomnewmsg = '" + getleaveMsg(context, msgvo.getSendname()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									"' , roommember = '" + ChatVo.setRoomMember(msgvo.getMemberseq()) +
									"', roommemname = '" + ChatVo.setRoomMember(msgvo.getMembername()) +
									/*"', roomtitle = '"+ msgvo.getSendname() +*/
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";	
						}else{
							query = "UPDATE room SET roomnewmsg = '" + msgvo.getMsg() +
									"' , roomupdate = '" + msgvo.getMsgregdate() + 
									/*"', roomtitle = '"+ msgvo.getSendname() +*/
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";
						}
					}if(msgvo.getRoomtype() == ChatGlobal.chatTypeSingle){
						if(msgvo.getMsgtype() == ChatType.leave){
							query = "UPDATE room SET roomnewmsg = '" + getleaveMsg(context, msgvo.getSendseq()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									/*"', roomtitle = '"+ msgvo.getSendname() +*/
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";
						}else{
							query = "UPDATE room SET roomnewmsg = '" + msgvo.getMsg() + 
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									/*"', roomtitle = '"+ msgvo.getSendname() +*/
									"' WHERE " +
									"roomseq = '" + msgvo.getRoomseq() + "';";	
						}
					}
				
				}else{ // 남이보낸메세지라면 방번호는  보낸사람
					if(msgvo.getRoomtype() == ChatGlobal.chatTypeGroup){
						if(msgvo.getMsgtype() == ChatType.add){
							query = "UPDATE room SET roomnewmsg = '" + getAddMsg(context, msgvo.getSendname(), msgvo.getMsg()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() + 
									"', roommember = '" + ChatVo.setRoomMember(msgvo.getMemberseq()) +
									"', roommemname = '" + ChatVo.setRoomMember(msgvo.getMembername()) +
									"', roomtitle = '"+ msgvo.getSendname() +
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";
						}else if(msgvo.getMsgtype() == ChatType.leave){
							query = "UPDATE room SET roomnewmsg = '" + getleaveMsg(context, msgvo.getSendname()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									"' , roommember = '" + ChatVo.setRoomMember(msgvo.getMemberseq()) +
									"', roommemname = '" + ChatVo.setRoomMember(msgvo.getMembername()) +
									"', roomtitle = '"+ msgvo.getSendname() +
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";	
						}else{
							query = "UPDATE room SET roomnewmsg = '" + msgvo.getMsg() +
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									"', roomtitle = '"+ msgvo.getSendname() +
									"' WHERE " + "roomseq = '" + msgvo.getRoomseq() + "';";
						}
						
					}else if(msgvo.getRoomtype() == ChatGlobal.chatTypeSingle){
						if(msgvo.getMsgtype() == ChatType.leave){
							query = "UPDATE room SET roomnewmsg = '" + getleaveMsg(context, msgvo.getSendname()) +
									"' , roomupdate = '" + msgvo.getMsgregdate() + 
									"', roomtitle = '"+ msgvo.getSendname() +
									"' WHERE " + "roomseq = '" + msgvo.getSendseq() + "';";
						}else{
							query = "UPDATE room SET roomnewmsg = '" + msgvo.getMsg() +
									"' , roomupdate = '" + msgvo.getMsgregdate() +
									"', roomtitle = '"+ msgvo.getSendname() +
									"' WHERE " + "roomseq = '" + msgvo.getSendseq() + "';";	
						}
					}
				}
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public String getleaveMsg(Context mContext,String sendname){
		 return String.format(mContext.getString(R.string.leftMessage), sendname);
	}
	
	public String getAddMsg(Context mContext,String sendname,String msg){
		String[] split = msg.split(",");
		
		return String.format(mContext.getString(R.string.invitationMessage), sendname,getAddMessage(split[1]));
	}
	
	private String getAddMessage(String msg){
		
		String addmsg = "";
		String[] members = msg.split("_");
		for(int i = 0 ; i < members.length; i++){
			if(addmsg.length() == 0){
				addmsg = members[i];	
			}else {
				addmsg = addmsg + "," +members[i];
			}
		}
		return addmsg;	
	}
	
	private String getName(String msg){
		try {
			String[] addmember = ChatVo.getRoomMember(msg);
			String addmsg = "";
			for(int i = 0 ; i < addmember.length; i++){
				if(addmsg.length() == 0){
					addmsg = ((MemberVo)ChattingApplication.getInstance().memberMap.get(addmember[i])).getUsername();	
				}else {
					addmsg = addmsg + "," + ((MemberVo)ChattingApplication.getInstance().memberMap.get(addmember[i])).getUsername();
				}
			}
			return addmsg;	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	/**
	 * 재전송 or 메세지 단일삭제
	 * @param context
	 * @param msgkey
	 */
	public void DeleteSendMessage(Context context, String msgkey){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "DELETE FROM msg WHERE msgkey = '"+msgkey +"';";
				chattingdb.execSQL(query);
				String query2 = "DELETE FROM msgmem WHERE msgkey = '" +msgkey +"';";
				chattingdb.execSQL(query2);
			}
			Close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 방리스트를 가져온다
	 * @param context
	 * @return
	 */
	public Cursor getChatRoomList(Context context,String sendseq){
		try {
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("SELECT a.roomseq ,a.roomowner , a.roommember, a.roomupdate, a.roomtype , " +
						" a.roomnewmsg , a.roomregdate , a.roomtitle, a.roommemname ,(select count(*) from msgmem b where a.roomseq = b.roomseq and b.memrevseq = ? group by b.roomseq) as cnt FROM room a order by a.roomupdate desc;",new String[]{sendseq});
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * 메세지 가져오기
	 * @param context
	 * @param seq
	 * @return
	 */
	public Cursor getChatMessage(Context context,String roomseq,int page){
		try {//a.roomrtype = b.roomrtype and
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				Cursor cursor = chattingdb.rawQuery("select a.msgkey,a.roomseq, a.msgtype, a.msgcontent, a.msgsender ,a.msgregdate,a.msgstatus,a.msgsendname," +
						"(select count(*) from msgmem b where a.roomseq = b.roomseq and  a.msgkey= b.msgkey group by b.msgkey) as cnt " +
						"from msg a where a.roomseq = ? order by a.msgregdate asc limit "+(ChatGlobal.ChatSelection*page)+" offset ((select count(*) from msg where roomseq = ?) - " + (ChatGlobal.ChatSelection*page) +");"
						,new String[]{roomseq,roomseq});
				/*Cursor cursor = chattingdb.rawQuery("select a.msgkey,a.roomseq, a.msgtype, a.msgcontent, a.msgsender ,a.msgregdate,a.msgstatus," +
						"(select count(*) from msgmem b where a.roomseq = b.roomseq and  a.msgkey= b.msgkey group by b.msgkey) as cnt " +
						"from msg a where a.roomseq = ? order by a.msgregdate asc limit "+(20*page)+" offset " + (20*page)  +";"
						,new String[]{roomseq});*/
			/*	Cursor cursor = chattingdb.rawQuery("SELECT roomseq ,msgtype,msgcontent,msgsender,msgregdate, msgstatus, " +
						"FROM msg WHERE roomseq = ? order by msgregdate asc ;",new String[]{seq});*/
				return cursor;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	public int getStatusMessage(Context context, String msgkey){
		 int status = -1;
		try {
			openRead(context);
			if(chattingdb != null && chattingdb.isOpen()){
				
				Cursor cursor = chattingdb.rawQuery("SELECT msgstatus FROM msg WHERE msgkey = ? ;" , new String[]{msgkey});
				if(cursor !=  null){
					if(cursor.moveToFirst()){
						status = cursor.getInt(0);
					}
				}
				Close();	
				return status;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return status;
	}
	
	public void UpdateMessage(Context context, String msgkey, int status){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "";
				query = "UPDATE msg SET msgstatus = " + status +" WHERE msgkey = '" + msgkey +"';" ;
				chattingdb.execSQL(query);
			}
			Close();
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	

	/**
	 * 대화방 나가기
	 * @param context
	 * @param roomseq
	 * @param sendseq
	 */
	public void DeleteRoom(Context context, String roomseq, String sendseq){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "DELETE FROM room WHERE roomseq = '"+roomseq +"';";
				chattingdb.execSQL(query);
			}
			Close();
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 대화방내 메세지 전부 지우기
	 * @param context
	 * @param roomseq
	 */
	public void DeleteMessage(Context context, String roomseq){
		try {
			openWrite(context);
			if(chattingdb != null && chattingdb.isOpen()){
				String query = "DELETE FROM msg WHERE roomseq = '"+roomseq +"';";
				chattingdb.execSQL(query);
			}
			Close();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
}
