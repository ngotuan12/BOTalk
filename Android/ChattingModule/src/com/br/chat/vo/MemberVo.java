package com.br.chat.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.database.Cursor;

import com.br.chat.ChattingApplication;
import com.br.chat.listview.ContactItemInterface;
import com.br.chat.listview.SoundSearcher;
import com.br.chat.service.ChattingService;
import com.br.chat.util.WriteFileLog;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

public class MemberVo implements ContactItemInterface,Serializable,Cloneable{
	 
	/**
	 * 시리얼라이즈 시 사용되는 버전명으로서 안쓰면 경고 뜸
	 */
	private static final long serialVersionUID = 1L;
	
	public String userId = "";
	public String username = "";
	public String regDate = "";
	public int userseq = 0;
	public String userNick = "";
	public String userPhone = "";
	public String userPhoto = "";
	public String userStmsg = "";
	public int userSection = -1;
	
	public boolean header = false;
	
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getSeq() {
		return userseq;
	}
	public void setSeq(int seq) {
		this.userseq = seq;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getUserStmsg() {
		return userStmsg;
	}
	public void setUserStmsg(String userStmsg) {
		this.userStmsg = userStmsg;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public boolean isHeader() {
		return header;
	}
	public void setHeader(boolean header) {
		this.header = header;
	}
	public int getUserSection() {
		return userSection;
	}
	public void setUserSection(int userSection) {
		this.userSection = userSection;
	}
	
	@Override
	public String getItemForIndex() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return String.valueOf(userseq);
		
	}
	
	
	/**
	 * 이름 내림차순
	 * @author falbb
	 *
	 */
	public static class  NameDescCompare implements Comparator<MemberVo> {
		/**
		 * 내림차순(DESC)
		 */
		@Override
		public int compare(MemberVo arg0, MemberVo arg1) {
			// TODO Auto-generated method stub
			
			 return Character.toString(SoundSearcher.getInitialSound(arg0.getUsername().charAt(0))).compareTo(Character.toString(SoundSearcher.getInitialSound(arg1.getUsername().charAt(0))));
			//return arg0.getUsername().compareTo(arg1.getUsername());
		}

	}
	
	public static ArrayList<MemberVo> PasingJson(String json,String myinfo,ArrayList<MemberVo> arraylist){
		try {
			JSONArray data = null;
			int header = 0;
			JSONObject obj2 = new JSONObject(myinfo);
			if(obj2 != null){
				MemberVo dto = new MemberVo();
				dto.setUserId(obj2.getString("user_id"));
				dto.setUsername(obj2.getString("user_name"));
				//dto.setUserNick(obj2.getString("userNick"));
				dto.setSeq(obj2.getInt("user_seq"));
				dto.setUserPhone(obj2.getString("user_phone"));
				dto.setUserPhoto(obj2.getString("user_photo"));
				dto.setUserStmsg(obj2.getString("user_stmsg"));
				dto.setUserSection(0);
				dto.setHeader(true);
			//	memberMap.put(String.valueOf(obj2.getInt("seq")), dto);
				arraylist.add(dto);
			}
			data =	new JSONArray(json.toString());
			
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
						JSONObject obj = data.getJSONObject(i);
						MemberVo dto = new MemberVo();
						dto.setUserId(obj.getString("user_id"));
						dto.setUsername(obj.getString("user_name"));
						//dto.setUserNick(obj.getString("userNick"));
						dto.setSeq(obj.getInt("user_seq"));
						dto.setUserPhone(obj.getString("user_phone"));
						dto.setUserPhoto(obj.getString("user_photo"));
						dto.setUserStmsg(obj.getString("user_stmsg"));
						
					/*	if(obj.getString("userId").equals("test")){
							dto.setUserSection(0);
							dto.setHeader(true);
							
						}else{*/
							if(header == 0){
								dto.setUserSection(1);
								dto.setHeader(true);
								header = 1;
							}else{
								dto.setUserSection(1);
								dto.setHeader(false);
							}
						//}
						//memberMap.put(String.valueOf(obj.getInt("seq")), dto);
						arraylist.add(dto);
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		return arraylist;
	}
	
	public static void PasingCursorFavor(Cursor cursor){
		if(cursor != null){
			ChattingApplication.getInstance().favorArray.clear();
			if(cursor.moveToFirst()){
				do{
					try {
						MemberVo dtoa = ChattingApplication.getInstance().memberMap.get(String.valueOf(cursor.getInt(0)));
						MemberVo dto = (MemberVo)dtoa.clone();
						dto.setUserSection(1);
						//ChattingApplication.getInstance().memberMap.put(String.valueOf(dto.getSeq()), dto);
						ChattingApplication.getInstance().favorArray.add(dto);	
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}while(cursor.moveToNext());
			}
		}
	}
	public static void PasingCursorMyinfo(Cursor cursor){
		try {
			if(cursor != null){
				ChattingApplication.getInstance().memberArray.clear();
				if(cursor.moveToFirst()){
					MemberVo dto = new MemberVo();
					dto.setSeq(cursor.getInt(0));
					dto.setUserId(cursor.getString(1));
					dto.setUsername(cursor.getString(2));
					dto.setUserNick(cursor.getString(3));
					dto.setUserPhone(cursor.getString(4));
					dto.setUserStmsg(cursor.getString(5));
					dto.setUserSection(0);
					ChattingApplication.getInstance().memberMap.put(String.valueOf(dto.getSeq()), dto);
					//ChattingApplication.getInstance().memberArray.add(dto);
					ChattingApplication.getInstance().myinfo = dto;
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void PasingCursor(Cursor cursor){
		try {
			if(cursor != null){
				ChattingApplication.getInstance().memberArray.clear();
				if(cursor.moveToFirst()){
					do{
						MemberVo dto = new MemberVo();
						dto.setSeq(cursor.getInt(0));
						dto.setUserId(cursor.getString(1));
						dto.setUsername(cursor.getString(2));
						dto.setUserNick(cursor.getString(3));
						dto.setUserPhone(cursor.getString(4));
						dto.setUserStmsg(cursor.getString(5));
						dto.setUserPhoto(cursor.getString(6));
						dto.setUserSection(cursor.getInt(7));
					/*	if(cursor.getString(8).startsWith("1")){
							dto.setHeader(true);
						}else{
							dto.setHeader(false);	
						}*/
						ChattingApplication.getInstance().memberMap.put(String.valueOf(dto.getSeq()), dto);
						ChattingApplication.getInstance().memberArray.add(dto);
					}while(cursor.moveToNext());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void PasingUser(String json){
		try {
			JSONObject obj = new JSONObject(json);
			if(obj != null){
						
						MemberVo dto = new MemberVo();
						dto.setUserId(obj.getString("user_id"));
						dto.setUsername(obj.getString("user_name"));
						//dto.setUserNick(obj.getString("userNick"));
						dto.setSeq(obj.getInt("user_seq"));
						dto.setUserPhone(obj.getString("user_phone"));
						dto.setUserPhoto(obj.getString("user_photo"));
						dto.setUserStmsg(obj.getString("user_stmsg"));
						dto.setUserSection(2);
						dto.setHeader(false);
						ChattingService.getInstance().InserMember(dto);
						ChattingApplication.getInstance().memberMap.put(String.valueOf(dto.getSeq()), dto);
						//ChattingApplication.getInstance().memberArray.add(dto);
							//ChattingApplication.getInstance().memberMap.put(String.valueOf(obj.getInt("seq")), dto);
							//ChattingApplication.getInstance().memberArray.add(dto);
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void PasingJson(JSONArray friends,JSONObject infor){
		try {
				
			    MemberVo user = new MemberVo();
				user.setUserId(infor.getString("user_id"));
				user.setUsername(infor.getString("user_name"));
				//dto.setUserNick(obj2.getString("userNick"));
				int userSeq = infor.getInt("user_seq");
				user.setSeq(infor.getInt("user_seq"));
				user.setUserPhone(infor.getString("user_phone"));
				user.setUserPhoto(infor.getString("user_photo"));
				user.setUserStmsg(infor.getString("user_stmsg"));
				user.setUserSection(0);
				ChattingService.getInstance().InsertMyInfo(user);
				
				
				
			if (friends != null) {
				for (int i = 0; i < friends.length(); i++) {
						JSONObject obj = friends.getJSONObject(i);
						MemberVo friend = new MemberVo();
						
						friend.setUserId(obj.getString("user_id"));
						friend.setUsername(obj.getString("user_name"));
						//dto.setUserNick(obj.getString("userNick"));
						if (obj.getInt("user_seq") == userSeq) continue;
						friend.setSeq(obj.getInt("user_seq"));
						friend.setUserPhone(obj.getString("user_phone"));
						friend.setUserPhoto(obj.getString("user_photo"));
						friend.setUserStmsg(obj.getString("user_stmsg"));
						friend.setUserSection(2);
						ChattingService.getInstance().InserMember(friend);
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public static void RealTimePasingJson(JSONArray data){
		try {
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
						JSONObject obj = data.getJSONObject(i);
						MemberVo dto = new MemberVo();
						if(ChattingApplication.getInstance().memberMap.get(String.valueOf(obj.getInt("user_seq"))) == null){
							dto.setUserId(obj.getString("user_id"));
							dto.setUsername(obj.getString("user_name"));
							//dto.setUserNick(obj.getString("userNick"));
							String userSeq =SharedManager.getInstance().getString(ChattingApplication.getInstance(), BaseGlobal.User_Seq);
							if (obj.getInt("user_seq") == Integer.parseInt(userSeq)) continue;
							dto.setSeq(obj.getInt("user_seq"));
							dto.setUserPhone(obj.getString("user_phone"));
							dto.setUserPhoto(obj.getString("user_photo"));
							dto.setUserStmsg(obj.getString("user_stmsg"));
							dto.setUserSection(2);
							ChattingService.getInstance().InserMember(dto);	
						}
						
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/*public static void PasingJson(String json,String myinfo){
		try {
			JSONArray data = null;
			int header = 0;
			JSONObject obj2 = new JSONObject(myinfo);
			if(obj2 != null){
				MemberVo dto = new MemberVo();
				dto.setUserId(obj2.getString("user_id"));
				dto.setUsername(obj2.getString("user_name"));
				//dto.setUserNick(obj2.getString("userNick"));
				dto.setSeq(obj2.getInt("user_seq"));
				dto.setUserPhone(obj2.getString("user_phone"));
				dto.setUserPhoto(obj2.getString("user_photo"));
				dto.setUserStmsg(obj2.getString("user_stmsg"));
				dto.setUserSection(0);
				dto.setHeader(true);
				
				ChattingService.getInstance().InserMember(dto);
				
				//ChattingApplication.getInstance().memberMap.put(String.valueOf(obj2.getInt("seq")), dto);
				//ChattingApplication.getInstance().memberArray.add(dto);
			}
			data =	new JSONArray(json.toString());
			
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
						JSONObject obj = data.getJSONObject(i);
						
						MemberVo dto = new MemberVo();
						dto.setUserId(obj.getString("user_id"));
						dto.setUsername(obj.getString("user_name"));
						//dto.setUserNick(obj.getString("userNick"));
						dto.setSeq(obj.getInt("user_seq"));
						dto.setUserPhone(obj.getString("user_phone"));
						dto.setUserPhoto(obj.getString("user_photo"));
						dto.setUserStmsg(obj.getString("user_stmsg"));
						
						
						if(obj.getString("userId").equals("test")){
							dto.setUserSection(0);
							dto.setHeader(true);
							
						}else{
							if(header == 0){
								dto.setUserSection(1);
								dto.setHeader(true);
								header = 1;
							}else{
								dto.setUserSection(1);
								dto.setHeader(false);
							}
						//}
							ChattingService.getInstance().InserMember(dto);
							//ChattingApplication.getInstance().memberMap.put(String.valueOf(obj.getInt("seq")), dto);
							//ChattingApplication.getInstance().memberArray.add(dto);
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}*/

}
