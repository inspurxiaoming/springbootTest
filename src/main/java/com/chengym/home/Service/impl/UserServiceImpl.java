package com.chengym.home.Service.impl;

import com.chengym.home.Bean.User;
import com.chengym.home.Dao.UserMapper;
import com.chengym.home.Service.UserService;
import com.chengym.home.utils.HttpRequest;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public int addUser(User user) {

        return userMapper.insertSelective(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }
    @Override
    public boolean getUserOpenid(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appid = "wx8c0e94d2e3a3d4d4";
        String secret = "10f8d287ee2c99c4b07b03b266515a36";
        String grantType = "authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> postParameters = new HashMap<String, String>();
        postParameters.put("appid", appid);
        postParameters.put("secret", secret);
        postParameters.put("js_code", code);
        postParameters.put("grant_type", grantType);
        String param = "appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
//        String responseEntity =  restTemplate.getForObject(url,String.class,postParameters);
        String result = HttpRequest.sendGet(url,param);
        log.info(result);
        return true;
    }
}


