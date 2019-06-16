package top.swiftr.book_shop.mapper;

import org.springframework.stereotype.Service;
import top.swiftr.book_shop.config.MyMapper;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.entity.User;

@Service
public interface UserMapper extends MyMapper<User> {

}
