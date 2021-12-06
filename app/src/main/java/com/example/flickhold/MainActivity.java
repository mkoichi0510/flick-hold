package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity implements OnTouchListener{

    private ImageView [] cards;
    private LinearLayout.LayoutParams layoutParams;
    private FrameLayout.LayoutParams frameParams;
    private FrameLayout layout;
    private int imageWidth, imageHeight;
    private CustomImageView imageView2;
    //private float preDx, preDy;
    //private TextView textView;
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
        //setContentViewにlayoutを設定
        //setContentView(layout);
        setContentView(layout);

        // 画像のサイズの設定
        imageWidth = 300;
        imageHeight = 300;

        //画像の縦横サイズをimageViewのサイズとして設定
        layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        frameParams = new FrameLayout.LayoutParams(imageWidth,imageHeight);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        cards = new ImageView[13];
        TypedArray cardsData = getResources().obtainTypedArray(R.array.card);
        for(int i=0;i < cards.length; i++){
            Drawable drawable = cardsData.getDrawable(i);
            cards[i] = new ImageView(this);
            cards[i].setImageDrawable(drawable);
            cards[i].setLayoutParams(frameParams);
            //layoutにimageViewを追加
            layout.addView(cards[i]);
            cards[i].setOnTouchListener(this);
            //cardの位置の設定
            cards[i].setTranslationX(layout.getWidth()/2 - imageWidth/2);
            cards[i].setTranslationY(layout.getHeight()/2 - imageHeight/2);

        }


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