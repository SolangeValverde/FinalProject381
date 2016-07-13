package com.example.miroslav.finalproject;

import android.app.Activity;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Remi {
    public int xPos, yPos, xVel, yVel;
    public ImageButton remi_button;
    public Accessory accessory;
    public Activity activity;

    public Remi(int xPos, int yPos, int xVel, Activity _activity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.activity = _activity;
        remi_button = (ImageButton) this.activity.findViewById(R.id.img_Remi);
    }


    boolean collisionBrush(ImageView other) {
        return ((Math.abs(xPos - other.getX()) < (int) ((other.getWidth()/ 2))) &&
                (Math.abs(yPos - other.getY()) < (int) ((other.getHeight()/ 2))));
    }
    public void draw() {
        remi_button.setX(xPos);
        remi_button.setY(yPos);
    }

}

