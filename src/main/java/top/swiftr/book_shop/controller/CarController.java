package top.swiftr.book_shop.controller;


import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import top.swiftr.book_shop.annotation.CheckToken;
import top.swiftr.book_shop.entity.Book;
import top.swiftr.book_shop.entity.Car;
import top.swiftr.book_shop.enums.StatusEnum;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.service.BookService;
import top.swiftr.book_shop.service.BookTypeService;
import top.swiftr.book_shop.service.CarService;
import top.swiftr.book_shop.service.UserService;
import top.swiftr.book_shop.utils.JwtUtil;
import top.swiftr.book_shop.vo.CarVo;
import top.swiftr.book_shop.vo.ResponseCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookTypeService bookTypeService;

    /**
     * 添加购物车
     *
     * @param car
     * @return
     */
    @CheckToken("user")
    @GetMapping("/add")
    public ResponseCode add(Car car, HttpServletRequest request) {
        car.setCid(null);
        /**
         * 从jwt中获取username
         */
        String username = JwtUtil.parseJWT(request.getHeader("jwt")).get("username").asString();
        car.setUid(userService.findByUsername(username).getUid());
        /**
         * 封装查询条件
         */
        Example example = new Example(Car.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", car.getBid());
        criteria.andEqualTo("uid",car.getUid());
        List<Car> cars = carService.selectByExample(example);
        //是否不为空

        if (!cars.isEmpty()){
            Car carCache = cars.get(0);
            car.setCid(carCache.getCid());
            car.setNumber(carCache.getNumber()+car.getNumber());
            carService.updateNotNull(car);
            return new ResponseCode(200, "添加成功");
        }

        try {
            carService.save(car);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return new ResponseCode(200, "添加成功");
    }

    @GetMapping("/getCar")
    public ResponseCode getCar(Integer uid) {
        //获取用户名
//        String username = "";
//        uid = userService.findByUsername(username).getUid();

        Example example = new Example(Car.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        try {
            List<Car> cars = carService.selectByExample(example);
            //拼接数据
            List<CarVo> carVos = new ArrayList<>();
            cars.forEach(e -> {
                CarVo carVo = new CarVo();
                Book book = bookService.selectByKey(e.getBid());
                carVo.setNumber(e.getNumber());
                carVo.setBookimg(book.getBookimage());
                carVo.setBookname(book.getBookname());
                carVo.setBookprice(book.getBookprice());
                carVo.setCarid(e.getCid());
                carVo.setBooktype(bookTypeService.selectByKey(book.getTid()).getTypename());
                carVos.add(carVo);
            });
            return ResponseCode.success(carVos);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }


}
