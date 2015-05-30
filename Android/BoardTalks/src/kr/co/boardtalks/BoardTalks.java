package kr.co.boardtalks;

import com.br.chat.ChattingApplication;
import com.br.chat.util.Request;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

public class BoardTalks extends ChattingApplication {

	public Request request = null; 
	
	//public ArrayList<MemberVo>arraylist = new ArrayList<MemberVo>();
	@Override
	public void onCreate() {
		super.onCreate();
		
		request = Request.getInstance();
	
	}


	public void Disconnect(){
		String sendseq = SharedManager.getInstance().getString(getApplicationContext(),BaseGlobal.User_Seq);
		ChattingApplication.getInstance().socket.DisConnect(sendseq);
		
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	//	Disconnect();
		DisconnectSocket();	
	}
}
