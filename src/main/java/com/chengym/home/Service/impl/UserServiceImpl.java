package com.chengym.home.Service.impl;

import com.chengym.home.Bean.User;
import com.chengym.home.Dao.UserMapper;
import com.chengym.home.Service.UserService;
import com.chengym.home.utils.HttpRequest;
import com.chengym.home.utils.ResponseBean;
import com.chengym.home.utils.encryptrd.AesCbcUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    public String getUserOpenidAndsessionKey(String code){
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
        String result = "";
        try{
            result = HttpRequest.sendGet(url,param);
        } catch (Exception e){
            log.error(e.toString());
        }
        log.info(result);
        return result;
    }

    @Override
    public ResponseBean checkUserInfo(String userOpenidAndsessionKey, String encryptedData, String iv) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Map<String,String> map = (new ObjectMapper()).readValue(userOpenidAndsessionKey,Map.class);
        String decodeEncryptedData = decode(encryptedData);
        String userInfo = null;
        String userOpenId = map.get("openid");
        String sessionKey = map.get("session_key");
        decodeEncryptedData = decodeEncryptedData.replaceAll(" ","\\+");
        userInfo = AesCbcUtil.decrypt(decodeEncryptedData, sessionKey, iv, "UTF-8");
        if(StringUtils.isNotEmpty(userInfo)){
            if(map!= null) {
                Map<String,String> userInfoMap =  (new ObjectMapper()).readValue(userInfo,Map.class);
                User user = getUserById(userOpenId);
                if (user == null) {
                    user = new User();
                    user.setId(userOpenId);
                    user.setName(userInfoMap.get("nickName"));
                    addUser(user);
                }else{
                    user.setName(userInfoMap.get("nickName"));
                    this.updateByPrimaryKeySelective(user);
                }
                responseBean.setResult(user);
                responseBean.setCode(HttpStatus.OK.toString());
                responseBean.setSuccess(true);
            }else{
                responseBean.setSuccess(false);
            }
        }
        return responseBean;
    }
    public String decode(String url){
        try {
            String prevURL="";
            String decodeURL=url;
            while(!prevURL.equals(decodeURL))
            {
                prevURL=decodeURL;
                decodeURL= URLDecoder.decode( decodeURL, "UTF-8" );
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" +e.getMessage();
        }
    }
    public User getUserById(String Id){
        return userMapper.selectUserById(Id);
    }
    public int updateByPrimaryKeySelective(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }
}


