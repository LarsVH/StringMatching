package com.lars;

/**
 * Created by lars on 30.05.16.
 */
public class ResultTriple {
    String algo;
    Double score;
    Boolean accepted;

    public ResultTriple(){}

    public ResultTriple(String algo, Double score, Boolean accepted) {
        this.algo = algo;
        this.score = score;
        this.accepted = accepted;
    }

    public String getAlgo() {
        return algo;
    }

    public Double getScore() {
        return score;
    }

    public Boolean getAccepted() {
        return accepted;
    }
}
