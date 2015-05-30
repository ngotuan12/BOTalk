package com.brainyxlib.image;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

public class BrImageUtilManager {

	public final String TAG = "BrImageUtilManager";
	private static BrImageUtilManager instance = null;

	public synchronized static BrImageUtilManager getInstance() {
		if (instance == null) {
			synchronized (BrImageUtilManager.class) {
				if (instance == null) {
					instance = new BrImageUtilManager();
				}
			}
		}
		
		return instance;
	}

	/**
	 * 이미지 돌아간지 확인
	 * 
	 * @param filepath
	 * @return
	 */
	public synchronized int GetExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);

			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;

				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;

				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	/**
	 * 
	 * @param b
	 * @param degrees
	 * @return
	 */
	public Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth(), (float) b.getHeight());
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	/*
	 * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that are tolerable in terms of memory usage.
	 * 
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 */

	public Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, boolean scaleUp) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b1 != source) {
			b1.recycle();
		}

		return b2;
	}
	
	
	/**
	 * 
	 * @param c
	 */

    public void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }
    
    
    public Bitmap getBitmap(String path, boolean album, int maxWidth, int maxHeight) {
		Bitmap tmpBitmap = null;
		int degree = 0;
		try {
			System.gc();


			int width = 0;
			int height = 0;

			BitmapFactory.Options bound = new BitmapFactory.Options();
			bound.inJustDecodeBounds = true;
			degree = GetExifOrientation(path);
			BitmapFactory.decodeFile(path, bound);
			if (bound.outWidth < bound.outHeight) {
				width = maxWidth * bound.outWidth / bound.outHeight;
				height = maxHeight;
			} else {
				width = maxWidth;
				height = maxHeight * bound.outHeight / bound.outWidth;
			}
			int scaleFactor = Math.min(bound.outWidth / width, bound.outHeight
					/ height);

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			if (album) {
				if (scaleFactor > 2) {
					o2.inSampleSize = scaleFactor;
				} else {
					o2.inSampleSize = 2;
				}
			} else {
				o2.inSampleSize = scaleFactor;
			}

			// �Ʒ� ���� �߰�.
			o2.inDither = false; // Disable Dithering mode
			o2.inPurgeable = true; // Tell to gc that whether it needs free
									// memory, the Bitmap can be cleared
			o2.inInputShareable = true; // Which kind of reference will be used
										// to recover the Bitmap data after
										// being clear, when it will be used in
										// the future
			o2.inTempStorage = new byte[32 * 1024];

			if (degree > 0) {
				tmpBitmap = BitmapFactory.decodeFile(path, o2);
				tmpBitmap = rotate(tmpBitmap, degree);
			} else {
				tmpBitmap = BitmapFactory.decodeFile(path, o2);
			}
			return tmpBitmap;
		} catch (Exception e) {

		}
		return null;
	}
    
    @SuppressWarnings("unused")
	public boolean saveOutput(Context context,Bitmap croppedImage, Uri mSaveUri) {
		boolean flag = false;
		ContentResolver mContentResolver = context.getContentResolver();
		if (mSaveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = mContentResolver.openOutputStream(mSaveUri);
				if (outputStream != null) {
					// 14.01.08 이한주 변경
					flag = croppedImage.compress(Bitmap.CompressFormat.JPEG,
							100, outputStream);
					try {
						Log.e("flag", "flag = " + flag);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException ex) {
				Log.e("flag", "flag = " + flag);
			} finally {
				Log.e("flag", "flag = " + flag);
				closeSilently(outputStream);
			}

			return flag;
		} else {
		}
		croppedImage.recycle();
		return flag;

	}
}
