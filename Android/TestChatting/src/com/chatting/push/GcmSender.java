package com.chatting.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GcmSender implements Runnable {

	private String apiKey;
	private List token;
	private String message;
	private Map<String, String> extra;
	
	private String memberpushkey ;
	public GcmSender(String apiKey, List token, String message, Map<String, String> extra) {
		this.apiKey = apiKey;
		this.token = token;
		this.message = message;
		this.extra = extra;
	}
	
	public GcmSender(String Apikey, String memberpushkey, String message){
		this.apiKey = Apikey;
		this.memberpushkey = memberpushkey;
		this.message = message;
	}
	
	@Override
	public void run() {
		//send(apiKey, token, message, extra);
		send(apiKey, memberpushkey, message, extra);
	}
	
	public void send(String apiKey, String token, String message, Map<String, String> extra) {
	    Sender sender = new Sender(apiKey);
	    
	    try { message = URLEncoder.encode(message, "UTF-8"); } 
	    catch (UnsupportedEncodingException ignore) {}
	    /*
		*/   

		Message.Builder messageBuilder = new Message.Builder();
	    
		String key = "";
		String value = "";
		if( extra != null ) {
	        Iterator<String> keys = extra.keySet().iterator();
	        while( keys.hasNext() ) {
	        	try {
	        		key = keys.next();
		            value = extra.get(key);
		            
		            messageBuilder.addData(key, value);
				} catch (Exception e) {
					// TODO: handle exception
				}
	        }
	    }

	    messageBuilder.addData("tickerText", "comment");
        messageBuilder.addData("contentTitle", Constant.CONTENT_TITLE);
        messageBuilder.addData("message", message);
        messageBuilder.addData("key", value);
        messageBuilder.build();
		
		MulticastResult result;
		try {
			Result results = sender.send(messageBuilder.build(), token, 5);
			System.out.println("GCM suc");
			//int successCount = result.getSuccess();

		/*	if(successCount > 0) {
				S.print("댓글 등록 푸시 전송 성공 : ",message, result.getResults());
			} else {
				S.print("댓글 등록 푸시 전송 실패 : ",message, result.getFailure());
			}	*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("GCM fail");
		}
	         
	}

}
