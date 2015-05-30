package com.br.chat.activity;

import java.io.File;

import android.graphics.BitmapFactory.Options;
import android.media.MediaRecorder;
import android.os.Environment;

public class FileTool {
	private static final int currentFormat = 0;
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String CAMERA_FILE_EXT_MP4 = ".jpg";
	private static int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
	private static String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
	private static final String FOLDER = "Multimedia";
	public static String getSoundRecorderFilename(String path) {
		return getPathWithExt(path, file_exts[currentFormat]);
	}
	
	public static String getCamRecorderFilename(String path) {
		return getPathWithExt(path, AUDIO_RECORDER_FILE_EXT_MP4);
	}
	
	public static String getCameraFilename(String path) {
		return getPathWithExt(path, CAMERA_FILE_EXT_MP4);
	}
	
	private static String getPathWithExt(String path, String ext){
		String filepath = null;
		File file = null;
	    if(path==null || path.length()<=0){
	    	filepath = Environment.getExternalStorageDirectory().getPath();
	    	file = new File(filepath, FOLDER);
	    }else{
	    	filepath = path.substring(0, path.lastIndexOf("/"));
	    	file = new File(filepath);
	    }
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	    
	    if(path==null || path.length()<=0)
	    	return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ext);
	    else
	    	return (file.getAbsolutePath() + "/" + path.substring(path.lastIndexOf("/") + 1, path.length()) + ext);
	}
	
	
	
	public static int getOutputFormats(){
		return output_formats[currentFormat];
	}
	
	public static final String TEMP_CAPTURE_IMAGE_PATH	=	android.os.Environment.getExternalStorageDirectory() + "/hyy/";
	
	private final static int MAX_IMG_LONG = 1280;
	private final static int MAX_IMG_SHORT = 720;
	public static Options getBitmapSize(Options options){
        int targetWidth = 0;
        int targetHeight = 0;
          
       
        targetWidth = options.outWidth;
        targetHeight = options.outHeight;
        
        float sizeRatio=0;
        
        if(options.outWidth > options.outHeight){    
//            targetWidth = (int)(600 * 1.3);
//            targetHeight = 600;
            if(options.outWidth>MAX_IMG_LONG){
	            targetWidth = MAX_IMG_LONG;
	            sizeRatio =(float) targetWidth/options.outWidth;
	            
	            targetHeight = (int)(options.outHeight*sizeRatio);
            }
        }else{
//            targetWidth = 600;
//            targetHeight = (int)(600 * 1.3);
//           
        	if(options.outHeight>MAX_IMG_LONG){
	            targetWidth = MAX_IMG_SHORT;
	            sizeRatio =(float) targetWidth/options.outWidth;            
	            targetHeight = (int)(options.outHeight*sizeRatio);
        	}
        }
  
      //  Mylog.e("", "getBitmapSize changeW: "+targetWidth+", changeH: "+targetHeight);
      //  Mylog.e("", "getBitmapSize sizeRatio: "+sizeRatio);
        
        Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth - targetWidth);
        
        if(options.outHeight * options.outWidth * 2 >= 16384){
            double sampleSize = scaleByHeight
                ? (double)options.outHeight / targetHeight
                : (double)options.outWidth / targetWidth;
      //      Mylog.e("", "getBitmapSize sampleSize: "+sampleSize);
            options.inSampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize)/Math.log(2d)));
        }
        
      //  Mylog.e("", "getBitmapSize options.inSampleSize: "+options.inSampleSize);
        
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16*1024];
      
        return options;
	}
}
