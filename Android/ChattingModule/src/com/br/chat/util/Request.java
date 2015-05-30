package com.br.chat.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;


public class Request {
	static Request request = null;
	static String baseUrl = "";

	static int CONNECT_TIMEOUT = 25000;
	
	public static Request getInstance() {		
		if(request == null) {
			request = new Request();			
		}		
		return request;
	}
	
	public static void setBaseUrl(String value) {
		baseUrl = value;
	}
	
	public String getBaseUrl() {		
		return baseUrl;
	}
	
	CookieStore cookieStore = new BasicCookieStore(); 
	BasicHttpContext httpContext = new BasicHttpContext(); 
    public BasicHttpContext getHttpContext() {   	 
    	try {
    		if (httpContext == null) {
    			httpContext = new BasicHttpContext();     		
    		}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
    	return httpContext;
    }
    
    
    public CookieStore getCookieStore() {   	 
    	try {
    		if (cookieStore == null) {
    			cookieStore = new BasicCookieStore();     		
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return cookieStore;
    }
        
    
    public void createNewCookie() {
    	httpContext = new BasicHttpContext();
    	cookieStore = new BasicCookieStore();    	
    }
    
	 
	
	@SuppressWarnings("deprecation")
	public  String request(DefaultHttpClient c, HttpUriRequest req) throws Exception {
		String result = "";
		try {
			Request.getInstance().getHttpContext().setAttribute(ClientContext.COOKIE_STORE
					, Request.getInstance().getCookieStore());
			HttpResponse response = c.execute(req, Request.getInstance().getHttpContext());
			InputStream stream = response.getEntity().getContent();
			java.io.ByteArrayOutputStream bos = new ByteArrayOutputStream();			
			byte[] buff = new byte[5120];
			do{
				int count = stream.read(buff, 0, 5120);
				if(count<=0)
					break;
				bos.write(buff,0,count);				
			}while(true);
			result = new String(bos.toByteArray()); //, "UTF-8"			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
			throw e;
		}
		return result;		
	}
	
	@SuppressWarnings("deprecation")
	public ResponseJson requestService(Context context, String procName, JSONObject json, int timeOut) throws Exception{
		ResponseJson result = new ResponseJson();

		try {
			String baseurl = Request.getInstance().getBaseUrl();
			String url = baseurl + procName;
						
			DefaultHttpClient c = new DefaultHttpClient();			
			
			HttpConnectionParams.setConnectionTimeout(c.getParams(), timeOut);
			
			HttpPost post = new HttpPost(url);
			
//			post.setHeader("userid", Myinfo.getId(context));
//			post.setHeader("authCode", Myinfo.getPw(context));
			
			StringEntity entity = new StringEntity(json.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			
			String httpResponse = request(c, post);
			
			result.setReponse(httpResponse);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
			throw e;
		}

		return result;
	}
	
	@SuppressWarnings("deprecation")
	public JSONObject requestService(Context context, String procName, List<NameValuePair> data) throws Exception{
		//ResponseJson result = new ResponseJson();
		JSONObject result = new JSONObject();

		try {
		//	String baseurl = Request.getInstance().getBaseUrl();
			String url = procName;
						
			DefaultHttpClient c = new DefaultHttpClient();			
			
			HttpConnectionParams.setConnectionTimeout(c.getParams(), CONNECT_TIMEOUT);
			//HttpConnectionParams.setSoTimeout(c.getParams(), _socketTimeout);
			
			HttpPost post = new HttpPost(url);
			
			//post.setHeader("userid", Myinfo.getId(context));
			//post.setHeader("authCode", Myinfo.getPw(context));
			
			//StringEntity entity = new StringEntity(data, HTTP.UTF_8);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data,HTTP.UTF_8);
			//entity.setContentType("application/json");
			post.setEntity(entity);
			
			
			String httpResponse = request(c, post);
			
			result = new JSONObject(httpResponse);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
			throw e;
		}

		return result;
	}
	
	public static int getIntFromJson(JSONObject jsonObj, String property) {
		int result = 0;
		try {
			Object tmpObj = jsonObj.get(property);
			String tmpStr = String.valueOf(tmpObj);					
			result = Integer.valueOf(tmpStr);			 
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return result;
	}
	
	public interface OnAfterParsedData{
		void onResult(boolean result, JSONObject jsonobj);
	}
	
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
	JSONObject rgb = null;
	public void requestHttp(final Activity _activity,final List<NameValuePair> data,final String httpurl ,  final OnAfterParsedData onAfter){
		try {
			
			AsyncTask<Void, Void, Void> executer = new AsyncTask<Void, Void, Void>() {
				@Override
				protected void onPostExecute(Void result) {
					mActionHandler.sendEmptyMessage(1);
					try {
						if(rgb != null){
							onAfter.onResult(true, rgb);	
							/*String resultData = rgb.getString("data");
							String myinfo = rgb.getString("myinfo");
							if(resultData.length() > 5){
							
							}*/
						}else{
							
							onAfter.onResult(false, null);	
						}	
					} catch (Exception e) {
						WriteFileLog.writeException(e);
						onAfter.onResult(false, null);	
					}
					
					super.onPostExecute(result);
				}
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					activity = _activity;
					mActionHandler.sendEmptyMessage(1);
				}
				
				@Override
				protected Void doInBackground(Void... params) {
					try {
						rgb = requestService(_activity,httpurl, data);	
						if(rgb != null){
							return null;
						}
					} catch (Exception e) {
						WriteFileLog.writeException(e);
					}
					return null;
				}
				
			};
			executer.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	@SuppressLint("NewApi") public void requestHttp2(final Activity _activity,final List<NameValuePair> data,final String httpurl ,  final OnAfterParsedData onAfter){
		try {
			
			AsyncTask<Void, Void, Void> executer = new AsyncTask<Void, Void, Void>() {
				@Override
				protected void onPostExecute(Void result) {
					//mActionHandler.sendEmptyMessage(1);
					try {
						mActionHandler.sendEmptyMessage(1);
						if(rgb != null){
							onAfter.onResult(true,rgb);
						}else{
							onAfter.onResult(false, null);	
						}	
					} catch (Exception e) {
						WriteFileLog.writeException(e);
						onAfter.onResult(false,null);	
					}
					
					super.onPostExecute(result);
				}
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					activity = _activity;
					mActionHandler.sendEmptyMessage(1);
				}
				
				@Override
				protected Void doInBackground(Void... params) {
					try {
						rgb = requestService(_activity,httpurl, data);	
						if(rgb != null){
							return null;
						}
					} catch (Exception e) {
						WriteFileLog.writeException(e);
					}
					return null;
				}
				
			};
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
				executer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
			}else{
				executer.execute((Void[])null);
			}

		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	

	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //성공
				showProgress(activity);
				break;
			}
		}
	};
	
	
}
