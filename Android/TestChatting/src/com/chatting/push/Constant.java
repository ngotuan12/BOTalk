package com.chatting.push;


public class Constant {
	
	/**
	 * 
	 */
	public static String CONTENT_TITLE = "채팅";
	
	/**
	 * 구글 API KEY	
	 */
	public static String GOOGLE_API_KEY = "AIzaSyCtNyzp8QHMWR72yqP0rrlRQAv11Ecx4LU";
	
	/**
	 * 애플 인증파일 경로
	 */
	//public static String APPLE_CERTIFICATE_FILE_PATH = "D:/00_WorkSpring/SmartIdea/WebContent/auth/smartthink.p12";
	public static String APPLE_CERTIFICATE_FILE_PATH = "C:/WEB_HOME/www.theso.co.kr/auth/smartthink.p12";

	/**
	 * 애플 인증파일 암호
	 */
	public static String APPLE_CERTIFICATE_PASSWORD = "[tmakxmgkstodrkr]";

	/**
	 * API url
	 */
	//public static String BaseUrl = "http://brainyx711.iptime.org:9998/";
	//public static String BaseUrl = "http://54.65.163.9/";
	public static String BaseUrl = "http://54.178.142.30/";
	//public static String BaseUrl = "http://192.168.1.20:8080/";
	//public static String BaseUrl = "http://localhost/";
	//
	
	/**
	 * 
	 */
	public static String getMemberList = BaseUrl + "m_get_member_list.go?userId=" ;
	
	/**
	 * 푸쉬키 , OS 정보
	 */
	public static String getMemberPush = BaseUrl + "m_get_pushkey.go?userSeq=" ;

}
