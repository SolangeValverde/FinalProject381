package com.example.miroslav.finalproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class BopIt extends AppCompatActivity {
    private final java.util.Random rand = new java.util.Random();
    private final byte MESSAGE_ROUND_WIN = 0; //for boardHost.gameMessage(MESSAGE_CODE)
    private final byte MESSAGE_LOSE = 1;
    public SensorManager mSensorManager;
    public SensorEventListener sel_gyroscope, sel_light;
    public float xGyro = 0;
    public float xGyroOld;
    public dogGame1 dog;
    private boolean gameOn = false;
    public boolean twistIt = false;
    public boolean coverIt = false;
    public boolean bopIt = false;
    private List<Byte> playList;
    private BoardHost boardHost;
    private ScoreObservable scoreObservable = new ScoreObservable();
    private byte[] indexButtons;
    private Iterator playListIterator; //used to verify user is playing melody correctly
    public TextView command;
    final int PLAY_DURATION_MS = 600; // How long the computer presses the buttons during playback
    final int PAUSE_DURATION_MS = 200; // How long the computer pauses between playback button presses
    private final Handler handlerPlay = new Handler();
    private List<View> simonButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bop_it);

        command = (TextView) findViewById(R.id.Command);
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
                    if (Math.abs(xGyro - xGyroOld)>0.5){
                        twistIt = true;
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
                        bopIt=true;
                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
        mSensorManager.registerListener(sel_gyroscope, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 9999);
    }


    protected void begin() {
        /* Start the game, start generating random notes and watching the keys to match to the playList */
        gameOn = true;
        scoreObservable.sendRequest(ScoreObservable.RESET_SCORE);
        playList = new ArrayList<>();
        playListIterator = playList.iterator();
        noteRandomAdd();
        boardHost.play(playList);
    }

    public void Collission(final byte indexButton) {
        if (!gameOn) return; //Simon ignores the button press if the game isn't in progress.
        if (verifyCorrectAnswer(indexButton)) {

            scoreObservable.sendRequest(ScoreObservable.INCREMENT_SCORE);
            if (!hasNext()) {
                //No more buttons to press, at the end of the melody!! Next round.
                scoreObservable.sendRequest(ScoreObservable.INCREMENT_ROUND);
                try {
                    boardHost.gameMessage(MESSAGE_ROUND_WIN);
                    //Toast.makeText(parentActivity, messageRoundWin, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.w("sol\n", "Unable to send Round Win Message");
                }
                noteRandomAdd();
                boardHost.play(playList);
            }
        } else {
            //Incorrect key press, the game is over.
            try {
                boardHost.gameMessage(MESSAGE_LOSE);
            } catch (Exception e) {
                Log.w("sol\n", "Unable to send Game Lost Message");
            } finally {
                boardHost.gameOver();
                gameOn = false;
            }
        }
    }

    private void noteRandomAdder() {
        //Typically not called directly as it does not create the iterator for verifying the user's input
        int deviceButtonIndex = rand.nextInt(3); // 3 options: Bop it, twist it and cover it
        Log.v("s\n", "deviceButtonIndex: " + deviceButtonIndex);
        playList.add(indexButtons[deviceButtonIndex]);
    }
    private boolean hasNext() {
        //Return true if the current melody iterator has any more notes, false otherwise
        try {
            return playListIterator.hasNext();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean play(List<Byte> playList) {
            /* In: List of byte objects (notes) to play
            Returns false if the melody won't play, true otherwise */
        int delayMultiplier = 1;
        if (playList == null) return false;
        for (final Byte buttonIndex : playList) {
            handlerPlay.postDelayed(new Runnable() {
                public void run() {
                    playOne(buttonIndex);
                }
            }, delayMultiplier * (PLAY_DURATION_MS + PAUSE_DURATION_MS));
            delayMultiplier++; //Need this multiplier so that played back notes are consecutive in time
        }
        return true;
    }

    public void playOne(byte buttonIndex) {

            /* convert the buttonIndex supplied by Simon to the actual View corresponding to that index.
            * Press the button (passive XML properties will make the COMMAND SHOW) */
        final View VW = simonButtons.get(buttonIndex);
        if(buttonIndex == 1) {
            command.setText("BOP IT!!");
        }else if (buttonIndex==2){
            command.setText("TWIST IT!!");
        }else {
            command.setText("COVER IT!!");
        }
    }

    @SuppressWarnings("unused")
    protected synchronized void noteRandomAdd(final int numberToAdd) {
        //Use this method to add Random buttons in bulk.
        for (int i = 0; i < numberToAdd; i++) {
            noteRandomAdder();
        }
        playListIterator = playList.iterator();
    }

    protected synchronized void noteRandomAdd() {
        noteRandomAdder();
        playListIterator = playList.iterator();
    }

    @SuppressWarnings("unused")
    protected void setPlayList(List<Byte> myPlayList) {
        this.playList = myPlayList;
    }
    boolean verifyCorrectAnswer(final byte indexButton) {
        /* Verify the button pressed by the user - is it part of the melody?
        The verification is very simple, just a comparison. We check .hasNext to make sure the user doesn't
        type too many keys - automatic fail during the game.
        Return true if part of melody, false otherwise */
        return playListIterator.hasNext() && indexButton == (Byte) playListIterator.next();
    }


    static interface BoardHost {
        public void gameOver();

        public boolean play(List<Byte> playList);

        @SuppressWarnings("unused")
        public void playOne(byte buttonIndex);

        public void gameMessage(byte MESSAGE_CODE);
    }
    public class ScoreObservable extends Observable {
        /* A request for a score bar update, to pass to any observers that wish to know the score
         * The ScoreBarUpdate will always have one of the listed values
         * sample usage: scoreObservable.requestCode(scoreObservable.INCREMENT_SCORE) to tell Observers that score has changed. */
        final static byte RESET_SCORE = 1;
        final static byte INCREMENT_ROUND = 2;
        final static byte INCREMENT_SCORE = 4;
        private byte requestCode;

        byte getRequest() {
            return requestCode;
        }

        void sendRequest(final byte REQUEST_CODE) {
            if (REQUEST_CODE == RESET_SCORE || REQUEST_CODE == INCREMENT_ROUND || REQUEST_CODE == INCREMENT_SCORE) {
                this.requestCode = REQUEST_CODE;
                if (REQUEST_CODE == INCREMENT_SCORE){

                    //TO DO: add coins and update db
                }
                setChanged();
                notifyObservers();
            }
        }
    }
}


