package com.example.mattbook_pro.tictactoe;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class BoardThread extends Thread{

    BoardView bv;

    public BoardThread(BoardView bv){
        this.bv = bv;
    }

    public void run(){
        SurfaceHolder sh = bv.getHolder();

        // Main game loop
        while(!Thread.interrupted()){
            Canvas c = sh.lockCanvas(null);
            try{
                synchronized(sh){
                    bv.draw(c);
                }
            }

            catch (Exception e){

            }

            finally{
                if(c!=null){
                    sh.unlockCanvasAndPost(c);
                }
            }

            // Set the frame rate
            try{
                Thread.sleep(0);
            }

            catch (InterruptedException e){
                // Thread was interrupted while sleeping
                return;
            }
        }
    }
}
