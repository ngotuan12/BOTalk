package com.brainyxlib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class WakeLockReleaseReceiver extends BroadcastReceiver{
	
	public static String TAG = "WakeLockReleaseReceiver";
	public static final String ACTION_RESTART_PERSISTENTSERVICE = "ACTION.RESTART.PersistentService";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(ACTION_RESTART_PERSISTENTSERVICE)){
			PushWakeLock.releaseCpuLock();
		}
	}
	
	public boolean isServiceRunningCheck(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        	Log.e("BrainyxPushService", "서비스 이름 = "  + service.service.getClassName());
            if ("kr.brainyx.libs.EtradePushService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
	}
}
