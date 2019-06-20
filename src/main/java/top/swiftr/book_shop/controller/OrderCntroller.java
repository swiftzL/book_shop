package top.swiftr.book_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.service.OrderService;

@RestController
public class OrderCntroller {

    @Autowired
    private OrderService orderService;


}
