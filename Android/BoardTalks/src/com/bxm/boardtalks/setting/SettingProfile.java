package com.bxm.boardtalks.setting;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.ActivityPhotoTool;
import com.br.chat.activity.BxmIntent;
import com.br.chat.activity.CameraActivity;
import com.br.chat.adapter.ChatType;
import com.br.chat.gallery.CustomGallery;
import com.br.chat.util.CreateKey;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.CircleImageViewBig;
import com.br.chat.view.ImageLoaderInterface;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;
import com.brainyxlib.image.ImageVo;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.main.BaseActivity;
import com.bxm.boardtalks.member.Fragment_Member;

public class SettingProfile extends BaseActivity implements ImageLoaderInterface{

	CircleImageViewBig circleview ;
	ArrayList<ImageVo>filekey = new ArrayList<ImageVo>();
	ArrayList<String> mSelectImg = new ArrayList<String>();
	
	public static SettingProfile instance = null;

	public static SettingProfile getinstance() {
		return instance;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_settingprofile);
		instance = this;
		init();
	}
	
	private void init(){
		
		circleview = (CircleImageViewBig)findViewById(R.id.profile_img);
		circleview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String[] array = {"앨범에서가져오기", "사진찍기"};
					BrUtilManager.getInstance().ShowArrayDialog(SettingProfile.this, null, array, -1, new BrUtilManager.setOnItemChoice() {
						@Override
						public void setOnItemClick(int arg0) {
							try {
								switch(arg0){
								case 0:
									Intent intent = new Intent(SettingProfile.this,CustomGallery.class);
									intent.putExtra("size", ChatGlobal.IMGTOTALCOUNT - filekey.size());//선택가능한갯수
									startActivityForResult(intent, ChatGlobal.CAMERA_ALBUM_REQUEST_CODE);	
									break;
								case 1:
									intent = new Intent(SettingProfile.this,CameraActivity.class) ;
									intent.putExtra("what", 2);
									startActivityForResult(intent, ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE);
									
									//BxmIntent.goCameraForResult(SettingProfile.this, ChatGlobal.CAMERA_CROP_REQUEST_CODE, null);
									break;
								}
							} catch (Exception e) {
								WriteFileLog.writeException(e);
							}	
						}
					});
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
			}
		});
		findViewById(R.id.name_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
			}
		});
		findViewById(R.id.statmsg_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(SettingProfile.this,SettingStatMsg.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setView();
	}
	public void setView(){
		String id = SharedManager.getInstance().getString(this, BaseGlobal.User_id);
		String seq = SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		
		imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(seq)),circleview.getImageView(),imageLoaderOption, animationListener);
	/*	ChattingApplication.getInstance().getImageLoader().get(myApp.memberMap.get(seq).getUserPhoto(), new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				circleview.setImageBitmap(response.getBitmap());
			}
		});*/
		
		((TextView)findViewById(R.id.phonenum)).setText(BrUtilManager.getInstance().getPhoneNumber(this));
	
		((TextView)findViewById(R.id.id_textview)).setText(id);
		
		String stmsg = myApp.memberMap.get(seq).getUserStmsg();
		if(stmsg.length() == 0){
			((TextView)findViewById(R.id.statmsg_textview)).setTextColor(getResources().getColor(R.color.color_darkgray));
			((TextView)findViewById(R.id.statmsg_textview)).setText("상태메세지를 입력하세요");	
			
		}else{
			((TextView)findViewById(R.id.statmsg_textview)).setText(stmsg);	
		}
		String username = myApp.memberMap.get(seq).getUsername();
		((TextView)findViewById(R.id.name_textview)).setText(username);
		
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		super.onActivityResult(arg0, arg1, intent);
		if(arg1 == RESULT_OK){
			String seq = SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
			switch(arg0){
			case ChatGlobal.CAMERA_CROP_REQUEST_CODE:
				if(intent == null)
					return;
				
				String filename = intent.getStringExtra(BxmIntent.KEY_FULL_PATH);
				SendMessage(filename, seq);
				 /*filepath = SendImg(filename, seq);
				 if(filepath != null){
					 Sendphoto(filepath);	 
				 }else{
						mActionHandler.sendEmptyMessage(2);
				 }*/
				break;
				
			case ChatGlobal.CAMERA_ALBUM_REQUEST_CODE:
				if(intent == null)
					return;
				
				mSelectImg.clear();
				mSelectImg = intent.getStringArrayListExtra("simg");
				SendMessage(mSelectImg.get(0), seq);
				 /*filepath = SendImg(mSelectImg.get(0), seq);
				 if(filepath != null){
					 Sendphoto(filepath);	 
				 }else{
					mActionHandler.sendEmptyMessage(2);
				 }*/
				 
				break;
			case ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE:
				if(intent == null)
					return;
				
				filename = intent.getStringExtra("thumbnailurl");
				//SendMessage(filename, seq);
				intent =  new Intent(SettingProfile.this,ActivityPhotoTool.class);
				intent.putExtra(BxmIntent.KEY_TEMP_IMAGE_FILEPATH, filename);
				intent.putExtra("activity", true);
				startActivityForResult(intent, ChatGlobal.CAMERA_CROP_REQUEST_CODE);
				break;
			}
		}
	}
	
	public void SendMessage(final String imgpath,final String sendseq){
		AsyncTask<Void, Void, String> getDataWorker = new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				try {
					mActionHandler.sendEmptyMessage(3);
				} catch (Exception e) {
				}
				super.onPreExecute();
			}
			
			@Override
			protected String doInBackground(Void... params) {
				
				String uploadpath =  "";
				try {
					SendImg(imgpath, sendseq,true);
					uploadpath = SendImg(imgpath, sendseq,false);
					
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
				return uploadpath;
			/*	String resultpath = SendImg(imgpath, msgkey);
				return resultpath;*/
			}
			
			@Override
			protected void onPostExecute(String resultvalue) {
				try {
					mActionHandler.sendEmptyMessage(3);
					if(resultvalue != null){
						
						ChattingApplication.getInstance().socket.ProfileModifyMSG(ChattingApplication.getInstance().getMemberSeq(), CreateKey.getFileKey(), sendseq,ChatType.photomd,"");
						
						/*ChattingApplication.getInstance().socket.SendMessage(ChattingApplication.getInstance().getMemberSeq(),CreateKey.getFileKey(),  sendseq,resultvalue,
								SharedManager.getInstance().getString(SettingProfile.this, BaseGlobal.User_id),ChatType.photomd,"",-1,"사진변경",ChattingApplication.getInstance().getMemberSeq());*/
						//setView();
					}
					// Sendphoto(resultvalue);	 
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					
				}
				super.onPostExecute(resultvalue);				
			}			
		};		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		}else{
			getDataWorker.execute((Void[])null);
		}
	}
	
	/*public String SendImg(String imgpath,String msgkey){
		try {

			BasicAWSCredentials credentials = new BasicAWSCredentials(ChatGlobal.AMAZON_A3_ACCESS_KEY, ChatGlobal.AMAZON_A3_SECRET_KEY);
			AmazonS3 s3 = new AmazonS3Client(credentials);
			PutObjectRequest putObjectRequest = null;
			File uploadfile = new File(imgpath);
			putObjectRequest = new PutObjectRequest(ChatGlobal.AMAZON_A3_BUCKET_NAME, msgkey+ "/"+uploadfile.getName(), uploadfile);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite); // URL 접근시 권한 읽을수 있도록 설정.
			String filePath = "http://s3-" + s3.getBucketLocation(ChatGlobal.AMAZON_A3_BUCKET_NAME)+".amazonaws.com/"+ChatGlobal.AMAZON_A3_BUCKET_NAME+"/"+msgkey+"/"+uploadfile.getName();
			s3.putObject(putObjectRequest);
			return filePath; 
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}*/
	public String SendImg(String imgpath,String msgkey,boolean thum){
		try {

			BasicAWSCredentials credentials = new BasicAWSCredentials(ChatGlobal.AMAZON_A3_ACCESS_KEY, ChatGlobal.AMAZON_A3_SECRET_KEY);
			AmazonS3 s3 = new AmazonS3Client(credentials);
			PutObjectRequest putObjectRequest = null;
			File uploadfile = new File(imgpath);
			if(thum){
				putObjectRequest = new PutObjectRequest(ChatGlobal.AMAZON_A3_BUCKET_NAME, msgkey+ "/thm_"+msgkey + ".jpg", uploadfile);
			}else{
				putObjectRequest = new PutObjectRequest(ChatGlobal.AMAZON_A3_BUCKET_NAME, msgkey+ "/"+msgkey + ".jpg", uploadfile);
			}
			//putObjectRequest = new PutObjectRequest(ChatGlobal.AMAZON_A3_BUCKET_NAME, msgkey+ "/"+msgkey + ".jpg", uploadfile);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite); // URL 접근시 권한 읽을수 있도록 설정.
			String filePath = "";
			if(thum){
				filePath = "http://s3-" + s3.getBucketLocation(ChatGlobal.AMAZON_A3_BUCKET_NAME)+".amazonaws.com/"+ChatGlobal.AMAZON_A3_BUCKET_NAME+"/"+msgkey+"/thm_"+msgkey + ".jpg";	
			}else{
				 filePath = "http://s3-" + s3.getBucketLocation(ChatGlobal.AMAZON_A3_BUCKET_NAME)+".amazonaws.com/"+ChatGlobal.AMAZON_A3_BUCKET_NAME+"/"+msgkey+"/"+msgkey + ".jpg";
			}
			
			s3.putObject(putObjectRequest);
			return filePath; 
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/*private void Sendphoto(final String filepath){
		try {
			String id = SharedManager.getInstance().getString(this, BaseGlobal.User_id);
			List<NameValuePair> dataList = new ArrayList<NameValuePair>();
			dataList.add(new BasicNameValuePair("userId", id));
			dataList.add(new BasicNameValuePair("userPhoto", filepath));
			
			request.requestHttp2(this, dataList, Global.setPhoto, new Request.OnAfterParsedData() {
					@Override
					public void onResult(boolean result, String resultData,String myinfo) {
						if(result){
							Message message = new Message();
							message.what = 1;
							message.obj = filepath;
							
							String myseq = SharedManager.getInstance().getString(SettingProfile.this, BaseGlobal.User_Seq);
							ChattingApplication.getInstance().socket.SendMessage(ChattingApplication.getInstance().getMemberSeq(),CreateKey.getFileKey(),  myseq,filepath,
									SharedManager.getInstance().getString(SettingProfile.this, BaseGlobal.User_id),ChatType.photomd,"",-1);
							
							mActionHandler.sendMessage(message);
							//myApp.memberMap.clear();
							//MemberVo.PasingJson(resultData,myinfo);
						
						}else{
							mActionHandler.sendEmptyMessage(2);
						}
						//mActionHandler.sendEmptyMessage(1);	
						Log.e("", "" + resultData);
					}
				});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String filepath = (String)msg.obj;
				String seq = SharedManager.getInstance().getString(SettingProfile.this, BaseGlobal.User_Seq);
				myApp.memberMap.get(seq).setUserPhoto(filepath);
				setView();
				if(Fragment_Member.getInstance() != null){
					Fragment_Member.getInstance().Update();
				}
				
				BrUtilManager.getInstance().ShowDialog(SettingProfile.this, null, "프로필이미지가 변경되었습니다");
				break;
				
			case 2:
				BrUtilManager.getInstance().ShowDialog(SettingProfile.this, null, "잠시후에 다시시도해주세요");
				break;
			case 3:
				showProgress(SettingProfile.this);
				break;
			}
		}
	};
	
	public Dialog dialog;
	public Activity activity;
	protected void showProgress(Activity ctx) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				return;
			}
			
			dialog = new Dialog(ctx, com.chattingmodule.R.style.TransDialog);
			dialog.setContentView(new ProgressBar(ctx));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {
		
	}
}
