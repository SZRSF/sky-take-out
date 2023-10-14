package com.sky.service;


import com.sky.dto.CategoryDTO;

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

}
