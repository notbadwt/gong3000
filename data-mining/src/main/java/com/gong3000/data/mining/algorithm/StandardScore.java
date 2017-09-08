package com.gong3000.data.mining.algorithm;

import java.util.Arrays;
import java.util.Map;

public class StandardScore implements Formula {

    /**
     * 相关数据集合
     */
    private Double[] relationCollection;
    /**
     * 标准差
     */
    private double standardDeviation;

    /**
     * 相关数据均值
     */
    private double avg = -1d;


    /**
     * 计算普通标准分数的算法类
     *
     * @param relationCollection 目标值的相关属性值集合
     */
    public StandardScore(Double[] relationCollection) {
        this.relationCollection = relationCollection;
        this.standardDeviation = calculateStardDeviation();
    }

    /**
     * 计算标准分数
     *
     * @param alphas 目标值
     * @return 目标值归一化的结果
     * @throws Exception
     */
    @Override
    public double calculate(double... alphas) throws Exception {
        if (alphas.length != 1) {
            throw new Exception("参数错误");
        }
        double targetValue = alphas[0];
        return (targetValue - avg) / standardDeviation;
    }


    private double calculateAvg() {
        if (avg == -1) {
            avg = Arrays.stream(relationCollection).reduce(0d, (d1, d2) -> d1 + d2) / relationCollection.length;
        }
        return avg;
    }

    private double calculateStardDeviation() {
        return Math.sqrt(Arrays.stream(relationCollection).map(x -> Math.pow(x - calculateAvg(), 2)).reduce(0d, (d1, d2) -> d1 + d2) / relationCollection.length);
    }
}
