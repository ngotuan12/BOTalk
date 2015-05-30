package com.chatting.server;

import java.io.DataOutputStream;
import java.net.InetAddress;

public class ChattingVo {

	public String msg = "";
	public String name = "";
	public String deviceID = "";
	public InetAddress ipaddress = null;
	public int ipport = 0;
	public DataOutputStream dataout = null;
	public boolean isConnect = false;
	public String roomnum = "";
	
	
	
	public String getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}
	public boolean isConnect() {
		return isConnect;
	}
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}
	public DataOutputStream getDataout() {
		return dataout;
	}
	public void setDataout(DataOutputStream dataout) {
		this.dataout = dataout;
	}
	public InetAddress getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(InetAddress ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getIpport() {
		return ipport;
	}
	public void setIpport(int ipport) {
		this.ipport = ipport;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	
}
