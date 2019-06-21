package top.swiftr.book_shop.controller;


import cn.hutool.core.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import top.swiftr.book_shop.entity.Address;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.service.AddressService;
import top.swiftr.book_shop.service.UserService;
import top.swiftr.book_shop.utils.JwtUtil;
import top.swiftr.book_shop.vo.ResponseCode;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/add")
    public ResponseCode add(Address address, HttpServletRequest request){
        /**
         * 从jwt中获取username
         */
        String username = JwtUtil.parseJWT(request.getHeader("jwt")).get("username").asString();
        address.setUid(userService.findByUsername(username).getUid());
        try{
            addressService.save(address);
            return new ResponseCode(200,"添加成功");
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }

    @GetMapping("/delete")
    public ResponseCode delete(Integer id,HttpServletRequest request){
        if (id == null || !NumberUtil.isInteger(id.toString())){
            return ResponseCode.error();
        }
        String username = JwtUtil.parseJWT(request.getHeader("jwt")).get("username").asString();
        Integer uid = (userService.findByUsername(username).getUid());
        try{
            addressService.deleteByIdAndUid(id,uid);
            return new ResponseCode(200,"删除成功");
        }catch (Exception e){
            return new ResponseCode(403,"非法操作");
        }

    }

    @GetMapping("/update")
    public ResponseCode update(Address address,HttpServletRequest request){
        String username = JwtUtil.parseJWT(request.getHeader("jwt")).get("username").asString();
        Integer uid = (userService.findByUsername(username).getUid());
        if (!addressService.selectByKey(address.getId()).getUid().equals(uid)){
            return new ResponseCode(403,"非法操作");
        }
        address.setUid(uid);
        try{
            addressService.updateAll(address);
            return new ResponseCode(200,"修改成功");
        }catch (Exception e){
            return ResponseCode.error();
        }
    }

    @GetMapping("/findAll")
    public ResponseCode findAll(HttpServletRequest request){
        String username = JwtUtil.parseJWT(request.getHeader("jwt")).get("username").asString();
        Integer uid = (userService.findByUsername(username).getUid());
        try {
            return ResponseCode.success(addressService.findAllByUid(uid));
        }catch (Exception e){
            return ResponseCode.error();
        }
    }





}
