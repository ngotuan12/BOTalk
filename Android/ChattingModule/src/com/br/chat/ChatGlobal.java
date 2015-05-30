package com.br.chat;


public class ChatGlobal {

	public static final int OnChatReQustCode = 10;
	
	public static final String ChatRoomObj = "ChatRoomObj";
	public static final String ChatRoomUser = "ChatRoomUser";
	
	///public static final String BaseChattingIp = "192.168.1.20";//내pc
	//public static final String BaseChattingIp = "54.65.163.9";//보톡스서버
	//public static final String BaseChattingIp = "54.178.142.30";//텐스페이스서버
	//public static final String BaseChattingIp = "52.68.148.183";
	//public static final String BaseChattingIp = "192.168.1.121";
	public static final String BaseChattingIp = "192.168.0.16";
	
	//public static final String BaseChattingIp = "54.64.173.138";
	//public static final String BaseChattingIp = "54.65.200.190"; //새서버
	
	public static final int BaseChattingPort = 7789;
	
	public static final int chatTypeSingle = 1;
	public static final int chatTypeGroup = 2;
	
	public static final long chatSendFailTime = 20000;
	
	public static final int CAMERA_CROP_REQUEST_CODE = 1001;
	public static final int CAMERA_ALBUM_REQUEST_CODE = 1002;
	public static final int TABLET_CAMERA_ALBUM_REQUEST_CODE = 1003;
	//아마존 파일서버 키값 , 버킷네
	/*public static String AMAZON_A3_SECRET_KEY = "63C3rl8HgqcSEWlsFUV2AZSxxL4hSSWOkE3lehaD";
	public static String AMAZON_A3_ACCESS_KEY = "AKIAJ2RDJBL2AQVO67FA";
	public static String AMAZON_A3_BUCKET_NAME = "brchatting";*/
	public static String AMAZON_A3_SECRET_KEY = "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";
	public static String AMAZON_A3_ACCESS_KEY = "AKIAJ7WTW2HZIPRMYVYA";
	public static String AMAZON_A3_BUCKET_NAME = "botalks";
	//
	
	public static int IMGTOTALCOUNT = 1;
	public static int ChatSelection = 40;
	//
	
	public static String ChatRoupName = "그룹채팅";
	//
	//public static String ProfileIntentName = "pname";
	//public static String ProfileIntentStmsg = "pstmsg";
	public static String ProfileIntentSeq = "pseq";
	public static String ProfileIntentActivity = "proot";
	public static String ProfileIntentMessageVO = "pmsgvo";
	public static String ProfileIntentChatType = "pchattype";
	
	//public static String BaseUrl = "http://192.168.1.20:8080/";
	//public static String BaseUrl = "http://54.65.163.9/";
	//public static String BaseUrl = "http://54.178.142.30/";
	//public static String BaseUrl = "http://52.68.148.183/";
	//public static String BaseUrl = "http://192.168.1.121/";
	public static String BaseUrl = "http://192.168.0.16:8080/basic/";
	
	
	
	public static String mJoinGo = BaseUrl + "m_join.go";
	public static String mUpdateProfile = BaseUrl + "m_updateprofile.go"; 
	public static String mContactList = BaseUrl + "m_contact.go";
	
	public static String getUsers= BaseUrl + "m_get_user_from_contact.go";
	
	public static String getMemberList = BaseUrl + "m_get_member_list.go" ;
	public static String getLogin = BaseUrl + "m_login.go";
	
	public static String setPhoto = BaseUrl + "m_set_profile_photo.go";
	public static String setStmsg = BaseUrl + "m_set_profile_stmsg.go";
	public static String getUserInfo = BaseUrl + "m_get_user_info.go";
	
	
	public static String updateContact = BaseUrl + "m_update_contact.go";
	public static String getFriendList = BaseUrl + "m_get_friend_list.go";
	
	
	
	public static String registerEmail = BaseUrl + "m_register_email.go";
	
	
	
	
	public static String getMemberFaceURL (String seq){
		return "http://s3-ap-northeast-1.amazonaws.com/"+ChatGlobal.AMAZON_A3_BUCKET_NAME+"/"+seq+"/"+seq + ".jpg";
	}
	
	public static String getMemberFaceThumURL (String seq){
		return "http://s3-ap-northeast-1.amazonaws.com/"+ChatGlobal.AMAZON_A3_BUCKET_NAME+"/"+seq+"/thm_"+seq + ".jpg";
	}
}
