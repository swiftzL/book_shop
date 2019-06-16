package top.swiftr.book_shop.book;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.swiftr.book_shop.mapper.BookMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookDao {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testSelect(){
        bookMapper.selectAll().forEach(e -> System.out.println(e));
    }
}
