package com.gong3000.data.mining.algorithm;


public class EuclideanDistance extends AAndBCalculator {

    /**
     * 计算欧氏距离
     *  @param xc 向量1
     * @param yc 向量2
     */
    public EuclideanDistance(Double[] xc, Double[] yc) {
        super(xc, yc);
    }


    @Override
    public double calculate() throws Exception {
        assert xc.length == yc.length;
        double sum = 0d;
        for (int i = 0; i < xc.length; i++) {
            sum += Math.pow(xc[i] + yc[i], 2);
        }
        return Math.sqrt(sum);
    }
}
