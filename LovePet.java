package com.example.miroslav.finalproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LovePet extends AppCompatActivity implements View.OnClickListener  {
    Remi remi;
    AnimationDrawable frameAnimation;
    MediaPlayer mp;
    ImageButton remiButton, backButton,menub1, menub2, menub3, menub4, menub5, menub6, menub7, menub8, menub9;
    ImageView animation;
    Accessory remisAccessory;
    FrameLayout menuFrame;
    boolean menuOff = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_pet);
        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);

        backButton = (ImageButton) findViewById(R.id.backBtn);
                remiButton = (ImageButton) findViewById(R.id.img_Remi);
                remiButton.setBackgroundResource(R.drawable.remi1);
                animation = (ImageView) findViewById(R.id.animation);
                animation.setBackgroundResource(R.drawable.animation);

        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (menuOff) {
                    menuOff = false;
                    menuOnOff(menuOff);
                } else {
                    menuOff = true;
                    menuOnOff(menuOff);
                }
            }
        });
        menuFrame = (FrameLayout) findViewById(R.id.frameLayout2);
        menub1 = (ImageButton) findViewById(R.id.menu_button1);
        menub2 = (ImageButton) findViewById(R.id.menu_button2);
        menub3 = (ImageButton) findViewById(R.id.menu_button3);
        menub4 = (ImageButton) findViewById(R.id.menu_button4);
        menub5 = (ImageButton) findViewById(R.id.menu_button5);
        menub6 = (ImageButton) findViewById(R.id.menu_button6);
        menub7 = (ImageButton) findViewById(R.id.menu_button7);
        menub8 = (ImageButton) findViewById(R.id.menu_button8);
        menub9 = (ImageButton) findViewById(R.id.menu_button9);
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

                backButton.setOnClickListener(this);


        menub1.setOnClickListener(this);
        menub2.setOnClickListener(this);
        menub3.setOnClickListener(this);
        menub4.setOnClickListener(this);
        menub5.setOnClickListener(this);
        menub6.setOnClickListener(this);
        menub7.setOnClickListener(this);
        menub8.setOnClickListener(this);
        menub9.setOnClickListener(this);

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

    public void menuOnOff(final boolean boolMenuOff) {
        if (boolMenuOff) {
            menuFrame.setVisibility(View.GONE);
            menub1.setVisibility(View.GONE);
            menub2.setVisibility(View.GONE);
            menub3.setVisibility(View.GONE);
            menub4.setVisibility(View.GONE);
            menub5.setVisibility(View.GONE);
            menub6.setVisibility(View.GONE);
            menub7.setVisibility(View.GONE);
            menub8.setVisibility(View.GONE);
            menub9.setVisibility(View.GONE);
        } else {
            menuFrame.setVisibility(View.VISIBLE);
            menub1.setVisibility(View.VISIBLE);
            menub2.setVisibility(View.VISIBLE);
            menub3.setVisibility(View.VISIBLE);
            menub4.setVisibility(View.VISIBLE);
            menub5.setVisibility(View.VISIBLE);
            menub6.setVisibility(View.VISIBLE);
            menub7.setVisibility(View.VISIBLE);
            menub8.setVisibility(View.VISIBLE);
            menub9.setVisibility(View.VISIBLE);
        }
    }

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.backBtn:
                        //Do something
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_button1:
                        remisAccessory.setImage(0);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button2:
                        remisAccessory.setImage(R.drawable.remicollar3);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button3:
                        remisAccessory.setImage(R.drawable.remicollar4);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button4:
                        remisAccessory.setImage(R.drawable.remicollar2);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button5:
                        remisAccessory.setImage(R.drawable.remicollar1);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button6:
                        remisAccessory.setImage(R.drawable.remibandana4);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button7:
                        remisAccessory.setImage(R.drawable.remibandana2);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button8:
                        remisAccessory.setImage(R.drawable.remibandana1);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;
                    case R.id.menu_button9:
                        remisAccessory.setImage(R.drawable.remibandana3);
                        menuOff = true;
                        menuOnOff(menuOff);
                        break;

                    default:
                        break;

                }
            }
        }
