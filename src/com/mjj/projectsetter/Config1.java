package com.mjj.projectsetter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.mjj.dao.Project;
import com.mjj.dao.ProjectSetterDAO;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
	private EditText pjComment;
	private EditText rdstartT;
	private EditText rdendT;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private DateFormat tsdfdate = new SimpleDateFormat("hh:mm");
	private Calendar date=Calendar.getInstance(Locale.CHINA);
	
	private ProjectSetterDAO pjDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config1);
		pjDAO=new ProjectSetterDAO(this);
		nextBt=(Button) findViewById(R.id.nextBt);
		
		startTime_Text=(EditText) findViewById(R.id.startTime_text);
		endTime_Text=(EditText) findViewById(R.id.endTime_text);
		
		rdstartT=(EditText) findViewById(R.id.remind_startTime_Text);
		rdendT=(EditText) findViewById(R.id.remind_endTime_Text);
		
		Date nowDate=new Date();
		date.setTime(nowDate);				
		year=date.get(Calendar.YEAR);
		month=date.get(Calendar.MONTH);
		day=date.get(Calendar.DAY_OF_MONTH);
		
		startTime_Text.setText(year+"-"+(month+1)+"-"+day);
		endTime_Text.setText(year+"-"+(month+1)+"-"+day);
		
		setDateTextViewClickEvent(startTime_Text);
		setDateTextViewClickEvent(endTime_Text);
		
		setTimeEditTextClickEvet(rdstartT);
		setTimeEditTextClickEvet(rdendT);
		
		nextBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean flag=isNext();
				if(flag){
					Project pj=new Project();
					pj.setName(pjName.getText().toString());
					pjComment=(EditText) findViewById(R.id.comment_text);
					pj.setComment(pjComment.toString());
					try {
						Timestamp startTsp =Timestamp.valueOf(startTime_Text.getText().toString()+" 00:00:00");
						pj.setStartTime(startTsp);
						Timestamp endTsp=Timestamp.valueOf(endTime_Text.getText().toString()+" 59:59:59");
						pj.setEndTime(endTsp);
						pj.setRemindStartTime(rdstartT.getText().toString());
						pj.setRemindEndTime(rdendT.getText().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}					
					pjDAO.add(pj);
					Intent intent=new Intent(Config1.this,MainActivity.class);
					startActivity(intent);
				}
			}
		});
		
	}

	public void setTimeEditTextClickEvet(final EditText et){
		
		et.setFocusableInTouchMode(false);
		
		date.setTimeInMillis(System.currentTimeMillis());
		int hour=date.get(Calendar.HOUR_OF_DAY);
		int minute=date.get(Calendar.MINUTE);
		final TimePickerDialog timePickerDialog=new TimePickerDialog(Config1.this, new OnTimeSetListener() {			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minuteIn) {
				date.setTimeInMillis(System.currentTimeMillis());
				date.set(Calendar.HOUR_OF_DAY, hourOfDay);
				date.set(Calendar.MINUTE, minuteIn);
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);
				et.setText(hourOfDay+":"+minuteIn);
			}
		}, hour, minute, true);
		
		et.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timePickerDialog.show();
			}
		});
		
		
}

	
	public void setDateTextViewClickEvent(final EditText editText){
		
		editText.setFocusableInTouchMode(false);

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
	
	public boolean isNext(){
		boolean flag=true;
		pjName=(EditText) findViewById(R.id.name_text);
		
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
