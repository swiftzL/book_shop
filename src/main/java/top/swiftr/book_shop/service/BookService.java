package top.swiftr.book_shop.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import top.swiftr.book_shop.common.BaseService;
import top.swiftr.book_shop.entity.Book;

import java.util.List;

public interface BookService extends BaseService<Book> {
    public PageInfo<Book> findAll(Integer pagenum, Integer pagesize);
    public List<Book> findByRand();

    /**
     * 查找同类型书籍
     * @param tid
     * @return
     */
    public PageInfo<Book> findByTid(Integer tid,Integer pagenum,Integer pagesize);

    /**
     *缓存书籍 如果tid为null则缓存所有
     * @param tid
     */
    public void cacheBook(Integer tid,PageInfo<Book> books);


}
