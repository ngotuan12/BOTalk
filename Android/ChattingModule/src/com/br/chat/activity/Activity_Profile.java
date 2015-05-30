package com.br.chat.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.service.ChattingService;
import com.br.chat.util.Request;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;

public class Activity_Profile extends Activity{


	private ImageView profile_img;
	private TextView name,stmsg;
	private String user_seq;
	private boolean activityflag = false;
	
	////
	private MessageVo Messagevo;
	private boolean isFriend = true;
	private int roomType = 0;
	Request request = null;
	private boolean isFavor = false;
	///
	
	TextView phonenumber;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		setContentView(R.layout.activity_profile);
		request= Request.getInstance();
		init();
	}
	
	private void init(){
		try {
			Intent intent = getIntent();
			activityflag = intent.getBooleanExtra(ChatGlobal.ProfileIntentActivity, false);
			if(activityflag){ // 채팅방에서 넘어온것
				Messagevo = (MessageVo)intent.getSerializableExtra(ChatGlobal.ProfileIntentMessageVO);
				roomType = intent.getIntExtra( ChatGlobal.ProfileIntentChatType, 0);
			}else{// 멤버리스트에서 넘어온것
				user_seq = intent.getStringExtra(ChatGlobal.ProfileIntentSeq);	
			}
			
			profile_img = (ImageView)findViewById(R.id.profile_img);
			name = (TextView)findViewById(R.id.name);
			stmsg = (TextView)findViewById(R.id.stmsg);
			phonenumber = (TextView)findViewById(R.id.phonenumber);
			findViewById(R.id.modify_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			findViewById(R.id.favor_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Favor();
				
				}
			});
			findViewById(R.id.close_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setResult(RESULT_CANCELED);
					finish();
				}
			});
			
			
			findViewById(R.id.chat_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!isFriend){ // 친구추가 클릭
						AddFriend(Messagevo.getSendseq());
					}else{ //1:1대화 클릭
						setResult(RESULT_OK);
						finish();	
					}
				}
			});
			findViewById(R.id.board_btn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent i = new Intent(Activity_Profile.this, ActivityBoard.class);
					startActivity(i);
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Favor(){
		AsyncTask<Void, Void, Void> executer = new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPostExecute(Void result) {
				mActionHandler.sendEmptyMessage(1);
				
				super.onPostExecute(result);
			}
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				//activity = _activity;
				//mActionHandler.sendEmptyMessage(1);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					if(isFavor){
						ChattingApplication.getInstance().chatdb.deleteFavor(Activity_Profile.this, user_seq);
						ChattingService.getInstance().getFavorList();
					}else{
						ChattingApplication.getInstance().chatdb.addFavor(Activity_Profile.this, user_seq);
						ChattingService.getInstance().getFavorList();
					}
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
				return null;
			}
			
		};
		executer.execute();
	}
	
	
	
	public void AddFriend(String userseq){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		dataList.add(new BasicNameValuePair("userSeq", userseq));
		
		request.requestHttp2(this, dataList, ChatGlobal.getUserInfo, new Request.OnAfterParsedData() {

				@Override
				public void onResult(boolean result, JSONObject jsonobj) {
					// TODO Auto-generated method stub
					try {
						if(result){
							if(jsonobj.getString("result").startsWith("t")){
								String userinfo = jsonobj.getString("data");
								MemberVo.PasingUser(userinfo);
								//ChattingService.getInstance().getMember();
								mActionHandler.sendEmptyMessage(1);	
							}else{
								mActionHandler.sendEmptyMessage(2);	
							}
							
							
						}else{
							mActionHandler.sendEmptyMessage(2);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		
	}
	
	private void SetView(){
		isFavor = ChattingApplication.getInstance().chatdb.isFavor(Activity_Profile.this, user_seq);
		if(activityflag && ChattingApplication.getInstance().memberMap.get(Messagevo.getSendseq()) == null){//채팅방에서 들어오고 친구목록에없다면
			isFriend = false;
		}else{
			isFriend = true;
		}
		
		if(activityflag){ // 채팅방에서 넘어온것
			name.setText(Messagevo.getSendname());
			//stmsg.setText(Messagevo.get);
			
				ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(Messagevo.getSendseq()), new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
					
					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						profile_img.setImageBitmap(response.getBitmap());
					}
				});
				//ImageLoader.getInstance().displayImage(myApp.memberMap.get(user_seq).getUserPhoto(), profile_img);
			
			if(roomType == ChatGlobal.chatTypeSingle){ //1대1채팅
				if(!isFriend){ //채팅방에서 넘어왔고 1대1기준. 친구가 아니라면
					//((ImageView)findViewById(R.id.chat_btn)).setBackgroundResource(R.drawable.prf_btn_f_prf);
					((Button)findViewById(R.id.chat_btn)).setText("친구추가");
					((ImageView)findViewById(R.id.favor_btn)).setVisibility(View.GONE);
				}else{ //친구라면 안보이게한다
					phonenumber.setText(ChattingApplication.getInstance().memberMap.get(user_seq).getUserPhone());
					//((ImageView)findViewById(R.id.chat_btn)).setBackgroundResource(R.drawable.prf_btn_f_prf_dw);
					((Button)findViewById(R.id.chat_btn)).setVisibility(View.GONE);
					((Button)findViewById(R.id.favor_btn)).setVisibility(View.VISIBLE);
					if(isFavor){
						((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites_dw);
					}else{
						((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites);
					}
					
				}				
			}else if(roomType == ChatGlobal.chatTypeGroup){//그룹채팅
				if(!isFriend){ //채팅방에서 넘어왔고 친구가 아니라면
					((Button)findViewById(R.id.chat_btn)).setText("친구추가");
					//((ImageView)findViewById(R.id.chat_btn)).setBackgroundResource(R.drawable.prf_btn_f_prf);
					((ImageView)findViewById(R.id.favor_btn)).setVisibility(View.GONE);
				}else{
					((ImageView)findViewById(R.id.favor_btn)).setVisibility(View.VISIBLE);
					((Button)findViewById(R.id.chat_btn)).setText("1:1채팅");
					//((ImageView)findViewById(R.id.chat_btn)).setBackgroundResource(R.drawable.prf_btn_chat);
					if(isFavor){
						((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites_dw);
					}else{
						((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites);
					}
				}
			}
		}else{// 멤버리스트에서 넘어온것
			name.setText(ChattingApplication.getInstance().memberMap.get(user_seq).getUsername());
			stmsg.setText(ChattingApplication.getInstance().memberMap.get(user_seq).getUserStmsg());
			phonenumber.setText(ChattingApplication.getInstance().memberMap.get(user_seq).getUserPhone());
			/*if(ChattingApplication.getInstance().memberMap.get(user_seq).getUserPhoto().length() == 0){
				profile_img.setBackgroundResource(R.drawable.ic_action_user_gray);
			}else{*/
				ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(user_seq), new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
					
					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						profile_img.setImageBitmap(response.getBitmap());
					}
				});
				if(isFavor){
					((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites_dw);
				}else{
					((ImageView)findViewById(R.id.favor_btn)).setBackgroundResource(R.drawable.prf_s_menu_ico_favorites);
				}
				//ImageLoader.getInstance().displayImage(myApp.memberMap.get(user_seq).getUserPhoto(), profile_img);
		//	}
		}
		
		String myseq = SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		if(myseq != null && myseq.equals(user_seq)){
			((Button)findViewById(R.id.chat_btn)).setVisibility(View.GONE);
			//((ImageView)findViewById(R.id.chat_btn)).setVisibility(View.GONE);
		}
	}

	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //성공
				Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
				sendBroadcast(intent);
				SetView();
				break;
				
			case 2: //실패
				break;
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SetView();
		
	}
	
}
