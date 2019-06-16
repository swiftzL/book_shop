package top.swiftr.book_shop.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.swiftr.book_shop.service.BookService;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @Test
    public void testFindAll(){
        System.out.println(bookService.findAll(10, 1));

    }

}