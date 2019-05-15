package com.tom.cf.api.v2.parser;


import com.tom.cf.api.v2.model.Ecg;

import java.io.File;

public abstract class FileParser implements Parser{
    protected File ecgFile;

    public FileParser(){}
    public FileParser(File ecgFile){
        if(ecgFile == null){
            throw new NullPointerException("ecgFile: 不可为空");
        }
        this.ecgFile = ecgFile;
    }

    @Override
    public Ecg ecgParse() {
        if(ecgFile == null){
            throw new NullPointerException("ecgFile: 不可为空");
        }

        if(!isSupportFileType(this.ecgFile)){
            throw new IllegalArgumentException("ecgFile: 不支持的文件类型");
        }
        return doEcgParser();
    }

    public abstract Ecg doEcgParser();
    public abstract boolean isSupportFileType(File ecgFile);

    public File getEcgFile() {
        return ecgFile;
    }

    public void setEcgFile(File ecgFile) {
        this.ecgFile = ecgFile;
    }
}
