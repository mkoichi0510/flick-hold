package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView remainTime;
    private TextView getPoint;
    private int remainTimeSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        remainTime = findViewById(R.id.remainTimeLabel);
        getPoint = findViewById(R.id.scoreLabel);
        String [] remains = (getIntent().getSerializableExtra("remainTime").toString()).split(":");
        remainTimeSec += Integer.parseInt(remains[0]) * 60;
        remainTimeSec += Integer.parseInt(remains[1]);

        getPoint.setText(getIntent().getSerializableExtra("getPoint").toString());
        remainTime.setText(getIntent().getSerializableExtra("remainTime").toString());
    }
}