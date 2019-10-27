package com.zc;

import com.zc.dao.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// 单元测试,测试dao层,向redis中存值,再取出
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

	public static Logger logger= LoggerFactory.getLogger(SpringbootRedisApplicationTests.class);
	@Test
	public void contextLoads() {
	}

	@Autowired
	RedisDao redisDao;
	@Test
	public void testRedis(){
		redisDao.setKey("name","zc");
		redisDao.setKey("age","11");
		logger.info(redisDao.getValue("name"));
		logger.info(redisDao.getValue("age"));
	}
}
