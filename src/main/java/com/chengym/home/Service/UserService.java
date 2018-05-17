package com.chengym.home.Service;

import com.chengym.home.Bean.User;
import com.chengym.home.utils.ResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);

    String getUserOpenidAndsessionKey(String code);

    ResponseBean checkUserInfo(String userOpenidAndsessionKey, String encryptedData, String iv) throws Exception;
}
