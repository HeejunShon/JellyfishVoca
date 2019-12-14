package com.example.cathy.jellyfish_voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_WORD_MAIN = 100;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    ListView lv;

    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        //툴바 적용 부분
        TextView titleName = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 타이틀 보여줄지 말지 설정
        getSupportActionBar().setTitle(titleName.getText());  //해당 액티비티의 툴바에 있는 타이틀을 ()으로 처리
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        Button word_main_test = (Button) findViewById(R.id.word_main_test);
        word_main_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WordExam_Choice.class);
                startActivity(intent);
            }
        });


        list = new ArrayList<String>();
        list.add("water 물");
        list.add("prince 왕자");
        list.add("queen 여왕");
        list.add("phone 폰");
        list.add("love 사랑");
        list.add("teacher 선생");
        list.add("student 학생");
        list.add("lion 사자");
        list.add("tiger 호랑이");

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, list);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, list);

        lv = (ListView) findViewById(R.id.lv);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        lv.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(clickListener);

        Button delete = (Button) findViewById(R.id.delete);
        findViewById(R.id.delete).setOnClickListener(clickListener);

    }

    private Button.OnClickListener clickListener = new Button.OnClickListener() {

        //private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            EditText et = (EditText)findViewById(R.id.et);

            if(v.getId() == R.id.add) {
                // 추가 버튼

                if(et.getText().length() != 0){

                    list.add(et.getText().toString());
                    et.setText("");

                    // 갱신되었음을 어댑터에 통보한다.

                    adapter.notifyDataSetChanged();
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }

            } else if(v.getId() == R.id.delete){

                // 삭제 버튼
                // multi choice

                SparseBooleanArray sba = lv.getCheckedItemPositions();

                if(sba!=null) {
                    if (sba.size() != 0) {

                        for (int i = lv.getCount() - 1; i >= 0; i--) {

                            if (sba.get(i)) {
                                list.remove(i);
                            }
                        }

                        lv.clearChoices();
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        }
    };
}

