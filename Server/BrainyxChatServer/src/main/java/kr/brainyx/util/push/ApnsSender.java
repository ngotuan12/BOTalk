package kr.brainyx.util.push;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;
import kr.brainyx.util.S;

public class ApnsSender {

	/**
	 * 애플 푸시 전송 (APNS)	
	 * @param certificate : 인증서 파일(*.p12) 경로
	 * @param password : 인증서 비밀번호
	 * @param production : 테스트=false | 실서비스=true 
	 * @param udid : 디바이스 고유키(푸시키)
	 * @param message : 전송되는 메세지
	 * @param extra : 전송되는 메세지 외에 커스텀 딕셔너리에 넣을 데이터
	 * @return
	 */
	public boolean sendSimple(String certificate, String password, boolean production, String udid, String message, Map<String, String> extra) {
		try {
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(-1);
			payload.addSound("default");
			
			if (extra != null) {
				Iterator<String> keys = extra.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					String value = extra.get(key);
					
					payload.addCustomDictionary(key, value);
				}
			}
			
			PushedNotifications notifications = Push.payload(payload, certificate, password, production, udid);
			S.print(notifications);
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 대용량 애플 푸시 전송 (APNS)
	 * @param certificate : 인증서 파일(*.p12) 경로
	 * @param password : 인증서 비밀번호
	 * @param production : 테스트=false | 실서비스=true 
	 * @param devices : 디바이스 고유키(푸시키)들의 List
	 * @param message : 전송되는 메세지
	 * @param extra : 전송되는 메세지 외에 커스텀 딕셔너리에 넣을 데이터
	 * @param threadCount : 전송에 사용될 thread의 갯수
	 * @return
	 */
	public boolean sendLargeAmount(String certificate, String password, boolean production, List devices, String message, Map<String, String> extra, int threadPoolCount) {
		try {
			
			PushNotificationPayload payload = PushNotificationPayload.complex();
			payload.addAlert(message);
			payload.addBadge(-1);
			payload.addSound("default");
			
			if (extra != null) {
				Iterator<String> keys = extra.keySet().iterator();
				int keyIdx = 1;
				while (keys.hasNext()) {
					String key = keys.next();
					String value = extra.get(key);
					//payload.addCustomDictionary(key, value);
					payload.addCustomDictionary("P"+keyIdx, value);
					keyIdx++;
				}
			}
			
			PushedNotifications notifications = Push.payload(payload, certificate, password, production, threadPoolCount, devices);
			//success : [[16777217] transmitted {"title":"hello","aps":{"sound":"default","alert":"test 멀티 푸시","badge":-1},"seq":"1"} on first attempt to token eb7a5..fcb0e]
			//fail    : [[16777217] not transmitted to token eb7a5..fcb0e  javapns.communication.exceptions.InvalidCertificateChainException: Invalid certificate chain (Received fatal alert: certificate_unknown)!  Verify that the keystore you provided was produced according to specs...]
			S.print(notifications);
			
			return (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
