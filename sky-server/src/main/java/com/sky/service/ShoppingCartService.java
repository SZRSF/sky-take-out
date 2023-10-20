package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

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

    /**
     * 查看购物车业务接口
     *
     * @return 返回结果
     */
    List<ShoppingCart> showShoppingCart();
}
