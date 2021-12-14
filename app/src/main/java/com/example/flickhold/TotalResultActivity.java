package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TotalResultActivity extends AppCompatActivity {

    private int totalScore;
    private TextView totalScoreText;
    private TextView highScoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_result);
        totalScoreText = findViewById(R.id.totalScoreLabel);
        highScoreText = findViewById(R.id.highScoreLabel);
        totalScore = (int)getIntent().getSerializableExtra("totalScore");
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (totalScore > highScore) {
            highScoreText.setText("High Score : " + String.valueOf(totalScore));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", totalScore);
            editor.apply();
        }
        else{
            highScoreText.setText("High Score : " + String.valueOf(highScore));
        }
        totalScoreText.setText("合計スコア："+String.valueOf(totalScore));
    }

    public void BackGameTitle(View view){
        startActivity(new Intent(new Intent(getApplicationContext(), StartActivity.class)));
    }

}