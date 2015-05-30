package com.bxm.boardtalks.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.brainyxlib.http.BrHttpManager;
import com.brainyxlib.util.HttpRequestor;
import kr.co.boardtalks.R;

public class HttpMultiUtil {
	
	private static HttpMultiUtil instance;
	
	public static HttpMultiUtil getInstance() {
		if (instance == null) {
			instance = new HttpMultiUtil();
		}
		return instance;
	}
	
	private Dialog dialog;
	private Activity activity;
	@SuppressWarnings("unchecked")
	public void requestHttp(final Activity _activity, Map<String, Object> data, final OnAfterParsedData onAfter){
		AsyncTask<Map<String,Object>, Integer, Boolean> executer = new AsyncTask<Map<String,Object>, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				activity = _activity;
				mActionHandler.sendEmptyMessage(1);
				//showProgress(activity);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				mActionHandler.sendEmptyMessage(1);
			}
			
			
			@Override
			protected Boolean doInBackground(Map<String, Object>... params) {
				boolean isfile = false;
				Map<String, Object> set = params[0];
				if (set == null) {
					return null;							
				}
				
				InputStream is = null;
				try {
					String url = (String) set.get(KEY_URL); 
					set.remove(KEY_URL);
					HttpRequestor http = BrHttpManager.getInstance().getHttpRequestor(url);
					
					if (!set.isEmpty()) {
						for (String key : set.keySet()) {
							if (!key.equals(KEY_URL)) {
								Object value = set.get(key);
								if (value instanceof String) {
									http.addParameter(key, value.toString());
								}else if (value instanceof File) {
									 isfile = true;
									http.addFile(key, (File)value);
								}else if (value instanceof ArrayList) {
									 isfile = true;
									 ArrayList<File>files = (ArrayList<File>)value;
									 for(int i = 0 ; i < files.size();i++){
										 http.addFile(key, files.get(i));	 
									 }
									
								}else {
									if (value == null) {
										continue;
									}
									http.addParameter(key, value.toString());
								}
							}
						}
					}
					
					if(isfile){
						is = http.sendMultipartPost();
					}else{
						is = http.sendPost();
					}
					
					String[] resultvalue = ResultToJson(is);
					if(resultvalue != null){
						onAfter.onResult(Boolean.valueOf(resultvalue[0]), resultvalue[1]);	
					}
					
				} catch (Exception e){
					WriteFileLog.writeException(e);
				}
				return null;
			}
			
		};
		executer.execute(data);
	}
	
	public void requestHttp2(final Activity _activity, Map<String, Object> data, final OnAfterParsedData2 onAfter){
		AsyncTask<Map<String,Object>, Integer, Boolean> executer = new AsyncTask<Map<String,Object>, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				activity = _activity;
				mActionHandler.sendEmptyMessage(1);
				//showProgress(activity);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				mActionHandler.sendEmptyMessage(1);
			}
			
			
			@Override
			protected Boolean doInBackground(Map<String, Object>... params) {
				boolean isfile = false;
				Map<String, Object> set = params[0];
				if (set == null) {
					return null;							
				}
				
				InputStream is = null;
				try {
					String url = (String) set.get(KEY_URL); 
					set.remove(KEY_URL);
					HttpRequestor http = BrHttpManager.getInstance().getHttpRequestor(url);
					
					if (!set.isEmpty()) {
						for (String key : set.keySet()) {
							if (!key.equals(KEY_URL)) {
								Object value = set.get(key);
								if (value instanceof String) {
									http.addParameter(key, value.toString());
								}else if (value instanceof File) {
									 isfile = true;
									http.addFile(key, (File)value);
								}else if (value instanceof ArrayList) {
									 isfile = true;
									 ArrayList<File>files = (ArrayList<File>)value;
									 for(int i = 0 ; i < files.size();i++){
										 http.addFile(key, files.get(i));	 
									 }
									
								}else {
									if (value == null) {
										continue;
									}
									http.addParameter(key, value.toString());
								}
							}
						}
					}
					
					if(isfile){
						is = http.sendMultipartPost();
					}else{
						is = http.sendPost();
					}
					
					String[] resultvalue = ResultToJson2(is);
					if(resultvalue != null){
						onAfter.onResult(Boolean.valueOf(resultvalue[0]), resultvalue[1],resultvalue[2]);	
					}
					
				} catch (Exception e){
					WriteFileLog.writeException(e);
				}
				return null;
			}
			
		};
		executer.execute(data);
	}
	
	

	
	
	/**
     * 
     * @param is
     * @return
     */
    public String[] ResultToJson(InputStream is) {
    	String[] resultdata = new String[2];
		String data = null;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				JSONObject obj = new JSONObject(builder.toString());
				if(obj.toString().length()>10){
					resultdata[0] = "true";	
				}
				resultdata[1] = obj.getString("data");
				
				/*resultdata[0] = obj.getString("result");
				resultdata[1] = obj.getString("message");*/
			} catch (JSONException e) {
				WriteFileLog.writeException(e);
				return null;
			}
		} catch (IOException e) {
			WriteFileLog.writeException(e);
		}
		return resultdata;
	}
	
    /**
     * 
     * @param is
     * @return
     */
    public String[] ResultToJson2(InputStream is) {
    	String[] resultdata = new String[3];
		String data = null;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				JSONObject obj = new JSONObject(builder.toString());
				if(obj.toString().length()>10){
					resultdata[0] = "true";	
				}
				resultdata[1] = obj.getString("data");
				resultdata[2] = obj.getString("cnt");
				/*resultdata[0] = obj.getString("result");
				resultdata[1] = obj.getString("message");*/
			} catch (JSONException e) {
				WriteFileLog.writeException(e);
				return null;
			}
		} catch (IOException e) {
			WriteFileLog.writeException(e);
		}
		return resultdata;
	}
	protected void showProgress(Activity ctx) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				return;
			}
			
			dialog = new Dialog(ctx, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(ctx));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				showProgress(activity);
				
				break;
			case 2:
				break;
			}
		}
	};
	

	public static final String KEY_URL = "url";
	
	public static final String KEY_DATA = "data";
	public static final String KEY_RESULT = "result";
	public static final String KEY_MSG = "message";
	/** ����¡�� ������ */
	public static final String KEY_PAGE = "paging";
	
	/**
	 * @author ���� ������ �ޱ�
	 */
	public interface OnAfterParsedData{
		void onResult(boolean result, String resultData);
	}
	
	public interface OnAfterParsedData2{
		void onResult(boolean result, String resultData,String count);

		
	}
	public class PageVO {
		int totalCnt 	= 0;
		int curPage 	= 0;
		public int getTotalCnt() {
			return totalCnt;
		}
		public void setTotalCnt(int totalCnt) {
			this.totalCnt = totalCnt;
		}
		public int getCurPage() {
			return curPage;
		}
		public void setCurPage(int curPage) {
			this.curPage = curPage;
		}
	}
}
