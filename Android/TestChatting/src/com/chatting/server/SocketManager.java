package com.chatting.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class SocketManager extends Thread{
	
	public static SocketManager instance = null;
	
	public static SocketManager getInstance(){
		return instance;
	}
	//public HashMap<String, ChattingVo> clients;
	public HashMap<String, SocketVo> socketlist = null;

	public void init(){
		socketlist = new HashMap<String, SocketVo>();
		instance = this;
	}
	
	/*public void setClient(HashMap<String, ChattingVo> clients,HashMap<String, Socket> socketlist){
		this.clients = clients;
		this.socketlist = socketlist;
	}*/
	
	
	public void addSocketList(String name,Socket socket){
		SocketVo vo = new SocketVo();
		vo.setConnected(true); // 커넥션여부
		vo.setSocket(socket); // 소켓객체
		socketlist.put(name, vo); // 키는 접속자명으로 구분
	}
	
	public void UpdateSocket(String name){
		try {
			SocketVo vo = socketlist.get(name);
	         vo.setConnected(true);
	         System.out.println("현재 접속중 = " + name);
	     	 socketlist.replace(name, vo);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	@Override
	public void run() {
		super.run();
		try {
			while(true){
			//	synchronized (socketlist) {
					Iterator<String> it = socketlist.keySet().iterator();
					System.out.println("현재 소켓리스트 스택 = " + socketlist.size());
					if(it.hasNext() && it != null){
						String next = it.next();
						ChattingVo client = AlManager.getInstance().getClient(next);
						 DataOutputStream dos = client.getDataout();
			             dos.writeUTF("##접속테스트");
			             System.out.println("접속테스트 보냄 = " + client.getName());
			             SocketVo vo = socketlist.get(next);
			             vo.setConnected(false);
			         	 socketlist.replace(next, vo);
						/*if(socketlist.get(next).getSocket().isConnected()){
							SocketVo vo = socketlist.get(next);
							vo.setConnected(true);
							//vo.setSocket(socketlist.get((String)it.next()).getSocket());
						
							
						}else{
							SocketVo vo = socketlist.get(next);
							vo.setConnected(false);
							//vo.setSocket(socketlist.get((String)it.next()).getSocket());
							socketlist.replace(next, vo);
						}*/
					}else{
						
					}
					Thread.sleep(10000);
			//	}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public boolean getIsConnected(String name){
		return socketlist.get(name).isConnected();
	}
	
}
