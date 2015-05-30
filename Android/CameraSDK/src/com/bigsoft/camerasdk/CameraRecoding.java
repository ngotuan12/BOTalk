package com.bigsoft.camerasdk;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class CameraRecoding  {
	
	
    public Context mContext;
    public Activity mActivity;
   

	public static File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	
	public CameraRecoding(Context context, Activity activity){
		
		mContext = context ;
		mActivity = activity;
		
		
		String state = Environment.getExternalStorageState();
    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    		mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
    	}
    	else {
    		mFileTemp = new File(mActivity.getFilesDir(), TEMP_PHOTO_FILE_NAME);
    	}
	}
	
	/**
	 * 현재 파일 위치 
	 * @return
	 */
	public static String getFilePath()
	{
		return mFileTemp.getPath();
	}
	/**
	 * 앨범을  호출 
	 */
	public void startAlbum(int result)
	{
		System.out.println("doTakeAlbumAction");
		// call album
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		mActivity.startActivityForResult(intent, result);

	}

	/**
	 * 카메라 촬영 모드로 바로 호출 
	 */
	public void startAlbumCamera(int result)
	{
		
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	        try {
	        	Uri mImageCaptureUri = null;
	        	String state = Environment.getExternalStorageState();
	        	if (Environment.MEDIA_MOUNTED.equals(state)) {
	        		mImageCaptureUri = Uri.fromFile(mFileTemp);
	        	}
	            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
	            intent.putExtra("return-data", true);
	            mActivity.startActivityForResult(intent, result);
	        } catch (ActivityNotFoundException e) {
	        	System.out.println(e);
	        }
	        
	}
	
	
	
	
}

