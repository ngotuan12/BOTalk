package com.br.chat.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import cn.Ragnarok.BitmapFilter;

import com.bigsoft.camerasdk.util.ImageFilter;
import com.br.chat.ChattingApplication;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.ControlButton;
import com.br.chat.view.DialogVHProgress;
import com.br.chat.view.TouchImageView;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.image.BrImageUtilManager;
import com.chattingmodule.R;

public class ActivityPhotoTool extends Activity 
{
	public static final int IMAGE_WIDTH = 480;
	public static final int IMAGE_HEIGHT = 480;

	public static final int IMAGE_THUMB_WIDTH = 136;
	public static final int IMAGE_THUMB_HEIGHT = 136;

	public static final int IMAGE_THUMB_BOARD_WIDTH = 150;
	public static final int IMAGE_THUMB_BOARD_HEIGHT = 150;

	public static float MAX_WIDTH = 480.0F;

	public static final int STATE_CAMERA = 0;
	public static final int STATE_GALLERY = 1;
	public static final int STATE_CAMERA_BOARD = 2;
	public static final int STATE_GALLERY_BOARD = 3;

	private int mState = STATE_CAMERA;

	// public static final String TEMP_CAPTURE_IMAGE_PATH =
	// android.os.Environment.getExternalStorageDirectory() + "";

	/**
	 * 팝업 정의
	 */
	public static final int SAVE_IMAGE_LOADING = 0;
	public static final int SAVE_IMAGE_NOTI = 1;
	public static final int SAVE_IMAGE_OVERLAP = 2;
	public static final int SAVE_IMAGE_ERROR_REGION = 3;
	public static final int UNMOUNT_SDCARD = 4;

	private static final String TAG = "ActivityPhotoTool";

	public static final String STR_UNMOUNT_SDCARD = "SD 카드가 인식되지 않아 파일을 저장할 수 없습니다.";

	public static final String STR_ALREADY_SAVE_IMAGE = "이미 저장된 이미지입니다.";

	public static final String STR_ERROR_REGION_SAVE_IMAGE = "선택 영역이 이미지 보다 커서 저장할 수 없습니다.\n" + "사진을 확대하여 다시 시도해 주십시오.";

	public static final String STR_NOTI_SAVE_FOLDER = "갤러리에 저장하시겠습니까?\n" + "저장된 이미지는 갤러리에서 확인해 주세요.";

	private ControlButton mControlButton_retry;
	private ControlButton mControlButton_done;
	private TouchImageView mTouchImageView;

	private Uri mUri;

	private String mTempFilePath;

	// 카메라에서 넘어왔을 경우 이미지를 회전하고,
	// 사진 앨범에서 넘어왔을 경우 이미지를 회전하지 않는다.
	// private boolean mbRotate;

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	private int mWidth;
	private int mHeight;

	private float mX;
	private float mY;

	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;

	private Bitmap mGetImage;
	private Bitmap mRotateImage;

	public static int LCD_WIDTH;
	public static int LCD_HEIGHT;
	private LinearLayout mImageBox;
	
	private int RotateValue = 0;
	
	/** Called when the activity is first created. */
	
	String filepath = "",rowPath = "";
	public boolean activity = false;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_photo_tool);
		// TODO Auto-generated method stub

		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		mWidth = display.getWidth();
		mHeight = display.getHeight();
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		LCD_HEIGHT = displaymetrics.heightPixels;
		LCD_WIDTH = displaymetrics.widthPixels;
		
		init();
	}
	
	private Handler mHandler = new Handler();
	private void initFilter(){
		final Bitmap bitmap = mRotateImage;
		mImageBox = (LinearLayout)this.findViewById(R.id.activityPhotoTool_LinearLayout_imageViewContainer);
		
		addFilter("원본", bitmap, 0);
		addFilter("gray", bitmap, BitmapFilter.GRAY_STYLE);
		
		Button filter = new Button(this);
		filter.setTextColor(getResources().getColor(R.color.color_white));
		filter.setText("bright");
		filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showLoading();
				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							mRotateImage = ImageFilter.doBrightness(bitmap, 20);	
						} catch (Exception e) {
							// TODO: handle exception
						}
						mHandler.post(new Runnable() {
		    				public void run() {
		    					mTouchImageView.setImageBitmap(mRotateImage);
		    					hideLoading();
		    				}
		    			});
					}
				};
		        thread.start();
			}
		});
		mImageBox.addView(filter);
		
		filter = new Button(this);
		filter.setTextColor(getResources().getColor(R.color.color_white));
		filter.setText("tint");
		filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showLoading();
				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							mRotateImage = ImageFilter.tintImage(bitmap, 20);	
						} catch (Exception e) {
							// TODO: handle exception
						}
						mHandler.post(new Runnable() {
		    				public void run() {
		    					hideLoading();
		    					mTouchImageView.setImageBitmap(mRotateImage);
		    				}
		    			});
					}
				};
		        thread.start();
			}
		});
		mImageBox.addView(filter);
		
		filter = new Button(this);
		filter.setTextColor(getResources().getColor(R.color.color_white));
		filter.setText("fastblur");
		filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showLoading();
				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							mRotateImage = ImageFilter.fastblur(bitmap, 30);
						} catch (Exception e) {
							// TODO: handle exception
						}
						mHandler.post(new Runnable() {
		    				public void run() {
		    					hideLoading();
		    					mTouchImageView.setImageBitmap(mRotateImage);
		    				}
		    			});
					}
				};
		        thread.start();
			}
		});
		mImageBox.addView(filter);
		
		
		addFilter("relief", bitmap, BitmapFilter.RELIEF_STYLE);
		addFilter("blur", bitmap, BitmapFilter.BLUR_STYLE);
		//addFilter("oil", bitmap, BitmapFilter.OIL_STYLE);
		addFilter("neon", bitmap, BitmapFilter.NEON_STYLE);
		addFilter("pixelate", bitmap, BitmapFilter.PIXELATE_STYLE);
		addFilter("Old TV", bitmap, BitmapFilter.TV_STYLE);
		addFilter("invert", bitmap, BitmapFilter.INVERT_STYLE);
		addFilter("engraving", bitmap, BitmapFilter.BLOCK_STYLE);
		addFilter("old photo", bitmap, BitmapFilter.OLD_STYLE);
		addFilter("sharpen", bitmap, BitmapFilter.SHARPEN_STYLE);
		addFilter("light", bitmap, BitmapFilter.LIGHT_STYLE);
		addFilter("lomo", bitmap, BitmapFilter.LOMO_STYLE);
		addFilter("HDR", bitmap, BitmapFilter.HDR_STYLE);
		//addFilter("gaussian blur", bitmap, BitmapFilter.GAUSSIAN_BLUR_STYLE);
		//addFilter("soft glow", bitmap, BitmapFilter.SOFT_GLOW_STYLE);
		addFilter("sketch", bitmap, BitmapFilter.SKETCH_STYLE);
		
	}
	
	private void addFilter(String title, final Bitmap bitmap, final int style){
		Button filter = new Button(this);
		filter.setTextColor(getResources().getColor(R.color.color_white));
		filter.setText(title);
		filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showLoading();
				Thread thread = new Thread() {
					@Override
					public void run() {
						try {
							if(style==0){
								mRotateImage = bitmap;
							}else{
								mRotateImage = BitmapFilter.changeStyle(bitmap, style);
							}
						} catch (Exception e) {
							WriteFileLog.writeException(e);
						}
						mHandler.post(new Runnable() {
		    				public void run() {
		    					if(style==0){
									mTouchImageView.setImageBitmap(bitmap);
								}else{
									mTouchImageView.setImageBitmap(mRotateImage);
								}
		    					hideLoading();
		    				}
		    			});
					}
				};
		        thread.start();
			}
		});
		
		mImageBox.addView(filter);
	}
	
	private DialogVHProgress mDialogVHProgress;
	public void showLoading(){
		if(mDialogVHProgress==null){
			mDialogVHProgress = new DialogVHProgress(this);
		}
	    	mDialogVHProgress.setCancelable(false);
	    	mDialogVHProgress.show();
	}
	public void hideLoading(){
		if(mDialogVHProgress!=null && mDialogVHProgress.isShowing()) mDialogVHProgress.dismiss();
	}

	private void init()
	{
		initGetIntent();
		//initData();
		initUI();
		//getMediumPath();
		//getRowPath();
		
		
	}
	
	public void getMediumPath(){
		//showLoading();
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					mTempFilePath =  SaveImg(mTempFilePath, true, new File(mTempFilePath).getName(),600,400);
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
				mHandler.post(new Runnable() {
    				public void run() {
    					//hideLoading();
    					//mTempFilePath = rowPath;
    					initImage();
    					initFilter();
    					hideLoading();
    					//initUI();
    					//getRowPath();
    				}
    			});
			}
		};
        thread.start();
		
	}
	public void getRowPath(){
		//showLoading();
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					mTempFilePath =  SaveImg(mTempFilePath, true, new File(mTempFilePath).getName(),300,200);
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
				mHandler.post(new Runnable() {
    				public void run() {
    					initImage();
    					initFilter();
    					hideLoading();
    					//initImage();
    					//initFilter();
    				}
    			});
			}
		};
        thread.start();
		
	}

	String mOriginalFilePath = null;
	private void initGetIntent()
	{
		mState = STATE_CAMERA;
		Intent intent = getIntent();
		
		if(intent==null){
			//자동경로
		}else{
			activity = intent.getBooleanExtra("activity", false);
			filepath = getIntent().getStringExtra(BxmIntent.KEY_TEMP_IMAGE_FILEPATH);
			mTempFilePath = filepath;
			try {
				if(!TextUtils.isEmpty(intent.getStringExtra(BxmIntent.KEY_FULL_PATH))){
					mOriginalFilePath = intent.getStringExtra(BxmIntent.KEY_FULL_PATH);
				}else{
					/*mOriginalFilePath = Utils.getFilePath(this, "myPic.jpg");*/
					mOriginalFilePath = ChattingApplication.getInstance().getFileDir_Ex() + "/tmp_"+ System.currentTimeMillis() + ".jpg";
				}
				//mTempFilePath = mOriginalFilePath;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void initData()
	{
		// if(mState == STATE_GALLERY || mState == STATE_GALLERY_BOARD)
		// {
		// mFilename = setUriToFilename(mUri);
		// }
	}

	private String setUriToFilename(Uri uri)
	{
		String[] proj =
		{ MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		File file = new File(cursor.getString(column_index));

		return file.toString();
	}

	// Bitmap 이미지를 파일로 저장해 둔다.
	private void saveImageFile(Bitmap bm, String filename)
	{
		String imgPath = android.os.Environment.getExternalStorageDirectory() + "/hyy/";

		if (bm != null)
		{
			try
			{
				File file = new File(imgPath + filename);
				OutputStream out = new FileOutputStream(file);
				bm.compress(CompressFormat.PNG, 100, out);
				out.close();

			}
			catch (Exception e)
			{
			}
		}
	}

	private void initImage()
	{
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options = FileTool.getBitmapSize(options);
			mGetImage = BitmapFactory.decodeFile(mTempFilePath, options);
			/*int width = mGetImage.getHeight();
			int height = mGetImage.getWidth();*/
			int width = mGetImage.getWidth();
			int height = mGetImage.getHeight();
			RotateValue = BrImageUtilManager.getInstance().GetExifOrientation(mTempFilePath);
			smallImageResizeCamera(width, height);
			mTouchImageView.setImageBitmap(mRotateImage);	
		} catch (Exception e) {
			e.printStackTrace();
			BrUtilManager.getInstance().ShowDialog1btn(this, null, "사진을 불러 올 수 없습니다. 다시 시도해주세요.", new BrUtilManager.dialogclick() {
				
				@Override
				public void setondialogokclick() {
					finish();
				}
				
				@Override
				public void setondialocancelkclick() {}
			});
		}
		//rowPath =  SaveImg(mTempFilePath, true, new File(mTempFilePath).getName());
		
		
		
	}

	private void smallImageResizeCamera(int width, int height)
	{
		int min = Math.min(width, height);
		float revisionRatio = revisionMin(min);
		float _w = 0;
		float _h = 0;
		_w = width * revisionRatio;
		_h = height * revisionRatio;

		mRotateImage = Bitmap.createScaledBitmap(mGetImage, (int) _w, (int) _h, true);
		mRotateImage = BrImageUtilManager.getInstance().rotate(mRotateImage, RotateValue);
	}

	/**
	 * min 값이 480 이하 일 경우 480으로 변경하고 해당 보정값(비율)을 반환
	 * 
	 * @param min
	 * @return
	 */
	private float revisionMin(int min)
	{
		return (float)mWidth / min;
	}

	private void initUI()
	{
		mControlButton_retry = (ControlButton) findViewById(R.id.activityPhotoTool_ControlButton_reTry);
		mControlButton_done = (ControlButton) findViewById(R.id.activityPhotoTool_ControlButton_btnDone);
		mTouchImageView = (TouchImageView) findViewById(R.id.activityPhotoTool_ToughImageView_viewer);
		Spinner spinner = (Spinner)findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
				this, R.array.camera, R.layout.spinner_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter);	
		mControlButton_done.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
		/*		if(activity){ // 앨범에서 온거라면
					Intent intent = new Intent();
					File file = new File(mTempFilePath);
			        if (file.exists()) 
			        	intent.putExtra(BxmIntent.KEY_FULL_PATH, file.getAbsolutePath());
			        
			        intent.putExtra(BxmIntent.KEY_FINISH_MODE, BxmIntent.FINISH_SAVE);
					setResult(RESULT_OK, intent);
					finish();
					return;
				}*/
				new ProgressWork().execute();
			}
		});

		mControlButton_retry.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent();
		        intent.putExtra(BxmIntent.KEY_FINISH_MODE, BxmIntent.FINISH_RESHOOT);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0: //원본
					showLoading();
					mTempFilePath = filepath;
					initImage();
					initFilter();
					hideLoading();
					break;
				case 1:// 중화질
					//String realpath =  SaveImg(mTempFilePath, true, new File(mTempFilePath).getName());
					showLoading();
					getMediumPath();
				
					break;	
				case 2://저화질
					showLoading();
					getRowPath();
					/*mTempFilePath = filepath;
					initImage();
					initFilter();
					hideLoading();*/
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}
	
	public String SaveImg(String filepath,boolean album,String filename,int width, int height) {
		Bitmap bitmap = BrImageUtilManager.getInstance().getBitmap(filepath, album,width,height);
		File testfile = new File(ChattingApplication.getInstance().getFileDir_Ex() + filename + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(this,bitmap,getImageUri(testfile.getAbsolutePath()));
		if (!flag)
			return "";
		
		return  testfile.getAbsolutePath();
	}
	
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}

	private void successfulyFinish()
	{
		Intent intent = new Intent();
		File file = new File(mOriginalFilePath);
        if (file.exists()) 
        	intent.putExtra(BxmIntent.KEY_FULL_PATH, file.getAbsolutePath());
        
        intent.putExtra(BxmIntent.KEY_FINISH_MODE, BxmIntent.FINISH_SAVE);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void saveImage()
	{
		OutputStream out = null;
		try
		{
			File file = new File(mOriginalFilePath.substring(0, mOriginalFilePath.lastIndexOf("/")));
	        if (!file.exists()) 
	            file.mkdirs();
	        
	        File fileCacheItem = new File(mOriginalFilePath);
	        fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            mRotateImage.compress(CompressFormat.JPEG, 70, out);
		}
		catch (Exception e)
		{
			return;
		}finally {
            try {
            	if(out!=null){
            		out.close();
            		out = null;
            	}
            	if(!activity)
            		removeFile();
            } catch (Exception e) {
                WriteFileLog.writeException(e);
            }
        }
	}
	public void removeFile(){
		File tempFile = new File(mTempFilePath);
        if(tempFile.exists()){
        	tempFile.delete();
        }
        tempFile = null;
	}

	private class ProgressWork extends AsyncTask<String, Void, Void>
	{

		protected void onPreExecute()
		{
			// 작업이 시작되기 직전에 화면에 처리해야 할 작업은 여기에 지정한다.
			showDialog(SAVE_IMAGE_LOADING);
		}

		protected void onPostExecute(Void unused)
		{
			// 백그라운드 작업이 종료된 직후에 화면에 처리해야 할 작업은 여기에 지정한다.
			removeDialog(SAVE_IMAGE_LOADING);

			successfulyFinish();
		}

		@Override
		protected Void doInBackground(String... sendData)
		{
			// TODO Auto-generated method stub

			saveImage();
			return null;
		}

		protected void onCancelled()
		{
			// TODO Auto-generated method stub
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		if (mGetImage != null)
		{
			mGetImage.recycle();
			mGetImage = null;
		}

		if (mRotateImage != null)
		{
			mRotateImage.recycle();
			mRotateImage = null;
		}
	}
	
	
	/**
	 * 업데이트 로딩 팝업
	 */
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case SAVE_IMAGE_LOADING:
			return dialogSaveImageLoading();

		case SAVE_IMAGE_NOTI:
			return notiSaveFile();

		case SAVE_IMAGE_OVERLAP:
			return alreadySaveImage();

		case UNMOUNT_SDCARD:
			return unMountSDCard();

		case SAVE_IMAGE_ERROR_REGION:
			return errorRegionSaveImage();
		}

		return null;
	}
	
	private ProgressDialog dialogSaveImageLoading()
	{
		ProgressDialog saveImage = new ProgressDialog(this);
		saveImage.setMessage(getString(R.string.file_saving));
		saveImage.setIndeterminate(true);
		saveImage.setCancelable(false);

		return saveImage;
	}

	private AlertDialog unMountSDCard()
	{
		AlertDialog my = new AlertDialog.Builder(this).setTitle(R.string.noti).setMessage(STR_UNMOUNT_SDCARD)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).create();

		return my;
	}

	private AlertDialog alreadySaveImage()
	{
		AlertDialog my = new AlertDialog.Builder(this).setTitle(R.string.noti).setMessage(STR_ALREADY_SAVE_IMAGE)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).create();

		return my;
	}

	private AlertDialog errorRegionSaveImage()
	{
		AlertDialog my = new AlertDialog.Builder(this).setTitle(R.string.noti).setMessage(STR_ERROR_REGION_SAVE_IMAGE)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).create();

		return my;
	}
	
	private AlertDialog notiSaveFile()
	{
		AlertDialog my = new AlertDialog.Builder(this).setTitle(R.string.noti).setMessage(STR_NOTI_SAVE_FOLDER)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						new ProgressWork().execute("saveImage");
					}
				}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				}).create();

		return my;
	}
	
}
