package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {
    // 3分= 3x60x1000 = 180000 msec
    private int remainTime = 60000;
    private int totalScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View view) {
        Intent intent = new Intent(new Intent(getApplicationContext(), BeforeGameActivity.class));
        intent.putExtra("remainTime",remainTime);
        intent.putExtra("totalScore", totalScore);
        startActivity(intent);
    }
}