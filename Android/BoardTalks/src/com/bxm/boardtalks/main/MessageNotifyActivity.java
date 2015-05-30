package com.bxm.boardtalks.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.vo.ChatVo;
import com.bxm.boardtalks.login.Activity_Init;
import com.bxm.boardtalks.util.WriteFileLog;

public class MessageNotifyActivity extends BaseActivity{

	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {}
	ChatVo chatvo;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Log.e("", "여기들어오나요??1" );
		Intent intent = getIntent();
        try{
        	chatvo = (ChatVo)intent.getSerializableExtra(ChatGlobal.ChatRoomObj);
        }catch(Exception e){
        	WriteFileLog.writeException(e);
        }
        
    	ChattingApplication.getInstance().clearNotifyMsg();
    	myApp.ConnectSocket(null);
		if(ChattingApplication.getInstance().memberMap == null ||ChattingApplication.getInstance().memberMap.size() == 0 ){ //값이없으면 init부터
			Log.e("", "어플리케이션 클래스 가 null 값 입니다. " );
				intent = new Intent(this,Activity_Init.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				startActivity(intent);
				finish();
				Log.e(""	, "Activity_Init 실행");
		}else{ // 값이 있으면 fragment부터\
			ChattingApplication.getInstance().getContactList(this);
			if(FragmentMain.getInstance() != null){
				Log.e(""	, "FragmentMain 살아있기때문에 챗팅방 실행함수");
				FragmentMain.getInstance().ChattingGo(chatvo);
				finish();
			}else{
				Log.e(""	, "FragmentMain 으로 이동");
				intent = new Intent(this,FragmentMain.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				startActivity(intent);
				finish();	
			}
		}
	}
}
