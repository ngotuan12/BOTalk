package com.bxm.boardtalks.login;

import java.util.ArrayList;
import java.util.List;

import kr.co.boardtalks.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.chat.ChatGlobal;
import com.br.chat.util.Request;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;

public class Activity_Login extends LoginBase{

	EditText edittext_phone;
	boolean isLoginfirst = true;
	String sitename = "인증번호";
	
	Spinner spinnerCountryCode;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_phone_login);
		init();
	}
	
	@Override
	public void onBackPressed() {
		mActionHandler.removeMessages(1);
		mActionHandler.removeMessages(2);
		mActionHandler.removeMessages(3);
		finish();
	}
	private void init(){
		edittext_phone = (EditText)findViewById(R.id.edt_phone_number);
		
		/*findViewById(R.id.delete_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edittext_phone.setText("");
			}
		});*/
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext_phone)){
					BrUtilManager.getInstance().ShowDialog(Activity_Login.this, null, "휴대폰번호 를 입력해주세요");
					return;
				}
				
				
				String phonNumber = edittext_phone.getText().toString().replaceAll("-", "").replaceAll("\\s+", "");
				
				
				if(!checkPhoneNumber(phonNumber)){
					BrUtilManager.getInstance().ShowDialog(Activity_Login.this, null, "휴대폰번호 를 정확히 입력해주세요");
					Log.e("wrong number", edittext_phone.getText().toString().replaceAll("\\s+", ""));
					return;
				}
				
				SendLogin(formatNumber(phonNumber));
			}
		});
		
		//String phonenum = BrUtilManager.getInstance().getPhoneNumber(this);
		//edittext_phone.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
		//edittext_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		//edittext_phone.setText(phonenum);
		
		//((TextView)findViewById(R.id.underlinetext)).setText(Html.fromHtml("<u>" + sitename + "</u>"));
	}

	private void SendLogin(String phoneNumber){
		try {
			if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext_phone)){
				BrUtilManager.getInstance().ShowDialog(this, null, "휴대폰번호 를 입력해주세요");
				return;
			}
			List<NameValuePair> dataList = new ArrayList<NameValuePair>();
			dataList.add(new BasicNameValuePair("userPhone", phoneNumber));
			String pushkey = SharedManager.getInstance().getString(Activity_Login.this, BaseGlobal.PushKey);
			dataList.add(new BasicNameValuePair("pushKey", pushkey));
			dataList.add(new BasicNameValuePair("OS", "ANDROID"));
			//dataList.add(new BasicNameValuePair("userName", BrUtilManager.getInstance().getPhoneNumber(this)));
			
			request.requestHttp2(this, dataList, ChatGlobal.mJoinGo, new Request.OnAfterParsedData() {

					@Override
					public void onResult(boolean result, JSONObject jsonobj) {
						// TODO Auto-generated method stub
						try {
							if(result){
								if(jsonobj.getString("result").startsWith("t")){
									String myinfo = jsonobj.getString("data");
									JSONObject obj = new JSONObject(myinfo.toString());
									SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_Seq, obj.getString("user_seq"));
									SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_Phone, obj.getString("user_phone"));
									SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_name, obj.getString("user_name"));
									
									//getMember();
									//myApp.memberMap.clear();
									//MemberVo.PasingJson(resultData,myinfo);
									mActionHandler.sendEmptyMessage(2);	
								}else{
									mActionHandler.sendEmptyMessage(3);	
								}
								
								
							}else{
								mActionHandler.sendEmptyMessage(3);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//if(myApp.memberArray.size() == 0){
					if(myApp.memberMap.size() == 0){
					BrUtilManager.getInstance().ShowDialog(Activity_Login.this, null, "멤버아이디가 아닙니다. 아이디를 확인해주세요");
					return;
				}
				
				//SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_id, edittext_id.getText().toString());
				//SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_pw, edittext_pw.getText().toString());
				/*SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_Seq, String.valueOf(myApp.memberArray.get(0).getSeq()));*/
				//SharedManager.getInstance().setString(Activity_Login.this, BaseGlobal.User_Seq, String.valueOf(myApp.memberArray.get(0).getSeq()));
				//SharedManager.getInstance().setBoolean(Activity_Login.this, BaseGlobal.AutoLogin, true);
				//GoMainActivity();
				myApp.ConnectSocket(null);
				break;
			case 2:
				Intent intent = new Intent(Activity_Login.this, ActivityMemberLogin.class);
				startActivity(intent);
				finish();
				break;
			case 3:
				
				BrUtilManager.getInstance().ShowDialog(Activity_Login.this, null, "이미 가입된 번호입니다.");
				break;
			}
		}
	};
	
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {
		
	}
	
	boolean checkPhoneNumber(String phoneNumber){
		int length = phoneNumber.length();
		if (length < 10 || length > 14) return false;
		if (length > 11) if (!phoneNumber.startsWith("+82")&&!phoneNumber.startsWith("+820")) return false;
		if (length < 12) if (!phoneNumber.startsWith("0")) return false;
		
		return true;
	}
	
	String formatNumber(String phoneNumber){
		
		if(phoneNumber.startsWith("0")) phoneNumber = "82" + phoneNumber.substring(1);                
		else phoneNumber = phoneNumber.replace("+820", "82").replace("+82", "82");
		
		return phoneNumber;
	}
	
}

