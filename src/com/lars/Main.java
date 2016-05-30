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
    @Deprecated
    private static List<String>findMatches(String needle, ArrayList<String> hayStack, Double treshold){
        ArrayList<String> resmatches = new ArrayList<>();

        JaroWrinklerDistance jwd = new JaroWrinklerDistance();

        for(String straw : hayStack){
            Double jwdcoeff = jwd.apply(needle, straw);
            printCurrStrawMatch(straw, jwdcoeff, treshold);
        }

        return resmatches;
    }
    @Deprecated
    private static void printCurrStrawMatch(String straw, Double score, Double treshold){
        //System.out.println("Straw  |  Score  | Above treshold");

        System.out.print(straw + ": " + score.toString() + " - " );
        if(score>=treshold)
            System.out.println("V");
        else
            System.out.println("X");
    }

}
