package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐业务实现类
 *
 * @author zengzhicheng
 */
@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 套餐业务实现
     *
     * @param setmealDTO 套餐信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SetmealDTO setmealDTO) {
        // 0.开启事物
        // 1.获取套餐对象
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 2.将获取的套餐对象插入数据库中
        setmealMapper.insert(setmeal);

        // 3.获取套餐插入数据库返回的套餐Id
        Long setmealId = setmeal.getId();

        // 4.获取套餐菜品数据
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        // 4.1判断套餐关联菜品是否为空
        if (setmealDishList != null && setmealDishList.size() > 0) {
            // 4.2 设置套餐id
            setmealDishList.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            // 5.将套餐关联的菜品数据插入套餐菜品关联表中
            setmealDishMapper.insertBatch(setmealDishList);
        }
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询参数
     * @return 返回分页查询结果
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        // 1.设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        // 2.进行分页查询
        Page<Setmeal> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        // 3.返回查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public SetmealVO getById(Long id) {
        // 1.根据套餐Id查询套餐数据
        Setmeal setmeal = setmealMapper.getById(id);
        // 2.根据套餐id查询套餐关联的菜品
        List<SetmealDish> setmealDishes = setmealDishMapper.getByDishId(id);
        // 3，拼接返回数据
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }
}
