package com.example.miroslav.finalproject;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LovePet extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener, View.OnDragListener{
    Remi remi;
    String remiAcc;
    AnimationDrawable frameAnimation;
    MediaPlayer mp;
    ImageButton remiButton, foodMenu, foodb1, backButton, menub1, menub2, menub3, menub4, menub5, menub6, menub7, menub8, menub9;
    ImageView animation;
    ImageButton foodb2, foodb3, foodb4, foodb5;
    Button money;
    Accessory remisAccessory;
    FrameLayout menuFrame;
    LinearLayout foodMenuLayout;
    boolean menuOff = true;
    boolean menuFoodOff = true;
    MyDatabase myDBclass;
    SQLiteDatabase myDB;
    MyHelper dbHelper;
   // CreateUser createU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_pet);
        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        ImageButton foodButton = (ImageButton) findViewById(R.id.menu_food_button);
        myDBclass = new MyDatabase(this);

         dbHelper = new MyHelper(this);
        myDB = dbHelper.getWritableDatabase();



        backButton = (ImageButton) findViewById(R.id.backBtn);
        remiButton = (ImageButton) findViewById(R.id.img_Remi);
        remiButton.setBackgroundResource(R.drawable.remi1);
        animation = (ImageView) findViewById(R.id.animation);
        animation.setBackgroundResource(R.drawable.animation);
        money = (Button) findViewById(R.id.coins);




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

        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (menuFoodOff) {
                    // menuFoodOff = false;
                    menuFoodOnOff(menuFoodOff);
                } else {
                    //menuOff = true;
                    menuFoodOnOff(menuFoodOff);
                }
            }
        });
        foodMenuLayout = (LinearLayout) findViewById(R.id.foodMenu);
        foodb1 = (ImageButton) findViewById(R.id.foodb1);
        foodb2 = (ImageButton) findViewById(R.id.foodb2);
        foodb3 = (ImageButton) findViewById(R.id.foodb3);
        foodb4 = (ImageButton) findViewById(R.id.foodb4);
        foodb5 = (ImageButton) findViewById(R.id.foodb5);
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


        foodb1.setOnTouchListener(this);
        foodb2.setOnTouchListener(this);
        foodb3.setOnTouchListener(this);
        foodb4.setOnTouchListener(this);
        foodb5.setOnTouchListener(this);
        remiButton.setOnDragListener(this);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if (b!=null){
            String username = (String) b.get("user");
            if (dbHelper.getAccesory(username)!= "" ||dbHelper.getAccesory(username)!= null) {
                String acc = dbHelper.getAccesory(username);

                int resID = getResources().getIdentifier(acc , "drawable", getPackageName());
                remisAccessory.setImage(resID);
            }

            if (dbHelper.getCoins(username)!= "" ||dbHelper.getCoins(username)!= null) {
                money.setText(dbHelper.getCoins(username));
                Log.d("SOL\n" ,dbHelper.getCoins(username));
            }
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

    public void menuFoodOnOff(boolean boolMenuOff) {
        if (boolMenuOff) {
            foodMenuLayout.setVisibility(View.GONE);
            foodb1.setVisibility(View.GONE);
            foodb2.setVisibility(View.GONE);
            foodb3.setVisibility(View.GONE);
            foodb4.setVisibility(View.GONE);
            foodb5.setVisibility(View.GONE);
            menuFoodOff = false;
        } else {
            foodMenuLayout.setVisibility(View.VISIBLE);
            foodb1.setVisibility(View.VISIBLE);
            foodb2.setVisibility(View.VISIBLE);
            foodb3.setVisibility(View.VISIBLE);
            foodb4.setVisibility(View.VISIBLE);
            foodb5.setVisibility(View.VISIBLE);
            menuFoodOff = true;
        }
    }


    @Override
    public boolean onDrag(View v, DragEvent event){
        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                ImageButton view = (ImageButton) event.getLocalState();
                if (view.getId() == R.id.foodb1 || view.getId() == R.id.foodb5 || view.getId() == R.id.foodb3) {

                    try{
                        String coins = money.getText().toString();
                        int newCoinAmt = Integer.parseInt(coins) - 20;
                        Intent iin= getIntent();
                        Bundle b = iin.getExtras();
                        if(b!= null){
                            String username = (String) b.get("user");;
                            dbHelper.updateCoins(username,Integer.toString(newCoinAmt), coins);
                            money.setText(Integer.toString(newCoinAmt));
                        }
                    }catch (Exception e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                if (view.getId() == R.id.foodb2 || view.getId() == R.id.foodb4) {

                    try {
                        String coins = money.getText().toString();
                        int newCoinAmt = Integer.parseInt(coins) - 30;
                        Intent iin = getIntent();
                        Bundle b = iin.getExtras();
                        if (b != null) {
                            String username = (String) b.get("user");
                            ;
                            dbHelper.updateCoins(username, Integer.toString(newCoinAmt), coins);
                            money.setText(Integer.toString(newCoinAmt));
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                frameAnimation.stop();
                frameAnimation.start();
                break;
        }
        return true;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    public void caseButtonsAction(Intent iin){
        Bundle b = iin.getExtras();
        if(b!= null){
            String username = (String) b.get("user");
            dbHelper.updateAccesory(username, remiAcc);
        }
        menuOff = true;
        menuOnOff(menuOff);

    }

    public void onClick(View v) {
        Intent iin= getIntent();

        switch (v.getId()) {

            case R.id.backBtn:
                Bundle bu = iin.getExtras();
                if(bu!=null) {
                    String username = (String) bu.get("user");
                    Intent intent = new Intent(this, MainActivity.class).putExtra("user", username);
                    startActivity(intent);
                }
                break;
            case R.id.menu_button1:
                remisAccessory.setImage(0);
                remiAcc = "0";
                menuOff = true;
                menuOnOff(menuOff);
                caseButtonsAction(iin);
                break;
            case R.id.menu_button2:
                remisAccessory.setImage(R.drawable.remicollar3);
                remiAcc = "remicollar3";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button3:
                remisAccessory.setImage(R.drawable.remicollar4);
                remiAcc = "remicollar4";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button4:
                remisAccessory.setImage(R.drawable.remicollar2);
                remiAcc = "remicollar2";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button5:
                remisAccessory.setImage(R.drawable.remicollar1);
                remiAcc = "remicollar1";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button6:
                remisAccessory.setImage(R.drawable.remibandana4);
                remiAcc = "remibandana4";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button7:
                remisAccessory.setImage(R.drawable.remibandana2);
                remiAcc = "remibandana2";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button8:
                remisAccessory.setImage(R.drawable.remibandana1);
                remiAcc = "remibandana1";
                caseButtonsAction(iin);
                break;
            case R.id.menu_button9:
                remisAccessory.setImage(R.drawable.remibandana3);
                remiAcc = "remibandana3";
                caseButtonsAction(iin);
                break;

            default:
                break;

        }
    }
}


///////////////////////////////////////////////////////////////////////////////