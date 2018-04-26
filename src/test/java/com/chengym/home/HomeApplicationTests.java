package com.chengym.home;

import com.chengym.home.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeApplicationTests {

	@Autowired
	UserService users;
	@Test
	public void contextLoads() {
		users.getUserOpenid("081Z6XuU1FruBV082huU16xBuU1Z6XuO");
	}

}
