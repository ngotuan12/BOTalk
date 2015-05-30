package com.bxm.boardtalks.main;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.br.chat.util.Request;

import kr.co.boardtalks.BoardTalks;
import kr.co.boardtalks.R;

import com.bxm.boardtalks.util.WriteFileLog;

public abstract class BaseActivity extends FragmentActivity{

	public static BaseActivity instance = null;
	public static BaseActivity getInstance(){
		return instance;
	}
	public BoardTalks myApp = null;
	public Request request = null;
	
	
	abstract public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2);
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		instance = this;
		myApp = (BoardTalks) getApplicationContext();
		request = Request.getInstance();
	}
	
	protected void addFragment(int layoutId, BaseFragment baseFragment, String name) {		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(layoutId, baseFragment, name);
		ft.addToBackStack(null);
		ft.commit();			
	}	
	
	protected void addFragment(int layoutId, BaseFragment baseFragment) {		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(layoutId, baseFragment);
		ft.addToBackStack(null);
		ft.commit();			
	}	
	
	protected void removeFragment(BaseFragment baseFragment) {		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.remove(baseFragment);
		ft.commit();
	}		
	
	protected void removeAllFragment(){
		try{
			  FragmentManager fragmentManager = getSupportFragmentManager();
			    List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
			    if(fragments != null){
			    	for(int i = 0 ;i < fragments.size(); i ++){
			    		if(fragments != null){
			    			removeFragment((BaseFragment)fragments.get(i));	
			    		}
			    		}
			    	}
		}catch(Exception e){
			WriteFileLog.writeException(e);
		}
	}
	
	protected android.support.v4.app.Fragment getVisibleFragment(int flag){
		try{
			  FragmentManager fragmentManager = getSupportFragmentManager();
			    List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
 			    if(fragments != null){
			    	for(int i = fragments.size() ; i > 0 ; i--){
			    			if(fragments.get(i-1) != null){
				    			return fragments.get(i-1);
				    		}
			    		}
			    	}
			    	/*  for(android.support.v4.app.Fragment fragment : fragments){
					        if(fragment != null && fragment.isVisible())
					            return fragment;
					    }*/
		}catch(Exception e){
			WriteFileLog.writeException(e);
		}
	    return null;
	}
	
	protected int getVisibleFragmentCount(){
		try{
			   FragmentManager fragmentManager = getSupportFragmentManager();
			    List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
			    
			    return fragments.size();
		}catch(Exception e){
			WriteFileLog.writeException(e);
		}
		 return 0;
	}
	
	protected Fragment getFragmentTag(String tag){
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(tag);
		return fragment;
	}
	
	protected int getBackStackCount(){
	    FragmentManager fragmentManager = getSupportFragmentManager();
	    return fragmentManager.getBackStackEntryCount();	    
	}	
	
	public void clearBackStack(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
	
/*	public void onMoveTab(int index,String name){
		FragmentMain main = FragmentMain.getInstance();
		if(main != null){
			main.MoveFragmentTab(index, name);
		}
	}*/
	
	public void RemoveAnimation(){
		overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
	public void addAnimation(){
		overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	}
	
	
}
