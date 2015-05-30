package com.brainyxlib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.People;

public class BrIntentManager {

	public final String TAG = "DateManager";
	private static BrIntentManager instance = null;

	public final String ACTION_SETTINGS = "android.settings.SETTINGS";
	public final String ACTION_APN_SETTINGS = "android.settings.APN_SETTINGS";
	public final String ACTION_LOCATION_SOURCE_SETTINGS = "android.settings.LOCATION_SOURCE_SETTINGS";
	public final String ACTION_WIRELESS_SETTINGS = "android.settings.WIRELESS_SETTINGS";
	public final String ACTION_AIRPLANE_MODE_SETTINGS = "android.settings.AIRPLANE_MODE_SETTINGS";
	public final String ACTION_ACCESSIBILITY_SETTINGS = "android.settings.ACCESSIBILITY_SETTINGS";
	public final String ACTION_SECURITY_SETTINGS = "android.settings.SECURITY_SETTINGS";
	public final String ACTION_PRIVACY_SETTINGS = "android.settings.PRIVACY_SETTINGS";
	public final String ACTION_WIFI_SETTINGS = "android.settings.WIFI_SETTINGS";
	public final String ACTION_WIFI_IP_SETTINGS = "android.settings.WIFI_IP_SETTINGS";
	public final String ACTION_BLUETOOTH_SETTINGS = "android.settings.BLUETOOTH_SETTINGS";
	public final String ACTION_DATE_SETTINGS = "android.settings.DATE_SETTINGS";
	public final String ACTION_SOUND_SETTINGS = "android.settings.SOUND_SETTINGS";
	public final String ACTION_DISPLAY_SETTINGS = "android.settings.DISPLAY_SETTINGS";
	public final String ACTION_LOCALE_SETTINGS = "android.settings.LOCALE_SETTINGS";
	public final String ACTION_INPUT_METHOD_SETTINGS = "android.settings.INPUT_METHOD_SETTINGS";
	public final String ACTION_USER_DICTIONARY_SETTINGS = "android.settings.USER_DICTIONARY_SETTINGS";
	public final String ACTION_APPLICATION_SETTINGS = "android.settings.APPLICATION_SETTINGS";
	public final String ACTION_APPLICATION_DEVELOPMENT_SETTINGS = "android.settings.APPLICATION_DEVELOPMENT_SETTINGS";
	public final String ACTION_QUICK_LAUNCH_SETTINGS = "android.settings.QUICK_LAUNCH_SETTINGS";
	public final String ACTION_MANAGE_APPLICATIONS_SETTINGS = "android.settings.MANAGE_APPLICATIONS_SETTINGS";
	public final String ACTION_SYNC_SETTINGS = "android.settings.SYNC_SETTINGS";
	public final String ACTION_ADD_ACCOUNT = "android.settings.ADD_ACCOUNT_SETTINGS";
	public final String ACTION_NETWORK_OPERATOR_SETTINGS = "android.settings.NETWORK_OPERATOR_SETTINGS";
	public final String ACTION_DATA_ROAMING_SETTINGS = "android.settings.DATA_ROAMING_SETTINGS";
	public final String ACTION_INTERNAL_STORAGE_SETTINGS = "android.settings.INTERNAL_STORAGE_SETTINGS";
	public final String ACTION_MEMORY_CARD_SETTINGS = "android.settings.MEMORY_CARD_SETTINGS";
	public final String ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
	public final String ACTION_DEVICE_INFO_SETTINGS = "android.settings.DEVICE_INFO_SETTINGS";
	public final String EXTRA_AUTHORITIES = "authorities";
	public final String AUTHORITY = "settings";

	public synchronized static BrIntentManager getInstance() {
		if (instance == null) {
			synchronized (BrIntentManager.class) {
				if (instance == null) {
					instance = new BrIntentManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 셋팅이동
	 * @param context
	 * @param setting
	 * @throws Exception
	 */
	public void IntentSetting(Context context, String setting)
	{
		try {
			Intent intent = new Intent(setting);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전화걸기 다이얼
	 * 
	 * @param context
	 * @param phonenum
	 */
	public void IntentDail(Context context, String phonenum) {
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ phonenum));
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전화걸기
	 * 
	 * @param context
	 * @param phonenum
	 */
	public void IntentCall(Context context, String phonenum) {
		try {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phonenum));
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 웹페이지 열기
	 * 
	 * @param context
	 * @param url
	 */
	public void IntentWebPage(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}

	/**
	 * sms보내기
	 * 
	 * @param context
	 * @param msg
	 */
	public void IntentSmS(Context context, String number) {

		Uri uri = Uri.parse("smsto:" + number);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", "");
		context.startActivity(it);

	}

	/**
	 * 
	 * @param context
	 * @param msg
	 */
	public void IntentSMSMMS(Context context, String msg) {

		Intent it = new Intent(Intent.ACTION_VIEW);
		it.putExtra("sms_body", msg);
		it.setType("vnd.android-dir/mms-sms");
		context.startActivity(it);
	}

	/**
	 * 이메일보내기
	 * 
	 * @param context
	 * @param email
	 */
	public void IntentEmail(Context context, String email) {
		Uri uri = Uri.parse("mailto:" + email);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		context.startActivity(it);
	}

	/**
	 * 전화번호부 저장
	 * 
	 * @param context
	 * @param phone
	 * @param name
	 * @param company
	 */
	public void IntentContactCall(Context context, String phone, String name,
			String company) {

		/*
		 * Intent contactIntent = new
		 * Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT);
		 *  contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,
		 * phone);
		 *  contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
		 * ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		 *  contactIntent.setData(Uri.fromParts("tel", phone, null));
		 */

		Intent intent = new Intent(Intent.ACTION_INSERT, People.CONTENT_URI);
		intent.putExtra(Contacts.Intents.Insert.NAME, name);
		intent.putExtra(Contacts.Intents.Insert.PHONE, phone);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(intent);

		/*
		 * Intent intent = new Intent(Intent.ACTION_INSERT); ComponentName
		 * insertComponentName = new ComponentName( "com.android.contacts",
		 * "com.android.contacts.ui.EditContactActivity");
		 * intent.setComponent(insertComponentName); Bundle bundle = new
		 * Bundle(); bundle.putString(Intents.Insert.PHONE, phone);
		 * //bundle.putString(Intents.Insert.PHONE_TYPE, "전화번호타입1");
		 * //bundle.putString(Intents.Insert.SECONDARY_PHONE, "000-000-0000");
		 * //bundle.putString(Intents.Insert.SECONDARY_PHONE_TYPE, "전화번호타입2");
		 * bundle.putString(Intents.Insert.NAME, name);
		 * bundle.putString(Intents.Insert.COMPANY, company);
		 * //bundle.putString(Intents.Insert.JOB_TITLE, "부서이름");
		 * intent.putExtras(bundle); context.startActivity(intent);
		 */
	}

}
