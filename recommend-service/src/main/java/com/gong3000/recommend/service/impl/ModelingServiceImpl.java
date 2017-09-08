package com.gong3000.recommend.service.impl;

import com.gong3000.data.mining.model.SimilarityModel;
import com.gong3000.data.mining.modeling.ProductModeling;
import com.gong3000.recommend.entity.product.NatureValue;
import com.gong3000.recommend.entity.product.ProductNature;
import com.gong3000.recommend.entity.product.ProductSku;
import com.gong3000.recommend.repository.*;
import com.gong3000.recommend.service.ModelingService;
import com.gong3000.recommend.service.util.NatureUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 初步计划将归一化的结果存储到redis中，标识（Long类型）做为key 在redis中为一个list结构
 * 根据商品的特性，应该按照一二三级分类来分别建模，因为每个分类中的商品应该是最相近
 * 最初：系统拿到一个商品的时候首先拿到商品的标识，再拿到跟该商品最邻近的相似商品推荐给用户
 * <p>
 * 用手机分类做一个例子
 * <p>
 * 查处手机分类的所有productNature
 * 列出每一个productNature的曲直范围
 * 对曲直范围中每个值做标准化（转换成一个区间数字）
 * 查询每个手机之间的相似度
 * 传入一个商品，查询与之相似度最小的一件商品，进行推荐
 * 或者，根据用户已经购买的商品来判断用户的喜好，
 */
@Component
public class ModelingServiceImpl implements ModelingService {

    /**
     * 属性类型标识列表，用来记录列表的顺序
     */
    private static LinkedList<Long> natureCategoryIdentificationList = new LinkedList<>();

    /**
     * 属性类型标识表，每个标识对应一个值
     */
    private static LinkedHashMap<Long, String> natureCategoryMap = new LinkedHashMap<>();

    /**
     * 属性类型对应的属性值列表的映射
     */
    private static LinkedHashMap<String, List<String>> natureCategoryValueListMap = new LinkedHashMap<>();


    /**
     * 属性类型对应的属性值列表的映射
     */
    private static LinkedHashMap<String, List<Double>> natureCategoryNormalizationValueListMap = new LinkedHashMap<>();

    /**
     * 每个商品对应的属性值列表的列表
     */
    private static LinkedHashMap<Long, LinkedList<String>> productNatureMap = new LinkedHashMap<>();

    /**
     * 每个商品对应的属性归一化结果列表的列表
     */
    private static LinkedHashMap<Long, LinkedList<Double>> productNatureNormalizationMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, LinkedList<Double>> productNatureNormalizationMapStrKey = new LinkedHashMap<>();

    private static List<SimilarityModel> similarityModelList;


    private ProductSkuRepository productSkuRepository;
    private ProductSpuRepository productSpuRepository;
    private NatureValueRepository natureValueRepository;
    private JdCategoryRepository jdCategoryRepository;
    private ProductNatureRepository productNatureRepository;

    @Autowired
    public ModelingServiceImpl(ProductSkuRepository productSkuRepository,
                               ProductSpuRepository productSpuRepository,
                               NatureValueRepository natureValueRepository,
                               JdCategoryRepository jdCategoryRepository,
                               ProductNatureRepository productNatureRepository) {
        this.productSkuRepository = productSkuRepository;
        this.productSpuRepository = productSpuRepository;
        this.natureValueRepository = natureValueRepository;
        this.jdCategoryRepository = jdCategoryRepository;
        this.productNatureRepository = productNatureRepository;
    }

    @PostConstruct
    private void init() throws Exception {
        //获取所有手机三级分类的属性类别
        List<ProductNature> productNatureList = productNatureRepository.findbyCategoryId(655L);
        productNatureList.forEach(productNature -> {
            List<NatureValue> natureValueList = natureValueRepository.findByNatureId(productNature.getId());
            List<String> natureValueLabelList = null;
            List<Double> natureValueNomalizationList = null;
            if (productNature.getName().equals("颜色")) {
                natureCategoryIdentificationList.add(productNature.getId());
                natureValueLabelList = natureValueList.stream().map(NatureValue::getValue).distinct().collect(Collectors.toList());
                natureValueNomalizationList = natureValueList.stream().map(NatureValue::getValue).distinct().map(NatureUtil::colorFilter).map(s -> s.contains(":") ? Double.parseDouble(s.split(":")[1]) : 0d).collect(Collectors.toList());
                natureCategoryMap.put(productNature.getId(), productNature.getName());
            } else if (productNature.getName().equals("容量")) {
                natureCategoryIdentificationList.add(productNature.getId());
                natureValueLabelList = natureValueList.stream().map(NatureValue::getValue).distinct().collect(Collectors.toList());
                natureValueNomalizationList = natureValueList.stream().map(NatureValue::getValue).distinct().map(NatureUtil::typeFilter).map(s -> s.contains(":") ? Double.parseDouble(s.split(":")[1]) : 0d).collect(Collectors.toList());
                natureCategoryMap.put(productNature.getId(), productNature.getName());
            }
//            else {
//                natureValueLabelList = natureValueList.stream().map(NatureValue::getValue).distinct().collect(Collectors.toList());
//            }

            if (natureValueLabelList != null) {
                natureCategoryValueListMap.put(productNature.getId() + "", natureValueLabelList);
            }

            if (natureValueNomalizationList != null) {
                natureCategoryNormalizationValueListMap.put(productNature.getId() + "", natureValueNomalizationList);
            }

        });

        System.out.println(new Gson().toJson(natureCategoryMap));

        List<ProductSku> productSkuList = productSkuRepository.findByCategoryId(655L);
        productSkuList.forEach(productSku -> {
            List<NatureValue> natureValueList = natureValueRepository.findBySkuId(productSku.getId());

            if (natureValueList != null && !natureValueList.isEmpty()) {

                String[] valueArray = new String[natureCategoryIdentificationList.size()];

                natureValueList.forEach(natureValue -> {
                    int index = natureCategoryIdentificationList.indexOf(natureValue.getNatureId());
                    if (index >= 0) {
                        valueArray[index] = natureValue.getValue() + ":" + natureValue.getNatureId();
                    }
                });

                LinkedList<String> valueList = new LinkedList<>(Arrays.asList(valueArray));

                LinkedList<Double> valueNormalList = valueList.stream().filter(Objects::nonNull).map(d -> {
                    String productNatureId = (d.split(":")[1]);
//                    System.out.println("productNatureId : " + productNatureId);
                    List<String> natureValues = natureCategoryValueListMap.get(productNatureId);
                    int valueIndex = natureValues.indexOf(d.split(":")[0]);
//                    System.out.println("productNature index : " + valueIndex);
                    if (valueIndex >= 0) {
                        return natureCategoryNormalizationValueListMap.get(productNatureId).get(valueIndex);
                    } else {
                        return 0d;
                    }
                }).collect(Collectors.toCollection(LinkedList::new));

                productNatureMap.put(productSku.getId(), valueList);
                productNatureNormalizationMap.put(productSku.getId(), valueNormalList);
                productNatureNormalizationMapStrKey.put(productSku.getId().toString(), valueNormalList);
            }
        });


//        System.out.println(new Gson().toJson(productNatureMap));
//
//        System.out.println(new Gson().toJson(productNatureNormalizationMap));
//
//        System.out.println(new Gson().toJson(natureCategoryValueListMap));
//
//        System.out.println(new Gson().toJson(natureCategoryNormalizationValueListMap));

        ProductModeling productModeling = new ProductModeling(productNatureNormalizationMapStrKey);
        similarityModelList = productModeling.forEachCalculate().build();


    }

    @Override
    public Long getRecommendProduct(Long productSkuId) {
        Optional<SimilarityModel> similarityModelOptional =
                similarityModelList.stream().filter(similarityModel -> similarityModel.getKeyTwo().equals(productSkuId.toString()) || similarityModel.getKeyOne().equals(productSkuId.toString()))
                        .max(Comparator.comparingDouble(SimilarityModel::getValue));

        if (similarityModelOptional.isPresent()) {
            SimilarityModel similarityModel = similarityModelOptional.get();
            if (similarityModel.getKeyOne().equals(productSkuId.toString())) {
                return Long.parseLong(similarityModel.getKeyTwo());
            } else if (similarityModel.getKeyTwo().equals(productSkuId.toString())) {
                return Long.parseLong(similarityModel.getKeyOne());
            } else {
                System.out.println("未找到相关商品");
                return null;
            }
        } else {
            return null;
        }

    }

}

