package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

/**
 * 分类接口
 *
 * @author zengzhicheng
 */
public interface CategoryService {

    /**
     * 新增分类
     *
     * @param categoryDTO 新增分类的数据
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO 分页查询参数
     * @return 返回查询结果
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
