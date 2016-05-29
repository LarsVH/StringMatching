package com.lars;

import com.lars.cosinedistance.CosineDistance;

import java.text.DecimalFormat;



public class Main {

    public static void main(String[] args) {
	// write your code here

        String needle1 = "Locations";
        String needle2 = "Locat";
        Double treshold = 1.0;
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
        printResult(jwdcoeff.toString(), "Jaro-Wrinkler Distance");

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

    }

    public static void printResult(String res, String algo){
        System.out.println(algo + ": " + res);
    }

}
