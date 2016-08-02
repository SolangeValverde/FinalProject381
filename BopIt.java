package com.example.miroslav.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class BopIt extends AppCompatActivity {
    private final java.util.Random rand = new java.util.Random();
    public SensorManager mSensorManager;
    public SensorEventListener sel_gyroscope, sel_light;
    public float xGyro = 0;
    public int score = 0;
    public float xGyroOld;
    private boolean gameOn = false;
    public ImageButton dogBopit;
    public Button startGame;
    public boolean twistIt = false;
    public boolean coverIt = false;
    public boolean bopIt = false;
    public String currentAskedAction;
    public String currentAction;
    public TextView command, points;
    MyDatabase myDBclass;
    SQLiteDatabase myDB;
    MyHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bop_it);

        myDBclass = new MyDatabase(this);

        dbHelper = new MyHelper(this);
        myDB = dbHelper.getWritableDatabase();

        startGame = (Button) findViewById(R.id.startGame);
        dogBopit = (ImageButton) findViewById(R.id.dogBopit);
        command = (TextView) findViewById(R.id.Command);
        points = (TextView) findViewById(R.id.points);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)) {
            Toast.makeText(this, "no Gyroscope Sensor available", Toast.LENGTH_SHORT).show();
        } else {

            sel_gyroscope = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    xGyroOld = xGyro;
                    xGyro = event.values[1];
                    if (Math.abs(xGyro - xGyroOld) > 0.5) {
                        twistIt = true;
                        currentAction = "twistIt";
                        checkIfCorrectAction(twistIt, currentAction);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };

            sel_light = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.values[0] < 10) {
                        coverIt = true;
                        currentAction = "coverIt";
                        checkIfCorrectAction(coverIt, currentAction);
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
        mSensorManager.registerListener(sel_gyroscope, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 9999);
        mSensorManager.registerListener(sel_light, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), 9999);

        dogBopit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bopIt = true;
                currentAction = "bopIt";
                checkIfCorrectAction(bopIt, currentAction);

            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                begin();

            }
        });
    }


    private void commandRandomAdder() {
        //Typically not called directly as it does not create the iterator for verifying the user's input
        int deviceButtonIndex = rand.nextInt(3);
        if (deviceButtonIndex == 1){
            command.setText("COVER IT!!");
            currentAskedAction = "coverIt";

        }
        else if(deviceButtonIndex==2){
            command.setText("SHAKE IT!!");
            currentAskedAction = "twistIt";
        }
        else {
            command.setText("BOP IT!!");
            currentAskedAction = "bopIt";
        }
    }

    public void checkIfCorrectAction(boolean fromAction, String str){

        if ( gameOn && currentAskedAction == str && fromAction){
            // gimme coins or something
            score = score + 20;
            //points.append(Integer.toString(score));
            points.setText("Points: "+Integer.toString(score));
            commandRandomAdder();
        }
        else{
            boolean errorMade = true;
            Vibrator v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1000);
            endGame(errorMade);
        }

    }

    protected void begin() {
        /* Start the game, start generating random notes and watching the keys to match to the playList */
        if (gameOn==false) {
            score = 0;
            gameOn = true;
            commandRandomAdder();
            command.setBackgroundColor(Color.rgb(188,247,198));
        }
    }

     void endGame(boolean errorMade) {
        /* Start the game, start generating random notes and watching the keys to match to the playList */
        if (gameOn==true && errorMade== true ) {
            Log.d("ERROR MADE???\n", "why you no turn red?!!?!??!");
            gameOn = false;
            command.setBackgroundColor(Color.rgb(235, 117,117));
            command.setText("Game Over :(");

            String finalScore = points.getText().toString();
            String[] separated = finalScore.split(" ");
            //separated[0]; // this will contain "Fruit"
            String finalCoins =separated[1];


            Intent iin= getIntent();
            Bundle b = iin.getExtras();
            if(b!=null) {
                String username = (String) b.get("user");

                if (dbHelper.getHighScoreG1(username)!= "" || dbHelper.getHighScoreG1(username)!= null) {
                    String currentHS = dbHelper.getHighScoreG1(username);
                    if (Integer.parseInt(finalCoins) > Integer.parseInt(currentHS)) {
                        dbHelper.updateHighScoreG1(username, finalCoins, currentHS);
                    }


                    String coins = dbHelper.getCoins(username);
                    int newCoinAmt = Integer.parseInt(coins) + Integer.parseInt(finalCoins);
                    Log.d("iiafyalfbsal\n", Integer.toString(newCoinAmt));

                    dbHelper.updateCoins(username,Integer.toString(newCoinAmt), coins);
                }


            }

            // set score as final score;
            // if final score highest score, set high score
        }
    }

}


