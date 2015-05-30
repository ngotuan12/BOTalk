package com.br.chat.socket;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.br.chat.ChattingApplication;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.activity.Activity_Profile;
import com.br.chat.adapter.ChatStatusType;
import com.br.chat.adapter.ChatType;
import com.br.chat.service.ChattingService;
import com.br.chat.util.CreateKey;
import com.br.chat.util.NetworkUtil;
import com.br.chat.util.NotificationBuilder;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;

public class MessageHandler extends Handler{

	Context mContext;
	NotificationBuilder builder = null; 
	public MessageHandler(Context context){
		mContext = context;
		builder = new NotificationBuilder(context);
	}
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		MessageVo vo = (MessageVo)msg.obj;
		ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
	    ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
		String myseq = SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq); //내아이디
		switch(msg.what){
		
		case 1: //1:1방 
			//if(Activity_Chat.getInstance() != null){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
			if(Activity_Chat.getInstance() != null && topActivity.getClassName().equals(Activity_Chat.class.getName()) || topActivity.getClassName().equals(Activity_Profile.class.getName())|| topActivity.getClassName().equals("com.br.chat.activity.ActivityFriendPicker") ){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
				if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq()) ){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,true);
						if(vo.getStatus() != ChatStatusType.SendFail)
							ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getRoomseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype(),vo.getRevname(),vo.getMembername());
					}else{ //아니면 다른방 이거나 멤버추가 채팅방만 리프레쉬
						if(vo.getMsgtype() != ChatType.add && vo.getMsgtype() != ChatType.leave){
							//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
							Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
						 	mContext.sendBroadcast(intent);
						 	builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
							//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq());
						}
						ChattingApplication.getInstance().getRoomList();
					}
				}else{ //내가보낸 메세지가 아니면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getSendseq()) && !NetworkUtil.isScreen(mContext)){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getSendseq(),Activity_Chat.getInstance().mPage,true);
						ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getSendseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype(),vo.getRevname(),vo.getMembername());
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
						if(vo.getMsgtype() != ChatType.add &&vo.getMsgtype() != ChatType.leave ){
							//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
							Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
						 	mContext.sendBroadcast(intent);
						 	builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
						 	//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getSendseq(),vo.getMemberseq(),vo.getRoomtype());
						}
					}
				}
			}else  if(mContext.getPackageName().equals(topActivity.getPackageName())){ //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
				ChattingApplication.getInstance().getRoomList();
				if(!vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가보낸메세지 가 아니면
					//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
					Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
				 	mContext.sendBroadcast(intent);
				 	if(vo.getMsgtype() != ChatType.add &&vo.getMsgtype() != ChatType.leave ){
				 		try {
				 			builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getSendseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
				 			//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getSendseq(),vo.getMemberseq(),vo.getRoomtype());
						} catch (Exception e) {
							Log.e("", "여기들어옴22" + ChattingApplication.getInstance().memberMap.size());
							Log.e("", "여기들어옴11" + ChattingApplication.getInstance().memberMap.get(vo.getSendseq()).getUsername());
						}
				 	}
						
				}else{
					if(vo.getStatus() == ChatStatusType.SendFail){ //내가보낸 메세지 지만, 실패로 떨어졌을때 룸 리프레쉬
						//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
						intent.putExtra("send", true);
					 	mContext.sendBroadcast(intent);
					}
				}
			}else{// 어플실행중이 아닐때
				Log.e("", "어플실행중이 아닙니다.");
				if(vo.getMsgtype() != ChatType.add &&vo.getMsgtype() != ChatType.leave ){
					builder.NotificationImageLoader(R.drawable.ic_launcher,  "새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getSendseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
					//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher,  "새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getSendseq(),vo.getMemberseq(),vo.getRoomtype());
				}
				
			}
			
		
			if(ChattingApplication.getInstance().memberMap.get(vo.getSendseq())!=null && !vo.getSendseq().equals(myseq)){
				ChattingApplication.getInstance().chatdb.UpdateMember(mContext, vo);
				ChattingApplication.getInstance().memberMap.get(vo.getSendseq()).setUsername(vo.getSendname());
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				mContext.sendBroadcast(intent);
				
			}
			
			//노티피케이션 보이기
			
			break;
			
		case 2: //1:1방읽음처리
			if(Activity_Chat.getInstance() != null && topActivity.getClassName().equals(Activity_Chat.class.getName())|| topActivity.getClassName().equals(Activity_Profile.class.getName())|| topActivity.getClassName().equals("com.br.chat.activity.ActivityFriendPicker")){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
				if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,false);		
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
					}
				}else{ //내가보낸 메세지가 아니면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getSendseq())){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getSendseq(),Activity_Chat.getInstance().mPage,false);	
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
					}
				}
			}else{ //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
				ChattingApplication.getInstance().getRoomList();
			}
			break;
			
		case 3://단체방 메세지
			if(Activity_Chat.getInstance() != null && topActivity.getClassName().equals(Activity_Chat.class.getName()) || topActivity.getClassName().equals("com.br.chat.activity.ActivityFriendPicker") || topActivity.getClassName().equals(Activity_Profile.class.getName())){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
				if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 방 seq와 같다면
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,true);
						if(vo.getStatus() != ChatStatusType.SendFail)
							ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getRoomseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype(),vo.getRevname(),vo.getMembername());
					}else{ //현재방이 다르다면
						if(vo.getMsgtype() != ChatType.add && vo.getMsgtype() != ChatType.leave){
							//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
							Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
						 	mContext.sendBroadcast(intent);
						 	builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
						 	//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype());
						}else if(vo.getMsgtype() == ChatType.add || vo.getMsgtype() == ChatType.leave){
							Activity_Chat.getInstance().setRoomSeq(vo.getRoomseq());
							ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,true);
						}
						ChattingApplication.getInstance().getRoomList();
					}
				}else{ //내가보낸 메세지가 아니면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())&& !NetworkUtil.isScreen(mContext) /*&& mContext.getPackageName().equals(topActivity.getPackageName())*/){ // 현재방이 룸시퀀스와 같다면
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,true);
						ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getRoomseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype(),vo.getRevname(),vo.getMembername());
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
						if(vo.getMsgtype() != ChatType.add && vo.getMsgtype() != ChatType.leave){
							Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
							//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						 	mContext.sendBroadcast(intent);
						 	builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname()+ " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
						 	//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher,  vo.getSendname()+ " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype());
						}
					}
				}
			}else if(mContext.getPackageName().equals(topActivity.getPackageName())){  //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
				ChattingApplication.getInstance().getRoomList();
				if(!vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){
					//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
					Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
				 	mContext.sendBroadcast(intent);
				 	if(vo.getMsgtype() != ChatType.add &&vo.getMsgtype() != ChatType.leave ){
				 		builder.NotificationImageLoader(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
				 		//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher,  vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityChat), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype());
				 	}
						
				}else{
					if(vo.getStatus() == ChatStatusType.SendFail){ //내가보낸 메세지 지만, 실패로 떨어졌을때 룸 리프레쉬
						Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
						//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						intent.putExtra("send", true);
					 	mContext.sendBroadcast(intent);
					}
				}
			}else{
				Log.e("", "어플실행중이 아닙니다");
				if(vo.getMsgtype() != ChatType.add &&vo.getMsgtype() != ChatType.leave ){
					builder.NotificationImageLoader(R.drawable.ic_launcher, "새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype(),vo.getSendseq(),vo.getSendname(),vo.getMembername());
					//builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, "새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true,vo.getRoomseq(),vo.getMemberseq(),vo.getRoomtype());
				}
			}
			if(ChattingApplication.getInstance().memberMap.get(vo.getSendseq())!=null && !vo.getSendseq().equals(myseq)){
				ChattingApplication.getInstance().chatdb.UpdateMember(mContext, vo);
				ChattingApplication.getInstance().memberMap.get(vo.getSendseq()).setUsername(vo.getSendname());
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				mContext.sendBroadcast(intent);
				
			}
			break;
			
		case 4: //단체방 읽음처리
			
			if(Activity_Chat.getInstance() != null && topActivity.getClassName().equals(Activity_Chat.class.getName())) { //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
				if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,false);		
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
					}
				}else{ //내가보낸 메세지가 아니면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 받는사람 seq와 같다면 현재방
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,false);	
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
					}
				}
			}else{ //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
				ChattingApplication.getInstance().getRoomList();
			}
			break;
			
		case 5://퇴장메세지
			ChattingApplication.getInstance().getRoomList();
			if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 퇴장메세지면
				if(Activity_Chat.getInstance() != null)
					Activity_Chat.getInstance().leaveChatRoom();
			}else{
				if(Activity_Chat.getInstance() != null){
					ChattingService.getInstance().getRoomMessage(vo.getRoomseq(),Activity_Chat.getInstance().mPage,true);
				}else{
					Intent intent = new Intent("android.intent.action.ReceiverChatRoombt");
					//Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
					intent.putExtra("send", true);
				 	mContext.sendBroadcast(intent);
				}

			}
			
	/*		if(Activity_Chat.getInstance() != null){ //현재 채팅방이 살아있는지 확인 살아있다면 메세지 리프레쉬
				if(vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){ //내가 보낸 메세지면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 방 seq와 같다면
						//ChattingService.getInstance().getRoomMessage(vo.getRoomseq());
						if(vo.getStatus() != ChatStatusType.SendFail)
							ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getRoomseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype());
					}else{ //현재방이 다르다면
						if(vo.getMsgtype() != ChatType.add && vo.getMsgtype() != ChatType.leave){
							Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						 	mContext.sendBroadcast(intent);
							builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true);	
						}else if(vo.getMsgtype() == ChatType.add || vo.getMsgtype() == ChatType.leave){
							Activity_Chat.getInstance().setRoomSeq(vo.getRoomseq());
							ChattingService.getInstance().getRoomMessage(vo.getRoomseq());
						}
						ChattingApplication.getInstance().getRoomList();
					}
				}else{ //내가보낸 메세지가 아니면
					if(Activity_Chat.getInstance().getRoomSeq().equals(vo.getRoomseq())){ // 현재방이 룸시퀀스와 같다면
						ChattingService.getInstance().getRoomMessage(vo.getRoomseq());
						ChattingApplication.getInstance().socket.StartSocket(vo.getMemberseq(), CreateKey.getFileKey(), vo.getRoomseq(),SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq), SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq),vo.getRoomtype());
					}else{ //아니면 다른방 이기때문에 채팅방만 리프레쉬
						ChattingApplication.getInstance().getRoomList();
						if(vo.getMsgtype() != ChatType.add && vo.getMsgtype() != ChatType.leave){
							Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						 	mContext.sendBroadcast(intent);
							builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true);	
						}
					}
				}
			}else{ //채팅방이 살아있지않다면 대화방 탭만 리프레쉬
				ChattingApplication.getInstance().getRoomList();
				if(!vo.getSendseq().equals(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq))){
					Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
				 	mContext.sendBroadcast(intent);
					builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, vo.getSendname() + " 님 에게 새로운 메세지가 도착했습니다.", vo.getMsg(), mContext.getString(R.string.action_activityMain), true);	
				}else{
					if(vo.getStatus() == ChatStatusType.SendFail){ //내가보낸 메세지 지만, 실패로 떨어졌을때 룸 리프레쉬
						Intent intent = new Intent("android.intent.action.ReceiverChatRoom");
						intent.putExtra("send", true);
					 	mContext.sendBroadcast(intent);
					}
				}
			}*/
			break;
		}
	}
}
