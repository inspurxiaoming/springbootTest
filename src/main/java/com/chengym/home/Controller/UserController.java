package com.chengym.home.Controller;

import com.chengym.home.Bean.User;
import com.chengym.home.Service.UserService;
import com.chengym.home.utils.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return userService.findAllUser(pageNum,pageSize);
    }
    @ResponseBody
    @RequestMapping(value = "/getcode")
    public Object getCode(@Param("resCode") String resCode,@Param("encryptedData")String encryptedData, @Param("iv")String iv){
        String responseCode = RandomStringUtils.random(20,resCode+ Constant.ALL_NUM+Constant.ALL_LETTER);
        logger.info("resCode:"+ resCode+"; encrypteData:"+encryptedData+"; iv:"+iv);
        userService.getUserOpenid(resCode);
        return responseCode;
    }
}
