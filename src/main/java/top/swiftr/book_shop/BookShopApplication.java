package top.swiftr.book_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("top.swiftr.book_shop.mapper")
public class BookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

}
