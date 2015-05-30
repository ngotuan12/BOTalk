package com.br.chat.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class CommonDialog extends Dialog
{
	protected Context mContext = null;

	public CommonDialog(Context context)
	{	
		super(context);
		start(context);
	}
	
	public void start(Context context)
	{	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setCanceledOnTouchOutside(true);
		mContext = context;
		initDialogEnvironment();
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
	}

	public void initDialogEnvironment()
	{
		setLayoutPosition();
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		/*if(dialogMode==MODE_NOTICEBAR){
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		}*/
	}
	
	public void setLayoutPosition(){
		LayoutParams params = getWindow().getAttributes();
		params.dimAmount = 0;
		params.gravity = Gravity.CENTER;
		getWindow().setAttributes(params);
	}

}