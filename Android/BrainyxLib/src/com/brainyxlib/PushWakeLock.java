package com.brainyxlib;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;

public class PushWakeLock {     
    private static PowerManager.WakeLock sCpuWakeLock;    
    private static KeyguardManager.KeyguardLock mKeyguardLock;    
    private static boolean isScreenLock;     
     
    /**
     * 휴대폰 wakeup 시키지
     * @param context
     */
    public static void acquireCpuWakeLock(Context context) {        
         
        if (sCpuWakeLock != null) {            
            return;        
        }         
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);         
        sCpuWakeLock = pm.newWakeLock(                
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |                
                PowerManager.ACQUIRE_CAUSES_WAKEUP |                
                PowerManager.ON_AFTER_RELEASE, "wakelock");        
         
        sCpuWakeLock.acquire();        
    }
     
    /**
     *  wake 종료
     */
    public static void releaseCpuLock() {        
        if (sCpuWakeLock != null) {            
            sCpuWakeLock.release();            
            sCpuWakeLock = null;        
        }    
    }
    
    /**
     * wakeup종료 예약
     * @param context
     */
	public static void registerRestartAlarm(Context context) {
		Intent intent = new Intent(context,WakeLockReleaseReceiver.class);
		intent.setAction(WakeLockReleaseReceiver.ACTION_RESTART_PERSISTENTSERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

		long firstTime = SystemClock.elapsedRealtime();
		firstTime += 1000;
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, sender);
	}

}
