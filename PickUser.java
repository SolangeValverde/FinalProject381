package com.example.miroslav.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class PickUser extends AppCompatActivity {
    public static EditText usernameEditView, passwordEditView;
    public static Button logon;
    public static final String DEFAULT = "not available";
    public String u, p;
    MyDatabase db;
    SimpleCursorAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_user);
        usernameEditView = (EditText) findViewById(R.id.retrUsername);
        passwordEditView = (EditText) findViewById(R.id.retrPassword);
        logon = (Button) findViewById(R.id.retrButton);
        logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieve(v);
            }
        });
        usernameEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (error) {
                    usernameEditView.setText("");
                    passwordEditView.setText("");
                    usernameEditView.setTextColor(Color.BLACK);
                    passwordEditView.setTextColor(Color.BLACK);
                    error = false;
                }
            }
        });
        passwordEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (error) {
                    Toast.makeText(getApplicationContext(), "ASDSA", Toast.LENGTH_SHORT).show();
                    usernameEditView.setText("");
                    passwordEditView.setText("");
                    usernameEditView.setTextColor(Color.BLACK);
                    passwordEditView.setTextColor(Color.BLACK);
                    error = false;
                }
            }
        });

    }

    public boolean error = false;

    public void CreateUser(View view){
        Toast.makeText(this, "Create User", Toast.LENGTH_LONG).show();
        Intent intent= new Intent(this, CreateUser.class);
        startActivity(intent);
    }

    public void retrieve(View view) {
        db = new MyDatabase(this);
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", DEFAULT);
        String password = sharedPrefs.getString("password", DEFAULT);
        if (username.equals(usernameEditView.getText().toString()) && password.equals(passwordEditView.getText().toString())) {
            Toast.makeText(this, "Data retrieve success", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Welcome back " + username + " " + password, Toast.LENGTH_LONG).show();
            usernameEditView.setText(username);
            passwordEditView.setText(password);

          /*  String[] fromColumns = {Constants.NAME, Constants.TYPE, Constants.LOCATION, Constants.LATINNAME};
            int[] toViews = {R.id.plantNameEntry, R.id.plantTypeEntry,R.id.plantLocationEntry,R.id.plantLatinNameEntry }; // The TextView in simple_list_item_1
*/
            Cursor c = null;
            try {/*
                if (intent.hasExtra("query")){//coming from query
                    String queryResult = intent.getStringExtra("query");*/
                    Toast.makeText(this, "pre loop", Toast.LENGTH_SHORT).show();
                    c = db.getData();
                    String id[] = new String[c.getCount()];
                    int i = 0;
                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        do {
                            id[i] = c.getString(c.getColumnIndex(Constants.ACCESSORYID));
                            i++;
                        } while (c.moveToNext());
//                c.close();
                    }
            } catch (Exception e) {
                Log.d("here", e.toString());
            }



            Intent intent2 = new Intent(this, MainActivity.class);
            intent2.putExtra("user", username);
            startActivity(intent2);
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
            usernameEditView.setTextColor(Color.RED);
            passwordEditView.setTextColor(Color.RED);
            error = true;
        }
    }
}
