package com.example.cathy.jellyfish_voca;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Word_item_View extends LinearLayout {// 리니어레이아웃 상속받으니 다른 뷰 포함 가능
    TextView textView1;
    TextView textView2;

    public Word_item_View(Context context){ // XML 레이아웃 인플레이션
        super(context);
        init(context);
    }
    public Word_item_View(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    public void init(Context context){  // inflate 이용해서 XML에 적혀있는 view의 정의를 실제 view객체로 생성
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.word_item,this, true);
        textView1 =(TextView)findViewById(R.id.textview1);
        textView2 =(TextView)findViewById(R.id.textview2);
    }

    public void setWord(String word){
        textView1.setText(word);
    }
    public void setMean(String mean){
        textView2.setText(mean);
    }
}
