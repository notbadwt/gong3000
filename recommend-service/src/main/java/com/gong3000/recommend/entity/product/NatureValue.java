package com.gong3000.recommend.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gaoxiang on 17/1/13.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "nature_value")
public class NatureValue implements Serializable{

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "product_nature_id")
    private Long natureId;


}
