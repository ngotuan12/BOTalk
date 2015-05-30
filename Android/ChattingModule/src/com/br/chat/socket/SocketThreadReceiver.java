package com.br.chat.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.chat.util.WriteFileLog;

public class SocketThreadReceiver extends Thread
{

	Socket socket;
	DataInputStream input;
	Context context;

	public SocketThreadReceiver(Context context, Socket socket)
	{
		this.socket = socket;
		this.context = context;
		try
		{
			input = new DataInputStream(socket.getInputStream());
		}
		catch (IOException e)
		{
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (input != null)
			{
				String inputs = input.readUTF();
				Intent intent = new Intent("android.intent.action.Backgroundbt");
				intent.putExtra("data", inputs);
				intent.putExtra("send", true);
				context.sendBroadcast(intent);
				Log.e("", "메세지 받았음" + inputs);
			}
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
		finally
		{
			input = null;
		}
	}

}
