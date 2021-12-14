package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView remainTime;
    private TextView getPoint;
    private TextView totalScoreView;
    static private int totalScore = 0;
    private int remainTimeSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        totalScore = (int)getIntent().getSerializableExtra("totalScore");
        remainTime = findViewById(R.id.remainTimeLabel);
        getPoint = findViewById(R.id.scoreLabel);
        totalScoreView = findViewById(R.id.totalScoreLabel);
        totalScore += (int)getIntent().getSerializableExtra("getPoint");
        totalScoreView.setText("トータルスコア："+String.valueOf(totalScore));
        String [] remains = (getIntent().getSerializableExtra("remainTime").toString()).split(":");
        remainTimeSec += Integer.parseInt(remains[0]) * 60;
        remainTimeSec += Integer.parseInt(remains[1]);

        getPoint.setText("今回のスコア："+getIntent().getSerializableExtra("getPoint").toString());
        remainTime.setText("残り時間："+getIntent().getSerializableExtra("remainTime").toString());
    }

    public void nextGame(View view){
        Intent intent = new Intent(getApplicationContext(), BeforeGameActivity.class);
        intent.putExtra("remainTime",remainTimeSec*1000);//ms基準のため1000倍する
        intent.putExtra("totalScore", totalScore);
        startActivity(intent);
    }


}