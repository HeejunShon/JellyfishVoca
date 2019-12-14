package com.example.cathy.jellyfish_voca;

public class Word_item { // 리스트에 들어갈 단어, 뜻 데이터 저장 클래스
    private String word;
    private String mean;

    public Word_item(String word , String mean){
        this.word = word;
        this.mean = mean;
    }
    public String getWord(){
        return word;
    }
    public String getMean(){
        return mean;
    }
    public void setWord(String word){
        this.word = word;
    }
    public void setMean(String mean){
        this.mean = mean;
    }

}