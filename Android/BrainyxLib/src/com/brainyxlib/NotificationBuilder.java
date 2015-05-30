package com.brainyxlib;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

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

	public void ShowNotificationTextBuilder(int icon, String title, String content,Class<?> activityClass,boolean flag) {
		Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), icon);
		Intent intent = new Intent(mContext, activityClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext).setSmallIcon(icon).setLargeIcon(largeIcon)
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
				builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
				builder.setDefaults(Notification.DEFAULT_VIBRATE);
		}

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
