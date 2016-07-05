package com.example.miroslav.finalproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyHelper extends SQLiteOpenHelper {


    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + Constants.DATABASE_NAME
            + "("+ Constants.USERNAME + " TEXT," + Constants.HIGHSCORE + " TEXT," +
            Constants.COINS + " TEXT,"+ Constants.ACCESSORYID + " TEXT);";

    private Context context;

    public MyHelper(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_IMAGE);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

            // create new table
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}


