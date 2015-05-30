package com.br.chat.socket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.adapter.ChatStatusType;
import com.br.chat.service.ChattingService;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.Constans;
import com.br.chat.vo.MessageVo;

public class SendHandlerQue extends Handler{

	private Context context;
	private HashMap<String, MessageVo> messageMap = new HashMap<String, MessageVo>();
	
	public  SendHandlerQue (Context context){
		this.context = context;
	}
	
	public void SendMessage(Context _context,MessageVo msgvo){
		try {
			context = _context;
			messageMap.put(msgvo.getMsgkey(), msgvo);
			Message msg = getHandleMessage(msgvo);
			sendMessageDelayed(msg, ChatGlobal.chatSendFailTime);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public Message getHandleMessage(MessageVo msgvo){
		try {
			Message msg = new Message();
			msg.obj = msgvo;
			msg.what = 1;
			return msg;
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch(msg.what){
		case 1:
			MessageVo msgvo = (MessageVo)msg.obj;
			if(CheckMessage(msgvo.getMsgkey()) == -1 || CheckMessage(msgvo.getMsgkey()) == 3){ //메세지가 저장되지않았거나 20초가 지나도 전송중이면 실패로 변경
				UpdateMessageStatus(msgvo, ChatStatusType.SendFail);
				ChattingApplication.getInstance().chatdb.InsertFailMSg(context, msgvo);
			}
			break;
		}
	}
	
	public MessageVo getFailMessage(String msgkey){
		if(messageMap.get(msgkey) == null){
			return ChattingApplication.getInstance().chatdb.SelectFailMsg(context, msgkey);
		}else{
			return messageMap.get(msgkey);	
		}
	}
	
	public String CreateMsg(MessageVo msgvo){
    	try {
			JSONObject obj = new JSONObject();
			obj.put(Constans.MSG_KEY, msgvo.getMsgkey());
			obj.put(Constans.SEND_SEQ, msgvo.getSendseq());
			obj.put(Constans.MEMBER_SEQ, ChatVo.setRoomMember(msgvo.getMemberseq()));
			obj.put(Constans.MESSAGE, msgvo.getMsg());
			obj.put(Constans.SEND_NAME, msgvo.getSendname());
			obj.put(Constans.SEND_TYPE, msgvo.getMsgtype());
			obj.put(Constans.ROOM_SEQ, msgvo.getRoomseq());
			obj.put(Constans.ROOM_TYPE, msgvo.getRoomtype());
			obj.put(Constans.REV_NAME, msgvo.getRevname());
			obj.put(Constans.MEM_NAME,ChatVo.setRoomMember(msgvo.getMembername()));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			Date date = new Date();
			obj.put(Constans.REG_DATE,msgvo.getMsgregdate());
			
			return obj.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
    	return null;
    }
	
	private void UpdateMessageStatus(MessageVo vo,int status){
		try {
		 	Intent intent = new Intent("android.intent.action.Backgroundbt");
			intent.putExtra("data", CreateMsg(vo));
			intent.putExtra("send", false);
			context.sendBroadcast(intent);
			
		/*	if(ChattingService.getInstance() != null ){
				ChattingService.getInstance().UpdateMessageStatus(vo.getMsgkey(), status);
				ChattingApplication.getInstance().getRoomList();
				if(Activity_Chat.getInstance() != null){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
					if(vo.getSendseq().equals(SharedManager.getInstance().getString(context, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
						if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 받는사람 seq와 같다면 현재방
							ChattingService.getInstance().getRoomMessage(vo.getRoomseq());		
						}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
							ChattingApplication.getInstance().getRoomList();
						}
					}else{ //내가보낸 메세지가 아니면
						if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getSendseq())){ // 현재방이 받는사람 seq와 같다면 현재방
							ChattingService.getInstance().getRoomMessage(vo.getSendseq());	
						}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
							ChattingApplication.getInstance().getRoomList();
						}
					}
				}else{ //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
					ChattingApplication.getInstance().getRoomList();
				}
			}*/
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	private int CheckMessage(String msgkey){
		try {
			if(ChattingService.getInstance() != null ){
				return ChattingService.getInstance().getStatusMessage(msgkey);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return -1;
	}
	
	
}
