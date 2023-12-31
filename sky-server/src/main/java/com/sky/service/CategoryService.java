package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

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

    /**
     * 根据分类查询
     *
     * @param type 分类查询参数
     * @return 返回查询结果
     */
    List<Category> listQuery(Integer type);

    /**
     * 修改分类
     *
     * @param categoryDTO 修改分类的信息
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启动或者停止分类
     *
     * @param id 分类Id
     * @param status 分类参数
     */
    void startOrStop(Long id, String status);

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    void delete(String id);
}
