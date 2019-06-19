package top.swiftr.book_shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.entity.Car;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.service.CarService;
import top.swiftr.book_shop.vo.ResponseCode;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 添加购物车
     * @param car
     * @return
     */
    @GetMapping("/add")
    public ResponseCode add(Car car){

        try{
            carService.save(car);
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
        return ResponseCode.success();
    }

}
