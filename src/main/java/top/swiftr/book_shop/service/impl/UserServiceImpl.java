package top.swiftr.book_shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.swiftr.book_shop.common.impl.BaseServiceImpl;
import top.swiftr.book_shop.entity.User;
import top.swiftr.book_shop.mapper.UserMapper;
import top.swiftr.book_shop.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public Boolean isExist(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        return !userMapper.selectByExample(example).isEmpty();

    }

    @Override
    public User findByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }
}
