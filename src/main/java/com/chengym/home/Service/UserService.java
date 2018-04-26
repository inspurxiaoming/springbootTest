package com.chengym.home.Service;

import com.chengym.home.Bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);

    boolean getUserOpenid(String code);
}
