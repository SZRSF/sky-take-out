package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    void insert(Category category);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO 查询的参数
     * @return 返回查询的结果
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据类型查询
     *
     * @param type 类型参数
     * @return 返回查询结果
     */
    List<Category> listQuery(Integer type);

    /**
     * 修改分类
     *
     * @param category 分类参数
     */
    void update(Category category);

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    void delete(String id);
}
