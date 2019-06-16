package top.swiftr.book_shop.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.entity.BookType;
import top.swiftr.book_shop.service.BookTypeService;
import top.swiftr.book_shop.vo.ResponseCode;

import java.util.List;

@Api(value = "BookTypeController",tags = {"图书类型api"})
@RestController
@RequestMapping("/booktype")
public class BooktypeController {

    @Autowired
    private BookTypeService bookTypeService;

    @GetMapping("/list")
    public ResponseCode findAll(){
        return ResponseCode.success(bookTypeService.selectAll());
    }
}
