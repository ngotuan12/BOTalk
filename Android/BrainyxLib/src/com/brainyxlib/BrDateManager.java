package com.brainyxlib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.brainyxlib.util.DialogDatePickerOnlyMonth;
import com.brainyxlib.util.DialogDatePickerOnlyYear;

public class BrDateManager {
	public onDateSelected callback = null;
	public onTimeSelected timecallback = null;
	
	public interface onDateSelected{
		public void onDateSelectedListner(String year, String month, String day);
	}
	public interface onTimeSelected{
		public void onTimeSelectedListner(String ampm,int hour, int minute);
	}
	public void setOntimeSelected(onTimeSelected callback){
		this.timecallback = callback;
	}
	
	public void setOnDateSelected(onDateSelected callback){
		this.callback = callback;
	}
	
	public final String TAG = "DateManager";
	private static BrDateManager instance = null;
	
	
	
	public synchronized static BrDateManager getInstance() {
		if (instance == null) {
			synchronized (BrDateManager.class) {
				if (instance == null) {
					instance = new BrDateManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 특정날짜 에서 입력한 일 뒤에 날짜 구한다
	 * 
	 * @return 예) 20130322
	 */
	public String selectDay(int year, int month, int day, int iDay) {
		StringBuffer sbDate = new StringBuffer();
		Calendar temp2 = Calendar.getInstance();
		// 특정날짜
		temp2.set(Calendar.YEAR, year);
		temp2.set(Calendar.MONTH, month - 1);
		temp2.set(Calendar.DATE, day);

		temp2.add(Calendar.DATE, iDay);

		int nYear = temp2.get(Calendar.YEAR);
		int nMonth = temp2.get(Calendar.MONTH) + 1;
		int nDay = temp2.get(Calendar.DAY_OF_MONTH);

		sbDate.append(nYear);
		if (nMonth < 10)
			sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10)
			sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();

	}

	/**
	 * 오늘날짜 에서 입력한일수 뒤에 날짜구하기
	 * 
	 * @return 예) 20130322
	 */
	public String today(int iDay) {
		StringBuffer sbDate = new StringBuffer();
		Calendar temp2 = Calendar.getInstance();

		temp2.add(Calendar.DAY_OF_MONTH, iDay); // 오늘날짜

		int nYear = temp2.get(Calendar.YEAR);
		int nMonth = temp2.get(Calendar.MONTH) + 1;
		int nDay = temp2.get(Calendar.DAY_OF_MONTH);

		sbDate.append(nYear + "_");
		if (nMonth < 10)
			sbDate.append("0");
		sbDate.append(nMonth + "_");
		if (nDay < 10)
			sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}

	/**
	 * 디데이 몇일 남았는지 구하기
	 * 
	 * @return 예) -32
	 */
	public int caldate(String myear, String mmonth, String mday) {
		try {
			Calendar today = Calendar.getInstance();
			Calendar dday = Calendar.getInstance();

			dday.set(Integer.parseInt(myear), Integer.parseInt(mmonth) - 1,
					Integer.parseInt(mday));

			long day = dday.getTimeInMillis() / 86400000;
			// 각각 날의 시간 값을 얻어온 다음
			// ( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )
			long tday = today.getTimeInMillis() / 86400000;
			long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
			return (int) count + 1; // 날짜는 하루 + 시켜줘야합니다.
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 원하는타입으로 날짜 변경을 해준다.
	 * 
	 * @return 예) 20130410 > 2013년04월10일
	 */
	public String GetTimeEncoding(String datetype, String type) {
		// String[] textDate = datetype.split("_");
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(datetype);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format1 = new java.text.SimpleDateFormat(type);
		String dateString = format1.format(date);
		return dateString;
	}

	public String getTimeEncoding(String datetime,String thistype ,String type) {
		SimpleDateFormat format = new java.text.SimpleDateFormat(thistype);
		Date date = null;
		try {
			date = format.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format1 = new java.text.SimpleDateFormat(type);
		String dateString = format1.format(date);
		return dateString;

	}

	/**
	 * 특정날짜 형변환 > 특정일 뒤에 날짜 가져오기
	 * 
	 * @return 예) int 20130410, int 30 > 20130510
	 */
	public  int getDate(int date, int ddayy) {
		String day = GetTimeEncoding(String.valueOf(date),"yyyy_MM_dd"); // 생일타입변경
		String[] day2 = day.split("_"); // 타입바꾼 생일 년,월,일로 나누기
		String day3 = selectDay(Integer.parseInt(day2[0]),
				Integer.parseInt(day2[1]), Integer.parseInt(day2[2]), ddayy); // 생일에서
																				// +일더한
																				// 날짜
																				// 가져오기
		return Integer.parseInt(day3);
	}

	/**
	 * Calendar.get(Calendar.dayofWeek)를 넣으면 해당 요일을 반환
	 * 
	 * @return 예) GetMonthOfWeek(cal.get(Calendar.DAY_OF_WEEK));
	 */
	public String GetMonthOfWeek(int week) {
		String monthof = "";
		switch (week) {
		case 1:
			monthof = "일요일";
			break;
		case 2:
			monthof = "월요일";
			break;
		case 3:
			monthof = "화요일";
			break;
		case 4:
			monthof = "수요일";
			break;
		case 5:
			monthof = "목요일";
			break;
		case 6:
			monthof = "금요일";
			break;
		case 7:
			monthof = "토요일";
			break;
		}
		return monthof;
	}

	/**
	 * Calendar.get(Calendar.dayofWeek)를 넣으면 해당 요일을 반환
	 * 
	 * @return 예) GetMonthOfWeek(cal.get(Calendar.DAY_OF_WEEK));
	 */
	public String GetMonthOfWeekeng(int week) {
		String monthof = "";
		switch (week) {
		case 1:
			monthof = "sunday";
			break;
		case 2:
			monthof = "monday";
			break;
		case 3:
			monthof = "tuesday";
			break;
		case 4:
			monthof = "wednesday";
			break;
		case 5:
			monthof = "thursday";
			break;
		case 6:
			monthof = "friday";
			break;
		case 7:
			monthof = "saturday";
			break;
		}
		return monthof;
	}

	/**
	 * 날짜가 한자리일때 앞에 0을 붙여줌
	 * 
	 * @return 예) 6 > 06
	 */
	public String ReturnZero(int days) {
		String day = Integer.toString(days);
		if (days < 10)
			day = "0" + day;

		return day;
	}

	/**
	 * 원을 입력하면 해당월에 int값을 리턴해줌
	 * 
	 * @return 예) 6 > 06
	 */
	public int GetMonthChange(int month) {
		int chmonth = 0;

		switch (month) {
		case 1:
			chmonth = Calendar.JANUARY;
			break;
		case 2:
			chmonth = Calendar.FEBRUARY;
			break;
		case 3:
			chmonth = Calendar.MARCH;
			break;
		case 4:
			chmonth = Calendar.APRIL;
			break;
		case 5:
			chmonth = Calendar.MAY;
			break;
		case 6:
			chmonth = Calendar.JUNE;
			break;
		case 7:
			chmonth = Calendar.JULY;
			break;
		case 8:
			chmonth = Calendar.AUGUST;
			break;
		case 9:
			chmonth = Calendar.SEPTEMBER;
			break;
		case 10:
			chmonth = Calendar.OCTOBER;
			break;
		case 11:
			chmonth = Calendar.NOVEMBER;
			break;
		case 12:
			chmonth = Calendar.DECEMBER;
			break;
		}
		return chmonth;
	}

	/**
	 * 나이구하기
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public int GetAge(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);

		Calendar now = Calendar.getInstance();

		int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
				|| (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) && cal
						.get(Calendar.DAY_OF_MONTH) > now
						.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}
		return age;
	}

	public int GetMonthAfter(String today, String dday) {
		/*
		 * String szSDate = "20080302"; String szEDate = "20091202";
		 */

		int sYear = Integer.parseInt(dday.substring(0, 4));
		int sMonth = Integer.parseInt(today.substring(4, 6));

		int eYear = Integer.parseInt(today.substring(0, 4));
		int eMonth = Integer.parseInt(today.substring(4, 6));

		int month_diff = (eYear - sYear) * 12 + (eMonth - sMonth);
		return month_diff;
	}

	public boolean isDateExpired(String today, String compareday,
			int sday) {
		Date todaydate = null;
		Date comparedate = null;

		todaydate = ChangeStringToDate(today);
		comparedate = ChangeStringToDate(compareday);

		Calendar todaycal = Calendar.getInstance();
		Calendar compacal = Calendar.getInstance();

		todaycal.set(Integer.parseInt(today.substring(0, 4)),
				Integer.parseInt(today.substring(4, 6)) - 1,
				Integer.parseInt(today.substring(6, 8)));
		compacal.set(Integer.parseInt(compareday.substring(0, 4)),
				Integer.parseInt(compareday.substring(4, 6)) - 1,
				Integer.parseInt(compareday.substring(6, 8)));

		long b = (compacal.getTimeInMillis() - todaycal.getTimeInMillis()) / 1000;
		long a = b / (60 * 60 * 24);

		if (a > 0 && a <= sday) {
			return true;
		}
		return false;
	}

	public boolean isDateExpired2(String today, String compareday,
			int sday) {
		Date todaydate = null;
		Date comparedate = null;

		todaydate = ChangeStringToDate(today);
		comparedate = ChangeStringToDate(compareday);

		Calendar todaycal = Calendar.getInstance();
		Calendar compacal = Calendar.getInstance();

		todaycal.set(Integer.parseInt(today.substring(0, 4)),
				Integer.parseInt(today.substring(5, 6)) - 1,
				Integer.parseInt(today.substring(6, 8)));
		compacal.set(Integer.parseInt(compareday.substring(0, 4)),
				Integer.parseInt(compareday.substring(5, 6)) - 1,
				Integer.parseInt(compareday.substring(6, 8)));

		long b = (compacal.getTimeInMillis() - todaycal.getTimeInMillis()) / 1000;
		long a = b / (60 * 60 * 24);

		if (a >= 0 && a <= sday) {
			return true;
		}
		return false;
	}

	public boolean isDateExpired4(String today, String compareday) {
		Date todaydate = null;
		Date comparedate = null;

		todaydate = ChangeStringToDate(today);
		comparedate = ChangeStringToDate(compareday);

		Calendar todaycal = Calendar.getInstance();
		Calendar compacal = Calendar.getInstance();

		todaycal.set(Integer.parseInt(today.substring(0, 4)),
				Integer.parseInt(today.substring(5, 6)) - 1,
				Integer.parseInt(today.substring(6, 8)));
		compacal.set(Integer.parseInt(compareday.substring(0, 4)),
				Integer.parseInt(compareday.substring(5, 6)) - 1,
				Integer.parseInt(compareday.substring(6, 8)));

		long b = (compacal.getTimeInMillis() - todaycal.getTimeInMillis()) / 1000;
		long a = b / (60 * 60 * 24);

		if (a > 7) {
			return true;
		}
		return false;
	}

	public long isDateExpired3(String today, String compareday) {
		Date todaydate = null;
		Date comparedate = null;

		todaydate = ChangeStringToDate(today);
		comparedate = ChangeStringToDate(compareday);

		Calendar todaycal = Calendar.getInstance();
		Calendar compacal = Calendar.getInstance();

		todaycal.set(Integer.parseInt(today.substring(0, 4)),
				Integer.parseInt(today.substring(5, 6)) - 1,
				Integer.parseInt(today.substring(6, 8)));
		compacal.set(Integer.parseInt(compareday.substring(0, 4)),
				Integer.parseInt(compareday.substring(5, 6)) - 1,
				Integer.parseInt(compareday.substring(6, 8)));

		long b = (compacal.getTimeInMillis() - todaycal.getTimeInMillis()) / 1000;
		long a = b / (60 * 60 * 24) - 1;

		return a;
	}

	public String ChangeDateToString(Date dateinput) {
		String dateFoSmat = "yyyyMMdd";
		String result = "";
		SimpleDateFormat formatt = new SimpleDateFormat(dateFoSmat);
		result = formatt.format(dateinput);
		return result;
	}

	public Date ChangeStringToDate(String input) {
		Date resultdate = null;
		String dateformat = "yyyyMMdd";
		java.text.DateFormat df = new SimpleDateFormat(dateformat);
		try {
			resultdate = df.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultdate;
	}

	/**
	 * d오늘날짜구하기
	 * @return
	 */
	public String GetToday() {
		Calendar today = Calendar.getInstance();
		int inmonth = today.get(Calendar.MONTH) + 1;
		int inDay = today.get(Calendar.DAY_OF_MONTH);
		int inyear = today.get(Calendar.YEAR);

		return Integer.toString(inyear) + ReturnZero(inmonth)+ ReturnZero(inDay);
	}

	/**
	 * system.currenttime 을 인코딩
	 * 
	 * @param time
	 * @return
	 */
	public String getCurrenTimeEndcoding(long time) {
		String timeis = "";
		try {
			Date date = new Date(time);
			SimpleDateFormat sdfNow = new SimpleDateFormat("yy-MM-dd_HH:mm:ss");
			timeis = sdfNow.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return timeis;
	}

	/**
	 * 년,월,일,시간,분을 입력하면 디비convert가능한 형태로 리턴
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public String getTimeEncoding(int year, int month, int day , int hour, int minute){
		String timeis = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, minute);
			
			Date date = calendar.getTime();
			SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeis = sdfNow.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timeis;
	}
	
	/**
	 * system.currenttime 을 인코딩
	 * 
	 * @param time
	 * @return
	 */
	public String getCurrenTimeEndcoding(long time, String format) {
		String timeis = "";
		try {
			Date date = new Date(time);
			SimpleDateFormat sdfNow = new SimpleDateFormat(format);
			timeis = sdfNow.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return timeis;
	}

	/**
	 * 오전오후붙여리턴
	 * @param ampm
	 * @param hour
	 * @param minute
	 * @return
	 */
	public String getAMPM(int ampm,String hour,String minute) {
		if (ampm == Calendar.getInstance().get(Calendar.AM)) {
			return "오전 " + hour+":"+minute;
		} else {
			int tims = Integer.valueOf(hour) - 12;
			return "오후 " + String.valueOf(tims) + ":" + minute;
		}
	}
	
	public String AfterMonth(int year,int month,int iMonth){
		StringBuffer sbDate = new StringBuffer();
		Calendar temp2 = Calendar.getInstance();
		temp2.set(Calendar.YEAR, year);
		temp2.set(Calendar.MONTH, month - 1);
		temp2.add(Calendar.MONTH, iMonth);
		int nYear = temp2.get(Calendar.YEAR);
		int nMonth = temp2.get(Calendar.MONTH) + 1;
		sbDate.append(nYear);
		sbDate.append("/");
		if (nMonth < 10)
			sbDate.append("0");
		sbDate.append(nMonth);

		return sbDate.toString();
	}
	
	public String GetAfterMonth(int iMonth,int month, int year){
		Calendar today = Calendar.getInstance();
		int inmonth = Integer.valueOf(month); 
		int inyear =  Integer.valueOf(year); 
		
		today.set(Calendar.YEAR, inyear);
		today.set(Calendar.MONTH, inmonth - 1);
		today.add(Calendar.MONTH, iMonth);
		
		int nYear = today.get(Calendar.YEAR);
		int nMonth = today.get(Calendar.MONTH) + 1;
		
		return Integer.toString(nYear) + "/" + ReturnZero(nMonth);
	}
	
	public int GetYear(){
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR);
	}
	
	public int GetMonth(){
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 해당년월 마지막 날을 가져옴
	 * @param year
	 * @param month
	 * @return
	 */
	public int getCalendarDay(int year, int month) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(year, month-1, 1);
			int maxday = cal.getActualMaximum(Calendar.DATE);
			return maxday;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 연도 피커
	 * @param context
	 * @param callback
	 */
	public void CallOnlyYearPicker(Context context,onDateSelected callback){
		setOnDateSelected(callback);
		final Calendar c = Calendar.getInstance();        
        int mYear = c.get(Calendar.YEAR);        
        int mMonth = c.get(Calendar.MONTH);        
        int mDay = c.get(Calendar.DAY_OF_MONTH);   
        
        DialogDatePickerOnlyYear dialog =  null;
        if (BrUtilManager.getInstance().mySDK() < 15) {
    		dialog = new DialogDatePickerOnlyYear(context, yearMonthListener, mYear, mMonth, mDay);
    	}else{
    		dialog = new DialogDatePickerOnlyYear(context, DatePickerDialog.THEME_HOLO_LIGHT, yearMonthListener, mYear, mMonth, mDay);
    	}
        dialog.setCancelable(false);
      	dialog.show();
	}
	/**
	 * 월 피커
	 * @param context
	 * @param callback
	 */
	public void CallOnlyMonthPicker(Context context,onDateSelected callback){
		setOnDateSelected(callback);
		final Calendar c = Calendar.getInstance();        
        int mYear = c.get(Calendar.YEAR);        
        int mMonth = c.get(Calendar.MONTH);        
        int mDay = c.get(Calendar.DAY_OF_MONTH);   
        
        DialogDatePickerOnlyMonth dialog =  null;
        if (BrUtilManager.getInstance().mySDK() < 15) {
    		dialog = new DialogDatePickerOnlyMonth(context, yearMonthListener, mYear, mMonth, mDay);
    	}else{
    		dialog = new DialogDatePickerOnlyMonth(context, DatePickerDialog.THEME_HOLO_LIGHT, yearMonthListener, mYear, mMonth, mDay);
    	}
        dialog.setCancelable(false);
      	dialog.show();
	}
	
	
	
	
	
	/**
	 * 데이트 피커 보이기
	 * @param context
	 * @param callback
	 */
	public void CallYearMonthPicker(Context context,onDateSelected callback){
		setOnDateSelected(callback);
		final Calendar c = Calendar.getInstance();        
        int mYear = c.get(Calendar.YEAR);        
        int mMonth = c.get(Calendar.MONTH);        
        int mDay = c.get(Calendar.DAY_OF_MONTH);   
        	
        DatePickerDialog dialog =  null;
    	if (BrUtilManager.getInstance().mySDK() < 15) {
    		dialog = new DatePickerDialog(context, yearMonthListener, mYear, mMonth, mDay);
    	}else{
    		dialog = new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT, yearMonthListener, mYear, mMonth, mDay);
    	}
        dialog.setCancelable(false);
		dialog.show();
	}
	
	public OnDateSetListener yearMonthListener = new OnDateSetListener() {		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			if(callback != null){
				callback.onDateSelectedListner(String.valueOf(year),String.valueOf(ReturnZero(monthOfYear + 1)) , String.valueOf(ReturnZero(dayOfMonth)));
			}
		}			
	};
	
	/**
	 * 타임피커 보이기
	 * @param context
	 * @param callback
	 */
	public void CallTimePicker(Context context,onTimeSelected callback){
		setOntimeSelected(callback);
		final Calendar c = Calendar.getInstance();    
		int hour= c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		boolean ampm = (c.get(Calendar.AM_PM) == 0);
        
		TimePickerDialog dialog = null;
		if (BrUtilManager.getInstance().mySDK() < 15) {
			dialog = new TimePickerDialog(context, ontimeset, hour, minute, false);
		} else {
			dialog = new TimePickerDialog(context, TimePickerDialog.THEME_HOLO_LIGHT, ontimeset, hour, minute, false);
		}
		dialog.setCancelable(false);
		dialog.show();
	}
	
	public OnTimeSetListener ontimeset = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			if(timecallback != null){
				if(hourOfDay > 12){
					timecallback.onTimeSelectedListner("오후" ,hourOfDay, minute);
				}else{
					timecallback.onTimeSelectedListner("오전",hourOfDay, minute);	
				}
				
			}
		}
	};
	
	public final static int SEC 	= 60;
	public final static int MIN 	= 60;
	public final static int HOUR 	= 24;
	public final static int DAY 	= 7;
	public final static int MONTH = 12;
	
	/**
	 * 
	 * @param dataString
	 * @return
	 */
	public String CreateDataWithCheck(String dataString)
	{
		//		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
		//				"yyyy-MM-dd HH:mm:ss.S");
		java.text.SimpleDateFormat format= null;
		if(dataString.length() != 21) {
			format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		}
		else {
			format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
		}
		java.util.Date date = null;
		try {
			date = format.parse(dataString);

			long curTime = System.currentTimeMillis();
			long regTime = date.getTime();
			long diffTime = (curTime - regTime) / 1000;

			String msg = null;
			if (diffTime < SEC) {
				// sec
				
				SimpleDateFormat aformat = new SimpleDateFormat("aa HH:mm");
				//msg = aformat.format(date);
				String time = aformat.format(date);
				String[] entime = time.split(" ");
				String[] entime2 = entime[1].split(":");
				if(Integer.valueOf(entime2[0]) > 12){
					msg =  entime[0] +" " + String.valueOf(Integer.valueOf(entime2[0]) -12) + ":" + entime2[1];
				}else{
					msg = aformat.format(date);
				}
				//msg = "방금 전";
			} else if ((diffTime /= SEC) < MIN) {
				// min
				SimpleDateFormat aformat = new SimpleDateFormat("aa HH:mm");
				//msg = aformat.format(date);
				String time = aformat.format(date);
				String[] entime = time.split(" ");
				String[] entime2 = entime[1].split(":");
				if(Integer.valueOf(entime2[0]) > 12){
					msg =  entime[0] +" " + String.valueOf(Integer.valueOf(entime2[0]) -12) + ":" + entime2[1];
				}else{
					msg = aformat.format(date);
				}
				//msg = diffTime + "분 전";
			} else if ((diffTime /= MIN) < HOUR) {
				// hour
				SimpleDateFormat aformat = new SimpleDateFormat("aa HH:mm");
				//msg = aformat.format(date);
				String time = aformat.format(date);
				String[] entime = time.split(" ");
				String[] entime2 = entime[1].split(":");
				if(Integer.valueOf(entime2[0]) > 12){
					msg =  entime[0] +" " + String.valueOf(Integer.valueOf(entime2[0]) -12) + ":" + entime2[1];
				}else{
					msg = aformat.format(date);
				}
				//msg = (diffTime) + "시간 전";
			} else if ((diffTime /= HOUR) < DAY) {
				// day
				SimpleDateFormat aformat = new SimpleDateFormat("MM월dd일");
				msg = aformat.format(date);
				
				//msg = (diffTime) + "일 전";
			}
			else { 
				SimpleDateFormat aformat = new SimpleDateFormat("MM월dd일");
				msg = aformat.format(date);
			}
			return msg;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
