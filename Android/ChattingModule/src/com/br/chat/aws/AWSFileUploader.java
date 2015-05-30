package com.br.chat.aws;

import java.io.File;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.chat.ChattingApplication;
import com.brainyxlib.image.BrImageUtilManager;

public class AWSFileUploader extends AsyncTask<Void, Integer, Boolean>{

	public interface OnResultListener{
		public boolean setOnResultListener(boolean result, String uploadpath);
	}
	
	public OnResultListener listener;
	public void setListener(OnResultListener _listener){
		listener =  _listener;
	}
	private String bucketname = "";
	private String accesskey = "";
	private String filepath = "";
	private String secretkey = "";
	private String filename = "";
	public long transfer = 0;
	public ProgressBar progresbar;
	public String msgkey ;
	public String resultPath = "";
			
			
	/**
	 * 파일서버 폴더이름
	 * @param _bucketname
	 */
	Context context;
	public AWSFileUploader (Context _context){
		context = _context;
	}
	
	public void setAWSBuckekName(String _bucketname){
		bucketname = _bucketname;
	}
	public void setAwsSecretKey(String _secretkey){
		secretkey = _secretkey;
	}
	
	public void setAwsAccesskey(String _accesskey){
		accesskey = _accesskey;
	}
	
	public void setFilePath(String _filepath){
		filepath = _filepath;
	}
	
	public void setFilename(String _filename){
		filename = _filename;
	}
	
	public void setMsgKey(String _msgkey){
		msgkey  = _msgkey;
	}
	
	public void setProgressBar(ProgressBar _bar){
		progresbar = _bar;
	}
	
	public String SaveImg(String filepath,boolean album,String filename) {
		Bitmap bitmap = BrImageUtilManager.getInstance().getBitmap(filepath, album,800,600);
		File testfile = new File(ChattingApplication.getInstance().getFileDir_Ex() + filename + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(context,bitmap,getImageUri(testfile.getAbsolutePath()));
		if (!flag)
			return "";
		
		return  testfile.getAbsolutePath();
	}
	
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			//if(ChattingApplication.getInstance().credentials == null){
			BasicAWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);
				AmazonS3  s3 = new AmazonS3Client(credentials);
			//}
			//BasicAWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);
		     //  AmazonS3 s3 = new AmazonS3Client(credentials);
		   	// 파일 업로드 부분 파일 이름과 경로를 동시에 넣어줌.
		     // PutObjectRequest putObjectRequest = null;
		      File uploadfile = new File(filepath);
		      //String realpath =  SaveImg(filepath, true, uploadfile.getName());
		      //uploadfile = new File(realpath);
		      Calendar calendar = Calendar.getInstance();
		      int year = calendar.get(Calendar.YEAR);
		      int month = calendar.get(Calendar.MONTH)+1;
		      int day = calendar.get(Calendar.DAY_OF_MONTH);
		      PutObjectRequest putObjectRequest = new PutObjectRequest(bucketname, String.valueOf(year)+"-"+ String.valueOf(month) +"-" +String.valueOf(day)+"/"+msgkey+ "/"+uploadfile.getName(),uploadfile);
		      putObjectRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite); // URL 접근시 권한 읽을수 있도록 설정.
	          progresbar.setMax((int)uploadfile.length());
	          putObjectRequest.setProgressListener(new ProgressListener() {
					@Override
					public void progressChanged(ProgressEvent arg0) {
						// TODO Auto-generated method stub
						long type  = arg0.getBytesTransferred();
						transfer = transfer + type;
						publishProgress((int)transfer);
					}
		          });
	          resultPath = "http://s3-" + s3.getBucketLocation(bucketname)+".amazonaws.com/"+bucketname+"/"+String.valueOf(year)+"-"+String.valueOf(month) +"-" +String.valueOf(day)+"/"+msgkey+"/"+uploadfile.getName();
	          s3.putObject(putObjectRequest);
	         
	          System.out.println("Uploadinf OK");
	          return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		try {
			progresbar.setProgress((int)progress[0]);	
		} catch (Exception e) {e.printStackTrace();}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
		/*	mDialog = new ProgressDialog(context);
			mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDialog.setCancelable(false);
			mDialog.setMessage("업로드중입니다.");
			mDialog.show();	*/
		} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		try {
		//	mDialog.cancel();
			if(listener != null){
				listener.setOnResultListener(result,resultPath);
			}	
		} catch (Exception e) {e.printStackTrace();}
		
	}
}
