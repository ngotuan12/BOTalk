package com.br.chat.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.br.chat.ChattingApplication;
import com.br.chat.adapter.ChatType;
import com.br.chat.view.ImageLoaderInterface;
import com.br.chat.vo.MessageVo;
import com.brainyxlib.image.BrImageUtilManager;
import com.chattingmodule.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Activity_GridViewer extends Activity implements ImageLoaderInterface ,GridView.OnItemClickListener{

	public static ArrayList<MessageVo> messageArray ;
	public static void setMessage(ArrayList<MessageVo> _messageArray){
		messageArray = _messageArray;
	}
	//ArrayList<MessageVo>imagearray = new ArrayList<MessageVo>();
	GridView mGvImageList;
	ImageAdapter mListAdapter;
	ImageView no_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gridviewer);
		
		
		init();
	}
	
	private void init(){
		mGvImageList = (GridView) findViewById(R.id.gvImageList);
		mGvImageList.setOnItemClickListener(this);
		
	/*	for(int i  = 0 ; i< messageArray.size();i++){
			if(messageArray.get(i).getMsgtype() == ChatType.file){
				imagearray.add(messageArray.get(i));
			}
		}*/
		mListAdapter = new ImageAdapter(Activity_GridViewer.this, R.layout.image_cell,messageArray);
		mGvImageList.setAdapter(mListAdapter);
		mListAdapter.notifyDataSetChanged();
		no_image = (ImageView)findViewById(R.id.no_image);
		findViewById(R.id.activityChat_ImageButton_close).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		if(messageArray.size() == 0){
			no_image.setVisibility(View.VISIBLE);
		}else{
			no_image.setVisibility(View.GONE);
		}
	}
	
	private class ImageAdapter extends BaseAdapter {
		static final int VISIBLE = 0x00000000;
		static final int INVISIBLE = 0x00000004;
		private Context mContext;
		private int mCellLayout;
		private LayoutInflater mLiInflater;
		private ArrayList<MessageVo> mThumbImageInfoList;
		private SparseArray<WeakReference<View>> viewArray;


		@SuppressWarnings("unchecked")
		public ImageAdapter(Context c, int cellLayout,
				ArrayList<MessageVo> thumbImageInfoList) {
			mContext = c;
			mCellLayout = cellLayout;
			mThumbImageInfoList = thumbImageInfoList;
			this.viewArray = new SparseArray<WeakReference<View>>(thumbImageInfoList.size());
			mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return mThumbImageInfoList.size();
		}

		public Object getItem(int position) {
			return mThumbImageInfoList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}
		public void update(){
			viewArray.clear();
			notifyDataSetChanged();
		}

		@SuppressWarnings("unchecked")
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(viewArray != null && viewArray.get(position) != null) {
				convertView = viewArray.get(position).get();
				if(convertView != null){
					View view = (LinearLayout)convertView.getTag();
					return convertView;
				}
			}
			try{
				convertView = mLiInflater.inflate(mCellLayout, parent, false);

				final ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
				LinearLayout image_selected = (LinearLayout)convertView.findViewById(R.id.image_selected);
				final ProgressBar bar = (ProgressBar)convertView.findViewById(R.id.progressbar);
				image_selected.setVisibility(View.GONE);
					try {
						ImageLoader.getInstance().displayImage(mThumbImageInfoList.get(position).getMsg(),
								ivImage, ChattingApplication.getInstance().options, new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								bar.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
								bar.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
								bar.setVisibility(View.GONE);
								int rotate = BrImageUtilManager.getInstance().GetExifOrientation(arg0);
								ivImage.setImageBitmap(BrImageUtilManager.getInstance().rotate(arg2,rotate));
								ivImage.setScaleType(ScaleType.CENTER_CROP);
							}

							@Override
							public void onLoadingCancelled(String arg0, View arg1) {
								bar.setVisibility(View.GONE);
							}
						});


						ivImage.setVisibility(VISIBLE);
						setProgressBarIndeterminateVisibility(false);
						ivImage.setTag(position);
					} catch (Exception e) {
						//WriteFileLog.writeException(e);
						setProgressBarIndeterminateVisibility(false);
					}
					
					convertView.setTag(image_selected);
			}catch(Exception e){
				//WriteFileLog.writeException(e);
			}finally{
				viewArray.put(position, new WeakReference<View>(convertView));
			}
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			Intent intent = new Intent(this,ActivityImageViewer.class);
			intent.putExtra(ActivityImageViewer.TITLE, "이미지보기");
			intent.putExtra(ActivityImageViewer.POSITION, position);
			//intent.putExtra(ActivityImageViewer.PATH, imagearray.get(position).getMsg());
			ActivityImageViewer.setMessage(messageArray);
			startActivity(intent);
			finish();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
