package com.brainyxlib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRequestor {
	 public static final String CRLF = "\r\n";
     
     private URL targetURL;
     
     private ArrayList list;
     
     public HttpRequestor(URL target) {
         this(target, 20);
     }
     
     /**
      * HttpRequest
      * 
      * @param target HTTPURL
      */
     public HttpRequestor(URL target, int initialCapicity) {
         this.targetURL = target;
         this.list = new ArrayList(initialCapicity);
     }
     
    /**
     * @param parameterName 
     * @param parameterValue 
     * @exception IllegalArgumentException parameterValue媛 null
     */
    public void addParameter(String parameterName, String parameterValue) {
        if (parameterValue == null) 
        throw new IllegalArgumentException("parameterValue can't be null!");
        
        list.add(parameterName);
        list.add(parameterValue);
    }
    
    public boolean getParameter(String parameterName){
    	for(int i = 0 ; i <list.size(); i++){
    		if(list.get(i).equals(parameterName)){
    			return true;    			
    		}
    	}
    	return false;
    }
    
    /**
     * @param parameterName 
     * @param parameterValue 
     * @exception IllegalArgumentException parameterValue媛 null
     */
    public void addFile(String parameterName, File parameterValue) {
    	
        if (parameterValue == null) {
            list.add(parameterName);
            list.add(new NullFile());
        } else {
            list.add(parameterName);
            list.add(parameterValue);
        }
    }
    
    /**
     * @return InputStream
     */
    public InputStream sendGet() throws IOException {
        String paramString = null;
        if (list.size() > 0)
            paramString = "?" + encodeString(list);
        else
            paramString = "";
        
        URL url = new URL(targetURL.toExternalForm() + paramString);
        
        URLConnection conn = url.openConnection();
        
        return conn.getInputStream();
    }
    
    /**
     * @return InputStream
     */
    public InputStream sendPost() throws IOException {
        String paramString = null;
        if (list.size() > 0)
            paramString = encodeString(list);
        else
            paramString = "";
        
        HttpURLConnection conn = (HttpURLConnection)targetURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(paramString);
            out.flush();
        } finally {
            if (out != null) out.close();
        }
        return conn.getInputStream();
    }
    
    public InputStream sendMultipartPost() throws IOException {
    	
        HttpURLConnection conn = (HttpURLConnection)targetURL.openConnection();
        
        // Delimeter 
        String delimeter = makeDelimeter();        
        byte[] newLineBytes = CRLF.getBytes();
        byte[] delimeterBytes = delimeter.getBytes();
        byte[] dispositionBytes = "Content-Disposition: form-data; name=".getBytes();
        byte[] quotationBytes = "\"".getBytes();
        byte[] contentTypeBytes = "Content-Type: application/octet-stream".getBytes();
        byte[] fileNameBytes = "; filename=".getBytes();
        byte[] twoDashBytes = "--".getBytes();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                                "multipart/form-data; boundary="+delimeter);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(conn.getOutputStream());
            
            Object[] obj = new Object[list.size()];
            list.toArray(obj);
            
            for (int i = 0 ; i < obj.length ; i += 2) {
                out.write(twoDashBytes);
                out.write(delimeterBytes);
                out.write(newLineBytes);
                out.write(dispositionBytes);
                out.write(quotationBytes);
                out.write( ((String)obj[i]).getBytes() );
                out.write(quotationBytes);
                if ( obj[i+1] instanceof String) {
                    out.write(newLineBytes);
                    out.write(newLineBytes);
                    out.write( ((String)obj[i+1]).getBytes() );
                    out.write(newLineBytes);
                } else {
                    if ( obj[i+1] instanceof File) {
                        File file = (File)obj[i+1];
                        out.write(fileNameBytes);
                        out.write(quotationBytes);
                        out.write(file.getName().getBytes());
                        out.write(quotationBytes);
                    } else {
                        out.write(fileNameBytes);
                        out.write(quotationBytes);
                        out.write(quotationBytes);
                    }
                    out.write(newLineBytes);
                    out.write(contentTypeBytes);
                    out.write(newLineBytes);
                    out.write(newLineBytes);
                    if (obj[i+1] instanceof File) {
                    		  File file = (File)obj[i+1];
                    		  if(file.getPath() ==null || file.getPath().equals("")){//추가
                    			  byte[] buffers = "noimg".toString().getBytes();//추가
                    			  //byte[] buffers = new byte[1024 * 8];//추가
                    			  BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(buffers));
      								int len = -1;//추가
      								while ((len = is.read(buffers)) != -1) {//추가
      									out.write(buffers, 0, len);//추가
      								}//추가
                    		  }else{
                    			  BufferedInputStream is = null;
                                  try {
                                      is = new BufferedInputStream(
                                               new FileInputStream(file));
                                      byte[] fileBuffer = new byte[1024 * 8]; // 8k
                                      int len = -1;
                                      while ( (len = is.read(fileBuffer)) != -1) {
                                          out.write(fileBuffer, 0, len);
                                      }
                                  } finally {
                                      if (is != null) try { is.close(); } catch(IOException ex) {}
                                  }
                    		  }
                    	}
                    out.write(newLineBytes);
                } 
                if ( i + 2 == obj.length ) {
                    out.write(twoDashBytes);
                    out.write(delimeterBytes);
                    out.write(twoDashBytes);
                    out.write(newLineBytes);
                }
            }
            
            out.flush();
        } finally {
        	String serverResponseMessage = conn.getResponseMessage();
			Log.e("serverResponseMessage", "serverResponseMessage"
					+ serverResponseMessage);
			out.close();
        }
        return conn.getInputStream();
    }
    
    
    
    private static String makeDelimeter() {
        return "---------------------------7d115d2a20060c";
    }
    
    private static String encodeString(ArrayList parameters) {
        StringBuffer sb = new StringBuffer(256);
        
        Object[] obj = new Object[parameters.size()];
        parameters.toArray(obj);
        
        for (int i = 0 ; i < obj.length ; i += 2) {
            if ( obj[i+1] instanceof File || obj[i+1] instanceof NullFile ) continue;            
            sb.append(URLEncoder.encode((String)obj[i]) );
            sb.append('=');
            sb.append(URLEncoder.encode((String)obj[i+1]) );            
            if (i + 2 < obj.length) sb.append('&');
        }
        
        return sb.toString();
    }
    
    private class NullFile {
        NullFile() {
        }
        public String toString() {
            return "";
        }
    }
    /**
     * 
     * @param is
     * @return
     */
    public static String UploadcheckObject(InputStream is) {
		String check = "";
		String data = null;
		JSONObject object;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				object = new JSONObject(builder.toString());
				data = object.getString("result");
						
				
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
    /**
     * 
     * @param is
     * @return
     */
    public static String Uploadcheckjson(InputStream is) {
		String check = "";
		String data = null;
		JSONObject object;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				JSONArray array = null;
				JSONObject obj = new JSONObject(builder.toString());
				//array = new JSONArray(builder.toString());
				data = obj.getString("result");
				
			
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 
	 * @param is
	 * @return
	 */
	public static String UploadcheckString(InputStream is) {
		String check = "";
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			check = builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return check;

	}
	
	public static String getEndcoding(String str) {
		String contentText2 = "";
		try {
			contentText2 = java.net.URLEncoder.encode(new String(str.toString().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return contentText2;
	}
	
}
