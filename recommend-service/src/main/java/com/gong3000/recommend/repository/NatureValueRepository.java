package com.gong3000.recommend.repository;

import com.gong3000.recommend.entity.product.NatureValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

public interface NatureValueRepository extends JpaRepository<NatureValue, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM nature_value AS nv WHERE nv.id IN (SELECT nature_value_id FROM product_nature_value_bak AS pnvb WHERE pnvb.sku_id=?1)")
    List<NatureValue> findBySkuId(Long skuId);

    List<NatureValue> findByNatureId(Long natureId);

}
