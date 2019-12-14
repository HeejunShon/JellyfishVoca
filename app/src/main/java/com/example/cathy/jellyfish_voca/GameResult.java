package com.example.cathy.jellyfish_voca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        //툴바 적용 부분

        TextView titleName = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 타이틀 보여줄지 말지 설정
        getSupportActionBar().setTitle(titleName.getText());  //해당 액티비티의 툴바에 있는 타이틀을 ()으로 처리
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        //인텐트로 틀린단어 받아오기
        Intent intent = new Intent(this.getIntent());
        ArrayList<String> words = intent.getStringArrayListExtra("words");
        ArrayList<String> means = intent.getStringArrayListExtra("means");

        //단어 표시할 텍스트뷰
        TextView wordsText = (TextView) findViewById(R.id.wrongWordsText);
        TextView meansText = (TextView) findViewById(R.id.wrongWordsMeanText);

        //단어 스트링
        String wrongWords = "";
        String wrongWordsMean = "";

        //스트링에 단어 저장
        for(int i = 0; i<words.size(); i++){
            wrongWords += words.get(i) + "\n";
            wrongWordsMean += means.get(i) + "\n";
        }

        //텍스트뷰에 표시
        wordsText.setText(wrongWords);
        meansText.setText(wrongWordsMean);

        Button btnExit = (Button) findViewById(R.id.exit_gameResult);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
