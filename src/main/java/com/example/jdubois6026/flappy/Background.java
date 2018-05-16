package com.example.jdubois6026.flappy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jdubois6026 on 5/8/2018.
 */

public class Background {

    private Bitmap image;
    public int x, y;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Background (Bitmap background) {
        image = background;
        x=0;
        y=0;

    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);


    }
    public void update() {

    }

}
