package com.gong3000.data.mining.model;

/**
 * keyOne 与 keyTwo 标识了一个对象
 */
public class SimilarityModel {

    private String keyOne;
    private String keyTwo;
    private double value;

    public SimilarityModel(String keyOne, String keyTwo, double value) {
        this.keyOne = keyOne;
        this.keyTwo = keyTwo;
        this.value = value;
    }

    public String getKeyOne() {
        return keyOne;
    }

    public void setKeyOne(String keyOne) {
        this.keyOne = keyOne;
    }

    public String getKeyTwo() {
        return keyTwo;
    }

    public void setKeyTwo(String keyTwo) {
        this.keyTwo = keyTwo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SimilarityModel{" +
                "keyOne='" + keyOne + '\'' +
                ", keyTwo='" + keyTwo + '\'' +
                ", value=" + value +
                '}' + '\n';
    }
}
