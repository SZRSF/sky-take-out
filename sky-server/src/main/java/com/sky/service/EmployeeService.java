package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

/**
 * @author zengzhicheng
 */
public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 前端登录请求数据
     * @return 返回数据库查询结果
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employee 员工的信息
     */
    void save(EmployeeDTO employee);

}
