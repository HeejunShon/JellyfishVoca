package com.example.cathy.jellyfish_voca;

public class EnglishWords {

    /* positions: 이차원 배열, 가로세로 구분 및 좌표 설정
     * position{{가로세로여부,시작 행,시작 열}}*/

    //★단어 많아지면 배열 구성요소 수정하는 함수 따로 만들 예정

    //영단어 추가
    //---------------중등

    //---------------고등

    //---------------토익
    String[] Toeic1_words = { "REGULATION", "EXCEPTION",
            "PRIORITY", "ATTITUDE",
            "NARRATIVE", "HIRE",
            "ENTIRE", "DOMESTIC",
            "PROFICIENT", "PERMIT" };

    String[] Toeic1_means = {"규정", "예외",
            "우선권, 우선사항", "태도, 마음가짐",
            "기술, 묘사", "고용하다",
            "전체의", "국내의, 국산의",
            "능숙한, 능한", "허락하다"};

    int[][] Toeic1_positions = { {0,0,2}, {1,0,3}, {0,4,3}, {0,6,0}, {0,8,3},
            {1,7,9}, {0,10,9}, {1,7,14}, {0,12,5}, {1,10,6}};

    //반환 함수들 : 추가시 제어문과 매개변수 사용해서 맞는거 반환하면 됨 or 다른 개인적인 방법 사용
    public String[] getWords(){
        return Toeic1_words;
    }

    public String[] getMeans(){
        return Toeic1_means;
    }

    public int[][] getPositions(){
        return Toeic1_positions;
    }
}