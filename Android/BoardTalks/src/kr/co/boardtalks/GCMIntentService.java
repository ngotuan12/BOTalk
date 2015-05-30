package kr.co.boardtalks;

import java.net.URLDecoder;
import kr.co.boardtalks.BuildConfig;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.bxm.boardtalks.login.Activity_Init;
import com.bxm.boardtalks.util.WriteFileLog;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	private String TAG = "GCMService";
	@Override
	protected void onError(Context context, String arg1) {
	    Log.i(TAG, "Received error: " + arg1);
	} 

	@Override
	protected void onMessage(Context context, Intent intent) {
		//Log.d(TAG, "GCM Service onMessage");
		Log.e(TAG, "GCM Service onMessage");
		 try {//title, content, type
			 String contents = intent.getStringExtra("message");
			 contents =   URLDecoder.decode(contents, "UTF-8");
			 //URLEncoder.encode(s, charsetName)(message, "UTF-8");
			 Log.e(TAG, contents);
			  	intent = new Intent("android.intent.action.Backgroundbt");
				intent.putExtra("data", contents);
				
				intent.putExtra("send", true);
				context.sendBroadcast(intent);
			 
			} catch (Exception e) {
				WriteFileLog.writeException(e);
			}
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.e("gcm", "registered");
		Log.v(TAG, "onRegistered-registrationId = " + regId);
		SharedManager.getInstance().setString(context, BaseGlobal.PushKey, regId);
		
		Activity_Init instance = Activity_Init.getinstance();
		if(instance != null){
			instance.loading();	
		}
	}
	
	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// TODO Auto-generated method stub
		return super.onRecoverableError(context, errorId);
	}
	

	@Override
	protected void onUnregistered(Context context, String regId) {
	/*	Activity_Init instance = Activity_Init.getinstance();
		if(instance != null){
			SharedManager.getInstance().setString(context,BaseGlobal.PushKey,"");
			instance.registerGcm();	
		}*/
		
		if (BuildConfig.DEBUG)
			Log.d(TAG, "onUnregistered-registrationId = " + regId);
	}
	
	public boolean isServiceRunningCheck(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("kr.brainyx.libs.EtradePushService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
	}
}
