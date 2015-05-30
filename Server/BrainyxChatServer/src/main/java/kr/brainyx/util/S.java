package kr.brainyx.util;

public class S {

	/**
	 * 배열에 담긴 Object의 toString()을 ,로 구분해서 콘솔에 출력한다. : new Object[] { obj1, obj2, ... }
	 * @param obj
	 */
	public static void print(Object[] obj) {
		String str = "";
		for (int i=0; i<obj.length; i++) {
			str += obj[i].toString() +",";
		}
		str = F.deleteLast(str, ",");
		System.out.println(str);
	}
	/**
	 * 배열에 담긴 String을 ,로 구분해서 콘솔에 출력한다. : new String[] { "A", "B", ... }
	 * @param obj
	 */
	public static void print(String[] strArr) {
		String str = "";
		for (int i=0; i<strArr.length; i++) {
			str += strArr[i] +",";
		}
		str = F.deleteLast(str, ",");
		System.out.println(str);
	}
	
	
	/**
	 * String을 콘솔에 출력한다. : str
	 * @param str
	 */
	public static void print(Object str) {
		System.out.println(str);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2
	 * @param str
	 */
	public static void print(Object str1, Object str2) {
		System.out.println(str1 +" "+ str2);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3) {
		System.out.println(str1 +" "+ str2 +" "+ str3);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5, Object str6) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5 +" "+ str6);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5, Object str6, Object str7) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5 +" "+ str6 +" "+ str7);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5, Object str6, Object str7, Object str8) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5 +" "+ str6 +" "+ str7 +" "+ str8);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5, Object str6, Object str7, Object str8, Object str9) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5 +" "+ str6 +" "+ str7 +" "+ str8 +" "+ str9);
	}
	/**
	 * String을 콘솔에 출력한다. : str1, str2, str3, str4
	 * @param str
	 */
	public static void print(Object str1, Object str2, Object str3, Object str4, Object str5, Object str6, Object str7, Object str8, Object str9, Object str10) {
		System.out.println(str1 +" "+ str2 +" "+ str3 +" "+ str4 +" "+ str5 +" "+ str6 +" "+ str7 +" "+ str8 +" "+ str9 +" "+ str10);
	}
}
