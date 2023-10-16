package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 *
 * @author zengzhicheng
 */
@Slf4j
@Api(tags = "菜品相关接口")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dto 新增菜品信息
     * @return 返回响应
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result<String> save(@RequestBody DishDTO dto) {
        log.info("新增菜品: {}", dto);
        dishService.save(dto);

        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 分页参数
     * @return 查询结果
     */
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据Id查询菜品
     *
     * @param id 要查询菜品的id
     * @return 返回查询的结果
     */
    @ApiOperation("根据菜品查询菜品s")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品:{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品的新数据
     * @return 返回响应
     */
    @ApiOperation("修改菜品")
    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        dishService.update(dishDTO);
        return  Result.success();
    }

    /**
     * 菜品起售、停售状态修改
     *
     * @param status 修改的状态
     * @return 返回响应
     */
    @ApiOperation("菜品起售、停售更改")
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@RequestParam(value = "id") Long id,@PathVariable Integer status) {
        log.info("菜品：{}的状态改为:{}", id, status);
        dishService.startOrStop(id, status);
        return Result.success();
    }

    /**
     * 批量删除菜品
     *
     * @param ids 要删除的Id
     * @return 返回响应数据
     */
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.delete(ids);

        return Result.success();
    }
}
