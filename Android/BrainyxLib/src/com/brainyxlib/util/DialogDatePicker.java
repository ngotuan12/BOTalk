package com.brainyxlib.util;

import java.lang.reflect.Field;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

public class DialogDatePicker extends DatePickerDialog {

	 public DialogDatePicker(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		  setTitle(year+"년 " + (monthOfYear+1) + "월"+dayOfMonth+"일");
		  
	}

	private String TITLE = null;
	 
	    public DialogDatePicker(Context context, OnDateSetListener callBack, int year, int monthOfYear,
	            int dayOfMonth) {
	        super(context, callBack, year, monthOfYear, dayOfMonth);
	        
	        try {
	            Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
	            for (Field datePickerDialogField : datePickerDialogFields) {
	                if (datePickerDialogField.getName().equals("mDatePicker")) {
	                    datePickerDialogField.setAccessible(true);
	                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);
	                    Field datePickerFields[] = datePickerDialogField.getType()
	                            .getDeclaredFields();
	                /*    for (Field datePickerField : datePickerFields) {
	                        if ("mDayPicker".equals(datePickerField.getName())
	                                || "mDaySpinner".equals(datePickerField.getName())) {
	                            datePickerField.setAccessible(true);
	                            Object dayPicker = new Object();
	                            dayPicker = datePickerField.get(datePicker);
	                            ((View) dayPicker).setVisibility(View.GONE);
	                        }
	                    }*/
	                }
	            }
	        }
	        	catch (IllegalArgumentException e) {
	        	 e.printStackTrace();
	        	}
	        	catch (IllegalAccessException e) {
	        	 e.printStackTrace();
	        	}
	        
//	        if (TITLE != null)
	            setTitle(year+"년 " + (monthOfYear+1) + "월" + dayOfMonth+"일");
	    }
	     
	    public void setFixedTitle(String title) {
	        TITLE = title;
	        setTitle(TITLE);
	    }
	 
	    @Override
	    public void onDateChanged(DatePicker view, int year, int month, int day) {
	        super.onDateChanged(view, year, month, day);
	         
//	        if (TITLE != null)
	        setTitle(year+"년 " + (month+1) + "월");
	    }
}
