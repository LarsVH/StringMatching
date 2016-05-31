package com.lars;

import com.lars.cosinedistance.CosineDistance;
import javafx.util.Pair;
import sun.awt.AWTAccessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Main {
    // write your code here
    //static String needle1 = "PersonInLocation";
    //static String needle2 = "PLocation";
    //static Double treshold = 0.7;
    static DecimalFormat f = new DecimalFormat("##.00");


    public static void main(String[] args) {
        // Multi Check
        //==============
        System.out.println("");
        DummyStrings ds = new DummyStrings();
        //findMatches("SandraInLiving", ds.getDummy(1), treshold);

        ArrayList<String> algonames = new ArrayList<>();
        Gui g = new Gui(ds.getDummy(1));

    }

    private static void printResult(String res, String algo){
        System.out.println(algo + ": " + res);
    }

}
