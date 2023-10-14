package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 分类分页查询业务实现
     *
     * @param categoryPageQueryDTO 分页查询参数
     * @return 返回查询结果
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        // 1.使用分页插件设置分页参数
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        // 2.进行查询
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        log.info("" + page.getResult());
        // 3.返回查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     *  根据分类查询
     *
     * @param type 分类参数
     * @return 返回分页查询结果
     */
    @Override
    public List<Category> listQuery(String type) {
        // 返回查询结果
        return categoryMapper.listQuery(type);
    }
}
