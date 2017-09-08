package com.gong3000.recommend.controller;

import com.gong3000.recommend.service.ModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendController {

    @Autowired
    private ModelingService modelingService;

    @RequestMapping({"/get_recommend_product_sku_id"})
    public Long getRecommendProductSkuId(Long id) {
        return modelingService.getRecommendProduct(id);
    }

}
