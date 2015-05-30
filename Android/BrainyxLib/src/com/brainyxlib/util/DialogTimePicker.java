package com.brainyxlib.util;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class DialogTimePicker extends TimePickerDialog{

	public DialogTimePicker(Context context, int theme,OnTimeSetListener callBack, int hourOfDay, int minute,
			boolean is24HourView) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
		if(is24HourView){
			 setTitle("오후" + hourOfDay+"시 " + minute + "분");
		}else{
			 setTitle("오전" + hourOfDay+"시 " + minute + "분");
		}
		
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		super.onTimeChanged(view, hourOfDay, minute);
		
		 setTitle(hourOfDay+"시 " + minute + "분");
		 
	}
}
