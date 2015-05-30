package com.br.chat.util;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class ContactUtil {
	
	String TAG = "ContactUtil";
	Activity mContext;
	HashMap<String, String>phoneMap = new HashMap<String, String>();
	public ContactUtil(Activity context) {
		mContext = context;
	}
	
	
	public JSONArray getContactList() {
		JSONArray jsonarray = new JSONArray();
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID, // 연락처 ID -> 사진 정보 가져오는데 사용
				ContactsContract.CommonDataKinds.Phone.NUMBER,        // 연락처
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME }; // 연락처 이름.

		String[] selectionArgs = null;

		String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		Cursor contactCursor = mContext.managedQuery(uri, projection, null,
				selectionArgs, sortOrder);


		if (contactCursor.moveToFirst()) {
			do {
				//if(isNumberTypeMobile(Integer.valueOf(contactCursor.getString(0)))){
					try {
						String phonenumber = contactCursor.getString(1).replaceAll("-","").replaceAll("\\s", "");
						if(phonenumber.startsWith("+")) 
							{
							phonenumber = phonenumber.substring(3);
							if(!phonenumber.startsWith("0")) phonenumber = "0" + phonenumber;
							}						
						if(phoneMap.get(phonenumber) == null){
							//jsonobj.put("contact", phonenumber);	
							jsonarray.put(phonenumber);
							phoneMap.put(phonenumber, phonenumber);
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				
			//	}	
			} while (contactCursor.moveToNext());
		}

		return jsonarray;

	}

	

	public JSONArray LoadContacts() {
		JSONArray jsonarray = new JSONArray();
	//	JSONObject jsonobj = new JSONObject();
		
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER };

		Cursor cursor = mContext.managedQuery(uri, projection, null, null, null);
		//ContactItem item = null;
		if(cursor != null){
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					if (cursor.getInt(2) == 1){
						JSONObject jsonobj = LoadPhoneNumbers(cursor.getString(0));
						if(jsonobj != null ){
							if(jsonarray.length() > 246)
								Log.e("", "");
							jsonarray.put( jsonobj);	
						}
						
					}
						// LoadPhoneNumbers(cursor.getString(0),jsonobj);
				}
			}
		}else{ 
			Log.e("", "");
		}
	
		return jsonarray;
	}

	private JSONObject LoadPhoneNumbers(String id) {
		
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.TYPE,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
				+ "=?";
		Cursor cursor = mContext.managedQuery(uri, projection, selection,
				new String[] { id }, null);
		if(cursor != null){
			while (cursor.moveToNext()) {
				try {
					if(isNumberTypeMobile(Integer.valueOf(cursor.getString(0)))){
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("contact", cursor.getString(1).replace("-", ""));
						return jsonobj;
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.w(TAG, "Number Type ==> " + cursor.getString(0));
				Log.w(TAG, "Number ==> " + cursor.getString(1));
			}
		}
	
		return null;
	}
	
	private boolean isNumberTypeMobile(int type){
		if(type == Phone.TYPE_MOBILE){
			return true;
		}
		return false;
	}

	private String getNumberType(int type) {
		String numberType = null;
		switch (type) {
		case Phone.TYPE_MOBILE:
		    numberType = "mobile";
		    break;
		case Phone.TYPE_HOME:
		    numberType = "home";
		    break;
		}
		return numberType;
		}
}
