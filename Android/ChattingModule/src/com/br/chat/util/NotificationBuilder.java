package com.br.chat.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.vo.ChatVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.PushWakeLock;
import com.brainyxlib.SharedManager;
import com.chattingmodule.R;

public class NotificationBuilder {

	Context mContext;
	public  final static int NOTIFICATION_ID = 1000;
	public NotificationBuilder(Context context) {
		mContext = context;
	}

	/*public void ShowNotificationNotDragBuilder(int NOTIFICATION_ID,int icon, String title,String content, Class<?> activityClass) {
		Bitmap largeIcon = BitmapFactory.decodeResource(
				mContext.getResources(), icon);

		RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.push);

		Intent intent = new Intent(mContext, activityClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext).setAutoCancel(true).setSmallIcon(icon)
				.setLargeIcon(largeIcon).setTicker(title)
				.setWhen(System.currentTimeMillis()).setContentTitle(title)
				.setContentText(content).setContentIntent(pendingIntent)
				.setContent(remoteViews);

		remoteViews.setTextViewText(R.id.push_title, title);
		remoteViews.setTextViewText(R.id.push_message, content);

		//if (ServiceInfo.getSoundFlag(mContext)) {
			builder.setSound(RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		//}
		//if (ServiceInfo.getVibFlag(mContext)) {
			builder.setDefaults(Notification.DEFAULT_VIBRATE);
		//}
		  
		NotificationManager notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, builder.build());
		
		PushWakeLock.acquireCpuWakeLock(mContext);
		PushWakeLock.registerRestartAlarm(mContext);
	}*/
	
	
	public void NotificationImageLoader(final int icon, final String title, final String content,final String activityClass,final boolean flag,final String roomseq, final String[] members,final int roomtype,String sendseq,final String sendname,final String[] membername){
		
		ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(sendseq), new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				Bitmap largeIcon = response.getBitmap();
				if(largeIcon != null){
					ShowNotificationTextBuilder(icon, title, content, activityClass, flag, roomseq, members, roomtype, largeIcon,sendname,membername);	
				}else{
					 largeIcon = BitmapFactory.decodeResource(mContext.getResources(), icon);
					ShowNotificationTextBuilder(icon, title, content, activityClass, flag, roomseq, members, roomtype, largeIcon,sendname,membername);	
				}
			}
		});
	}
	
	
	public void ShowNotificationTextBuilder(int icon, String title, String content,String activityClass,boolean flag,String roomseq, String[] members,int roomtype,Bitmap largeicon,String sendname,String[] membername) {
		//Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), icon);
		
	/*	ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(sendseq), new ImageListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						Bitmap largeIcon = response.getBitmap();
					}
				});*/
		
		Intent intent = new Intent(activityClass);
		if(activityClass.equals("action_BoardTalksMain")){
			ChattingApplication.getInstance().setChatGo(true);
		}
		Log.e("", "여기들어오나요??빌더 = " +  activityClass);
		ChatVo chatvo = new ChatVo();
		String my = SharedManager.getInstance().getString(mContext, BaseGlobal.User_name); //내아이디
		String myseq = SharedManager.getInstance().getString(mContext, BaseGlobal.User_Seq); //내seq
		chatvo.chatRoomMemberName = membername;
		chatvo.chatRoomOwnerName = my;
		chatvo.chatRoomRevName = sendname;
		chatvo.chatRoomOwner = myseq; //내seq
		chatvo.chatRoomSeq = roomseq;
		chatvo.chatRoomMember = members; // 멤버들
		chatvo.chatRoomType = roomtype; //싱글
		//chatvo.chatRoomName = mSelectUserName; // 상대이름
		intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
		
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
		if(content.startsWith("http")){
			content = "사진";
		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext).setSmallIcon(icon).setLargeIcon(largeicon)
				.setTicker(content).setContentTitle(title)
				.setContentText(content).setWhen(System.currentTimeMillis())
				.setAutoCancel(true);

		NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
		//style.setSummaryText("Etrade증권");
		style.setBigContentTitle(title);
		style.bigText(content);

		builder.setStyle(style);
		builder.setContentIntent(pendingIntent);

		if(flag){
			//	builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
				builder.setDefaults(Notification.DEFAULT_VIBRATE);
		}
		
		builder.setSound(Uri.parse("android.resource://"+ mContext.getPackageName() + "/" + R.raw.sound1));
	/*	SoundPool sound_pool = new SoundPool(5, AudioManager.STREAM_NOTIFICATION, 0);
		int sound_slide = sound_pool.load(mContext, R.raw.sound1, 0);
		sound_pool.play(sound_slide, 1.0f, 1.0f, 0, 0, 1.0f);	*/
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, builder.build());
		PushWakeLock.acquireCpuWakeLock(mContext);
		PushWakeLock.registerRestartAlarm(mContext);
	}

	public void ShowNotificationImageBuilder(int icon, String title,String content, Bitmap banner, Class<?> activityClass,boolean flag) {
		Intent intent = new Intent(mContext, activityClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext).setSmallIcon(icon).setTicker(content)
				.setContentTitle(title).setWhen(System.currentTimeMillis())
				.setContentText(content).setAutoCancel(true);

		NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
		style.setBigContentTitle(title);
		style.setSummaryText(content);
		style.bigPicture(banner);

		builder.setStyle(style);
		builder.setContentIntent(pendingIntent);

		if(flag){
				builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
				builder.setDefaults(Notification.DEFAULT_VIBRATE);
		}
	

		NotificationManager notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, builder.build());
		PushWakeLock.acquireCpuWakeLock(mContext);
		PushWakeLock.registerRestartAlarm(mContext);
	}
}
