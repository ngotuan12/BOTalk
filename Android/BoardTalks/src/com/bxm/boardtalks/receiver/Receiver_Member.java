package com.bxm.boardtalks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bxm.boardtalks.member.Fragment_Member;
import com.bxm.boardtalks.setting.Fragment_Setting;
import com.bxm.boardtalks.setting.SettingProfile;
import com.bxm.boardtalks.setting.SettingStatMsg;

public class Receiver_Member extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Fragment_Member.getInstance() != null){
			Fragment_Member.getInstance().Update();
		}
		if(Fragment_Setting.getinstance() != null){
			Fragment_Setting.getinstance().setView();
		}
		if(SettingProfile.getinstance() != null){
			SettingProfile.getinstance().setView();
		}
		if(SettingStatMsg.getinstance() != null){
			SettingStatMsg.getinstance().setView();
		}
	}

}
