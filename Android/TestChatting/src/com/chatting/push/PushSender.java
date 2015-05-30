/**
 * publish : bestist@nate.com
 * create date : 2013.06.12
 * update version : 1
 * update :
 * 
 * pushKey, pushOs 가 담긴 push 오브젝트를 받아서 android, ios에 멀티 쓰레드로 푸시를 전송한다.
 * 
 */
package com.chatting.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;

public class PushSender {

	protected String gcmApiKey;
	protected String appleCertificateFilePath;
	protected String appleCertificatePassword;
	protected JSONArray pushList;
	protected String message;
	protected HashMap extraMessage;
	protected PushKey pushmember;
	protected int threadPoolCount = 1; 
	// 생성자
	public PushSender(String gcmApiKey, String appleCertificateFilePath, String appleCertificatePassword) {
		this.gcmApiKey = gcmApiKey;
		this.appleCertificateFilePath = appleCertificateFilePath;
		this.appleCertificatePassword = appleCertificatePassword;
	}
	public PushSender(String gcmApiKey, String appleCertificateFilePath, String appleCertificatePassword, JSONArray pushList, String message, HashMap extraMessage) {
		this.gcmApiKey = gcmApiKey;
		this.appleCertificateFilePath = appleCertificateFilePath;
		this.appleCertificatePassword = appleCertificatePassword;
		this.pushList = pushList;
		this.message = message;
		this.extraMessage = extraMessage;
	}
	public PushSender(String gcmApiKey, String appleCertificateFilePath,String appleCertificatePassword,PushKey pushmember,String message){
		this.gcmApiKey = gcmApiKey;
		this.appleCertificateFilePath = appleCertificateFilePath;
		this.appleCertificatePassword = appleCertificatePassword;
		this.pushmember = pushmember;
		this.message = message;
	}

	// setter
	public void setPush(JSONArray pushList) {
		this.pushList = pushList;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setExtraMessage(HashMap extraMessage) {
		this.extraMessage = extraMessage;
	}
	
	public void pushSendMessage(){
		try {
			boolean production = false;	// 테스트=false | 실서비스=true
			int pushListVolume = 500;	// Thread 하나당 처리할 push 갯수
			int threadPoolCount;	// 생성할 Thread Pool 갯수
			
			if (pushmember.getOs().startsWith("ANDROID")) {
				SendAndroid(pushmember.getPushKey(), message);
			} else if (pushmember.getOs().startsWith("IOS")) {
				//applePushList.add(pushKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SendAndroid(String pushkey, String message){
		try {
			GcmSender gcmSender = new GcmSender(gcmApiKey, pushkey, message);
		//	threadPoolCount = (androidPushList.size() / 100) + 1;
			try {
				ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
				executorService.execute(gcmSender);
				executorService.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// 푸시 전송
	public void pushSendMulti() {
		
		boolean production = false;	// 테스트=false | 실서비스=true
		int pushListVolume = 500;	// Thread 하나당 처리할 push 갯수
		int threadPoolCount;	// 생성할 Thread Pool 갯수
		
		List androidPushList = new ArrayList();
		List applePushList = new ArrayList();
		
		try {
			for (int i=0; i<pushList.length(); i++) {
				
				String pushOs = pushList.getJSONObject(i).getString("os");
				String pushKey = pushList.getJSONObject(i).getString("pushKey");
				
				if (pushOs.equals("ANDROID")) {
					androidPushList.add(pushKey);
				} else if (pushOs.equals("IOS")) {
					applePushList.add(pushKey);
				}
			}
			
			// GCM 전송
			if (androidPushList.size() > 0) {
				GcmSender gcmSender = new GcmSender(gcmApiKey, androidPushList, message, extraMessage);
				threadPoolCount = (androidPushList.size() / 100) + 1;
				try {
					ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
					executorService.execute(gcmSender);
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// APNS 전송
			if (applePushList.size() > 0) {
				ApnsSender apnsSender = new ApnsSender();
				threadPoolCount = (applePushList.size() / 100) + 1;
				try {
					apnsSender.sendLargeAmount(appleCertificateFilePath, appleCertificatePassword, production, applePushList, message, extraMessage, threadPoolCount);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
