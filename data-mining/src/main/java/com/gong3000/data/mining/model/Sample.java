package com.gong3000.data.mining.model;

import java.util.LinkedHashMap;

/**
 * 样本
 */
public class Sample {

    private ClassType classType;
    private String label;
    private LinkedHashMap<String, Double> numbers;

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LinkedHashMap<String, Double> getNumbers() {
        return numbers;
    }

    public void setNumbers(LinkedHashMap<String, Double> numbers) {
        this.numbers = numbers;
    }
}
