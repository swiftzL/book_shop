package top.swiftr.book_shop.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.swiftr.book_shop.common.impl.BaseServiceImpl;
import top.swiftr.book_shop.entity.Address;
import top.swiftr.book_shop.mapper.AddressMapper;
import top.swiftr.book_shop.service.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public void deleteByIdAndUid(Integer id, Integer uid) {
        Example example = new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        criteria.andEqualTo("id",id);
        addressMapper.deleteByExample(example);
    }

    @Override
    public List<Address> findAllByUid(Integer uid) {
        Example example = new Example(Address.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        return addressMapper.selectByExample(example);

    }
}
