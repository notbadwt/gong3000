package com.gong3000.data.mining.algorithm;

import com.gong3000.data.mining.model.SimilarityModel;
import com.gong3000.data.mining.model.UserModel;
import com.gong3000.data.mining.modeling.ProductModeling;

import java.util.*;

public class ForecastScore implements Calculator {

    private UserModel userModel;
    private String productName;
    private List<SimilarityModel> similarityModelList;

    /**
     * 预测某一个用户对某个商品的评分
     */
    public ForecastScore(UserModel userModel, String productName, List<SimilarityModel> similarityModelList) {
        this.productName = productName;
        this.userModel = userModel;
        this.similarityModelList = similarityModelList;
    }

    //该用户所有评级的产品与目标产品与当前产品相似度与该用户对当前产品的评级的乘积的集合 除以 相似度绝对值的集合
    @Override
    public double calculate() throws Exception {
        LinkedHashMap<String, Double> scoreMap = userModel.getScoreMap();
        Set<String> keySet = scoreMap.keySet();
        double numerator = 0d;
        double denominator = 0d;

        for (String key : keySet) {
            Optional<SimilarityModel> result = similarityModelList.stream().filter(s -> (s.getKeyOne().equals(key) && s.getKeyTwo().equals(productName) || (s.getKeyOne().equals(productName) && s.getKeyTwo().equals(key)))).findAny();
            if (result.isPresent()) {
                double similarity = result.get().getValue();
                numerator += similarity * normalization(scoreMap.get(key), 5, 1);
                denominator += Math.abs(similarity);
            } else {
                throw new Exception("数据格式错误");
            }
        }

        System.out.println("fs_numerator : " + numerator);
        System.out.println("fs_denominator : " + denominator);

        return unnormalization(numerator / denominator, 5, 1);
    }

    /**
     * 归一化公式
     *
     * @param originalValue 原始值
     * @param max           最大值
     * @param min           最小值
     * @return -1～1；
     */
    private double normalization(double originalValue, double max, double min) {
        System.out.println("normalization originalValue : " + originalValue);
        double result = (((originalValue - min) * 2) - (max - min)) / (max - min);
        System.out.println("normalization result : " + result);
        return result;
    }

    private double unnormalization(double originalValue, double max, double min) {
        System.out.println("unnormalization originalValue : " + originalValue);
        double result = (0.5 * ((originalValue + 1) * (max - min)) + min);
        System.out.println("unnormalization result : " + result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        LinkedList<Double> kaceyMusgraves = new LinkedList<>(Arrays.asList(0d, 0d, 4d, 4d, 5d));
        LinkedList<Double> imagineDragons = new LinkedList<>(Arrays.asList(3d, 3d, 3d, 4d, 4d));
        LinkedList<Double> daftPunk = new LinkedList<>(Arrays.asList(5d, 4d, 0d, 4d, 5d));
        LinkedList<Double> lorde = new LinkedList<>(Arrays.asList(4d, 4d, 3d, 3d, 0d));
        LinkedList<Double> fallOutBoy = new LinkedList<>(Arrays.asList(1d, 1d, 1d, 1d, 3d));
        LinkedHashMap<String, LinkedList<Double>> testData = new LinkedHashMap<>();
        testData.put("kaceyMusgraves", kaceyMusgraves);
        testData.put("imagineDragons", imagineDragons);
        testData.put("daftPunk", daftPunk);
        testData.put("lorde", lorde);
        testData.put("fallOutBoy", fallOutBoy);

        ProductModeling productModeling = new ProductModeling(testData);
        List<SimilarityModel> modelList = productModeling.forEachCalculate().build();

        LinkedHashMap<String, Double> scoreMap = new LinkedHashMap<>();
        scoreMap.put("imagineDragons", 3d);
        scoreMap.put("daftPunk", 5d);
        scoreMap.put("lorde", 4d);
        scoreMap.put("fallOutBoy", 1d);
        UserModel userModel = new UserModel("David", scoreMap);

        ForecastScore forecastScore = new ForecastScore(userModel, "kaceyMusgraves", modelList);
        double result = forecastScore.calculate();
        assert result == 4.509984014468131d;
        System.out.println(forecastScore.calculate());


    }
}
