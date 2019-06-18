package top.swiftr.book_shop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import top.swiftr.book_shop.common.impl.BaseServiceImpl;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.mapper.BookMapper;
import top.swiftr.book_shop.redisVo.BookPage;
import top.swiftr.book_shop.redisVo.RedisObj;
import top.swiftr.book_shop.service.BookService;
import top.swiftr.book_shop.utils.RedisService;

import java.util.List;

@Service
public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @Override
    public PageInfo<Book> findAll(Integer pagenum, Integer pagesize) {
        PageHelper.startPage(pagenum,pagesize);
        Page<Book> bookPage = (Page)this.selectAll();
        return bookPage.toPageInfo();
    }

    @Override
    public List<Book> findByRand() {
        return bookMapper.findbyRand();
    }

    @Override
    public PageInfo<Book> findByTid(Integer tid,Integer pagenum,Integer pagesize) {
        /**
         * Ecample 封装查询条件
         */
        Example example = new Example(Book.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tid",tid);
        /**
         * 分页开始
         */
        PageHelper.startPage(pagenum,pagesize);
        Page<Book> bookPage = (Page)bookMapper.selectByExample(example);
        return bookPage.toPageInfo();
    }

    @Override
    public void cacheBook(BookPage bookPage, PageInfo<Book> books) {
        redisService.setObj(new RedisObj(bookPage,books));
    }

    @Override
    public PageInfo<Book> getByBookPage(BookPage bookPage) {
        RedisObj<PageInfo> pageInfoRedisObj =  redisService.findBykey(bookPage);
        if (pageInfoRedisObj == null){
            return null;
        }
        return pageInfoRedisObj.getT();
    }


}
