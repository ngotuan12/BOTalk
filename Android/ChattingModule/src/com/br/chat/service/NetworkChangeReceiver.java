package com.br.chat.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.chat.ChattingApplication;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.util.NetworkUtil;
import com.brainyxlib.BrUtilManager;

public class NetworkChangeReceiver extends BroadcastReceiver {
	 
	
    @Override
    public void onReceive(final Context context, final Intent intent) {
 
        int status = NetworkUtil.getConnectivityStatusString(context);
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	    ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
        switch(status){
        case 1: // mobile
        	//BrUtilManager.getInstance().showToast(context, "현재 모바일상태 로 변경");
        	
    	    if(Activity_Chat.getInstance() != null || context.getPackageName().equals(topActivity.getPackageName())){
    	    	//BrUtilManager.getInstance().showToast(context, "현재 앱실행중이므로 소켓 재연결");
    	    	ChattingApplication.getInstance().ConnectSocket(null);
    	    }else{
    	    	//BrUtilManager.getInstance().showToast(context, "현재 앱실행중이므로 소켓 재연결 시도 하지않음");
    	    }
        
        	break;
        case 2: //wifi
        	//BrUtilManager.getInstance().showToast(context, "현재 와피아피상태 로 변경");
        	  if(Activity_Chat.getInstance() != null || context.getPackageName().equals(topActivity.getPackageName())){
        		//  BrUtilManager.getInstance().showToast(context, "현재 앱실행중이므로 소켓 재연결");
      	    	ChattingApplication.getInstance().ConnectSocket(null);
      	    }else{
      	    	//BrUtilManager.getInstance().showToast(context, "현재 앱실행중이므로 소켓 재연결 시도 하지않음");
      	    }
        	break;
        	
        case 3: // 네트워크없음
        	//BrUtilManager.getInstance().showToast(context, "현재 연결된 네트워크 없음");
        	
        	break;
        
        }
    }
}