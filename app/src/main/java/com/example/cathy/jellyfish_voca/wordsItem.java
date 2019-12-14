package com.example.cathy.jellyfish_voca;

import android.widget.Button;

public class wordsItem {

    String english;
    String korean;

    public wordsItem(String english, String korean) {
        this.english = english;
        this.korean = korean;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    @Override
    public String toString() {
        return "wordsItem{" +
                "english='" + english + '\'' +
                ", korean='" + korean + '\'' +
                '}';
    }
}
