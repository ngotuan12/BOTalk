package com.br.chat.socket;

import java.io.DataOutputStream;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.chat.ChatGlobal;
import com.br.chat.adapter.ChatType;
import com.br.chat.util.WriteFileLog;

public class ConnetedSocket
{

	public interface ConnectedListner
	{
		public void setConnectedListner();
	}

	private Socket socket;

	private Context context;
	private DataOutputStream output;
	private SocketThreadReceiver clientReceiver;

	public ConnectedListner listner;

	public ConnetedSocket(Context _context, ConnectedListner _listner)
	{
		context = _context;
		listner = _listner;
	}

	public SocketThreadReceiver getSocketReceiver()
	{
		return clientReceiver;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void ConnectSocket(final String sendseq, final String sendname)
	{
		AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					Connect(sendseq, sendname);
				}
				catch (Exception e)
				{
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resultvalue)
			{
				try
				{

				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		// getDataWorker.execute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		}
		else
		{
			getDataWorker.execute((Void[]) null);
		}

	}

	/**
	 * 
	 * @param memseq
	 *            접속정보를 전달할 멤버들
	 * @param myseq
	 * @param myname
	 * @param roomtype
	 */
	public void StartSocket(final String[] memseq, final String msgkey,
			final String roomseq, final String sendseq, final String sendname,
			final int roomtype, final String revname, final String[] membername)
	{
		AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					Start(memseq, roomseq, msgkey, sendseq, sendname, roomtype,
							revname, membername);
				}
				catch (Exception e)
				{
					WriteFileLog.writeException(e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resultvalue)
			{
				try
				{

				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		// getDataWorker.execute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		}
		else
		{
			getDataWorker.execute((Void[]) null);
		}

	}

	public void ProfileModifyMSG(final String[] memseq, final String msgkey,
			final String sendseq, final int sendtype, final String msg)
	{
		AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					SendProfile(memseq, msgkey, sendseq, sendtype, msg);
				}
				catch (Exception e)
				{
					WriteFileLog.writeException(e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resultvalue)
			{
				try
				{

				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		// getDataWorker.execute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		}
		else
		{
			getDataWorker.execute((Void[]) null);
		}

	}

	public void DisConnect(final String sendseq)
	{
		try
		{
			AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
			{
				@Override
				protected void onPreExecute()
				{
					try
					{
					}
					catch (Exception e)
					{
					}
					super.onPreExecute();
				}

				@Override
				protected Void doInBackground(Void... params)
				{
					try
					{
						output = new DataOutputStream(socket.getOutputStream()); // 데이터
																					// 전송
																					// 객체
						output.writeUTF(CreateMessage.getCreatedisMSG(sendseq,
								ChatType.remove));
					}
					catch (Exception e)
					{
						WriteFileLog.writeException(e);
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void resultvalue)
				{
					try
					{

					}
					catch (Exception e)
					{
						// TODO: handle exception
					}
					finally
					{

					}
					super.onPostExecute(resultvalue);
				}
			};
			// getDataWorker.execute();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			{
				getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						(Void[]) null);
			}
			else
			{
				getDataWorker.execute((Void[]) null);
			}

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	public void Connect(String sendseq, String sendname)
	{
		try
		{
			SocketAddress socketAddress = new InetSocketAddress(
					ChatGlobal.BaseChattingIp, ChatGlobal.BaseChattingPort);
			socket = new Socket(); // 소켓접속
			socket.connect(socketAddress, 3000);

			clientReceiver = new SocketThreadReceiver(context, socket); // 소켓쓰래드
																		// 생성
			clientReceiver.start();
			output = new DataOutputStream(socket.getOutputStream()); // 데이터 전송
																		// 객체
			output.writeUTF(CreateMessage.getConnectMSG(sendseq, sendname));
			if (listner != null)
			{
				listner.setConnectedListner();
			}

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
		finally
		{
			Log.e("", "");
		}
	}

	public void SendProfile(final String[] memseq, final String msgkey,
			final String sendseq, final int sendtype, final String msg)
	{
		try
		{
			/*
			 * socket = new Socket(ChatGlobal.BaseChattingIp,
			 * ChatGlobal.BaseChattingPort); //소켓접속 SocketThreadReceiver
			 * clientReceiver = new SocketThreadReceiver(context,socket);
			 * //소켓쓰래드 생성 //ClientSender clientSender = new
			 * ClientSender(socket); clientReceiver.start();
			 */
			output = new DataOutputStream(socket.getOutputStream()); // 데이터 전송
																		// 객체
			output.writeUTF(CreateMessage.getCreateMSG(memseq, msgkey, sendseq,
					"", sendtype, msg, "", 0, "", null));
			// if(roomtype == 1){ //1:1채팅방 접속

			/*
			 * JSONObject obj = new JSONObject(); obj.put("recevseq", memseq);
			 * obj.put("sendseq", myseq); obj.put("sendname", myname);
			 * obj.put("sendtype","5"); output.writeUTF(obj.toString()); //데이터전문
			 * 이름,메세지,디바이스id,방번호
			 *//*
				 * }else if(roomtype == 2){ // 단체 채팅방 접속 //output.writeUTF(name+
				 * "," +""+ "," + deviceID + "," + roomnum + "," + "5"); //데이터전문
				 * 이름,메세지,디바이스id,방번호 }
				 */
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}

	}

	public void Start(final String[] member, String roomseq,
			final String msgkey, final String sendseq, final String sendname,
			final int roomtype, final String revname, final String[] membername)
	{
		try
		{
			/*
			 * socket = new Socket(ChatGlobal.BaseChattingIp,
			 * ChatGlobal.BaseChattingPort); //소켓접속 SocketThreadReceiver
			 * clientReceiver = new SocketThreadReceiver(context,socket);
			 * //소켓쓰래드 생성 //ClientSender clientSender = new
			 * ClientSender(socket); clientReceiver.start();
			 */
			output = new DataOutputStream(socket.getOutputStream()); // 데이터 전송
																		// 객체
			output.writeUTF(CreateMessage.getCreateMSG(member, msgkey, sendseq,
					sendname, ChatType.read, "", roomseq, roomtype, revname,
					membername));
			// if(roomtype == 1){ //1:1채팅방 접속

			/*
			 * JSONObject obj = new JSONObject(); obj.put("recevseq", memseq);
			 * obj.put("sendseq", myseq); obj.put("sendname", myname);
			 * obj.put("sendtype","5"); output.writeUTF(obj.toString()); //데이터전문
			 * 이름,메세지,디바이스id,방번호
			 *//*
				 * }else if(roomtype == 2){ // 단체 채팅방 접속 //output.writeUTF(name+
				 * "," +""+ "," + deviceID + "," + roomnum + "," + "5"); //데이터전문
				 * 이름,메세지,디바이스id,방번호 }
				 */
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}

	}

	public void SendFileMessage(final String[] member, final String msgkey,
			final String sendseq, final String msg, final String sendname,
			final int sendtype, final String roomseq, final int roomtype,
			final String revname, final String[] membername,
			final String filepath)
	{
		AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					// String filepath = SendImg(msg, msgkey);
					output.writeUTF(CreateMessage.getCreateMSG(member, msgkey,
							sendseq, sendname, sendtype, filepath, roomseq,
							roomtype, revname, membername));

				}
				catch (Exception e)
				{
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resultvalue)
			{
				try
				{
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		// getDataWorker.execute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		}
		else
		{
			getDataWorker.execute((Void[]) null);
		}

	}

	public void SendMessage(final String[] member, final String msgkey,
			final String sendseq, final String msg, final String sendname,
			final int sendtype, final String roomseq, final int roomtype,
			final String revname, final String[] membername)
	{
		AsyncTask<Void, Void, Void> getDataWorker = new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					if (sendtype == ChatType.text)
					{
						output.writeUTF(CreateMessage.getCreateMSG(member,
								msgkey, sendseq, sendname, sendtype, msg,
								roomseq, roomtype, revname, membername));
					}
					else if (sendtype == ChatType.file)
					{
						// String filepath = SendImg(msg, msgkey);
						// output.writeUTF(CreateMessage.getCreateMSG(member,msgkey,
						// sendseq, sendname, sendtype,
						// filepath,roomseq,roomtype,revname,membername));
					}
					else if (sendtype == ChatType.add)
					{
						output.writeUTF(CreateMessage.getCreateMSG(member,
								msgkey, sendseq, sendname, sendtype, msg,
								roomseq, roomtype, revname, membername));
					}
					else if (sendtype == ChatType.leave)
					{
						output.writeUTF(CreateMessage.getCreateMSG(member,
								msgkey, sendseq, sendname, sendtype, msg,
								roomseq, roomtype, revname, membername));
					}
					else
					{
						output.writeUTF(CreateMessage.getCreateMSG(member,
								msgkey, sendseq, sendname, sendtype, msg,
								roomseq, roomtype, revname, membername));
					}

				}
				catch (Exception e)
				{
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void resultvalue)
			{
				try
				{
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		// getDataWorker.execute();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getDataWorker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					(Void[]) null);
		}
		else
		{
			getDataWorker.execute((Void[]) null);
		}

	}

	public String SendImg(String imgpath, String msgkey)
	{
		try
		{
			BasicAWSCredentials credentials = new BasicAWSCredentials(
					ChatGlobal.AMAZON_A3_ACCESS_KEY,
					ChatGlobal.AMAZON_A3_SECRET_KEY);
			AmazonS3 s3 = new AmazonS3Client(credentials);
			PutObjectRequest putObjectRequest = null;
			File uploadfile = new File(imgpath);
			putObjectRequest = new PutObjectRequest(
					ChatGlobal.AMAZON_A3_BUCKET_NAME, msgkey + "/"
							+ uploadfile.getName(), uploadfile);
			putObjectRequest
					.setCannedAcl(CannedAccessControlList.PublicReadWrite); // URL
																			// 접근시
																			// 권한
																			// 읽을수
																			// 있도록
																			// 설정.
			String filePath = "http://s3-"
					+ s3.getBucketLocation(ChatGlobal.AMAZON_A3_BUCKET_NAME)
					+ ".amazonaws.com/" + ChatGlobal.AMAZON_A3_BUCKET_NAME
					+ "/" + msgkey + "/" + uploadfile.getName();
			s3.putObject(putObjectRequest);
			return filePath;
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
		return null;
	}
	
	/**
	 * @author TuanNA
	 * @since 05-28-2015
	 * @param strMsg
	 */
	public void sendMsg(String strMsg)
	{
		
	}
	
	/**
	 * @author TuanNA
	 * @since 05-28-2015
	 */
	public void disconnect()
	{
		try
		{
			if(output!=null)
				output.close();
			if(socket!=null)
				socket.close();
			output = null;
			socket = null;
		}
		catch (Exception ex)
		{
			WriteFileLog.writeException(ex);
		}
	}
	
}
