package com.chengym.home.Controller;

import com.chengym.home.Bean.User;
import com.chengym.home.Service.UserService;
import com.chengym.home.utils.ResponseBean;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    HttpSession session;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        session.setAttribute("JSESSIONID","a");
        return userService.findAllUser(pageNum,pageSize);
    }
    @ResponseBody
    @RequestMapping(value = "/getcode", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public ResponseBean getCode(@RequestBody JSONObject requestJson ){
        ResponseBean responseBean = new ResponseBean();
        String resCode = requestJson.get("resCode") instanceof JSONNull ?"":requestJson.get("resCode").toString();
        String encryptedData = requestJson.get("encryptedData") instanceof JSONNull ?"":requestJson.get("encryptedData").toString();
        String iv = requestJson.get("iv") instanceof JSONNull ?"":requestJson.get("iv").toString();
//        String responseCode = RandomStringUtils.random(20,resCode+ Constant.ALL_NUM+Constant.ALL_LETTER);
        logger.info("resCode:"+ resCode+"; encrypteData:"+encryptedData+"; iv:"+iv);
        String userOpenidAndsessionKey = userService.getUserOpenidAndsessionKey(resCode);
        try {
            responseBean = userService.checkUserInfo(userOpenidAndsessionKey,encryptedData,iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBean;
    }
}
