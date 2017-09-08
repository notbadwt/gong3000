package com.gong3000.data.mining.classifier;

import com.gong3000.data.mining.algorithm.EuclideanDistance;
import com.gong3000.data.mining.algorithm.ManhattanDistance;
import com.gong3000.data.mining.model.CharacteristicSpace;
import com.gong3000.data.mining.model.Sample;

import java.util.List;

/**
 * 通过对特征空间与样本进行距离计算来给样本分类
 */
public interface Classifier {

    /**
     * 初始化数据集
     */
    void initModelData();

    /**
     * 标准化，归一化
     */
    void normalization() throws Exception;

    /**
     * 获取最近的元素，通过最近的元素来判定分类 .getClassType
     */
    Sample nearestNeighbor(Sample targetItem) throws Exception;

    List<Sample> nearestNeighbor(Sample targetItem, int k) throws Exception;

    /**
     * 计算曼哈顿距离
     */
    default double manhattan(Double[] xc, Double[] yc) throws Exception {
        ManhattanDistance manhattanDistance = new ManhattanDistance(xc, yc);
        return manhattanDistance.calculate();
    }

    /**
     * 计算欧式距离
     */
    default double euclidean(Double[] xc, Double[] yc) throws Exception {
        EuclideanDistance euclideanDistance = new EuclideanDistance(xc, yc);
        return euclideanDistance.calculate();
    }

}
