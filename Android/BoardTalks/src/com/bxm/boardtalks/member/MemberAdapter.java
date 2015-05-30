package com.bxm.boardtalks.member;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.br.chat.view.CircleImageView1;
import com.br.chat.view.ImageLoaderInterface;
import com.br.chat.vo.MemberVo;
import kr.co.boardtalks.R;
import com.woozzu.android.util.StringMatcher;

public class MemberAdapter extends ArrayAdapter<MemberVo> implements SectionIndexer,ImageLoaderInterface {
	/*public class AListAdapter extends ArrayAdapter<MemberVo> implements SectionIndexer,ImageLoaderInterface {*/
		public Context mContext;
		ArrayList<MemberVo> arraylist;
		//private SparseArray<WeakReference<View>> viewArray;
		
		public MemberAdapter(Context context, ArrayList<MemberVo> arraylist) {
			super(context, R.layout.item_memberlist, arraylist);
			this.mContext = context;
			this.arraylist = arraylist;
			//this.viewArray = new SparseArray<WeakReference<View>>(arraylist.size());
		}

		public class HeaderViewHolder{
			TextView headertextview;
		}
		
		public void UpdateAdpeter(){
		//	viewArray.clear();
			this.notifyDataSetChanged();
		}
		
	/*	@Override
		public View getHeaderView(int position, View convertView,
				ViewGroup parent) {
			HeaderViewHolder holder;
			 if (convertView == null) {
					LayoutInflater mInflater = LayoutInflater.from(mContext);
		            convertView = mInflater.inflate(R.layout.member_header, null);
		            holder = new HeaderViewHolder();
		            convertView.setTag(holder);
		        } else {
		            holder = (HeaderViewHolder) convertView.getTag();
		        }
			 
			 try {
				 holder.headertextview = (TextView)convertView.findViewById(R.id.listHeaderChat_TextView_header);
				if(getItemViewType(position) == 0){
					holder.headertextview.setText("내프로필");
				}else{
					char sse = SoundSearcher.getInitialSound(arraylist.get(position).getUsername().charAt(0));
					Character cr = new Character(sse);            
					holder.headertextview.setText(cr.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;
		}*/

		/*@Override
		public long getHeaderId(int position) {
			try {
				char sse = SoundSearcher.getInitialSound(arraylist.get(position).getUsername().charAt(0));
				return sse;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return 0;
		}
		*/
	
		
		@Override
		public int getItemViewType(int position) {
			MemberVo tmpItem = arraylist.get(position);
			return tmpItem.getUserSection();
		};

		@Override
		public int getViewTypeCount() {
			return 1;
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
			//final ViewHolder holder;
			MemberVo data = arraylist.get(position);
			LinearLayout member_row = null;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_memberlist, parent, false);
				 
			}
			 member_row = ViewHolder.get(convertView, R.id.member_row);
			/*LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.item_memberlist,null);
			holder = new ViewHolder();
			convertView.setTag(holder);*/
			/*if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(R.layout.item_memberlist,null);
				holder = new ViewHolder();
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}*/
			if (data != null) {
				TextView textview_name = (TextView) member_row.findViewById(R.id.textviewname);
				TextView textview_sections = (TextView) member_row.findViewById(R.id.sections);
				TextView textview_stat = (TextView) member_row.findViewById(R.id.stat_msg);
				CircleImageView1 faceview = (CircleImageView1)member_row.findViewById(R.id.faceimgview);
				View sectionsline1 = (View)member_row.findViewById(R.id.sections_line1); 
				View sectionsline2 = (View)member_row.findViewById(R.id.sections_line2); 
				if(getItemViewType(position) == 0){
					textview_sections.setVisibility(View.VISIBLE);
					sectionsline1.setVisibility(View.VISIBLE);
					sectionsline2.setVisibility(View.VISIBLE);
					textview_sections.setText("내 프로필");
					textview_name.setText(data.getUsername());
					/*SharedManager.getInstance().setString(mContext,BaseGlobal.User_id ,String.valueOf(data.getSeq()));*/
				}else if(getItemViewType(position) == 2){
					if(data.isHeader()){
						textview_sections.setVisibility(View.VISIBLE);
						sectionsline1.setVisibility(View.VISIBLE);
						sectionsline2.setVisibility(View.VISIBLE);
						textview_sections.setText("친구");
						textview_name.setText(data.getUsername());	
					}else{
						textview_sections.setVisibility(View.GONE);
						sectionsline1.setVisibility(View.GONE);
						sectionsline2.setVisibility(View.GONE);
						textview_name.setText(data.getUsername());
					}
				}else if(getItemViewType(position) == 1){
					if(data.isHeader()){
						textview_sections.setVisibility(View.VISIBLE);
						sectionsline1.setVisibility(View.VISIBLE);
						sectionsline2.setVisibility(View.VISIBLE);
						textview_sections.setText("즐겨찾기");
						textview_name.setText(data.getUsername());	
					}else{
						textview_sections.setVisibility(View.GONE);
						sectionsline1.setVisibility(View.GONE);
						sectionsline2.setVisibility(View.GONE);
						textview_name.setText(data.getUsername());
					}
				}
				if(data.getUserStmsg().length() == 0){
					textview_stat.setVisibility(View.GONE);
				}else{
					textview_stat.setVisibility(View.VISIBLE);
					textview_stat.setText(data.getUserStmsg());	
				}
				//ImageLoader.getInstance().displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(data.getSeq())), holder.faceview.getFaceView());
				imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(data.getSeq())),faceview.getFaceView(),imageLoaderOption, animationListener);
			/*	if(data.getUserPhoto().length() == 0){
					//Log.e("", "사진없음 == " + data.getUsername());
				ImageLoader.getInstance().displayImage(data.getUserPhoto(), holder.faceview.getFaceView());
				}else{
					ImageLoader.getInstance().displayImage(data.getUserPhoto(), holder.faceview.getFaceView());
					
					ChattingApplication.getInstance().getImageLoader().get(data.getUserPhoto(), new ImageListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
						
						@Override
						public void onResponse(ImageContainer response, boolean isImmediate) {
								holder.faceview.setImageBitmap(response.getBitmap());	
						}
					});		
					//Log.e("", "사진있음 == " + data.getUsername());
				}*/
				
				
			/*	if(data.getUserPhoto().trim().length() == 0){
					ChattingApplication.getInstance().getImageLoader().get(data.getUserPhoto(), new ImageListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
						
						@Override
						public void onResponse(ImageContainer response, boolean isImmediate) {
							holder.faceview.setBackgroundResource(R.drawable.ic_action_user_gray);
						}
					});	
				}else{
					ChattingApplication.getInstance().getImageLoader().get(data.getUserPhoto(), new ImageListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
						
						@Override
						public void onResponse(ImageContainer response, boolean isImmediate) {
								holder.faceview.setImageBitmap(response.getBitmap());	
						}
					});	
				}*/
					
			}
		//	viewArray.put(position, new WeakReference<View>(convertView));
			//return viewArray.get(position).get();
			return convertView;
		}
		
		public static class ViewHolder {

			TextView textview_name;
			TextView textview_sections;
			TextView textview_stat;
			CircleImageView1 faceview;
			View sectionsline ;
			@SuppressWarnings("unused")
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
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}

	/*	@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist.get(position);
		}*/

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
///////
		public  String OTHER = "#";
		/*public  String[] mSections2 = { "ㄱ", "ㄴ", "ㄷ" , "ㄹ", "ㅁ" ,"ㅂ", "ㅅ" , "ㅇ" , "ㅈ", 
			"ㅊ", "ㅋ", "ㅌ", "ㅍ" , "ㅎ" , "A", "F" ,"J" ,"O" ,"S" , 
			"Z" ,OTHER } ;*/
		private String mSections = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎAFJOSZ#";
		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}

		@Override
		public int getPositionForSection(int section) {
			// If there is no item for current section, previous section will be selected
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(String.valueOf(getItem(j).getUsername().charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(getItem(j).getUsername().charAt(0)), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

}

