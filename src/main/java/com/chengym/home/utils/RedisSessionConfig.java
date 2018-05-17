package com.chengym.home.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
@ImportResource(locations={"classpath:spring-redis.xml"})
public class RedisSessionConfig {
}
