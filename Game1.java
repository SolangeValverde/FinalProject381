package com.example.miroslav.finalproject;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Game1 extends AppCompatActivity{
dogGame1 dog;
    TextView gyroInfo;
   ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8,iv9,iv10;

    public SensorManager mSensorManager;
    public SensorEventListener sel_gyroscope;
    public float xGyro;
    public RelativeLayout layout;
    Random r = new Random();
    ArrayList<String> panel1 = new ArrayList<>();
    ArrayList<objectGame1> objects = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        Context c = Game1.this;
        layout = (RelativeLayout) findViewById(R.id.relLayout);

        gyroInfo = (TextView) findViewById(R.id.gyroInfo);


        final ImageView doge = new ImageView(this);

       // final ImageView picture = new ImageView(this);

        final ImageView[]  IMGS = { iv1, iv2, iv3,iv4,iv5,iv6,iv7,iv8,iv9,iv10 };


        dog = new dogGame1(200,800,0);
        for (int j = 0; j<10; j++) {
            IMGS[j] = new ImageView(this);
            objects.add(new objectGame1(r.nextInt(800),r.nextInt(200),3));

        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)) {
            Toast.makeText(this, "no Gyroscope Sensor available", Toast.LENGTH_SHORT).show();
        } else {
            //dog.draw(layout, doge);
            for (int i = 0; i<objects.size(); i++){
                objectGame1 ob = objects.get(i) ;
               // ob.draw(layout, IMGS[i] );
                //ob.update(IMGS[i]);

                // dog.collision(ob);
            }
            sel_gyroscope = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    xGyro=event.values[1];
                    gyroInfo.setText(((Float.toString(xGyro * 100/2))));
                    doge.post(
                            new Runnable(){
                                @Override
                                public void run() {
                                    dog.update(xGyro, doge);
                                    for (int i = 0; i<objects.size(); i++){
                                        objectGame1 ob = objects.get(i) ;
                                        ob.update(IMGS[i]);
                                        if(ob.yPos>dog.yPos + 24){
                                            objects.remove(objects.get(i));
                                            ob.update(IMGS[i]);

                                            //Log.d("SOL \n", "(obs) "+objects.size());

                                        }

                                        if (dog.collision(dog, ob)){
                                            objects.remove(i);
                                            Log.d("SOOOOOL \n", "i  "+ i);
                                            Log.d("SOOOOOL \n", "arraylist  "+objects.size());
                                        }
                                        // dog.collision(ob);
                                    }

                                }
                            });
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
        mSensorManager.registerListener(sel_gyroscope, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 9999);
    }

    void updateGame(){


    }
}

//###############################################################################
//dog that moves with gyroscope
//###############################################################################
 class dogGame1 {

     public float xPos, yPos, xVel, yVel, newGyro;
     public dogGame1(int xPos, int yPos, int xVel) {
         this.xPos = xPos;
         this.yPos = yPos;
         this.xVel = xVel;

     }

     public void update(float xGyro, ImageView doge){
         newGyro = xGyro * 100/2;
         xPos = newGyro +xPos;
         doge.setX(xPos);

         if (doge.getX() <60 ){
             doge.setX(59f);
         }else if(doge.getX() >800){
             doge.setX(799f);
         }

     }
     //*layout.addView(picture);

     boolean collision(dogGame1 doge, objectGame1 other) {
//         return ((Math.abs(doge.getX() - other.xPos) < (int) ((other.owidth/ 2))) &&
//                 (Math.abs(doge.getY() - other.yPos) < (int) ((other.oheight/ 2))));
         return ((Math.abs(doge.xPos - other.xPos) < (int) ((other.owidth/ 2))) &&
                 (Math.abs(doge.yPos - other.yPos) < (int) ((other.oheight/ 2))));
     }

    boolean collisionButton(Button other) {;
        return ((Math.abs(xPos - other.getX()) < (int) ((other.getWidth()/ 2))) &&
                (Math.abs(yPos - other.getY()) < (int) ((other.getHeight()/ 2))));
    }

     public void draw(LinearLayout l, ImageView doge) {
         doge.setImageResource(R.drawable.object2);
         doge.setX(xPos);
         doge.setY(yPos);
         l.addView(doge);
     }

}
//###############################################################################
//falling objects
//###############################################################################

class objectGame1 {

    public float xPos, yPos, xVel, yVel,owidth, oheight;

    public objectGame1(int xPos, int yPos, int yVel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.yVel = yVel;
    }

 public void update(ImageView picture){
     yPos = yPos+yVel;
     picture.setY(yPos);
     owidth = picture.getWidth();
     oheight = picture.getHeight();

     //Log.d("Sol\n", "HEREEEEE    " + yPos+ "   " + yVel);


 }
public void destroy(objectGame1 ob){
    //to do:
    ob = null;
}
    public void draw(TableLayout l, ImageView picture) {
        picture.setImageResource(R.drawable.object1);
        picture.setX(xPos);
        picture.setY(yPos);
        l.addView(picture);

    }

}