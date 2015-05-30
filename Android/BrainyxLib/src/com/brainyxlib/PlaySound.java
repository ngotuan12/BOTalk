package com.brainyxlib;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;

public class PlaySound {
	private static SoundPool soundpool = null;
	private static int takReady = 0;    
	private static int takReceived = 0;
	private static int takSelect = 0;
	private static int takUnselect = 0;
	private static int takPlayStop = 0;
	private static int takButtonClicked = 0;
	private static int takCloseRecord = 0;
	
	public static void setSoundPool(SoundPool aSoundpool) {
		soundpool = aSoundpool;
	}
	
	public static SoundPool getSoundPool() {
		return soundpool;
	}
	
	public static void setTakReady(int aTak) {
		takReady = aTak;
	}
	
	public static void setTakReceived(int aTak) {
		takReceived = aTak;
	}
	
	public static void setTakSelect(int aTak) {
		takSelect = aTak;
	}
	
	public static void setTakUnselect(int aTak) {
		takUnselect = aTak;
	}	
	
	public static void setTakPlayStop(int aTak) {
		takPlayStop = aTak;
	}			
	
	public static void setTakButtonClicked(int aTak) {
		takButtonClicked = aTak;
	}
	
	public static void setTakCloseRecord(int aTak) {
		takCloseRecord = aTak;
	}
	
	
	public static void playReady(Context context) {
			
			if(allowPlaySound(context) == true) {			
				checkSoundResource(context);
				if (soundpool != null) {				
					soundpool.play(takReady, 1, 1, 0, 0, 1);  		  		
				}
			}
	}
	
	public static void playReceived(Context context) {
			if(allowPlaySound(context) == true) {
				checkSoundResource(context);
				if (soundpool != null) {			
					soundpool.play(takReceived, 1, 1, 0, 0, 1); //
				}
			}
	}
	
	public static void playButtonClicked(Context context) {
			if(allowPlaySound(context) == true) {
				checkSoundResource(context);
				if (soundpool != null) {
					soundpool.play(takButtonClicked, 1, 1, 0, 0, 1);  		  		
				}
			}
	}		
	
	static boolean allowPlaySound(Context context) {
		boolean result = false;
		try {		
			AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	        switch(am.getRingerMode())
	        {
	            case AudioManager.RINGER_MODE_SILENT:
	                break;
	            case AudioManager.RINGER_MODE_VIBRATE:
	                break;
	            case AudioManager.RINGER_MODE_NORMAL:
	            	result = true;
	                break;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static void checkSoundResource(Context context){
		try {
			if (soundpool == null) {
				setSoundEffectResource(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void PlayRingtone(Context context){
		try {
			Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);
			Ringtone ringtone = RingtoneManager.getRingtone(context, ringtoneUri);
			ringtone.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void setSoundEffectResource(Context context) {
		try {
			SoundPool sp = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
			PlaySound.setSoundPool(sp);
	
			
			
			/*PlaySound.setTakReady(sp.load(context, R.raw.recoding_start, 1));
			PlaySound.setTakReceived(sp.load(context, R.raw.receive, 1));
			PlaySound.setTakSelect(sp.load(context, R.raw.select, 1));
			PlaySound.setTakUnselect(sp.load(context, R.raw.unselect, 1));
			PlaySound.setTakPlayStop(sp.load(context, R.raw.play_stop, 1));
			PlaySound.setTakButtonClicked(sp.load(context, R.raw.button_click, 1));	
			PlaySound.setTakCloseRecord(sp.load(context, R.raw.sendcomplete, 1));	*/
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
