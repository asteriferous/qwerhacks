package com.company;

import edu.princeton.cs.introcs.StdDraw;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.HashSet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static final int WIDTH = 70;
    public static final int HEIGHT = 40;
    private static Font font;
    private static Font smallFont;

    private static int numwords; //number of cards
    private static ArrayList<Point> pointset;
    private static ArrayList<word> wordset;

    public static void main(String[] args) {
        // write your code here
        numwords = 4;
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        font = new Font("TimesRoman", Font.PLAIN, 30);
        smallFont = new Font("TimesRoman", Font.BOLD, 25);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.PINK);
        StdDraw.enableDoubleBuffering();

        wordset = makeWords("dictionary.csv");
        pointset = drawFlashcards();
        makeFlashcards();
        StdDraw.show();


        // import csv file
        // make a bunch of words
        // have all the words in a Set

    }

    public static ArrayList<word> makeWords (String csv) {
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

    public static ArrayList<Point> drawFlashcards() { // n is number of cards we are testing on
        ArrayList<Point> s = new ArrayList<>();
        StdDraw.setPenColor(new Color(184,82,253));
        StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 35));

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

    public static void makeFlashcards() {
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setFont(font);
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

            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setFont(font);
            index = rnd.nextInt(pointset.size());
            while (indices.contains(index)) {
                index = rnd.nextInt(pointset.size());
            }
            indices.add(index);
            p = pointset.get(index);
            StdDraw.text(p.x, p.y, w.wordEnglish);
        }
    }
}