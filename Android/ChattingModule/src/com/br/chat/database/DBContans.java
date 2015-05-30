package com.br.chat.database;

public class DBContans {

	public static final String DBNAME = "chatting.db";
	public static final int DBVERSION = 1;

	public static final String MyInfoTableName = "myinfo";
	public static final String MemberTableName = "member";
	public static final String RoomTableName = "room";
	public static final String RoomMemberTableName = "roommemer";
	public static final String MSGTableName = "msg";
	public static final String MSGMemTableName = "msgmem";
	public static final String FailMSGTableName = "failmsg";
	public static final String MemberFavorTableName = "favor";
	
	public static final String My_seq = "myseq";//내유저키
	public static final String My_id = "myid";//내아이디
	public static final String My_phone = "myphone"; //내번호
	public static final String My_name = "myname";//내이름
	public static final String My_nick = "mynick";//내닉네임
	public static final String My_stmsg = "mystmsg";//내상태메세지
	
	
	/*// 내정보필드
		public static final String User_seq = "userseq"; // 유저seq(키)
		public static final String User_id = "userid"; // 
		public static final String User_phone = "userphone"; //유저폰번호(키)
		public static final String User_name = "username"; //유저이름
		public static final String User_nick = "usernick"; //유저닉네임
		public static final String User_stmsg = "userstmsg"; //유저상태메세지
		public static final String User_photo = "userphoto"; // 유저포토url
		public static final String User_Section = "usersection"; // 유저섹션
		public static final String User_Header = "userheader"; // 유저섹션헤더
*/		
	// 멤버테이틀 필드
	public static final String User_seq = "userseq"; // 유저seq(키)
	public static final String User_id = "userid"; // 
	public static final String User_phone = "userphone"; //유저폰번호(키)
	public static final String User_name = "username"; //유저이름
	public static final String User_nick = "usernick"; //유저닉네임
	public static final String User_stmsg = "userstmsg"; //유저상태메세지
	public static final String User_photo = "userphoto"; // 유저포토url
	public static final String User_Section = "usersection"; // 유저섹션
	public static final String User_Header = "userheader"; // 유저섹션헤더
	//public static final String User_fav = "userfav"; //유저즐겨찾기 상태 1: 즐겨찾기 0: 일반
	
	//즐겨찾기 테이블
	public static final String Favor_seq = "userseq"; // 유저seq(키)
	
	//대화방 테이블 필드
	public static final String Room_Seq = "roomseq"; //대화방 seq
	public static final String Room_Member = "roommember"; //대화방 참여멤버
	public static final String Room_Owner = "roomowner"; //대화방 만든멤버
	//public static final String Room_Receiver = "roomreceiver"; //대화방 상대멤버
	public static final String Room_Regdate = "roomregdate"; //대화방 만든날짜
	public static final String Room_UpdateDate = "roomupdate"; //대화방 업데이트날짜
	public static final String Room_Type = "roomtype"; //대화방 타입 0이면 1대1, 1이면 단체
	//public static final String Room_MsgCnt = "roommsgcnt"; // 새로운메세지
	public static final String Room_NewMsg = "roomnewmsg"; //최근메세지
	public static final String Room_Title = "roomtitle"; //방제목 1:1이면 상대방이름, 그룹이면 그룹채팅
	//추가
	public static final String Room_MemberName = "roommemname"; //대화방 참여 멤버 이름 
	//대화방멤버 테이블 필드
	//방 seq 포함
	
	
	// 메세지 테이블 필드
	
	//방 seq 포함
	public static final String Msg_Key = "msgkey"; //메세지 키값
	public static final String Msg_Regdate = "msgregdate"; //메세지 보낸time
	public static final String Msg_Type = "msgtype"; //메세지 타입 1: 방개설  2: 일반메세지(이모티콘) %&em 3: 파일  4: 방나감 5: 읽음메세지 6: 멤버추가메세지 
	public static final String Msg_Content = "msgcontent"; //메세지 내용 (파일일경우 파일경로)
	//public static final String Msg_Member = "msgmember"; //메세지 받는모든멤버
	//public static final String Msg_Receiver = "msgreceiver"; //메세지 상대멤버
	public static final String Msg_Sender = "msgsender"; //메세지 보낸사람 seq
	public static final String Msg_Status = "msgstatus"; //메세지 상태 1:전송, 2: 실패  3: 전송중
	public static final String Msg_RoomType = "msgroomtype";//룸타입
	public static final String Msg_SenderName = "msgsendname"; //메세지 보낸사람 이름
	//메세지 멤버테이블
	//public static final String Msg_Member = "roomseq";
	// 방seq
	public static final String Msg_Revseq = "memrevseq";
	//메시지보낸시간
	//방타입
	
	////////////메세지 전송 실패 테이블'
	//msgkey
	public final static String MSG_KEY = "msgkey";
	public static final String SEND_SEQ = "sendseq";
	public final static String MEMBER_SEQ = "memberseq";
	public final static String MESSAGE = "msg";
	public final static String SEND_NAME = "sendname";
	public final static String SEND_TYPE = "sendtype";
	public final static String ROOM_SEQ = "roomseq";
	public final static String ROOM_TYPE = "roomtype";
	public final static String REV_NAME = "revname";
	public final static String MEM_NAME = "membername";
	public final static String REG_DATE = "msgregdate";
	
	
}
