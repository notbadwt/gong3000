package com.gong3000.recommend.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gaoxiang on 17/1/19.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "product_sku")
public class ProductSku implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_spu_id")
    private Long spuId;


    @Column(name = "name")
    private String name;

    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    @Column(name = "stocks")
    private Long stocks;


    @Column(name = "gift")
    private String gift;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_price")
    private BigDecimal supplierPrice;

    @Column(name = "official_price")
    private BigDecimal officialPrice;

    @Column(name = "update_price_time")
    private Date updatePriceTime;

    @Column(name = "release_time")
    private Date releaseTime;

    @Column(name = "vendible")
    private Boolean vendible;//可销售属性


    @Column(name = "shareable")
    private Boolean shareable;

    @Column(name = "market_price")
    private BigDecimal marketPrice;
    //仅作为封装使用 不做持久化
    @Transient
    private String path;

    @Column(name = "jd_sku_id")
    private Long jdSkuId = -1L;

}
