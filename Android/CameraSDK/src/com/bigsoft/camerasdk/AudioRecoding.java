package com.bigsoft.camerasdk;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class AudioRecoding  {
	
	private static final String LOG_TAG = "AudioRecord";
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
  

	public AudioRecoding(String filename){
		
		 
		 if(filename==null)
		 { 
			 mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
			 mFileName += "/audiorecordtest.3gp";
		 } else {
			 mFileName = filename;
		 }
	    
	}
	public String isFilepath()
	{
		return mFileName;
		
	}
	public boolean isFile()
	{

		File file = new File(mFileName);
		if(file.exists()) {
			return true;
		}
			
		return false;
		
	}
	public boolean isRecordFile()
	{
		mPlayer = new MediaPlayer();
	    try {
	            mPlayer.setDataSource(mFileName);
	            return true;
	    } catch (IOException e) {
	         return false;
	    }
	}
    public void onPause() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    
    
    /**
     * 레코딩 시작 
     */
	public void startRecording() {
		
		stopRecording();
		
		 mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFile(mFileName);
    
        if (Build.VERSION.SDK_INT >= 10) {
        	mRecorder.setAudioSamplingRate(44100);
        	mRecorder.setAudioEncodingBitRate(96000);
        	mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        } else {
            // older version of Android, use crappy sounding voice codec
        	mRecorder.setAudioSamplingRate(8000);
        	mRecorder.setAudioEncodingBitRate(12200);
        	mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        }
        
        
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

	public void stopRecording() {
		if(mRecorder!=null){
			 mRecorder.stop();
		     mRecorder.release();
		     mRecorder = null;
		}
       
    }

	public void startPlaying() {
	        mPlayer = new MediaPlayer();
	        
	        try {
	            mPlayer.setDataSource(mFileName);
	            mPlayer.prepare();
	            mPlayer.start();
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "prepare() failed");
	        }
	}


	public void startPlaying(Context context, Uri uri) {
	        mPlayer = new MediaPlayer();
	        
	        try {
	        	mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	            mPlayer.setDataSource(context, uri);
	            mPlayer.prepare();
	            mPlayer.start();
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "prepare() failed");
	        }
	}
	
	public int getDuration() {
		if (mPlayer != null) {
			return mPlayer.getCurrentPosition();
		} 
		return 0;
	}
	
	
	public void startWavPlaying(Context context, String name) {
        mPlayer = new MediaPlayer();
        try {
        	String packageName = context.getPackageName(); 
        	int resID = context.getResources().getIdentifier( name , "raw" , packageName ); //in res/raw folder I have filename.wav audio file
        	
        	AssetFileDescriptor afd = context.getResources().openRawResourceFd(resID);
        	mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        	
             //mPlayer.setDataSource(context,  Uri.parse("android.resource://"+context.getPackageName()+"/res/raw/" + name));
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
	}

	
	public void stopPlaying() {
		if (mPlayer != null) {
			mPlayer.release();
	        mPlayer = null;
		} 
	}
	
	 public double getAmplitude() {
         if (mRecorder != null)
                 return  mRecorder.getMaxAmplitude();
         else
                 return 0;

	 }
}

