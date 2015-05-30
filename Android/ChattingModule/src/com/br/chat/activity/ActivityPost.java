package com.br.chat.activity;

import com.chattingmodule.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ActivityPost extends Activity {
	
	public static String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		WebView w = (WebView) findViewById(R.id.webViewPost);
		w.getSettings().setJavaScriptEnabled(true);
		w.loadUrl(link);
	}
	
	public void exit(View v){
		finish();
	}
	
	
}
