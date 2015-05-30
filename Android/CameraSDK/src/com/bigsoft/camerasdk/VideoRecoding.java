package com.bigsoft.camerasdk;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoRecoding implements SurfaceHolder.Callback {
	
	
    public Context mContext;
    public Activity mActivity;
   

	private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    public MediaRecorder mrec = new MediaRecorder();    
    private Camera mCamera;
    private static  String filename;
    private static String path;
    
	public VideoRecoding(Context context, Activity activity){
		
		mContext = context ;
		mActivity = activity;
		
	}
	
	/**
	 * 현재 파일 위치 
	 * @return
	 */
	public static String getFilePath()
	{
		return path+filename;
	}
	

    public void startRecording() throws IOException
    {

    	
    	surfaceView = (SurfaceView) mActivity.findViewById(R.id.surface_camera);
    
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        if(mCamera==null)
            mCamera = Camera.open();
        
        
         path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
         
         Date date=new Date();
         filename="/rec"+date.toString().replace(" ", "_").replace(":", "_")+".mp4";
         
         //create empty file it must use
         File file = new File(path,filename);
         
        mrec = new MediaRecorder(); 

        mCamera.lock();
        mCamera.unlock();

        // Please maintain sequence of following code. 

        // If you change sequence it will not work.
        mrec.setCamera(mCamera);    
        mrec.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mrec.setAudioSource(MediaRecorder.AudioSource.MIC);     
        mrec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mrec.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mrec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setOutputFile(path+filename);
        mrec.prepare();
        mrec.start();

        
    }

    protected void stopRecording() {

        if(mrec!=null)
        {
            mrec.stop();
            mrec.release();
            mCamera.release();
            mCamera.lock();
        }
    }

    private void releaseMediaRecorder() {

        if (mrec != null) {
            mrec.reset(); // clear recorder configuration
            mrec.release(); // release the recorder object
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {      

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {       

        if (mCamera != null) {
            Parameters params = mCamera.getParameters();
            mCamera.setParameters(params);
            Log.i("Surface", "Created");
        }
        else {
           
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();       

    }
     
}

