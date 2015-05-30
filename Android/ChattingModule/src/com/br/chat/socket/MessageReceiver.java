package com.br.chat.socket;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.adapter.ChatStatusType;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageReceiver extends BroadcastReceiver{

	MessageHandler handler = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String datamsg = intent.getStringExtra("data");
			boolean send = intent.getBooleanExtra("send", false);
			Log.e("", "리시버 메세지 받음 = " + datamsg);
			JSONObject obj = new JSONObject(datamsg);
			/*if(ChattingApplication.getInstance().memberMap == null ||ChattingApplication.getInstance().memberMap.size() == 0 ){
				Log.e("", "어플리케이션 클래스 가 null 값 입니다. " );
				ChattingService.getInstance().getMember();
			}*/
			if(obj != null){
				MessageVo vo = new MessageVo();
				vo.setMsgkey(obj.getString("msgkey"));
				vo.setSendseq(obj.getString("sendseq"));
				vo.setMsg(obj.getString("msg"));
				vo.setRoomseq(obj.getString("roomseq"));
				vo.setSendname(obj.getString("sendname"));
				vo.setMsgtype(Integer.valueOf(obj.getString("sendtype")));
				vo.setMsgregdate(obj.getString("msgregdate"));
				vo.setMemberseq(ChatVo.getRoomMember(obj.getString("memberseq")));
				vo.setRoomtype(Integer.valueOf(obj.getString("roomtype")));
				vo.setRevname(obj.getString("revname"));
				vo.setMembername(ChatVo.getRoomMember(obj.getString("membername")));
				if(send){
					vo.setStatus(ChatStatusType.SendSuc);
				}else{
					vo.setStatus(ChatStatusType.SendFail);
				}
				
				String myseq = SharedManager.getInstance().getString(context, BaseGlobal.User_Seq); //내아이디
				
				switch(vo.getMsgtype()){
				case 1: // 
					break;
				case 2:// 일반메세지
					if(vo.getRoomtype() == ChatGlobal.chatTypeGroup){
						SendMessageTextGroup(context, myseq, vo);	
					}else if(vo.getRoomtype() == ChatGlobal.chatTypeSingle){
						SendMessageText(context, myseq, vo);	
					}
					
					break;
					
				case 3: //파일
					if(vo.getRoomtype() == ChatGlobal.chatTypeGroup){
						SendMessageTextGroup(context, myseq, vo);	
					}else if(vo.getRoomtype() == ChatGlobal.chatTypeSingle){
						SendMessageText(context, myseq, vo);	
					}
					break;
					
				case 4: //방나감
					if(vo.getRoomtype() == ChatGlobal.chatTypeGroup){
						leaveMessageGroup(context, vo,myseq);	
					}else if(vo.getRoomtype() == ChatGlobal.chatTypeSingle){
						leaveMessage(context, vo,myseq);
					}
					
					
					break;
					
				case 5: // 읽음
					if(vo.getRoomtype() == ChatGlobal.chatTypeGroup){
						SendMessageReadGroup(context, vo,myseq);	
					}else if(vo.getRoomtype() == ChatGlobal.chatTypeSingle){
						SendMessageRead(context, vo,myseq);
					}
					
					break;
					
				case 6: //멤버추가 or 단체채팅
					SendMessageTextGroup(context, myseq, vo);
					break;
				case 8: //멤버이미지변경
					UpdateMember(context, vo, myseq);
					break;
				case 9:// 멤버상태변경
					UpdateMemberStMsg(context, vo, myseq);
					break;
				}
				
		
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void UpdateMember(Context context, MessageVo vo, String myseq){
		try {
			
			   ImageLoader.getInstance().clearMemoryCache();
		       ImageLoader.getInstance().clearDiskCache();
		   		Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
		   		context.sendBroadcast(intent);
		   		intent = new Intent("android.intent.action.ReceiverChatRoombt");
		   		context.sendBroadcast(intent);
/*			if(!vo.getSendseq().equals(myseq)){//상대방 이미지가 변경되었을경우
				ChattingApplication.getInstance().chatdb.UpdateMemberImg(context, vo);
				String sendseq = vo.getSendseq();
				ChattingApplication.getInstance().memberMap.get(sendseq).setUserPhoto(vo.getMsg());
				//Intent intent = new Intent("android.intent.action.ReceiverMember");
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				context.sendBroadcast(intent);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void UpdateMemberStMsg(Context context, MessageVo vo, String myseq){
		try {
			if(!vo.getSendseq().equals(myseq)){//상대방 이미지가 변경되었을경우
				ChattingApplication.getInstance().chatdb.UpdateMemberStMsg(context, vo);
				String sendseq = vo.getSendseq();
				ChattingApplication.getInstance().memberMap.get(sendseq).setUserStmsg(vo.getMsg());
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				context.sendBroadcast(intent);
			}else{ //내가보낸것
				ChattingApplication.getInstance().chatdb.UpdateMyStMsg(context, vo);
				ChattingApplication.getInstance().memberMap.get(myseq).setUserStmsg(vo.getMsg());
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				context.sendBroadcast(intent);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1:1방 나가기
	 * @param context
	 * @param vo
	 * @param sendseq
	 */
	public void leaveMessage(Context context, MessageVo vo, String myseq){
		try {
			if(vo.getSendseq().equals(myseq)){//내가 방을 나간것이라면
				ChattingApplication.getInstance().chatdb.DeleteRoom(context, vo.getRoomseq(), myseq); //대화방삭제
				ChattingApplication.getInstance().chatdb.DeleteMessage(context, vo.getRoomseq()); //대화방 메세지 삭제
				
			}else{ //다른사람이 방을 나간것 이라면
				String[] members = vo.getMemberseq(); 
				String member = "";
				for(int i = 0 ; i < members.length; i++){
					if(!members[i].equals(vo.getSendseq())){ //나간사람 삭제
						if(member.length() == 0){
							member = members[i];
						}else {
							member = member + "_" + members[i];
						}
					}
				}
				vo.setMemberseq(ChatVo.getRoomMember(member)); // 나간사람제외후 업데이트
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);// 방업데이트
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getSendseq(), vo.getSendseq(), vo.getMsgregdate());// 메세지멤버 삭제
				ChattingApplication.getInstance().getRoomList();//대화방 새로고침
			}
			
			if(handler != null){
				Message msg = new Message();
				msg.obj = vo;
				msg.what = 5;
				handler.sendMessage(msg);
			}else{
				handler = new MessageHandler(context);
				Message msg = new Message();
				msg.obj = vo;
				msg.what = 5;
				handler.sendMessage(msg);
			}
			
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	/**
	 * 단체방 나가기
	 * @param context
	 * @param vo
	 * @param sendseq
	 */
	public void leaveMessageGroup(Context context, MessageVo vo, String myseq){
		try {
			if(vo.getSendseq().equals(myseq)){//내가 방을 나간것이라면
				ChattingApplication.getInstance().chatdb.DeleteRoom(context, vo.getRoomseq(), myseq); //대화방삭제
				ChattingApplication.getInstance().chatdb.DeleteMessage(context, vo.getRoomseq()); //대화방 메세지 삭제
				
			}else{ //다른사람이 방을 나간것 이라면
				String[] members = vo.getMemberseq(); 
				String[] membernames = vo.getMembername();
				String member = "";
				String membername = "";
				for(int i = 0 ; i < members.length; i++){
					if(!members[i].equals(vo.getSendseq())){ //나간사람 삭제
						if(member.length() == 0){
							member = members[i];
							membername = membernames[i];
						}else {
							member = member + "_" + members[i];
							membername = membername + "_" +membernames[i];
						}
					}
				}
				vo.setMemberseq(ChatVo.getRoomMember(member)); // 나간사람제외후 업데이트
				vo.setMembername(ChatVo.getRoomMember(membername));
				if(Activity_Chat.getInstance() != null){
					Activity_Chat.getInstance().setRoomMember(vo.getMemberseq());
				}
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);// 방업데이트
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());// 메세지멤버 삭제
				ChattingApplication.getInstance().getRoomList();//대화방 새로고침
			}
			
			if(handler != null){
				Message msg = new Message();
				msg.obj = vo;
				msg.what = 5;
				handler.sendMessage(msg);
			}else{
				handler = new MessageHandler(context);
				Message msg = new Message();
				msg.obj = vo;
				msg.what = 5;
				handler.sendMessage(msg);
			}
			
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	/**
	 * 1:1방 기준 읽음처리
	 * @param context
	 * @param vo
	 * @param myseq
	 */
	public void SendMessageRead(Context context,MessageVo vo,String myseq){
		if(vo.getSendseq().equals(myseq)){//내가보낸메세지
			ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());	
		}else{ //남이보낸메세지
			ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getSendseq(), vo.getSendseq(), vo.getMsgregdate());
			//ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getSendseq(),SharedManager.getInstance().getString(context, BaseGlobal.User_Seq), SharedManager.getInstance().getString(context, BaseGlobal.User_Seq));
		}
		ChattingApplication.getInstance().getRoomList();
		if(handler != null){
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 2;
			handler.sendMessage(msg);
		}else{
			handler = new MessageHandler(context);
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 2;
			handler.sendMessage(msg);
		}
		//ChattingApplication.getInstance().getRoomList();
	}
	/**
	 * 그룹방 기준 읽음처리
	 * @param context
	 * @param vo
	 * @param myseq
	 */
	public void SendMessageReadGroup(Context context,MessageVo vo,String myseq){
		if(vo.getSendseq().equals(myseq)){//내가보낸메세지
			ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());	
		}else{ //남이보낸메세지
			ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
			//ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getSendseq(),SharedManager.getInstance().getString(context, BaseGlobal.User_Seq), SharedManager.getInstance().getString(context, BaseGlobal.User_Seq));
		}
		ChattingApplication.getInstance().getRoomList();
		if(handler != null){
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 4;
			handler.sendMessage(msg);
		}else{
			handler = new MessageHandler(context);
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 4;
			handler.sendMessage(msg);
		}
		//ChattingApplication.getInstance().getRoomList();
	}
	/**
	 * 1:1채팅방기준
	 * @param context
	 * @param myseq
	 * @param vo
	 */
	public void SendMessageText(Context context,String myseq,MessageVo vo){
		
		if(vo.getSendseq().equals(myseq)){ //내가보낸메세지 라면 
			if(ChattingApplication.getInstance().chatdb.IsRoom(context, vo.getRoomseq())){ //내가보낸메세지라면 방번호는 상대방번호
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,true);
				if(vo.getStatus() != 2){
					ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
					ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
				}
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, true);
			}else{ //방이없으면
				ChattingApplication.getInstance().chatdb.CreateRoom(context, vo.getRoomseq(), vo.getSendseq(), vo.getMemberseq(), vo.getRoomtype(), /*0,*/ vo.getMsg(),vo.getRevname(),vo.getMembername());
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,true);
				if(vo.getStatus() != 2){
					ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
					ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
				}
					
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, true);
			}
		}else{ // 남이보낸 메세지
			if(ChattingApplication.getInstance().chatdb.IsRoom(context, vo.getSendseq())){ //남이보낸메세지라면 방번호는 상대방번호
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getSendseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getSendseq(), vo.getSendseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);
			}else{
				ChattingApplication.getInstance().chatdb.CreateRoom(context, vo.getSendseq(), vo.getRoomseq(), vo.getMemberseq(),vo.getRoomtype(),/* 1, 1,*/  vo.getMsg(),vo.getSendname(),vo.getMembername());
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getSendseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getSendseq(), vo.getSendseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);
			}
		}
		
		ChattingApplication.getInstance().getRoomList();
		
		if(handler != null){
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 1;
			handler.sendMessage(msg);
		}else{
			handler = new MessageHandler(context);
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 그룹채팅기준
	 * @param context
	 * @param myseq
	 * @param vo
	 */
	public void SendMessageTextGroup(Context context,String myseq,MessageVo vo){
		if(vo.getSendseq().equals(myseq)){ //내가보낸메세지 라면 
			if(ChattingApplication.getInstance().chatdb.IsRoom(context, vo.getRoomseq())){ //내가보낸 메세지이면 방번호는 발급받은 방키값
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,true);
				if(vo.getStatus() != 2){
					ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
					ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
				}
				//if(vo.getMsgtype() != ChatType.add)
					ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, true);
			}else{ //방이없으면
				ChattingApplication.getInstance().chatdb.CreateRoom(context, vo.getRoomseq(), vo.getSendseq(), vo.getMemberseq(), vo.getRoomtype(), /*0,*/ vo.getMsg(),vo.getRevname(),vo.getMembername());
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,true);
				if(vo.getStatus() != 2){
					ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
					ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
				}
			//	if(vo.getMsgtype() != ChatType.add)
					ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, true);
			}
		}else{ // 남이보낸 메세지
			if(ChattingApplication.getInstance().chatdb.IsRoom(context, vo.getRoomseq())){ //남이보낸메세지라면 방번호는 전달받은 그룹방 키값
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
			//	if(vo.getMsgtype() != ChatType.add)
					ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);
			}else{
				ChattingApplication.getInstance().chatdb.CreateRoom(context, vo.getRoomseq(), myseq, vo.getMemberseq(),vo.getRoomtype(),/* 1, 1,*/  vo.getMsg(),vo.getRevname(),vo.getMembername());
				ChattingApplication.getInstance().chatdb.InsertMessage(context, vo,false);
				ChattingApplication.getInstance().chatdb.InsertMsgMember(context, vo.getRoomseq(), vo.getMsgkey(), vo.getMemberseq(), vo.getMsgregdate());
				ChattingApplication.getInstance().chatdb.DeleteMsgMember(context, vo.getRoomseq(), vo.getSendseq(), vo.getMsgregdate());
			//	if(vo.getMsgtype() != ChatType.add)
					ChattingApplication.getInstance().chatdb.UpdateRoom(context, vo, false);
			}
		}
		
		ChattingApplication.getInstance().getRoomList();
		
		if(handler != null){
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 3;
			handler.sendMessage(msg);
		}else{
			handler = new MessageHandler(context);
			Message msg = new Message();
			msg.obj = vo;
			msg.what = 3;
			handler.sendMessage(msg);
		}
	}
	
}
