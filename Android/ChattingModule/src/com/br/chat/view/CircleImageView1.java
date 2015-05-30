package com.br.chat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.chattingmodule.R;

public class CircleImageView1 extends RelativeLayout {
	public ImageView mImageViewFace;
	public ImageView mIcon;
	public ImageView mBackground;
	public CircleImageView1(Context context) {
		super(context);
		init(context);
	}
	
	public CircleImageView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
	    LayoutInflater li = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = li.inflate(R.layout.templete_circle_image1,  this, false);
	    addView(view);
	    
	    mImageViewFace = (ImageView)		view.findViewById(R.id.templeteCircleImage1_ImageView_faceImage);
	    mIcon = (ImageView)view.findViewById(R.id.templeteCircleImage1_ImageView_icon);
	    mBackground = (ImageView)view.findViewById(R.id.templeteCircleImage1_ImageView_background);
	    
	}
	
	public void setChatRoom(boolean flag){
		if(flag){
			mBackground.setBackgroundResource(R.drawable.chat_talk_prf_img_bg);
		}
	}
	
	public ImageView getFaceView(){
		return mImageViewFace;
	}
	
	public void setImageBitmap(Bitmap bitmap){
		mImageViewFace.setImageBitmap(bitmap);
	}
}
