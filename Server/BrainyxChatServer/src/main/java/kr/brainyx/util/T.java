package kr.brainyx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.util.ChineseCalendar;

public class T {

	/**
	 * SimpleDateFormat
	 * 		yyyy : 년도
	 * 		w    : 일년안에서 몇번째 주인지		W   : 한달안에서 몇번째 주인지
	 * 		MM   : 월
	 * 		dd   : 일							D   : 일년에서 몇번째 일인지
	 * 		E    : 요일(Tuesday,Tue)			F   : 요일을 숫자로(2)
	 * 		hh   : 시간(1~12)					HH  : 시간(1~24)
	 * 		kk   : 시간(0~11)					KK  : 시간(0~23)
	 * 		a	 : AM/PM
	 * 		mm	 : 분
	 * 		ss	 : 초							SSS : Millisecond
	 */
	
	private static String[] solarArr = new String[]{"0101", "0301", "0505", "0606", "0815", "1003", "1009", "1225"};
	private static String[] solarValueArr = new String[]{"신정", "삼일절", "어린이날", "현충일", "광복절", "개천절", "한글날", "크리스마스"};

	private static String[] lunarArr = new String[]{"1231", "0101", "0102", "0408", "0814", "0815", "0816"};
	private static String[] lunarValueArr = new String[]{"설날연휴", "설날", "설날연휴", "석가탄신일", "추석연휴", "추석", "추석연휴"};



	/**
	 * 해당일자가 음력 법정공휴일에 해당하는 지 확인
	 * @param date
	 * @return
	 */
	public static String isHolidayLunar (String date) {

		boolean result = false;	

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));
		return isHolidayLunar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6)) );
	}

	public static String isHolidayLunar (int year, int month, int day) {

		boolean result = false;	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, day);

		ChineseCalendar chinaCal = new ChineseCalendar();
		chinaCal.setTimeInMillis(calendar.getTimeInMillis());

		/** 음력으로 변환된 월과 일자 **/
		int mm = chinaCal.get(ChineseCalendar.MONTH) + 1;
		int dd = chinaCal.get(ChineseCalendar.DAY_OF_MONTH);
		
		StringBuilder sb = new StringBuilder();
		if (mm < 10)    sb.append("0");
		sb.append(mm);

		if (dd < 10)    sb.append("0");
		sb.append(dd);

		/** 음력 12월의 마지막날 (설날 첫번째 휴일)인지 확인 **/
		if (mm == 12) {
			int lastDate = chinaCal.getActualMaximum(ChineseCalendar.DAY_OF_MONTH);
			if (dd == lastDate) {
				return "설날연휴";
			}
		}

		for (int i=0;i<lunarArr.length; i++) {
			if (sb.toString().equals(lunarArr[i])) {
				return lunarValueArr[i];
			}
		}

		return "";
	}

	/**
	 * 해당일자가 양력 법정공휴일에 해당하는 지 확인
	 * @param date
	 * @return
	 */
	public static String isHolidaySolar (String date) {

		boolean result = false;
		Calendar calendar = Calendar.getInstance();

		if (date.equals("") || date.length() > 8) {
			date = new java.text.SimpleDateFormat("MMdd").format(calendar.getTime());
		} else {
			date = date.substring(4);
		}
		int month = Integer.parseInt(date.substring(0,2));
		int day = Integer.parseInt(date.substring(2,4));
		return isHolidaySolar(month, day);
	}
	public static String isHolidaySolar (int month, int day) {

		boolean result = false;

		String date = F.addZero(month, 2) + F.addZero(day, 2);

		for (int i=0; i<solarArr.length; i++) {
			if (date.equals(solarArr[i])) {
				return solarValueArr[i];
			}
		}

		return "";
	}

	/**
	 * 해당일자가 일요일인지 확인
	 * @param date
	 * @return
	 */

	public static boolean isSunday (String date) {

		boolean result = false;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));
		if (calendar.get ( Calendar.DAY_OF_WEEK ) == 1) {
			result = true;
		}

		return result;
	}

	
	/**
	 * 오늘 날짜를 구한다.
	 * @return "yyyyMMDD"
	 */
	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String today = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		return today;
	}
	

	/**
	 * 오늘 연도를 구한다.
	 * @return "yyyy"
	 */
	public static String getTodayYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String today = new SimpleDateFormat("yyyy").format(calendar.getTime());	//"yyyy"
		return today;
	}

	/**
	 * 오늘 월을 구한다.
	 * @return "MM"
	 */
	public static String getTodayMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String today = new SimpleDateFormat("MM").format(calendar.getTime());	//"MM"
		return today;
	}
	/**
	 * 오늘 날짜를 구한다.
	 * @return "dd"
	 */
	public static String getTodayDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String today = new SimpleDateFormat("dd").format(calendar.getTime());	//"dd"
		return today;
	}

	/**
	 * 현재 시간을 구한다. 시간(0~23)
	 * @return "yyyyMMDDHHmmss"
	 */
	public static String getHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String now = new SimpleDateFormat("kk").format(calendar.getTime());	//"hh" : 시간(0~23)
		return now;
	}

	/**
	 * 현재 요일을 구한다.
	 * @return "yyyyMMDDHHmmss"
	 */
	public static String getWeekday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String now = new SimpleDateFormat("E").format(calendar.getTime());	//"E" : 요일을 숫자로
		return now;
	}

	/**
	 * 오늘이 이번달의 몇번째 주인지 구한다.
	 * @return "W"
	 */
	public static String getTodayWeekInMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String today = new SimpleDateFormat("W").format(calendar.getTime());	//"W"
		return today;
	}

	/**
	 * 현재 날짜와 시간, 분, 초를 구한다.
	 * @return "yyyyMMDDHHmmss"
	 */
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());	//"yyyyMMDDHHmmss"
		return now;
	}
	
	/**
	 * 이달의 마지막 날을 구한다.
	 * @return int 날짜
	 */
	public static int getLastMonthday() {
		Calendar calendar = Calendar.getInstance();
		int lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastday;
	}

	/**
	 * 주어진 달의 마지막 날을 구한다.
	 * @param year
	 * @param month
	 * @return int 날짜
	 */
	public static int getLastMonthday(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1);
		int lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastday;
	}

	/**
	 * 주어진 달의 마지막 날을 구한다.
	 * @param yyyy_MM
	 * @return int 날짜
	 */
	public static int getLastMonthday(String yyyy_MM) {
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(yyyy_MM.split("-")[0]);
		int month = Integer.parseInt(yyyy_MM.split("-")[1])-1;
		calendar.set(year, month, 1);
		int lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastday;
	}
	
	/**
	 * 이달의 마지막 날을 구한다.
	 * @return String yyyyMMdd
	 */
	public static String getLastMonthFullday() {
		String today = T.getToday().substring(0, 7);
		Calendar calendar = Calendar.getInstance();
		String lastday = today +"-"+ F.addZero(calendar.getActualMaximum(Calendar.DAY_OF_MONTH),2);
		return lastday;
	}

	/**
	 * 주어진 달의 마지막 날을 구한다.
	 * @param year
	 * @param month
	 * @return String yyyyMMdd
	 */
	public static String getLastMonthFullday(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1);
		String lastday = year +"-"+ F.addZero(month, 2) +"-"+ F.addZero(calendar.getActualMaximum(Calendar.DAY_OF_MONTH),2);
		return lastday;
	}

	/**
	 * 주어진 달의 마지막 날을 구한다.
	 * @param yyyy_MM
	 * @return String yyyyMMdd
	 */
	public static String getLastMonthFullday(String yyyy_MM) {
		String today = T.getToday().substring(0, 6);
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt(yyyy_MM.split("-")[0]);
		int month = Integer.parseInt(yyyy_MM.split("-")[1])-1;
		calendar.set(year, month, 1);
		String lastday = yyyy_MM.substring(0, 7) +"-"+ F.addZero(calendar.getActualMaximum(Calendar.DAY_OF_MONTH),2);
		return lastday;
	}
	
	/**
	 * 주어진 달의 첫 날을 구한다.
	 * @param year
	 * @param month
	 * @return int 날짜
	 */
	public static String getFirstMonthday(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1);
		String firstDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return firstDay;
	}
	/**
	 * 주어진 날짜의 요일을 구한다.
	 * 1:일요일, 2:월요일, 3:화요일, 4:수요일, 5:목요일, 6:금요일, 7:토요일
	 * @param yyyyMMdd
	 * @return
	 */
	public static int getWeekDay(String yyyyMMdd) {
		String[] date = yyyyMMdd.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	

	/**
	 * 주어진 날짜에 주어진 날짜를 더하거나 뺀다.
	 * @param yyyyMMdd
	 * @param add
	 * @return
	 */
	public static String getDateAdd(String yyyyMMdd, int add) {
		String[] date = yyyyMMdd.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
		calendar.add(Calendar.DATE, add);
		String addedDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		return addedDay;
	}
	
	/**
	 * 주어진 시간에 주어진 분을 더하거나 뺀다.
	 * @param HHmm
	 * @param add
	 * @return
	 */
	public static String getMinuteAdd(String HHmm, int add) {
		int hour = Integer.parseInt(HHmm.substring(0, 2));
		int min = Integer.parseInt(HHmm.substring(2, 4));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.MINUTE, add);
		String addedMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());	//"HHmm"
		return addedMinute;
	}

	/**
	 * 두 날짜를 비교한다.
	 * 뒷 날짜가 앞 날짜와 같으면 : 0
	 * 뒷 날짜가 앞 날짜보다 이전이면 : -1
	 * 뒷 날짜가 앞 날짜보다 이후이면 : 1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDateDiff(String date1, String date2) {
		String[] d1 = date1.split("-");
		String[] d2 = date2.split("-");

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, Integer.parseInt(d1[0]));
		calendar1.set(Calendar.MONTH, Integer.parseInt(d1[1])-1);
		calendar1.set(Calendar.DATE, Integer.parseInt(d1[2]));
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.YEAR, Integer.parseInt(d2[0]));
		calendar2.set(Calendar.MONTH, Integer.parseInt(d2[1])-1);
		calendar2.set(Calendar.DATE, Integer.parseInt(d2[2]));
		
		return calendar2.compareTo(calendar1);
	}
	
	/**
	 * 요청한 주차의 일요일부터 토요일까지의 날짜를 배열로 리턴
	 * @param year
	 * @param month
	 * @param week
	 */
	public static String[] getFirstAndFinishDayOfWeek(int year, int month, int week) {
		Calendar calendar = Calendar.getInstance();
		//calendar.setFirstDayOfWeek(2);	//주 시작을 월요일로 세팅(월~일)
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.WEEK_OF_MONTH, week);
		
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		String weekStartDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		String weekEndDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		
		String[] thisWeekDay = { weekStartDay, weekEndDay}; 
		return thisWeekDay;
	}

	/**
	 * 요청한 주차의 월요일부터 다음주 일요일까지의 날짜를 배열로 리턴
	 * @param year
	 * @param month
	 * @param week
	 */
	public static String[] getFirstAndFinishDayOfWeekFromMonday(int year, int month, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(2);	//주 시작을 월요일로 세팅(월~일)
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.WEEK_OF_MONTH, week);
		
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		String weekStartDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		String weekEndDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		
		String[] thisWeekDay = { weekStartDay, weekEndDay}; 
		return thisWeekDay;
	}

	/**
	 * 요청한 주차의 월요일부터 다음주 일요일까지의 날짜를 배열로 리턴
	 * 단 1일이 금,토,일 일 경우 전달의 마지막주로 처리한다.
	 * @param year
	 * @param month
	 * @param week
	 */
	public static String[] getFirstAndFinishDayOfWeekFromMondayExceptFriday(int year, int month, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(2);	//주 시작을 월요일로 세팅(월~일)
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		
		String firstMonthDay = getFirstMonthday(year, month); 				// 월의 첫째날을 구한다.
		int firstWeekDay = getWeekDay(firstMonthDay);						// 월의 첫째날의 요일을 구한다.
		if (firstWeekDay == 6 || firstWeekDay == 7 || firstWeekDay == 1) {	// 월의 첫째날이 금,토,일이면 다음 주를 첫째주로 한다.
			calendar.set(Calendar.WEEK_OF_MONTH, week+1);
		} else {
			calendar.set(Calendar.WEEK_OF_MONTH, week);
		}
		
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		String weekStartDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		String weekEndDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	//"yyyyMMDD"
		
		String[] thisWeekDay = { weekStartDay, weekEndDay}; 
		return thisWeekDay;
	}
		
	/**
	 * 주어진 년월이 몇주까지인지 리턴
	 * @return "W"
	 */
	public static int getWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();

		String[] firstAndFinish = getFirstAndFinishDayOfMonth(year, month);
		String firstMonthDay = firstAndFinish[0]; 				// 월의 첫째날을 구한다.
		int finishMonthDay = getLastMonthday(year, month);				// 월의 마지막날을 구한다.
		
		int startDate = 1;
		int endDate = finishMonthDay;

		int firstWeekDay = getWeekDay(firstMonthDay);						// 월의 첫째날의 요일을 구한다.
		if (firstWeekDay == 6) {
			startDate = 4;
		} else if (firstWeekDay == 7) {
			startDate = 3;
		} else if (firstWeekDay == 1) {
			startDate = 2;
		}

		int weekCount = (int)Math.ceil((float)(endDate - startDate) / 7);
		return weekCount;
	}

	/**
	 * 요청한 년월의 처음 날짜와 마지막 날짜를 배열로 리턴
	 * @param year
	 * @param month
	 * @param week
	 */
	public static String[] getFirstAndFinishDayOfMonth(int year, int month) {
		String monthStartDay = T.getFirstMonthday(year, month);	//"yyyyMMDD"
		String monthEndDay = T.getLastMonthFullday(year, month);	//"yyyyMMDD"
		
		String[] thisMonthDay = { monthStartDay, monthEndDay}; 
		return thisMonthDay;
	}
	
	/**
	 * 주어진 기간동안의 분을 리턴한다.
	 * @param startDateTime : yyyyMMddHHmm. ex:201303202400
	 * @param endDateTime : yyyyMMddHHmm. ex:201303202400
	 * @return
	 * @throws ParseException
	 */
	public static int getDurationByMinute(String startDateTime, String endDateTime) {
		int duration = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		
		Date start = new Date(System.currentTimeMillis());
		Date end = new Date(System.currentTimeMillis());
		try {
			start = formatter.parse(startDateTime);
			end = formatter.parse(endDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long diff = end.getTime() - start.getTime();
		long d = diff / (60 * 1000);

		duration = Math.round(d);
		
		return duration;
	}
	
	/**
	 * 현재부터 주어진 종료일시 동안의 분을 리턴한다.
	 * @param endDateTime : yyyyMMddHHmm. ex:201303202400
	 * @return
	 * @throws ParseException
	 */
	public static int getDurationByMinute(String endDateTime) {
		int duration = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		
		Date start = new Date(System.currentTimeMillis());
		Date end = new Date(System.currentTimeMillis());
		try {
			end = formatter.parse(endDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long diff = end.getTime() - start.getTime();
		duration = (int)diff / (60 * 1000);
		
		return duration;
	}
	
	/**
	 * 나이를 리턴한다 : 현재 연도에서 생년월일의 연도를 뺀다. 
	 * @param birth
	 * @return
	 */
	public static int getAge(String birth) {
		int age = 0;
		if(birth.trim().length()==10) {
			int birthYear = Integer.parseInt(birth.substring(0, 4));
			Calendar calendar = Calendar.getInstance();
			int todayYear = calendar.get(Calendar.YEAR);
			age = todayYear - birthYear;
		}
		return age;
	}
	


}
