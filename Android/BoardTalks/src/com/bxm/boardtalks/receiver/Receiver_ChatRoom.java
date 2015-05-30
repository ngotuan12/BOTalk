package com.bxm.boardtalks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bxm.boardtalks.chatroom.Fragment_ChatList;

public class Receiver_ChatRoom extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Fragment_ChatList.getInstance() != null){
			if(intent.getBooleanExtra("send", false)){ // true면 전송실패
				
			}else{
				
			}
			Fragment_ChatList.getInstance().SetView();
		}
	}

}
