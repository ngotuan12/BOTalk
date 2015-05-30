package com.br.chat.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.br.chat.activity.ActivityFriendPicker;
import com.br.chat.activity.ActivityImageViewer;
import com.br.chat.activity.Activity_Chat;
import com.br.chat.activity.Activity_GridViewer;
import com.br.chat.view.ImageLoaderInterface;
import com.br.chat.vo.MessageVo;
import com.br.chat.vo.UserVo;
import com.chattingmodule.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AdapterChatDrawer extends BaseAdapter implements ImageLoaderInterface {
	private final String TAG = "AdapterDrawer";
	private Context mContext;
	private ArrayList<Object> mArrObject;
	private int sizeCircle = 0;
	private ArrayList<MessageVo> mArrChatUI;
	public AdapterChatDrawer(Context context, ArrayList<Object> arrObject,ArrayList<MessageVo> ArrChatUI) {
		mContext = context;
		mArrObject = arrObject;
		sizeCircle  = context.getResources().getDimensionPixelSize(R.dimen.list_item_user_image2);
		mArrChatUI = ArrChatUI;
	}
    
	private static final int TYPE_SECTION_Chat_Top = 0;
    private static final int TYPE_SECTION_Chat_User = 1;
    private static final int TYPE_MAX_COUNT = 2;
   
	@Override
    public int getItemViewType(int position) {
		if(mArrObject.get(position)!=null && mArrObject.get(position) instanceof UserVo){
			return TYPE_SECTION_Chat_User;
		}else{
			return TYPE_SECTION_Chat_Top;
		}
    }
	
	@Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if(TYPE_SECTION_Chat_Top==type){
			ViewHolderChatTop viewHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_drawer_chat_top_item, parent, false);
				viewHolder = new ViewHolderChatTop();
				
				viewHolder.viewer = (LinearLayout)convertView.findViewById(R.id.total_imgviewer);
				viewHolder.addmember = (LinearLayout)convertView.findViewById(R.id.addmember);
				
				convertView.setTag(viewHolder);
				
			}else{
				viewHolder = (ViewHolderChatTop) convertView.getTag();
			}
			
			viewHolder.viewer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,Activity_GridViewer.class);
					intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
					ArrayList<MessageVo>messageArray = new ArrayList<MessageVo>();
					for(int i  = 0 ; i< mArrChatUI.size();i++){
						if(mArrChatUI.get(i).getMsgtype() == ChatType.file){
							messageArray.add( mArrChatUI.get(i));
						}
					}
					Activity_GridViewer.setMessage(messageArray);
					mContext.startActivity(intent);
				}
			});
			viewHolder.addmember.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Activity_Chat.getInstance() != null ){
						Activity_Chat.getInstance().AddMember();
					}
				}
			});
			
		}else{
			final UserVo vhUser = (UserVo)mArrObject.get(position);
			ViewHolderChatUser viewHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_drawer_chat_user_item, parent, false);
				viewHolder = new ViewHolderChatUser();
				viewHolder.face = (ImageView)convertView.findViewById(R.id.adapterDrawerChatUserItem_ImageView_face);
				viewHolder.title = (TextView)convertView.findViewById(R.id.adapterDrawerChatUserItem_TextView_text);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolderChatUser) convertView.getTag();
			}

			viewHolder.title.setText("" + vhUser.UserId);
			imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(vhUser.UserSeq), viewHolder.face,imageLoaderOption);
			/*imageLoader.displayImage(vhUser.UserPhone,
					viewHolder.face,
					roundImageLoaderOption);*/
		}
		
		
		return convertView;
	}

	private static class ViewHolderChatTop{
		LinearLayout viewer;
		LinearLayout addmember;
	}
	
	private static class ViewHolderChatUser{
		View line;
		ImageView face;
		TextView title;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrObject.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}