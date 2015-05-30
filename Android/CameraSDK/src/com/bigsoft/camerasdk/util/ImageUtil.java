package com.bigsoft.camerasdk.util;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImageUtil {
	
	 public static void setImagePit(ImageView iv) {
		 
		final int WIDTH = 0;
		final int HEIGHT = 1;
			
         float[] value = new float[9];
         
         Matrix matrix = iv.getImageMatrix();
         
         matrix.getValues(value);

         int width = iv.getWidth();
         int height = iv.getHeight();

         
         Drawable d = iv.getDrawable();
         if (d == null)
                 return;
         int imageWidth = d.getIntrinsicWidth();
         int imageHeight = d.getIntrinsicHeight();
         int scaleWidth = (int) (imageWidth * value[0]);
         int scaleHeight = (int) (imageHeight * value[4]);

        
         value[2] = 0;
         value[5] = 0;

         int target = WIDTH;
         if (imageWidth < imageHeight)
                 target = HEIGHT;
         
        
         if (target == WIDTH)
                 value[0] = value[4] = (float) width / imageWidth;
         if (target == HEIGHT)
                 value[0] = value[4] = (float) height / imageHeight;

         scaleWidth = (int) (imageWidth * value[0]);
         scaleHeight = (int) (imageHeight * value[4]);
         if (scaleWidth > width)
                 value[0] = value[4] = (float) width / imageWidth;
         if (scaleHeight > height)
                 value[0] = value[4] = (float) height / imageHeight;
         
      
         scaleWidth = (int) (imageWidth * value[0]);
         scaleHeight = (int) (imageHeight * value[4]);
         if (scaleWidth < width) {
                 value[2] = (float) width / 2 - (float) scaleWidth / 2;
         }
         if (scaleHeight < height) {
                 value[5] = (float) height / 2 - (float) scaleHeight / 2;
         }

         matrix.setValues(value);
         
         
        
         iv.setImageMatrix(matrix);
	 }
	 
	 
	 
    public static String getRealPathFromURI(Activity activity, Uri contentUri){
		String []proj = {MediaStore.Images.Media.DATA};
		Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
    
    

	
	public static int exifOrientationToDegrees(int exifOrientation)
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
	
	
	/**
	 *
	 * @param selPhotoPath
	 * @param selPhoto
	 * @return
	 */
	public static Bitmap bitmapRotate(String selPhotoPath, Bitmap selPhoto)
	{
		
		try {
			 ExifInterface exif = new ExifInterface(selPhotoPath);
			 int exifOrientation = exif.getAttributeInt(
					 ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
					 int exifDegree = exifOrientationToDegrees(exifOrientation);
					 selPhoto = rotate(selPhoto, exifDegree);	
					 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		 
		
		return selPhoto;
	}
	
	
	/**
	 * 휴대폰 방향에 따라서 이미지 bitmap 회전 
	 * @param bitmap
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotate(Bitmap bitmap, int degrees)
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
	      
	    }
	  }
	  return bitmap;
	}
	

	/**
	 * 이미지 축소 
	 * @param context
	 * @param imgFilePath
	 * @return
	 * @throws Exception
	 * @throws OutOfMemoryError
	 */
	public static Bitmap loadBackgroundBitmap(Context context, String imgFilePath) throws Exception, OutOfMemoryError { 


	    Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    int displayWidth = display.getWidth();
	    int displayHeight = display.getHeight();
	 
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inPreferredConfig = Config.RGB_565;
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imgFilePath, options);
	 
	    float widthScale = options.outWidth / displayWidth;
	    float heightScale = options.outHeight / displayHeight;
	    float scale = widthScale > heightScale ? widthScale : heightScale;
	            
	    if(scale >= 8) {
	        options.inSampleSize = 8;
	    } else if(scale >= 6) {
	        options.inSampleSize = 6;
	    } else if(scale >= 4) {
	        options.inSampleSize = 4;
	    } else if(scale >= 2) {
	        options.inSampleSize = 2;
	    } else {
	        options.inSampleSize = 1;
	    }
	    options.inJustDecodeBounds = false;
	 
	    return BitmapFactory.decodeFile(imgFilePath, options);
	}
	
	
	
}
