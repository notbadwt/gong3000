package com.gong3000.recommend.repository;

import com.gong3000.recommend.entity.product.JdCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JdCategoryRepository extends JpaRepository<JdCategory, Long> {
}
