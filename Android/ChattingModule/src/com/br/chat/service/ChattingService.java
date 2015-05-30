package com.br.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.socket.ConnetedSocket;
import com.br.chat.socket.ConnetedSocket.ConnectedListner;
import com.br.chat.util.ContactUtil;
import com.br.chat.util.Request;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

public class ChattingService extends Service{

	public static ChattingService instance = null;
	
	public static ChattingService getInstance(){
			return instance;
	}
	
	public ConnetedSocket socket;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	/**
	 * 채팅방이 있는지 확인
	 * @param seq
	 * @return
	 */
	public boolean getRoomCheck(String seq,int type){
		try {
			if(ChattingApplication.getInstance() != null){
				return ChattingApplication.getInstance().chatdb.IsRoom(instance, seq);
				//return ChattingApplication.getInstance().chatdb.IsRoom(instance, seq,String.valueOf(type));
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return false;
	}
	
	/**
	 * 채팅방을 만듭니다.
	 * @param seq
	 * @param owner
	 * @param member
	 * @param receiver
	 * @param type
	 * @param cnt
	 * @param newmsg
	 */
	public void CreateRoom(String roomseq, String owner, String[] member, /*String receiver,*/int type,/* int cnt, */String newmsg,String revname,String[] membername){
		try {
			if(ChattingApplication.getInstance() != null){
				ChattingApplication.getInstance().chatdb.CreateRoom(instance, roomseq, owner, member, /*receiver ,*/type,/* cnt,*/ newmsg,revname,membername);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 채팅방 메세지를 가져온다
	 * @param seq
	 * @param roomtype
	 */
	public void getRoomMessage(String roomseq,int mPage,boolean flag){
		try {
			if(ChattingApplication.getInstance() != null){
				Cursor cursor = ChattingApplication.getInstance().chatdb.getChatMessage(instance, roomseq,mPage);
				if(Activity_Chat.getInstance() != null){
					MessageVo.ParseCusor(cursor,Activity_Chat.getInstance().messg);
					Activity_Chat.getInstance().UpdateView(flag);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 소켓 연결
	 * @param myseq
	 * @param myname
	 */
	public void SocketConnect(/*String[] memseq,final String revseq,*/final String myseq,final String myname/*,final int roomtype*/,ConnectedListner listener){
		try {
			socket = new ConnetedSocket(instance,listener);
			socket.ConnectSocket(myseq, myname);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	/**
	 * 보낸메세지 상태를 가져온다.
	 * @param msgkey
	 * @return
	 */
	public int getStatusMessage(String msgkey){
			if(ChattingApplication.getInstance() != null){
				 return ChattingApplication.getInstance().chatdb.getStatusMessage(instance, msgkey);
			}
			return -1;
	}
	
	/**
	 * 메세지 상태를 업데이트 한다.
	 * @param msgkey
	 * @param status
	 */
	public void UpdateMessageStatus(String msgkey, int status){
		if(ChattingApplication.getInstance() != null){
			 ChattingApplication.getInstance().chatdb.UpdateMessage(instance, msgkey, status);
		}
	}
	
	public void InsertMyInfo(MemberVo member){
		if(ChattingApplication.getInstance() != null){
			ChattingApplication.getInstance().chatdb.InsertMyInfo(instance, member.getSeq(), member.getUserId(), member.getUsername(), member.getUserNick(), member.getUserPhone(),
					member.getUserStmsg());
		}
	}
	public void InserMember(MemberVo member){
		if(ChattingApplication.getInstance() != null){
			 ChattingApplication.getInstance().chatdb.InsertMember(instance, member.getSeq(), member.getUserId(),member.getUsername(),
					 member.getUserNick(), member.getUserPhone(), member.getUserStmsg(), member.getUserPhoto(), member.getUserSection(), member.isHeader());
		}
	}
	
	public void getMyInfo(){
		if(ChattingApplication.getInstance() != null){
			 Cursor cursor = ChattingApplication.getInstance().chatdb.getMyInfo(instance);
			 MemberVo.PasingCursorMyinfo(cursor);
			 ChattingApplication.getInstance().chatdb.Close();
		}
	}
	public void getMember(){//2
		if(ChattingApplication.getInstance() != null){
			/*//내정보가져오기
			 Cursor cursor = ChattingApplication.getInstance().chatdb.getMyInfo(instance);
			 MemberVo.PasingCursorMyinfo(cursor);
			 ChattingApplication.getInstance().chatdb.Close();*/
		
			 // 멤버리스트 가져오기
			 Cursor cursor2 = ChattingApplication.getInstance().chatdb.getMember(instance);
			 MemberVo.PasingCursor(cursor2);
			 ChattingApplication.getInstance().chatdb.Close();
			/* // 즐겨찾기리스트 가져오기
			 Cursor cursor3 = ChattingApplication.getInstance().chatdb.getFavor(instance);
			 MemberVo.PasingCursorFavor(cursor3);
			 ChattingApplication.getInstance().chatdb.Close();*/
		}
	}
	
	public void getFavorList(){//3
		if(ChattingApplication.getInstance() != null){
			 // 즐겨찾기리스트 가져오기
			 Cursor cursor3 = ChattingApplication.getInstance().chatdb.getFavor(instance);
			 MemberVo.PasingCursorFavor(cursor3);
			 ChattingApplication.getInstance().chatdb.Close();
		}
	}
	
	public void getMyinfo(){//1
		if(ChattingApplication.getInstance() != null){
			//내정보가져오기
			 Cursor cursor = ChattingApplication.getInstance().chatdb.getMyInfo(instance);
			 MemberVo.PasingCursorMyinfo(cursor);
			 ChattingApplication.getInstance().chatdb.Close();
		}
	}
	
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
