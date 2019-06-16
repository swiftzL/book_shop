package top.swiftr.book_shop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.common.impl.BaseServiceImpl;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.mapper.BookMapper;
import top.swiftr.book_shop.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

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
}
