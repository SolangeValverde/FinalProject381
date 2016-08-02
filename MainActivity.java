package com.example.miroslav.finalproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Remi remi;
    AnimationDrawable frameAnimation;
    MediaPlayer mp;
    ImageButton loveButton, remiButton,feedButton, brushButton;
    ImageView animation;
    Accessory remisAccessory;
    Intent in;
    MyDatabase myDBclass;
    SQLiteDatabase myDB;
    CreateUser createU;
    PickUser pickU;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBclass = new MyDatabase(this);

        MyHelper dbHelper = new MyHelper(this);
        myDB = dbHelper.getWritableDatabase();

        Intent iin= getIntent();
        in = iin;
        Bundle b = iin.getExtras();

        username = iin.getStringExtra("user");

        loveButton = (ImageButton) findViewById(R.id.loveButton);
        feedButton = (ImageButton) findViewById(R.id.feedButton);
        brushButton = (ImageButton) findViewById(R.id.brushButton);
        remiButton = (ImageButton) findViewById(R.id.img_Remi);
        remiButton.setBackgroundResource(R.drawable.remi1);
        animation = (ImageView) findViewById(R.id.animation);
        animation.setBackgroundResource(R.drawable.animation);
        //remiAccessory_imgView= (ImageView) findViewById(R.id.accessory);//
        frameAnimation = (AnimationDrawable) animation.getBackground();
        remi = new Remi(0, 0, 0, this);
        remisAccessory = new Accessory(0, 0, 0, this);
        mp = MediaPlayer.create(this, R.raw.woof);
        remi.draw();
        remisAccessory.draw();
        remiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });

        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,LovePet.class).putExtra("accessoryOn", remisAccessory.IDACC);
                //Intent oldintent = new Intent();
                if (in.hasExtra("user")) {//coming from query
                    //String username = oldintent.getStringExtra("user");
                    Intent intent = new Intent(MainActivity.this, LovePet.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent oldintent = new Intent();
                if (in.hasExtra("user")) {//coming from query
                   // String username = oldintent.getStringExtra("user");
                    Intent intent = new Intent(MainActivity.this,Game1.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }
            }
        });
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent oldintent = new Intent();
                if (in.hasExtra("user")) {//coming from query
                    //String username = oldintent.getStringExtra("user");
                    Intent intent = new Intent(MainActivity.this,Game2.class);
                    intent.putExtra("user", username);
                    startActivity(intent);
                }

            }
        });



        if (b!=null){
            String username = (String) b.get("user");
            if (dbHelper.getAccesory(username)!= null) {
                String acc = dbHelper.getAccesory(username);

                int resID = getResources().getIdentifier(acc , "drawable", getPackageName());
                remisAccessory.drawMain(resID);
            }
        }
    }

    // Called when Activity becomes visible or invisible to the user
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Starting the animation when in Focus
            frameAnimation.start();
        } else {
            // Stoping the animation when not in Focus
            frameAnimation.stop();
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loveButton:
                //Do something
                break;

            default:
                break;

        }
    }
}
