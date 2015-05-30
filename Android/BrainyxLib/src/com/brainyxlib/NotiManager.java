package com.brainyxlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

public class NotiManager {

	/**
	 * 텍스트 노티피케이션을 띄운다.
	 * @param context
	 * @param icon
	 * @param title
	 * @param content
	 * @param activityClass
	 */
	public static void showNotifiText(Context context, int icon, String title,String content, Class<?> activityClass,boolean flag) {
		try {
			NotificationBuilder builder = new NotificationBuilder(context);
			builder.ShowNotificationTextBuilder(icon, title, content,activityClass,flag);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 이미지 노티피케이션을 띄운다
	 * @param context
	 * @param icon
	 * @param title
	 * @param content
	 * @param imgurl
	 * @param activityClass
	 */
	public static void showNotifiImage(final Context context,final int icon,final String type,final String content, String imgurl,final Class<?> activityClass,final boolean flag) {
		try {
			if (ImageLoader.getInstance().isInited()) {
				ImageLoader.getInstance().loadImage(imgurl,new com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
								NotificationBuilder builder = new NotificationBuilder(context);
								builder.ShowNotificationImageBuilder(icon,type, content, loadedImage, activityClass,flag);
							}
						});
			}else{
				BrImageCacheManager.getInstance().initImageLoader(context);
				ImageLoader.getInstance().loadImage(imgurl,new com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
								NotificationBuilder builder = new NotificationBuilder(context);
								builder.ShowNotificationImageBuilder(icon,type, content, loadedImage, activityClass,flag);
							}
						});
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
