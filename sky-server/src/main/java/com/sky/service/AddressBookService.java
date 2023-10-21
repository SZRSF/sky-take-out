package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * 地址簿业务接口
 *
 * @author zengzhicheng
 */
public interface AddressBookService {

    /**
     * 新增地址
     *
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 查询当前登录对象的所有地址信息
     *
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 根据Id查询地址
     *
     * @param id
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 跟新地址
     *
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 设置默认地址
     *
     * @param addressBook
     */
    void setDefault(AddressBook addressBook);

    /**
     * 根据Id删除地址
     *
     * @param id
     */
    void deleteById(Long id);

}
