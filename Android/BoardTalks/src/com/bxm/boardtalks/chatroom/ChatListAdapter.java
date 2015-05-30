package com.bxm.boardtalks.chatroom;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.view.ChatRoomFaceView;
import com.br.chat.vo.ChatRoomVo;
import com.br.chat.vo.MemberVo;
import com.brainyxlib.BrDateManager;
import kr.co.boardtalks.R;
import com.bxm.boardtalks.util.WriteFileLog;

public class ChatListAdapter extends ArrayAdapter<ChatRoomVo> {
	/*public class AListAdapter extends ArrayAdapter<ChatRoomVo> {*/
		public Context mContext;
		ArrayList<ChatRoomVo> arraylist;
		
		//private SparseArray<WeakReference<View>> viewArray;
		
		public ChatListAdapter(Context context, ArrayList<ChatRoomVo> arraylist) {
			super(context, R.layout.room_item_row, arraylist);
			this.mContext = context;
			this.arraylist = arraylist;
			//this.viewArray = new SparseArray<WeakReference<View>>();
		}

		public static class ViewHolder {
			ChatRoomFaceView roomfaceview;
			TextView room_item_title;
			TextView room_item_msg;
			TextView room_item_date;
			TextView room_item_newmsg;
			ImageView room_item_sizeimg;
			TextView room_item_size;
			int position;
			/*@SuppressWarnings("unused")
			public static <T extends View >T get(View convertview, int id){
				SparseArray<View>	viewArray = (SparseArray<View>)convertview.getTag();
				if(viewArray == null){
					viewArray = new SparseArray<View>();
				}
				View child = viewArray.get(id);
				if(child == null){
					child = convertview.findViewById(id);
					viewArray.put(id, child);
					convertview.setTag(viewArray);
				}
				
				return (T) child;
			}*/
		}
		public void UpdateAdpeter(){
			//viewArray.clear();
			this.notifyDataSetChanged();
		}
		

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public int getCount() {
			return arraylist.size();
		}
		
		@Override
		public ChatRoomVo getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist.get(position);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			
	/*		if(viewArray != null && viewArray.get(position) != null) {
				convertView = viewArray.get(position).get();
					if(convertView != null){
						return convertView;
					}
			}
			*/
			ViewHolder holder;
			ChatRoomVo data = getItem(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.room_item_row, parent, false);
				holder = new ViewHolder();
				holder.room_item_title = (TextView) convertView.findViewById(R.id.room_item_title);
				holder.room_item_msg = (TextView) convertView.findViewById(R.id.room_item_msg);
				holder.room_item_date = (TextView) convertView.findViewById(R.id.room_item_date);
				holder.room_item_newmsg = (TextView) convertView.findViewById(R.id.room_item_newmsg);
				holder.roomfaceview = (ChatRoomFaceView)convertView.findViewById(R.id.roomfaceview);
				holder.room_item_sizeimg = (ImageView)convertView.findViewById(R.id.membersizeimg);
				holder.room_item_size = (TextView)convertView.findViewById(R.id.membersize);
				
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			if (data != null) {
				//	new ImgLoad(position	, holder, data.getRoom_Member()).execute();
					holder.roomfaceview.setFaceView(data.getRoom_Member());
				if(data.getRoom_Type() == ChatGlobal.chatTypeSingle){
					holder.room_item_title.setText(data.getRoom_Title());
					holder.room_item_sizeimg.setVisibility(View.GONE);
					holder.room_item_size.setVisibility(View.GONE);
				}else if(data.getRoom_Type() == ChatGlobal.chatTypeGroup){
					
					holder.room_item_title.setText(getRoomTitle(data.getRoom_Type(), data.getRoom_MemberName(),String.valueOf(data.getRoom_Seq())));
					holder.room_item_sizeimg.setVisibility(View.VISIBLE);
					holder.room_item_size.setVisibility(View.VISIBLE);
					holder.room_item_size.setText(String.valueOf(data.getRoom_Member().length));
				}
				
				if(data.getRoom_NewMsg().startsWith("http")){
					holder.room_item_msg.setText("사진");
				}else{
					holder.room_item_msg.setText(data.getRoom_NewMsg());	
				}
				
				holder.room_item_date.setText(BrDateManager.getInstance().CreateDataWithCheck(String.valueOf(data.getRoom_UpdateDate())));
				
				try {
					int newmsg = Integer.valueOf(data.getRoom_MsgCnt());
					if(newmsg > 0){
						holder.room_item_newmsg.setVisibility(View.VISIBLE);
						holder.room_item_newmsg.setText(String.valueOf(newmsg));
					}else{
						holder.room_item_newmsg.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
			
			}
			return convertView;
			//	viewArray.put(position, new WeakReference<View>(convertView));
			//return viewArray.get(position).get();
		}
		
		
		private class ImgLoad extends AsyncTask< integer, Void , Void>{
			
			private int mPos;
			private ViewHolder mHolder;
			private  String[] member;
			
			public ImgLoad(int pos, ViewHolder vh, String[] _member){
				mPos = pos;
				mHolder = vh;
				member = _member;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				if(mHolder.position == mPos){
					//mHolder.roomfaceview.setBackgroundResource(resource_id);
					mHolder.roomfaceview.setFaceView(member);
				}
			}

			@Override
			protected Void doInBackground(integer... params) {
				// TODO Auto-generated method stub
				return null;
			}
			
		}
		

		private String getRoomTitle(int roomtype,String[] member , String roomseq){
			try {
				if(roomtype == ChatGlobal.chatTypeSingle){
					
					return ((MemberVo)ChattingApplication.getInstance().memberMap.get(roomseq)).getUsername();	
				}else if(roomtype == ChatGlobal.chatTypeGroup){
					String members = "";
					for(int i = 0 ; i < member.length;i++){
						if(members.length() == 0){
							members = member[i];	
						}else{
							members = members +"," + member[i];
						}
					}
					return members;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
}
