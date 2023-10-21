package com.sky.service;

import com.sky.entity.AddressBook;

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
}
