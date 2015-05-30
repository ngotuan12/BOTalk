package com.bxm.boardtalks.member;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.activity.Activity_Profile;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.UserVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.RecycleUtils;
import com.brainyxlib.SharedManager;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.indexlistview.IndexableListView;
import com.bxm.boardtalks.main.BaseFragment;
import com.bxm.boardtalks.util.WriteFileLog;
public class Fragment_Member extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	public static Fragment_Member instance = null;

	public static Fragment_Member getInstance() {
		return instance;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == getActivity().RESULT_OK){
				switch(requestCode){
				case ChatGlobal.OnChatReQustCode:
					
					Intent intent = new Intent(getActivity(), Activity_Chat.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					ChatVo chatvo = new ChatVo();
					String myname = SharedManager.getInstance().getString(getActivity(), BaseGlobal.User_name); //내아이디
					String myseq = SharedManager.getInstance().getString(getActivity(), BaseGlobal.User_Seq); //내seq
					chatvo.chatRoomOwnerName = myname;
					chatvo.chatRoomOwner = myseq; //내seq
					chatvo.chatRoomRevName = getMyapp().memberMap.get(String.valueOf(mSelectSeq)).getUsername();
					String[] members = {myseq,String.valueOf(mSelectSeq)};
					chatvo.chatRoomSeq = String.valueOf(mSelectSeq);
					String[] membersname = {myname,chatvo.chatRoomRevName};
					chatvo.chatRoomMemberName = membersname;
					chatvo.chatRoomMember = members; // 멤버들
					chatvo.chatRoomType = ChatGlobal.chatTypeSingle; //싱글
					//chatvo.chatRoomName = mSelectUserName; // 상대이름
					intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); //방정보
					intent.putExtra(ChatGlobal.ChatRoomUser, mSelectUser); //상대정보
					
					startActivity(intent);
					
				break;
			}
		}
		
	}
	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {}

	@Override
	public void Onback() {}
	
	
	View view; 
	SwipeRefreshLayout refreshLayout = null;;
	IndexableListView listview;
	LinearLayout linearLayout_notdata = null;
	MemberAdapter adapter = null;
	UserVo mSelectUser  = null;
	//String mSelectUserId = "",mSelectUserName = "" ;
	int mSelectSeq = 0;
	ArrayList<MemberVo> narray = new ArrayList<MemberVo>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			getActivity().setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
			if (view == null) {
				view = inflater.inflate(R.layout.fragment_memberlist, container, false);
				instance = this;
				initialize();
				//getdata();
				mActionHandler.sendEmptyMessage(1);	
				//m_get_member_list.go?userId="test"
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
	
	public void Update(){
		mActionHandler.sendEmptyMessage(1);
	}
	
	private void initialize(){
		try {
			listview = (IndexableListView) view.findViewById(R.id.listview);
			adapter = new MemberAdapter(getActivity(), narray);
			listview.setAdapter(adapter);
			listview.setFastScrollEnabled(true);
			linearLayout_notdata = (LinearLayout)view.findViewById(R.id.linearLayout_notdata);
			refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
			refreshLayout.setOnRefreshListener(this);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//	ArrayList<MemberVo>memberarray = new ArrayList<MemberVo>(getMyapp().memberMap.values());
					if(listview.isShow()){
						return;
					}
					Intent intent = new Intent();
					intent.setClassName(getBaseActivity(), Activity_Profile.class.getName());
					//mSelectSeq =  getMyapp().memberArray.get(position).getSeq();
					mSelectSeq =  narray.get(position).getSeq();
					intent.putExtra(ChatGlobal.ProfileIntentSeq, String.valueOf(mSelectSeq)); //아이디
					intent.putExtra(ChatGlobal.ProfileIntentActivity, false); //멤버에서 넘어가는것
					
					startActivityForResult(intent, ChatGlobal.OnChatReQustCode);
					getBaseActivity().overridePendingTransition(0, 0);
				}
			});
			
		
			//MemberVo my = ChattingApplication.getInstance().memberArray.get(0);
			//ChattingApplication.getInstance().memberArray.remove(0);
			/*Collections.sort(ChattingApplication.getInstance().memberArray, new com.br.chat.vo.MemberVo.NameDescCompare());
			ChattingApplication.getInstance().memberArray.get(0).setHeader(true);
			ChattingApplication.getInstance().memberArray.add(0, my);*/
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void setViewArray(){
		narray.clear();
		
//		Collections.sort(getMyapp().memberArray, new com.br.chat.vo.MemberVo.NameDescCompare()); //초성 정렬하고
		if(getMyapp().memberArray.size()> 0 ){
			getMyapp().memberArray.get(0).setHeader(true); //초성 첫번째 데이터에 헤더 값 넣어주고	
		}
		
		narray.add(0, getMyapp().myinfo);
		if(getMyapp().favorArray.size() > 0){
			getMyapp().favorArray.get(0).setHeader(true);
			for(int i = 0 ; i < getMyapp().favorArray.size(); i++){
				if(i>0){
					getMyapp().favorArray.get(i).setHeader(false);
				}
				narray.add(getMyapp().favorArray.get(i));
			}	
		}
		for(int i = 0 ; i < getMyapp().memberArray.size(); i++){
			if(i > 0 ){
				getMyapp().memberArray.get(i).setHeader(false);
				narray.add(getMyapp().memberArray.get(i));	
			}else{
				narray.add(getMyapp().memberArray.get(i));
			}
		}
	}
	
	@Override
	public void onDestroy() {
		RecycleUtils.recursiveRecycle(view);
		System.gc() ;
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
		ChattingApplication.getInstance().getContactList(getActivity());
	}
	
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				refreshLayout.setRefreshing(false);
			//	refreshLayout.setRefreshing(false);
				setViewArray();
				//Collections.sort(ChattingApplication.getInstance().memberArray, new com.br.chat.vo.MemberVo.NameDescCompare());
				//adapter.notifyDataSetChanged();
				adapter.UpdateAdpeter();
				if(getMyapp().memberMap.size() == 0){
					linearLayout_notdata.setVisibility(View.VISIBLE);	
					//refreshLayout.setVisibility(View.GONE);
				}else{
					linearLayout_notdata.setVisibility(View.GONE);
					//refreshLayout.setVisibility(View.VISIBLE);
				}
				break;
				
			case 2:
				//refreshLayout.setRefreshing(false);
				linearLayout_notdata.setVisibility(View.VISIBLE);
				//refreshLayout.setVisibility(View.GONE);
				break;
			}
		}
	};
}
