package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类表操作
 *
 * @author zengzhicheng
 */
@Mapper
public interface CategoryMapper {

    /**
     * 插入新增的分类数据
     *
     * @param category 分类数据
     */
    @Insert("insert into category " +
            "(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type}, #{name},#{sort},#{status},#{createTime},#{updateTime}," +
            "#{createUser},#{updateUser})")
    void insert(Category category);
}
