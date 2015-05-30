package com.bxm.boardtalks.boards;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import kr.co.boardtalks.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter {
	public ArrayList<Post> posts = new ArrayList<Post>();
	Context ctx;
	
	public PostAdapter(ArrayList<Post> postData, Context context){
		this.posts = postData;
		this.ctx = context;
	}

	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public Object getItem(int position) {
		return posts.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Post p = posts.get(position);
		if(convertView == null) convertView = LayoutInflater.from(ctx).inflate(R.layout.post_row, parent, false);
		((TextView)convertView.findViewById(R.id.tv_content)).setText(p.getContent());
		((TextView)convertView.findViewById(R.id.tv_author)).setText(p.getAuthor());
		((TextView)convertView.findViewById(R.id.tv_shared_count)).setText(57 + position + "");
		((TextView)convertView.findViewById(R.id.tv_time_posted)).setText(p.getDateTimePosted());
		ImageLoader.getInstance().displayImage(p.getAvatarUrl(), (ImageView)convertView.findViewById(R.id.imgViewAvatar));
		
		return convertView;
		
	}

}
