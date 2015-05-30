package com.bxm.boardtalks.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.br.chat.ChatGlobal;
import com.br.chat.view.CircleImageView1;
import com.br.chat.view.ImageLoaderInterface;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.main.BaseFragment;
import com.bxm.boardtalks.util.WriteFileLog;

public class Fragment_Setting extends BaseFragment implements ImageLoaderInterface{

	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {}

	@Override
	public void Onback() {}

	public static Fragment_Setting instance = null;

	public static Fragment_Setting getinstance() {
		return instance;
	}
	
	View view; 
	CircleImageView1 profileview;
	String myseq ;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		try {
			if (view == null) {
				view = inflater.inflate(R.layout.fragment_setting, container, false);
				instance = this;
				init();
			} else {
				ViewGroup parent = (ViewGroup) view.getParent();
				if (parent != null)
					parent.removeView(view);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return view;
	}
	
	private void init(){
		try {
			profileview = (CircleImageView1)view.findViewById(R.id.profile_img);
			view.findViewById(R.id.set_profile).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), SettingProfile.class);
					startActivity(intent);
			
				}
			});
			myseq = SharedManager.getInstance().getString(getActivity(), BaseGlobal.User_Seq);
		//	imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(myseq)),profileview.getFaceView(),imageLoaderOption, animationListener);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setView();
	}
	public void setView(){
		imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(myseq)),profileview.getFaceView(),imageLoaderOption, animationListener);
	}
}
