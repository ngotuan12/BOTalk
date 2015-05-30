package kr.brainyx.util.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PushController {

	@RequestMapping("/sendPush.go")
	public String sendPushController(
			@RequestParam("message") String message,
			@RequestParam("extra") String extra,
			@RequestParam("pushList") String pushList,
			Model model
		) {

		HashMap extraMessage = new HashMap();
		extraMessage.put("seq", extra);
		
		sendPush(message, extraMessage, pushList);
		
		return "";
	}
	
	public void sendPush(String message, HashMap extraMessage, String pushList) {

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
		
	}
	

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
