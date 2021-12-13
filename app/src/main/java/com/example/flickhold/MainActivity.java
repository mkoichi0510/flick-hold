package com.example.flickhold;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnTouchListener{

    private ImageView [] cards;
    private ArrayList<ImageView> trumps;
    private LinearLayout.LayoutParams layoutParams;
    private FrameLayout.LayoutParams frameParams;
    private FrameLayout layout;

    private int imageWidth, imageHeight;
    private CustomImageView imageView2;
    //private float preDx, preDy;

    //private TranslateAnimation translateAnimation;
    private int targetLocalX;
    private int targetLocalY;

    private int screenX;
    private int screenY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // 画像のサイズの設定
        imageWidth = 300;
        imageHeight = 300;

        //画像の縦横サイズをimageViewのサイズとして設定
        layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        frameParams = new FrameLayout.LayoutParams(imageWidth,imageHeight);

        trumps = new ArrayList<ImageView>();
        TypedArray cardsData = getResources().obtainTypedArray(R.array.card);


        //カードの角度設定用のランダム関数
        Random random = new Random();

        //トランプデータの格納
        for(int i=0;i < cardsData.length(); i++){
            Drawable drawable = cardsData.getDrawable(i);
            //cards[i] = new ImageView(this);
            ImageView card = new ImageView(this);
            card.setImageDrawable(drawable);
            card.setLayoutParams(frameParams);
            //getDrawableメソッドで取り戻したものを、BitmapDrawable形式にキャストする
            BitmapDrawable bd = (BitmapDrawable)card.getDrawable();
            //getBitmapメソッドでビットマップファイルを取り出す
            Bitmap bmp = bd.getBitmap();
            //回転させる
            Matrix matrix = new Matrix();
            matrix.postRotate(random.nextInt(360));
            //Bitmap回転させる
            Bitmap flippedBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
            //加工したBitmapを元のImageViewにセットする
            card.setImageDrawable(new BitmapDrawable(flippedBmp));
            trumps.add(card);
            //card.refreshDrawableState();
            //trumps.get(i).setImageDrawable(drawable);
            //trumps.get(i).setLayoutParams(frameParams);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //cards = new ImageView[13];


        ArrayList<ImageView> instant = new ArrayList<ImageView>(trumps);
        Collections.shuffle(instant);
        instant.forEach(trump -> {
            //layoutにimageViewを追加
            layout.addView(trump);
            trump.setOnTouchListener(this);
            //cardの位置の設定
            trump.setTranslationX(layout.getWidth()/2 - imageWidth/2);
            trump.setTranslationY(layout.getHeight()/2 - imageHeight/2);
        });



    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        v.performClick();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:

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

                break;
        }
        return true;
    }

}