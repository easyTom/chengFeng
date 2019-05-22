package com.tom.cf.core.service;

import com.tom.cf.core.dao.mapper.EchartsMapper;
import com.tom.cf.core.entity.ResultTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EchartsService {

    @Autowired
    private EchartsMapper echartsMapper;

    public List<Map<String,Object>> getZZData() {
        return echartsMapper.getZZData();
    }

    public List<Map<String,Object>> getBZData() {
            //暂时用一样的
            return echartsMapper.getZZData();
    }
    public List<ResultTwo> getZSData() {
            return echartsMapper.getZSData();
    }
}
