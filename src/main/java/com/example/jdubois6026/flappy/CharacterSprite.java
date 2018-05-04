package com.example.jdubois6026.flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jdubois6026 on 5/4/2018.
 */

public class CharacterSprite {
    private Bitmap image;

    public CharacterSprite(Bitmap bmp) {
        image = bmp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 100, 100, null);
    }

}
