package com.lars;

import com.lars.cosinedistance.CosineDistance;
import sun.awt.AWTAccessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
	// write your code here

        String needle1 = "PersonInLocation";
        String needle2 = "PLocation";
        Double treshold = 0.7;
        DecimalFormat f = new DecimalFormat("##.00");

        // Algorithms
        //========================================================
        // Sorensen-Dice (Case-Sensitive!)
        //--------------
        SorensenDice sd = new SorensenDice();
        Double sdcoeff = sd.diceCoefficient(needle1, needle2);
        printResult(f.format(sdcoeff).toString(), "Sorensen-Dice");

        // Hamming Distance
        //------------------
        HammingDistance hd = new HammingDistance();
        String hdcoeffs = "not same length";
        Double hdsimilarity = -1.0;
        if(needle1.length() == needle2.length()) {
            Integer hdcoeff = hd.apply(needle1, needle2);
            hdsimilarity = 1 - (hdcoeff.doubleValue()/ new Integer(needle1.length()).doubleValue());
            hdcoeffs = hdcoeff.toString();
        }
        printResult(hdcoeffs.toString() + "; Similarity = " + hdsimilarity.toString(), "Hamming Distance");


        // Levenshtein Distance
        //---------------------
        Double tresholdperc = treshold*100;
        LevenshteinDistance ld = new LevenshteinDistance(tresholdperc.intValue());
        Integer lcoeff = ld.apply(needle1, needle2);
        Double lsimilarity = 1 - (lcoeff.doubleValue()/Math.max(needle1.length(), needle2.length()));
        printResult(lcoeff.toString() + "; Similarity (artifcial)= " + lsimilarity.toString() , "LevenshteinDistance (" + treshold.toString() + ")");


        // Jaro-Wrinkler Distance
        //------------------------
        JaroWrinklerDistance jwd = new JaroWrinklerDistance();
        Double jwdcoeff = jwd.apply(needle1, needle2);
        Double jwdsimilarity =  (jwdcoeff/Math.max(needle1.length(), needle2.length()));
        printResult(jwdcoeff.toString(), "Jaro-Wrinkler");

        // Cosine Distance
        //-----------------
        CosineDistance cd = new CosineDistance();
        Double cdcoeff = cd.apply(needle1, needle2);
        printResult(cdcoeff.toString(), "Cosine Distance");

        // Smith-Waterman
        //---------------
        SmithWaterman sw = new SmithWaterman(needle1, needle2);
        Integer swcoeff = sw.computeSmithWaterman();
        Double swrelative = swcoeff.doubleValue()/Math.max(needle1.length(),needle2.length());
        printResult(swcoeff.toString(), "Smith Waterman");

        //sw.printMatrix();

        // Multi Check
        //==============
        System.out.println("");
        DummyStrings ds = new DummyStrings();
        findMatches("SandraInLiving", ds.getDummy(1), treshold);
    }

    public static void printResult(String res, String algo){
        System.out.println(algo + ": " + res);
    }

    public static List<String>findMatches(String needle, ArrayList<String> hayStack, Double treshold){
        ArrayList<String> resmatches = new ArrayList<>();

        JaroWrinklerDistance jwd = new JaroWrinklerDistance();

        for(String straw : hayStack){
            Double jwdcoeff = jwd.apply(needle, straw);
            printCurrStrawMatch(straw, jwdcoeff, treshold);
        }

        return resmatches;
    }

    public static void printCurrStrawMatch(String straw, Double score, Double treshold){
        //System.out.println("Straw  |  Score  | Above treshold");

        System.out.print(straw + ": " + score.toString() + " - " );
        if(score>=treshold)
            System.out.println("V");
        else
            System.out.println("X");
    }

}
