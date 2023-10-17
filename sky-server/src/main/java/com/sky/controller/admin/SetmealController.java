package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐接口类
 *
 * @author zengzhicheng
 */
@Api("套餐接口类")
@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDTO 前端传来的套餐信息
     * @return 返回响应
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result<String> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐:{}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询参数
     * @return 返回分页查询结果
     */
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据套餐id查询
     *
     * @param id 套餐id
     * @return 返回查询结果
     */
    @ApiOperation("根据套餐id查询")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据套餐id查询：{}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     *
     * @param setmealVO 修改的套餐信息
     * @return 返回响应
     */
    @ApiOperation("修改套餐")
    @PutMapping
    public Result<String> update(@RequestBody SetmealVO setmealVO) {
        log.info("修改套餐：{}", setmealVO);
        setmealService.update(setmealVO);
        return Result.success();
    }

    /**
     * 套餐起售、停售
     *
     * @param status 套餐状态
     * @param id 套餐id
     * @return 返回响应
     */
    @ApiOperation("套餐起售、停售")
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(Long id, @PathVariable Integer status){
        log.info("套餐为：{}, 修改状态为: {}", id, status);
        setmealService.startOrStop(id,status);
        return Result.success();
    }
}
