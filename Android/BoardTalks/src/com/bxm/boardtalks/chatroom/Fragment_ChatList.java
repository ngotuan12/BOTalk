package com.bxm.boardtalks.chatroom;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.br.chat.ChatGlobal;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.service.ChattingService;
import com.br.chat.vo.ChatRoomVo;
import com.br.chat.vo.ChatVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.RecycleUtils;
import com.brainyxlib.SharedManager;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.main.BaseFragment;
import com.bxm.boardtalks.util.WriteFileLog;

public class Fragment_ChatList extends BaseFragment
{

	public static Fragment_ChatList instance = null;

	public static Fragment_ChatList getInstance()
	{
		return instance;
	}

	View view;
	ListView listview;
	LinearLayout linearLayout_notdata = null;
	ChatListAdapter adapter = null;
	ArrayList<ChatRoomVo> roomarray = null;
	int mposition = 0;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		try
		{
			if (view == null)
			{
				view = inflater.inflate(R.layout.chatlist, container, false);
				instance = this;
				initialize();
				SetView();
				// getdata();
				// m_get_member_list.go?userId="test"
			}
			else
			{
				ViewGroup parent = (ViewGroup) view.getParent();
				if (parent != null) parent.removeView(view);
			}
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
		return view;
	}

	private void initialize()
	{
		try
		{
			// roomarray = new ArrayList<ChatRoomVo>(getMyapp().room.values());
			listview = (ListView) view.findViewById(R.id.listview);
			linearLayout_notdata = (LinearLayout) view
					.findViewById(R.id.linearLayout_notdata);

			listview.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					try
					{

						mposition = position;

						Intent intent = new Intent(getActivity(),
								Activity_Chat.class);
						
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						ChatVo chatvo = new ChatVo();
						chatvo.chatRoomOwner = SharedManager.getInstance()
								.getString(getActivity(), BaseGlobal.User_Seq); // 내seq
						String myname = SharedManager.getInstance().getString(
								getActivity(), BaseGlobal.User_name); // 내아이디
						chatvo.chatRoomSeq = String.valueOf(roomarray.get(
								position).getRoom_Seq()); // 상대seq
						String[] members = roomarray.get(position)
								.getRoom_Member();
						chatvo.chatRoomMember = members;
						chatvo.chatRoomOwnerName = myname;
						chatvo.chatRoomRevName = roomarray.get(position)
								.getRoom_Title();
						chatvo.chatRoomType = roomarray.get(position)
								.getRoom_Type(); // 싱글
						chatvo.chatRoomMemberName = roomarray.get(position)
								.getRoom_MemberName();
						// chatvo.chatRoomName =
						// roomarray.get(position).getRoom_Seq(); // 상대이름
						intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); // 방정보
						// intent.putExtra(ChatGlobal.ChatRoomUser,
						// mSelectUser); //상대정보

						startActivity(intent);
					}
					catch (Exception e)
					{
						// TODO: handle exception
					}
				}
			});

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

	}

	public void UpdateView()
	{
		if (adapter != null)
		{
			adapter.UpdateAdpeter();
		}
	}

	public void SetView()
	{
		try
		{
			roomarray = new ArrayList<ChatRoomVo>(getMyapp().room.values());
			Collections.sort(roomarray,
					new com.br.chat.vo.ChatRoomVo.NameDescCompare());
			adapter = new ChatListAdapter(getActivity(), roomarray);
			listview.setAdapter(adapter);
			// adapter.notifyDataSetChanged();
			adapter.UpdateAdpeter();
			listview.setSelection(mposition);
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	public OnScrollListener onscroll = new OnScrollListener()
	{
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount)
		{
			mposition = firstVisibleItem;

		}
	};

	@Override
	public void onResume()
	{
		super.onResume();
		SetView();
		// UpdateView();
	}

	@Override
	public void onDestroy()
	{
		RecycleUtils.recursiveRecycle(view);
		System.gc();
		super.onDestroy();
	}

	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2)
	{
	}

	@Override
	public void Onback()
	{
	}

}
