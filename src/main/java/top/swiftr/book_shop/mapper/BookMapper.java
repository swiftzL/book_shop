package top.swiftr.book_shop.mapper;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.config.MyMapper;
import top.swiftr.book_shop.entity.Book;

@Service
public interface BookMapper extends MyMapper<Book> {

}
