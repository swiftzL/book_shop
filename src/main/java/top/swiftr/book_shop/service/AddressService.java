package top.swiftr.book_shop.service;

import top.swiftr.book_shop.common.BaseService;
import top.swiftr.book_shop.entity.Address;

import java.util.List;

public interface AddressService extends BaseService<Address> {

    /**
     * 根据id和用户id删除地址
     * @param id
     * @param uid
     */
    public void deleteByIdAndUid(Integer id,Integer uid);

    /**
     * 根据用户id查找所有地址
     * @param uid
     * @return
     */
    public List<Address> findAllByUid(Integer uid);

}
