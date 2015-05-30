package com.brainyxlib;

import android.content.Context;
import android.os.Vibrator;

public class Vibrater {
	
	
	/**
	 * 진동울림(퍼밋션추가필수)
	 * @param context
	 * @param milliseconds
	 */
	public static void vibrate(Context context,long milliseconds) {
		try {
			Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

			if (vibe != null) {
				vibe.vibrate(milliseconds);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}