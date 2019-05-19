package com.tom.cf.core.dao.mapper;

import com.tom.cf.core.entity.FcCol;
import org.apache.ibatis.annotations.Mapper;

/**
 * @method:mybatis
 */
@Mapper
public interface StudyMapper {
    int getIndexCount(String s);

    FcCol getIdByName(String s);
}
