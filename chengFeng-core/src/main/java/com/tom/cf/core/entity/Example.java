package com.tom.cf.core.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @method: Demo类
 */
@Data
@Entity
@Table(name = "cf_demo")
public class Example {
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(name = "id", length = 50, columnDefinition = "varchar(50) COMMENT '主键'")
    private String id;

    @Column(name = "user_id", length = 50, columnDefinition = "varchar(50) COMMENT 'Demo人主键'")
    private String userId;

    @Column(name = "user_name", length = 50, columnDefinition = "varchar(50) COMMENT 'Demo人姓名(冗余字段)'")
    private String userName;

    @Column(name = "name", length = 50, columnDefinition = "varchar(50) COMMENT 'Demo关键词'")
    private String name;

    @Column(name = "fileName", length = 400, columnDefinition = "varchar(400) COMMENT '问题文件名'")
    private String fileName;

    @Column(name = "count", length = 10, columnDefinition = "int(10) default 0 COMMENT  '点击量'")
    private Integer count;

    @Column(name = "content", length = 400, columnDefinition = "varchar(400) COMMENT '大致内容'")
    private String content;

    @Column(name = "create_at",columnDefinition="datetime COMMENT '创建时间'")
    private Date createAt;

    @Column(name = "update_at",columnDefinition="datetime COMMENT '修改时间'")
    private Date updateAt;



}