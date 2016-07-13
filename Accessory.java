package com.example.miroslav.finalproject;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by miroslav on 02/07/16.
 */
public class Accessory {
    public int xPos, yPos, xVel, yVel, IDACC;
    public ImageView remi_accessory;
    public Drawable img_remi_accessory;


    public Activity activity;

    public Accessory(int xPos, int yPos, int xVel ,Activity _activity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.activity = _activity;
        remi_accessory = (ImageView) this.activity.findViewById(R.id.accessory);
    }

    public void setImage(int imageId){
        remi_accessory.setBackgroundResource(imageId);
    }

    public void draw() {
        remi_accessory.setX(xPos);
        remi_accessory.setY(yPos);
    }
    public void remiSendId(int resID){
        IDACC=resID;
    }

    public void drawMain(int resID) {
        remiSendId(resID);
        remi_accessory.setImageResource(resID);
        remi_accessory.setX(xPos);
        remi_accessory.setY(yPos);
    }


}
