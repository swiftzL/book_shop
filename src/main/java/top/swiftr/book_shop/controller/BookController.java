package top.swiftr.book_shop.controller;


import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.service.BookService;
import top.swiftr.book_shop.vo.ResponseCode;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public ResponseCode findAll(Integer pagenum,Integer pagesize){
        return ResponseCode.success(bookService.findAll(pagenum,pagesize));
    }
}
