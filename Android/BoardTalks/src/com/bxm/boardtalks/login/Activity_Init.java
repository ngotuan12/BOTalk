package com.bxm.boardtalks.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.service.ChattingService;
import com.br.chat.vo.ChatVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

import kr.co.boardtalks.Global;
import kr.co.boardtalks.R;

import com.bxm.boardtalks.util.WriteFileLog;
import com.google.android.gcm.GCMRegistrar;

public class Activity_Init extends LoginBase{

	public static Activity_Init instance = null;
	public static Activity_Init getinstance() {
		return instance;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Log.e("", "여기들어오나요??3" );
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		setContentView(R.layout.activity_splash);
		instance = this;
		initialize();
	
	}
	
	private void initialize(){
		try {
			//loading();
			Intent intent = getIntent();
	        try{
	        	chatvo = (ChatVo)intent.getSerializableExtra(ChatGlobal.ChatRoomObj);
	        }catch(Exception e){
	        	WriteFileLog.writeException(e);
	        }
			registerGcm();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
	public void registerGcm() {
		String regId = SharedManager.getInstance().getString(this,BaseGlobal.PushKey);
		if (regId == null || regId.length() < 1) {
			GCMRegistrar.checkDevice(this);
			Log.e("gcm", "called");
			GCMRegistrar.checkManifest(this);
			Log.e("gcm", "called");
			GCMRegistrar.register(this, Global.GcmPushId);
			Log.e("gcm", "called");
		} else {
			//GCMRegistrar.unregister(this);
			//SharedManager.getInstance().setString(this,Global.mShared_PushKey,"");
			//registerGcm();
		
			loading();
		}
	}
	
	public void loading() {
		SendLogin();
		//handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	private void SendLogin(){
		try {
			if (CheckLoginType()) {
				
				handler.sendEmptyMessageDelayed(0, 1000);
				
			/*	List<NameValuePair> dataList = new ArrayList<NameValuePair>();
				String userid = SharedManager.getInstance().getString(Activity_Init.this, BaseGlobal.User_Phone);
				String userPw = SharedManager.getInstance().getString(Activity_Init.this, BaseGlobal.User_Seq);
				String pushkey = SharedManager.getInstance().getString(Activity_Init.this, BaseGlobal.PushKey);
				
				dataList.add(new BasicNameValuePair("loginId", userid));
				dataList.add(new BasicNameValuePair("loginPw", userPw));
				dataList.add(new BasicNameValuePair("pushKey", pushkey));
				dataList.add(new BasicNameValuePair("OS","ANDROID"));
				
				request.requestHttp2(this, dataList, Global.getLogin, new Request.OnAfterParsedData() {
						@Override
						public void onResult(boolean result, JSONObject jsonobj) {
							if(result){
								handler.sendEmptyMessageDelayed(0, 1000);
								//myApp.memberMap.clear();
								//MemberVo.PasingJson(resultData,myinfo);
							
							}else{
								mActionHandler.sendEmptyMessage(2);
							}
						}
					});*/
			}else{
				mActionHandler.sendEmptyMessage(2);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		//	mActionHandler.sendEmptyMessage(1);
			//getdata();
			try {
				if (CheckLoginType()) {
				//	myApp.ConnectSocket(null);
					if(myApp.memberMap == null || myApp.memberMap.size() == 0){
						
						//ChattingService.getInstance().getContactList(Activity_Init.this);
						ChattingService.getInstance().getMyInfo();
						ChattingService.getInstance().getMember();
						ChattingService.getInstance().getFavorList();
						ChattingApplication.getInstance().getContactList(Activity_Init.this);
					}
					mActionHandler.sendEmptyMessage(1);
					
				} else {
					mActionHandler.sendEmptyMessage(2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
	};
	
}
