package com.bxm.boardtalks.setting;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.adapter.ChatType;
import com.br.chat.util.CreateKey;
import com.br.chat.util.Request;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;

import kr.co.boardtalks.Global;
import kr.co.boardtalks.R;

import com.bxm.boardtalks.main.BaseActivity;
import com.bxm.boardtalks.member.Fragment_Member;

public class SettingStatMsg extends BaseActivity{

	EditText edittext_msg;
	public static SettingStatMsg instance = null;

	public static SettingStatMsg getinstance() {
		return instance;
	}
	public String myseq = "";
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_statmsg);
		
		init();
	}
	
	private void init(){
		myseq = SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		edittext_msg = (EditText)findViewById(R.id.edittext_msg);
		findViewById(R.id.savebtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(edittext_msg.getText().toString().trim().length() == 0){
					BrUtilManager.getInstance().ShowDialog(SettingStatMsg.this, null, "상태메세지를 입력해주세요");
					return;
				}
				
				Sendphoto(edittext_msg.getText().toString());
				
			}
		});
	}
	
	public void setView(){
		//String seq = SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		edittext_msg.setText(myApp.memberMap.get(myseq).getUserStmsg());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setView();
	}

	private void Sendphoto(final String statmsg){
		try {
			//String id = SharedManager.getInstance().getString(this, BaseGlobal.User_id);
			List<NameValuePair> dataList = new ArrayList<NameValuePair>();
			dataList.add(new BasicNameValuePair("userSeq", myseq));
			dataList.add(new BasicNameValuePair("userStmsg", statmsg));
			
			request.requestHttp(this, dataList, ChatGlobal.setStmsg, new Request.OnAfterParsedData() {

					@Override
					public void onResult(boolean result, JSONObject jsonobj) {
						if(result){
							Message message = new Message();
							message.what = 1;
							message.obj = statmsg;
							
							String myseq = SharedManager.getInstance().getString(SettingStatMsg.this, BaseGlobal.User_Seq);
							ChattingApplication.getInstance().socket.ProfileModifyMSG(ChattingApplication.getInstance().getMemberSeq(), CreateKey.getFileKey(), myseq,ChatType.stmsgmd,statmsg);
							
							mActionHandler.sendMessage(message);
							//finish();
							
						}else{
							mActionHandler.sendEmptyMessage(2);
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
				/*String statmsg = (String)msg.obj;
				String seq = SharedManager.getInstance().getString(SettingStatMsg.this, BaseGlobal.User_Seq);
				myApp.memberMap.get(seq).setUserStmsg(statmsg);
				if(Fragment_Member.getInstance() != null){
					Fragment_Member.getInstance().Update();
				}*/
				finish();
				break;
				
			case 2:
				BrUtilManager.getInstance().ShowDialog(SettingStatMsg.this, null, "잠시후에 다시시도해주세요");
				break;
				
			}
		}
	};
	
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {}
}
