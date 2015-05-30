package com.br.chat.socket;

import java.io.File;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.chat.ChatGlobal;
import com.br.chat.util.WriteFileLog;

public class AWSFileUploader extends AsyncTask<Void, Integer, ArrayList<String>>{

	public interface OnResultListener{
		public boolean setOnResultListener(boolean result);
		public void setOnResult(ArrayList<String> result);
	}
	
	public OnResultListener listener;
	public void setListener(OnResultListener _listener){
		listener =  _listener;
	}
	private String bucketname = ChatGlobal.AMAZON_A3_BUCKET_NAME;
	private String accesskey = ChatGlobal.AMAZON_A3_ACCESS_KEY;
	private String filepath = "";
	private String secretkey = ChatGlobal.AMAZON_A3_SECRET_KEY;
	private String filename = "";
	private String folderName = "iss72002/";
	
	private ProgressDialog mDialog;
	private String[] filesName = {};
	private ArrayList<File> files  = new ArrayList<File>();
	private String[] filesPath = {};
	ArrayList<String> result = new ArrayList<String>();
	public long transfer = 0;
	/**
	 * 파일서버 폴더이름
	 * @param _bucketname
	 */
	Context context;
	public AWSFileUploader (Context _context){
		context = _context;
	}
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}



//	public void setAWSBuckekName(String _bucketname){
//		bucketname = _bucketname;
//	}
//	public void setAwsSecretKey(String _secretkey){
//		secretkey = _secretkey;
//	}
//	
//	public void setAwsAccesskey(String _accesskey){
//		accesskey = _accesskey;
//	}
	
	public void setFilePath(String _filepath){
		filepath = _filepath;
	}
	
	public void setFilename(String _filename){
		filename = _filename;
	}
	
	public void setFiles(String[] files) {
		filesName = files;
	}
	
	public void setFilesPath(String[] filesPath) {
		this.filesPath = filesPath;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected ArrayList<String> doInBackground(Void... params) {
		try {
			
			BasicAWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);
		       AmazonS3 s3 = new AmazonS3Client(credentials);
		   	// 파일 업로드 부분 파일 이름과 경로를 동시에 넣어줌.
		       boolean video = false;
		       boolean audio = false;
		       boolean videoThumb = false;
		       for(int i = 0 ; i < filesName.length ; i ++) {
		    	   PutObjectRequest putObjectRequest = null;
		    	   if(filesName[i].indexOf("audio::") > -1) {
		    		   filesName[i]= filesName[i].replaceAll("audio::", "");
		    		   audio = true;
		    	   }
		    	   else {
		    		   audio = false;
		    	   }
		    	   
		    	   if(filesName[i].indexOf("video::") > -1) {
		    		   filesName[i]= filesName[i].replaceAll("video::", "");
		    		   video = true;
		    	   }
		    	   else {
		    		   video = false;
		    	   }
		    	   
		    	   if(filesName[i].indexOf("videoThumb::") > -1) {
		    		   filesName[i]= filesName[i].replaceAll("videoThumb::", "");
		    		   videoThumb = true;
		    	   }
		    	   else {
		    		   videoThumb = false;
		    	   }
				      File uploadfile = new File(filesPath[i]+filesName[i]);
				      putObjectRequest = new PutObjectRequest(bucketname, folderName+ "/"+filesName[i], uploadfile);
				      
				 
				      
			          putObjectRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite); // URL 접근시 권한 읽을수 있도록 설정.
			          mDialog.setMax((int)uploadfile.length());
			          putObjectRequest.setProgressListener(new ProgressListener() {
							@Override
							public void progressChanged(ProgressEvent arg0) {
								// TODO Auto-generated method stub
								long type  = arg0.getBytesTransferred();
								transfer = transfer + type;
								publishProgress((int)transfer);
							}
				          });
			          String kind = "";
			          if(audio) kind = "audio::";
			          if(video) kind = "video::";
			          if(videoThumb) kind = "videoThumb::";
			          String filePath = kind + "http://s3-" + s3.getBucketLocation(bucketname)+".amazonaws.com/"+bucketname+"/"+folderName+"/"+filesName[i];
			          s3.putObject(putObjectRequest);
			          System.out.println("Uploadinf OK");
			          result.add(filePath);
		       }
		       return result;
		     
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		//	WriteFileLog.writeException(e);
			return null;
		}
		
//		for(int i = 0 ; i < filesName.length ; i ++) {
//			File f = new File(filepath+filename);
//			files.add(f);
//		}
//		
//		File directory = new File(filepath);
//		AmazonS3 s3 = new AmazonS3Client(new ProfileCredentialsProvider());
////		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(
////		                                                    bucketname, accesskey);
////		InitiateMultipartUploadResult initResponse = s3.initiateMultipartUpload(initRequest);
////		File file = new File(filepath);
//		
//		ListMultipartUploadsRequest allMultpartUploadsRequest = 
//			     new ListMultipartUploadsRequest (bucketname);
//			MultipartUploadListing multipartUploadListing = 
//			     s3.listMultipartUploads (allMultpartUploadsRequest);
			
//		long contentLength = file.length();
//		long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.
//		long filePosition = 0;
//		for (int i = 1; filePosition < contentLength; i++) {
//	        // Last part can be less than 5 MB. Adjust part size.
//	    	partSize = Math.min(partSize, (contentLength - filePosition));
//	    	
//	        // Create request to upload a part.
//	        UploadPartRequest uploadRequest = new UploadPartRequest()
//	            .withBucketName(bucketname).withKey(accesskey)
//	            .withUploadId(initResponse.getUploadId()).withPartNumber(i)
//	            .withFileOffset(filePosition)
//	            .withFile(file)
//	            .withPartSize(partSize);
//
//	        // Upload part and add response to our list.
//	        partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
//
//	        filePosition += partSize;
//	    }
		
//		return "";
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		try {
			mDialog.setProgress((int)progress[0]);	
		} catch (Exception e) {WriteFileLog.writeException(e);}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			mDialog = new ProgressDialog(context);
			mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDialog.setCancelable(false);
			mDialog.setMessage("업로드중입니다.");
			mDialog.show();	
		} catch (Exception e) {WriteFileLog.writeException(e);}
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		try {
			mDialog.cancel();
			for(String path : result) {
				Log.d("TAG", path);
			}

			if(listener != null){
//				listener.setOnResultListener(result);
				listener.setOnResult(result);
			}	
		} catch (Exception e) {WriteFileLog.writeException(e);}
		
	}
}
