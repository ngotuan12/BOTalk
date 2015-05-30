package com.chatting.push;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.chatting.constans.Constans;

public class PushController {

	public String sendPushController(
			 String message,
			 String extra,
			 String pushList
		) {

		HashMap extraMessage = new HashMap();
		extraMessage.put("seq", extra);
		
	//	sendPush(message, extraMessage, pushList);
		
		return "";
	}
	
	public void SendPushMessage(String member,String message){
		try {
			String gcmApiKey = Constant.GOOGLE_API_KEY;
			String appleCertificateFilePath = Constant.APPLE_CERTIFICATE_FILE_PATH;
			String appleCertificatePassword = Constant.APPLE_CERTIFICATE_PASSWORD;
			
			System.out.println("pushmember == " + member);
			
			String pushkey = getMemberPushKey(member);
			PushKey pushmember = setPushMember(pushkey);
			PushSender sender = new PushSender(gcmApiKey, appleCertificateFilePath, appleCertificatePassword, pushmember, message);
			sender.pushSendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static int CONNECT_TIMEOUT = 25000;
	
	public String getMemberPushKey(String memeber){
		try {
			 	URL url = new URL(Constant.getMemberPush + memeber);
		        InputStream is = url.openStream();
		    	StringBuilder builder = new StringBuilder();
		        BufferedReader inFile = new BufferedReader(new InputStreamReader(is));
		        String line = null;
		        while( (line = inFile.readLine()) != null ) {
		        	builder.append(line);
		        }
		        is.close();
		        
		        return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return "";
	}
	public PushKey setPushMember(String json){
		try {
			JSONObject jsonobj = (JSONObject)JSONValue.parse(json);
			Iterator iter = jsonobj.keySet().iterator();
			PushKey push = new PushKey();
			while(iter.hasNext()){
				String key = (String) iter.next();
    			Object value = jsonobj.get(key);
    			if(key.equals(Constans.PUSH_OS)){
    				push.setOs(String.valueOf(value));
    			}else if(key.equals(Constans.PUSH_KEY)){
    				push.setPushKey(String.valueOf(value));	
    			}
			}
			
			return push;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
/*	public void sendPush(String message, HashMap extraMessage, String pushList) {

		String gcmApiKey = Constant.GOOGLE_API_KEY;
		String appleCertificateFilePath = Constant.APPLE_CERTIFICATE_FILE_PATH;
		String appleCertificatePassword = Constant.APPLE_CERTIFICATE_PASSWORD;
		
		try {
			JSONObject jObject = new JSONObject(pushList);
			JSONArray jArray = jObject.getJSONArray("pushList");

			PushSender pushSender = new PushSender(gcmApiKey, appleCertificateFilePath, appleCertificatePassword, jArray, message, extraMessage);
			pushSender.pushSendMulti();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}*/
	

	public void sendPushServer(String message, HashMap extraMessage, String pushList) {

		try {
			int seq = Integer.parseInt((String)extraMessage.get("seq"));
			String type = (String) extraMessage.get("type");
			String urlstr = "http://218.234.17.195:8080/sendPush.do?message="+URLEncoder.encode(message, "UTF-8")+"&seq="+ seq +"&type="+ type +"&pushList="+ pushList;
			
			HttpURLConnection conn;
			BufferedReader rd;
			String line;
			String result = "";
			try {
				URL url = new URL(urlstr);
			    conn = (HttpURLConnection) url.openConnection();
			    conn.setRequestMethod("GET");
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = rd.readLine()) != null) {
			    	result += line;
			    }
			    rd.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
