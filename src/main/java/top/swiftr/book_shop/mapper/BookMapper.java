package top.swiftr.book_shop.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.config.MyMapper;
import top.swiftr.book_shop.entity.Book;

import java.util.List;

@Service
public interface BookMapper extends MyMapper<Book> {

    @Select("select * from book order by rand() limit 5")
    public List<Book> findbyRand();
}
