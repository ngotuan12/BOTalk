package com.br.chat.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.br.chat.ChattingApplication;
import com.br.chat.listview.ContactItemInterface;
import com.br.chat.listview.ExampleContactAdapter;
import com.br.chat.listview.ExampleContactListView;
import com.br.chat.listview.SoundSearcher;
import com.br.chat.util.Mylog;
import com.br.chat.vo.MemberVo;
import com.chattingmodule.R;


public class ActivityFriendPicker extends Activity implements TextWatcher{
	private static String TAG= "ActivityFriendPicker";
	public final static String VHUSERS = "MEMBERS";
	public static final int RESPONSE_CODE = 2351;
    
    private ExampleContactListView mListView;
    private TextView mTextView_empty;
    private EditText mEditText_search;
    private String searchString;
    private TextView mTextView_cancel;
    private TextView mTextView_done;
    private Object searchLock = new Object();
	boolean inSearchMode = false;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.push_up_in, R.anim.start_exit_stay_nothing);
        setContentView(R.layout.fragment_friend_picker);
        Mylog.e(TAG, "onCreate()");
        
        mTextView_cancel = (TextView) findViewById(R.id.activityChat_TextView_cancel);
        mTextView_done = (TextView) findViewById(R.id.activityChat_TextView_done);
        mTextView_done.setVisibility(View.INVISIBLE);
        mTextView_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition( 0, R.anim.push_down_out );
			}
		});
        mTextView_done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rightButton();
			}
		});
        
        
        mEditText_search = (EditText) findViewById(R.id.activityChat_EditText_search);
        mEditText_search.addTextChangedListener(this);
        
        mListView = (ExampleContactListView) findViewById(R.id.fragmentFriendPicker_ListView);
        mTextView_empty = (TextView) findViewById(R.id.fragmentFriendPicker_TextView_empty);
        mListView.setFastScrollEnabled(true);
        mListView.setDivider(null);
        mListView.setOnItemClickListener(mListItemClickListener);
      //  final ArrayList<UserVo> arrFriend = VHApplication.getInstance().getDBManager().getBundleSaver().getFriendList();
       // ArrayList<MemberVo>memberArray = new ArrayList<MemberVo>(ChattingApplication.getInstance().memberMap.values());
    	//Collections.sort(ChattingApplication.getInstance().memberArray, new com.br.chat.vo.MemberVo.NameDescCompare());
        getFrientList(ChattingApplication.getInstance().memberArray);
        
    }
    
    private List<ContactItemInterface> contactList;
	private List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
    private ArrayList<MemberVo> mArrVHUser;
    private void getFrientList(ArrayList<MemberVo> arrFriend){
    	if(arrFriend==null || arrFriend.size()<=0){
    		
    	}
    		//arrFriend = VHApplication.getInstance().getDBManager().getBundleSaver().getFriendList();
    	
    	filterList = new ArrayList<ContactItemInterface>();
		//contactList = ExampleDataSource.getSampleContactList(this);
    	contactList = new ArrayList<ContactItemInterface>();
		for(MemberVo user:arrFriend){
			contactList.add(user);
		}
    	
    	if(arrFriend==null|| arrFriend.size()<=0){
    		mTextView_empty.setVisibility(View.VISIBLE);
    		mListView.setVisibility(View.GONE);
    	}else{
    		mTextView_empty.setVisibility(View.GONE);
    		mListView.setVisibility(View.VISIBLE);
			mListView.setAdapter(null);
    		
    		mArrVHUser = arrFriend;
    		if (mListView!=null){
    			ExampleContactAdapter adapter = new ExampleContactAdapter(this, R.layout.adapter_contact_item, contactList);
    	        mListView.setAdapter(adapter);
//        		mAdapterFriendList = new AdapterFriendList(mActivityMain, mArrVHUser);
//            	mListView.setAdapter(mAdapterFriendList);
            }
    	}
	}
    
    private Handler customHandler = new Handler();
	
    private OnItemClickListener mListItemClickListener = new OnItemClickListener(){
		@SuppressLint("NewApi")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			int size = 0;
			
			/*if(Utils.hasHoneycomb()){
				size = mListView.getCheckedItemCount();
			}else{*/
				ArrayList<ContactItemInterface> users = new ArrayList<ContactItemInterface>();
				int len = mListView.getCount();
				
		        SparseBooleanArray checked = mListView.getCheckedItemPositions();
		        for (int i = 0; i < len; i++){
		        	if (checked.get(i)) {
		        		if(inSearchMode){
		        			MemberVo item = (MemberVo)filterList.get(i);
			        		users.add(item);
		        		}else{
		        			MemberVo item = (MemberVo)contactList.get(i);
			        		users.add(item);
		        		}
		        		
		        	}
		        }
		        size = users.size();
		//	}
			
	        
			if(size > 0){
				mTextView_done.setVisibility(View.VISIBLE);
			}else{
				mTextView_done.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	
	@Override
	public void afterTextChanged(Editable s) {
		

		searchString = mEditText_search.getText().toString().trim().toUpperCase();

		if(curSearchTask!=null && curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
		{
			try{
				curSearchTask.cancel(true);
			}
			catch(Exception e){
				Log.i(TAG, "Fail to cancel running search task");
			}
			
		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); // put it in a task so that ui is not freeze
    }
    
    
    @Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
    	// do nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// do nothing
	}
    
	private class SearchListTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			filterList.clear();
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
//			filterList.clear();
			
			String keyword = params[0];
			
			inSearchMode = (keyword.length() > 0);

			if (inSearchMode) {
				// get all the items matching this
				for (ContactItemInterface item : contactList) {
					MemberVo contact = (MemberVo)item;
					
					/*if ((contact.getItemForIndex().toUpperCase().indexOf(keyword) > -1) ) {
						filterList.add(item);
					}*/
					if(SoundSearcher.matchString(contact.getItemForIndex().toUpperCase(), keyword)){
						filterList.add(item);
					}

				}


			} 
			return null;
		}
		
		protected void onPostExecute(String result) {
			
			synchronized(searchLock)
			{
			
				if(inSearchMode){
					
					ExampleContactAdapter adapter = new ExampleContactAdapter(ActivityFriendPicker.this, R.layout.adapter_contact_item, filterList);
					adapter.setInSearchMode(true);
					mListView.setInSearchMode(true);
					mListView.setAdapter(adapter);
				}
				else{
					ExampleContactAdapter adapter = new ExampleContactAdapter(ActivityFriendPicker.this, R.layout.adapter_contact_item, contactList);
					adapter.setInSearchMode(false);
					mListView.setInSearchMode(false);
					mListView.setAdapter(adapter);
				}
			}
			
		}
	}
	
    
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition( 0, R.anim.push_down_out );
	}
	
	private void rightButton(){
		ArrayList<ContactItemInterface> users = new ArrayList<ContactItemInterface>();
		
		int len = mListView.getCount();
		
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        for (int i = 0; i < len; i++){
        	if (checked.get(i)) {
        		if(inSearchMode){
        			MemberVo item = (MemberVo)filterList.get(i);
	        		users.add(item);
        		}else{
        			MemberVo item = (MemberVo)contactList.get(i);
	        		users.add(item);
        		}
        		
        	}
        }
        
        if(users.size()==0){
			onBackPressed();
			return;
		}
        
        Intent intent = new Intent();
        intent.putExtra(VHUSERS, users);
		setResult(RESULT_OK, intent);
		finish();
		overridePendingTransition( 0, R.anim.push_down_out );
	}
	
}

