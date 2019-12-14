package com.example.cathy.jellyfish_voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Word_Exam_Mean extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_exam_mean);

        //툴바 적용 부분
        TextView titleName = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 타이틀 보여줄지 말지 설정
        getSupportActionBar().setTitle(titleName.getText());  //해당 액티비티의 툴바에 있는 타이틀을 ()으로 처리
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        String meanPro[]=new String[10];
        for(int i=0;i<10;i++){
            meanPro[i]="1"+i;
        }
        String answer[] = new String[10];
        TextView textView;
        Button exam_submit_mean = (Button)findViewById(R.id.exam_submit_mean);
        Intent intent = getIntent();
        String str = intent.getStringExtra("type");
        if(str.equals("mean")){
            for(int i =0;i<10;i++){
                textView= (TextView)findViewById(R.id.exam_tv0+i);
                textView.setText(meanPro[i]);
            }
        }else{
            for(int i =0;i<10;i++){
                textView= (TextView)findViewById(R.id.exam_tv0+i);
                textView.setText(meanPro[i]+"스펠링시험");
            }
        }

        exam_submit_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Word_Exam_Score.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
