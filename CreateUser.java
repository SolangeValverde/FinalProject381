package com.example.miroslav.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUser extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        usernameEditText = (EditText)findViewById(R.id.editTextUsername);
        passwordEditText = (EditText)findViewById(R.id.editTextPassword);
        //check if any default thing left

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", DEFAULT);
        String password = sharedPreferences.getString("password", DEFAULT);

        if (!username.equals(DEFAULT) && !password.equals(DEFAULT)){
            Toast.makeText(this, "user data exists, loading login page...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PickUser.class);
            startActivity(intent);
        }

    }

    public void submit (View view){

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username", usernameEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        Toast.makeText(this, "Username and password saved to Preferences", Toast.LENGTH_LONG).show();
        editor.commit();
    }

    public void gotoActivity2(View view){
        Toast.makeText(this, "Pick User", Toast.LENGTH_LONG).show();
        Intent intent= new Intent(this, PickUser.class);
        startActivity(intent);
    }



}