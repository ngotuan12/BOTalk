package com.bxm.boardtalks.login;

import java.util.ArrayList;
import java.util.List;

import kr.co.boardtalks.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.br.chat.ChatGlobal;
import com.br.chat.util.Request;
import com.br.chat.util.WriteFileLog;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

public class Activity_member_Register extends LoginBase {
	
	private Dialog dialog;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_member_register);
	}
	
	public void skipToProfile(View v){
		Intent intent = new Intent(this,Activity_MyProfile.class);
		startActivity(intent);
		finish();
	}
	
	public void register(View v){
		EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
		EditText edtPassword = (EditText) findViewById(R.id.edt_password);
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		String userSeq =SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		String email = edtEmail.getText().toString();
		String password = edtPassword.getText().toString();
		dataList.add(new BasicNameValuePair("user_seq", userSeq));
		dataList.add(new BasicNameValuePair("user_email", email));
		dataList.add(new BasicNameValuePair("user_pwd", password));
		
		request.requestHttp(this, dataList, ChatGlobal.registerEmail, new Request.OnAfterParsedData(){

			@Override
			public void onResult(boolean result, JSONObject jsonobj) {
				
				try {
					if(result){
						Intent intent = new Intent(Activity_member_Register.this,Activity_MyProfile.class);
						startActivity(intent);
						finish();
					}else{
						mActionHandler.sendEmptyMessage(3);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		
	}
	
	
	protected void showProgress() {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				return;
			}
			
			dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //다이얼로그 띄우기
				showProgress();
				break;
			case 2:
			
				break;
				
			case 3:
				
				break;
			}
		}
	};

}
