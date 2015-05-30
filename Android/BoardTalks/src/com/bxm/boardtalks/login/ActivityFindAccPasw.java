package com.bxm.boardtalks.login;

import kr.co.boardtalks.R;
import android.os.Bundle;
import android.view.View;

public class ActivityFindAccPasw extends LoginBase {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_find_acc_pasw);
	}
	
	public void exit(View v){
		finish();
	}
	
		
}
