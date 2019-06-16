package top.swiftr.book_shop.service;

import com.github.pagehelper.Page;
import top.swiftr.book_shop.common.BaseService;
import top.swiftr.book_shop.entity.Book;

public interface BookService extends BaseService<Book> {
    public Page<Book> findAll(Integer pagenum,Integer pagesize);
}
