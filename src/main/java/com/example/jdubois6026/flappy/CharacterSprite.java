package com.example.jdubois6026.flappy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jdubois6026 on 5/4/2018.
 */

public class CharacterSprite {
    private Bitmap image;
    public int x, y;
    private int xVelocity = 10;
    public int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp) {
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }
    
    public void update() {
        y += yVelocity;



    }

}
