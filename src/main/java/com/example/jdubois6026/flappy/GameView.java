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


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.graphics.Color.WHITE;
import static com.example.jdubois6026.flappy.MainThread.canvas;
import static com.example.jdubois6026.flappy.ScoreText.paint;


/**
 * Created by jdubois6026 on 5/4/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    public ScoreText scoreText;
    private Background backgroundImg;
    public static int gapHeight = 800;
    public int flappyWidth = 240;
    public int flappyHeight = 171;
    public static int velocity = 15;
    public static int score = 0;
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
        characterSprite.setYVelocity();
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        makeLevel();

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

        pipe1 = new PipeSprite(pipe_up, pipe_down, 2000, 100);
        pipe2 = new PipeSprite(pipe_up, pipe_down, 4500, 100);
        pipe3 = new PipeSprite(pipe_up, pipe_down, 3200, 100);
        scoreText = new ScoreText();
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
        logic();
        characterSprite.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
        scoreText.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        backgroundImg = new Background(getResizedBitmap
                (BitmapFactory.decodeResource(getResources(),R.drawable.background), screenWidth, screenHeight));

        super.draw(canvas);
        if(canvas!=null) {
            backgroundImg.draw(canvas);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
            scoreText.draw(canvas);

        }
    }

    public void logic() {

        List<PipeSprite> pipes = new ArrayList<>();
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);


        for (int i = 0; i < pipes.size(); i++) {
            //Detect if the character is touching one of the pipes
            if (characterSprite.y < pipes.get(i).yY + (screenHeight / 2)
                    - (gapHeight / 2) && characterSprite.x + 300 > pipes.get(i).xX
                    && characterSprite.x < pipes.get(i).xX + 500) {
                resetLevel();
            } else if (characterSprite.y + 240 > (screenHeight / 2) +
                    (gapHeight / 2) + pipes.get(i).yY
                    && characterSprite.x + 300 > pipes.get(i).xX
                    && characterSprite.x < pipes.get(i).xX + 500) {
                resetLevel();
            }

            //Detect if the pipe has gone off the left of the
            //screen and regenerate further ahead
            if (pipes.get(i).xX + 500 < 0) {
                Random r = new Random();
                int value2 = r.nextInt(500);
                pipes.get(i).xX = screenWidth + 2000;
                pipes.get(i).yY = value2 - 250;
            }

            /*Detect number of pipes the character has passed, add score for every pipe passed
            if (pipes.get(i).xX == (screenWidth/2)){
                score++;
            }*/

        }


        //Detect if the character has gone off the
        //bottom or top of the screen
        if (characterSprite.y + 240 < 0) {
            resetLevel(); }
        if (characterSprite.y > screenHeight) {
            resetLevel(); }
    }

    public void resetLevel() {
        characterSprite.y = 100;
        score = 0;
        pipe1.xX = 2000;
        pipe1.yY = 0;
        pipe2.xX = 4500;
        pipe2.yY = 200;
        pipe3.xX = 3200;
        pipe3.yY = 250;
        characterSprite.yVelocity = 15;

    }
}
