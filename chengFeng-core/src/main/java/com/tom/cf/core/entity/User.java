package com.tom.cf.core.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "cf_user")
public class User implements Serializable {
    @GenericGenerator(name="jpa-uuid", strategy="uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(name = "user_id",length = 50,columnDefinition="varchar(50) COMMENT '主键'")
    private String userId;

    @Column(name = "user_name",length = 50,columnDefinition="varchar(50) COMMENT '用户名'")
    private String userName;

    @Column(name = "password",length = 100,columnDefinition="varchar(100) COMMENT '密码'")
    private String password;

    @Column(name = "real_name",length = 20,columnDefinition="varchar(20) COMMENT '真实姓名'")
    private String realName;

    @Column(name = "mobile",length = 20,columnDefinition="varchar(20) COMMENT '手机'")
    private String mobile;

    @Column(name = "org_name",length = 100,columnDefinition="varchar(100) COMMENT '所属单位'")
    private String orgName;

    @Column(name = "token",length = 50,columnDefinition="varchar(50) COMMENT '校验请求安全'")
    private String token;

    @Column(name = "status",columnDefinition="int(10) COMMENT '状态' default 0")
    private Integer status;

    @Column(name = "memo",length = 100,columnDefinition="varchar(100) COMMENT '备注'")
    private String memo;

    @Column(name = "salt",length = 100,columnDefinition="varchar(100) COMMENT '盐值'")
    private String salt;

    @CreatedDate
    @Column(name = "created_at",columnDefinition="datetime COMMENT '创建时间'")
    private Date createdAt;

}
