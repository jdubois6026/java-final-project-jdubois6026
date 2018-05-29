package com.example.jdubois6026.flappy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.example.jdubois6026.flappy.MainThread.canvas;

/**
 * Created by jdubois6026 on 5/8/2018.
 */

public class ScoreText {

    static Paint paint = new Paint();
    private static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


    public ScoreText () {
    }


    public void draw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText(Integer.toString(GameView.score), screenWidth/2, 500, paint);

    }

    public static void update() {

    }
}
