package kr.brainyx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MtB {
	
	/**
	 * "1,2,3,4"나 "호랑이,고양이,사자,퓨마"와 같이 쉼표로 구분된 문자열들을 랜덤으로 섞을 수 있도록 index를 반환한다.
	 * @param src
	 * @return
	 */
	public static int[] makeRandomIndex(String src) {
		String[] source = src.split(",");
		
		int tmp = 0;
		int size = source.length;
		int[] result = new int[size];
		Random rnd = new Random();

		for(int i=0;i<size;i++){
			result[i]= i;
		}

		for(int i=0;i<size;i++){
			int des =rnd.nextInt(size);
			tmp = result[i];
			result[i]=result[des];
			result[des]=tmp;
		}
		
		return result;
	}

	/**
	 * List를 랜덤으로 섞어서 반환한다.
	 * @param list
	 * @return
	 */
	public static List makeRandomList(List list) {
		
		Object tmp = 0;
		int size = list.size();
		List result = new ArrayList();
		Random rnd = new Random();

		for(int i=0;i<size;i++){
			result.add(i, list.get(i));
		}

		for(int i=0;i<size;i++){
			int des =rnd.nextInt(size);
			tmp = result.get(i);
			result.set(i, result.get(des));
			result.set(des, tmp);
		}
		
		return result;
	}
	
	
	/**
	 * 랜덤한 문자열을 원하는 길이만큼 반환합니다.
	 * 
	 * @param length 문자열 길이
	 * @return 랜덤문자열
	 */
	public static String getRandomString(int length)
	{
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		 
		String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9".split(",");
		 
		for (int i=0 ; i<length ; i++) {
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}
	
	/**
	 * 랜덤한 문자열을 원하는 길이만큼 반환합니다. : 대문자, 숫자
	 * 
	 * @param length 문자열 길이
	 * @return 랜덤문자열
	 */
	public static String getRandomStringUpperNum(int length)
	{
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		 
		String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9".split(",");
		 
		for (int i=0 ; i<length ; i++) {
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}
	
}
