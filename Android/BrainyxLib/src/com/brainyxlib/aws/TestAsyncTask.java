package com.brainyxlib.aws;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.brainyxlib.aws.CustomMultipartEntity.ProgressListener;

class TestAsyncTask extends AsyncTask<HttpResponse, Integer, Long> {
	Context context;
	public TestAsyncTask(Context _context){
		context = _context;
	}
	
	private ProgressDialog mDialog ;
	long totalSize;
	@Override
	protected Long doInBackground(HttpResponse... arg0) {
		String url = "URL정보 입력";
		//파라미터 등록
		Map<String, Object> params = new HashMap<String, Object>();
		//파일파라미터 등록
		Map<String, File> fileParams = new HashMap<String, File>();
		
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);
			CustomMultipartEntity multipart = new CustomMultipartEntity(new ProgressListener() {
				@Override
				public void transferred(long transferred) {
					// TODO Auto-generated method stub
					publishProgress((int)transferred);
				}
			});
			
			//Params 첨부
			for (String strKey : params.keySet()) {
				StringBody body = new StringBody(params.get(strKey).toString());
				multipart.addPart(strKey, body);
			}
			
			//파일첨부
			for (String keys : fileParams.keySet()) {
				multipart.addPart(keys , new FileBody(fileParams.get(keys)));
			}
			
			totalSize = multipart.getContentLength();
			//Time Check
			mDialog.setMax((int)totalSize);
			httpPost.setEntity(multipart);
			HttpResponse response = httpClient.execute(httpPost,httpContext);
			InputStream is = response.getEntity().getContent();
			/**
				is를 가지고 추가 작업
			 */
		} catch (Exception e) {
			return 0L;
		}
		
		return 0L;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		mDialog.dismiss();
	}

	@Override
	protected void onPostExecute(Long result) {
		super.onPostExecute(result);
		mDialog.dismiss();
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = new ProgressDialog(context);
		mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mDialog.setCancelable(true);
		//mDialog.setOnCancelListener(cancelListener);
		mDialog.setMessage("업로드중입니다.");
		mDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		//Progress 업데이트
		mDialog.setProgress((int)progress[0]);
	}
	/*
	OnCancelListener cancelListener = new OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			
		}
	};*/
}
