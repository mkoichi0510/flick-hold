package com.example.flickhold;

import android.widget.ImageView;

public class CardData {
    private ImageView img;
    private int cardPoint;
    private int cardNum;

    void SetImageView(ImageView imageView){
        img = imageView;
    }

    void SetCardPoint(int point){
        cardPoint = point;
    }

    void SetCardNum(int num){
        cardNum = num;
    }

    ImageView GetImageView(){
        return img;
    }
    int GetCardPoint(){
        return cardPoint;
    }

    int GetCardNum(){
        return cardNum;
    }

}
