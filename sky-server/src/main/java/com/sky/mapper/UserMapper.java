package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * C端用户数据库操作
 *
 * @author zengzhicheng
 */
@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     *
     * @param openid 用户openid
     * @return 返回查询的用户信息
     */
    User getByOpenid(String openid);

    /**
     * 插入用户数据
     *
     * @param user 要插入的用户数据
     */
    void insert(User user);
}
