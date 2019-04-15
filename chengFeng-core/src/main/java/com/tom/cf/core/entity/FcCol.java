package com.tom.cf.core.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 分类
 */
@Data
@Entity
@Table(name = "cf_col")
public class FcCol {

    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(name = "id", length = 50, columnDefinition = "varchar(50) COMMENT '主键'")
    private String id;

    @Column(name = "name",length = 50,columnDefinition="varchar(50) COMMENT '分类名称'")
    private String name;

    @Column(name = "type",length = 50,columnDefinition="varchar(50) COMMENT '分类类型'")
    private String type;
}
