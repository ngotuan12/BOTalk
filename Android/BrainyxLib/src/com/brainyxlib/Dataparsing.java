package com.brainyxlib;


public class Dataparsing {

	private static Dataparsing instance = null;
	public static interface SetDataCall{
		public void onSetDataCall(int type, String value,String value2);
	}
	public SetDataCall datacallback;
	public synchronized static Dataparsing getInstance() {
		if (instance == null) {
			synchronized (Dataparsing.class) {
				if (instance == null) {
					instance = new Dataparsing();
				}
			}
		}
		return instance;
	}
	
	public String text = "";
	public String imgurl = "";
	public String videourl = "";
	public String videothum = "";
	
	public void setOnDataCall(SetDataCall callback){
		this.datacallback = callback;
	}
	
	public void DataParsing(String data){
		try {
			String[]  values = data.split("]");
			for(int i = 0 ; i < values.length ;i++){
				String[]  values2 = values[i].split(";");
				values2[0] = values2[0].substring(1);
				if(datacallback != null){
					if(Integer.valueOf(values2[0]) == 3){
						datacallback.onSetDataCall(Integer.valueOf(values2[0]), values2[1], values2[2]);	
					}else{
						datacallback.onSetDataCall(Integer.valueOf(values2[0]), values2[1], null);
					}
				}
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
