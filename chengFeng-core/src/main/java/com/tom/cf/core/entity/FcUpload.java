package com.tom.cf.core.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 分类
 */
@Data
@Entity
@Table(name = "cf_upload")
public class FcUpload {

    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(name = "id", length = 50, columnDefinition = "varchar(50) COMMENT '主键'")
    private String id;

    @Column(name = "file_name",length = 50,columnDefinition="varchar(50) COMMENT '文件名称'")
    private String fileName;

    @Column(name = "file_size",length = 11,columnDefinition="int(11) COMMENT '文件大小'")
    private long fileSize;

    @Column(name = "type",length = 50,columnDefinition="varchar(50) COMMENT '文件类别'")
    private String type;

    @Column(name = "file_path",length = 150,columnDefinition="varchar(150) COMMENT '文件路径'")
    private String filePath;
}
