package com.gong3000.recommend.repository;

import com.gong3000.recommend.entity.product.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM product_sku AS sku WHERE sku.product_spu_id IN (SELECT id FROM product_spu AS spu WHERE spu.category_id = ?1 AND spu.is_delete=FALSE AND spu.un_shelve=FALSE ) AND sku.is_delete=FALSE ")
    List<ProductSku> findByCategoryId(Long categoryId);

}
