package com.company;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static final int WIDTH = 70;
    public static final int HEIGHT = 40;
    private static Font font;
    private static Font normalFont;
    private static Font smallFont;
    private static Font renderFont;
    private static String name;
    private static int wordsSolved;
    private static Color greenColor;

    private static int numwords; //number of cards
    private static ArrayList<Point> pointset;
    private static ArrayList<word> wordset;
    private static HashMap<Point, flashcard> pointflashcard;

    public static void main(String[] args) {
        // write your code here
        numwords = 4;
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        font = new Font("TimesRoman", Font.BOLD, 45);
        normalFont = new Font("TimesRoman", Font.PLAIN, 30);
        smallFont = new Font("TimesRoman", Font.BOLD, 25);
        renderFont = new Font("TimesRoman", Font.PLAIN, 10);
        greenColor = new Color(103, 182, 68);

        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.PINK);
        StdDraw.enableDoubleBuffering();

        welcomePage();

        StdDraw.clear(Color.PINK);
        wordset = makeWords("dictionary.csv");
        pointset = drawFlashcards();
        pointflashcard = makeFlashcards();
        wordsSolved = 0;
        StdDraw.show();

        playGame();
    }

    private static ArrayList<word> makeWords (String csv) {
        ArrayList<word> s = new ArrayList<>();
        // parse the csv file, make a bunch of words

        String line = "";
        String splitBy = ",";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] employee = line.split(splitBy);    // use comma as separator
                String[] attributes = line.split(",");
                System.out.println();
                for (String w : attributes) {
                    System.out.println(w);
                }
                System.out.println();
                word w = new word(attributes[0], attributes[1], attributes[2], attributes[3], "queer");
                s.add(w);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    private static ArrayList<Point> drawFlashcards() { // n is number of cards we are testing on
        ArrayList<Point> s = new ArrayList<>();
        StdDraw.setPenColor(new Color(184,82,253));
        StdDraw.setFont(font);

        StdDraw.text(WIDTH / 2, HEIGHT - 5, "Rainbow Cookies >>>>>>>>");
        // stddraw
        StdDraw.setPenColor(Color.WHITE);
        for (int i = 0; i < numwords; i++) {
            Point p = new Point(i * WIDTH / numwords +  WIDTH / numwords / 2 + 1, HEIGHT - 15);
            StdDraw.filledRectangle(p.x, p.y, WIDTH / numwords / 2, HEIGHT / 10);
            s.add(p);
        }
        for (int i = 0; i < numwords; i++) {
            Point p = new Point(i * WIDTH / numwords +  WIDTH / numwords / 2 + 1,  HEIGHT / 3);
            StdDraw.filledRectangle(p.x, p.y, WIDTH / numwords / 2, HEIGHT / 10);
            s.add(p);
        }
        System.out.println(s.size());
        return s;
    }

    private static HashMap<Point, flashcard> makeFlashcards() {
        HashMap<Point, flashcard> s = new HashMap<>();
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setFont(normalFont);
        HashSet<Integer> indices = new HashSet<>();
        Random rnd = new Random();
        for (int i = 0; i < numwords; i++) {
            // randomly select a p from pointset
            word w = wordset.get(rnd.nextInt(wordset.size()));
            while (w.used) {
                w = wordset.get(rnd.nextInt(wordset.size()));
            }
            w.used = true;

            int index = rnd.nextInt(pointset.size());
            while (indices.contains(index)) {
                index = rnd.nextInt(pointset.size());
            }
            indices.add(index);
            Point p = pointset.get(index);
            StdDraw.text(p.x, p.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p.x, p.y - 2, w.pronunication); //x, y coordinates
            flashcard f = new flashcard(false, w);
            s.put(p, f);

            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setFont(normalFont);
            index = rnd.nextInt(pointset.size());
            while (indices.contains(index)) {
                index = rnd.nextInt(pointset.size());
            }
            indices.add(index);
            p = pointset.get(index);
            StdDraw.text(p.x, p.y, w.wordEnglish);
            f = new flashcard(true, w);
            s.put(p, f);
        }
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT - 1, "insert instruction here");
        StdDraw.text(5, HEIGHT - 1, name + "'s Game");

        return s;
    }

    private static void welcomePage() {
        //draw the main menu
        drawTitleFrame();
        //10 seconds to choose a thing
        char key = ' ';
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 'i') {
                    drawInstructionsFrame();
                    key = ' ';
                    drawTitleFrame();
                } else if (key != ' '){
                    break;
                }
            }
        }
        titleThings(key);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT - 1, "Use WASD to move");
        StdDraw.text(5, HEIGHT - 1, name + "'s Game");
        StdDraw.show();
    }

    private static void drawTitleFrame() {
        StdDraw.clear(Color.PINK);
        StdDraw.setPenColor(new Color(184,82,253));
        StdDraw.setFont(font);

        StdDraw.text(WIDTH / 2, HEIGHT - 5, "Rainbow Cookies >>>>>>>>");

        StdDraw.setFont(normalFont);
        // can put our names here
        StdDraw.text(WIDTH / 2, HEIGHT - 9, "QWERHacks Team 24");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Quit Game (Q)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Instructions (I)");

        StdDraw.show();
    }

    //instructions, to improve
    private static void drawInstructionsFrame() {
        StdDraw.setFont(smallFont);
        StdDraw.clear(Color.PINK);
        StdDraw.setPenColor(greenColor);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 6, "We're learning some fun LGBTQ+ terms in Chinese today!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 4, "This will be a matching game; you have 4 terms in Chinese");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, "pinyin (pronunciation) is in orange below the Chinese characters");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please match the flashcards in Chinese with their meaning in English");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "If the words turn green, you got it right!");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Otherwise, keep guessing");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 6, "Are you ready? Press Space to return to the Menu.");
        StdDraw.show();
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                break;
            }
        }
    }

    private static void titleThings(char key) {
        if (key == 'n') {
            drawCenterFrame("Please input your name:");
            name = "";
            StdDraw.setFont(smallFont);
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Press Enter to submit");
            StdDraw.show();
            while (true) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                    break;
                } else if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                    name += c;
                    drawCenterFrame("your name: " + name);
                }
            }
            drawCenterFrame("hello, " + name);
            StdDraw.pause(1000);
        } else if (key == 'q') {
            drawCenterFrame("you have quit the game :( come back soon");
            exitFunc();
        } else {
            drawCenterFrame("invalid input");
            exitFunc();
        }
    }

    private static void exitFunc() {
        StdDraw.pause(2000);
        System.exit(0);
    }

    private static void drawCenterFrame(String s) {
        StdDraw.clear(Color.PINK);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);
        StdDraw.show();
    }

    private static void playGame() {
        int selectedCards = 0;
        Point[] cards = new Point[2];
        char key = ' ';
        String curr = "";
        // endless loop until they win or quit
        while (!(StdDraw.hasNextKeyTyped()
                && Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q')) {
            if (wordsSolved == numwords) { // all done!
                drawWinFrame();
            }
            // either this one or the next part, choose one
            // option 1: nextkeytyped => have this dependent on numbers
            if (StdDraw.hasNextKeyTyped()) {
                key = Character.toLowerCase(StdDraw.nextKeyTyped());
                // do something
                int i = key; // or whatever char->int is
                Point p = pointset.get(i+1);
                if (pointflashcard.get(p).solved) {
                    continue;
                }
                updateFlashcard(p);
                cards[selectedCards] = p;
                selectedCards += 1;
                StdDraw.show();
            }

            // option 2: mouseeclicking. if this one, math-ing necessary so it clicks the
            // right flashcard. possible, just kinda tedious calculating
            // also you need to mess around with mouseClicked which cannot be directly called
            // to get the coordinates. stack overflow etc, i can't figure this out rn
            else if (StdDraw.isMousePressed()) {
                int mouseX = Math.toIntExact(Math.round(StdDraw.mouseX()));
                int mouseY = Math.toIntExact(Math.round(StdDraw.mouseY()));
                // detectMouse(mouseX, mouseY); // detectMouse would be the method
                // that calculates the math part i.e. which point/word they clicked on

                Point p = pointset.get(0); // whatever point was clicked
                if (pointflashcard.get(p).solved) {
                    continue;
                }
                updateFlashcard(p);
                selectedCards += 1;
            }

            // now for the actual 'two cards weere seelected, what now?"
            if (selectedCards == 2) {
                selectedCards = 0; //reset it
                flashcard one = pointflashcard.get(cards[0]);
                flashcard two = pointflashcard.get(cards[1]);
                if (one.sameWord(two)) {
                    successCards(cards[0], cards[1]);
                } else {
                    notSameWord(cards[0], cards[1]);
                }

            }


        }
        drawCenterFrame("You quit the game. We hope you enjoyed!");
        StdDraw.pause(2000);
        System.exit(0);
    }

    private static void drawWinFrame() {
        StdDraw.clear(Color.PINK);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "You Won the Game! <3");
        StdDraw.show();
        StdDraw.pause(2000);
        System.exit(0);
    }

    private static void updateFlashcard(Point p) { //highlights in Green
        flashcard f = pointflashcard.get(p);
        word w = f.getW();
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.setFont(normalFont);
        if (!f.getEnglish()) {
            StdDraw.text(p.x, p.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p.x, p.y - 2, w.pronunication); //x, y coordinates
        } else {

            StdDraw.text(p.x, p.y, w.wordEnglish);
        }
        StdDraw.show();
    }


    private static void successCards(Point p1, Point p2) {
        flashcard f = pointflashcard.get(p1);
        word w = f.getW();
        f.solved = true;

        StdDraw.setPenColor(greenColor);
        StdDraw.setFont(normalFont);
        if (!f.getEnglish()) {
            StdDraw.text(p1.x, p1.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p1.x, p1.y - 2, w.pronunication); //x, y coordinates
        } else {
            StdDraw.text(p1.x, p1.y, w.wordEnglish);
        }
        f = pointflashcard.get(p2);
        f.solved = true;
        w = f.getW();

        StdDraw.setPenColor(greenColor);
        StdDraw.setFont(normalFont);
        if (!f.getEnglish()) {
            StdDraw.text(p2.x, p2.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p2.x, p2.y - 2, w.pronunication); //x, y coordinates
        } else {
            StdDraw.text(p2.x, p2.y, w.wordEnglish);
        }
        StdDraw.show();
        // success message?
    }

    private static void notSameWord(Point p1, Point p2) {
        flashcard f = pointflashcard.get(p1);
        word w = f.getW();
        if (f.getEnglish()) {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setFont(normalFont);
            StdDraw.text(p1.x, p1.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p1.x, p1.y - 2, w.pronunication); //x, y coordinates
        } else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setFont(normalFont);
            StdDraw.text(p1.x, p1.y, w.wordEnglish);
        }
        f = pointflashcard.get(p2);
        w = f.getW();
        if (f.getEnglish()) {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setFont(normalFont);
            StdDraw.text(p2.x, p2.y + 1, w.wordLanguage); //x, y coordinates
            StdDraw.setPenColor(Color.orange);
            StdDraw.setFont(smallFont);
            StdDraw.text(p2.x, p2.y - 2, w.pronunication); //x, y coordinates
        } else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setFont(normalFont);
            StdDraw.text(p2.x, p2.y, w.wordEnglish);
        }

        StdDraw.show();
    }
}