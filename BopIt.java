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

public class BopIt extends AppCompatActivity implements View.OnClickListener {
    private final java.util.Random rand = new java.util.Random();
    public SensorManager mSensorManager;
    public SensorEventListener sel_gyroscope, sel_light, sel_accelerometer;
    public float xGyro = 0;
    public float yGyro = 0;
    public float zGyro = 0;
    public int score = 0;
    public float xGyroOld, yGyroOld, zGyroOld;
    private boolean gameOn = false;
    public ImageButton dogBopit, backBtn;
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
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        points = (TextView) findViewById(R.id.points);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE) && !pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)) {
            Toast.makeText(this, "no Gyroscope Sensor and/or Light Sensor available", Toast.LENGTH_SHORT).show();
        } else {

            sel_gyroscope = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    xGyroOld = xGyro;
                    yGyroOld = yGyro;
                    zGyroOld = zGyro;
                    xGyro = event.values[1];
                    yGyro = event.values[0];
                    zGyro = event.values[2];
                    Log.d("X GYROSCOPE\n", String.valueOf(xGyro));
                    if (Math.abs(xGyro - xGyroOld) > 1 || Math.abs(yGyro - yGyroOld) > 1 || Math.abs(zGyro - zGyroOld) > 1) {
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
                    Log.d(" light\n", String.valueOf(event.values[0]));
                    if (event.values[0] < 12) {
                        Log.d("X light\n", String.valueOf(event.values[0]));
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


        backBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent iin = getIntent();
        Bundle bu = iin.getExtras();
        if (bu != null) {
            String username = (String) bu.get("user");
            Intent intent = new Intent(this, MainActivity.class).putExtra("user", username);
            startActivity(intent);
        }
    }


    private void commandRandomAdder() {
        //Typically not called directly as it does not create the iterator for verifying the user's input
        int deviceButtonIndex = rand.nextInt(3);
        if (deviceButtonIndex == 1){
            command.setText("COVER IT!!");
            currentAskedAction = "coverIt";
            dogBopit.setBackgroundDrawable(getResources().getDrawable(R.drawable.coveritdog));

        }
        else if(deviceButtonIndex==2){
            command.setText("SHAKE IT!!");
            currentAskedAction = "twistIt";
            dogBopit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shakeitdog));

        }
        else {
            command.setText("BOP IT!!");
            currentAskedAction = "bopIt";
            dogBopit.setBackgroundDrawable(getResources().getDrawable(R.drawable.bopitdog));

        }
    }

    public void checkIfCorrectAction(boolean fromAction, String str){

        if ( gameOn && currentAskedAction == str && fromAction){
            // gimme coins or something
            score = score + 40;
            //points.append(Integer.toString(score));
            points.setText("Points: "+Integer.toString(score));
            commandRandomAdder();
        } else if (gameOn && currentAskedAction != str) {
            boolean errorMade = true;
            if (gameOn) {
                Vibrator v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(1000);
            }
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

        }
    }

}


