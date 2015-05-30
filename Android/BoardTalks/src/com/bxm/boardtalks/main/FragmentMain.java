package com.bxm.boardtalks.main;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.br.chat.activity.ActivityFriendPicker;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.util.CreateKey;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.UserVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;

import kr.co.boardtalks.R;

import com.bxm.boardtalks.boards.BoardFragment;
import com.bxm.boardtalks.chatroom.Fragment_ChatList;
import com.bxm.boardtalks.me.MeFragment;
import com.bxm.boardtalks.member.Fragment_Member;
import com.bxm.boardtalks.setting.Fragment_Setting;
import com.bxm.boardtalks.util.WriteFileLog;

public class FragmentMain extends BaseActivity implements OnClickListener{

	public static FragmentMain instance = null;
	public SparseArray<View>views = new SparseArray<View>();
	public boolean onStop = false;
	
	public static FragmentMain getInstance(){
		return instance;
	}
	
	
	@Override
	public void onClick(View v) {
		
	}

	
	@Override
	protected void onStop() {
		super.onStop();
		onStop = true;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		/*if(SharedManager.getInstance().getBoolean(this, "pass")||onStop){
			Intent intent = new Intent(this,Password.class);
			intent.putExtra("password", 5);
			startActivity(intent);
			onStop = false;
		}*/
	}
	
	@Override
	public void onBackPressed() {
		try {
			// super.onBackPressed();
			//BrUtilManager.getInstance().ShowExitToast(this);
			 //  ImageLoader.getInstance().clearMemoryCache();
		      //  ImageLoader.getInstance().clearDiskCache();
			Intent intent = new Intent();
	        intent.setAction("android.intent.action.MAIN");
	        intent.addCategory("android.intent.category.HOME");
	        /*intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
	        | Intent.FLAG_ACTIVITY_FORWARD_RESULT
	        | Intent.FLAG_ACTIVITY_NEW_TASK
	        | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
	        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);*/
	        startActivity(intent);
	        myApp.DisconnectSocket();	
	     
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}finally{
			//myApp.Disconnect();
			//myApp.DisconnectSocket();	
		}
	}
	
	int mCurrentFragmentIndex;
	private RadioGroup tabGroups;
	final static int[] tabs = {
		R.id.buttonTab1,
		R.id.buttonTab2,
		R.id.buttonTab3,
		R.id.buttonTab4
		};
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	ChatVo chatvo;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Log.e("", "여기들어오나요??2" );
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);
		instance = this;
		initialize();

	}
	
	public void ChattingGo(ChatVo chatvo){
		this.chatvo = chatvo;
		  if(chatvo != null && myApp.isChatGo()){
			  Log.e(""	, "Activity_Chat 호출됨");
			  
			    if(Activity_Chat.getInstance() !=null) Activity_Chat.getInstance().finish();
			    
			    Intent intent = new Intent(this, Activity_Chat.class);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo);
			    startActivity(intent);
			}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e(""	, "onNewIntent 호출됨");
		  try{
	        	chatvo = (ChatVo)intent.getSerializableExtra(ChatGlobal.ChatRoomObj);
	        }catch(Exception e){
	        	WriteFileLog.writeException(e);
	        }
		  if(chatvo != null && myApp.isChatGo()){
			  Log.e(""	, "Activity_Chat 호출됨");
	        	intent = new Intent(this, Activity_Chat.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
				startActivity(intent);
			}
	}
	public void initialize() {
		
		mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
		tabGroups = (RadioGroup) findViewById(R.id.tab_groups);
		findViewById(R.id.chatroomcreate).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("action_activityFriendPickerBt");
            	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			i.addCategory(Intent.CATEGORY_DEFAULT);
    			//i.putExtra(ActivityChat.ChatRoomObject, swChatRoom);
    			startActivityForResult(i ,ActivityFriendPicker.RESPONSE_CODE);
			}
		});
		tabGroups.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				int curIdx = -1;
				for (int i = 0; i < tabs.length; i++) {
					if (tabs[i] == arg1) {
						curIdx = i;
						break;
					}
				}
				
				if(curIdx > -1){
					fragmentReplaces(curIdx);
				}
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == 0){
					((TextView)findViewById(R.id.titlename)).setText("친구 " + String.valueOf(myApp.memberMap.size()-1));
					findViewById(R.id.imgViewManageFriend).setVisibility(View.VISIBLE);
					findViewById(R.id.chatroomcreate).setVisibility(View.VISIBLE);
				}else if(arg0 == 1){
					((TextView)findViewById(R.id.titlename)).setText("대화");
					findViewById(R.id.imgViewManageFriend).setVisibility(View.VISIBLE);
					findViewById(R.id.chatroomcreate).setVisibility(View.VISIBLE);
				}else if(arg0 == 2){
					((TextView)findViewById(R.id.titlename)).setText("게시판");
					findViewById(R.id.imgViewManageFriend).setVisibility(View.INVISIBLE);
					findViewById(R.id.chatroomcreate).setVisibility(View.INVISIBLE);
				}else if(arg0 == 3){
					((TextView)findViewById(R.id.titlename)).setText("설정");
					findViewById(R.id.imgViewManageFriend).setVisibility(View.INVISIBLE);
					findViewById(R.id.chatroomcreate).setVisibility(View.INVISIBLE);
				}
				fragmentReplaces(arg0);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		fragmentReplaces(mCurrentFragmentIndex);
		
		
		Intent intent = getIntent();
        try{
        	chatvo = (ChatVo)intent.getSerializableExtra(ChatGlobal.ChatRoomObj);
        }catch(Exception e){
        	WriteFileLog.writeException(e);
        }
        if(chatvo != null && myApp.isChatGo()){
        	intent = new Intent(this, Activity_Chat.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
			startActivity(intent);
		}
    	((TextView)findViewById(R.id.titlename)).setText("친구 " + String.valueOf(myApp.memberMap.size()-1));
	}
	
	public void fragmentReplaces(int reqNewFragmentIndex) {
		//mCurrentFragmentIndex = reqNewFragmentIndex;
		fragmentReplace(reqNewFragmentIndex);
		//mViewPager.setCurrentItem(reqNewFragmentIndex);
	}
	
	
	public void addFragmentReplace(int layoutId, BaseFragment baseFragment, String name) {		
		clearBackStack();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(layoutId, baseFragment, name);
		ft.commit();			
	}	
	
	public void clearBackStack(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		/*
		for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {            
			fragmentManager.popBackStackImmediate();			
		}    
		*/
	}
	
	public void fragmentReplace(int reqNewFragmentIndex) {
		if (mCurrentFragmentIndex == reqNewFragmentIndex) {
			return;
		}
		mCurrentFragmentIndex = reqNewFragmentIndex;
		mViewPager.setCurrentItem(mCurrentFragmentIndex);
		((RadioButton)tabGroups.getChildAt(mCurrentFragmentIndex)).setChecked(true);
	/*	BaseFragment newFragment = null;
		newFragment = getFragment(reqNewFragmentIndex);
		
		Bundle args = new Bundle();
		args.putInt("fragment", reqNewFragmentIndex);
		newFragment.setArguments(args);
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.pager, newFragment,newFragment.getClass().getName());
			transaction.addToBackStack(null);
			transaction.commitAllowingStateLoss();*/
	}

	
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		Context mContext;
		SparseArray< View > views = new SparseArray< View >();
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);

		}
		
		public int getItemPosition(Object object) {
		    return POSITION_NONE;
		}
		
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new Fragment_Member();
			case 1:
				return new Fragment_ChatList();
			case 2:
				return new BoardFragment();
			case 3:
				return new MeFragment();
			}

			return null;
		}

		@Override
		public int getCount() {
			return 4;
		}
	}
	
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		super.onActivityResult(arg0, arg1, intent);
		
		if(arg1 == RESULT_OK){
			switch(arg0){
			case ActivityFriendPicker.RESPONSE_CODE:
				
				if(intent==null) return;
    			Serializable list = intent.getSerializableExtra(ActivityFriendPicker.VHUSERS);
	    		ArrayList<MemberVo> contactUsers = (ArrayList<MemberVo>)list;
	    		
	    		//선택안됬을때
	    		if(contactUsers==null || contactUsers.size()<=0)
	    			return;
	    		
	    		//중복유저는 제외
	    		ArrayList<UserVo> newUsers = new ArrayList<UserVo>();
	    		String myseq = SharedManager.getInstance().getString(FragmentMain.this, BaseGlobal.User_Seq);
	    		for(MemberVo user : contactUsers){
	    			//자신 제외
	    			if(myseq.equals(String.valueOf(user.getSeq())))
	    				continue;
	    			//if((new VHAccountManager()).getLoginUser(this).itemName.equals(user.itemName))
	    			
	    				UserVo vo = new UserVo();
	    				vo.UserId = user.getUsername();
	    				vo.UserSeq = String.valueOf(user.getSeq());
	    				
	    				newUsers.add(vo);
	    				
	    		}
	    		
	    		if(newUsers.size() > 0){
	    			intent = new Intent(FragmentMain.this, Activity_Chat.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ChatVo chatvo = new ChatVo();
					String myname = SharedManager.getInstance().getString(FragmentMain.this, BaseGlobal.User_name); //내아이디
	    	 		if(newUsers.size() == 1){ // 1대1채팅
						
						chatvo.chatRoomOwnerName = myname;
						chatvo.chatRoomOwner = myseq; //내seq
						chatvo.chatRoomRevName = myApp.memberMap.get(String.valueOf(newUsers.get(0).UserSeq)).getUsername();
						String[] members = {myseq,String.valueOf(newUsers.get(0).UserSeq)};
						chatvo.chatRoomSeq = String.valueOf(newUsers.get(0).UserSeq);
						String[] membersname = {myname,chatvo.chatRoomRevName};
						chatvo.chatRoomMemberName = membersname;
						chatvo.chatRoomMember = members; // 멤버들
						chatvo.chatRoomType = ChatGlobal.chatTypeSingle; //싱글
						//chatvo.chatRoomName = mSelectUserName; // 상대이름
						intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
						//intent.putExtra(ChatGlobal.ChatRoomUser, mSelectUser); //상대정보
						
		    		}else if(newUsers.size() > 1){ //그룹채팅
		    			intent = new Intent(FragmentMain.this, Activity_Chat.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						
						String[] chatmember2 = new String[newUsers.size()+1];
			    		String[] chatmembername2 = new String[newUsers.size()+1];
			    		for(int i = 0 ; i <( newUsers.size() +1); i++){
			    			if(i == newUsers.size()){ //마지막배열엔 내정보담음
			    				chatmember2[i] = myseq;
				    			chatmembername2[i] = myname;
			    			}else{
			    				chatmember2[i] = newUsers.get(i).UserSeq;
				    			chatmembername2[i] = newUsers.get(i).UserId;	
			    			}
			    		}
			    		chatvo.chatRoomOwnerName = myname;
			    		chatvo.chatRoomOwner = myseq; //내seq
			    		chatvo.chatRoomMember = chatmember2;
			    		String groupseq = CreateKey.getFileKey();
			    		chatvo.chatRoomType = ChatGlobal.chatTypeGroup;
			    		chatvo.chatRoomRevName = ChatGlobal.ChatRoupName;
			    		chatvo.chatRoomMemberName = chatmembername2;
			    		chatvo.chatRoomSeq = groupseq;
			    		intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
		    		}
	    	 		startActivity(intent);
	    		}
				break;
			}
		}
		
		
	}
}
