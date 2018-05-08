package com.example.jdubois6026.flappy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;



/**
 * Created by jdubois6026 on 5/4/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    public static int gapHeight = 500;
    public static int velocity = 10;
    public PipeSprite pipe1, pipe2, pipe3;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread =  new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public boolean onTouchEvent(MotionEvent event){
        characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 10);
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.flappysprite));

        thread.setRunning(true);
        thread.start();

    }

    private void makeLevel() {
        characterSprite = new CharacterSprite(getResizedBitmap
                (BitmapFactory.decodeResource(getResources(),R.drawable.flappysprite), 300, 240));
        Bitmap pipe_up;
        Bitmap pipe_down;
        int y;
        int x;
        pipe_up = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe_down), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        pipe_down = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe_up), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        pipe1 = new PipeSprite(pipe_up, pipe_down, 0, 2000);
        pipe2 = new PipeSprite(pipe_up, pipe_down, -250, 3200);
        pipe3 = new PipeSprite(pipe_up, pipe_down, 250, 4500);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        characterSprite.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
    }

    @Override
    public void draw(Canvas canvas)
    {

        super.draw(canvas);
        if(canvas!=null) {
            canvas.drawRGB(0, 100, 205);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);

        }
    }
}
