package com.example.cathy.jellyfish_voca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.example.cathy.jellyfish_voca.DictionaryActivity.REQUEST_CODE_WORD_MAIN;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQUEST_CODE_WORD_MAIN){
            if(intent != null){
                String contents = intent.getStringExtra("contents");


            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mainsettings = (Button)findViewById(R.id.mainsettings);
        Button word_Page = (Button)findViewById(R.id.word_Page);
        Button game_Page = (Button)findViewById(R.id.game_Page);
        Button userGame_Page = (Button)findViewById(R.id.userGame_Page);
        mainsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),User_InformActivity.class);
                startActivity(intent);
            }
        });
        word_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
                startActivity(intent);
            }
        });

        game_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChoiceLevel.class);
                startActivity(intent);
            }
        });

        userGame_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
