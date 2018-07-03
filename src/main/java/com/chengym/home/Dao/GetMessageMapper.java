package com.chengym.home.Dao;

import com.chengym.home.Bean.Message;
import com.chengym.home.Bean.User;

import java.util.List;

public interface GetMessageMapper {

    List<Message> getListForPage();
}