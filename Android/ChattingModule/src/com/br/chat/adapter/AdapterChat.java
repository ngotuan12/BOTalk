package com.br.chat.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.ActivityImageViewer;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.activity.Activity_Profile;
import com.br.chat.gallery.ImageInternalFetcher;
import com.br.chat.service.ChattingService;
import com.br.chat.util.Mylog;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.CircleImageView1;
import com.br.chat.view.FileUploadView;
import com.br.chat.view.ImageLoaderInterface;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.setOnItemChoice;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;
import com.rockerhieu.emojicon.EmojiconTextView;

@SuppressLint("ResourceAsColor")
public class AdapterChat extends BaseAdapter implements StickyListHeadersAdapter , ImageLoaderInterface{
	private final String TAG = "AdapterChat";
	private Context mContext;
	private ArrayList<MessageVo> mArrChatUI;
	//private String mItemName = "";
	private Activity_Chat mActivityChat;
	private int sizeCircle = 0; 
	private String mDate = "";
	
	public FaceViewClickListner faceviewclicklistner;
	public FailMsgSend failsendlistner;
	public String times = "";
	//private CPImageLoader mCPImageLoader;
	//private ImageViewerLoadingListener mImageViewerLoadingListener;
	
//	public static SparseArray<WeakReference<View>> viewArray;
	//private HashMap<String, WeakReference<View>> viewArray;
	public interface FaceViewClickListner{
		public void setOnFaceViewClickListner(Intent intent);
	}
	public void setFaceViewClickListner(FaceViewClickListner faceviewclicklistner){
		this.faceviewclicklistner = faceviewclicklistner;
	}
	
	public interface FailMsgSend{
		public void setOnFailMsgSendListner(String key);
	}
	
	public void setOnFailMsgSendListner(FailMsgSend failsendlistner){
		this.failsendlistner = failsendlistner;
	}
	//public ImageInternalFetcher mImageFetcher;
	public AdapterChat(Context context,  ArrayList<MessageVo> arrChatUI/*, ImageViewerLoadingListener imageViewerLoadingListener*/) {
		mContext = context;
		//mCPImageLoader = new CPImageLoader(context);
		mArrChatUI = arrChatUI;
		//mImageViewerLoadingListener = imageViewerLoadingListener;
		//mItemName = (new VHAccountManager()).getLoginUser(context).itemName;
		mActivityChat = Activity_Chat.getInstance();
		sizeCircle  = context.getResources().getDimensionPixelSize(R.dimen.list_item_user_image1);
		//mImageFetcher = new ImageInternalFetcher(context, 500);
		//this.viewArray = new SparseArray<WeakReference<View>>(mArrChatUI.size());
		//this.viewArray = new SparseArray<WeakReference<View>>();
		//viewArray = new HashMap<String, WeakReference<View>>();
	}
	
	public class HeaderViewHolder{
		TextView headertextview;
	}
	
	@Override
    public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
       // Build your custom HeaderView
       //In this case I will use a Card, but you can use any view
		HeaderViewHolder holder;
		 if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(mContext);
	            convertView = mInflater.inflate(R.layout.list_header_chat, null);
	            holder = new HeaderViewHolder();
	            convertView.setTag(holder);
	        } else {
	            holder = (HeaderViewHolder) convertView.getTag();
	        }
		try {
			holder.headertextview = (TextView)convertView.findViewById(R.id.listHeaderChat_TextView_header);
		        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
		        Date dateNew = sdf.parse(mArrChatUI.get(position).getMsgregdate());
		        
		        SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy년 MM월 dd일");
				String dateString = format1.format(dateNew);
		   	 	
				holder.headertextview.setText(dateString);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
   
        return convertView;
    }
	
    @Override
     public long getHeaderId(int position) {
    	try {
    			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
		        Date dateNew = sdf.parse(mArrChatUI.get(position).getMsgregdate());
		        	
		        SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyyMMdd");
				String dateString = format1.format(dateNew);
				
    		//SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
       	 	//Date dateNew = sdf.parse(String.valueOf(mArrChatUI.get(position).getMsgregdate()));
       	  return Long.parseLong(dateString);
		} catch (Exception e) {
			//WriteFileLog.writeException(e);
		}
    	 
    	  return 0;
    }
	
    private static final int TYPE_MINE_Text = 0;
    private static final int TYPE_MINE_Pic = 1;
    private static final int TYPE_FRIENDS_Text = 2;
    private static final int TYPE_FRIENDS_Pic = 3;
    private static final int TYPE_INVITATION = 4;
    private static final int TYPE_MAX_COUNT = 5;
   
	@Override
    public int getItemViewType(int position) {
		int mode = TYPE_MINE_Text;
		if(/*mArrChatUI.get(position).getSendtype()==ChatType.invitation_text
				|| mArrChatUI.get(position).getSendtype()==ChatType.invitation_pic
				||*/ mArrChatUI.get(position).getMsgtype()==ChatType.leave || mArrChatUI.get(position).getMsgtype() == ChatType.add){
			mode = TYPE_INVITATION;
		}else{
			if(SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq).equals(mArrChatUI.get(position).getSendseq())){ //내가보낸거
				if(mArrChatUI.get(position).getMsgtype()==ChatType.file)
					mode = TYPE_MINE_Pic;
				else
					mode = TYPE_MINE_Text;
			}else{
				if(mArrChatUI.get(position).getMsgtype()==ChatType.file)
					mode = TYPE_FRIENDS_Pic;
				else
					mode = TYPE_FRIENDS_Text;
			}
			/*if(mItemName.equals(mArrChatUI.get(position).chat.userItemName)){
				if(mArrChatUI.get(position).chat.chatType==ChatType.pic)
					mode = TYPE_MINE_Pic;
				else
					mode = TYPE_MINE_Text;
			}else{
				if(mArrChatUI.get(position).chat.chatType==ChatType.pic)
					mode = TYPE_FRIENDS_Pic;
				else
					mode = TYPE_FRIENDS_Text;
			}*/
		}
			
		
		return mode;
    }
	
	@Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }
	public void updateview(){
		//viewArray.clear();
		//viewArray.remove(msgkey);
		//viewArray.remove(mArrChatUI.size());
		this.notifyDataSetChanged();
	}
	private boolean clicked = false;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		/*if(convertView != null ){
			//View viewhold = (View)convertView.getTag();
			if(viewArray != null && viewArray.get(convertView.getId()) != null) {
				convertView = viewArray.get(convertView.getId()).get();
					if(convertView != null){
						return convertView;
					}
			}
		}*/
		
		int type = getItemViewType(position);
		if(TYPE_INVITATION==type){
			//ViewHolderInvitation viewHolder;
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_invitation, parent, false);
			View invitation = ViewHolderInvitation.get(convertView, R.id.invitation_layout);
			//viewHolder = new ViewHolderInvitation();
			TextView textViewMessage = (TextView)invitation.findViewById(R.id.activityChatListRowInvitation_TextView_text);
			
			StringBuilder sb = new StringBuilder();
			//convertView.setTag(viewHolder);
			
			if(mArrChatUI.get(position).getMsgtype()==ChatType.leave){
				/*viewHolder.textViewMessage.setText( String.format(mContext.getString(R.string.leftMessage)
						, getName(mArrChatUI.get(position).getSendseq())));*/
				textViewMessage.setText(String.format(mContext.getString(R.string.leftMessage)
						,mArrChatUI.get(position).getSendname()));
			}else{
				
				String[] split = mArrChatUI.get(position).getMsg().split(",");
				
		/*		viewHolder.textViewMessage.setText( String.format(mContext.getString(R.string.invitationMessage)
						, mArrChatUI.get(position).getSendname(),split[1]) );	*/
				textViewMessage.setText( String.format(mContext.getString(R.string.invitationMessage)
						, mArrChatUI.get(position).getSendname(),	getAddMessage(split[1]) ));
			
			}
			
			
		}else if(TYPE_FRIENDS_Text==type){
			
			final ViewHolderFriends viewHolder;
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_friends_text, parent, false);
			viewHolder = new ViewHolderFriends();
			viewHolder.linearLayout_messageBox = (LinearLayout)convertView.findViewById(R.id.activityChatListRowFriendsText_LinearLayout_messageBox);
			viewHolder.textView_time = (TextView)convertView.findViewById(R.id.activityChatListRowFriendsText_TextView_time);
			viewHolder.textViewMessage = (EmojiconTextView)convertView.findViewById(R.id.activityChatListRowFriendsText_TextView_message);
			viewHolder.textViewNickName = (TextView)convertView.findViewById(R.id.activityChatListRowFriendsText_TextView_nickName);
			viewHolder.circleImageView1_face = (CircleImageView1)convertView.findViewById(R.id.activityChatListRowFriendsText_CircleImageView1_face);
			viewHolder.circleImageView1_face.setChatRoom(true);
			viewHolder.textview_read = (TextView)convertView.findViewById(R.id.readtextview);
			convertView.setTag(viewHolder);
			
			viewHolder.linearLayout_messageBox.setOnLongClickListener(new View.OnLongClickListener() {
				@SuppressLint("NewApi")
				@Override
				public boolean onLongClick(View v) {
					longClick(position);
					return true;
				}
			});
			viewHolder.circleImageView1_face.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MessageVo msgvo = (MessageVo)v.getTag();
					Intent intent = new Intent();
					intent.setClassName(mContext, Activity_Profile.class.getName());
					//String mSelectSeq =  msgvo.getSendseq();
					//intent.putExtra(ChatGlobal.ProfileIntentSeq, String.valueOf(mSelectSeq)); //아이디
					intent.putExtra(ChatGlobal.ProfileIntentActivity, true);
					intent.putExtra(ChatGlobal.ProfileIntentMessageVO, msgvo);
					if(faceviewclicklistner != null){
						faceviewclicklistner.setOnFaceViewClickListner(intent);
					}
					//mContext.startActivityForResult(intent, ChatGlobal.OnChatReQustCode);
				}
			});
			viewHolder.circleImageView1_face.setTag(mArrChatUI.get(position));
			viewHolder.textViewMessage.setOnLongClickListener(new View.OnLongClickListener() {
				@SuppressLint("NewApi")
				@Override
				public boolean onLongClick(View v) {
					longClick(position);
					return true;
				}
			});
			
			viewHolder.textViewMessage.setOnTouchListener(mOnTouchListener);
			//viewHolder.textViewNickName.setText(getMsgName(mArrChatUI.get(position).getSendseq()));
			viewHolder.textViewNickName.setText(mArrChatUI.get(position).getSendname());
			viewHolder.textViewMessage.setText("" + mArrChatUI.get(position).getMsg());
			/*String time = getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())) ;
			if(times.equals(time)){
				viewHolder.textView_time.setVisibility(View.INVISIBLE);
			}else{
				viewHolder.textView_time.setVisibility(View.VISIBLE);
				//viewHolder.textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate()) ));	
				viewHolder.textView_time.setText("" +time);
			}
			times = time;*/
			viewHolder.textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate()) ));
			if(mArrChatUI.get(position).getCnt() > 0){
				viewHolder.textview_read.setText(String.valueOf(mArrChatUI.get(position).getCnt()) );
				viewHolder.textview_read.setVisibility(View.VISIBLE);
			}else{
				//viewHolder.textview_read.setText(mArrChatUI.get(position).getCnt() );
				viewHolder.textview_read.setVisibility(View.GONE);
			}
			
			if(!TextUtils.isEmpty(getFace(mArrChatUI.get(position).getSendseq()))){
			/*	mCPImageLoader.DisplayImageWithMask(
						getFace(mArrChatUI.get(position).chat.userItemName), 
						viewHolder.circleImageView1_face.mImageViewFace, 
						sizeCircle, sizeCircle, 
						R.drawable.photo_bg_main, CPImageLoader.MASK_TYPE_NORMAL);	*/
				viewHolder.circleImageView1_face.mIcon.setVisibility(View.GONE);
			}else{
				//viewHolder.circleImageView1_face.mImageViewFace.setImageBitmap(null);
				viewHolder.circleImageView1_face.mIcon.setVisibility(View.VISIBLE);
			}
			
			//viewHolder.circleImageView1_face.getFaceView().setImageUrl(ChatGlobal.getMemberFaceThumURL(mArrChatUI.get(position).getSendseq()), ChattingApplication.getInstance().getImageLoader());
			ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(mArrChatUI.get(position).getSendseq()), new ImageListener() {
				
				@Override
				public void onErrorResponse(VolleyError error) {}
				@Override
				public void onResponse(ImageContainer response, boolean isImmediate) {
					viewHolder.circleImageView1_face.setImageBitmap(response.getBitmap());
				}
			});
			
		}else if(TYPE_FRIENDS_Pic==type){
			RelativeLayout friend_pic = null;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_friends_pic, parent, false);
				friend_pic = ViewHolderFriends.get(convertView, R.id.friend_pic);
        	}else{
        		friend_pic = ViewHolderFriends.get(convertView, R.id.friend_pic);
        		//upview.setFileUpload(mArrChatUI.get(position));
        	}
			
		//	final ViewHolderFriends viewHolder;
			//convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_friends_pic, parent, false);
			//viewHolder = new ViewHolderFriends();
			LinearLayout linearLayout_messageBox = (LinearLayout)friend_pic.findViewById(R.id.activityChatListRowFriendsPic_LinearLayout_messageBox);
			TextView textView_time = (TextView)friend_pic.findViewById(R.id.activityChatListRowFriendsPic_TextView_time);
			ImageView imageViewPic = (ImageView)friend_pic.findViewById(R.id.activityChatListRowFriendsPic_ImageView_pic);
			TextView textViewNickName = (TextView)friend_pic.findViewById(R.id.activityChatListRowFriendsPic_TextView_nickName);
			final CircleImageView1 circleImageView1_face = (CircleImageView1)friend_pic.findViewById(R.id.activityChatListRowFriendsPic_CircleImageView1_face);
			TextView textview_read = (TextView)friend_pic.findViewById(R.id.textview_read);
			circleImageView1_face.setChatRoom(true);
			//convertView.setTag(viewHolder);
			
			linearLayout_messageBox.setOnLongClickListener(new View.OnLongClickListener() {
				@SuppressLint("NewApi")
				@Override
				public boolean onLongClick(View v) {
					longClick(position);
					return true;
				}
			});
			circleImageView1_face.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MessageVo msgvo = (MessageVo)v.getTag();
					Intent intent = new Intent();
					intent.setClassName(mContext, Activity_Profile.class.getName());
					//String mSelectSeq =  msgvo.getSendseq();
					//intent.putExtra(ChatGlobal.ProfileIntentSeq, String.valueOf(mSelectSeq)); //아이디
					intent.putExtra(ChatGlobal.ProfileIntentActivity, true);
					intent.putExtra(ChatGlobal.ProfileIntentMessageVO, msgvo);
					if(faceviewclicklistner != null){
						faceviewclicklistner.setOnFaceViewClickListner(intent);
					}
				}
			});
			circleImageView1_face.setTag(mArrChatUI.get(position));
			imageViewPic.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActivityImageViewer.class);
					intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
					String msgkey = (String)v.getTag();
					int fileposition = 0;
					ArrayList<MessageVo>messageArray = new ArrayList<MessageVo>();
					for(int i  = 0 ; i< mArrChatUI.size();i++){
						if(mArrChatUI.get(i).getMsgtype() == ChatType.file){
							messageArray.add( mArrChatUI.get(i));
							if(mArrChatUI.get(i).getMsgkey().equals(msgkey)){
								fileposition = messageArray.size()-1;
							}
						}
					}
					ActivityImageViewer.setMessage(messageArray);
					intent.putExtra(ActivityImageViewer.POSITION, fileposition);
					
					mContext.startActivity(intent);
				/*	if(mImageViewerLoadingListener!=null && !TextUtils.isEmpty(mArrChatUI.get(position).chat.message))
						mImageViewerLoadingListener.getImageView(mContext.getString(R.string.push_picture)
								, ChatImageURL.getURL(mArrChatUI.get(position).chat.message, mArrChatUI.get(position).chat.chatType, mArrChatUI.get(position).chat.chatTransferStatus));*/
				}
			});
			imageViewPic.setTag(mArrChatUI.get(position).getMsgkey());
			
			imageLoader.displayImage(mArrChatUI.get(position).getMsg(),imageViewPic,imageLoaderOption, animationListener);
			
		/*	ChattingApplication.getInstance().getImageLoader().get(mArrChatUI.get(position).getMsg(), new ImageListener() {
				
				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onResponse(ImageContainer response, boolean isImmediate) {
					viewHolder.imageViewPic.setImageBitmap(response.getBitmap());
				}
			});*/

			//viewHolder.textViewNickName.setText(getMsgName( mArrChatUI.get(position).getSendseq()));
			textViewNickName.setText(mArrChatUI.get(position).getSendname());
			/*String time = getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())) ;
			if(times.equals(time)){
				textView_time.setVisibility(View.INVISIBLE);
				//textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())));	
			}else{
				textView_time.setVisibility(View.VISIBLE);
				textView_time.setText("" +time);
			}
			times = time;*/
			textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())));
			//textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())));
			if(mArrChatUI.get(position).getCnt() > 0){
				textview_read.setText(String.valueOf(mArrChatUI.get(position).getCnt()) );
				textview_read.setVisibility(View.VISIBLE);
			}else{
				//viewHolder.textview_read.setText(mArrChatUI.get(position).getCnt() );
				textview_read.setVisibility(View.GONE);
			}
			
		/*	imageLoader.displayImage(ChatImageURL.getURL(mArrChatUI.get(position).chat.message, mArrChatUI.get(position).chat.chatType, mArrChatUI.get(position).chat.chatTransferStatus),
					viewHolder.imageViewPic,
					roundImageLoaderOption);*/
			
			if(!TextUtils.isEmpty(getFace(mArrChatUI.get(position).getSendseq()))){
				/*mCPImageLoader.DisplayImageWithMask(
						getFace(mArrChatUI.get(position).chat.userItemName), 
						viewHolder.circleImageView1_face.mImageViewFace, 
						sizeCircle, sizeCircle, 
						R.drawable.photo_bg_main, CPImageLoader.MASK_TYPE_NORMAL);	*/
				circleImageView1_face.mIcon.setVisibility(View.GONE);
			}else{
				circleImageView1_face.mImageViewFace.setImageBitmap(null);
				circleImageView1_face.mIcon.setVisibility(View.VISIBLE);
			}
			
		//	viewHolder.circleImageView1_face.getFaceView().setImageUrl(ChatGlobal.getMemberFaceThumURL(mArrChatUI.get(position).getSendseq()), ChattingApplication.getInstance().getImageLoader());
			
			ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(mArrChatUI.get(position).getSendseq()), new ImageListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
				}
				
				@Override
				public void onResponse(ImageContainer response, boolean isImmediate) {
					circleImageView1_face.setImageBitmap(response.getBitmap());
				}
			});
			
        }else if(TYPE_MINE_Text==type){
        	final ViewHolderMine viewHolder;
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_mine_text, parent, false);
			viewHolder = new ViewHolderMine();
			
			viewHolder.textView_time = (TextView)convertView.findViewById(R.id.activityChatListRowMineText_TextView_time);
			viewHolder.textViewMessage = (EmojiconTextView)convertView.findViewById(R.id.activityChatListRowMineText_TextView_message);
			viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.activityChatListRowMineText_ProgressBar);
			viewHolder.failView = (ImageView)convertView.findViewById(R.id.activityChatListRowMineText_ImageView_fail);
			viewHolder.textview_read = (TextView)convertView.findViewById(R.id.textview_read);
			viewHolder.textViewMessage.setFocusable(true);
			convertView.setTag(viewHolder);
			
			viewHolder.textViewMessage.setOnLongClickListener(new View.OnLongClickListener() {
				@SuppressLint("NewApi")
				@Override
				public boolean onLongClick(View v) {
					longClick(position);
					return true;
				}
			});
			viewHolder.textViewMessage.setOnTouchListener(mOnTouchListener);
			
			viewHolder.failView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
//					if(mActivityChat!=null) mActivityChat.getRetryAlert(position);
				}});
			
			if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendIng){
				viewHolder.progressBar.setVisibility(View.VISIBLE);
				
				viewHolder.failView.setVisibility(View.GONE);
			}else if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendFail){
				viewHolder.progressBar.setVisibility(View.GONE);
				viewHolder.failView.setVisibility(View.VISIBLE);
				viewHolder.failView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String[] arrays = {"재전송","삭제"};
						BrUtilManager.getInstance().ShowArrayDialog(mContext, null, arrays, -1, new setOnItemChoice() {
							@Override
							public void setOnItemClick(int arg0) {
								MessageVo msgvo = (MessageVo)viewHolder.failView.getTag();
								//String msgkey = (String)viewHolder.failView.getTag();
								switch(arg0){
								case 0:
									if(failsendlistner != null){
										ChattingApplication.getInstance().chatdb.DeleteSendMessage(mContext, msgvo.getMsgkey());
										failsendlistner.setOnFailMsgSendListner(msgvo.getMsgkey());
									}
									break;
								case 1:
									ChattingApplication.getInstance().chatdb.DeleteSendMessage(mContext, msgvo.getMsgkey());
									ChattingService.getInstance().getRoomMessage(msgvo.getRoomseq(),Activity_Chat.getInstance().mPage,false);		
									Activity_Chat.getInstance().UpdateView(false);
									break;
								}
							}
						});
					}
				});
				viewHolder.failView.setTag(mArrChatUI.get(position));
			}else{
				viewHolder.progressBar.setVisibility(View.GONE);
				viewHolder.failView.setVisibility(View.GONE);
			}
			
			viewHolder.textViewMessage.setText("" + mArrChatUI.get(position).getMsg());
		/*	String time = getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())) ;
			if(times.equals(time)){
				viewHolder.textView_time.setVisibility(View.INVISIBLE);
				//viewHolder.textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())));
			}else{
				viewHolder.textView_time.setVisibility(View.VISIBLE);
				viewHolder.textView_time.setText("" +time);	
			}
			times = time;*/
			viewHolder.textView_time.setText("" +getTime(String.valueOf(mArrChatUI.get(position).getMsgregdate())));
			if(mArrChatUI.get(position).getStatus()!=ChatStatusType.SendFail){
				if(mArrChatUI.get(position).getCnt() > 0){
					viewHolder.textview_read.setText(String.valueOf(mArrChatUI.get(position).getCnt()));
					viewHolder.textview_read.setVisibility(View.VISIBLE);
				}else{
					//viewHolder.textview_read.setText(mArrChatUI.get(position).getCnt() );
					viewHolder.textview_read.setVisibility(View.GONE);
				}
			}else{
				viewHolder.textview_read.setVisibility(View.GONE);
			}
		
        }else if(TYPE_MINE_Pic==type){
        	//final ViewHolderMinePic viewHolder;
        	FileUploadView upview = null;
        	if(convertView == null){
        		convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_chat_list_row_mine_pic2, parent, false);
        		upview = ViewHolderMinePic.get(convertView, R.id.fileup);
        		upview.setFileUpload(mArrChatUI.get(position), mArrChatUI);
        	}else{
        		upview = ViewHolderMinePic.get(convertView, R.id.fileup);
        		upview.setFileUpload(mArrChatUI.get(position),mArrChatUI);
        	}
        	
			//viewHolder = new ViewHolderMinePic();
        	
			//viewHolder.fileuploadview = (FileUploadView)convertView.findViewById(R.id.fileup);
			/*if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendIng){
				  BitmapFactory.Options options = new BitmapFactory.Options();
				  options.inSampleSize = 8;
				  Bitmap bm = BitmapFactory.decodeFile(mArrChatUI.get(position).getMsg(),options);
				  viewHolder.fileuploadview.imageViewPic.setImageBitmap(bm);
			}*/
			//imageLoader.displayImage(mArrChatUI.get(position).getMsg(),viewHolder.fileuploadview.imageViewPic,imageLoaderOption, animationListener);
		//	if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendIng){
        		
			//}
			
			/*viewHolder.textView_time = (TextView)convertView.findViewById(R.id.activityChatListRowMinePic_TextView_time);
			viewHolder.imageViewPic = (ImageView)convertView.findViewById(R.id.activityChatListRowMinePic_ImageView_pic);
			viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.activityChatListRowMinePic_ProgressBar);
			viewHolder.failView = (ImageView)convertView.findViewById(R.id.activityChatListRowMinePic_ImageView_fail);
			viewHolder.textview_read = (TextView)convertView.findViewById(R.id.textview_read);
			convertView.setTag(viewHolder);
			
			viewHolder.failView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					if(mActivityChat!=null) mActivityChat.getRetryAlert(position);
				}
			});
			viewHolder.imageViewPic.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActivityImageViewer.class);
					intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
					String path = (String)v.getTag();
					intent.putExtra(ActivityImageViewer.PATH, path);
					mContext.startActivity(intent);
					if(mImageViewerLoadingListener!=null && !TextUtils.isEmpty(mArrChatUI.get(position).chat.message))
						mImageViewerLoadingListener.getImageView(mContext.getString(R.string.push_picture)
								, ChatImageURL.getURL(mArrChatUI.get(position).chat.message, mArrChatUI.get(position).chat.chatType, mArrChatUI.get(position).chat.chatTransferStatus));
				}
			});
			viewHolder.imageViewPic.setTag(mArrChatUI.get(position).getMsg());
			if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendIng){
				viewHolder.progressBar.setVisibility(View.VISIBLE);
				viewHolder.failView.setVisibility(View.GONE);
			}else if(mArrChatUI.get(position).getStatus()==ChatStatusType.SendFail){
				viewHolder.progressBar.setVisibility(View.GONE);
				viewHolder.failView.setVisibility(View.VISIBLE);
				viewHolder.failView.setOnClickListener(new OnClickListener() {
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
			}else{
				viewHolder.progressBar.setVisibility(View.GONE);
				viewHolder.failView.setVisibility(View.GONE);
			}
			if(mArrChatUI.get(position).getStatus()!=ChatStatusType.SendFail){
				if(mArrChatUI.get(position).getCnt() > 0){
					viewHolder.textview_read.setText(String.valueOf(mArrChatUI.get(position).getCnt()));
					viewHolder.textview_read.setVisibility(View.VISIBLE);
				}else{
					//viewHolder.textview_read.setText(mArrChatUI.get(position).getCnt() );
					viewHolder.textview_read.setVisibility(View.GONE);
				}
			}else{
				viewHolder.textview_read.setVisibility(View.GONE);
			}
			
			imageLoader.displayImage(mArrChatUI.get(position).getMsg(),viewHolder.imageViewPic,imageLoaderOption, animationListener);*/
			
			/*ChattingApplication.getInstance().getImageLoader().get(mArrChatUI.get(position).getMsg(), new ImageListener() {
				
				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onResponse(ImageContainer response, boolean isImmediate) {
					viewHolder.imageViewPic.setImageBitmap(response.getBitmap());
					viewHolder.imageViewPic.setTag(mArrChatUI.get(position).getMsg());
				}
			});*/
			
	/*		imageLoader.displayImage(ChatImageURL.getURL(mArrChatUI.get(position).chat.message, mArrChatUI.get(position).chat.chatType, mArrChatUI.get(position).chat.chatTransferStatus),
					viewHolder.imageViewPic,
					roundImageLoaderOption);*/
			
        }
		//viewArray.put(convertView.getId(), new WeakReference<View>(convertView));
		//viewArray.put(mArrChatUI.get(position).getMsgkey(), new WeakReference<View>(convertView));
		
		//return viewArray.get(convertView.getId()).get();
	//	return viewArray.get(mArrChatUI.get(position).getMsgkey()).get();
		return convertView;
	}
	
	
	
	
	private String getTime(String datetime){
		try {
			java.text.SimpleDateFormat format= null;
			if(datetime.length() != 21) {
				format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			}
			else {
				format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
			}
			java.util.Date date = null;
			date = format.parse(datetime);
			
			SimpleDateFormat aformat = new SimpleDateFormat("aa HH:mm");
			String time = aformat.format(date);
			String[] entime = time.split(" ");
			String[] entime2 = entime[1].split(":");
			if(Integer.valueOf(entime2[0]) > 12){
				return entime[0] +" " + String.valueOf(Integer.valueOf(entime2[0]) -12) + ":" + entime2[1];
			}else{
				return aformat.format(date);
			}
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
		return "";

	}
	
	private String getMsgName(String msg){
	//	Log.e("", "널인가요?" + ChattingApplication.getInstance());
	//	Log.e("", "널인가요?" + ChattingApplication.getInstance().memberMap);
	//	Log.e("", "널인가요?" + msg);
			return ((MemberVo)ChattingApplication.getInstance().memberMap.get(msg)).getUsername();	
	}
	
	private String getAddMessage(String member){
		String addmsg = "";
		String[] members = member.split("_");
		for(int i = 0 ; i < members.length; i++){
			if(addmsg.length() == 0){
				addmsg = members[i];	
			}else {
				addmsg = addmsg + "," +members[i];
			}
		}
		return addmsg;	
	}
	private String getName(String msg){
		try {
			String[] addmember = ChatVo.getRoomMember(msg);
			String addmsg = "";
			for(int i = 0 ; i < addmember.length; i++){
				if(addmsg.length() == 0){
					addmsg = ((MemberVo)ChattingApplication.getInstance().memberMap.get(addmember[i])).getUsername();	
				}else {
					addmsg = addmsg + "," + ((MemberVo)ChattingApplication.getInstance().memberMap.get(addmember[i])).getUsername();
				}
			}
			return addmsg;	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
		//return itemName;
		/*if(VHApplication.getInstance().getHashMapVHUser()==null)
			return name;
		else{
			String result = "";
			try{
				Mylog.e(TAG,"((VHUser)VHApplication.getInstance().getHashMapVHUser().get(itemName)):" + ((VHUser)VHApplication.getInstance().getHashMapVHUser().get(itemName)).name);
				result = ((VHUser)VHApplication.getInstance().getHashMapVHUser().get(itemName)).name;
			}catch(Exception e){
				result = "";
			}
			return result;
		}*/
	}
	
	private String getFace(String itemName){
		return itemName;
		/*if(VHApplication.getInstance().getHashMapVHUser()==null)
			return "";
		else{
			String result = "";
			try{
				result = ((VHUser)VHApplication.getInstance().getHashMapVHUser().get(itemName)).face;
			}catch(Exception e){
				result = "";
			}
			return result;
		}*/
	}
	
	private void longClick(int position){
		/*	if(mActivityChat!=null) mActivityChat.performLongClick(position);
		clicked = true;*/
	}
	
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
        	if (event.getAction() == MotionEvent.ACTION_UP) {
        		Mylog.e(TAG,"ACTION_UP");
            	if(clicked){
            		clicked = false;
            		return true;
            	}
            	
            }else if(event.getAction() == MotionEvent.ACTION_DOWN){
            	Mylog.e(TAG,"ACTION_DOWN");
            	
            }else if(event.getAction() == MotionEvent.ACTION_CANCEL){
            	Mylog.e(TAG,"ACTION_CANCEL");
            }
        	
            return false;
        }
    };

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrChatUI.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrChatUI.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private static class ViewHolderInvitation{
		TextView textViewMessage;
		@SuppressWarnings("unused")
		public static <T extends View >T get(View convertview, int id){
			SparseArray<View>	viewArray = (SparseArray<View>)convertview.getTag();
			if(viewArray == null){
				viewArray = new SparseArray<View>();
				convertview.setTag(viewArray);
			}
			View child = viewArray.get(id);
			if(child == null){
				child = convertview.findViewById(id);
				viewArray.put(id, child);
			}
			
			return (T) child;
		}
	}
	
	private static class ViewHolderFriends {
		LinearLayout linearLayout_messageBox;
		TextView textViewNickName;
		ImageView imageViewPic;
		EmojiconTextView textViewMessage;
		TextView textView_time;
		CircleImageView1 circleImageView1_face;
		TextView textview_read;
		@SuppressWarnings("unused")
		public static <T extends View >T get(View convertview, int id){
			SparseArray<View>	viewArray = (SparseArray<View>)convertview.getTag();
			if(viewArray == null){
				viewArray = new SparseArray<View>();
			}
			View child = viewArray.get(id);
			if(child == null){
				child = convertview.findViewById(id);
				viewArray.put(id, child);
				convertview.setTag(viewArray);
			}
			
			return (T) child;
		}
	}
	
	private static class ViewHolderMinePic {
		FileUploadView fileuploadview;
		@SuppressWarnings("unused")
		public static <T extends View >T get(View convertview, int id){
			SparseArray<View>	viewArray = (SparseArray<View>)convertview.getTag();
			if(viewArray == null){
				viewArray = new SparseArray<View>();
			}
			View child = viewArray.get(id);
			if(child == null){
				child = convertview.findViewById(id);
				viewArray.put(id, child);
				convertview.setTag(viewArray);
			}
			
			return (T) child;
		}
/*		TextView textView_time;
		ImageView imageViewPic;
		TextView textViewMessage;
		ProgressBar progressBar;
		ImageView failView;
		TextView textview_read;*/
	}
	
	private static class ViewHolderMine {
		TextView textView_time;
		ImageView imageViewPic;
		EmojiconTextView textViewMessage;
		ProgressBar progressBar;
		ImageView failView;
		TextView textview_read;
	}
}