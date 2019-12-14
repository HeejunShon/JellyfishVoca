package com.example.cathy.jellyfish_voca;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {

    final int game_size_h = 15; // 게임 가로칸, 변경할 시 activity_main.xml파일의 스크롤 뷰 안 리니어레이아웃 weightsum도 바꿔줘야함
    final int game_size_v = 17; // 게임 세로칸, 게임 크기에 따라 조절해주면 됨
    boolean clear = false; // 클리어 여부 변수
    int depth = 0; // 출력할 수심, 지금은 0이지만 합칠 경우 총 수심 가져와서 여기다가 넣으면 됩니다
    int progress = 0; // 퍼즐이 순서대로 진행되므로 풀이 진행중인 단어 순서 설정해주는 변수
    int life = 3; // 틀리면 하나씩 깎인다
    int time = 300; // 스테이지 클리어 제한시간
    boolean pause = false; //게임 일시정지 변수

    EnglishWords words = new EnglishWords(); // 영단어 가져오기 위해 객체 생성

    LinearLayout linearLayout_main; // acitivity_main의 스크롤 뷰 안에 있는 리니어 레아이웃 가져올 용도, 세로방향
    LinearLayout[] sub_linearLayouts; // 퍼즐 칸 넣기 위한 가로 방향 리니어 레이아웃

    /* 퍼즐 구조

    sub_linearLayouts ┐
    sub_linearLayouts │
    sub_linearLayouts ├ linearLayout_main(세로 길이는 game_size_v에 따라
    sub_linearLayouts │
    sub_linearLayouts ┘

     */


    Button[][] btnquiz = new Button[game_size_v][game_size_h]; // 십자말풀이 판 2차원 버튼 배열

    String[] answers; // EnglishWords에서 영단어 가져올 배열
    String[] meanings; // " 에서 뜻 가져올 배열
    int [][] positions; // " 에서 단어 위치 가져올 배열


    EditText input_edittext; // 단어 입력 칸 EditText 객체
    TextView textView; // 수심 출력 텍스트뷰 객체
    TextView textView2; // 뜻 출력 텍스트뷰 객체
    TextView textView3; //시간 출력 텍스트뷰 객체
    Button btnInsert; // 입력 버튼 객체

    ArrayList<String> wrongWords = new ArrayList<String>(); //틀린 단어 어레이리스트
    ArrayList<String> wrongWordsMean = new ArrayList<String>(); //틀린 단어뜻 어레이리스트


    ImageView[] heart = new ImageView[3]; //목숨 하트 이미지 배열
    int[] heartArr = {R.id.heart1, R.id.heart2, R.id.heart3}; //하트 배열 초기화 int배열

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play); //★이름 바뀔시 수정

        input_edittext = (EditText)findViewById(R.id.edittext_input);
        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        btnInsert = (Button)findViewById(R.id.btnInsertWord);

        for(int i=0; i<heart.length; i++)
            heart[i] = (ImageView)findViewById(heartArr[i]);


        TextView titleName = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 타이틀 보여줄지 말지 설정
        getSupportActionBar().setTitle(titleName.getText());  //해당 액티비티의 툴바에 있는 타이틀을 ()으로 처리

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        //시간 조절
        handler = new Handler(Looper.getMainLooper()){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                textView3.setText("시간 : "+msg.arg1+"");
            }
        };

        //시간 돌아가는 스레드
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); // 스레드 중 토스트 만들시 생기는 오류 방지

                while (true) {
                    while(pause){ }
                    Message msg = handler.obtainMessage();
                    msg.arg1 = time;
                    handler.sendMessage(msg);

                    try {
                        time--;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(time<0 || clear) {
                        endGame();
                        break;
                    }
                }
            }
        });
        th.start();


        //---------------------십자말풀이


        //EnglishWords에서 배열 가져오기
        positions = words.getPositions();
        answers = words.getWords();
        meanings = words.getMeans();

        //초기 출력 설정
        textView.append(Integer.toString(depth)+"m");
        textView2.append(meanings[progress]);

        insertPuzzle(); //퍼즐 레이아웃 삽입
        changeBlock(); //블록 버튼 모양 바꾸기

    }

    //메뉴 추가 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    //ToolBar(메뉴)에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                Toast.makeText(getApplicationContext(), "뒤로가기 버튼 클릭됨", Toast.LENGTH_LONG).show();
                finish();
                return true;

            case R.id.action_settings: //일시정지 버튼
                // 정지시 이미지 변경
                if(!pause) {
                    //Toast.makeText(getApplicationContext(), "일시정지", Toast.LENGTH_LONG).show();
                    item.setIcon(R.drawable.resume);
                }
                else {
                    //Toast.makeText(getApplicationContext(), "다시시작", Toast.LENGTH_LONG).show();
                    item.setIcon(R.drawable.pause);
                }
                pause = !pause;
                return true;

            default: //메뉴버튼(테스트용, 향후 필요시 추가)
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    //퍼즐 삽입 함수
    public void insertPuzzle(){

        linearLayout_main = (LinearLayout)findViewById(R.id.game_main_layout);
        sub_linearLayouts = new LinearLayout[game_size_v];  // 퍼즐의 세로 크기만큼 sub 리니어 레이아웃 생성

        //sub리니어레이아웃 param 설정
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //버튼 param 설정
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //param들 세부 설정
        params.height = 80;
        params.weight = 1; //weightsum에 맞추기 위해
        params2.width = 80;
        params2.weight = 1;

        //버튼 생성 및 설정 for 문
        for(int i = 0; i < game_size_v ; i++){
            for(int j = 0; j < game_size_h; j++){
                btnquiz[i][j] = new Button(this);
                //btnquiz[i][j].setId(i);  //필요?
                btnquiz[i][j].setLayoutParams(params2);
                btnquiz[i][j].setClickable(false);
                btnquiz[i][j].setPadding(1,1,1,1);
                btnquiz[i][j].setText("");

            }
        }



        //sub리니어레이아웃 생성 및 설정 for문
        for (int i = 0; i < sub_linearLayouts.length; i++){
            sub_linearLayouts[i] = new LinearLayout(this); //객체 생성
            sub_linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL); //각 레아아웃을 가로 방향으로 설정
            sub_linearLayouts[i].setWeightSum(game_size_h); //가로 사이즈만큼 weight_sum 설정
            sub_linearLayouts[i].setLayoutParams(params); //param설정
            linearLayout_main.addView(sub_linearLayouts[i]); //main 리니어 레이아웃에 한개씩 넣어주기
        }

        //sub 리니어 레이아웃에 버튼들 넣어주기
        for(int i = 0; i < game_size_v; i++){
            for(int j = 0; j < game_size_h; j++){
                sub_linearLayouts[i].addView(btnquiz[i][j]);
            }
        }

        //단어 위치에 맞춰 퍼즐 삽입
        int h_;
        int v_;

        for(int i = 0; i<answers.length; i++){

            h_ = positions[i][1];
            v_ = positions[i][2];

            if(positions[i][0] == 0){ // 단어가 가로방향으로 배치될 경우
                for(int j = 0; j < answers[i].length(); j++){
                    btnquiz[h_][v_+j].setText(" "); //열만 높여줌 단어 없는 칸과 구분 위해 ""이 아니라 " "로 설정
                }
            }
            else if(positions[i][0] == 1){ //단어가 세로 방향으로 배치될 경우
                for(int j = 0; j < answers[i].length(); j++){
                    btnquiz[h_+j][v_].setText(" "); //행만 높여줌
                }
            }
        }

        //버튼 배경 설정
        for(int i = 0; i < game_size_v; i++){
            for(int j = 0; j < game_size_h; j++){
                if(btnquiz[i][j].getText().equals("")) // 단어 배치 (" ")안된 부분이면 투명으로 설정
                    btnquiz[i][j].setBackgroundResource(R.drawable.btnquiz_bg_transparent);
                else
                    btnquiz[i][j].setBackgroundResource(R.drawable.btnquiz_bg_filled); //단어 배치되었을경우 버블 모양으로 버튼 바꾸기
            }
        }


    }

    //단어 맞출시 블록 바꿔주는 함수
    public void changeBlock(){

        int i = progress;
        int h_ = positions[i][1];
        int v_ = positions[i][2];

        //전에 풀었던 문제 부분 다시 파란 버블로 바꿔줌
        if(progress>0){
            int pre_h = positions[i-1][1];
            int pre_v = positions[i-1][2];
            if(positions[i-1][0] == 0){
                for(int j = 0; j < answers[i-1].length(); j++) {
                    btnquiz[pre_h][pre_v + j].setBackgroundResource(R.drawable.btnquiz_bg_filled);
                }
            }
            else if(positions[i-1][0] == 1) {
                for (int j = 0; j < answers[i - 1].length(); j++) {
                    btnquiz[pre_h+j][pre_v].setBackgroundResource(R.drawable.btnquiz_bg_filled);
                }
            }
        }

        //현재 풀고있는 버블 노란색으로 바꿔줌
        if(positions[i][0] == 0){
            for(int j = 0; j < answers[i].length(); j++) {
                btnquiz[h_][v_ + j].setBackgroundResource(R.drawable.btnquiz_bg_selected);
            }
        }
        else if(positions[i][0] == 1){
            for(int j = 0; j < answers[i].length(); j++){
                btnquiz[h_+j][v_].setBackgroundResource(R.drawable.btnquiz_bg_selected);
            }
        }

    }

    //입력한 단어가 맞을 경우 단어를 칸 안에 넣어주는 함수
    public void insertWords(){

        int i = progress; //현재 풀고 있는 단어 순서
        int h_;
        int v_;

        //퍼즐 삽입

        h_ = positions[i][1];
        v_ = positions[i][2];

        //해당 영단어를 칸에 순서대로 넣어줌
        if(positions[i][0] == 0){
            for(int j = 0; j < answers[i].length(); j++){
                btnquiz[h_][v_+j].setText(Character.toString(answers[i].charAt(j)));
            }
        }
        else if(positions[i][0] == 1){
            for(int j = 0; j < answers[i].length(); j++){
                btnquiz[h_+j][v_].setText(Character.toString(answers[i].charAt(j)));
            }
        }

    }

    //조건이 충족되면 게임 끝내기
    public void endGame(){

        //클리어 아닐시와 맞을시를 구분하여 토스트 메세지 출력
        if(!clear){
            Toast.makeText(this, "You Failed^^", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Stage Clear!!!", Toast.LENGTH_SHORT).show();

        //게임이 끝났으므로 입력 칸과 버튼 비활성화
        input_edittext.setEnabled(false);
        btnInsert.setEnabled(false);

        //결과액티비티에 전달
        Intent intent = new Intent(this, GameResult.class);
        intent.putExtra("words", wrongWords);
        intent.putExtra("means", wrongWordsMean);
        startActivity(intent);
        finish();
    }

    //입력 버튼 리스너
    public void onInsertClicked(View view){

        String inputted;
        inputted = input_edittext.getText().toString().toUpperCase();

        if(progress<10) {
            if (inputted.equals(answers[progress])) { //입력한 단어가 맞을 경우
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                insertWords(); //단어 넣어주는 함수 실행


                if(progress<9) //마지막 단어 맞추면 더해지지 않도록
                    progress++;
                else
                    clear = true;

                //수심, 뜻 갱신해주기
                depth += 10;
                textView.setText("수심 : " + Integer.toString(depth) + "m");
                textView2.setText("뜻 : " + meanings[progress]);

            } else {
                Toast.makeText(this, "Wrong... Try again", Toast.LENGTH_SHORT).show(); //틀릴경우 틀렸다는 메세지 출력
                life--;
                heart[life].setImageResource(0); //틀리면 하트 이미지 삭제

                wrongWords.add(answers[progress]); //틀린단어 어레이리스트에 추가
                wrongWordsMean.add(meanings[progress]); //틀린단어뜻 어레이리스트에 추가

                if(life == 0) //목숨 0개면 게임 종료
                    endGame();
            }
        }

        //게임 진행중일시 블록 선 색깔 갱신
        if(progress<10)
            changeBlock();

        //클리어 시 게임 끝내주기
        if(clear)
            endGame();

        input_edittext.setText(""); //입력 칸 공백으로 초기화해주기
    }
}
