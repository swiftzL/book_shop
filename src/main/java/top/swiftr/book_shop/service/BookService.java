package top.swiftr.book_shop.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import top.swiftr.book_shop.common.BaseService;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.redisVo.BookPage;

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
     * @param bookPage
     */
    public void cacheBook(BookPage bookPage, PageInfo<Book> books);

    /**
     * 获取缓存中的page信息
     * @param bookPage
     * @return
     */
    public PageInfo<Book> getByBookPage(BookPage bookPage);

    /**
     * 根据书名查对应Book对象
     * @param bookname
     * @return
     */
    public Book findByBookName(String bookname);

    /**
     * 根据书id查找Book对象
     * @param bid
     * @return
     */
    public Book findByBookName(Integer bid);



}
