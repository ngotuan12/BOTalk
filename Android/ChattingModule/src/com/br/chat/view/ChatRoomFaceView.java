package com.br.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.br.chat.ChatGlobal;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;

public class ChatRoomFaceView extends LinearLayout implements ImageLoaderInterface{

	//public NetworkImageView faceview1, faceview2, faceview3, faceview4;
	public ImageView faceview1, faceview2, faceview3, faceview4;
	public LinearLayout facelyaout2;
	public Context mContext;
	public String myseq = "";
	public ChatRoomFaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ChatRoomFaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ChatRoomFaceView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		/*LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.chatroom_faceview, this, false);*/

		View view = LayoutInflater.from(context).inflate(R.layout.chatroom_faceview, this,true);
		
	/*	faceview1 = (NetworkImageView) view.findViewById(R.id.faceview1);
		faceview2 = (NetworkImageView) view.findViewById(R.id.faceview2);
		faceview3 = (NetworkImageView) view.findViewById(R.id.faceview3);
		faceview4 = (NetworkImageView) view.findViewById(R.id.faceview4);*/
		faceview1 = (ImageView) view.findViewById(R.id.faceview1);
		faceview2 = (ImageView) view.findViewById(R.id.faceview2);
		faceview3 = (ImageView) view.findViewById(R.id.faceview3);
		faceview4 = (ImageView) view.findViewById(R.id.faceview4);
		facelyaout2 = (LinearLayout) view.findViewById(R.id.facelayout2);
		myseq = SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq);
	}

	public int getCount(String[] member){
		int count = 0;
		
		for(int i = 0 ; i < member.length; i++){
			if(!member[i].equals(myseq)){
				count ++;
			}
		}
		return count;
	}
	int count = 0 ;
	public void setFaceView(String[] member) {
		try {
			count = 0;
			for(int i = 0 ; i < member.length;i++){
				if(!member[i].equals(myseq)){
					setVisibleView(count);
					imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(member[i]),getFaceView(count),roundImageLoaderOption, animationListener);
					//getFaceView(count).setImageUrl(ChatGlobal.getMemberFaceThumURL(member[i]), ChattingApplication.getInstance().getImageLoader());
					count ++;
					
				/*	ImageLoader.getInstance().displayImage(ChatGlobal.getMemberFaceThumURL(member[i]), getFaceView(index), new ImageLoadingListener() {
						
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							// TODO Auto-generated method stub
							Log.e("", "onLoadingStarted ==" + ChatGlobal.getMemberFaceThumURL(imageUri));
						}
						
						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// TODO Auto-generated method stub
							Log.e("", "onLoadingFailed ==" + ChatGlobal.getMemberFaceThumURL(imageUri));
						}
						
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							// TODO Auto-generated method stub
							
							getFaceView(index).setImageBitmap(loadedImage);
							Log.e("", "onLoadingComplete ==" + ChatGlobal.getMemberFaceThumURL(imageUri));
						}
						
						@Override
						public void onLoadingCancelled(String imageUri, View view) {
							// TODO Auto-generated method stub
							Log.e("", "onLoadingCancelled ==" + ChatGlobal.getMemberFaceThumURL(imageUri));
						}
					});*/
					//ImageLoader.getInstance().displayImage(ChatGlobal.getMemberFaceThumURL(member[i]), getFaceView(index));
					Log.e("", "" + ChatGlobal.getMemberFaceThumURL(member[i]));
				/*	ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(member[i]), new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						getFaceView(index).setImageBitmap(response.getBitmap());
					}
				});*/
				}
				
				
				/*ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(member[i]), new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						getFaceView(index).setImageBitmap(response.getBitmap());
					}
				});*/
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public ImageView getFaceView(int index){
		switch(index){
		case 0:
			return faceview1;
		case 1:
			return faceview2;
		case 2:
			return faceview3;
		case 3:
			return faceview4;
		}
		return null;
	}
	
	private void setVisibleView(int count) {
		switch (count) {
		case 0:
			faceview1.setVisibility(View.VISIBLE);
			faceview2.setVisibility(View.GONE);
			faceview3.setVisibility(View.GONE);
			faceview4.setVisibility(View.GONE);
			facelyaout2.setVisibility(View.GONE);
			break;
		case 1:
			faceview1.setVisibility(View.VISIBLE);
			faceview2.setVisibility(View.VISIBLE);
			faceview3.setVisibility(View.GONE);
			faceview4.setVisibility(View.GONE);
			facelyaout2.setVisibility(View.GONE);
			break;
		case 2:
			faceview1.setVisibility(View.VISIBLE);
			faceview2.setVisibility(View.VISIBLE);
			faceview3.setVisibility(View.VISIBLE);
			faceview4.setVisibility(View.GONE);
			facelyaout2.setVisibility(View.VISIBLE);
			break;
		case 3:
			faceview1.setVisibility(View.VISIBLE);
			faceview2.setVisibility(View.VISIBLE);
			faceview3.setVisibility(View.VISIBLE);
			faceview4.setVisibility(View.VISIBLE);
			facelyaout2.setVisibility(View.VISIBLE);
			break;
		}
	}
}
