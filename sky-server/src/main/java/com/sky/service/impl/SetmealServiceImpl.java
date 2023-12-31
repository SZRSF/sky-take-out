package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
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

    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐业务实现
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
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        // 3.返回查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据套餐id查询关联的菜品
     *
     * @param id 套餐id
     * @return 返回查询的数据
     */
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

    /**
     * 修改套餐
     *
     * @param setmealVO 修改的套餐数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SetmealVO setmealVO) {
        // 0.开启事物
        // 1.获取套餐对象
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealVO, setmeal);

        // 2.将获取的套餐对象更新到数据库中
        setmealMapper.update(setmeal);

        // 3.获取套餐Id
        Long setmealId = setmealVO.getId();

        // 4.删除套餐菜品关联表
        setmealDishMapper.delete(setmealId);

        // 4.获取更新套餐菜品数据
        List<SetmealDish> setmealDishes = setmealVO.getSetmealDishes();
        // 4.1判断套餐关联菜品是否为空
        if (setmealDishes != null && setmealDishes.size() > 0) {
            // 4.2 设置套餐id
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            // 5.将套餐关联的菜品数据插入套餐菜品关联表中
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 套餐起售、停售
     *
     * @param id 套餐Id
     * @param status 套餐状态
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();

        if (status.equals(StatusConstant.DISABLE)) {
            // 判断该套餐内是否有套餐未上架
            List<SetmealDish> dishIds = setmealDishMapper.getByDishId(id);
            for (SetmealDish dishId : dishIds) {
                if (dishMapper.getById(dishId.getDishId()).getStatus().equals(StatusConstant.ENABLE)) {
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     *
     * @param ids 套餐id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        // 1.判断套餐的状态，如果有状态为在售，则不能删除
        for (Long id : ids) {
            if (setmealMapper.getById(id).getStatus().equals(StatusConstant.DISABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        // 2.删除套餐表里当前套餐
        setmealMapper.deleteBatch(ids);
        // 3.删除套餐菜品关联表的数据
        setmealDishMapper.deleteBatch(ids);
    }

    /**
     * 条件查询
     *
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     *
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
