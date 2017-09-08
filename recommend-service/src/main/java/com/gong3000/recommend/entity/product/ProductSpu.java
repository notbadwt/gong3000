package com.gong3000.recommend.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by gaoxiang on 17/1/19.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "product_spu")
public class ProductSpu implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "un_shelve")
    private Boolean unShelve;//下架


    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "self_brand_id")
    private Long selfBrandId;

    @Column(name = "remark_detail")
    private String remarkDetail;//商品属性

    @Column(name = "sell_description")
    private String sellDescription;//售后说明

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "after_sale_page")
    private String afterSalePage;//售后页面地址

    @Column(name = "is_special")
    private Boolean isSpecial;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "is_activity")
    private Boolean isActivity;

    @Column(name = "is_delete")
    private Boolean haveDelete;//是否被删除

    @Column(name = "min_price")
    private BigDecimal minPrice;

    @Column(name = "max_price")
    private BigDecimal maxPrice;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "return_back")
    private Boolean returnBack;//是否支持七天无理由退货

    @Column(name = "sales")
    private Long sales;

    @Column(name = "is_jd")
    private Boolean asJd;

    @Column(name = "free_shipping")
    private Boolean freeShipping;//是否免运费

    @PreUpdate
    public void preUpdate() {
        updateTime = new Timestamp(System.currentTimeMillis());
    }
//不做持久化的数据 仅作为关联查询
    @Transient
    private String path;

}
