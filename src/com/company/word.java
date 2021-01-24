package com.company;

public class word {
    //vars: language, word_language, word_english, pronunciation, tag
    String language;
    String wordLanguage; // ???????
    String wordEnglish;
    String pronunication; // "chi1"
    String tag;
    boolean used;

    public word(String language, String wordLanguage, String wordEnglish, String pronunciation, String tag) {
        this.language = language;
        this.wordLanguage = wordLanguage;
        this.wordEnglish = wordEnglish;
        this.pronunication = pronunciation;
        this.tag = tag;
        this.used = false;
    }

}
