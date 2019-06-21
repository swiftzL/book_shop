package top.swiftr.book_shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.swiftr.book_shop.service.OrderService;
import top.swiftr.book_shop.vo.OrderMo;
import top.swiftr.book_shop.vo.ResponseCode;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderCntroller {

    @Autowired
    private OrderService orderService;


    @PostMapping("/add")
    public ResponseCode add(@RequestBody List<OrderMo> orderList){
        System.out.println(orderList);
        return ResponseCode.error();
    }



}
