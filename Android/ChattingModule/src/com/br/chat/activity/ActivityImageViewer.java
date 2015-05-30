package com.br.chat.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.chat.ChatGlobal;
import com.br.chat.util.Mylog;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.ImageLoaderInterface;
import com.br.chat.view.ImgViewTouch;
import com.br.chat.view.TouchImageViewClickListener;
import com.br.chat.vo.MessageVo;
import com.chattingmodule.R;

public class ActivityImageViewer extends Activity implements ImageLoaderInterface{
	public final String TAG = "ActivityImageViewer";
//	private ImgViewTouch mImgViewTouch;
	
	public final static String TITLE = "TITLE";
	public final static String PATH = "PATH";
	public final static String POSITION = "POSITION";
	public static ArrayList<MessageVo> messageArray ;
	ViewPager mViewPager;
	 RelativeLayout titleBox;
	public static void setMessage(ArrayList<MessageVo> _messageArray){
		messageArray = _messageArray;
	}
	
	//
	ImageView image ;
	TextView name,day;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_viewer);
        
        final ImageView imageView = (ImageView)findViewById(R.id.activityImageViewer_ImageView_close);
        imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        findViewById(R.id.gridviewmore).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityImageViewer.this, Activity_GridViewer.class);
				Activity_GridViewer.setMessage(messageArray);
				startActivity(intent);
				finish();
			}
		});
        
         image = (ImageView)findViewById(R.id.image);
         name = (TextView)findViewById(R.id.name);
         day = (TextView)findViewById(R.id.day);
         
         titleBox = (RelativeLayout)findViewById(R.id.activityImageViewer_RelativeLayout_titleBox);
         mViewPager = (ViewPager) findViewById(R.id.pager);
         mViewPager.setAdapter(new BkPagerAdapter(this));
         mViewPager.setOffscreenPageLimit(3);		
	
         mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				setViewerInfo(arg0);
			}
		});
    /*    mImgViewTouch = (ImgViewTouch)findViewById(R.id.activityImageViewer_ImgViewTouch);
        mImgViewTouch.setTouchImageViewClickListener(new TouchImageViewClickListener(){
			@Override
			public void clicked() {
				if(titleBox.getVisibility()==View.VISIBLE){
					titleBox.setVisibility(View.GONE);
				}else{
					titleBox.setVisibility(View.VISIBLE);
				}
			}
		});*/
        
        try {
        	Intent intent = getIntent();
    		int position = intent.getIntExtra(POSITION,0);
    	//	String title = intent.getStringExtra(TITLE);
    		mViewPager.setCurrentItem(position);
    		setViewerInfo(position);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void setViewerInfo(int position){
		try {
			name.setText(messageArray.get(position).getSendname());
			
			  SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
		        Date dateNew = sdf.parse(messageArray.get(position).getMsgregdate());
		        
		        SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy년 MM월 dd일");
				String dateString = format1.format(dateNew);
		   	 	
			day.setText(dateString);
			imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(messageArray.get(position).getSendseq()),image,imageLoaderOption, animationListener);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	private class BkPagerAdapter extends PagerAdapter{
		private Context mContext;
		public BkPagerAdapter( Context con) { super(); mContext = con; }

		@Override public int getCount() { return messageArray.size(); }	

		
		@Override public Object instantiateItem(View pager, int position) 
		{
			ImgViewTouch iv = null;
			iv = new ImgViewTouch(mContext);
			imageLoader.displayImage(messageArray.get(position).getMsg(),iv,imageLoaderOption, animationListener);
			//myApp.getImageLoader().displayImage(imglist.get(position), iv, myApp.getImageOptions());
			//iv.setScaleType(ScaleType.FIT_XY);
			((ViewPager)pager).addView(iv, 0);
			
			  iv.setTouchImageViewClickListener(new TouchImageViewClickListener(){
					@Override
					public void clicked() {
						if(titleBox.getVisibility()==View.VISIBLE){
							titleBox.setVisibility(View.GONE);
						}else{
							titleBox.setVisibility(View.VISIBLE);
						}
					}
				});
			
			return iv;
		}

		@Override public void destroyItem(View pager, int position, Object view) {
			((ViewPager)pager).removeView((View)view);
		}

		@Override public boolean isViewFromObject(View view, Object obj) { return view == obj; }

		@Override public void finishUpdate(View arg0) {}
		@Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
		@Override public Parcelable saveState() { return null; }
		@Override public void startUpdate(View arg0) {}
	}
	
	int mTargetOrgWidth;
	int mTargetOrgHeight;
	private Bitmap mDefatultBitmap;
	private void setCropAndSetimage(String imagePath){
    	Mylog.e(TAG,"imagePath:" + imagePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        mTargetOrgWidth = options.outWidth;
        mTargetOrgHeight = options.outHeight;
        Mylog.e(TAG, "setCropAndSetimage !! result 0 w: "+mTargetOrgWidth+", h:"+mTargetOrgHeight);
        
        Mylog.e(TAG, "setCropAndSetimage mDefatultBitmap1 count: "+mDefatultBitmap);
        options = getBitmapSize(options);
        mDefatultBitmap = BitmapFactory.decodeFile(imagePath, options);
        
        if(mDefatultBitmap == null){
//        	finish();
        	Toast.makeText(this, "이미지를 불러오지 못 했습니다. 다시 불러오시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }
        else{
	        Mylog.e(TAG, "setCropAndSetimage mDefatultBitmap2 count: "+mDefatultBitmap);
	        Mylog.e(TAG, "setCropAndSetimage !! result 1 w: "+mDefatultBitmap.getWidth()+", h:"+mDefatultBitmap.getHeight());
	        mDefatultBitmap = resizeBitmapImage(mDefatultBitmap);
	        Mylog.e(TAG, "setCropAndSetimage !! result 2 w: "+mDefatultBitmap.getWidth()+", h:"+mDefatultBitmap.getHeight());
	        
	      
	        
	        
	      //회전값 가져오기
	        int exifDegree = 0;
	        try {
	        	ExifInterface exif = new ExifInterface(imagePath);
	        	 int exifOrientation  = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	        	 exifDegree = exifOrientationToDegrees(exifOrientation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				WriteFileLog.writeException(e);
			}
	        if(exifDegree>0){
	        	Mylog.e(TAG, "setCropAndSetimage mDefatultBitmap1 exifDegree: "+exifDegree);
	        	mDefatultBitmap = rotate(mDefatultBitmap, exifDegree);
	        	if(exifDegree==90 || exifDegree==270){
	        		
	        	}
	        }
	        
	        Mylog.e(TAG, "setCropAndSetimage !! result 3 w: "+mDefatultBitmap.getWidth()+", h:"+mDefatultBitmap.getHeight());
	        //mImgViewTouch.setImageBitmap(mDefatultBitmap);
        }
	}
	
	public int exifOrientationToDegrees(int exifOrientation)
	{
	  if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
	  {
	    return 90;
	  }
	  else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
	  {
	    return 180;
	  }
	  else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
	  {
	    return 270;
	  }
	  return 0;
	}
	
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor  = null;
        String returnStr = null;

        try {
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);

            if (null != cursor && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                returnStr = cursor.getString(column_index);
                cursor.close();
                cursor = null;
            }
        }catch(Exception e){
            WriteFileLog.writeException(e);
        }finally{
            if(null != cursor){
                cursor.close();
            }
        }
        return returnStr;
    }
    
    final int MAX_IMG_LONG = 1280;
	final int MAX_IMG_SHORT = 720;
    public Options getBitmapSize(Options options){
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
  
        Mylog.e(TAG, "getBitmapSize changeW: "+targetWidth+", changeH: "+targetHeight);
        Mylog.e(TAG, "getBitmapSize sizeRatio: "+sizeRatio);
        
        Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth - targetWidth);
        
        if(options.outHeight * options.outWidth * 2 >= 16384){
            double sampleSize = scaleByHeight
                ? (double)options.outHeight / targetHeight
                : (double)options.outWidth / targetWidth;
            Mylog.e(TAG, "getBitmapSize sampleSize: "+sampleSize);
            options.inSampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize)/Math.log(2d)));
        }
        
        Mylog.e(TAG, "getBitmapSize options.inSampleSize: "+options.inSampleSize);
        
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16*1024];
      
        return options;
	}
	
	public Bitmap resizeBitmapImage(Bitmap source)
	{
	        int width = source.getWidth();
	        int height = source.getHeight();
	        int newWidth = width;
	        int newHeight = height;
	        float rate = 0.0f;
	        
	        if(width > height)
	        {
	                if(MAX_IMG_LONG < width)
	                {
	                        rate = MAX_IMG_LONG / (float) width;
	                        newHeight = (int) (height * rate);
	                        newWidth = MAX_IMG_LONG;
	                }
	                
	        }else
	        {
	                if(MAX_IMG_LONG < height)
	                {
	                        rate = MAX_IMG_LONG / (float) height;
	                        newWidth = (int) (width * rate);
	                        newHeight = MAX_IMG_LONG;
	                }
	        }
	        
	        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
	}
	
	public Bitmap rotate(Bitmap bitmap, int degrees)
	{
	  if(degrees != 0 && bitmap != null)
	  {
	    Matrix m = new Matrix();
	    m.setRotate(degrees, (float) bitmap.getWidth() / 2, 
	    (float) bitmap.getHeight() / 2);
	    
	    try
	    {
	      Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
	      bitmap.getWidth(), bitmap.getHeight(), m, true);
	      if(bitmap != converted)
	      {
	        bitmap.recycle();
	        bitmap = converted;
	      }
	    }
	    catch(OutOfMemoryError ex)
	    {
	    	ex.printStackTrace();
	    }
	  }
	  return bitmap;
	}
}
