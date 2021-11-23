package com.example.flickhold;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity implements OnTouchListener, OnClickListener {

    private CustomImageView imageView;
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
        setContentView(R.layout.activity_main);

        //textView = findViewById(R.id.textView);

        imageView = this.findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);

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

////                int trashLeft    = trash.getLeft() + trash.getWidth()/2;
////                int trashTop     = trash.getTop()  + trash.getHeight()/2;
//                int targetRight  = v.getLeft() + v.getWidth();
//                int targetBottom = v.getTop() + v.getHeight();
//
////                if (targetRight > trashLeft && targetBottom > trashTop) {
////                    frameLayout01.removeView(v);
////                }
                break;
        }
        return true;
    }

//    private void startTranslate(float fromX, float toX, float fromY, float toY){
//        // 設定を切り替え可能
//        Log.d("Move","ACTION_MOVE: fromX="+ fromX + ", toX=" + toX + ", fromY=" + fromY + ", toY=" + toY);
//        int type = 0;
//        if(type == 0){
//            // TranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)
//            translateAnimation = new TranslateAnimation(
//                    Animation.ABSOLUTE, fromX,
//                    Animation.ABSOLUTE, toX,
//                    Animation.ABSOLUTE, fromY,
//                    Animation.ABSOLUTE, toY);
//        }
//        else if(type == 1){
//            translateAnimation = new TranslateAnimation(
//                    Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 0.9f,
//                    Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 1.8f);
//        }
//        else if(type ==2){
//            translateAnimation = new TranslateAnimation(
//                    Animation.RELATIVE_TO_PARENT, 0.0f,
//                    Animation.RELATIVE_TO_PARENT, 0.4f,
//                    Animation.RELATIVE_TO_PARENT, 0.0f,
//                    Animation.RELATIVE_TO_PARENT, 0.6f);
//        }
//
//        // animation時間 
//        translateAnimation.setDuration(2000);
//        // 繰り返し回数
//        translateAnimation.setRepeatCount(0);
//        // animationが終わったそのまま表示にする
//        translateAnimation.setFillAfter(true);
//        //アニメーションの開始
//        imageView.startAnimation(translateAnimation);
//    }

    @Override
    public void onClick(View v) {

    }
}