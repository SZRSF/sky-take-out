package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

/**
 * 购物车业务接口
 *
 * @author zengzhicheng
 */
public interface ShoppingCartService {

    /**
     * 添加购物车业务接口
     *
     * @param shoppingCartDTO 添加到购物车中的菜品信息
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
