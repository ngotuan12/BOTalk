package com.chatting.server;

import java.io.DataOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class AlManager extends Thread{

	public static AlManager instance = null;
	public static AlManager getInstance(){
		return instance; 
	}
	
	
	 public HashMap<String, ChattingVo> clients; 
	 public HashMap<String, String> sendmsglist;
	 
	 public void init(){
		 sendmsglist = new HashMap<String, String>();
		 clients = new HashMap<String, ChattingVo>();
		 Collections.synchronizedMap(clients);
		 instance = this;
	 }
	 
	 public void AddMessege(String name,String messege){
		 sendmsglist.put(name,messege); //방번호가 키가되어 메세지스택을 쌓는다.
	 }
	 
	 public void AddClient(ChattingVo vo){
		 clients.put(vo.getName(), vo); //이름이 키가되어 접속정보를 담는다.
	 }
	 
	 public void RemoveClient(String name){
		  clients.remove(name);
	 }
	 
	 public int getClientSize(){
		 return clients.size();
	 }
	 
	 public ChattingVo getClient(String name){
		 return clients.get(name);
	 }
	@Override
	public void run() {
		super.run();
		while(true){
			synchronized (sendmsglist) {
				try {
					Iterator<String> it = sendmsglist.keySet().iterator();
					System.out.println("현재 메세지 스택 = " + sendmsglist.size());
					if(it.hasNext()  && it != null){
						String next = it.next();
						ChattingVo vo = clients.get(next);
						 if(vo != null && SocketManager.getInstance().getIsConnected(next)){
							 System.out.println("소켓접속됨 = " + next);
							 DataOutputStream dos = clients.get(next).getDataout();
							 String message = sendmsglist.get(next).toString();
				             dos.writeUTF(message);
				             System.out.println("메세지 보냄 = " + vo.getName() + ", 전달메세지 = " + message);
				             sendmsglist.remove(next);
						 }else{
							 System.out.println("소켓접속안됨 = " + next);
						 }
					}
					
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    /*    while (it.hasNext()) {
            try {
                DataOutputStream dos = clients.get(it.next()).getDataout();
                dos.writeUTF(msg);
                clients.remove(it.next());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }*/
	}
	
}
