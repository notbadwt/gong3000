package com.gong3000.data.mining.algorithm;

import java.util.Arrays;

public abstract class AAndBCalculator implements Calculator {

    Double[] xc;
    Double[] yc;

    public AAndBCalculator(Double[] xc, Double[] yc) {
        this.xc = xc;
        this.yc = yc;
    }


    double calculateVectorLength(Double[] c) {
        return Math.sqrt(Arrays.stream(c).map(item -> Math.pow(item, 2)).reduce(0d, (d1, d2) -> d1 + d2));
    }

    double calculateInnerProduct(Double[] a, Double[] b) {
        assert a.length == b.length;
        int size = a.length;
        double sum = 0d;
        for (int i = 0; i < size; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

}
