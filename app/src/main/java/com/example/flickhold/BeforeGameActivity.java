package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class BeforeGameActivity extends AppCompatActivity {

    private LinearLayout beforeGameLayout;
    private TextView textView;
    private FrameLayout.LayoutParams frameParams;
    private int imageWidth, imageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_game);
        beforeGameLayout = new LinearLayout(this);
        // orientationは垂直方向
        beforeGameLayout.setOrientation(LinearLayout.VERTICAL);
        beforeGameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        beforeGameLayout.setGravity(Gravity.CENTER);
        beforeGameLayout.setBackgroundColor(Color.argb(0xff, 0xaa, 0xcc, 0xff));
        // テキスト設定
        textView = new TextView(this);
        textView.setText("今回のターゲット");

        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        textView.setLayoutParams(textLayoutParams);
        beforeGameLayout.addView(textView);



        setContentView(beforeGameLayout);
        //目標カード決定用のランダム関数
        Random random = new Random();
        TypedArray cardsData = getResources().obtainTypedArray(R.array.card);

        // 目標カードの設定
        ImageView target = new ImageView(this);
        int targetNum = random.nextInt(cardsData.length());

        Drawable rand = cardsData.getDrawable(targetNum);
        target.setImageDrawable(rand);
        // 画像のサイズの設定
        imageWidth = 300;
        imageHeight = 300;
        frameParams = new FrameLayout.LayoutParams(imageWidth,imageHeight);
        target.setLayoutParams(frameParams);
        beforeGameLayout.addView(target);

        // ボタンの設定
        Button button = new Button(this);
        button.setText("ゲームスタート");

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        button.setLayoutParams(buttonLayoutParams);
        beforeGameLayout.addView(button);
        button.setOnClickListener(startButtonClickListener);
    }

    View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
    };

}