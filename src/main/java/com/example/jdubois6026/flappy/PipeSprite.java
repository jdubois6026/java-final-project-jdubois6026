package com.example.jdubois6026.flappy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jdubois6026 on 5/8/2018.
 */

public class PipeSprite {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public PipeSprite (Bitmap pipe_up, Bitmap pipe_down, int x, int y) {
        image = pipe_up;
        image2 = pipe_down;
        yY = y;
        xX = x;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xX, -(GameView.gapHeight / 2) + yY, null);
        canvas.drawBitmap(image2,xX, ((screenHeight / 2) + (GameView.gapHeight / 2)) + yY, null);


    }
    public void update() {

        xX -= GameView.velocity;
    }

}
