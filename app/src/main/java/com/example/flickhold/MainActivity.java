package com.example.flickhold;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnTouchListener{

    private ImageView [] cards;
    private ArrayList<CardData> trumps;
    private LinearLayout.LayoutParams layoutParams;
    private FrameLayout.LayoutParams frameParams;
    private FrameLayout layout;
    private int targetNum;
    private long start;
    private long finish;

    private int imageWidth, imageHeight;
    private CustomImageView imageView2;
    //private float preDx, preDy;

    //private TranslateAnimation translateAnimation;
    private int targetLocalX;
    private int targetLocalY;

    private int screenX;
    private int screenY;

    private int totalScore;
    private int remainTime;

    private ArrayList<Bitmap> flippedBmp;

    //時間関連
    private TextView timerText;
    private SimpleDateFormat displayFormat =
            new SimpleDateFormat("mm:ss", Locale.US);

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("sss", Locale.US);
    private CountDown countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalScore = (int)getIntent().getSerializableExtra("totalScore");
        remainTime = (int)getIntent().getSerializableExtra("remainTime");
        targetNum = (int)getIntent().getSerializableExtra("targetNum");

        //setContentView(R.layout.activity_main);
        // リニアレイアウトのインスタンス生成
        //LinearLayout layout = new LinearLayout(this);
        layout = new FrameLayout(this);


        //レイアウトの縦横サイズをMATCH_PARENTにする
        layout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        // レイアウト中央寄せ
        //layout.setGravity(Gravity.CENTER);
        layout.setForegroundGravity(Gravity.CENTER);

        // 背景色
        //layout.setBackgroundColor(Color.argb(0xff, 0xaa, 0xcc, 0xff));
        layout.setBackgroundColor(Color.argb(0xff, 0xaa, 0xcc, 0xff));





        //ボタンを押したらタイマーをスタートさせゲーム画面へ移動する処理を記述する

        //setContentViewにlayoutを設定
        setContentView(layout);


        // インターバル msec
        long interval = 10;
        timerText = new TextView(this);
        timerText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20.0f);
        timerText.setText(displayFormat.format(0));
        // インスタンス生成
        // CountDownTimer(long millisInFuture, long countDownInterval)
        countDown = new CountDown(remainTime, interval);
        layout.addView(timerText);
        // 開始
        countDown.start();

        // 画像のサイズの設定
        imageWidth = 300;
        imageHeight = 300;

        //画像の縦横サイズをimageViewのサイズとして設定
        layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        frameParams = new FrameLayout.LayoutParams(imageWidth,imageHeight);

        trumps = new ArrayList<CardData>();
        flippedBmp = new ArrayList<Bitmap>();
        TypedArray cardsData = getResources().obtainTypedArray(R.array.card);


        //カードの角度設定用のランダム関数
        Random random = new Random();

        //トランプデータの格納
        for(int i=0;i < cardsData.length(); i++){
            Drawable drawable = cardsData.getDrawable(i);
            //cards[i] = new ImageView(this);
            ImageView card = new ImageView(this);
            card.setImageDrawable(drawable);
            //card.setBackgroundResource(R.drawable.boder);

            card.setLayoutParams(frameParams);
            //getDrawableメソッドで取り戻したものを、BitmapDrawable形式にキャストする
            BitmapDrawable bd = (BitmapDrawable)card.getDrawable();
            //getBitmapメソッドでビットマップファイルを取り出す
            Bitmap bmp = bd.getBitmap();
            //回転させる
            Matrix matrix = new Matrix();
            matrix.postRotate(random.nextInt(360));
            //Bitmap回転させる
            flippedBmp.add(Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false));
            //加工したBitmapを元のImageViewにセットする
            card.setImageDrawable(new BitmapDrawable(flippedBmp.get(i)));
            //メモリの解放
//            flippedBmp.recycle();
//            flippedBmp = null;
            card.setId(i);

            CardData instant = new CardData();
            instant.SetImageView(card);
            instant.SetCardNum(i);
            trumps.add(instant);


            card.setOnTouchListener(this);

            //card.refreshDrawableState();
            //trumps.get(i).setImageDrawable(drawable);
            //trumps.get(i).setLayoutParams(frameParams);
        }
        Collections.shuffle(trumps);
        for(int i=0; i < trumps.size();i++) {
            CardData card = trumps.get(i);
            card.SetCardPoint((trumps.size() - i)*5);//重なっている深さに応じて獲得ポイントを変える
            //layoutにimageViewを追加
            layout.addView(trumps.get(i).GetImageView());
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //cards = new ImageView[13];

        for(int i=0; i < trumps.size();i++) {
            CardData card = trumps.get(i);
            ImageView trump = card.GetImageView();
            //cardの位置の設定
            trump.setTranslationX(layout.getWidth() / 2 - imageWidth / 2);
            trump.setTranslationY(layout.getHeight() / 2 - imageHeight / 2);
        }
        //ArrayList<ImageView> instant = new ArrayList<ImageView>(trumps);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        v.performClick();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = System.currentTimeMillis();
                targetLocalX = v.getLeft();
                targetLocalY = v.getTop();

                screenX = x;
                screenY = y;

                break;

            case MotionEvent.ACTION_MOVE:

                int diffX = screenX - x;
                int diffY = screenY - y;

                targetLocalX -= diffX;
                targetLocalY -= diffY;

                v.layout(targetLocalX,
                        targetLocalY,
                        targetLocalX + v.getWidth(),
                        targetLocalY + v.getHeight());

                screenX = x;
                screenY = y;

                break;

            case MotionEvent.ACTION_UP:
                finish = System.currentTimeMillis();
                //目標カード選択判定
                if(finish - start > 2000){
                    //layout.removeAllViews();
                    if(v.getId() == targetNum){
                        countDown.cancel();
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("remainTime", timerText.getText());
                        for(CardData trump:trumps){
                            if(trump.GetCardNum() == targetNum)
                                intent.putExtra("getPoint", trump.GetCardPoint());
                        }
                        intent.putExtra("totalScore", totalScore);
                        //メモリの解放
                        for(CardData trump:trumps){
                            trump.GetImageView().setImageDrawable(null);
                        }
                        for(Bitmap bit:flippedBmp){
                            bit.recycle();
                            bit = null;
                        }
                        startActivity(intent);
                    }
                }
                break;
        }
        return true;
    }

    class CountDown extends CountDownTimer {

        CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 完了
            //timerText.setText(dataFormat.format(0));
            Intent intent = new Intent(getApplicationContext(), TotalResultActivity.class);
            intent.putExtra("remainTime", 0);
            intent.putExtra("getPoint", 0);
            intent.putExtra("totalScore", totalScore);
            //メモリの解放
            for(CardData trump:trumps){
                trump.GetImageView().setImageDrawable(null);
            }
            for(Bitmap bit:flippedBmp){
                bit.recycle();
                bit = null;
            }
            startActivity(intent);
        }

        // インターバルで呼ばれる
        @Override
        public void onTick(long millisUntilFinished) {
            // 残り時間を分、秒、ミリ秒に分割
            //long mm = millisUntilFinished / 1000 / 60;
            //long ss = millisUntilFinished / 1000 % 60;
            //long ms = millisUntilFinished - ss * 1000 - mm * 1000 * 60;
            //timerText.setText(String.format("%1$02d:%2$02d.%3$03d", mm, ss, ms));

            timerText.setText(displayFormat.format(millisUntilFinished));

        }
    }
}