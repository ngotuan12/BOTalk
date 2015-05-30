package com.br.chat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.br.chat.util.WriteFileLog;

public class ChattingDB extends SQLiteOpenHelper{

	public Context mContext;
	
	
	public String CreateTableMyInfo = "CREATE TABLE " + DBContans.MyInfoTableName + "("
			+ DBContans.My_seq + " INTEGER NOT NULL , "
			+ DBContans.My_id + " VARCHAR  , "
			+ DBContans.My_name + " VARCHAR  , "
			+ DBContans.My_nick + " VARCHAR  , "
			+ DBContans.My_phone + " VARCHAR NOT NULL , "
			+ DBContans.My_stmsg + " VARCHAR ); ";
	
	public String CreateTableFavor = "CREATE TABLE " + DBContans.MemberFavorTableName + "("
			+ DBContans.Favor_seq + " INTEGER NOT NULL); ";
	
	public String CreateTableMember = "CREATE TABLE " + DBContans.MemberTableName + "("
			+ DBContans.User_seq + " INTEGER NOT NULL , "
			+ DBContans.User_id + " VARCHAR , "
			+ DBContans.User_name + " VARCHAR , "
			+ DBContans.User_nick + " VARCHAR , "
			+ DBContans.User_phone + " VARCHAR NOT NULL , "
			+ DBContans.User_stmsg + " VARCHAR , "
			+ DBContans.User_photo + " VARCHAR , "
			+ DBContans.User_Section + " INTEGER NOT NULL ,"
			+ DBContans.User_Header + " VARCHAR ); ";
	
	public String CreateTableRoom = "CREATE TABLE " + DBContans.RoomTableName + "("
			+ DBContans.Room_Seq + " VARCHAR  NOT NULL , "
			+ DBContans.Room_Owner + " VARCHAR NOT NULL , "
			+ DBContans.Room_Member + " VARCHAR NOT NULL , "
			//+ DBContans.Room_Receiver + " VARCHAR NOT NULL , "
			+ DBContans.Room_UpdateDate + " DATETIME NOT NULL , "
			+ DBContans.Room_Type + " INTEGER , "
			//+ DBContans.Room_MsgCnt + " INTEGER , "
			+ DBContans.Room_NewMsg + " VARCHAR , "
			+ DBContans.Room_Title + " VARCHAR  , "
			+ DBContans.Room_MemberName + " VARCHAR  , "
			+ DBContans.Room_Regdate + " DATETIME NOT NULL )";
	
/*	public String CreateTableRoom = "CREATE TABLE " + DBContans.RoomTableName + "("
			+ DBContans.Room_Seq + " VARCHAR  NOT NULL , "
			+ DBContans.Room_Owner + " VARCHAR NOT NULL , "
			+ DBContans.Room_Receiver + " VARCHAR NOT NULL , "
			+ DBContans.Room_UpdateDate + " DATETIME NOT NULL , "
			+ DBContans.Room_Type + " INTEGER , "
			+ DBContans.Room_MsgCnt + " INTEGER , "
			+ DBContans.Room_NewMsg + " VARCHAR , "
			+ DBContans.Room_Title + " VARCHAR  , "
			+ DBContans.Room_Regdate + " DATETIME NOT NULL )";*/
	
	public String CreateTableRoomMember = "CREATE TABLE " + DBContans.RoomMemberTableName + "("
			+ DBContans.Room_Seq + " VARCHAR  NOT NULL , "
			+ DBContans.Room_Member + " VARCHAR NOT NULL , "
			+ DBContans.Room_MemberName + " VARCHAR NOT NULL ); ";
	
	public String CreateTableMSG = "CREATE TABLE " + DBContans.MSGTableName + "("
			+ DBContans.Msg_Key + " VARCHAR NOT NULL , "
			+ DBContans.Room_Seq + " VARCHAR NOT NULL , "
			+ DBContans.Msg_Type + " INTEGER NOT NULL  , "
			+ DBContans.Msg_Content + " VARCHAR NOT NULL , "
			/*+ DBContans.Msg_Member + " VARCHAR  , "*/
			+ DBContans.Msg_Sender + " VARCHAR NOT NULL , "
			+ DBContans.Msg_SenderName + " VARCHAR NOT NULL , "
			/*+ DBContans.Msg_Receiver + " VARCHAR NOT NULL , "*/
			+ DBContans.Msg_Regdate + " DATETIME NOT NULL , "
			+ DBContans.Msg_Status + " INTEGER NOT NULL );";
			/*+ DBContans.Msg_RoomType + " INTEGER NOT NULL )";*/
	
	public String CreateTableMSGMember = "CREATE TABLE " + DBContans.MSGMemTableName + "("
			+ DBContans.Msg_Key + " VARCHAR NOT NULL , "
			+ DBContans.Room_Seq + " VARCHAR NOT NULL , "
			/*+ DBContans.Room_Type + " INTEGER NOT NULL , "*/
			+ DBContans.Msg_Revseq + " VARCHAR NOT NULL , "
			+ DBContans.Msg_Regdate + " DATETIME NOT NULL )";
	
	
	public String CreateTableFailMSG = "CREATE TABLE " + DBContans.FailMSGTableName + "("
			+ DBContans.MSG_KEY + " VARCHAR NOT NULL , "
			+ DBContans.SEND_SEQ + " VARCHAR NOT NULL , "
			+ DBContans.MEMBER_SEQ + " VARCHAR NOT NULL , "
			+ DBContans.MESSAGE + " VARCHAR NOT NULL , "
			+ DBContans.SEND_NAME + " VARCHAR NOT NULL , "
			+ DBContans.SEND_TYPE + " VARCHAR NOT NULL , "
			+ DBContans.ROOM_SEQ + " VARCHAR NOT NULL , "
			+ DBContans.ROOM_TYPE + " VARCHAR NOT NULL , "
			+ DBContans.REV_NAME + " VARCHAR NOT NULL , "
			+ DBContans.MEM_NAME + " VARCHAR NOT NULL , "
			+ DBContans.REG_DATE + " VARCHAR NOT NULL );";
	
	public ChattingDB(Context context) {
		super(context, DBContans.DBNAME, null, DBContans.DBVERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			
			db.execSQL(CreateTableFavor);
			db.execSQL(CreateTableMyInfo);
			db.execSQL(CreateTableMember);
			db.execSQL(CreateTableRoom);
			db.execSQL(CreateTableMSG);
			db.execSQL(CreateTableMSGMember);
			db.execSQL(CreateTableFailMSG);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableFavor);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableMyInfo);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableMember);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableRoom);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableMSG);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableMSGMember);
			db.execSQL("DROP TABLE IF EXISTS " + CreateTableFailMSG);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public static SQLiteDatabase getWriteDB(Context context){
		try {
			ChattingDB dbadapter = new ChattingDB(context);
			SQLiteDatabase mDb = dbadapter.getWritableDatabase();
			return mDb;
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	public static SQLiteDatabase getReadDB(Context context){
		try {
			ChattingDB dbadapter = new ChattingDB(context);
			SQLiteDatabase mDb = dbadapter.getReadableDatabase();
			return mDb;
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
}
