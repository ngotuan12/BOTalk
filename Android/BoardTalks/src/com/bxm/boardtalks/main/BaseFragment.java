package com.bxm.boardtalks.main;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import kr.co.boardtalks.BoardTalks;
import kr.co.boardtalks.R;

import com.bxm.boardtalks.util.WriteFileLog;


public abstract class BaseFragment extends Fragment {
	abstract public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2);	
	abstract public void Onback();
	
	public LinearLayout view;
	public ArrayList<View>viewhistory = new ArrayList<View>();
	public BoardTalks  myapp;
	
	public BoardTalks getMyapp(){
		myapp = (BoardTalks) getActivity().getApplicationContext();
		return myapp;
	}
	
	protected FragmentMain getBaseActivity(){
		FragmentActivity fragment = getActivity();
	    return (FragmentMain) fragment;	    
	}	
	
	public void ViewResize(){
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public void AddFragment(int id,Fragment fragment){
		try{
			FragmentManager fm = getFragmentManager();
			Bundle bundle = new Bundle();
			fragment.setArguments(bundle);
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.setCustomAnimations(R.anim.start_enter, R.anim.start_exit);
			transaction.add(id, fragment,fragment.getClass().getName());
			transaction.commit();
		}catch(Exception e){
			
			FragmentManager fm = getFragmentManager();
			Bundle bundle = new Bundle();
			fragment.setArguments(bundle);
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.setCustomAnimations(R.anim.start_enter, R.anim.start_exit);
			transaction.add(id, fragment,fragment.getClass().getName());
			transaction.commitAllowingStateLoss();
			WriteFileLog.writeException(e);
		}
	}
	
	public void RemoveFragment(Fragment framment){
		try {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.setCustomAnimations(R.anim.end_enter, R.anim.end_exit);
			transaction.remove(framment);
			
			transaction.commit();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void RemoveMap(Fragment fragment){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.remove(fragment);
		transaction.commit();
	}
	
	public void SendCall(String number){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ number));
		startActivity(intent);
	}
	/**
	 * ?���? �? ?��?�� true : ?��?��?��, false : ?��?��?���?
	 * @param url
	 * @param flag
	 */
	public void GoWebView(String url,boolean flag){
		
	}

	public void LoadingVisible(LinearLayout loading){
		try {
			loading.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void LoadingGone(LinearLayout loading){
		try {
			loading.setVisibility(View.GONE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public boolean IsFragment(String classname){
		Fragment fragment = getBaseActivity().getVisibleFragment(0);
		if(fragment instanceof Fragment){
			if(fragment.getTag().equals(classname)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	public void addanimation(){
		getBaseActivity().overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	}
	public void StartActivity(String classname){
		Intent intent = new Intent();
		intent.setClassName(getBaseActivity(), classname);
		startActivity(intent);
		//getBaseActivity().overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
		getBaseActivity().overridePendingTransition(0, 0);
	}
}
