package com.company;

public class flashcard {
    private boolean english;
    private word w;
    boolean solved;

    public flashcard(boolean english, word w) {
        this.english = english;
        this.w = w;
    }

    public boolean sameWord(flashcard f) {
        return this.w == f.getW();
    }

    public word getW() {
        return this.w;
    }
    public boolean getEnglish() {
        return this.english;
    }
}
