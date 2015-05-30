package com.br.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.br.chat.database.ChattingDBHeler;
import com.br.chat.service.ChattingService;
import com.br.chat.socket.ConnetedSocket;
import com.br.chat.socket.ConnetedSocket.ConnectedListner;
import com.br.chat.util.ContactUtil;
import com.br.chat.util.FileCache;
import com.br.chat.util.Mylog;
import com.br.chat.util.NotificationBuilder;
import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatRoomVo;
import com.br.chat.vo.MemberVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class ChattingApplication extends com.kakao.GlobalApplication{

	public static final String TAG ="ChattingApplication";
	
	 /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;
    
    private static ChattingApplication sInstance;
    
	static {
        com.android.volley.VolleyLog.DEBUG = Mylog.DEBUG;
    }
	public ChattingDBHeler chatdb = null;
	private ImageLoader imageLoader = null;
	public HashMap<String, ChatRoomVo> room = new HashMap<String, ChatRoomVo>();
	public HashMap<String, MemberVo> memberMap = new HashMap<String, MemberVo>();
	
	public ArrayList<MemberVo>favorArray = new ArrayList<MemberVo>();
	public ArrayList<MemberVo>memberArray = new ArrayList<MemberVo>();
	public MemberVo myinfo = null;
	public ConnetedSocket socket = null;
	public boolean chatGo = false;
	public DisplayImageOptions options = null;
	public com.nostra13.universalimageloader.core.ImageLoader auilImageLoader;
	
	public String isTablet = "";
	
//	public AmazonS3 s3 = null;
	//public BasicAWSCredentials credentials = null;
	//public   PutObjectRequest putObjectRequest = null;
	@Override
	public void onCreate() {
		super.onCreate();
		  sInstance = this;
		  chatdb = new ChattingDBHeler();
		  chatdb.openWrite(getApplicationContext());
		  chatdb.Close();
		  StartService();
		 
		  getRoomList();
		  WriteFileLog.initialize(this);
		   final RequestQueue requestQueue = Volley.newRequestQueue(this);

	        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
	            @SuppressLint("NewApi")
				final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(3);
	            @SuppressLint("NewApi")
				@Override
	            public void putBitmap(String key, Bitmap value) {
	                imageCache.put(key, value);
	            }
	            @SuppressLint("NewApi")
				@Override
	            public Bitmap getBitmap(String key) {
	                return imageCache.get(key);
	            }
	        };
	        imageLoader = new ImageLoader(requestQueue, imageCache);
	        setImageLoader(getApplicationContext());
	        
	        isTablet = getString(R.string.screen_type);
	        Log.e("", "" + isTablet);
	}
	
	public  void clearNotifyMsg() {
		try {
			NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			nm.cancel(NotificationBuilder.NOTIFICATION_ID);
	
		} catch(Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public ImageLoaderConfiguration getConfig(){
		FileCache filecache = new FileCache(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).discCacheFileCount(200)
				.threadPoolSize(5)
				.threadPriority(Thread.MIN_PRIORITY + 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2000000))
				// You can pass your own memory cache implementation
				.discCache(new UnlimitedDiskCache(filecache.getCacheDir()))
				// You can pass your own disc cache implementation
				// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs().build();
		
		return config;
	}
	
	public void setImageLoader(Context context) {
		FileCache filecache = new FileCache(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).discCacheFileCount(200)
				.threadPoolSize(5)
				.threadPriority(Thread.MIN_PRIORITY + 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2000000))
				// You can pass your own memory cache implementation
				.discCache(new UnlimitedDiskCache(filecache.getCacheDir()))
				// You can pass your own disc cache implementation
				// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs().build();
		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
		auilImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
		options = getOptions(R.drawable.ic_launcher, R.drawable.ic_launcher);
	}
	
	private DisplayImageOptions getOptions(int defaultImg,int failimg){
		  DisplayImageOptions options = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.EXACTLY)
			.cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).showStubImage(defaultImg).showImageOnFail(failimg)
			.build();
		  return options;
	}	
	
	
	public void setChatGo(boolean flag){
		chatGo = flag;
	}
	public boolean isChatGo(){
		return chatGo;
	}
	
	public String[] getMemberSeq(){
		try {
			ArrayList<MemberVo> arraylist = new ArrayList<MemberVo>(memberMap.values());
			String[] members = new String[arraylist.size()];
			for(int i = 0 ; i < arraylist.size();i++){
				
				members[i] = String.valueOf(arraylist.get(i).getSeq());
			}
			
			return members;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public void getRoomList(){
		try {
			Cursor cursor = chatdb.getChatRoomList(getApplicationContext(),SharedManager.getInstance().getString(getApplicationContext(), BaseGlobal.User_Seq));
			ChatRoomVo.ParseCusor(cursor);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void ConnectSocket(ConnectedListner listner){
		try {
			 socket = new ConnetedSocket(this,listner);
			 socket.ConnectSocket(SharedManager.getInstance().getString(getApplicationContext(), BaseGlobal.User_Seq),SharedManager.getInstance().getString(getApplicationContext(), BaseGlobal.User_name));
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void DisconnectSocket(){
		try {
			if(socket != null && socket.getSocket().isConnected()){
				socket.getSocket().close();
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	public void StartService(){
		try {
			Intent intent = new Intent(this,ChattingService.class);
			startService(intent);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void StopService(){
		try {
			stopService(new Intent(this,ChattingService.class));
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public String getFileDir_Ex() {
		String result = "";
		try {
			int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
			if (sdkVersion < 8) {
				File extSt = Environment.getExternalStorageDirectory();
				result = extSt.getAbsolutePath() + "/Android" + "/data"+ "/kr.co.boardtalks";
			} else {
				result = getExternalFilesDir(null).getAbsolutePath();
			}
		} catch (Exception e) {
			//WriteFileLog.writeException(e);
		}
		return result;
	}
	
	public void getContactList(final Activity context){
		try {
			AsyncTask<Void, Void, JSONArray> getDataWorker = new AsyncTask<Void, Void, JSONArray>() {
				@Override
				protected void onPreExecute() {
					try {
						//mActionHandler.sendEmptyMessage(1);
					} catch (Exception e) {
					}
					super.onPreExecute();
				}
				
				@Override
				protected JSONArray doInBackground(Void... params) {
					
					try {
						
						ContactUtil util = new ContactUtil(context);
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
						updateContact(context, resultvalue.toString());	
					} catch (Exception e) {
						
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
	
	private void updateContact(final Activity context, final String arraycontact){
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		String user_seq =SharedManager.getInstance().getString(this, BaseGlobal.User_Seq);
		String user_phone =SharedManager.getInstance().getString(this, BaseGlobal.User_Phone).replaceAll("-", "").replaceAll("\\s+", "");
		dataList.add(new BasicNameValuePair("user_seq", user_seq));
		dataList.add(new BasicNameValuePair("user_phone", user_phone));
		dataList.add(new BasicNameValuePair("contacts", arraycontact));
		
		
		com.br.chat.util.Request.getInstance().requestHttp(context, dataList, ChatGlobal.updateContact, new com.br.chat.util.Request.OnAfterParsedData(){

			@Override
			public void onResult(boolean result, JSONObject jsonobj) {
				
				
				if(jsonobj != null) Log.e("update_contact", jsonobj.toString() + arraycontact);
				
				try {
					if(result){
						getMember(arraycontact, context);
					}else{
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
	}
	
	private void getMember(String arraycontact,final Activity context){
		Log.e("get_member", "kk");
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();
		String user_seq =SharedManager.getInstance().getString(context, BaseGlobal.User_Seq);
		dataList.add(new BasicNameValuePair("user_seq", user_seq));
		
		com.br.chat.util.Request.getInstance().requestHttp(context, dataList, ChatGlobal.getUsers, new com.br.chat.util.Request.OnAfterParsedData() {

				@Override
				public void onResult(boolean result, JSONObject jsonobj) {
					Log.e("friend_list", jsonobj.toString());
					try {
						if(result){
							JSONArray resultData = jsonobj.getJSONArray("users");
							//MemberVo.PasingJson(resultData,myinfo);
							MemberVo.RealTimePasingJson(resultData);
							ChattingService.getInstance().getMyInfo();
							ChattingService.getInstance().getMember();
							ChattingService.getInstance().getFavorList();
							
							//ChattingApplication.getInstance().memberMap.get(vo.getSendseq()).setUsername(vo.getSendname());
							Intent intent = new Intent("android.intent.action.ReceiverMemberbt");
							context.sendBroadcast(intent);
						//	mActionHandler.sendEmptyMessage(2);
						}else{
							Log.e("get_friend_fail", "kk");
						//	mActionHandler.sendEmptyMessage(3);
						}
					} catch (Exception e) {
						Log.e("get_friend_fail", "kk");
						e.printStackTrace();
					}
				}
			});
	}
	
	
	/**
     * 이미지 로더를 반환한다.
     * @return 이미지 로더
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    

	/**
     * 이미지 로더를 반환한다.
     * @return 이미지 로더
     */
    public com.nostra13.universalimageloader.core.ImageLoader getAUILImageLoader() {
        return auilImageLoader;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        sInstance = null;
    }

	
	
	/**
     * @return ApplicationController singleton instance
     */
    public synchronized static ChattingApplication getInstance() {
        return sInstance;
    }
    
    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     * 
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * 
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * 
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    
}
