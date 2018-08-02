package com.example.mattbook_pro.tictactoe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    int width, height;
    private int BoardStartX, BoardStartY;
    private int BoardEndX, BoardEndY;
    private int x1,x2,y1,y2;
    static int[][] array = new int[3][3];
    private Rect BoardRect;
    private static int PlayerTurn;
    private int[][][] CenterArray = new int[3][3][4];
    static boolean end;
    private int x1win,x2win,x3win,y1win,y2win,y3win;
    private static int filled;
    private static boolean draw;
    private int xWinner, yWinner;
    private int xPA, yPA;
    private static ArrayList<Integer> undoArray1;
    private static ArrayList<Integer> undoArray2;
    private static boolean AI;
    private TTT TTTB = new TTT();
    static boolean AIStart;
    Bitmap xImage, oImage;

    // Point class
    class Point{
        int x, y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    // AI algorithm
    class TTT {
        List<Point> availablePoints;
        Point computersMove;

        public TTT() {
        }

        // Check if X wins
        public boolean hasXWon() {
            if ((array[0][0] == array[1][1] && array[0][0] == array[2][2] && array[0][0] == 1) || (array[0][2] == array[1][1] && array[0][2] == array[2][0] && array[0][2] == 1)) {
                return true;
            }
            for (int i = 0; i < 3; ++i) {
                if (((array[i][0] == array[i][1] && array[i][0] == array[i][2] && array[i][0] == 1) || (array[0][i] == array[1][i] && array[0][i] == array[2][i] && array[0][i] == 1))) {
                    return true;
                }
            }
            return false;
        }

        // Check if O wins
        public boolean hasOWon() {
            if ((array[0][0] == array[1][1] && array[0][0] == array[2][2] && array[0][0] == 2) || (array[0][2] == array[1][1] && array[0][2] == array[2][0] && array[0][2] == 2)) {
                return true;
            }
            for (int i = 0; i < 3; ++i) {
                if ((array[i][0] == array[i][1] && array[i][0] == array[i][2] && array[i][0] == 2) || (array[0][i] == array[1][i] && array[0][i] == array[2][i] && array[0][i] == 2)) {
                    return true;
                }
            }

            return false;
        }

        // Return open positions
        public List<Point> getAvailableStates() {
            availablePoints = new ArrayList<>();
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (array[i][j] == 0) {
                        availablePoints.add(new Point(i, j));
                    }
                }
            }
            return availablePoints;
        }

        // Place a move
        public void placeAMove(Point point, int player) {
            array[point.x][point.y] = player;
        }

        // Minimax algorithm
        public int minimax(int depth, int turn) {
            if (hasXWon()) {
                return -1;
            }
            if (hasOWon()) {
                return +1;
            }

            List<Point> pointsAvailable = getAvailableStates();
            if (pointsAvailable.isEmpty()) {
                return 0;
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < pointsAvailable.size(); ++i) {
                Point point = pointsAvailable.get(i);
                if (turn == 1) {
                    placeAMove(point, 2);
                    int currentScore = minimax(depth + 1, 0);
                    max = Math.max(currentScore, max);

                    if (currentScore >= 0) {
                        if (depth == 0) {
                            computersMove = point;
                        }
                    }

                    if (currentScore == 1) {
                        array[point.x][point.y] = 0;
                        break;
                    }

                    if (i == pointsAvailable.size() - 1 && max < 0) {
                        if (depth == 0) {
                            computersMove = point;
                        }
                    }
                } else if (turn == 0) {
                    placeAMove(point, 1);
                    int currentScore = minimax(depth + 1, 1);
                    min = Math.min(currentScore, min);
                    if (min == -1) {
                        array[point.x][point.y] = 0;
                        break;
                    }
                }

                array[point.x][point.y] = 0;
            }
            return turn == 1 ? max : min;
        }
    }

    public Board(int width, int height, Bitmap x_image, Bitmap o_image) {
        // Board space
        BoardStartX = 0;
        BoardStartY = height / 2 - width / 2;
        BoardEndX = BoardStartX + width;
        BoardEndY = BoardStartY + width;
        BoardRect = new Rect(BoardStartX, BoardStartY, BoardEndX, BoardEndY);

        // Board lines
        x1 = BoardStartX + width / 3;
        x2 = BoardStartX + 2 * width / 3;
        y1 = BoardStartY + width / 3;
        y2 = BoardStartY + 2 * width / 3;

        // Board array
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                array[i][j] = 0;
            }
        }

        // Player turn
        PlayerTurn = 0;

        // Center positions
        CenterArray[0][0][0] = BoardStartX;
        CenterArray[0][0][1] = BoardStartY;
        CenterArray[0][0][2] = BoardStartX + width/3;
        CenterArray[0][0][3] = BoardStartY + width/3;

        CenterArray[1][0][0] = BoardStartX + width/3;
        CenterArray[1][0][1] = BoardStartY;
        CenterArray[1][0][2] = BoardStartX + 2*width/3;
        CenterArray[1][0][3] = BoardStartY + width/3;

        CenterArray[2][0][0] = BoardStartX + 2*width/3;
        CenterArray[2][0][1] = BoardStartY;
        CenterArray[2][0][2] = BoardStartX + width;
        CenterArray[2][0][3] = BoardStartY + width/3;

        CenterArray[0][1][0] = BoardStartX;
        CenterArray[0][1][1] = BoardStartY + width/3;
        CenterArray[0][1][2] = BoardStartX + width/3;
        CenterArray[0][1][3] = BoardStartY + 2*width/3;

        CenterArray[1][1][0] = BoardStartX + width/3;
        CenterArray[1][1][1] = BoardStartY + width/3;
        CenterArray[1][1][2] = BoardStartX + 2*width/3;
        CenterArray[1][1][3] = BoardStartY + 2*width/3;

        CenterArray[2][1][0] = BoardStartX + 2*width/3;
        CenterArray[2][1][1] = BoardStartY + width/3;
        CenterArray[2][1][2] = BoardStartX + width;
        CenterArray[2][1][3] = BoardStartY + 2*width/3;

        CenterArray[0][2][0] = BoardStartX;
        CenterArray[0][2][1] = BoardStartY + 2*width/3;
        CenterArray[0][2][2] = BoardStartX + width/3;
        CenterArray[0][2][3] = BoardStartY + width;

        CenterArray[1][2][0] = BoardStartX + width/3;
        CenterArray[1][2][1] = BoardStartY + 2*width/3;
        CenterArray[1][2][2] = BoardStartX + 2*width/3;
        CenterArray[1][2][3] = BoardStartY + width;

        CenterArray[2][2][0] = BoardStartX + 2*width/3;
        CenterArray[2][2][1] = BoardStartY + 2*width/3;
        CenterArray[2][2][2] = BoardStartX + width;
        CenterArray[2][2][3] = BoardStartY + width;

        // Win lines
        x1win = BoardStartX + width/6;
        x2win = BoardStartX + width/2;
        x3win = BoardStartX + 5*width/6;
        y1win = BoardStartY + width/6;
        y2win = BoardStartY + width/2;
        y3win = BoardStartY + 5*width/6;

        // End game
        end = false;

        // Filled board counter
        filled = 1;

        // Draw
        draw = false;

        // End game text
        xWinner = width/2;
        yWinner = BoardStartY/2;
        xPA = width/2;
        yPA = BoardEndY + (height - BoardEndY)/2;

        // Undo array
        undoArray1 = new ArrayList<>();
        undoArray2 = new ArrayList<>();

        // AI
        AI = MainActivity.bool;
        AIStart = false;

        // Images
        xImage = x_image;
        oImage = o_image;

        this.width = width;
        this.height = height;
    }

    void draw(Canvas c){
        // Draw board space
        Paint BoardSpace = new Paint();
        BoardSpace.setColor(Color.WHITE);
        c.drawRect(BoardRect, BoardSpace);

        // Draw board lines
        Paint lines = new Paint();
        lines.setColor(Color.GREEN);
        c.drawLine(x1,BoardStartY,x1,BoardEndY,lines);
        c.drawLine(x2,BoardStartY,x2,BoardEndY,lines);
        c.drawLine(BoardStartX,y1,BoardEndX,y1,lines);
        c.drawLine(BoardStartX,y2,BoardEndX,y2,lines);

        // Draw x and o
        Paint x = new Paint();
        x.setColor(Color.BLUE);

        Paint o = new Paint();
        o.setColor(Color.RED);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(AIStart){
                    if(array[i][j] == 2){
                        Rect r = new Rect(CenterArray[i][j][0],CenterArray[i][j][1],CenterArray[i][j][2],CenterArray[i][j][3]);
                        c.drawBitmap(xImage,null,r,x);
                    }

                    if(array[i][j] == 1){
                        Rect r = new Rect(CenterArray[i][j][0],CenterArray[i][j][1],CenterArray[i][j][2],CenterArray[i][j][3]);
                        c.drawBitmap(oImage,null,r,o);
                    }
                }
                else {
                    if (array[i][j] == 1) {
                        Rect r = new Rect(CenterArray[i][j][0], CenterArray[i][j][1], CenterArray[i][j][2], CenterArray[i][j][3]);
                        c.drawBitmap(xImage, null, r, x);
                    }

                    if (array[i][j] == 2) {
                        Rect r = new Rect(CenterArray[i][j][0], CenterArray[i][j][1], CenterArray[i][j][2], CenterArray[i][j][3]);
                        c.drawBitmap(oImage, null, r, o);
                    }
                }
            }
        }

        // Win condition
        Paint win = new Paint();
        win.setColor(Color.BLACK);

        if((array[0][0] == 1 && array[1][0] == 1 && array[2][0] == 1) || (array[0][0] == 2 && array[1][0] == 2 && array[2][0] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(BoardStartX, y1win, BoardEndX, y1win, win);
            }
            end = true;
        }

        if((array[0][1] == 1 && array[1][1] == 1 && array[2][1] == 1) || (array[0][1] == 2 && array[1][1] == 2 && array[2][1] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(BoardStartX, y2win, BoardEndX, y2win, win);
            }
            end = true;
        }

        if((array[0][2] == 1 && array[1][2] == 1 && array[2][2] == 1) || (array[0][2] == 2 && array[1][2] == 2 && array[2][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(BoardStartX, y3win, BoardEndX, y3win, win);
            }
            end = true;
        }

        if((array[0][0] == 1 && array[0][1] == 1 && array[0][2] == 1) || (array[0][0] == 2 && array[0][1] == 2 && array[0][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(x1win, BoardStartY, x1win, BoardEndY, win);
            }
            end = true;
        }

        if((array[1][0] == 1 && array[1][1] == 1 && array[1][2] == 1) || (array[1][0] == 2 && array[1][1] == 2 && array[1][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(x2win, BoardStartY, x2win, BoardEndY, win);
            }
            end = true;
        }

        if((array[2][0] == 1 && array[2][1] == 1 && array[2][2] == 1) || (array[2][0] == 2 && array[2][1] == 2 && array[2][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(x3win, BoardStartY, x3win, BoardEndY, win);
            }
            end = true;
        }

        if((array[0][0] == 1 && array[1][1] == 1 && array[2][2] == 1) || (array[0][0] == 2 && array[1][1] == 2 && array[2][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(BoardStartX, BoardStartY, BoardEndX, BoardEndY, win);
            }
            end = true;
        }

        if((array[2][0] == 1 && array[1][1] == 1 && array[0][2] == 1) || (array[2][0] == 2 && array[1][1] == 2 && array[0][2] == 2)){
            draw = true;
            if(draw) {
                c.drawLine(BoardEndX, BoardStartY, BoardStartX, BoardEndY, win);
            }
            end = true;
        }

        // Tie
        if(!end && filled >= 10){
            Paint Winner = new Paint();
            Winner.setColor(Color.WHITE);
            Winner.setTextAlign(Paint.Align.CENTER);
            Winner.setStyle(Paint.Style.FILL);
            Winner.setTextSize(yWinner/2);

            c.drawText("It's a tie!",xWinner,yWinner,Winner);

            Winner.setTextSize(yWinner/2);
            c.drawText("Press New Game to play again.",xPA,yPA,Winner);
        }

        if(AIStart){
            // x wins
            if (end && PlayerTurn == 0) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (array[i][j] == 0) {
                            array[i][j] = 3;
                        }
                    }
                }

                Paint Winner = new Paint();
                Winner.setColor(Color.WHITE);
                Winner.setTextAlign(Paint.Align.CENTER);
                Winner.setStyle(Paint.Style.FILL);
                Winner.setTextSize(yWinner / 2);

                c.drawText("Congratualtions! X wins!", xWinner, yWinner, Winner);

                Winner.setTextSize(yWinner / 2);
                c.drawText("Press New Game to play again.", xPA, yPA, Winner);
            }

            // o wins
            if (end && PlayerTurn == 1) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (array[i][j] == 0) {
                            array[i][j] = 3;
                        }
                    }
                }

                Paint Winner = new Paint();
                Winner.setColor(Color.WHITE);
                Winner.setTextAlign(Paint.Align.CENTER);
                Winner.setStyle(Paint.Style.FILL);
                Winner.setTextSize(yWinner / 2);

                c.drawText("Congratualtions! O wins!", xWinner, yWinner, Winner);

                Winner.setTextSize(yWinner / 2);
                c.drawText("Press New Game to play again.", xPA, yPA, Winner);
            }
        }
        else {
            // x wins
            if (end && PlayerTurn == 1) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (array[i][j] == 0) {
                            array[i][j] = 3;
                        }
                    }
                }

                Paint Winner = new Paint();
                Winner.setColor(Color.WHITE);
                Winner.setTextAlign(Paint.Align.CENTER);
                Winner.setStyle(Paint.Style.FILL);
                Winner.setTextSize(yWinner / 2);

                c.drawText("Congratualtions! X wins!", xWinner, yWinner, Winner);

                Winner.setTextSize(yWinner / 2);
                c.drawText("Press New Game to play again.", xPA, yPA, Winner);
            }

            // o wins
            if (end && PlayerTurn == 0) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (array[i][j] == 0) {
                            array[i][j] = 3;
                        }
                    }
                }

                Paint Winner = new Paint();
                Winner.setColor(Color.WHITE);
                Winner.setTextAlign(Paint.Align.CENTER);
                Winner.setStyle(Paint.Style.FILL);
                Winner.setTextSize(yWinner / 2);

                c.drawText("Congratualtions! O wins!", xWinner, yWinner, Winner);

                Winner.setTextSize(yWinner / 2);
                c.drawText("Press New Game to play again.", xPA, yPA, Winner);
            }
        }

        // AI
        if(AI && PlayerTurn == 1){
            TTTB.minimax(0,1);
            TTTB.placeAMove(TTTB.computersMove,2);
            PlayerTurn = 0;
            if(filled < 10) {
                filled = filled + 1;
                undoArray1.add(TTTB.computersMove.x);
                undoArray2.add(TTTB.computersMove.y);
            }
        }
    }

    // Make a move
    public static void setPlayer(int a, int b){
        if(PlayerTurn == 0 && array[a][b] == 0){
            array[a][b] = 1;
            PlayerTurn = 1;
            filled = filled + 1;
            undoArray1.add(a);
            undoArray2.add(b);
        }
        if(PlayerTurn == 1 && array[a][b] == 0){
            array[a][b] = 2;
            PlayerTurn = 0;
            filled = filled + 1;
            undoArray1.add(a);
            undoArray2.add(b);
        }
    }

    // Reset to a new game
    public static void reset() {
        // Board array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = 0;
            }
        }

        // Filled board counter
        filled = 1;

        // Player turn
        if(!AIStart) {
            PlayerTurn = 0;
        }
        AIStart = false;

        // End game
        end = false;

        // Draw
        draw = false;

        // Undo arrays;
        undoArray1.clear();
        undoArray2.clear();
    }

    // Undo last move
    public static void undo(){
        try {
            int undo1 = undoArray1.remove(undoArray1.size() - 1);
            int undo2 = undoArray2.remove(undoArray2.size() - 1);

            array[undo1][undo2] = 0;

            if (PlayerTurn == 1 || filled == 1) {
                PlayerTurn = 0;
            }
            else {
                PlayerTurn = 1;
            }

            end = false;
            draw = false;

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(array[i][j] == 3){
                        array[i][j] = 0;
                    }
                }
            }

            if(AI && !AIStart && filled == 10){
                PlayerTurn = 0;
            }

            else if((AI && filled > 2 && filled < 11)){
                int AIundo1 = undoArray1.remove(undoArray1.size() - 1);
                int AIundo2 = undoArray2.remove(undoArray2.size() - 1);
                array[AIundo1][AIundo2] = 0;
                filled = filled - 1;
                PlayerTurn = 0;
            }

            if(filled > 1) {
                filled = filled - 1;
            }
        }
        catch(Exception e){

        }
    }

    public static void randomMove(){
        Random rand = new Random();
        int xrandom = rand.nextInt(3);
        int yrandom = rand.nextInt(3);
        array[xrandom][yrandom] = 2;
        undoArray1.add(xrandom);
        undoArray1.add(yrandom);
        filled = filled + 1;
    }
}
