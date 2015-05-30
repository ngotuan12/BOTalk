package com.br.chat.view;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.br.chat.activity.ActivityCamera;
import com.br.chat.util.WriteFileLog;

public class HPreview extends SurfaceView implements SurfaceHolder.Callback {
	private final String TAG = "HPreview";
	
	protected SurfaceHolder mHolder;
	public Camera mCamera;
	private Context mContext;
	public HPreview(Context context) {
		super(context);
		mContext = context;
//		initSize(context);
		
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		initHolder();
	}
	
	public HPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;		
//		initSize(context);
		
		initHolder();
	}
	
	public void initHolder(){
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void setCameraDensity(int w, int h){
		changeViewLayoutSize(h, w);
		Camera.Parameters parameters = mCamera.getParameters();
		List<Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width,optimalSize.height);
		parameters.setPictureSize(w,h);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}
	
	public void changeViewLayoutSize(int w, int h){
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int width = display.getWidth();  // deprecated
		int height = display.getHeight();  // deprecated
		
		int resizedW_WF = width;
		int resizedH_WF = height;
		int resizedW_HF = width;
		int resizedH_HF = height;
		
		resizedW_WF = width;
		resizedH_WF = width*h/w;
		
		resizedW_HF = height*w/h;
		resizedH_HF = height;
		
		int viewSize_w = 0;
		int viewSize_h = 0;
		if(resizedH_WF<=height){
			viewSize_w = resizedW_WF;
			viewSize_h = resizedH_WF;
		}
		
		if(resizedW_HF<=width){
			viewSize_w = resizedW_HF;
			viewSize_h = resizedH_HF;
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		this.setLayoutParams(params);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		Camera.Parameters parameters = mCamera.getParameters();
		int m_resWidth = 800;
		int m_resHeight = 480;
		List<Camera.Size> sizes = null;
		
        sizes = parameters.getSupportedPictureSizes();
        if(sizes!=null && sizes.size()>0){
        	int index = 0;
        	index = sizes.size()/2;
        	//Camera.Size current_size = sizes.get(sizes.size()-(sizes.size()/2));
        	Camera.Size current_size = sizes.get(sizes.size()-6);
        	m_resWidth = current_size.width;
        	m_resHeight = current_size.height;
        }
        
	    List<String> focusModes = parameters.getSupportedFocusModes();
	    String CAF_PICTURE = Parameters.FOCUS_MODE_CONTINUOUS_PICTURE, 
	           supportedMode = focusModes
	                   .contains(CAF_PICTURE) ? CAF_PICTURE : "";

	    if (!supportedMode.equals("")) {
	    	parameters.setFocusMode(supportedMode);
	    }
        
        parameters.setPictureSize(m_resWidth, m_resHeight);
        parameters.setRotation(getRotationValue());
		mCamera.setParameters(parameters);
        
		try {
			mCamera.setDisplayOrientation(getRotationValue());
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			WriteFileLog.writeException(e);
			mCamera.release();
			mCamera = null;
			// TODO: add more exception handling logic here
		}
	}
	
	public List<Camera.Size> getSupportedPictureSizes() {
	    if (mCamera == null) {
	        return null;
	    }
	    List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();
	    return pictureSizes;
	}
	
	public Camera.Size getCurrentPictureSize() {
	    if (mCamera == null) {
	        return null;
	    }
	    return mCamera.getParameters().getPictureSize();
	}
	 
	private int getRotationValue(){
		int rotation = ((Activity)mContext).getWindowManager().getDefaultDisplay().getRotation();
		//int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

		int degrees = 0;

		switch (rotation) {

		case Surface.ROTATION_0: degrees = 0; break;

		case Surface.ROTATION_90: degrees = 90; break;

		case Surface.ROTATION_180: degrees = 180; break;

		case Surface.ROTATION_270: degrees = 270; break;

		}

		int result  = (90 - degrees + 360) % 360;
		return result;
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}


	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null) return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;
 
		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();

		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		//parameters.setPreviewSize(optimalSize.width, optimalSize.height);
		if(ActivityCamera.isOrientations()){
			//parameters.setPreviewSize(optimalSize.width, optimalSize.height);
			parameters.setPreviewSize(w, h);
			  parameters.setRotation(0);
			  mCamera.setDisplayOrientation(0);
			  //changeViewLayoutSize(optimalSize.width, optimalSize.height);
		}else{
			//parameters.setPreviewSize(optimalSize.height, optimalSize.width);
			parameters.setPreviewSize(h, w);
			parameters.setRotation(90);
			mCamera.setDisplayOrientation(90);
			/*parameters.setPreviewSize(w, h);	
			parameters.setRotation(0);
			mCamera.setDisplayOrientation(0);*/
			 // changeViewLayoutSize(optimalSize.height, optimalSize.width);
		}
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}
}
