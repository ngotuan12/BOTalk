package com.bxm.boardtalks.login;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.chat.ChatGlobal;
import com.br.chat.activity.BxmIntent;
import com.br.chat.gallery.CustomGallery;
import com.br.chat.service.ChattingService;
import com.br.chat.util.ContactUtil;
import com.br.chat.util.Request;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;
import com.brainyxlib.image.BrImageUtilManager;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.main.BaseActivity;
import com.bxm.boardtalks.main.FragmentMain;

public class Activity_MyProfile extends BaseActivity{

	ImageView myprofile_img;
	EditText edittext_name;
	CheckBox checkbox;
	String filepath  = "", thm_filepath = "";
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_myprofile);
		init();
		
	}
	private void init(){
		try {
			myprofile_img = (ImageView) findViewById(R.id.myprofile_img);
			edittext_name = (EditText)findViewById(R.id.edt_user_name);
			checkbox = (CheckBox)findViewById(R.id.checkbox);
			findViewById(R.id.myprofile_img).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*try {
						String[] array = {"앨범에서가져오기", "사진찍기"};
						BrUtilManager.getInstance().ShowArrayDialog(Activity_MyProfile.this, null, array, -1, new BrUtilManager.setOnItemChoice() {
							@Override
							public void setOnItemClick(int arg0) {
								try {
									switch(arg0){
									case 0:
										Intent intent = new Intent(Activity_MyProfile.this,CustomGallery.class);
										intent.putExtra("size", 1);//선택가능한갯수
										startActivityForResult(intent, ChatGlobal.CAMERA_ALBUM_REQUEST_CODE);	
										break;
									case 1:
										BxmIntent.goCameraForResult(Activity_MyProfile.this, ChatGlobal.CAMERA_CROP_REQUEST_CODE, null);
										break;
									}
								} catch (Exception e) {
									WriteFileLog.writeException(e);
								}	
							}
						});
					} catch (Exception e) {
						WriteFileLog.writeException(e);
					}*/
					
					Intent intent = new Intent(Activity_MyProfile.this,CustomGallery.class);
					intent.putExtra("size", 1);//선택가능한갯수
					startActivityForResult(intent, ChatGlobal.CAMERA_ALBUM_REQUEST_CODE);
					
				}
			});
			
			findViewById(R.id.send).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(edittext_name.getText().toString().trim().length() == 0){
						BrUtilManager.getInstance().showToast(Activity_MyProfile.this	, "대화명을 입력해주세요");
						 return;
					}
					
					if(filepath != null && filepath.length() > 0){
						String userseq=  SharedManager.getInstance().getString(Activity_MyProfile.this, BaseGlobal.User_Seq);
						SendImage(userseq);
					}else{
						updateProfile("");
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SendImage(final String sendseq){
		try {
			AsyncTask<Void, Void, String> getDataWorker = new AsyncTask<Void, Void, String>() {
				@Override
				protected void onPreExecute() {
					try {
						mActionHandler.sendEmptyMessage(1);
					} catch (Exception e) {
					}
					super.onPreExecute();
				}
				
				@Override
				protected String doInBackground(Void... params) {
					String uploadpath =  "";
					try {
						SendImg(thm_filepath, sendseq,true);
						uploadpath = SendImg(filepath, sendseq,false);
						
					} catch (Exception e) {
						WriteFileLog.writeException(e);
					}
					return uploadpath;
				}
				
				@Override
				protected void onPostExecute(String resultvalue) {
					try {
						mActionHandler.sendEmptyMessage(1);
						updateProfile(resultvalue);
						//getMember();	
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						
					}
					super.onPostExecute(resultvalue);				
				}			
			};		
			getDataWorker.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	private void updateProfile(String file){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		dataList.add(new BasicNameValuePair("userName", edittext_name.getText().toString()));
		//String userphone = BrUtilManager.getInstance().getPhoneNumber(this);
		final String userphone =SharedManager.getInstance().getString(Activity_MyProfile.this, BaseGlobal.User_Phone);
		dataList.add(new BasicNameValuePair("userPhone", userphone));
		dataList.add(new BasicNameValuePair("userPhoto", file));
		
		request.requestHttp2(this, dataList, ChatGlobal.mUpdateProfile, new Request.OnAfterParsedData() {
				@Override
				public void onResult(boolean result, JSONObject jsonobj) {
					//Log.e("update_profile", jsonobj.toString() + " " + userphone);
					try {
						if(result){
							SharedManager.getInstance().setString(Activity_MyProfile.this, BaseGlobal.User_name, edittext_name.getText().toString());
							String flag = jsonobj.getString("result");
							if(flag.startsWith("t")){
								getContactList();
								//getMember();	
							}else{
								mActionHandler.sendEmptyMessage(3);
							}
						//getMember();
						//myApp.memberMap.clear();
						//MemberVo.PasingJson(resultData,myinfo);
						//mActionHandler.sendEmptyMessage(2);
						}else{
							mActionHandler.sendEmptyMessage(3);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	
	
	
	public void getContactList(){
		try {
			AsyncTask<Void, Void, JSONArray> getDataWorker = new AsyncTask<Void, Void, JSONArray>() {
				@Override
				protected void onPreExecute() {
					try {
						mActionHandler.sendEmptyMessage(1);
					} catch (Exception e) {
					}
					super.onPreExecute();
				}
				
				@Override
				protected JSONArray doInBackground(Void... params) {
					
					try {
						
						ContactUtil util = new ContactUtil(Activity_MyProfile.this);
						JSONArray jsonarray = util.getContactList();
						return jsonarray;	
						
					} catch (Exception e) {
						WriteFileLog.writeException(e);
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(JSONArray resultvalue) {
					try {
 						mActionHandler.sendEmptyMessage(1);
						//updateProfile(resultvalue);
						getInfor(resultvalue.toString());
 						
 						
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						
					}
					super.onPostExecute(resultvalue);				
				}			
			};		
			getDataWorker.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
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
	
	private void getInfor(final String arraycontact){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		//String userphone = BrUtilManager.getInstance().getPhoneNumber(this);
		String userSeq =SharedManager.getInstance().getString(Activity_MyProfile.this, BaseGlobal.User_Seq);
		dataList.add(new BasicNameValuePair("user_seq", userSeq));
		
		request.requestHttp(this, dataList, ChatGlobal.getUserInfo, new Request.OnAfterParsedData(){

			@Override
			public void onResult(boolean result, JSONObject jsonobj) {
				
				try {
					if(result){
						JSONObject infor = jsonobj.getJSONObject("info");
						updateContact(arraycontact, infor);
					}else{
						mActionHandler.sendEmptyMessage(3);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		
		
	}
	
	private void updateContact(final String arraycontact, final JSONObject infor){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		String user_seq =SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		String user_phone =SharedManager.getInstance().getString(this, BaseGlobal.User_Phone).replaceAll("-", "").replaceAll("\\s+", "");
		dataList.add(new BasicNameValuePair("user_seq", user_seq));
		dataList.add(new BasicNameValuePair("user_phone", user_phone));
		dataList.add(new BasicNameValuePair("contacts", arraycontact));
		
		request.requestHttp(this, dataList, ChatGlobal.updateContact, new Request.OnAfterParsedData(){

			@Override
			public void onResult(boolean result, JSONObject jsonobj) {
				
				
				Log.e("update_contact", jsonobj.toString() + arraycontact);
				
				try {
					if(result){
						getMember(infor);
					}else{
						mActionHandler.sendEmptyMessage(3);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
	}
	
	private void getMember(final JSONObject infor){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		String user_seq =SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		dataList.add(new BasicNameValuePair("user_seq", user_seq));
		
		request.requestHttp(this, dataList, ChatGlobal.getUsers, new Request.OnAfterParsedData() {

				@Override
				public void onResult(boolean result, JSONObject jsonobj) {
					// TODO Auto-generated method stub
					try {
						if(result){
							JSONArray resultData = jsonobj.getJSONArray("users");
							MemberVo.PasingJson(resultData, infor);
							ChattingService.getInstance().getMyInfo();
							ChattingService.getInstance().getMember();
							ChattingService.getInstance().getFavorList();
							mActionHandler.sendEmptyMessage(2);
						}else{
							mActionHandler.sendEmptyMessage(3);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //다이얼로그 띄우기
				showProgress(activity);
				break;
			case 2:
				SharedManager.getInstance().setBoolean(Activity_MyProfile.this, BaseGlobal.AutoLogin, true);
				ChatVo chatvo = null;
				Intent intent = new Intent(Activity_MyProfile.this,FragmentMain.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				startActivity(intent);
				finish();
				break;
				
			case 3:
				
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
			
			dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		super.onActivityResult(arg0, arg1, intent);
		
		if(arg1 == RESULT_OK){
			switch(arg0){
			case ChatGlobal.CAMERA_CROP_REQUEST_CODE:
				if(intent == null)
					return;
				
				filepath = intent.getStringExtra(BxmIntent.KEY_FULL_PATH);
				
				myprofile_img.setImageURI(Uri.parse(filepath));
				SaveImglist(filepath, false);
				//SendMessageVo(ChatType.file, filename,chatvo.chatRoomSeq);
				break;
				
			case ChatGlobal.CAMERA_ALBUM_REQUEST_CODE:
				if(intent == null)
					return;
				
				//mSelectImg.clear();
				filepath = intent.getStringArrayListExtra("simg").get(0);
				myprofile_img.setImageURI(Uri.parse(filepath));
				SaveImglist(filepath, false);
				//SendMessageVo(ChatType.file, mSelectImg.get(0),chatvo.chatRoomSeq);
				break;
			}
		}
	}
	
	public void SaveImglist(final String filepath,final boolean flag) {
		try {
			AsyncTask<Context, Void, Void> SendFavor = new AsyncTask<Context, Void, Void>() {
				@Override
				protected void onPreExecute() {
					mActionHandler.sendEmptyMessage(1);
					super.onPreExecute();
				}

				@Override
				protected void onPostExecute(Void result) {
					mActionHandler.sendEmptyMessage(1);
					//linearLayout_loading.setVisibility(View.GONE);
					super.onPostExecute(result);
				}

				@Override
				protected Void doInBackground(Context... params) {
					SaveImg(filepath,flag);
					SaveImgThm(filepath, flag);
					return null;
				}

				@Override
				protected void onCancelled() {
					super.onCancelled();
				}

			};
			SendFavor.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void SaveImgThm(String filepath,boolean album) {
		Bitmap resize = BrImageUtilManager.getInstance().getBitmap(filepath, album,100,100);
		String myseq = SharedManager.getInstance().getString(Activity_MyProfile.this, BaseGlobal.User_Seq);
		File testfile = new File(myApp.getFileDir_Ex() + myseq + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(this,resize,getImageUri(testfile.getAbsolutePath()));
		if (!flag)
			return ;
		
		thm_filepath =  testfile.getAbsolutePath();
	}
	
	public void SaveImg(String filepath,boolean album) {
		Bitmap resize = BrImageUtilManager.getInstance().getBitmap(filepath, album,300,300);
		String myseq = SharedManager.getInstance().getString(Activity_MyProfile.this, BaseGlobal.User_Seq);
		File testfile = new File(myApp.getFileDir_Ex() + "_profile" + myseq + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(this,resize,getImageUri(testfile.getAbsolutePath()));
		if (!flag)
			return ;
		
		this.filepath =  testfile.getAbsolutePath();
	}
	
	
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}
	
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {
		
	}
}
