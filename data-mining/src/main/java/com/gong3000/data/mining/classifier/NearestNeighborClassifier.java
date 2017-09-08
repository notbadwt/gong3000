package com.gong3000.data.mining.classifier;

import com.gong3000.data.mining.algorithm.ImprovedStandardScore;
import com.gong3000.data.mining.model.CharacteristicSpace;
import com.gong3000.data.mining.model.Sample;

import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class NearestNeighborClassifier implements Classifier {

    private LinkedList<LinkedList<Double>> vectorCollection = new LinkedList<>();

    private CharacteristicSpace characteristicSpace;

    private LinkedHashMap<String, LinkedList<Double>> propertyCollectionMap = new LinkedHashMap<>();

    public NearestNeighborClassifier(CharacteristicSpace characteristicSpace) {
        this.characteristicSpace = characteristicSpace;
    }

    /**
     * 初始化数据，将特征空间转换成，double数组的数组
     */
    @Override
    public void initModelData() {
        //@TODO 如何保证属性列表长度一致
        List<Sample> samples = characteristicSpace.getSampleList();
        for (Sample sample : samples) {
            LinkedList<Double> vector = new LinkedList<>();
            LinkedHashMap<String, Double> numbersMap = sample.getNumbers();
            for (String key : numbersMap.keySet()) {
                vector.add(numbersMap.get(key));
                LinkedList<Double> propertyCollection;
                if (propertyCollectionMap.containsKey(key)) {
                    propertyCollection = propertyCollectionMap.get(key);
                } else {
                    propertyCollection = new LinkedList<>();
                    propertyCollectionMap.put(key, propertyCollection);
                }
                propertyCollection.add(numbersMap.get(key));

            }
            vectorCollection.add(vector);
        }


    }

    /**
     * 归一化数据集，该实现需要保证，每个样本的每个属性都有值。列表长度一致
     */
    @Override
    public void normalization() throws Exception {
        for (LinkedList<Double> vector : vectorCollection) {
            normalization(vector);
        }
    }

    private void normalization(LinkedList<Double> vector) throws Exception {
        for (int i = 0; i < vector.size(); i++) {
            String propertyKey = propertyCollectionMap.keySet().toArray()[i].toString();
            LinkedList<Double> propertyCollection = propertyCollectionMap.get(propertyKey);
            Double[] relationCollection = new Double[propertyCollection.size()];
            for (int k = 0; k < propertyCollection.size(); k++) {
                relationCollection[k] = propertyCollection.get(k);
            }
            ImprovedStandardScore improvedStandardScore = new ImprovedStandardScore(relationCollection);
            double normalizationResult = improvedStandardScore.calculate(vector.get(i));
            vector.set(i, normalizationResult);
        }
    }


    /**
     * 在特征空间中找到与样本距离最近的特征
     *
     * @param targetSample 目标样本
     * @return
     * @TODO 把新的样本添加到特征空间中
     */
    @Override
    public Sample nearestNeighbor(Sample targetSample) throws Exception {
        LinkedList<Double> vector = new LinkedList<>();
        for (String key : targetSample.getNumbers().keySet()) {
            vector.add(targetSample.getNumbers().get(key));
        }
        normalization(vector);
        Double[] xc = vector.toArray(new Double[vector.size()]);
        List<Double> result = vectorCollection.stream().map(d -> {
            try {
                return manhattan(xc, d.toArray(new Double[d.size()]));
            } catch (Exception e) {
                e.printStackTrace();
                return -1d;
            }
        }).collect(Collectors.toList());
        //找到最小值的下标
        int smallestIndex = 0;
        Double smallestValue = result.get(smallestIndex);
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i) < smallestValue) {
                smallestIndex = i;
                smallestValue = result.get(i);
            }
        }

        return characteristicSpace.getSampleList().get(smallestIndex);

    }

    public List<Sample> nearestNeighbor(Sample targetSample, int k) throws Exception {
        LinkedList<Double> vector = new LinkedList<>();
        for (String key : targetSample.getNumbers().keySet()) {
            vector.add(targetSample.getNumbers().get(key));
        }
        normalization(vector);
        Double[] xc = vector.toArray(new Double[vector.size()]);
        List<Double> result = vectorCollection.stream().map(d -> {
            try {
                return manhattan(xc, d.toArray(new Double[d.size()]));
            } catch (Exception e) {
                e.printStackTrace();
                return -1d;
            }
        }).collect(Collectors.toList());
        //找到最小值的下标
        int smallestIndex = 0;
        List<Integer> smallestIndexes = new ArrayList<>(k);
        LinkedList<HashMap<Integer, Double>> indexAndValueList = new LinkedList<>();
        Double smallestValue = result.get(smallestIndex);
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i) < smallestValue) {
                smallestIndex = i;
                smallestValue = result.get(i);
            }

            final double value = result.get(i);

            if (indexAndValueList.size() < k) {
                HashMap<Integer, Double> indexAndValue = new HashMap<>();
                indexAndValue.put(i, result.get(i));
                indexAndValueList.add(indexAndValue);
            } else {
                Optional<HashMap<Integer, Double>> temp = indexAndValueList.stream().filter(m ->
                        m.keySet().stream().anyMatch(key ->
                                m.get(key) > value
                        )).findAny();

                if (temp.isPresent()) {
                    HashMap<Integer, Double> mapTemp = temp.get();
                    mapTemp.clear();
                    mapTemp.put(i, value);
                }

            }

        }

        return indexAndValueList.stream().map(indexAndValue -> indexAndValue.keySet().iterator().next()).map(index -> characteristicSpace.getSampleList().get(index)).collect(Collectors.toList());


    }

}
