package com.example.mattbook_pro.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static boolean bool = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void nextPvP(View view){
        Intent intent = new Intent(this,NextActivity.class);
        startActivity(intent);
        bool = false;
    }

    public void nextPvC(View view){
        Intent intent = new Intent(this,NextActivity.class);
        startActivity(intent);
        bool = true;
    }
}
