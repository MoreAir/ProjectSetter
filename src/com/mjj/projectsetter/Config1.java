package com.mjj.projectsetter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Config1 extends Activity {
	
	private static final String config_tag="CONFIG1";
	private Button nextBt;
	private int year;
	private int month;
	private int day;
	private EditText startTime_Text;
	private EditText endTime_Text;
	private EditText pjName;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config1);
		nextBt=(Button) findViewById(R.id.nextBt);
		startTime_Text=(EditText) findViewById(R.id.startTime_text);
		endTime_Text=(EditText) findViewById(R.id.endTime_text);
		Calendar date=Calendar.getInstance(Locale.CHINA);
		Date nowDate=new Date();
		date.setTime(nowDate);				
		year=date.get(Calendar.YEAR);
		month=date.get(Calendar.MONTH);
		day=date.get(Calendar.DAY_OF_MONTH);
		
		startTime_Text.setText(year+"-"+(month+1)+"-"+day);
		endTime_Text.setText(year+"-"+(month+1)+"-"+day);
		
		setTextViewClickEvent(startTime_Text);
		setTextViewClickEvent(endTime_Text);
		
		nextBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean flag=isNext();
				System.out.println(flag);
				if(flag){
					
				}
			}
		});
		
	}

	public void setTextViewClickEvent(final EditText editText){
		
		editText.setFocusableInTouchMode(false);
		Calendar date=Calendar.getInstance(Locale.CHINA);
		Date nowDate=new Date();
		date.setTime(nowDate);				
		year=date.get(Calendar.YEAR);
		month=date.get(Calendar.MONTH);
		day=date.get(Calendar.DAY_OF_MONTH);
		OnDateSetListener dateListener;
		if(editText.getId()==R.id.startTime_text){
			dateListener=start_dateListener;
		}else{
			dateListener=end_dateListener;
		}
		final DatePickerDialog datePickDialog=new DatePickerDialog(Config1.this,dateListener, year, month, day);
		
		editText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				datePickDialog.show();

			}
		});
	}
	
	
	public DatePickerDialog.OnDateSetListener start_dateListener=new DatePickerDialog.OnDateSetListener() {		
		@Override
		public void onDateSet(DatePicker view, int yearChoose, int monthOfYear,
				int dayOfMonth) {
				year=yearChoose;
				month=monthOfYear;
				day=dayOfMonth;
				startTime_Text.setText(yearChoose+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		}
	};
	
	public DatePickerDialog.OnDateSetListener end_dateListener=new DatePickerDialog.OnDateSetListener() {		
		@Override
		public void onDateSet(DatePicker view, int yearChoose, int monthOfYear,
				int dayOfMonth) {
				year=yearChoose;
				month=monthOfYear;
				day=dayOfMonth;
				endTime_Text.setText(yearChoose+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		}
	};
	
	private void updateTextView(EditText edText,int yearChoose,int monthOfYear,int dayOfMonth){
		System.out.println(edText.getId());
		Calendar date=Calendar.getInstance(Locale.CHINA);
		Date nowDate=new Date();
		date.setTime(nowDate);	
		int yearNow=date.get(Calendar.YEAR);
		int monthNow=date.get(Calendar.MONTH);
		int dayNow=date.get(Calendar.DAY_OF_MONTH);
		edText.setText(yearChoose+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date pickerTime = null;
		try {
			pickerTime = df.parse(edText.getText().toString());
			System.out.println(edText.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(pickerTime.getTime()+" ,"+nowDate.getTime());
		
		if(pickerTime.getTime()<nowDate.getTime()){
			Toast toast=Toast.makeText(getApplicationContext(),"日期不能小于今天！", Toast.LENGTH_SHORT);
				  toast.setGravity(Gravity.BOTTOM, 0, 0);
				  toast.show();
			edText.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
		}else{
			edText.setText(yearChoose+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		}
//		if(edText.getId()==R.id.startTime_text){
//			if(pickerTimeSum<nowDateSum){
//				startSumTime=nowDateSum;
//			}else{
//				startSumTime=pickerTimeSum;
//			}
//		}
//		if(edText.getId()==R.id.endTime_text){
//			if(pickerTimeSum<startSumTime){
//				Toast toast=Toast.makeText(getApplicationContext(),"结束日期不能小于开始日期！", Toast.LENGTH_SHORT);
//				  toast.setGravity(Gravity.BOTTOM, 0, 0);
//				  toast.show();
//				  edText.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
//			}
//		}else{
//			if(pickerTimeSum>endSumTime){
//				Toast toast=Toast.makeText(getApplicationContext(),"开始日期不能大于结束日期！", Toast.LENGTH_SHORT);
//				  toast.setGravity(Gravity.BOTTOM, 0, 0);
//				  toast.show();
//				  edText.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
//			}
//		}
	}
	
	public boolean isNext(){
		boolean flag=true;
		pjName=(EditText) findViewById(R.id.name_text);
		
		Calendar date=Calendar.getInstance(Locale.CHINA);
		Date nowDate=new Date();
		date.setTime(nowDate);	
		int yearNow=date.get(Calendar.YEAR);
		int monthNow=date.get(Calendar.MONTH);
		int dayNow=date.get(Calendar.DAY_OF_MONTH);
		Date startPickerTime = null;
		Date endPickerTime = null;
		long startSumTime=0;
		long endSumTime=0;
		try {
			startPickerTime = df.parse(startTime_Text.getText().toString()+" 23:59:59");
			endPickerTime = df.parse(endTime_Text.getText().toString()+" 23:59:59");
			startSumTime=startPickerTime.getTime();
			endSumTime=endPickerTime.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(startSumTime+" ,"+endSumTime+" ,"+nowDate.getTime());
		if(startSumTime<nowDate.getTime()&&endSumTime<nowDate.getTime()){
			Toast toast=Toast.makeText(getApplicationContext(),"开始和结束日期都不能小于今天！", Toast.LENGTH_SHORT);
			  toast.setGravity(Gravity.BOTTOM, 0, 0);
			  toast.show();
			  startTime_Text.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
			  endTime_Text.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
			  flag=false;
		}
		else if(startSumTime<nowDate.getTime()){
			Toast toast=Toast.makeText(getApplicationContext(),"开始日期不能小于今天！", Toast.LENGTH_SHORT);
				  toast.setGravity(Gravity.BOTTOM, 0, 0);
				  toast.show();
				  startTime_Text.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
				  flag=false;
		}else if(endSumTime<nowDate.getTime()){
			Toast toast=Toast.makeText(getApplicationContext(),"结束日期不能小于今天！", Toast.LENGTH_SHORT);
			  toast.setGravity(Gravity.BOTTOM, 0, 0);
			  toast.show();
			  endTime_Text.setText(yearNow+"-"+(monthNow+1)+"-"+dayNow);
			  flag=false;
		}

		if(endSumTime<startSumTime){
			Toast toast=Toast.makeText(getApplicationContext(),"结束日期不能小于开始日期！", Toast.LENGTH_SHORT);
				  toast.setGravity(Gravity.BOTTOM, 0, 0);
				  toast.show();
				  flag=false;
		}
		String namePJ=pjName.getText().toString();
		if(namePJ.equals("")||namePJ.trim().equals("")){
			Toast toast=Toast.makeText(getApplicationContext(),"标题不能为空或空格字符！", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM, 0, 0);
			toast.show();
			flag=false;
		}
		return flag;
	}
	
}
