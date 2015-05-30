package com.br.chat.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.br.chat.ChattingApplication;
import com.br.chat.util.MediaScanning;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.ControlButton;
import com.br.chat.view.DialogVHProgress;
import com.br.chat.view.HPreview;
import com.br.chat.view.ImageButtonIconText;
import com.br.chat.vo.CameraDensity;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.brainyxlib.image.BrImageUtilManager;
import com.chattingmodule.R;

public class ActivityCamera extends Activity {

	public static String TEMP_CAPTURE_IMAGE_PATH;

	private HPreview mPreview;
	private ImageButtonIconText mBtnTakePic;
	private ImageButtonIconText mBtnGoCrop;
	private ImageButtonIconText mCancel;
	private ImageButtonIconText mRetry;
	private ControlButton mControlButton_density;
	
	private File mFile;
	
	String mOriginalFilePath = null;
	static boolean isOrientation = false;
	public static  boolean isOrientations(){
		return isOrientation;
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_camera);
		
		//TEMP_CAPTURE_IMAGE_PATH = Utils.getFilePath(this, "tempPic.jpg");
		TEMP_CAPTURE_IMAGE_PATH = ChattingApplication.getInstance().getFileDir_Ex() + "/tmp_"+ System.currentTimeMillis() + ".jpg";
		String intentPath = null;
	    try {
	    	
	    	Intent intent = getIntent();
	    	if(intent!=null && !TextUtils.isEmpty(intent.getStringExtra(BxmIntent.KEY_FULL_PATH))){
	    		intentPath = intent.getStringExtra(BxmIntent.KEY_FULL_PATH);	
	    	}
		    
		} catch (Exception e) {
			intentPath = null;
		}
	    mOriginalFilePath = FileTool.getCameraFilename(intentPath);

		mPreview = (HPreview) findViewById(R.id.preview);

		mBtnTakePic = (ImageButtonIconText) findViewById(R.id.imageButtonCamera);
		mBtnTakePic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPreview.mCamera.autoFocus(new Camera.AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						camera.takePicture(null, null, jpegCallback);
						setLayout(CAMERA_PREVIEW);
					}
				});
			}
		});
		
		mBtnGoCrop = (ImageButtonIconText) findViewById(R.id.buttonOk);
		mBtnGoCrop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveImage();
			}
		});
		
		mCancel = (ImageButtonIconText) findViewById(R.id.buttonCancel);
		mCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		mRetry = (ImageButtonIconText) findViewById(R.id.imageButtonRetry);
		mRetry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setLayout(CAMERA_READY);
			}
		});
		
		mControlButton_density = (ControlButton) findViewById(R.id.activityCamera_ControlButton_density);
		mControlButton_density.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getDensityDialog();
			}
		});
		
		setLayout(CAMERA_READY);
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					WriteFileLog.writeException(e);
				}
				
				mHandler.post(new Runnable() {
    				public void run() {
    					Camera.Size size = mPreview.getCurrentPictureSize();
    					mControlButton_density.mTextView.setText(size.width + " X " + size.height);
    					mPreview.changeViewLayoutSize(size.height, size.width);
    				}
    			});
			}
		};
        thread.start();
	}
	
	
	@SuppressLint("NewApi")
	private void getDensityDialog(){
		List<String> textList = new ArrayList<String>();
		final List<CameraDensity> listItems = new ArrayList<CameraDensity>();
		
		List<Camera.Size> cameraSizeList = mPreview.getSupportedPictureSizes();
		Camera.Size size;
		int selectedPosition = 0;
		Camera.Size currentCameraSize = mPreview.getCurrentPictureSize();
		for(int i=0;i<cameraSizeList.size();i++){
			size = cameraSizeList.get(i);
        	listItems.add(new CameraDensity(size.width, size.height));
        	textList.add(size.width+ " X " + size.height);
        	if(size.width == currentCameraSize.width && size.height == currentCameraSize.height){
        		selectedPosition = i;
        	}
		}
		
		final CharSequence[] cameraDensities = textList.toArray(new CharSequence[textList.size()]);
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
        	alt_bld = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		}else{
			alt_bld = new AlertDialog.Builder(this);
		}
        
        alt_bld.setTitle(R.string.changeDensity);
        alt_bld.setSingleChoiceItems(cameraDensities, selectedPosition, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               // Toast.makeText(getApplicationContext(), "Phone Model = "+cameraDensities[item], Toast.LENGTH_SHORT).show();
                
                mControlButton_density.mTextView.setText(cameraDensities[item]);
               	mPreview.setCameraDensity(listItems.get(item).getW(), listItems.get(item).getH());	
                dialog.dismiss();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
	}
	
	private final int CAMERA_PREVIEW = 0;
	private final int CAMERA_READY = 1;
	private void setLayout(int select)
	{
		if(select == CAMERA_PREVIEW)
		{
		
			mBtnTakePic.setVisibility(View.GONE);
			mBtnGoCrop.setVisibility(View.VISIBLE);
			mRetry.setVisibility(View.VISIBLE);
			mControlButton_density.setEnabled(false);
		}
		else
		{
			try {
				mPreview.mCamera.startPreview();				
			} catch (Exception e) {
				e.printStackTrace();
			}
			mBtnTakePic.setVisibility(View.VISIBLE);
			mBtnGoCrop.setVisibility(View.GONE);
			mRetry.setVisibility(View.GONE);
			mControlButton_density.setEnabled(true);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i("HYY", "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
		   if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) // 세로 전환시
	        { 
			   isOrientation = false;
	        } 
	        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)// 가로 전환시
	        { 
	        	isOrientation = true;
	        }
	}

	PictureCallback jpegCallback = new PictureCallback() 
	{  
		public void onPictureTaken(byte[] data, Camera camera)
		{
			FileOutputStream outStream = null;
			FileInputStream   inputStream = null;
			
			File outputPath = new File(TEMP_CAPTURE_IMAGE_PATH.substring(0, TEMP_CAPTURE_IMAGE_PATH.lastIndexOf("/")));
			
			if(!outputPath.exists())
			{
				outputPath.mkdir();
			}
			
			try 
			{ 
				outStream = new FileOutputStream(TEMP_CAPTURE_IMAGE_PATH);
				outStream.write(data);
				
				Log.d("HYY", "onPictureTaken - wrote bytes: " + data.length);  

				mFile = new File(TEMP_CAPTURE_IMAGE_PATH);
				
				inputStream = new FileInputStream(mFile);
				
			} 
			catch(FileNotFoundException e)
			{
				WriteFileLog.writeException(e);
			}
			catch (IOException e) 
			{
				WriteFileLog.writeException(e);
			} 
			finally
			{
				try {
					if(outStream!=null){
						outStream.close();
						outStream = null;
					}
					
					if(inputStream!=null){
						inputStream.close();
						inputStream = null;
					}

					try {
						mPreview.mCamera.stopPreview();				
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			Log.d("HYY", "onPictureTaken - jpeg");
		}
	};
	
	public String SaveImg(String filepath,boolean album,String filename,Bitmap bitmap) {
		bitmap = BrImageUtilManager.getInstance().getBitmap(filepath, album,1200,800);
		String myseq = SharedManager.getInstance().getString(ActivityCamera.this, BaseGlobal.User_Seq);
		File testfile = new File(ChattingApplication.getInstance().getFileDir_Ex() + filename + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(this,bitmap,getImageUri(testfile.getAbsolutePath()));
		if (!flag)
			return "";
		
		return  testfile.getAbsolutePath();
	}
	
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}
	
	public  void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath,
            String filename) {
		//Mylog.e("","strFilePath: " + strFilePath);
		//Mylog.e("","filename: " + filename);
		Bitmap rotatebitmap = null;
		if(!isOrientation){
			rotatebitmap = BrImageUtilManager.getInstance().rotate(bitmap, 90);
		}else{
			rotatebitmap = bitmap;
		}
		File fileCacheItem = new File(strFilePath , filename);
	
        File file = new File(strFilePath);
 
        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
 
        //fileCacheItem = new File(strFilePath , filename);
        OutputStream out = null;
 
        try {
            //fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
 
            rotatebitmap.compress(CompressFormat.JPEG, 100, out);
            
        	//mFile = new File( SaveImg(fileCacheItem.getAbsolutePath(),false , filename,rotatebitmap));
            
        } catch (Exception e) {
            WriteFileLog.writeException(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                WriteFileLog.writeException(e);
            }
        }
    }

	PictureCallback rawCallback = new PictureCallback() 
	{
		public void onPictureTaken(byte[] data, Camera camera) 
		{
			Log.d("HYY", "onPictureTaken - raw");  
		}
	};

	ShutterCallback shutterCallback = new ShutterCallback() 
	{
		public void onShutter() 
		{
			Log.d("HYY", "onShutter'd");  
		}
	};

	private DialogVHProgress mDialogVHProgress;
	public void showTitleLoadingDialog(){
		if(mDialogVHProgress==null){
			mDialogVHProgress = new DialogVHProgress(this);
		}
	    	mDialogVHProgress.setCancelable(false);
	    	mDialogVHProgress.show();
	}
	public void hideTitleLoadingDialog(){
		if(mDialogVHProgress!=null && mDialogVHProgress.isShowing()) mDialogVHProgress.dismiss();
	}
	
	private Handler mHandler = new Handler();
	private void saveImage()
	{
		showTitleLoadingDialog();
		Thread thread = new Thread() {
			@Override
			public void run() {
				Bitmap getImage = null;
				try {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					options = FileTool.getBitmapSize(options);
					
					getImage = BitmapFactory.decodeFile(mFile.toString(), options);
					
					String folder = mFile.toString();
					folder = folder.substring(0, folder.lastIndexOf("/"));
					String fileName = mFile.toString();
					fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
					SaveBitmapToFileCache(getImage, folder, fileName);
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}finally{
					try {
						if(getImage!=null){
							getImage.recycle();
							getImage = null;
						}	
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				mHandler.post(new Runnable() {
    				public void run() {
    					hideTitleLoadingDialog();
    					if(mFile==null)
        					return;
    					
    					/*Intent intent = new Intent();
        				intent.putExtra(BxmIntent.KEY_TEMP_IMAGE_FILENAME, mFile.toString());
        				setResult(RESULT_OK, intent);*/
    					
        				cameraFilteringProcess(mFile.toString(), mOriginalFilePath);
        				
    				}
    				
    			});
			}
		};
        thread.start();
		
	}
	
	public void cameraFilteringProcess(String tempFilePath, String originalFilePath)
	{
		if(TextUtils.isEmpty(tempFilePath))
		{
			return;
		}
		BxmIntent.goCorpImageCameraForResult(ActivityCamera.this, BxmIntent.GO_CROP_IMAGE, tempFilePath, originalFilePath);
	}
	
	private void success(Intent data){
		Intent intent = new Intent();
		if(data != null){
			intent.putExtra(BxmIntent.KEY_FULL_PATH, data.getStringExtra(BxmIntent.KEY_FULL_PATH));
			try {
				MediaScanning scanning = new MediaScanning(this, new File(data.getStringExtra(BxmIntent.KEY_FULL_PATH)));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(intent==null) {
			finish();
			return;
		}
    	switch(requestCode){
			case BxmIntent.GO_CROP_IMAGE:
				if(resultCode != RESULT_OK){
					finish();
					return;
				}

				int finishMode = intent.getIntExtra(BxmIntent.KEY_FINISH_MODE, 0);
				if(finishMode==BxmIntent.FINISH_RESHOOT){
					setLayout(CAMERA_READY);
				}else if(finishMode==BxmIntent.FINISH_SAVE){
					success(intent);	
				}else{
					finish();
				}
				
				break;
    	}
	}
}