package com.brainyxlib.camera;

import android.app.Activity;
import android.content.Intent;


public class BrCameraManager {

	public final String TAG = "BrCameraManager";
	private static BrCameraManager instance = null;
	public static final int PICTURE = 1010;
	public static final int ALBUM = 1020;
	
	public synchronized static BrCameraManager getInstance() {
		if (instance == null) {
			synchronized (BrCameraManager.class) {
				if (instance == null) {
					instance = new BrCameraManager();
				}
			}
		}
		return instance;
	}
	
	
	
	public void CallCameraPicture(Activity context){
		try {
			Intent intent = new Intent(context,CameraActivity.class) ;
			intent.putExtra("what", 2);
			context.startActivityForResult(intent, PICTURE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void CallCameraAlbum(Activity context){
		try {
			Intent intent = new Intent(context,CameraActivity.class) ;
			intent.putExtra("what", 1);
			context.startActivityForResult(intent, ALBUM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
