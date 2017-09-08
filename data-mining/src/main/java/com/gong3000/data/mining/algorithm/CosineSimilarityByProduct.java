package com.gong3000.data.mining.algorithm;

import java.util.Arrays;

public class CosineSimilarityByProduct extends AAndBCalculator {


    private Double[] uavg;


    /**
     * 基于物品的余弦相似度算法
     * 三个数组顺序保持一一对应关系
     * 建模
     *
     * @param xc   所有样本用户对x的评分集合
     * @param yc   所有样本用户对y的评分集合
     * @param uavg 所有用户对所有物品评级的平均值集合
     */
    public CosineSimilarityByProduct(Double[] xc, Double[] yc, Double[] uavg) {
        super(xc, yc);
        this.uavg = uavg;
        System.out.println("uavg : " + Arrays.toString(uavg));
        System.out.println("xc : " + Arrays.toString(xc));
        System.out.println("yc : " + Arrays.toString(yc));
    }

    @Override
    public double calculate() {
        return calculateNumerator() / calculateDenominator();
    }

    private double calculateNumerator() {
        assert xc.length == yc.length;
        int size = getLength(xc);
        double sum = 0d;
        for (int i = 0; i < size; i++) {
            if (xc.length < i + 1 || yc.length < i + 1) {
                continue;
            }

            if (xc[i] == 0 || yc[i] == 0) {
                continue;
            }
            sum += (xc[i] - uavg[i]) * (yc[i] - uavg[i]);
        }
        System.out.println("numerator : " + sum);
        return sum;
    }

    private double calculateDenominator() {
        double xcResult = calculateDenominatorTemplate(xc, yc);
        double ycResult = calculateDenominatorTemplate(yc, xc);
        System.out.println("xcResult : " + xcResult);
        System.out.println("ycResult : " + ycResult);
        return xcResult * ycResult;
    }

    /**
     * 分母计算模版
     *
     * @param c 计算主体
     * @param d 辅助参数
     */
    private double calculateDenominatorTemplate(Double[] c, Double[] d) {
        double sum = 0d;
        int size = c.length;

        for (int i = 0; i < size; i++) {
            //容错
            if (c.length < i + 1 || d.length < i + 1) {
                continue;
            }
            if (c[i] == 0 || d[i] == 0) {
                continue;
            }
            sum += Math.pow(c[i] - uavg[i], 2);
        }
        return Math.sqrt(sum);
    }


    public static void main(String[] args) {
        Double[][] xy = {{0d, 0d, 4d, 4d, 5d}, {3d, 3d, 3d, 4d, 4d}, {5d, 4d, 0d, 4d, 5d}, {4d, 4d, 3d, 3d, 0d}, {1d, 1d, 1d, 1d, 3d}};
        Double[] uavg;
        int size = xy[0].length;
        uavg = new Double[size];
        for (int i = 0; i < size; i++) {
            uavg[i] = calculateUAverage(xy, i);
        }
        CosineSimilarityByProduct cosineSimilarityByProduct = new CosineSimilarityByProduct(xy[1], xy[3], uavg);
        double result = cosineSimilarityByProduct.calculate();
        assert result == -0.2525265372291518d;
        System.out.println(result);
    }

    public static double calculateUAverage(Double[][] xy, int index) {
        double sum = 0;
        int len = 0;
        for (Double[] x : xy) {
            if (x.length < index + 1) {
                sum += 0;
                len++;
            } else if (x[index] != 0) {
                sum += x[index];
                len++;
            }
        }
        return sum / len;
    }


    private int getLength(Double[] c) {
//        int size = c.length;
//        return (int) c[size - 1];
        return c.length;
    }

}
