package com.br.chat.activity;

import android.app.Activity;
import android.content.Intent;

public class BxmIntent {
	/**
	 * Intent requestCode 
	 */
	public static final int GO_CAMERA			=	0;
	public static final int GO_GALLERY			=	1;
	public static final int GO_CROP_IMAGE		=	3;
	public static final int GET_IMAGE			=	4;
	public static final int GO_SOUND_RECORDER	=	5;
	public static final int GO_VIDEO_RECORDER	=	6;
	
	/*
	 * 뷰어/편집간이동
	 */
	public static final int FINISH_RESHOOT = 1;
	public static final int FINISH_SAVE = 2;
	
	/**
	 * 인텐트를 이용하여 내부에 생성된 카메라/크롭 이미지 액티비티 이동시 intent key 값 
	 */
	public static final String KEY_TEMP_IMAGE_FILEPATH						=	"KEY_TEMP_IMAGE_FILEPATH";
	public static final String KEY_FULL_PATH = "KEY_FULL_PATH";
	public static final String KEY_FINISH_MODE = "KEY_FINISH_MODE";
	
	 /**
     * 카메라 
     */
    public static void goCameraForResult(Activity act, int requestCode, String filePath)
	{
		Intent intent = new Intent(act, ActivityCamera.class);
		intent.putExtra(KEY_FULL_PATH, filePath);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		act.startActivityForResult(intent, requestCode);
	}

    /**
     * 이미지편집 
     */
    public static void goCorpImageCameraForResult(Activity act, int requestCode, String tempFilePath, String originalFilePath)
	{
		Intent intent = new Intent(act, ActivityPhotoTool.class);
		intent.putExtra(KEY_TEMP_IMAGE_FILEPATH, tempFilePath);
		intent.putExtra(KEY_FULL_PATH, originalFilePath);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		act.startActivityForResult(intent, requestCode);
	}
    
    /*
     * 오디오녹음
	 * PATH : 저장경로  (확장자없는 파일명이 포함된 저장경로를 기재    예) /storage/sdcard0/AudioRecorderFolder/newFileName  )
	 *        없으면 자동으로 반환
	 */
  /*  public static void goVoiceRecorder(Activity act, int requestCode, String path)
	{
    	Intent intent = new Intent(act, ActivitySoundRecorder.class);
		intent.putExtra(KEY_FULL_PATH, path);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		act.startActivityForResult(intent, requestCode);
	}
    */
    //캠코더
/*    public static void goVideoRecorder(Activity act, int requestCode, String path)
	{
    	Intent intent = new Intent(act, ActivityCamcorder.class);
		intent.putExtra(KEY_FULL_PATH, path);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		act.startActivityForResult(intent, requestCode);
	}*/
    
  //동영상플레이어
   /* public static void goVideoViewer(Activity act, int requestCode, String path)
	{
    	Intent intent = new Intent(act, ActivityVideoViewer.class);
		intent.putExtra(KEY_FULL_PATH, path);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		act.startActivityForResult(intent, requestCode);
	}*/
    
    
    
}
