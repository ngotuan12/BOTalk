package com.bxm.boardtalks.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.br.chat.ChatGlobal;
import com.br.chat.util.Request.OnAfterParsedData;
import com.br.chat.vo.ChatVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.bxm.boardtalks.main.BaseActivity;
import com.bxm.boardtalks.main.FragmentMain;
import com.bxm.boardtalks.util.WriteFileLog;

public class LoginBase extends BaseActivity{

	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {}
	ChatVo chatvo;
	public void AutoLogin(String userid, /* String pushkey, */OnAfterParsedData lisener){
		try {
			
			  List<NameValuePair> dataList = new ArrayList<NameValuePair>();
			  dataList.add(new BasicNameValuePair("userId", userid));
			  //dataList.add(new BasicNameValuePair("pushKey", pushkey));
			  request.requestHttp(this, dataList,ChatGlobal.getMemberList, lisener);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	protected boolean CheckLoginType(){
		boolean loginType = SharedManager.getInstance().getBoolean(this, BaseGlobal.AutoLogin);
		return loginType;
	}
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //성공
				GoMainActivity();
				break;
				
			case 2: //실패
				GoLoginActivity();
				break;
			}
		}
	};
	
	public void GoMainActivity(){
		try {
			
			Intent intent = new Intent(this,FragmentMain.class);
			intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
			startActivity(intent);
			
		/*	if(SharedManager.getInstance().getBoolean(this, "pass")){
				intent = new Intent(this,Password.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				intent.putExtra("password", 4);
				startActivity(intent);
			}*//*else{
				Intent intent = new Intent(this,FragmentMain.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				startActivity(intent);
			}
		*/
			finish();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void GoLoginActivity(){
		try {
			Intent intent = new Intent(this,Activity_Login.class);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
}
