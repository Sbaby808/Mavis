package com.mavis.chattingroom.config.init;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Sbaby
 * @Date 2021/3/29 19:38
 * @Version 1.0
 */
@Mapper
public interface InitDao {

    @Select("select param_value from application_parameters where param_name =#{key}")
    String getAppParams(String key);
}
