package com.example.mattbook_pro.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder . Callback {
    public BoardView(Context context) {

        super(context);

        // Notify the SurfaceHolder to receive SurfaceHolder callbacks
        getHolder().addCallback(this);
        setFocusable(true);
    }

    Board b;
    BoardThread bt;

    Bitmap xB = BitmapFactory.decodeResource(getResources(),R.drawable.x);
    Bitmap oB = BitmapFactory.decodeResource(getResources(),R.drawable.o);

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Construct initial game state and launch animator thread
        b = new Board(getWidth(), getHeight(),xB,oB);
        bt = new BoardThread(this);
        bt.start();
    }

    public void draw(Canvas c) {
        c.drawColor(Color.BLACK);
        //Draw the aliens and player
        b.draw(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Respond to surface changes and aspect ratio changes.

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Stop the thread by interrupting it
        bt.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Update game state in response to events
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // Touch square 1
                if(x>0 && x<getWidth()/3 && y>getHeight()/2 - getWidth()/2 && y<getHeight()/2 - getWidth()/2 + getWidth()/3){
                    Board.setPlayer(0,0);
                }

                // Touch square 2
                if(x>getWidth()/3 && x<2*getWidth()/3 && y>getHeight()/2 - getWidth()/2 && y<getHeight()/2 - getWidth()/2 + getWidth()/3){
                    Board.setPlayer(1,0);
                }

                // Touch square 3
                if(x>2*getWidth()/3 && x<getWidth() && y>getHeight()/2 - getWidth()/2 && y<getHeight()/2 - getWidth()/2 + getWidth()/3){
                    Board.setPlayer(2,0);
                }

                // Touch square 4
                if(x>0 && x<getWidth()/3 && y>getHeight()/2 - getWidth()/2 + getWidth()/3 && y<getHeight()/2 - getWidth()/2 + 2*getWidth()/3){
                    Board.setPlayer(0,1);
                }

                // Touch square 5
                if(x>getWidth()/3 && x<2*getWidth()/3 && y>getHeight()/2 - getWidth()/2 + getWidth()/3 && y<getHeight()/2 - getWidth()/2 + 2*getWidth()/3){
                    Board.setPlayer(1,1);
                }

                // Touch square 6
                if(x>2*getWidth()/3 && x<getWidth() && y>getHeight()/2 - getWidth()/2 + getWidth()/3 && y<getHeight()/2 - getWidth()/2 + 2*getWidth()/3){
                    Board.setPlayer(2,1);
                }

                // Touch square 7
                if(x>0 && x<getWidth()/3 && y>getHeight()/2 - getWidth()/2 + 2*getWidth()/3 && y<getHeight()/2 - getWidth()/2 + getWidth()){
                    Board.setPlayer(0,2);
                }

                // Touch square 8
                if(x>getWidth()/3 && x<2*getWidth()/3 && y>getHeight()/2 - getWidth()/2 + 2*getWidth()/3 && y<getHeight()/2 - getWidth()/2 + getWidth()){
                    Board.setPlayer(1,2);
                }

                // Touch square 9
                if(x>2*getWidth()/3 && x<getWidth() && y>getHeight()/2 - getWidth()/2 + 2*getWidth()/3 && y<getHeight()/2 - getWidth()/2 + getWidth()){
                    Board.setPlayer(2,2);
                }
        }
        return true;
    }
}
