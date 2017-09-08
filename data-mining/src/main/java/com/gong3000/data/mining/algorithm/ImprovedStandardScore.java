package com.gong3000.data.mining.algorithm;


import java.util.Arrays;

public class ImprovedStandardScore implements Formula {

    /**
     * 相关数据集合
     */
    private Double[] relationCollection;
    /**
     * 标准差
     */
    private double absoluteStandardDeviation;

    /**
     * 中值
     */
    private double middle = -1d;


    /**
     * 改进后的标准分数算法 属于建模工具
     *
     * @param relationCollection
     */
    public ImprovedStandardScore(Double[] relationCollection) {
        this.relationCollection = relationCollection;
        this.absoluteStandardDeviation = calculateAbsoluteStandardDeviation();
    }

    @Override
    public double calculate(double... alphas) throws Exception {
        if (alphas.length != 1) {
            throw new Exception("参数错误");
        }
        double targetValue = alphas[0];
        return (targetValue - middle) / absoluteStandardDeviation;

    }

    private double calculateMiddleValue() {
        if (this.middle == -1d) {

            Double[] temp = relationCollection.clone();
            Arrays.sort(temp);
            double biggest = temp[temp.length - 1];
            double smallest = temp[0];
            this.middle = (biggest + smallest) / 2;
        }
        return middle;
    }

    private double calculateAbsoluteStandardDeviation() {
        return Arrays.stream(relationCollection).map(x -> Math.abs(x - calculateMiddleValue())).reduce(0d, (d1, d2) -> d1 + d2) / relationCollection.length;
    }

}
