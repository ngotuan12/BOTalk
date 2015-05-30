package com.br.chat.socket;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.br.chat.util.WriteFileLog;
import com.br.chat.vo.ChatVo;

public class CreateMessage {

	/**
	 * 일반 메세지 및 읽음 메세지 처리
	 * @param memberseq
	 * @param msgkey
	 * @param sendseq
	 * @param sendname
	 * @param sendtype
	 * @param msg
	 * @param roomseq
	 * @return
	 */
	public static String getCreateMSG(String[] memberseq, String msgkey,String sendseq, String sendname, int sendtype,String msg,String roomseq,int roomtype,String revname,String[] membername){
		try {
			
			JSONObject obj = new JSONObject();
        	obj.put("memberseq", ChatVo.setRoomMember(memberseq));
        	obj.put("msgkey", msgkey);
        	obj.put("sendseq", sendseq);
        	obj.put("msg", msg);
        	obj.put("sendname", sendname);
        	obj.put("sendtype",String.valueOf(sendtype));
        	obj.put("roomseq",roomseq);
        	obj.put("roomtype",String.valueOf(roomtype));
        	obj.put("revname",revname);
        	obj.put("membername", ChatVo.setRoomMember(membername));
			return obj.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
	
	public static String getCreatedisMSG(String sendseq,int sendtype){
		try {
			
			JSONObject obj = new JSONObject();
        	obj.put("sendseq", sendseq);
        	obj.put("sendtype",String.valueOf(sendtype));
			
			return obj.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
	
	/**
	 * 소켓 접속 메세지
	 * @param sendseq
	 * @param sendname
	 * @return
	 */
	public static String getConnectMSG(String sendseq, String sendname){
		try {
			JSONObject obj = new JSONObject();
			obj.put("sendname", sendname);
        	obj.put("sendseq", sendseq);
        	return obj.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
	
	/**
	 * 읽음 메세지 전송
	 * @param memberseq
	 * @param roomseq
	 * @param sendseq
	 * @param sendname
	 * @return
	 */
	public static String getReadMsg(String[] memberseq, String roomseq,String sendseq, String sendname){
		try {
			JSONObject obj = new JSONObject();
			obj.put("memberseq", ChatVo.setRoomMember(memberseq));
			obj.put("sendname", sendname);
        	obj.put("sendseq", sendseq);
        	obj.put("roomseq",roomseq);
        	return obj.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
	
	public static String getMemberSeq(ArrayList<String> arraylist){
		try {
			JSONArray jarray = new JSONArray();
			for(int i = 0 ; i < arraylist.size(); i++){
				JSONObject obj = new JSONObject();
				obj.put("recevseq", arraylist.get(i));
				jarray.put(obj);
			}
			return jarray.toString();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return "";
	}
}
