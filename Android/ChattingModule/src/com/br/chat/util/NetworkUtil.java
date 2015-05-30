package com.br.chat.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
     
     
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        } 
        return TYPE_NOT_CONNECTED;
    }
     
    public static int getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        int status = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = 2;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = 1;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = 3;
        }
        return status;
    }
    
    public static boolean isScreen(Context context){
    	try {
			KeyguardManager km = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
			return km.inKeyguardRestrictedInputMode();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
    	return false;
    }
    
}