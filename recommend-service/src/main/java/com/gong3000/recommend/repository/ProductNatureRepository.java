package com.gong3000.recommend.repository;

import com.gong3000.recommend.entity.product.ProductNature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductNatureRepository extends JpaRepository<ProductNature, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM product_nature AS pn WHERE pn.type=0 GROUP BY pn.name")
    List<ProductNature> findByGroupName();

    @Query(nativeQuery = true, value = "SELECT * FROM product_nature AS pn WHERE pn.id IN (SELECT product_nature_id FROM category_nature AS cn WHERE cn.category_id = ?1)")
    List<ProductNature> findbyCategoryId(Long categoryId);

}
