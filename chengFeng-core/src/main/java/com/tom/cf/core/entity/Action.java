package com.tom.cf.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cf_action")
public class Action {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "id",columnDefinition="int(10) COMMENT '主键'")
    private Integer id;
    @Column(name = "action_name",length = 50,columnDefinition="varchar(50) COMMENT '功能名称'")
    private String actionName;
    @Column(name = "action_alias",length = 50,columnDefinition="varchar(50) COMMENT '功能别名'")
    private String actionAlias;
}
