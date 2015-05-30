package com.br.chat.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.ActivityImageViewer;
import com.br.chat.adapter.ChatStatusType;
import com.br.chat.adapter.ChatType;
import com.br.chat.aws.AWSFileUploader;
import com.br.chat.aws.AWSFileUploader.OnResultListener;
import com.br.chat.gallery.ImageInternalFetcher;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.setOnItemChoice;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;

public class FileUploadView extends RelativeLayout implements ImageLoaderInterface{

	public Context mContext;
	public ProgressBar bar;
	public TextView textView_time;
	public TopAlignedImageView imageViewPic;
	public ImageView failView;
	public TextView textview_read;
	AWSFileUploader uploader = null;
	public String msgkey ;
	public ArrayList<MessageVo> arraylist ;
	//public ImageInternalFetcher mImageFetcher;
	public FileUploadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public FileUploadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public FileUploadView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context) {
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.activity_chat_list_row_mine_pic, this,true);
		bar = (ProgressBar)view.findViewById(R.id.activityChatListRowMinePic_ProgressBar);
		textView_time = (TextView)view.findViewById(R.id.activityChatListRowMinePic_TextView_time);
		imageViewPic = (TopAlignedImageView)view.findViewById(R.id.activityChatListRowMinePic_ImageView_pic);
		bar = (ProgressBar)view.findViewById(R.id.activityChatListRowMinePic_ProgressBar);
		failView = (ImageView)view.findViewById(R.id.activityChatListRowMinePic_ImageView_fail);
		textview_read = (TextView)view.findViewById(R.id.textview_read);
	//	mImageFetcher = new ImageInternalFetcher(context, 500);
	}
	
	public void setFileUpload(final MessageVo messagevo, ArrayList<MessageVo> _arraylist){
		try {
			arraylist = _arraylist;
			failView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					if(mActivityChat!=null) mActivityChat.getRetryAlert(position);
				}
			});
			imageViewPic.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					
					Intent intent = new Intent(mContext,ActivityImageViewer.class);
					intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
					String msgkey = (String)v.getTag();
					int fileposition = 0;
					ArrayList<MessageVo>messageArray = new ArrayList<MessageVo>();
					for(int i  = 0 ; i< arraylist.size();i++){
						if(arraylist.get(i).getMsgtype() == ChatType.file){
							messageArray.add( arraylist.get(i));
							if(arraylist.get(i).getMsgkey().equals(msgkey)){
								fileposition = messageArray.size()-1;
							}
						}
					}
					ActivityImageViewer.setMessage(messageArray);
					intent.putExtra(ActivityImageViewer.POSITION, fileposition);
					
					mContext.startActivity(intent);
					
				/*	Intent intent = new Intent(mContext,ActivityImageViewer.class);
					intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
					String path = (String)v.getTag();
					intent.putExtra(ActivityImageViewer.PATH, path);
					ActivityImageViewer.setMessage(arraylist);
					mContext.startActivity(intent);*/
				/*	if(mImageViewerLoadingListener!=null && !TextUtils.isEmpty(mArrChatUI.get(position).chat.message))
						mImageViewerLoadingListener.getImageView(mContext.getString(R.string.push_picture)
								, ChatImageURL.getURL(mArrChatUI.get(position).chat.message, mArrChatUI.get(position).chat.chatType, mArrChatUI.get(position).chat.chatTransferStatus));*/
				}
			});
			imageViewPic.setTag(messagevo.getMsgkey());
			if(messagevo.getStatus()==ChatStatusType.Send){
				
				imageLoader.displayImage("file://"+ messagevo.getMsg(),imageViewPic,imageLoaderOption, animationListener);
				
				//if(!msgkey.equals(messagevo.getMsgkey())) {
					bar.setVisibility(View.VISIBLE);
					failView.setVisibility(View.GONE);
					//if(uploader == null){
						Log.e("", "파일 새로 보내는 준비중");
						uploader = new AWSFileUploader(mContext);
						uploader.setAwsAccesskey(ChatGlobal.AMAZON_A3_ACCESS_KEY);
						uploader.setAWSBuckekName(ChatGlobal.AMAZON_A3_BUCKET_NAME);
						uploader.setAwsSecretKey(ChatGlobal.AMAZON_A3_SECRET_KEY);
						uploader.setFilePath(messagevo.getMsg());
						uploader.setProgressBar(bar);
						uploader.setListener(new OnResultListener() {
							@Override
							public boolean setOnResultListener(boolean arg0, String arg1) {
								ChattingApplication.getInstance().socket.SendFileMessage(messagevo.getMemberseq(),messagevo.getMsgkey(),  messagevo.getSendseq(), messagevo.getMsg(),
										SharedManager.getInstance().getString(mContext, BaseGlobal.User_name),ChatType.file,messagevo.getRoomseq(),messagevo.getRoomtype(),messagevo.getRevname(),messagevo.getMembername(),arg1);
								imageLoader.displayImage(arg1,imageViewPic,imageLoaderOption, animationListener);
								return false;
							}
						});
						uploader.setMsgKey(messagevo.getMsgkey());
						messagevo.setStatus(ChatStatusType.SendIng);
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
							uploader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
						}else{
							uploader.execute((Void[])null);
						}
						//uploader.execute();
						
					//}
					
				//}
				//msgkey = messagevo.getMsgkey();
			//	imageViewPic.setImageURI(Uri.fromFile(new File(messagevo.getMsg())));
				
			}else if(messagevo.getStatus()==ChatStatusType.SendFail){
				bar.setVisibility(View.GONE);
				failView.setVisibility(View.VISIBLE);
				failView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String[] arrays = {"재전송","삭제"};
						BrUtilManager.getInstance().ShowArrayDialog(mContext, null, arrays, -1, new setOnItemChoice() {
							@Override
							public void setOnItemClick(int arg0) {
								
							}
						});
					}
				});
			}else if(messagevo.getStatus() == ChatStatusType.SendSuc){
				
				bar.setVisibility(View.GONE);
				failView.setVisibility(View.GONE);
				//mImageFetcher.loadImage(Uri.messagevo.getMsg(), imageViewPic);
				imageLoader.displayImage(messagevo.getMsg(),imageViewPic,imageLoaderOption,animationListener);
				
			}else{
				Log.e("", "파일 새로 보내는중");
				imageLoader.displayImage("file://"+ messagevo.getMsg(),imageViewPic,imageLoaderOption, animationListener);
			}
			if(messagevo.getStatus()!=ChatStatusType.SendFail){
				if(messagevo.getCnt() > 0){
					textview_read.setText(String.valueOf(messagevo.getCnt()));
					textview_read.setVisibility(View.VISIBLE);
				}else{
					//viewHolder.textview_read.setText(mArrChatUI.get(position).getCnt() );
					textview_read.setVisibility(View.GONE);
				}
			}else{
				textview_read.setVisibility(View.GONE);
			}
			
			//imageLoader.displayImage(messagevo.getMsg(),imageViewPic,imageLoaderOption, animationListener);
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
