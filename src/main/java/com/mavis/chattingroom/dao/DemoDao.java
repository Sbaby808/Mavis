package com.mavis.chattingroom.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author xinjingjie
 */
@Mapper
public interface DemoDao {
    /**
     * @return 数据库名字
     */
    String getDataBaseName();
}
