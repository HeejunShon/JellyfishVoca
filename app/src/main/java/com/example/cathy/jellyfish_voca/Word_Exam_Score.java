package com.example.cathy.jellyfish_voca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;


public class Word_Exam_Score extends AppCompatActivity {
    EditText editText;

    ListView listView;
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_exam_score);

        //툴바 적용 부분
        TextView titleName = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 타이틀 보여줄지 말지 설정
        getSupportActionBar().setTitle(titleName.getText());  //해당 액티비티의 툴바에 있는 타이틀을 ()으로 처리
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        ListView listView = (ListView) findViewById(R.id.listview1);

        adapter = new WordAdapter();
        // 리스트에 들어갈 값 지정
        adapter.additem(new Word_item("produce","생산하다"));
        adapter.additem(new Word_item("television","텔레비전"));
        adapter.additem(new Word_item("happy","행복한"));
        adapter.additem(new Word_item("professior","전문가"));
        adapter.additem(new Word_item("friend","친구"));
        adapter.additem(new Word_item("rest","나머지"));
        adapter.additem(new Word_item("break","휴식"));
        adapter.additem(new Word_item("milk","우유"));
        adapter.additem(new Word_item("pen","볼펜"));
        adapter.additem(new Word_item("note","공책"));
        adapter.additem(new Word_item("phone","휴대폰"));
        adapter.additem(new Word_item("book","책"));
        adapter.additem(new Word_item("white","흰색"));
        adapter.additem(new Word_item("black","검은색"));
        adapter.additem(new Word_item("byeong","병"));
        adapter.additem(new Word_item("hee","희"));
        // 리스트뷰 지정
        listView.setAdapter(adapter);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // 액션바 뒤로가기 기능
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    class WordAdapter extends BaseAdapter { // 어댑터클래스 정의 클래스
        ArrayList<Word_item> items = new ArrayList<Word_item>(); // WordItem 객체들을 담고 있을 수 있게 정의

        public int getCount(){
            return items.size();
        }

        public void additem(Word_item item){
            items.add(item);
        }

        @Override
        public Object getItem(int position){
            return items.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        // position = 아이템 인덱스를 의미 , convertView = 한번 만들어진 뷰를 화면에 다시 보이게 해줌, viewGroup =  뷰를 포함 하고있는 부모컨테이너 객체
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Word_item_View view = new Word_item_View(getApplicationContext());

            Word_item item = items.get(position);
            view.setWord(item.getWord());
            view.setMean(item.getMean());
            return view;
        }
    }
}
