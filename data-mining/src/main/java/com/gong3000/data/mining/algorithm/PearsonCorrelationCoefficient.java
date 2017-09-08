package com.gong3000.data.mining.algorithm;

import java.util.Arrays;

/**
 * 两个事物之间的相似度
 * 皮尔逊相关系数算法，根据评级求两个用户的相似度,根据这两个用户对相同物品的评级
 * 分析：入参，两个用户对几个物品评级的集合或者向量 返回结果：-1～1 相似度，从小到大
 * <p>
 * 抽象：二维坐标系中传入两个向量，计算他们的相似度（y轴的值 数组的index代表x轴）
 * <p>
 * 数据受分数贬值，不同用户使用不同的评级范围，使用该算法
 */
public class PearsonCorrelationCoefficient extends AAndBCalculator {


    public PearsonCorrelationCoefficient(Double[] xc, Double[] yc) {
        super(xc, yc);
    }

    @Override
    public double calculate() {
        return calculateNumerator() / calculateDenominator();
    }

    private double calculateNumerator() {
        double result = calculateNumeratorLeft() - calculateNumeratorRight();
        System.out.println("numerator : " + result);
        return result;
    }

    private double calculateDenominator() {
        double xcResult = calculateDenominatorTemplate(xc);
        double ycResult = calculateDenominatorTemplate(yc);
        double result = xcResult * ycResult;
        System.out.println("denominator : " + result);
        return result;

    }

    private double calculateNumeratorLeft() {
        return calculateInnerProduct(xc, yc);
    }

    private double calculateNumeratorRight() {
        int size = xc.length;
        double sumx = Arrays.stream(xc).reduce(0d, (d1, d2) -> d1 + d2);
        double sumy = Arrays.stream(yc).reduce(0d, (d1, d2) -> d1 + d2);
        double result = sumx * sumy / size;
        System.out.println("numerator_right: " + result);
        return result;
    }

    private double calculateDenominatorTemplate(Double[] c) {
        int size = c.length;
        double xPowerSum = Arrays.stream(c).map(item -> Math.pow(item, 2)).reduce(0d, (d1, d2) -> d1 + d2);
        System.out.println("xPowerSum : " + xPowerSum);
        double xSumPower = Math.pow(Arrays.stream(c).reduce(0d, (d1, d2) -> d1 + d2), 2);
        System.out.println("xSumPower : " + xSumPower);
        return Math.sqrt(xPowerSum - (xSumPower / size));
    }

    public static void main(String[] args) {
        Double[] xc = {4.75d, 4.5d, 5d, 4.25d, 4d};
        Double[] yc = {4d, 3d, 5d, 2d, 1d};
        PearsonCorrelationCoefficient pearsonCorrelationCoefficient = new PearsonCorrelationCoefficient(xc, yc);
        double result = pearsonCorrelationCoefficient.calculate();
        assert result == 0.9999999999999998d;
        System.out.println("total result : " + result);

    }


}
