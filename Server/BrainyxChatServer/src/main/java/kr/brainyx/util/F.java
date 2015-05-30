package kr.brainyx.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class F {

	/**
	 * cookieName에 해당하는 saveId를 반환한다.
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieId(HttpServletRequest request, String cookieName) {
		//saveID 쿠키 확인
		Cookie[] cookie = request.getCookies();
		String saveId = "";
		int cookieFind = -1;
		for (int i=0; i<cookie.length; i++) {
			if (cookie[i].getName().equals(cookieName)) {
				cookieFind = i;
				break;
			}
		}
		
		if (cookieFind != -1) {
			saveId = cookie[cookieFind].getValue();		
		}
		return saveId;
	}
	
	/**
	 * null이 아니고 str.trim()이 ""이 아니면 true
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !"".equals(str.trim()));
	}

	/**
	 * str이 null이면 ""를 리턴
	 * @param str
	 * @return
	 */
	public static String nullCheck(Object str) {
		return str==null ? "" : str.toString().trim();
	}
	
	/**
	 * str이 null이면 replaceStr을 리턴
	 * @param str
	 * @param replaceStr
	 * @return
	 */
	public static String nullCheck(Object str, String replaceStr) {
		if (str == null) {
			return replaceStr;
		} else if (str.toString().trim().equals("")) {
			return replaceStr;
		} else {
			return str.toString().trim();
		}
	}
	
	/**
	 * List에 담긴 내용을 String으로 반환한다.
	 * @param list
	 * @return String
	 */
	public static String listToString(List list) {
		String str = "";
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				str += list.get(i);
			}
		}
		return str;
	}
	
	/**
	 * List에 담긴 내용을 devider로 구분된 String으로 반환한다.
	 * @param list
	 * @param devider : 구분자
	 * @return String
	 */
	public static String listToString(List list, String devider) {
		String str = "";
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				str += list.get(i) + devider;
			}
			str = deleteLast(str, ",");
		}
		return str;
	}
	
	/**
	 * String의 마지막 문자를 제거한다.
	 * @param str
	 * @return String
	 */
	public static String deleteLast(String str) {
		return str.substring(0, str.length()-1);
	}
	/**
	 * String의 마지막 문자가 제시한 문자와 동일하면 제거한다.
	 * @param str
	 * @param delChar : 제거할 마지막 문자
	 * @return String
	 */
	public static String deleteLast(String str, String delChar) {
		if (str == null || str == "") {
			str = "";
		} else {
			if (str.substring(str.length()-delChar.length(), str.length()).equals(delChar)) {
				str = str.substring(0, str.length()-delChar.length());
			}
		}
		return str;
	}
	
	/**
	 * 배열(source)에 담긴 내용이 모두 target으로 바뀐, 쉼표(,)로 구분된 문자열을 반환한다.<br> 
	 * jdbcTemplate 등에서 Where in (?,?,?) 등을 구성할때 사용하면 편리하다.<br>
	 * 예제 :  <br>
	 	String[] keyArr = keys.split(",");<br>
		String inParam = F.arrayChangeTo(keyArr, "?");<br>
		String query = "" +<br>
				"SELECT SEQ, USER_ID, DEPART_ID, NAME " +<br>
				"FROM ecom.dbo.T_USER" +<br>
				"WHERE SEQ in ("+inParam+") ";<br>
		return (List<User>)this.jdbcTemplate.query(query, keyArr, this.userMapper);<br>

	 * @param source
	 * @param target
	 * @return
	 */
	public static String arrayChangeTo(Object[] source, Object target) {
		String str = "";
		for (int i=0; i<source.length; i++) {
			str += target +",";
		}
		str = F.deleteLast(str, ",");
		return str;
	}
	
	/**
	 * Tree 형식의 레이블을 작성할 수 있도록 "/A/B/C"의 String을 "→→C"로 변경한다.
	 * @param source
	 * @param regex
	 * @param changeWord
	 * @param bulletImagePath
	 * @return
	 */
	public static String stringToTree(String source, String regex, String changeWord, String bulletImagePath) {
		String txt = "";
		String[] arr = source.split(regex);
		if (arr.length>0) {
			for (int i=1; i<arr.length; i++){
				txt += changeWord;
			}
			if (!bulletImagePath.equals("")) {
				txt += "<img src='"+ bulletImagePath +"' />";
			}
			txt += arr[arr.length-1];
		}
		return txt;
	}
	
	public static String addZero(int num, int amount) {
		String added = "00000000000000000000"+num;
		added = added.substring(added.length()-amount, added.length());
		return added;
	}
	
	/**
	 * str의 '-'를 기준으로 idx번째 문자를 반환한다. 
	 * @param str
	 * @param idx
	 * @return
	 */
	public static String getSplit(String source, int idx) {
		String str = "";
		if (source!=null) {
			if (source.split("-").length >= (idx+1)) {
				str = source.split("-")[idx];
			}
		}
		return str;
	}

	/**
	 * str을 regex로 분리하여 idx번째 문자를 반환한다.
	 * @param str
	 * @param regex
	 * @param idx
	 * @return
	 */
	public static String getSplit(String source, String regex, int idx) {
		String str = "";
		if (source!=null) {
			if (source.split("-").length >= (idx+1)) {
				str = source.split("-")[idx];
			}
		}
		return str;
	}

	/**
	 * array에서 obj가 몇번째인지를 반환.
	 * @param array
	 * @param obj
	 * @return
	 */
	public static int getArrayIndex(Object[] array, Object obj) {
		int idx = -1;
		for (int i=0; i<array.length; i++) {
			if (array[i] == obj) {
				idx = i;
				break;
			}
		}
		return idx;
	}

	/**
	 * array에서 obj가 몇번째인지를 반환.
	 * @param array
	 * @param obj
	 * @return
	 */
	public static int getArrayIndex(String[] array, String obj) {
		int idx = -1;
		for (int i=0; i<array.length; i++) {
			if (array[i].equals(obj)) {
				idx = i;
				break;
			}
		}
		return idx;
	}
	/**
	 * array에서 obj가 몇번째인지를 반환.
	 * @param array
	 * @param obj
	 * @return
	 */
	public static int getArrayIndex(int[] array, int obj) {
		int idx = -1;
		for (int i=0; i<array.length; i++) {
			if (array[i] == obj) {
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	/**
	 * 전화번호 포맷 설정
	 * @param phoneNo
	 * @return
	 */
	public static String phoneFormat(String phoneNo) {
	  
		if (phoneNo.length() == 0){
			return phoneNo;
		}
	   
		String strTel = phoneNo;
		String[] strDDD = {"02" , "031", "032", "033", "041", "042", "043",
				"051", "052", "053", "054", "055", "061", "062",
				"063", "064", "010", "011", "012", "013", "015",
				"016", "017", "018", "019", "070"};
	  
		if (strTel.length() < 9) {
			return strTel;
		} else if (strTel.substring(0,2).equals(strDDD[0])) {
			strTel = strTel.substring(0,2) + '-' + strTel.substring(2, strTel.length()-4) + '-' + strTel.substring(strTel.length() -4, strTel.length());
		} else {
			for(int i=1; i < strDDD.length; i++) {
				if (strTel.substring(0,3).equals(strDDD[i])) {
					strTel = strTel.substring(0,3) + '-' + strTel.substring(3, strTel.length()-4) + '-' + strTel.substring(strTel.length() -4, strTel.length());
				}
			}
		}
		return strTel;
	}

}
