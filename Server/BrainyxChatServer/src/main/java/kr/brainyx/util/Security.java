package kr.brainyx.util;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	/**
	 * pw를 SHA-256, ASCII로 암호화된 byte[]을 반환
	 * @param pw
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getSha256Ascii(String pw) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		try {
			return digest.digest(pw.getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * pw를 SHA-256, ASCII로 암호화된 String을 반환
	 * @param pw
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSha256(String pw) throws NoSuchAlgorithmException {
		String result = "";
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	 	    digest.reset();
	 	    
	 	    
	 	   byte[] byteData = digest.digest(pw.getBytes("ASCII")); //UTF-8
	 	    StringBuffer sb = new StringBuffer();
	 	    
	 	   for (int i = 0; i < byteData.length; i++){
		 	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		   }

		 		
	 	   result = sb.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
 		
		return result;
	}
	
	public static Charset charset_ASCII = Charset.forName("US-ASCII");
	public static CharsetDecoder decoder_ASCII = charset_ASCII.newDecoder();


	
	public static String convert(byte[] data) {
	    StringBuilder sb = new StringBuilder(data.length);
	    for (int i = 0; i < data.length; ++ i) {
	        //if (data[i] < 0) throw new IllegalArgumentException();
	        sb.append((char) data[i]);
	    }
	    return sb.toString();
	}
}
