package com.example.mattbook_pro.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BoardView(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(MainActivity.bool){
            startAs();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            Board.reset();

            if(MainActivity.bool){
                startAs();
            }
        }

        if (item.getItemId() == R.id.action_undo) {
            Board.undo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startAs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Play as?");
        // Add the buttons
        builder.setPositiveButton("O", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Board.AIStart = true;
                Board.randomMove();
            }
        });
        builder.setNegativeButton("X", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
