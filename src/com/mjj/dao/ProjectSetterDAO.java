package com.mjj.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProjectSetterDAO {
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	private static final String TAG="DAO";
	
	public ProjectSetterDAO(Context context){
		helper=new DBHelper(context);
		db=helper.getWritableDatabase();
	}

	public void add(Project pj){
		
		db.beginTransaction();
		
		Object [] param=new Object[]{
				pj.name,pj.comment,pj.startTime,pj.endTime,pj.remindStartTime,pj.remindEndTime,pj.state,
				pj.custom1,pj.custom2,pj.custom3};
		try{
			db.execSQL("INSERT INTO PROJECT VALUES(NULL,?,?,?,?,?,?,?,?,?,?)",param);	
			db.setTransactionSuccessful();
		}catch(Exception e){
			Log.e(TAG,e.getMessage());
		}finally{
			db.endTransaction();
		}
	}
	
	public void delete(Project pj){
		db.delete("PROJECT", "PK=?", new String []{pj.pk.toString()});
	}
	
	public Cursor queryPjCursor(){
		Cursor cursor=db.rawQuery("SELECT * FROM PROJECT",null);
		return cursor;
	}
	
	public List<Project> query(){
		
		ArrayList<Project> pjs=new ArrayList<Project>();
		Cursor cursor=queryPjCursor();
		while (cursor.moveToNext()) {
			Project pj=new Project();
			pj.pk=cursor.getInt(cursor.getColumnIndex("PK"));
			pj.name=cursor.getString(cursor.getColumnIndex("NAME"));
			pj.comment=cursor.getString(cursor.getColumnIndex("COMMENT"));
			pj.startTime=Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("STARTTIME")));
			pj.endTime=Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("ENDTIME")));
			pj.remindStartTime=Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("REMIND_STARTTIME")));
			pj.remindEndTime=Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("REMIND_ENDTIME")));
			pj.state=cursor.getString(cursor.getColumnIndex("STATE"));
			pj.custom1=cursor.getString(cursor.getColumnIndex("CUSTOM1"));
			pj.custom2=cursor.getString(cursor.getColumnIndex("CUSTOM2"));
			pj.custom1=cursor.getString(cursor.getColumnIndex("CUSTOM3"));
			pjs.add(pj);
		}
		cursor.close();
		return pjs;
	}
	
	public void closeDB(){
		db.close();
	}
}
