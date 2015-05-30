package com.br.chat.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.br.chat.ChattingApplication;
import com.br.chat.util.Mylog;
import com.chattingmodule.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public interface  ImageLoaderInterface {

	
	DisplayImageOptions imageLoaderOption  = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.transparent) // resource or drawable
    .showImageForEmptyUri(R.drawable.transparent) // resource or drawable
    .showImageOnFail(R.drawable.transparent) 
	.cacheInMemory(true)
    .cacheOnDisk(true)
    
	.build();
	
	DisplayImageOptions roundImageLoaderOption  = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.main_f_list_prf_man_img) // resource or drawable
    //.showImageForEmptyUri(R.drawable.ic_action_user_gray) // resource or drawable
    //.showImageOnFail(R.drawable.ic_action_user_gray)
//    .showImageOnFail(R.drawable.ic_action_halt)
	.cacheInMemory(true)
    .cacheOnDisk(true)
	.build();

	// Image Loader
	//ImageLoader imageLoader = ImageLoader.getInstance().init(ChattingApplication.getInstance().getConfig());
	ImageLoader imageLoader = ChattingApplication.getInstance().getAUILImageLoader();
	
	AnimateFirstDisplayListener animationListener = new AnimateFirstDisplayListener();

	static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());
			
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			
			Mylog.e("", "onLoadingComplete: imageUri: "+imageUri);
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
				boolean firstDisplay = !displayedImages.equals(imageUri);
				
				if (firstDisplay) {
				
					FadeInBitmapDisplayer.animate(imageView, 0);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
