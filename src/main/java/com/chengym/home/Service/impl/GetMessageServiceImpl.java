package com.chengym.home.Service.impl;

import com.chengym.home.Bean.Message;
import com.chengym.home.Dao.GetMessageMapper;
import com.chengym.home.Service.GetMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "getMessageService")
public class GetMessageServiceImpl implements GetMessageService {
    private Logger log = LoggerFactory.getLogger(GetMessageServiceImpl.class);

    @Autowired
    GetMessageMapper getMessageMapper;

    @Override
    public List<Message> getListForPage() {
        return getMessageMapper.getListForPage();
    }
}


