package com.tom.cf.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "nm_user_right")
public class UseRright {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition="int(10) COMMENT '主键'")
    private Integer id;
    @Column(name = "user_id",length = 50,columnDefinition="varchar(50) COMMENT '外键'")
    private String userId;
    @Column(name = "action_alias",length = 50,columnDefinition="varchar(50) COMMENT '功能别名'")
    private String actionAlias;
}
