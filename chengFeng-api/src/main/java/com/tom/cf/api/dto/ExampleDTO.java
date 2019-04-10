package com.tom.cf.api.dto;

import com.tom.cf.api.utils.FcFileUtil;
import com.tom.cf.core.dao.config.WebUtil;
import com.tom.cf.core.entity.FcExample;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @method: Demo文件类
 */
@Data
public class ExampleDTO {
    private String id;

    private String userId;

    private String userName;

    private String name;

    private String fileName;

    private Integer count;

    private Date createAt;

    private Date updateAt;

    private String content;

    private MultipartFile fileNameFile;

    public void setFileNameFile(MultipartFile fileNameFile) {
        this.fileNameFile = fileNameFile;
        setFileName(fileNameFile.getOriginalFilename());
    }

    public void storageFile(String dir) throws IOException {
        FcFileUtil.storageFile(dir,"EXAMPLE",WebUtil.getCurrentUser().getUserId(),fileNameFile);
    }

    public static File getFile(String dir, String userId, String fileName){
        return FcFileUtil.findFile(dir,"EXAMPLE",userId,fileName);
    }

    public FcExample convert(){
        FcExample e = new FcExample();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
