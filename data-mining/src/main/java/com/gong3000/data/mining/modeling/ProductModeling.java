package com.gong3000.data.mining.modeling;

import com.gong3000.data.mining.algorithm.CosineSimilarityByProduct;
import com.gong3000.data.mining.model.SimilarityModel;

import java.util.*;

import static com.gong3000.data.mining.algorithm.CosineSimilarityByProduct.calculateUAverage;

public class ProductModeling {


    /**
     * 建模结果，元素类型代表key1与key2的相似度，key1与key2分别表示两个商品，每个商品有不同的标识
     */
    private List<SimilarityModel> modelResult;

    private LinkedHashMap<String, LinkedList<Double>> referenceData;

    private Double[][] xy = null;

    private Double[] uavg = null;

    /**
     * 产品建模，计算出所有商品相互之间的相似度，用作模型
     * 这里会使用基于商品的余弦相似度算法
     * 给定一个表，该表是每个用户对每个商品的评级，每个独立的数组或者列表，代表某个商品的评级集合，每个商品中集合的顺序都是一一对应的，index代表是一个用户
     * 可以把用户抽象成 a，b，c，c，e 或者 c[0] c[1] c[2] c[3] c[4] c代表某个商品评级的集合
     * 输出结果是一个类似矩阵的结构（相似度矩阵）
     */
    public ProductModeling(LinkedHashMap<String, LinkedList<Double>> referenceData) {
        this.referenceData = referenceData;
    }

    public ProductModeling forEachCalculate() throws Exception {
        //依次计算形成中间结果 调用 CosineSimilarityByProduct 算法
        //1.首先将referenceData转换成二维数组的形式
        //限制：referenceData中value的所有值长度都必须一样
        Set<String> keySet = referenceData.keySet();
        int index = 0;
        for (String key : keySet) {
            LinkedList<Double> dataCollection = referenceData.get(key);
            if (xy == null) {
                xy = new Double[keySet.size()][dataCollection.size()];
            }
            Double[] temp = new Double[dataCollection.size()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = dataCollection.get(i);
            }

            xy[index] = temp;
            index++;
        }

        if (xy == null) {
            throw new Exception("参考数据格式不正确");
        }

        //2.通过算法提供的工具酸楚每个用户的评级的平均值
        int size = xy[0].length;
        uavg = new Double[size];
        for (int i = 0; i < size; i++) {
            uavg[i] = calculateUAverage(xy, i);
        }


        return this;
    }

    public List<SimilarityModel> build() throws Exception {
        //根据 xy[][] 以及uavg 逐个生成模型数据
        if (xy == null || uavg == null) {
            throw new Exception("中间数据创建失败，无法建模");
        }

        //xy[0] ~ xy[i] 计算所有index的组合
        List<List<Integer>> tupleCollection = permutationFromArray(xy.length);

        Object[] keyArray = referenceData.keySet().toArray();

        for (List<Integer> tuple : tupleCollection) {
            CosineSimilarityByProduct cosineSimilarityByProduct = new CosineSimilarityByProduct(xy[tuple.get(0)], xy[tuple.get(1)], uavg);
            double value = cosineSimilarityByProduct.calculate();
            SimilarityModel similarityModel = new SimilarityModel(keyArray[tuple.get(0)].toString(), keyArray[tuple.get(1)].toString(), value);
            if (modelResult == null) {
                modelResult = new ArrayList<>();
            }
            modelResult.add(similarityModel);
        }

        System.out.println(Arrays.toString(modelResult.toArray()));

        return modelResult;
    }


    private List<List<Integer>> permutationFromArray(int length) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (i - 1 >= 0) {
                for (int j = i - 1; j >= 0; j--) {
                    List<Integer> tuple = new ArrayList<>(2);
                    tuple.add(i);
                    tuple.add(j);
                    result.add(tuple);
                    System.out.println("[ " + i + " : " + j + " ]");
                }
            }
        }
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
        productModeling.forEachCalculate().build();

    }


}
