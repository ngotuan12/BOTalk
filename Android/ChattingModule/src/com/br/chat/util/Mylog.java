package com.br.chat.util;

import java.util.HashMap;
import java.util.Map;

public class Mylog {

	public static String DEBUG_TAG = "[vh.frl.vhui]";
	
	public static boolean DEBUG = true;
	
	public static void v(String c, String log){
		if(DEBUG){
			android.util.Log.v(DEBUG_TAG, "<<"+c+"> " + log);
		}
	}
	
	public static void d(String c, String log){
		if(DEBUG){
			android.util.Log.d(DEBUG_TAG, "<<"+c+"> " + log);
		}
	}
	
	public static void i(String c, String log){
		if(DEBUG){
			android.util.Log.i(DEBUG_TAG, "<<"+c+"> " + log);
		}
	}
	
	public static void w(String c, String log){
		if(DEBUG){
			android.util.Log.w(DEBUG_TAG, "<<"+c+"> " + log);
		}
	}
	
	public static void e(String c, String log){
		if(DEBUG){
			android.util.Log.e(DEBUG_TAG, "<<"+c+"> " + log);
		}
	}

	public static void printMap(String c, HashMap<String, Object> hashMap){
		if(DEBUG){
			Mylog.e(""," Type : " + hashMap.getClass().getCanonicalName());
            Map map1 = (Map) hashMap;
            for (Object key : map1.keySet()) {
            	Mylog.e("","        - " + key + " : " + map1.get(key));
            }
		}
	}
	
	public static void printMapAWS(String c, Map<String, String> hashMap){
		if(DEBUG){
			Mylog.e(""," Type : " + hashMap.getClass().getCanonicalName());
            Map map1 = (Map) hashMap;
            for (Object key : map1.keySet()) {
            	Mylog.e("","        - " + key + " : " + map1.get(key));
            }
		}
	}
}
