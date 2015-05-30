package com.br.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chattingmodule.R;

public class ControlButton extends RelativeLayout {
	public TextView mTextView;
	public ImageView mImageView;
	public RelativeLayout mRoot;
	private RelativeLayout mRelativeLayout_textBox;
	private Context mContext;
	public ControlButton(Context context) {
		super(context);
		mContext = context;
		init(context);
	}
	
	private int mIconId = 0;
	private int mIconIdDisable = 0;
	private int mColorOnText = 0;
	private int mColorOffText = 0;
	
	public ControlButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(context);
		
		TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.ButtonTypeCustomView);
		
		String text1 = a.getString(R.styleable.ButtonTypeCustomView_text1);
        if (text1 != null) {
        	mTextView.setText("" + text1.toString());
        	mTextView.setVisibility(View.VISIBLE);
        	mRelativeLayout_textBox.setVisibility(View.VISIBLE);
        }else{
        	mRelativeLayout_textBox.setVisibility(View.GONE);
        }
        
        
        mColorOnText = mContext.getResources().getColor(a.getResourceId(R.styleable.ButtonTypeCustomView_text1ColorOn, R.color.color_darkgray));
		if(mColorOnText!=0){
			mTextView.setTextColor(mColorOnText);
		}
		
		mColorOffText = mContext.getResources().getColor(a.getResourceId(R.styleable.ButtonTypeCustomView_text1ColorOff, R.color.color_white));
		if(mColorOffText!=0){
			mTextView.setTextColor(mColorOffText);
		}
        
        mIconId = a.getResourceId(R.styleable.ButtonTypeCustomView_buttonIcon, 0);
        if (mIconId != 0) {
        	mImageView.setImageResource(mIconId);
        	mImageView.setVisibility(View.VISIBLE);
        }
        
        int left = 0;
        int right = 0;
        int size  = mContext.getResources().getDimensionPixelSize(R.dimen.controlButtonMargin);
    	left = size;
    	right = size;
        if(mTextView.getVisibility()==View.VISIBLE && mImageView.getVisibility()==View.VISIBLE){
        	right = 0;
        }
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	params.setMargins(left, 0, right, 0);
    	mRelativeLayout_textBox.setLayoutParams(params);
    	
        mIconIdDisable = a.getResourceId(R.styleable.ButtonTypeCustomView_buttonIconDisable, 0);
        
        a.recycle();
	}
	
	private void init(Context context) {
	    LayoutInflater li = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View View = li.inflate(R.layout.widget_control_button,  this, false);
	    addView(View);
	    mRoot =  (RelativeLayout)View.findViewById(R.id.widgetControlButton_RelativeLayout_clickBox);
	    mTextView =  (TextView)View.findViewById(R.id.widgetControlButton_TextView_text);
	    mImageView = (ImageView)View.findViewById(R.id.widgetControlButton_ImageView_icon);
	    mRelativeLayout_textBox = (RelativeLayout)View.findViewById(R.id.widgetControlButton_RelativeLayout_textBox);
	    
	}
	
	private View.OnClickListener mOnClickListener;
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(enabled){
			if (mIconId != 0) {
	        	mImageView.setImageResource(mIconId);
	        	mImageView.setVisibility(View.VISIBLE);
	        }
			if(mColorOnText!=0)mTextView.setTextColor(mColorOnText);
		}else{
			if (mIconIdDisable != 0) {
	        	mImageView.setImageResource(mIconIdDisable);
	        	mImageView.setVisibility(View.VISIBLE);
	        }
			if(mColorOffText!=0)mTextView.setTextColor(mColorOffText);
		}
		
	}
}
