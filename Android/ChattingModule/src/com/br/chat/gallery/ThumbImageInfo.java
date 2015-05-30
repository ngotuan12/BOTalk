package com.br.chat.gallery;

import android.net.Uri;

public class ThumbImageInfo {
	private String id;
	private Uri data;
	private boolean checkedState;
	private String bucket_name;
	private String bucket_id;
	
	public String getBucket_name() {
		return bucket_name;
	}

	public void setBucket_name(String bucket_name) {
		this.bucket_name = bucket_name;
	}

	public String getBucket_id() {
		return bucket_id;
	}

	public void setBucket_id(String bucket_id) {
		this.bucket_id = bucket_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Uri getData() {
		return data;
	}

	public void setData(Uri data) {
		this.data = data;
	}

	public boolean getCheckedState() {
		return checkedState;
	}

	public void setCheckedState(boolean checkedState) {
		this.checkedState = checkedState;
	}
}
