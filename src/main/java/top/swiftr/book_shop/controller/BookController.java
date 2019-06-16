package top.swiftr.book_shop.controller;


import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.service.BookService;
import top.swiftr.book_shop.vo.ResponseCode;

@Api(value = "图书",description = "图书api")
@RestController
@RequestMapping("/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "图书列表",
            notes = "查找所有图书并分页",
            response = ResponseCode.class
    )
    @GetMapping("/list")
    public ResponseCode findAll(Integer pagenum,Integer pagesize){
        return ResponseCode.success(bookService.findAll(pagenum,pagesize));
    }

    @ApiOperation(value = "增加图书")
    @PostMapping("/add")
    public ResponseCode add(@Validated Book book){
        try {
            bookService.save(book);
            return ResponseCode.success();
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }

    }

    @PutMapping("/update")
    public ResponseCode update(@Validated Book book){
        if (book.getBid() == null){
            throw new GlobalException("图书id不存在");
        }
        try {
            bookService.updateNotNull(book);
            return ResponseCode.success();
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseCode delete(@Validated Book book){
        if (book.getBid() == null){
            throw new GlobalException("图书id不存在");
        }
        try {
            bookService.delete(book.getBid());
            return ResponseCode.success();
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/seckill")
    public ResponseCode findSpikeBook(){
        return ResponseCode.success(bookService.findByRand());
    }

    @GetMapping("/findById")
    public ResponseCode findById(String bid){
        try{
            return ResponseCode.success(bookService.selectByKey(bid));
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }
}
