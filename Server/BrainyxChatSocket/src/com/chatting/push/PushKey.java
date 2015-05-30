package com.chatting.push;


public class PushKey {
	String userId = "";
	String pushKey = "";
	String os = "";
	
	public String getPushKey() {
		return pushKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
}
