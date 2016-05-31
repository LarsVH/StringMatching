package com.lars;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;

/**
 * Created by lars on 30.05.16.
 */
public class Util {
    Double treshold = -1.0;


    public Boolean checkTreshold(Double score){
        if(score>=treshold)
            return true;
        else
            return false;
    }


    public ArrayList<ResultTriple> performAlgorithms(String needle1, String needle2, Double treshold){
        ArrayList<ResultTriple> res = new ArrayList<>();

        this.treshold = treshold;

        // Algorithms
        //========================================================
        // Sorensen-Dice (Case-Sensitive!)
        //--------------
        SorensenDice sd = new SorensenDice();
        Double sdcoeff = sd.diceCoefficient(needle1, needle2);

        res.add(new ResultTriple("Sorensen-Dice", sdcoeff, checkTreshold(sdcoeff)));
        //printResult(f.format(sdcoeff).toString(), "Sorensen-Dice");

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
        res.add(new ResultTriple("Hamming Distance", hdsimilarity, checkTreshold(hdsimilarity)));
        //printResult(hdcoeffs.toString() + "; Similarity = " + hdsimilarity.toString(), "Hamming Distance");


        // Levenshtein Distance
        //---------------------
        Double tresholdperc = treshold*100;
        LevenshteinDistance ld = new LevenshteinDistance(tresholdperc.intValue());
        Integer lcoeff = ld.apply(needle1, needle2);
        Double lsimilarity = 1 - (lcoeff.doubleValue()/Math.max(needle1.length(), needle2.length()));

        res.add(new ResultTriple("Levenshtein Distance", lsimilarity, checkTreshold(lsimilarity)));
        //printResult(lcoeff.toString() + "; Similarity (artifcial)= " + lsimilarity.toString() , "LevenshteinDistance (" + treshold.toString() + ")");


        // Jaro-Wrinkler Distance
        //------------------------
        JaroWrinklerDistance jwd = new JaroWrinklerDistance();
        Double jwdcoeff = jwd.apply(needle1, needle2);
        Double jwdsimilarity =  (jwdcoeff/Math.max(needle1.length(), needle2.length()));

        res.add(new ResultTriple("Jaro-Wrinkler Distance", jwdcoeff, checkTreshold(jwdcoeff)));
        //printResult(jwdcoeff.toString(), "Jaro-Wrinkler");

        // Cosine Distance
        //-----------------
       // CosineDistance cd = new CosineDistance();
       // Double cdcoeff = cd.apply(needle1, needle2);

       // res.add(new ResultTriple("Cosine Distance", cdcoeff, false));
        // printResult(cdcoeff.toString(), "Cosine Distance");

        // Smith-Waterman
        //---------------
        SmithWaterman sw = new SmithWaterman(needle1, needle2);
        Integer swcoeff = sw.computeSmithWaterman();
        Double swrelative = swcoeff.doubleValue()/Math.max(needle1.length(),needle2.length());

        res.add(new ResultTriple("Smith-Waterman", swcoeff.doubleValue(), false));
        //printResult(swcoeff.toString(), "Smith Waterman");

        //sw.printMatrix();

        return res;
    }

    // Compute suggestions, only with Jaro-Winkler and external sources
    public ArrayList<String> computeSuggestions(String needle, ArrayList<String> hayStack, Double treshold){
        ArrayList<String> results = new ArrayList<>();



        for(String currNeedle : hayStack){
            boolean jaroWinkleraccept = false;
            JaroWrinklerDistance jwd = new JaroWrinklerDistance();
            Double jwdcoeff = jwd.apply(needle, currNeedle);
            if(jwdcoeff >= treshold)
                jaroWinkleraccept = true;

            // synonyms
            //TODO

        }
        return results;
    }

    // Get synonyms for word
    public ArrayList<String> getSynonyms(String word) {
        ArrayList<String> results = new ArrayList<>();
        String SYNONYMURL = "http://words.bighugelabs.com/api/2/e6f43a73aed761daf738a2072ffbd38d/";
        String SYNONYMURLSUFFIX = "/json";

        try {
            HttpResponse<String> request = Unirest.get(SYNONYMURL + word + SYNONYMURLSUFFIX).asString();
            System.out.println(request.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return results;
    }

}
