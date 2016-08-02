package com.example.miroslav.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.util.Log;

public class MyHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    // Table create statement
    private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE " + Constants.TABLE_NAME
            + "("+ Constants.USERNAME + " TEXT," + Constants.HIGHSCORE + " TEXT," +
             Constants.HIGHSCORE2 + " TEXT,"+Constants.COINS + " TEXT,"+ Constants.ACCESSORYID + " TEXT);";



    private Context context;

    public MyHelper(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USER_INFO_TABLE);
            //Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }

    }

    public Cursor testConnection(SQLiteDatabase db){
        String testQuery = "SELECT COUNT(*) FROM "+ Constants.TABLE_NAME;

       Cursor testTest = db.rawQuery(testQuery,null);
        //Log.d("SOL", testTest.toString());
        return testTest;
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

    public boolean updateCoins(String rowId,String newCoins, String oldCoins)
    {
        ContentValues args = new ContentValues();
        args.put(Constants.COINS, newCoins);
        String where = Constants.COINS + " = ?";
        String[] whereArgs = {oldCoins};
        int i =  db.update(Constants.TABLE_NAME, args, where, whereArgs);
        return i > 0;
    }

    public String getCoins(String rowId)
    {
        db = getReadableDatabase();
        String query = "SELECT * FROM "+Constants.TABLE_NAME+" WHERE "+Constants.USERNAME+" = '" + rowId+ "' ";

        Cursor c1 =db.rawQuery(query, null);

        String coins = "100";
        int colAcc = c1.getColumnIndex(Constants.COINS);
        Log.d("\n sol \n", Integer.toString(colAcc));
        if (c1!= null) {
            if (c1.moveToFirst() && colAcc ==3) {
                coins = c1.getString(colAcc);
                }
            return coins;
        }else {

            Log.d( "\n sol \n", "el cursor es nulo");
        }
        return coins;
    }
    public boolean updateAccesory(String rowId,String accesory)
    {
        ContentValues args = new ContentValues();
        args.put(Constants.ACCESSORYID, accesory);
        //String where = Constants.USERNAME + " = " + rowId;
        String where = Constants.ACCESSORYID + " = ?";
        String[] whereArgs = {getAccesory(rowId)};
        int i =  db.update(Constants.TABLE_NAME, args, where, whereArgs);
        return i > 0;
    }

    public String getAccesory(String rowId)
    {
        db = getReadableDatabase();
        Cursor c2 = testConnection(db);

        String query = "SELECT * FROM "+Constants.TABLE_NAME+" WHERE "+Constants.USERNAME+" = '" + rowId+ "' ";
        Log.d("-", query);
        Cursor c1 =db.rawQuery(query, null);

        String remiAccesory= "0";
        int colAcc = c1.getColumnIndex(Constants.ACCESSORYID);
        Log.d("\n sol \n", Integer.toString(colAcc));
        if (c1!= null && colAcc == 4) {
            //remiAccesory = c1.getString(c1.getColumnIndex(Constants.ACCESSORYID));
            if (c1.moveToFirst()) {

                remiAccesory = c1.getString(colAcc);
            }
            return remiAccesory;
        }else {

            Log.d( "\n sol \n", "el cursor es nulo");
        }
        return remiAccesory;
    }
    public boolean updateHighScore(String rowId,String hs1, String hs2)
    {
        ContentValues args = new ContentValues();
        args.put(Constants.USERNAME, rowId);
        args.put(Constants.HIGHSCORE, hs1);
        args.put(Constants.HIGHSCORE2, hs2);
        int i =  db.update(Constants.TABLE_NAME, args, Constants.USERNAME + "=" + rowId, null);
        return i > 0;
    }

    public Cursor getData() {
        db = getReadableDatabase();


        Cursor cursor = db.rawQuery("Select * FROM " + Constants.TABLE_NAME, null);
        // db.rawQuery("SELECT * FROM permissions_table WHERE name = 'Comics' ", null);
        return cursor;
    }
}


