package com.br.chat.listview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.view.CircleImageView1;
import com.br.chat.vo.MemberVo;
import com.chattingmodule.R;

public class ExampleContactAdapter extends ContactListAdapter{

	public ExampleContactAdapter(Context _context, int _resource,
			List<ContactItemInterface> _items) {
		super(_context, _resource, _items);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*ViewGroup parentView;
		
		ContactItemInterface item = getItem(position);
		
		//Log.i("ContactListAdapter", "item: " + item.getItemForIndex());
		
		if (convertView == null) {
	    	parentView = new LinearLayout(getContext()); // Assumption: the resource parent id is a linear layout
	    	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	    	LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
	    	vi.inflate(resource, parentView, true);
	    } else {
	    	parentView = (LinearLayout) convertView;
	    }
	    
	    // for the very first section item, we will draw a section on top
		showSectionViewIfFirstItem(parentView, item, position);
	    
		// set row items here
		populateDataForRow(parentView, item, position);*/
		
		final ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.line = (View)convertView.findViewById(R.id.adapterContactItem_view_line);
			viewHolder.TextViewHeader = (TextView)convertView.findViewById(R.id.adapterContactItem_sectionTextView);
			viewHolder.TextViewName = (TextView)convertView.findViewById(R.id.nickNameView);
			//viewHolder.TextViewPhone = (TextView)convertView.findViewById(R.id.fullNameView);
			viewHolder.contact_ImageView_face = (CircleImageView1)convertView.findViewById(R.id.contact_ImageView_face);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(position==0){
			viewHolder.line.setVisibility(View.GONE);
		}else{
			viewHolder.line.setVisibility(View.VISIBLE);
		}
		
		ContactItemInterface item = getItem(position);
		
		// if in search mode then dun show the section header
		if(inSearchMode){
			viewHolder.TextViewHeader.setVisibility(View.GONE);
	    }
	    else
	    {
		    // if first item then show the header
	    	
		    if(indexer.isFirstItemInSection(position)){
		    	
		    	String sectionTitle = indexer.getSectionTitle(item.getItemForIndex());
		    	viewHolder.TextViewHeader.setText(sectionTitle);
		    	viewHolder.TextViewHeader.setVisibility(View.VISIBLE);
		    	
		    }
		    else
		    	viewHolder.TextViewHeader.setVisibility(View.GONE);
	    }
	    
		viewHolder.TextViewName.setText(item.getItemForIndex());
		String phone = "";
		try {
			/*if(!TextUtils.isEmpty(item.getPhoneNumber()))
				phone = Utils.phoneFormat(item.getPhoneNumber());*/
		} catch (Exception e) {
			phone = item.getPhoneNumber();
			// TODO: handle exception
		}
		//viewHolder.TextViewPhone.setText(phone);
		imageLoader.displayImage(ChatGlobal.getMemberFaceThumURL(String.valueOf(ChatGlobal.getMemberFaceThumURL(item.getPhoneNumber()))),viewHolder.contact_ImageView_face.getFaceView(),imageLoaderOption, animationListener);
		/*ChattingApplication.getInstance().getImageLoader().get(ChatGlobal.getMemberFaceThumURL(item.getPhoneNumber()), new ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				viewHolder.contact_ImageView_face.setImageBitmap(response.getBitmap());
			}
		});*/
		
		/*if(item instanceof VHUser){
			VHUser contactItem = (VHUser)item;
			String phone = "";
			try {
				if(!TextUtils.isEmpty(phone))
					phone = Utils.phoneFormat(contactItem.phone);
			} catch (Exception e) {
				phone = contactItem.phone;
				// TODO: handle exception
			}
			viewHolder.TextViewPhone.setText(phone);
		}*/
		
		return convertView;
		
	}
	
	private static class ViewHolder{
		View line;
		TextView TextViewHeader;
		TextView TextViewName;
		//TextView TextViewPhone;
		CircleImageView1 contact_ImageView_face;
	}
	
	// override this for custom drawing
	public void populateDataForRow(View parentView, ContactItemInterface item , int position){
		// default just draw the item only
		
		View line = parentView.findViewById(R.id.adapterContactItem_view_line);
		if(position==0)line.setVisibility(View.GONE);
		else			line.setVisibility(View.VISIBLE);
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		//TextView fullNameView = (TextView)infoView.findViewById(R.id.fullNameView);
		TextView nicknameView = (TextView)infoView.findViewById(R.id.nickNameView);
		nicknameView.setInputType(android.text.InputType.TYPE_CLASS_PHONE);

		nicknameView.setText(item.getItemForIndex());
		
		if(item instanceof MemberVo){
			MemberVo contactItem = (MemberVo)item;
			/*String phone = Utils.phoneFormat(contactItem.phone);*/
			String phone = String.valueOf(contactItem.getSeq());
			//fullNameView.setText(phone);
			
		}
		
	}

	
	
}
