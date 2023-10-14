package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 分类实现业务类
 *
 * @author zengzhicheng
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     *
     * @param categoryDTO 新增分类的数据
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        // 1.创建分类对象
        Category category = new Category();

        // 2.复制前端传来的要新增的分类数据
        BeanUtils.copyProperties(categoryDTO, category);

        // 3.创建日期和更新日期
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 4.创建人和更新人
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        // 5.设置默认状态为启用1
        category.setStatus(1);

        // 5.数据库插入新增分类
        categoryMapper.insert(category);
    }
}
