package com.br.chat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chattingmodule.R;

public class CircleImageViewBig extends RelativeLayout {
	public ImageView mImageViewFace;
	public ImageView mIcon;
	public ImageView mBackground;
	public CircleImageViewBig(Context context) {
		super(context);
		init(context);
	}
	
	public CircleImageViewBig(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
	    LayoutInflater li = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = li.inflate(R.layout.templete_circle_image_big,  this, false);
	    addView(view);
	    
	    mImageViewFace = (ImageView)		view.findViewById(R.id.templeteCircleImage1_ImageView_faceImage);
	    mIcon = (ImageView)view.findViewById(R.id.templeteCircleImage1_ImageView_icon);
	    mBackground = (ImageView)view.findViewById(R.id.templeteCircleImage1_ImageView_background);
	    
	}
	
	public void setImageBitmap(Bitmap bitmap){
		mImageViewFace.setImageBitmap(bitmap);
	}

	public ImageView getImageView(){
		return mImageViewFace;
	}
	public void setWidth(int width){
		LayoutParams params = (LayoutParams) mImageViewFace.getLayoutParams();
		params.width = getResources().getDimensionPixelSize(R.dimen.list_item_user_image3);
		mImageViewFace.setLayoutParams(params);
		
		LayoutParams params2 = (LayoutParams) mBackground.getLayoutParams();
		params.width = getResources().getDimensionPixelSize(R.dimen.list_item_user_image3_background);
		mBackground.setLayoutParams(params2);
		
	}
	
	public void setHeight(int height){
	}
}
