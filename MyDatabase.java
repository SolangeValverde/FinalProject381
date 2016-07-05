package com.example.miroslav.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class MyDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);

    }

    public void insertData( String name,String type,String color, String pattern, byte[] image) throws SQLiteException {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERNAME, name);
        cv.put(Constants.HIGHSCORE, type);
        cv.put(Constants.COINS, color);
        cv.put(Constants.ACCESSORYID, pattern);
        db.insert( Constants.DATABASE_NAME, null, cv );

    }

    public Cursor getData() {
        SQLiteDatabase db = helper.getReadableDatabase();


        Cursor cursor = db.rawQuery("Select * FROM " + Constants.TABLE_NAME, null);
        // db.rawQuery("SELECT * FROM permissions_table WHERE name = 'Comics' ", null);
        return cursor;
    }


    public Cursor getDataQuery(String query)
    {
//       Cursor cursor = db.query
//                (
//                        Constants.TABLE_NAME,
//                        new String[] {Constants.NAME, Constants.TYPE, Constants.LOCATION, Constants.LATINNAME },
//                        "*",
//                        null, null, null, null, null
//                );
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM "+Constants.TABLE_NAME+" WHERE "+Constants.USERNAME+"=?", new String[]{query});
        // db.rawQuery("SELECT * FROM permissions_table WHERE name = 'Comics' ", null);
        return cursor;
    }

}
