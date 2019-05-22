package com.tom.cf.core.dao.mapper;

import com.tom.cf.core.entity.ResultTwo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @method:统计图
 */
@Mapper
public interface EchartsMapper {
    List<Map<String,Object>> getZZData();

    List<ResultTwo> getZSData();
}
