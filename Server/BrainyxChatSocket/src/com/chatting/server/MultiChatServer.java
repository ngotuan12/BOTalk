package com.chatting.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.chatting.constans.Constans;
import com.chatting.push.PushController;
import com.chatting.vo.MessageVo;

public class MultiChatServer
{
	private HashMap<String, DataOutputStream> clients;
	private ServerSocket serverSocket;
	PushController pushController;

	public static void main(String[] args)
	{
		try
		{
			new MultiChatServer().start();
		}
		catch (Exception e)
		{
			System.exit(0);
		}
		
	}

	public MultiChatServer()
	{
		clients = new HashMap<String, DataOutputStream>();
		pushController = new PushController();
		// 여러 스레드에서 접근할 것이므로 동기화
		Collections.synchronizedMap(clients);

	}

	public void start() throws Exception
	{
		try
		{
			Socket socket;

			// 리스너 소켓 생성
			serverSocket = new ServerSocket(Constans.ChattingPort);
			System.out.println("Startting server on port "+ Constans.ChattingPort);

			// 클라이언트와 연결되면
			while (true)
			{
				// 통신 소켓을 생성하고 스레드 생성(소켓은 1:1로만 연결된다)
				socket = serverSocket.accept();
				ServerReceiver receiver = new ServerReceiver(socket);
				receiver.start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	class ServerReceiver extends Thread
	{
		Socket socket;
		DataInputStream input;
		DataOutputStream output;

		public ServerReceiver(Socket socket)
		{
			this.socket = socket;
			try
			{
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
			}
			catch (IOException e)
			{
			}
		}

		@Override
		public void run()
		{
			String name = "";
			String seq = "";
			try
			{
				// 클라이언트가 서버에 접속하면 대화방에 알린다.
				name = input.readUTF();
				MessageVo vo = ParsingMSG(name);
				/*
				 * sendToAll("#" + name + "[" + socket.getInetAddress() + ":" +
				 * socket.getPort() + "]" + "님이 대화방에 접속하였습니다.");
				 */
				seq = vo.getSEND_SEQ();
				clients.put(seq, output);
				// System.out.println(name + "[" + socket.getInetAddress() +
				// ":"+ socket.getPort() + "]" + "님이 대화방에 접속하였습니다.");
				// System.out.println("현재 " + clients.size() +
				// "명이 대화방에 접속 중입니다.");

				// 메세지 전송
				while (input != null)
				{
					sendToAll(input.readUTF());
				}
			}
			catch (IOException e)
			{
			}
			finally
			{
				// 접속이 종료되면
				clients.remove(seq);
				/*
				 * sendToAll("#" + name + "[" + socket.getInetAddress() + ":"+
				 * socket.getPort() + "]" + "님이 대화방에서 나갔습니다.");
				 */
				// System.out.println(name + "[" + socket.getInetAddress() +
				// ":"+ socket.getPort() + "]" + "님이 대화방에서 나갔습니다.");
				System.out.println("현재 " + clients.size() + "명이 대화방에 접속 중입니다.");
			}
		}

		public MessageVo ParsingMSG(String msge)
		{
			try
			{
				JSONObject jsonobj = (JSONObject) JSONValue.parse(msge);
				Iterator iter = jsonobj.keySet().iterator();
				MessageVo msgvo = new MessageVo();
				while (iter.hasNext())
				{
					String key = (String) iter.next();
					Object value = jsonobj.get(key);
					if (key.equals(Constans.MSG_KEY))
					{
						msgvo.setMSGKEY((String) value);
						// recevseq = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.SEND_SEQ))
					{
						msgvo.setSEND_SEQ((String) value);
						// sendseq = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.MESSAGE))
					{
						msgvo.setMESSAGE((String) value);
						// msg = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.SEND_NAME))
					{
						msgvo.setSEND_NAME((String) value);
						// sendname = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.SEND_TYPE))
					{
						msgvo.setSEND_TYPE((String) value);
						// sendtype = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.MEMBER_SEQ))
					{
						msgvo.setMEMBER_SEQ((String) value);
						// memberseq = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.ROOM_SEQ))
					{
						msgvo.setROOM_SEQ((String) value);
						// memberseq = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.ROOM_TYPE))
					{
						msgvo.setROOM_TYPE((String) value);
						// memberseq = (String)value;
						System.out.println((String) value);
					}
					else if (key.equals(Constans.REV_NAME))
					{
						msgvo.setREV_NAME((String) value);
					}
					else if (key.equals(Constans.MEMBER_NAME))
					{
						msgvo.setMEMBER_NAME((String) value);
					}
				}
				return msgvo;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		public String CreateMsg(MessageVo msgvo)
		{
			try
			{
				JSONObject obj = new JSONObject();
				obj.put(Constans.MSG_KEY, msgvo.getMSGKEY());
				obj.put(Constans.SEND_SEQ, msgvo.getSEND_SEQ());
				obj.put(Constans.MEMBER_SEQ, msgvo.getMEMBER_SEQ());
				obj.put(Constans.MESSAGE, msgvo.getMESSAGE());
				obj.put(Constans.SEND_NAME, msgvo.getSEND_NAME());
				obj.put(Constans.SEND_TYPE, msgvo.getSEND_TYPE());
				obj.put(Constans.ROOM_SEQ, msgvo.getROOM_SEQ());
				obj.put(Constans.ROOM_TYPE, msgvo.getROOM_TYPE());
				obj.put(Constans.REV_NAME, msgvo.getREV_NAME());
				obj.put(Constans.MEMBER_NAME, msgvo.getMEMBER_NAME());
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.SSS");
				Date date = new Date();
				/*
				 * obj.put("msgregdate",String.valueOf(System.currentTimeMillis()
				 * ));
				 */
				obj.put("msgregdate", dateFormat.format(date));

				return obj.toString();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		public void getFileSave(String savefile, DataInputStream dis)
		{
			try
			{

				File f = new File("C:\\노티베이션\\" + "teset.jpg");
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				// System.out.println(savefile + "파일을 생성하였습니다.");
				// DataOutputStream dos = clients.get(sendseq);
				// 바이트 데이터를 전송받으면서 기록
				int len;
				int size = 4096;
				byte[] data = new byte[size];
				while ((len = dis.read(data)) != -1)
				{
					bos.write(data, 0, len);
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
		}

		public void sendToAll(String message)
		{

			MessageVo msgvo = ParsingMSG(message);
			/*
			 * if(msgvo.getSEND_TYPE().equals(7)){
			 * clients.remove(msgvo.getSEND_SEQ());
			 * System.out.println("removemember ==  " + msgvo.getSEND_SEQ());
			 * return; }
			 */
			String[] members = getRoomMember(msgvo.getMEMBER_SEQ());
			for (int i = 0; i < members.length; i++)
			{
				if (clients.get(members[i]) != null)
				{
					try
					{
						DataOutputStream dos = clients.get(members[i]);
						if (dos != null)
						{
							String msge = CreateMsg(msgvo);
							System.out.println("sendmsg = to: "
									+ msgvo.getMEMBER_SEQ() + " - from : "
									+ msgvo.getSEND_SEQ());
							dos.writeUTF(msge);
						}
						else
						{
							clients.remove(members[i]);
							String msge = CreateMsg(msgvo);
							pushController.SendPushMessage(members[i], msge);
						}
					}
					catch (Exception e)
					{
						// e.printStackTrace();
						clients.remove(members[i]);
						String msge = CreateMsg(msgvo);
						pushController.SendPushMessage(members[i], msge);
					}
				}
				else
				{
					clients.remove(members[i]);
					String msge = CreateMsg(msgvo);
					pushController.SendPushMessage(members[i], msge);
				}
			}
		}

		public String[] getRoomMember(String member)
		{
			try
			{
				return member.split("_");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		public String setRoomMember(String[] member)
		{
			try
			{
				String members = "";
				for (int i = 0; i < member.length; i++)
				{
					if (members.length() == 0)
					{
						members = member[i];
					}
					else
					{
						members = members + "_" + member[i];
					}
				}
				return members;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return "";
		}

	}
}