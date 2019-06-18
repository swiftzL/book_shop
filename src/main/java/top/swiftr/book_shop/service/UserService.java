package top.swiftr.book_shop.service;

import top.swiftr.book_shop.common.BaseService;
import top.swiftr.book_shop.entity.User;

public interface UserService extends BaseService<User> {
    public Boolean isExist(String username);

}
