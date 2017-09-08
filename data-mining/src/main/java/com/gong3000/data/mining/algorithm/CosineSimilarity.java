package com.gong3000.data.mining.algorithm;

/**
 * 余弦相似度算法
 * 用于计算稀疏数据
 * <p>
 * 内积计算 ／ 向量距离的乘积
 */
public class CosineSimilarity extends AAndBCalculator {

    public CosineSimilarity(Double[] xc, Double[] yc) {
        super(xc, yc);
    }

    @Override
    public double calculate() {
        return calculateNumerator() / calculateDenominator();
    }

    private double calculateNumerator() {
        return calculateInnerProduct(xc, yc);
    }


    private double calculateDenominator() {
        return calculateVectorLength(xc) * calculateVectorLength(yc);
    }


    public static void main(String[] args) {
        Double[] xc = {4.75d, 4.5d, 5d, 4.25d, 4d};
        Double[] yc = {4d, 3d, 5d, 2d, 1d};
        CosineSimilarity pearsonCorrelationCoefficient = new CosineSimilarity(xc, yc);
        double result = pearsonCorrelationCoefficient.calculate();
        assert result == 0.9351534585705217d;
        System.out.println("total result : " + result);
    }
}
