package com.gong3000.recommend.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cdl on 2017/1/9 0009.
 */
@Entity
@Table(name = "product_nature")
@Setter
@Getter
@ToString
public class ProductNature implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "type")
    private String type;


}
