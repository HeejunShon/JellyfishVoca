package com.example.cathy.jellyfish_voca;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class wordsitemView extends LinearLayout {
    TextView textView; //영어 텍스트뷰
    TextView textView2;  // 뜻 텍스트뷰

    public wordsitemView(Context context) {
        super(context);

        init(context);
    }

    public wordsitemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.words_items,this,true);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

    }

    public void setenglish(String english) {
        textView.setText(english);
    }

    public void setkorean(String korean) {
        textView2.setText(korean);
    }
}