package com.br.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.chat.util.Mylog;
import com.chattingmodule.R;

public class ImageButtonIconText extends RelativeLayout {
	public TextView mTextView_white;
	public TextView mTextView_gray;
	public ImageView mImageView;
	public LinearLayout mRoot;
	private View mClickBox;
	private Context mContext;
	public ImageButtonIconText(Context context) {
		super(context);
		mContext = context;
		init(context);
	}
	
	private final int COLOR_WHITE = 1;
	private final int COLOR_GRAY_whiteShadow = 2;
	public ImageButtonIconText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(context);
		
		TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.ButtonTypeCustomView);

		int defaultFontSize = getResources().getDimensionPixelSize(R.dimen.fontSize_12);
		int textColor = a.getResourceId(R.styleable.ButtonTypeCustomView_text1ColorOn, 0);
		
		int textColorMode = a.getInt(R.styleable.ButtonTypeCustomView_text1TextColorMode, COLOR_WHITE);
		if(textColorMode==COLOR_GRAY_whiteShadow){
			mTextView_gray.setVisibility(View.VISIBLE);
			if(textColor!=0) mTextView_gray.setTextColor(mContext.getResources().getColor(textColor));
		}else{
			mTextView_white.setVisibility(View.VISIBLE);
			if(textColor!=0)
				mTextView_white.setTextColor(mContext.getResources().getColor(textColor));
		}
		
		String text1 = a.getString(R.styleable.ButtonTypeCustomView_text1);
        if (text1 != null) {
        	setText(text1.toString());
        }
        
		int text1Color = a.getResourceId(R.styleable.ButtonTypeCustomView_text1Color, 0);
		Mylog.e("","text1Color:" + text1Color);
		if(text1Color!=0){
			setTextColor(text1Color);
		}
		
		int textSize = a.getDimensionPixelSize(R.styleable.ButtonTypeCustomView_text1Size, defaultFontSize);
		setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		
		int backgroundId = a.getResourceId(R.styleable.ButtonTypeCustomView_buttonBackground, 0);
        if (backgroundId != 0) {
        	mRoot.setBackgroundResource(backgroundId);
        }
        
        int buttonClickBackground = a.getResourceId(R.styleable.ButtonTypeCustomView_buttonClickBackground, 0);
        if (buttonClickBackground != 0) {
        	mClickBox.setBackgroundResource(buttonClickBackground);
        }
		
        int iconId = a.getResourceId(R.styleable.ButtonTypeCustomView_buttonIcon, 0);
        if (iconId != 0) {
        	mImageView.setImageResource(iconId);
        	mImageView.setVisibility(View.VISIBLE);
        }
        
        a.recycle();
	}
	
	private void init(Context context) {
	    LayoutInflater li = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View View = li.inflate(R.layout.widget_image_button_icon_text,  this, false);
	    addView(View);
	    
	    mRoot =  (LinearLayout)View.findViewById(R.id.widgetImageButtonIconText_LinearLayout_root);
	    mTextView_white =  (TextView)View.findViewById(R.id.widgetImageButtonIconText_TextView_white);
	    mTextView_gray =  (TextView)View.findViewById(R.id.widgetImageButtonIconText_TextView_gray_whiteShadow);
	    
	    mImageView = (ImageView)View.findViewById(R.id.widgetImageButtonIconText_ImageView);
	    mClickBox =  (View)View.findViewById(R.id.widgetImageButtonIconText_View_clickBox);
	    mClickBox.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(android.view.View v) {
				
			}
		});
	}
	
	private void setTextSize(int typeValue, int textSize){
		mTextView_white.setTextSize(typeValue, textSize);
		mTextView_gray.setTextSize(typeValue, textSize);
	}
	
	public void setText(String text){
		mTextView_white.setText(text);
		mTextView_gray.setText(text);
	}
	
	public void setTextColor(int color){
		mTextView_white.setTextColor(mContext.getResources().getColor(color));
		mTextView_gray.setTextColor(mContext.getResources().getColor(color));
	}
	
	public void setClickable(boolean clickable, View.OnClickListener listener){
		if(clickable){
			mClickBox.setVisibility(View.VISIBLE);
			mClickBox.setOnClickListener(listener);
		}else{
			mClickBox.setVisibility(View.GONE);
			mClickBox.setOnClickListener(null);
		}
	}
	
	public void setClickable(boolean clickable){
		if(clickable){
			mTextView_white.setVisibility(View.VISIBLE);
			mTextView_gray.setVisibility(View.GONE);
			mClickBox.setVisibility(View.VISIBLE);
		}else{
			mTextView_white.setVisibility(View.GONE);
			mTextView_gray.setVisibility(View.VISIBLE);
			mClickBox.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void setOnClickListener(OnClickListener listener){
		mClickBox.setOnClickListener(listener);
	}
}
