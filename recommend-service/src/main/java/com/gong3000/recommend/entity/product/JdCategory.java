package com.gong3000.recommend.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yue on 2017/3/31.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "jd_category")
public class JdCategory implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "cat_class")
    private Integer catClass;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "banner_type")
    private Integer bannerType;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "life_service")
    private Boolean lifeService;

    @Column(name = "jd_category_id")
    private Long jdCategoryId;
}
