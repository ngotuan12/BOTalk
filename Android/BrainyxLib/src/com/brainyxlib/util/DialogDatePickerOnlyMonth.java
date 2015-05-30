package com.brainyxlib.util;

import java.lang.reflect.Field;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

public class DialogDatePickerOnlyMonth extends DatePickerDialog {

	 public DialogDatePickerOnlyMonth(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
        // 일, 월 선택 스피너 제거
        try {
            Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField.getName())
                                || "mYearPicker".equals(datePickerField.getName())
                                || "mYearSpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
 
//        if (TITLE != null)
            setTitle((monthOfYear+1)+"월");
	}

	private String TITLE = null;
	 
	    public DialogDatePickerOnlyMonth(Context context,
	            OnDateSetListener callBack, int year, int monthOfYear,
	            int dayOfMonth) {
	        super(context, callBack, year, monthOfYear, dayOfMonth);
	 
	        // 일, 월 선택 스피너 제거
	        try {
	            Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
	            for (Field datePickerDialogField : datePickerDialogFields) {
	                if (datePickerDialogField.getName().equals("mDatePicker")) {
	                    datePickerDialogField.setAccessible(true);
	                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);
	                    Field datePickerFields[] = datePickerDialogField.getType()
	                            .getDeclaredFields();
	                    for (Field datePickerField : datePickerFields) {
	                        if ("mDayPicker".equals(datePickerField.getName())
	                                || "mDaySpinner".equals(datePickerField.getName())
	                                || "mYearPicker".equals(datePickerField.getName())
	                                || "mYearSpinner".equals(datePickerField.getName())) {
	                            datePickerField.setAccessible(true);
	                            Object dayPicker = new Object();
	                            dayPicker = datePickerField.get(datePicker);
	                            ((View) dayPicker).setVisibility(View.GONE);
	                        }
	                    }
	                }
	            }
	        } catch (Exception ex) {
	        }
	 
//	        if (TITLE != null)
	            setTitle((monthOfYear+1)+"월");
	    }
	     
	    public void setFixedTitle(String title) {
	        TITLE = title;
	        setTitle(TITLE);
	    }
	 
	    @Override
	    public void onDateChanged(DatePicker view, int year, int month, int day) {
	        super.onDateChanged(view, year, month, day);
	         
//	        if (TITLE != null)
	        	setTitle((month+1)+"월");
	    }
}
