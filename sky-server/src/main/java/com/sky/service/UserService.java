package com.sky.service;


import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * 用户业务实现接口
 *
 * @author zengzhicheng
 */
public interface UserService {

    /**
     * 微信登录
     *
     * @param userLoginDTO 登录信息
     * @return 返回登录用户信息
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
