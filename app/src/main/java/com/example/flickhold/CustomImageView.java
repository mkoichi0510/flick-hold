package com.example.flickhold;

import androidx.appcompat.widget.AppCompatImageView;
import android.content.Context;
import android.util.AttributeSet;

public class CustomImageView extends AppCompatImageView{

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}