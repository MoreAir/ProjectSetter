package com.mjj.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME="project_setter.db";
	private static final Integer DATABASE_VERSION=1;
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		
	}
	
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS PROJECT (" +
				"PK INTEGER PRIMARY KEY AUTOINCREMENT," +
				"NAME VARCHAR," +
				"COMMENT TEXT," +
				"STARTTIME TIMESTAMP," +
				"ENDTIME TIMESTAMP," +
				"REMIND_STARTTIME VARCHAR," +
				"REMIND_ENDTIME VARCHAR," +
				"STATE VARCHAR," +
				"CUSTOM1 TEXT," +
				"CUSTOM2 TEXT," +
				"CUSTOM3 TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
