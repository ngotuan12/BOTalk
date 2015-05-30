package com.brainyxlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class BrUtilManager {

	public dialogclick dialogclick ;
	public static interface dialogclick{
		public void setondialogokclick();
		public void setondialocancelkclick();
	}
	
	public void setOndialogClickCallback(dialogclick callback){
		this.dialogclick = callback;
	}
	
	public static interface setOnItemChoice{
		public void setOnItemClick(int index);
	}
	
	public setOnItemChoice choicecallback;
	public void setOnItemChoiceCallback(setOnItemChoice callback){
		this.choicecallback = callback;
	}
	
	public final String TAG = "BrUtilManager";
	private static BrUtilManager instance = null;
	private boolean mIsBackKeyPressed = false;
	private long mCurrentTimeInMillis = 0;
	private final int BACKEY_TIMEOUT = 2000;
	private final int MSG_TIMER_EXPIRED = 1;
	private boolean isInternet = false;
	public final int NETWORKSTATE = 100;
	public final int WIFISTATE = 200;
	
	public synchronized static BrUtilManager getInstance() {
		if (instance == null) {
			synchronized (BrUtilManager.class) {
				if (instance == null) {
					instance = new BrUtilManager();
				}
			}
		}
		return instance;
	}
	
	
	public  String DownloadHtml(String addr) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);


		StringBuilder html = new StringBuilder();
		try {
			// String contentText2 = java.net.URLEncoder.encode(new
			// String(addr.getBytes("UTF-8")));

			URL url = new URL(addr);
			Log.e("URL", "URL = " + url);
			/*
			 * StrictMode.ThreadPolicy policy = new
			 * StrictMode.ThreadPolicy.Builder().permitAll().build();
			 * StrictMode.setThreadPolicy(policy);
			 */

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					for (;;) {
						String line = br.readLine();
						if (line == null)
							break;
						html.append(line);

						Log.e("html", "html = " + html.toString());
					}
					br.close();
				}
				conn.disconnect();

			}
		} catch (Exception ex) {

		}
				return html.toString();
	}
	
	/**
	 * 토스트 메세지를 띄운다.
	 * @param context
	 * @param msg
	 */
	public void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void StrickModePolicy() {
		int nSDKVersion = Integer.parseInt(Build.VERSION.SDK);
		if (nSDKVersion > 10) // 2.1?�꾨릭
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}
	

	/**
	 * string 데이터 인코딩
	 * @param value
	 * @return
	 */
	public  String StringEncoding(String value){
		String encoding = null;
		try {
			 encoding = java.net.URLEncoder.encode(new String(value.getBytes("UTF-8"))); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoding;
	}
	
	/**
	 * 앱 버젼 가져오기
	 * @param context
	 * @return
	 */
	public String GetVersion(Context context) {
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

			return pinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 키보드 숨기기
	 * @param context
	 * @param p1
	 */
	public void InputKeybordHidden(Context context , EditText p1){
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(p1.getWindowToken(), 0);
	}
	
	/**
	 * 키보드 보이기
	 * @param context
	 * @param p1
	 */
	public void InputKeybordShow(Context context , EditText p1){
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	}
	
	/**
	 * sdk버전 가져오기
	 * @return
	 */
	public  int mySDK() {
		return Integer.parseInt(Build.VERSION.SDK);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean ChkGps(Context context) {
		String gs = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (gs.indexOf("gps", 0) < 0 && gs.indexOf("network", 0) < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 휴대폰번호 가져오기
	 * @param context
	 * @return
	 */
	public String getPhoneNumber(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNum = telManager.getLine1Number();
		if (phoneNum != null) {
			if (phoneNum.startsWith("+82")) {
				phoneNum = "0" + phoneNum.substring(3);
			}
		} else
			phoneNum = "";
		return phoneNum;
	}
	
	/**
	 * 종료 EXIT 토스트 띄우기
	 * @param context
	 */
	public void ShowExitToast(Context context) { // exit 토스트
		if (mIsBackKeyPressed == false) {
			mIsBackKeyPressed = true;
			mCurrentTimeInMillis = Calendar.getInstance().getTimeInMillis();
			
			showToast(context,"'뒤로'버튼 한번더 누르면 종료됩니다.");

			startTimer();
		} else {
			mIsBackKeyPressed = false;
			if (Calendar.getInstance().getTimeInMillis() <= (mCurrentTimeInMillis + (BACKEY_TIMEOUT))) {
				((Activity) context).moveTaskToBack(true);

				/*for (int i = 0; i < LinnoApplication.mAt.size(); i++) {
					LinnoApplication.mAt.get(i).finish();
				}*/
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
	}

	private void startTimer() {
		mTimerHander.sendEmptyMessageDelayed(MSG_TIMER_EXPIRED, BACKEY_TIMEOUT);
	}

	private Handler mTimerHander = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_TIMER_EXPIRED: {
				mIsBackKeyPressed = false;
			}
				break;
			}
		}
	};
	
	/**
	 * 네트워크 상태 체크
	 * @param activity
	 * @return
	 */
	public boolean isInternet(Activity activity) {
		internetState(activity);
		return isInternet();
	}
	
	public boolean isInternet() {
		return isInternet;
	}

	public void internetState(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		NetworkInfo wibro = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

		if (wifi.isConnected() || mobile.isConnected() || wibro.isConnected()) {
			isInternet = true;
		} else {
			isInternet = false;
		}
	}
	
	public int getinternetState(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		NetworkInfo wibro = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

		if(wifi.isConnectedOrConnecting()){
			return WIFISTATE;
		}else if(mobile.isConnected()){
			return NETWORKSTATE;
		}
		return 0;
	}
	
	/**
	 * 액티비티 notitle 만들기
	 * @param activity
	 */
	public void onActivityNoTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 *	dialog 띄우기
	 * @param context
	 * @param title
	 * @param msg
	 * @param version
	 */
	public void ShowDialog1btn(Context context, String title, String msg, dialogclick callback) { // 다이얼로그
		setOndialogClickCallback(callback);
		AlertDialog.Builder a_builder;
		if (mySDK() < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(dialogclick != null){
							dialogclick.setondialogokclick();
						}
						dialog.dismiss();
					}
				});
		a_builder.show();
	}
	/**
	 *	dialog 띄우기
	 * @param context
	 * @param title
	 * @param msg
	 * @param version
	 */
	public void ShowDialog1btndark(Context context, String title, String msg, dialogclick callback) { // 다이얼로그
		setOndialogClickCallback(callback);
		AlertDialog.Builder a_builder;
		if (mySDK() < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(dialogclick != null){
							dialogclick.setondialogokclick();
						}
						dialog.dismiss();
					}
				});
		a_builder.show();
	}
	/**
	 *	dialog 띄우기
	 * @param context
	 * @param title
	 * @param msg
	 * @param version
	 */
	public void ShowDialog2btn(Context context, String title, String msg, dialogclick callback) { // 다이얼로그
		setOndialogClickCallback(callback);
		AlertDialog.Builder a_builder;
		if (mySDK() < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(dialogclick != null){
							dialogclick.setondialogokclick();
						}
						dialog.dismiss();
					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(dialogclick != null){
							dialogclick.setondialocancelkclick();
						}
						dialog.dismiss();
					}
				});
		a_builder.show();
	}
	/**
	 *	dialog 띄우기
	 * @param context
	 * @param title
	 * @param msg
	 * @param version
	 */
	public void ShowDialog(Context context, String title, String msg) { // 다이얼로그
		AlertDialog.Builder a_builder;
		if (mySDK() < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					
						dialog.dismiss();
					}
				});
		a_builder.show();
	}
	
	/**
	 *	dialogExit 종료
	 * @param context
	 * @param title
	 * @param msg
	 * @param version
	 */
	public void ShowDialogExit(final Context context, String title, String msg) { // 다이얼로그
		AlertDialog.Builder a_builder;
		if (mySDK() < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						((Activity) context).finish();
					}
				});
		a_builder.show();
	}
	
	
	/**
	 * progress dialog 띄우기
	 * @param mContext
	 * @param msg
	 * @param version
	 * @return
	 */
	public ProgressDialog ShowProgressDialog(Context mContext, String msg) {
		ProgressDialog mdial = null;
		 if (mySDK() < 15) {
			mdial = ProgressDialog.show(mContext, null, msg, true, false);
		} else {
			mdial = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
			mdial.setMessage(msg);
			mdial.setCancelable(false);
			mdial.setIndeterminate(true);
			mdial.show();
		 }

		 		 
		return mdial;
	}
	
	/**
	 * 스크린 width
	 * @param context
	 */
	public int getScreenWidth(Context context){
		int mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
		return mScreenWidth;
	}
	
	/**
	 * 스크린height
	 * @param context
	 */
	public int getScreenHeight(Context context){
		int  mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
		return mScreenHeight;
	}
	
	/**
	 * edittext null 체크
	 * @param edittext
	 * @return
	 */
	public boolean getEditTextNullCheck(EditText edittext){
		if(edittext.getText().toString().trim().equals("") ||edittext.getText().toString().trim().length() == 0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 비밀번호 2개 체크 확인
	 * @param edittext1
	 * @param edittext2
	 * @return
	 */
	public boolean getPasswordCheck(EditText edittext1,EditText edittext2){
		if(edittext1.getText().toString().equals(edittext2.getText().toString())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * edittext 글자수 를 지정한다.
	 * @param length
	 * @param edittext
	 * @return
	 */
	public EditText setInputFilter(int length,EditText edittext){
		try {
			InputFilter[] filters = new InputFilter[] {new ByteLengthFilter(length, "KSC5601")};
			edittext.setFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return edittext;
		
	}
	
	/**
	 * EditText 등의 필드에 텍스트 입력/수정시 
	 * 입력문자열의 바이트 길이를 체크하여 입력을 제한하는 필터.
	 *
	 */
	public class ByteLengthFilter implements InputFilter {


	    private String mCharset; //인코딩 문자셋

	    protected int mMaxByte; // 입력가능한 최대 바이트 길이

	    public ByteLengthFilter(int maxbyte, String charset) {
	        this.mMaxByte = maxbyte;
	        this.mCharset = charset;
	    }

	    /**
	     * 이 메소드는 입력/삭제 및 붙여넣기/잘라내기할 때마다 실행된다.
	     *
	     * - source : 새로 입력/붙여넣기 되는 문자열(삭제/잘라내기 시에는 "")
	     * - dest : 변경 전 원래 문자열
	     */
	    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
	            int dend) {

	        // 변경 후 예상되는 문자열
	        String expected = new String();
	        expected += dest.subSequence(0, dstart);
	        expected += source.subSequence(start, end);
	        expected += dest.subSequence(dend, dest.length());

	        int keep = calculateMaxLength(expected) - (dest.length() - (dend - dstart));

	        if (keep <= 0) {
	            return ""; // source 입력 불가(원래 문자열 변경 없음)
	        } else if (keep >= end - start) {
	            return null; // keep original. source 그대로 허용
	        } else {
	            return source.subSequence(start, start + keep); // source중 일부만 입력 허용
	        }
	    }

	    /**
	     * 입력가능한 최대 문자 길이(최대 바이트 길이와 다름!).
	     */
	    protected int calculateMaxLength(String expected) {
	        return mMaxByte - (getByteLength(expected) - expected.length());
	    }    
	    
	    /**
	     * 문자열의 바이트 길이.
	     * 인코딩 문자셋에 따라 바이트 길이 달라짐.
	     * @param str
	     * @return
	     */
	    private int getByteLength(String str) {
	        try {
	            return str.getBytes(mCharset).length;
	        } catch (UnsupportedEncodingException e) {
	            //e.printStackTrace();
	        }
	        return 0;
	    }
	}    
	
	/**
	 * 휴대폰번호 유효체크
	 * @param cellphoneNumber
	 * @return
	 */
	public boolean isValidCellPhoneNumber(String cellphoneNumber) {
		boolean returnValue = false;
		boolean returnValue010 = false;
		boolean returnValue01x = false;
		
		String regex010 = "^\\s*(010)(|\\)|\\s)*(\\d{4})(|\\s)*(\\d{4})\\s*$";
		Pattern p010 = Pattern.compile(regex010);
		Matcher m010 = p010.matcher(cellphoneNumber);
		if (m010.matches()) {
			returnValue010 = true;
		}
		
		String regex01x = "^\\s*(011|016|017|018|019)(|\\)|\\s)*(\\d{3})(|\\s)*(\\d{4})\\s*$";
		Pattern p01x = Pattern.compile(regex01x);
		Matcher m01x = p01x.matcher(cellphoneNumber);
		if (m01x.matches()) {
			returnValue01x = true;
		}
		
		if(returnValue010 || returnValue01x){
			returnValue = true;
		}
		
		return returnValue;
	}
	
	/**
	 * 이메일유효성 체크
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email){
		 String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(email);
		 boolean isNormal = m.matches();
		 return isNormal;
	}
	
	public boolean checkValid(String szData){
		boolean bRValue = false;
		if( szData != null && !szData.trim().equals("")){
			bRValue = true;
		}
		
		return bRValue;
	}
	
	/**
	 * 천자리마다 콤마찍기
	 * @param str
	 * @return
	 */
	public String makeStringComma(String str) {
   	if (str.length() == 0)
   		return "";
   		long value = Long.parseLong(str);
   		DecimalFormat format = new DecimalFormat("###,###");
   		return format.format(value);    
	}
	
	
	public void ShowArrayDialog(Context context,String title,String[] array,int basevalue,setOnItemChoice callback) {
		setOnItemChoiceCallback(callback);
		AlertDialog.Builder builder;
		if (mySDK() < 15) {
			builder = new AlertDialog.Builder(context);
		} else {
			builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		builder.setTitle(title);
		builder.setSingleChoiceItems(array, basevalue,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if(choicecallback != null){
							choicecallback.setOnItemClick(which);
						}
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/**
	 * 현재 최상위 화면 activity인지 체크
	 * @param context
	 * @return
	 */
	public boolean isTopApplication(Context context,int index) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> tasks = am.getRunningTasks(index);
	    if (!tasks.isEmpty() && tasks.size() > 1) {
	        ComponentName topActivity = tasks.get(1).topActivity;
	        if (!topActivity.getPackageName().equals(context.getPackageName())) {
	            return true;
	        }
	    }

	    return false;
	}
	
	
	/**
     * Process가 실행중인지 여부 확인.
     * @param context, packageName
     * @return true/false
     */
    public boolean isRunningProcess(Context context, String packageName) {
 
        boolean isRunning = false;
        ActivityManager actMng = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);                      
        List<RunningAppProcessInfo> list = actMng.getRunningAppProcesses();     
        for(RunningAppProcessInfo rap : list)                           
        {                                
            if(rap.processName.equals(packageName))                              
            {                                   
                isRunning = true;     
                break;
            }                         
        }
        return isRunning;
    }
    
    /**
     * 파일경로
     * @param context
     * @param pakagename
     * @return
     */
    public String getFileDir_Ex(Context context,String pakagename) {
		String result = "";
		try {
			int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
			if (sdkVersion < 8) {
				File extSt = Environment.getExternalStorageDirectory();
				result = extSt.getAbsolutePath() + "/Android" + "/data"+ "/"+pakagename;
			} else {
				result = context.getExternalFilesDir(null).getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    
    private final char[] KEY_LIST = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z' };
	
	private Random rnd = new Random();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	private String getRandomStr() {
		char[] rchar = { KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)]
				, KEY_LIST[rnd.nextInt(36)] };
		return String.copyValueOf(rchar);
	}
		
	/**
	 * 파일명 만들기
	 * @return
	 */
	public String getFileKey() {
			return getRandomStr()
					+ dateFormat.format(new Date(System.currentTimeMillis()));
		}		
}
