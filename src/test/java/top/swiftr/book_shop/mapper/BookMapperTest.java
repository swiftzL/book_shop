package top.swiftr.book_shop.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;
    @Test
    public void testRand(){
        System.out.println(bookMapper.selectByPrimaryKey("497"));
    }

}