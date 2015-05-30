package com.chatting.server;

import java.net.Socket;

public class SocketVo {

	public Socket socket;
	public boolean isConnected = false;
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
}
