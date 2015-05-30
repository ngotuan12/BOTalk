package com.bxm.boardtalks.login;


import kr.co.boardtalks.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityMemberLogin extends LoginBase {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_member_login);
	}

	public void skipToProfile(View v){
		Intent intent = new Intent(this, Activity_MyProfile.class);
		startActivity(intent);
		finish();
	}
	
	public void register(View v) {
		
		Intent intent = new Intent(this, Activity_member_Register.class);
		startActivity(intent);		
	}
	
	public void login(View v){
		Intent intent = new Intent(this, Activity_MyProfile.class);
		startActivity(intent);
		finish();
	}
	
	public void find(View v){
		Intent intent = new Intent(this, ActivityFindAccPasw.class);
		startActivity(intent);
	}

}
