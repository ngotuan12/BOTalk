package com.br.chat.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chattingmodule.R;

public class DialogVHProgress extends CommonDialog{

	public static String TAG = "DialogCMProgress";
	
	public DialogVHProgress(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		setContentView(R.layout.widget_progressbar);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("","mContext:" +mContext );
        ProgressBar progressBar;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        	progressBar = (ProgressBar) this.findViewById(R.id.widgetProgressbar_ProgressBarHolo);
        else
        	progressBar = (ProgressBar) this.findViewById(R.id.widgetProgressbar_ProgressBarGinger);
        
        progressBar.setVisibility(View.VISIBLE);
    }
}
