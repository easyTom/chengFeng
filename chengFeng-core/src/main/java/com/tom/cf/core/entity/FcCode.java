package com.tom.cf.core.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @method:Code类
 */
@Data
@Entity
@Table(name = "cf_code")
public class FcCode {
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(name = "id", length = 50, columnDefinition = "varchar(50) COMMENT '主键'")
    private String id;

    @Column(name = "user_id", length = 50, columnDefinition = "varchar(50) COMMENT 'Demo人主键'")
    private String userId;

    @Column(name = "user_name", length = 50, columnDefinition = "varchar(50) COMMENT 'Demo人姓名(冗余字段)'")
    private String userName;

    @Column(name = "title", length = 50, columnDefinition = "varchar(50) COMMENT '代码标题'")
    private String title;

    @Column(name = "content", length = 400, columnDefinition = "longtext COMMENT '内容或者base64图片'")
    private String content;

    @Column(name = "create_at",columnDefinition="datetime COMMENT '创建时间'")
    private Date createAt;

    @Column(name = "update_at",columnDefinition="datetime COMMENT '修改时间'")
    private Date updateAt;



}